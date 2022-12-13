package com.point.contoller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.point.domain.UserListDTO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {


	private static final Logger logger = LoggerFactory.getLogger(UserTest.class);
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	
	@Test
	@Order(1)
	public void getTest() throws Exception {
		String url = "/user/user-list";
		
		UserListDTO dto = new UserListDTO();
		
		String content = objectMapper.writeValueAsString(dto);
		logger.info("post content : " + content);
		
		mockMvc.perform(MockMvcRequestBuilders.get(url)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(result -> {
				MockHttpServletResponse response = result.getResponse();
				logger.info(response.getContentAsString());
			});
	}
}
