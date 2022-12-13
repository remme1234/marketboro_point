package com.point.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.point.domain.UserListDTO;


@Mapper
public interface UserMapper {

	public List<UserListDTO> selectUserList(UserListDTO params) throws Exception;

}
