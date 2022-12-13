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
import com.point.domain.PointEventDTO;
import com.point.domain.PointSummaryDTO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PointTest {

	
	private static final Logger logger = LoggerFactory.getLogger(PointTest.class);
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	

	@Test
	@Order(0)
	public void getPointSummaryAll() throws Exception {
		String url = "/point/point-summary";
		PointSummaryDTO dto = new PointSummaryDTO();
		commonGetPointSummary(url, dto);
	}
	

	@Test
	@Order(1)
	public void getPointSummary() throws Exception {
		String url = "/point/point-summary/userIdx=1";
		PointSummaryDTO dto = new PointSummaryDTO();
		commonGetPointSummary(url, dto);
	}
	
	
	@Test
	@Order(2)
	public void getPointEventAll() throws Exception {
		String url = "/point/point-event";
		PointEventDTO dto = new PointEventDTO();
		commonGetPointEvent(url, dto);
	}
	
	@Test
	@Order(3)
	public void getPointEvent() throws Exception {
		String url = "/point/point-event/userIdx=1";
		PointEventDTO dto = new PointEventDTO();
		commonGetPointEvent(url, dto);		
	}
	
	
	@Test
	@Order(4)
	public void postUsePointEvent() throws Exception {
		String url = "/point/point-event";
		PointEventDTO dto = new PointEventDTO();
		dto.setUserIdx(1l);
		dto.setStatus("use");
		dto.setPoint(-400l);
		
		commonPostPointEvent(url, dto);
		getPointSummaryAll();
	}
	
	
	@Test
	@Order(5)
	public void postAddPointEvent() throws Exception {
		String url = "/point/point-event";
		PointEventDTO dto = new PointEventDTO();
		dto.setUserIdx(1l);
		dto.setStatus("save");
		dto.setPoint(400l);
		
		commonPostPointEvent(url, dto);
		getPointSummaryAll();
	}
	
	
	
	
	@Test
	@Order(6)
	public void deletePointEvent() throws Exception {
		String url = "/point/point-event";
		PointEventDTO dto = new PointEventDTO();
		dto.setUserIdx(1l);
		dto.setIdx(7l);
		
		commonDeletePointEvent(url, dto);
		getPointSummaryAll();
	}
	
	

	@Test
	@Order(5)
	private void commonGetPointSummary(String url, PointSummaryDTO dto) {
		try {
			String content = objectMapper.writeValueAsString(dto);
			logger.info("## Start test url : " + url + ", content : " + content);
			
			mockMvc.perform(MockMvcRequestBuilders.get(url)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> {
					MockHttpServletResponse response = result.getResponse();
					logger.info(response.getContentAsString());
				});
		} catch (Exception e) {
			logger.error("Fail to doGetMethod test codes, error.msg => " + e.getMessage(), e);
		}
	}
	
	
	private void commonGetPointEvent(String url, PointEventDTO dto) {
		try {
			String content = objectMapper.writeValueAsString(dto);
			logger.info("## Start test url : " + url + ", content : " + content);
			
			mockMvc.perform(MockMvcRequestBuilders.get(url)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> {
					MockHttpServletResponse response = result.getResponse();
					logger.info(response.getContentAsString());
				});
		} catch (Exception e) {
			logger.error("Fail to doGetMethod test codes, error.msg => " + e.getMessage(), e);
		}
	}
	
	
	private void commonPostPointEvent(String url, PointEventDTO dto) {
		try {
			String content = objectMapper.writeValueAsString(dto);
			logger.info("## Start test url : " + url + ", content : " + content);
			
			mockMvc.perform(MockMvcRequestBuilders.post(url)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> {
					MockHttpServletResponse response = result.getResponse();
					logger.info(response.getContentAsString());
				});
		} catch (Exception e) {
			logger.error("Fail to doGetMethod test codes, error.msg => " + e.getMessage(), e);
		}
	}
	
	

	private void commonDeletePointEvent(String url, PointEventDTO dto) {
		
		try {
			String content = objectMapper.writeValueAsString(dto);
			logger.info("## Start test url : " + url + ", content : " + content);
			
			mockMvc.perform(MockMvcRequestBuilders.delete(url)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> {
					MockHttpServletResponse response = result.getResponse();
					logger.info(response.getContentAsString());
				});
		} catch (Exception e) {
			logger.error("Fail to doGetMethod test codes, error.msg => " + e.getMessage(), e);
		}
		
	}
}
