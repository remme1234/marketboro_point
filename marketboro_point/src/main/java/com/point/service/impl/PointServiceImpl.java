package com.point.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.point.domain.PointEventDTO;
import com.point.domain.PointEventDetailDTO;
import com.point.domain.PointSummaryDTO;
import com.point.mapper.PointMapper;
import com.point.service.PointService;


@Service
public class PointServiceImpl implements PointService {

	private static final Logger logger = LoggerFactory.getLogger(PointServiceImpl.class);
	
	@Autowired
	private PointMapper pointMapper;
	
	@Override
	public List<PointSummaryDTO> selectPointSummary(PointSummaryDTO params) throws Exception {
		return pointMapper.selectPointSummary(params);
	}

	@Override
	public List<PointEventDTO> selectPointEvent(PointEventDTO params) throws Exception {
		return pointMapper.selectPointEvent(params);
	}


	@Override
	public void usePoint(PointEventDTO params) throws Exception {
		
		long userIdx = params.getUserIdx();
		long usePoint = params.getPoint(); // 음수
		
		
		// 적립금이 충분한지 체크
		validUsablePoint(userIdx, usePoint);
		
		
		// user_idx 기준으로 point_event 테이블 row 추가
		int insertUsePointEvent = pointMapper.insertUsePointEvent(params);
		logger.info("insertUsePointEvent insert result : " + insertUsePointEvent);
		

		// lastIndex를 origin_event_detail_idx 값으로 저장해야 하기 때문에, 방금 저장한 pointEvent의 idx 값 조회
		PointEventDTO eventDto = new PointEventDTO();
		long lastPointEventIdx = loadLastPointEventIdx(eventDto, userIdx);
		logger.info("lastPointEventIdx : " + lastPointEventIdx);
	
		
		/**
		 * group by 한 결과를 바탕으로 PointEventDetail 테이블에 저장함
		 * sample : [
		 * 		{SUM_VALUE=10, ORIGIN_EVENT_DETAIL_IDX=1}
		 * 		, {SUM_VALUE=30, ORIGIN_EVENT_DETAIL_IDX=5}
		 * 		, ... , 
		 *	]
		 */
		List<Map<String, Object>> groupBySumDetail = pointMapper.selectGroupBySumDetail(eventDto);
		logger.info("groupBySumDetail :  " + groupBySumDetail);
		
		
		// point_event_detail, user_idx와 선 적립금 먼저 감면 하도록 계산 및 row 추가 
		calUsePoint(params, groupBySumDetail, userIdx, usePoint, lastPointEventIdx);
		
		
		// point_event_detail의 group by 결과를 summary 테이블 업데이트
		refreshPointSummary(userIdx);
		
		
	}


	@Override
	public void addPoint(PointEventDTO params) throws Exception {
		
		long userIdx = params.getUserIdx();
		long addPoint = params.getPoint(); // 양수
		
		// user_idx 기준으로 point_event 테이블 row 추가
		int insertUsePointEvent = pointMapper.insertUsePointEvent(params);
		logger.info("insertUsePointEvent insert result : " + insertUsePointEvent);
		
		
		// lastIndex를 origin_event_detail_idx 값으로 저장해야 하기 때문에, 방금 저장한 pointEvent의 idx 값 조회
		PointEventDTO eventDto = new PointEventDTO();
		long lastPointEventIdx = loadLastPointEventIdx(eventDto, userIdx);
		logger.info("lastPointEventIdx : " + lastPointEventIdx);
		
		
		// add point point_event_detail 테이블에 저장
		calAddPoint(params, userIdx, addPoint, lastPointEventIdx);
		
		
		// point_event_detail의 group by 결과를 summary 테이블 업데이트
		refreshPointSummary(userIdx);
	}

	
	
	private void calAddPoint(PointEventDTO params, long userIdx, long addPoint, long lastPointEventIdx) {

		try {
			PointEventDetailDTO eventDetailDto = new PointEventDetailDTO();
			eventDetailDto.setUserIdx(userIdx);
			eventDetailDto.setEventIdx(lastPointEventIdx);
			eventDetailDto.setPoint(addPoint);
			eventDetailDto.setStatus("적립");
			eventDetailDto.setCreateDttm( params.getCreateDttm());
			eventDetailDto.setUpdateDttm( params.getUpdateDttm());
			
			logger.info("eventDetailDto : " + eventDetailDto);
			
			pointMapper.insertPointEventDetail(eventDetailDto);
			pointMapper.syncOriginEventIdx(eventDetailDto);
		} catch (Exception e) {
			throw new IllegalStateException("Fail to calAddPoint, error.msg => " + e.getMessage(), e);
		}
	}

	
	
