package com.point.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.point.domain.UserListDTO;
import com.point.mapper.UserMapper;
import com.point.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<UserListDTO> selectUserList(UserListDTO params) throws Exception {
		return userMapper.selectUserList(params);
	}
	
}
