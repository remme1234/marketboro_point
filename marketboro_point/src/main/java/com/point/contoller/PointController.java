package com.point.contoller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.point.domain.PointEventDTO;
import com.point.domain.PointSummaryDTO;
import com.point.service.PointService;
import com.point.util.ApiDate;

@RestController
public class PointController {

	private static final Logger logger = LoggerFactory.getLogger(PointController.class);
	
	private final String dttmFormat = "yyyy-MM-dd HH:mm:ss.SSS";
	
	@Autowired
	private PointService pointService;

	/**
	 * point summary 조회
	 * 	1. 전체 조회
	 * 	2. user_idx 기준으로 조회
	 * TODO : 유효기간 처리
	 * @param userIdx
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value={"/point/point-summary", "/point/point-summary/userIdx={userIdx}"})
	public JsonArray selectPointSummary(@PathVariable(value="userIdx", required = false) Long userIdx
			,@ModelAttribute("params") PointSummaryDTO params) throws Exception {
		
		JsonArray summaryDataArr = new JsonArray();
		
		try {
			if(userIdx != null) {
				params.setUserIdx(userIdx);
			}
			logger.info("select pointSummaryList param : " + params.toString());
			
		
			List<PointSummaryDTO> summaryDataList = pointService.selectPointSummary(params);
			summaryDataArr = new Gson().toJsonTree(summaryDataList).getAsJsonArray();
			
			if(!summaryDataArr.isEmpty()) {
				logger.info("summaryDataArr.size() : " + summaryDataArr.size()  + ", sample idx[0] : " + summaryDataArr.get(0));
			}
			
		} catch (Exception e) {
			throw new Exception("Fail to select point_summary data, error.msg => " + e.getMessage(), e);
		}
		
		return summaryDataArr;
		
	}
	
	
	
	/**
	 * point event 조회
	 * 	1. 전체 조회
	 * 	2. user_idx 기준으로 조회
	 * @param userIdx
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value={"/point/point-event", "/point/point-event/userIdx={userIdx}"})
	public JsonArray selectPointEvent(@PathVariable(value="userIdx", required = false) Long userIdx
			,@ModelAttribute("params") PointEventDTO params) throws Exception {
	
		JsonArray pointEventArr = new JsonArray();
		
		try {
			if(userIdx != null) {
				params.setUserIdx(userIdx);
			}
			logger.info("select pointSummaryList param : " + params.toString());
			
			List<PointEventDTO> resultList = pointService.selectPointEvent(params);
			pointEventArr = new Gson().toJsonTree(resultList).getAsJsonArray();
			
			if(!pointEventArr.isEmpty()) {
				logger.info("pointEventArr.size() : " + pointEventArr.size()  + ", sample idx[0] : " + pointEventArr.get(0));
			}
			
		} catch (Exception e) {
			throw new Exception("Fail to select point_event data, error.msg => " + e.getMessage(), e);
		}
		
		return pointEventArr;
	}
	
	
	
	/**
	 * 
	 * 적립금 적립/사용 기능 
	 * 
	 * 1. user_idx 기준으로 point_event 테이블 row 추가
	 * 2. point_event_detail, user_idx와 선 적립금 먼저 감면 하도록 계산 및 row 추가
	 * 3. point_event_detail의 group by 결과를 summary 테이블 업데이트
	 * 4. 변경된 summary 데이터 반환
	 * @param params
	 *  sample : {"userIdx": 1 , "status":"use" , "point":-30}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/point/point-event"}, method={ RequestMethod.POST })
	public JsonArray addPointEvent(@RequestBody PointEventDTO params) throws Exception {
		
		JsonArray pointEventArr = new JsonArray();
		
		try {
			logger.info("RequestBody param !! " + params);
			
			// set nowTimes
			String currentTime = ApiDate.currentDate().format(dttmFormat);
			params.setCreateDttm(currentTime);
			params.setUpdateDttm(currentTime);
			logger.info("params : " + params);

			
			String status = params.getStatus();
			
			switch (status) {
				case "use":
					params.setStatus("사용");
					pointService.usePoint(params);
					break;
				case "save":
					params.setStatus("적립");
					params.setExpirationDttm( new ApiDate(currentTime, dttmFormat).addDay(365).format(dttmFormat) );
					pointService.addPoint(params);
					break;

				default:
					break;
			}
			
			pointEventArr = selectPointEvent(params.getUserIdx(), params);
			logger.info("pointEventArr : " + pointEventArr);
			
		} catch (Exception e) {
			throw new Exception("Fail to update point info, error.msg => " + e.getMessage(), e);
		}
		
		return pointEventArr;
	}
	
	
	/**
	 * 
	 * 적립금 사용 취소 기능 구현
	 *  
	 * 1. point_event의 idx 기준으로 point_event, point_event_detailt 삭제
	 * 2. group by 결과를 summary 테이블에 저장
	 * 
	 * @param params
	 * 	sample : {"userIdx":1, "idx":7}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/point/point-event"}, method={ RequestMethod.DELETE })
	public JsonArray deleteUsePointHist(@RequestBody PointEventDTO params) throws Exception {
		
		JsonArray pointEventArr = new JsonArray();
		
		try {
			logger.info("deleteUsePointHist param : " + params);
			pointService.deleteUsePointHist(params);
			
		} catch (Exception e) {
			throw new Exception("Fail to delete use point hist, error.msg => " + e.getMessage(), e);
		}
		
		
		return pointEventArr;
	}
}
