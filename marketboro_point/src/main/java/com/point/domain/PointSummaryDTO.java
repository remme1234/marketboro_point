package com.point.domain;

public class PointSummaryDTO {

	
	private Long idx;
	private Long userIdx;
	private Long nowPoint;
	private String options;
	private String createDttm;
	private String updateDttm;
	public Long getIdx() {
		return idx;
	}
	public void setIdx(Long idx) {
		this.idx = idx;
	}
	public Long getUserIdx() {
		return userIdx;
	}
	public void setUserIdx(Long userIdx) {
		this.userIdx = userIdx;
	}
	public Long getNowPoint() {
		return nowPoint;
	}
	public void setNowPoint(Long nowPoint) {
		this.nowPoint = nowPoint;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getCreateDttm() {
		return createDttm;
	}
	public void setCreateDttm(String createDttm) {
		this.createDttm = createDttm;
	}
	public String getUpdateDttm() {
		return updateDttm;
	}
	public void setUpdateDttm(String updateDttm) {
		this.updateDttm = updateDttm;
	}
	@Override
	public String toString() {
		return "PointSummaryDTO [idx=" + idx + ", userIdx=" + userIdx + ", nowPoint=" + nowPoint + ", options="
				+ options + ", createDttm=" + createDttm + ", updateDttm=" + updateDttm + "]";
	}
	
	
	
	
}
