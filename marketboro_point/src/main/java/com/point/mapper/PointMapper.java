package com.point.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.point.domain.PointEventDTO;
import com.point.domain.PointEventDetailDTO;
import com.point.domain.PointSummaryDTO;


@Mapper
public interface PointMapper {

	public List<PointSummaryDTO> selectPointSummary(PointSummaryDTO params) throws Exception;

	public List<PointEventDTO> selectPointEvent(PointEventDTO params) throws Exception;

	public int insertUsePointEvent(PointEventDTO params) throws Exception;

	public PointEventDTO selectLastPointEvent(PointEventDTO eventDto)throws Exception;

	public List<Map<String, Object>> selectGroupBySumDetail(PointEventDTO eventDto) throws Exception;

	public void insertPointEventDetail(PointEventDetailDTO eventDetailDto) throws Exception;

	public void refreshPointSummary(PointSummaryDTO summaryDTO) throws Exception;

	public int deleteUseHistPointEvent(PointEventDTO params) throws Exception;

	public int deleteIseHistPointEventDetail(PointEventDTO params) throws Exception;

	public void syncOriginEventIdx(PointEventDetailDTO eventDetailDto) throws Exception;

	public long targetNowPoint(PointSummaryDTO summaryDto) throws Exception;
	
	
}
