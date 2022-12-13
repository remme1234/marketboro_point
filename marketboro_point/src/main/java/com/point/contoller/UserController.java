package com.point.contoller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.point.domain.UserListDTO;
import com.point.service.UserService;

@RestController
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping(value="/user/user-list")
	public JsonArray selectUserList() throws Exception {
		
		logger.info("Start selectUserList !! ");
		
		JsonArray selectResultArr = new JsonArray();
		try {
			List<UserListDTO> userList = userService.selectUserList(new UserListDTO());
			logger.info("userList.size() : " + userList.size()  + ", sample idx[0] : " + userList.get(0));
			
			selectResultArr = new Gson().toJsonTree(userList).getAsJsonArray();
			
		} catch (Exception e) {
			throw new Exception("Fail to select userList, error.msg => " + e.getMessage(), e);
		}
		
		return selectResultArr;
	}
	
}
