package com.ntsphere.ecn.basicweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ntsphere.ecn.basicweb.config.ServerProperty;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {
	
	@Autowired private ServerProperty serverProperty;

	
	
	
	
	@RequestMapping(value= {"", "/"}, method=RequestMethod.GET)
	public String index() {
		return "/index";
	}
}
