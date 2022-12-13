package com.point.domain;

public class PointEventDTO {

	private Long idx;
	private Long userIdx;
	private String status;
	private Long point;
	private String options;
	private String createDttm;
	private String updateDttm;
	private String expirationDttm;
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
	public String getExpirationDttm() {
		return expirationDttm;
	}
	public void setExpirationDttm(String expirationDttm) {
		this.expirationDttm = expirationDttm;
	}
	@Override
	public String toString() {
		return "PointEventDTO [idx=" + idx + ", userIdx=" + userIdx + ", status=" + status + ", point=" + point
				+ ", options=" + options + ", createDttm=" + createDttm + ", updateDttm=" + updateDttm
				+ ", expirationDttm=" + expirationDttm + "]";
	}
	
	
	
	
}