	private void calUsePoint(PointEventDTO params, List<Map<String, Object>> groupBySumDetail, long userIdx, long usePoint, long lastPointEventIdx) {
		
		long calPoint = Math.abs(usePoint); // 절대값으로 변경
		for(Map<String, Object> groupByInfo : groupBySumDetail) {
		
			try {
				if(calPoint <= 0) {
					break;
				}
				
				long sumValue = Long.parseLong(groupByInfo.get("SUM_VALUE").toString());
				long calSumValue = (calPoint >= sumValue) ? sumValue : calPoint ;
				logger.info("calPoint : " + calPoint + ", sumValue : " + sumValue + ", calSumValue : "+ calSumValue);
				
				long originEventDetailIdx = Long.parseLong(groupByInfo.get("ORIGIN_EVENT_DETAIL_IDX").toString());
				
				// detail 저장
				PointEventDetailDTO eventDetailDto = new PointEventDetailDTO();
				eventDetailDto.setUserIdx(userIdx);
				eventDetailDto.setEventIdx(lastPointEventIdx);
				eventDetailDto.setPoint(-calSumValue);
				eventDetailDto.setOriginEventDetailIdx(originEventDetailIdx);
				eventDetailDto.setStatus("사용");
				eventDetailDto.setCreateDttm( params.getCreateDttm());
				eventDetailDto.setUpdateDttm( params.getUpdateDttm());
				
				logger.info("eventDetailDto : " + eventDetailDto);
				
				pointMapper.insertPointEventDetail(eventDetailDto);
				calPoint -= calSumValue;
				
			} catch (Exception e) {
				throw new IllegalStateException("Fail to calUsePoint, error.msg => " + e.getMessage(), e);
			}
		}		
	}

	/**
	 * lastIndex를 origin_event_detail_idx 값으로 저장해야 하기 때문에, 방금 저장한 pointEvent의 idx 값 조회
	 * @param eventDto
	 * @param userIdx
	 * @return
	 */
	private long loadLastPointEventIdx(PointEventDTO eventDto, long userIdx) {
		
		long lastPointEventIdx = 0;
		try {
			eventDto.setUserIdx(userIdx);
			lastPointEventIdx = pointMapper.selectLastPointEvent(eventDto).getIdx();
		} catch (Exception e) {
			throw new IllegalStateException("Fail to loadLasPointEventIdx, error.msg => " + e.getMessage(), e);
		}
		
		return lastPointEventIdx;
	}

	/**
	 * 가용 가능한 범위 내에 있는 포인트 사용 요청 확인
	 * @param userIdx
	 * @param usePoint
	 * @throws Exception
	 */
	private void validUsablePoint(long userIdx, long usePoint) throws Exception {
		
		PointSummaryDTO summaryDto = new PointSummaryDTO();
		summaryDto.setUserIdx(userIdx);
		long nowPoint = pointMapper.targetNowPoint(summaryDto);
		
		if( nowPoint < Math.abs(usePoint)) {
			throw new IllegalStateException("usePoint is bigger than nowPoint, usePoint : "+ usePoint + ", nowPoint : " + nowPoint);
		}
		
	}

	/**
	 * PointEventDTO 의 idx 에 맞춰서 삭제
	 * 1. point_event 삭제
	 * 2. point_event_detail 삭제 
	 */
	@Override
	public void deleteUsePointHist(PointEventDTO params) throws Exception {
		
		// 삭제
		int pointEventDeleteResult = pointMapper.deleteUseHistPointEvent(params);
		int pointEventDetailDeleteResult = pointMapper.deleteIseHistPointEventDetail(params);
		logger.info("delete result, point_event : " + pointEventDeleteResult + ", point_event_detail : " + pointEventDetailDeleteResult);
		
		// point_event_detail의 group by 결과를 summary 테이블 업데이트
		refreshPointSummary(params.getUserIdx());
	}
	
	
	/**
	 * 변경 사항이 발생 한 다음 현재 누적 적립금을 저장하는 point_summary 테이블을 갱신
	 * @param userIdx
	 */
	private void refreshPointSummary(long userIdx) {
		try {
			PointSummaryDTO summaryDTO = new PointSummaryDTO();
			summaryDTO.setUserIdx(userIdx);
			pointMapper.refreshPointSummary(summaryDTO);
		} catch (Exception e) {
			throw new IllegalStateException("Fail to refresh point_summary, error.msg => " + e.getMessage(), e);
		}
	}

}
