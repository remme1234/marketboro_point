package com.point.service;

import java.util.List;

import com.point.domain.PointEventDTO;
import com.point.domain.PointSummaryDTO;

public interface PointService {

	public List<PointSummaryDTO> selectPointSummary(PointSummaryDTO params) throws Exception ;

	public List<PointEventDTO> selectPointEvent(PointEventDTO params) throws Exception;

	public void usePoint(PointEventDTO params) throws Exception;

	public void deleteUsePointHist(PointEventDTO params) throws Exception;

	public void addPoint(PointEventDTO params) throws Exception;

}
