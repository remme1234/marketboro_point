package com.point.domain;

public class UserListDTO {

	
	private Long idx;
	private Long groupIdx;
	private String userId;
	private String passward;
	private String userName;
	private String email;
	private String address;
	private String phone;
	private String createDttm;
	private String updateDttm;
	public Long getIdx() {
		return idx;
	}
	public void setIdx(Long idx) {
		this.idx = idx;
	}
	public Long getGroupIdx() {
		return groupIdx;
	}
	public void setGroupIdx(Long groupIdx) {
		this.groupIdx = groupIdx;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassward() {
		return passward;
	}
	public void setPassward(String passward) {
		this.passward = passward;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
		return "UserListDTO [idx=" + idx + ", groupIdx=" + groupIdx + ", userId=" + userId + ", passward=" + passward
				+ ", userName=" + userName + ", email=" + email + ", address=" + address + ", phone=" + phone
				+ ", createDttm=" + createDttm + ", updateDttm=" + updateDttm + "]";
	}
	
}
