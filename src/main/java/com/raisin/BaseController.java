package com.raisin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

@Controller
public abstract class BaseController {

	public HttpSession getSession(HttpServletRequest req) {
		return req.getSession();
	}

	public void setSessionValue(HttpServletRequest req, String name, Object value) {
		req.getSession().setAttribute(name, value);
	}

	public Object getSessionValue(HttpServletRequest req, String searchKey) {
		return req.getSession().getAttribute(searchKey);
	}

	public void disConnSession(HttpServletRequest req) {
		req.getSession().invalidate();
	}

	public boolean chkLoginUserSession(HttpServletRequest req) {
		if (req.getSession().getAttribute("userInfo") == null) {
			return false;
		} else {
			return true;
		}
	}

}
