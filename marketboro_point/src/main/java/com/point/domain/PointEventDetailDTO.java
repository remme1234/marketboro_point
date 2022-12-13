package com.point.domain;

public class PointEventDetailDTO {

	private Long idx;
	private Long userIdx;
	private Long eventIdx;
	private Long originEventDetailIdx;
	private String status;
	private Long point;
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
	public Long getEventIdx() {
		return eventIdx;
	}
	public void setEventIdx(Long eventIdx) {
		this.eventIdx = eventIdx;
	}
	public Long getOriginEventDetailIdx() {
		return originEventDetailIdx;
	}
	public void setOriginEventDetailIdx(Long originEventDetailIdx) {
		this.originEventDetailIdx = originEventDetailIdx;
	}
	public void setUserIdx(Long userIdx) {
		this.userIdx = userIdx;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getPoint() {
		return point;
	}
	public void setPoint(Long point) {
		this.point = point;
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
		return "PointEventDetailDTO [idx=" + idx + ", userIdx=" + userIdx + ", eventIdx=" + eventIdx
				+ ", originEventDetailIdx=" + originEventDetailIdx + ", status=" + status + ", point=" + point
				+ ", options=" + options + ", createDttm=" + createDttm + ", updateDttm=" + updateDttm + "]";
	}

	
	
}
