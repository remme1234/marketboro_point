package com.point.service;

import java.util.List;

import com.point.domain.UserListDTO;

public interface UserService {

	public List<UserListDTO> selectUserList(UserListDTO params) throws Exception;
	
}
