package com.point.contoller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebContoller {

	@GetMapping(value="/")
	public String test(HttpSession session, HttpServletRequest request) {
		return "index";
	}
}
