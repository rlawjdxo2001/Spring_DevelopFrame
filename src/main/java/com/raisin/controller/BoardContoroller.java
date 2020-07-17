package com.raisin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.raisin.BaseController;

@Controller
public class BoardContoroller extends BaseController {


	@RequestMapping( value = "/board", method = RequestMethod.GET)
	public String getLoginAction() {
		return "board/boardList";
	}
}
