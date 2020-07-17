package com.raisin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.raisin.BaseController;
import com.raisin.service.AccountService;
import com.raisin.utils.MessageUtils;
import com.raisin.vo.RegistVo;

@Controller
public class RegisterController extends BaseController {

	@Autowired
	AccountService accountService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String goToRegisterFormByGet(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		return "login/register";
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String goToRegisterFormByPost(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		return "login/register";
	}

	@RequestMapping(value = "/registeUser", method = RequestMethod.POST)
	public String chkRegistUser(RedirectAttributes redirectAttributes, RegistVo vo) {

		if (!chkRegistForm(vo, redirectAttributes)) {

			if (accountService.registUser(vo) == 0) {
				redirectAttributes.addFlashAttribute("userIdErrMsg", "既に存在するIDです。");
				redirectAttributes.addFlashAttribute("processFlg", "0");
			} else {
				redirectAttributes.addFlashAttribute("processFlg", "1");
			}
		} else {
			redirectAttributes.addFlashAttribute("processFlg", "0");
		}

		return "redirect:register";

	}


	/**
	 * 画面上の値に対して基本的なチェック処理を行う。
	 * @param req リクエスト情報
	 * @param redirectAttributes 画面上に表示する値情報
	 * @return boolean true:エラーあり、false:エラーなし
	 */
	private boolean chkRegistForm(RegistVo vo, RedirectAttributes redirectAttributes) {
		boolean chkFlg = false;

		redirectAttributes.addFlashAttribute("userid", vo.getUserid());
		if (vo.getUserid().equals("")) {
			redirectAttributes.addFlashAttribute("userIdErrMsg", MessageUtils.getMessage("register.errMsg.001"));
			chkFlg = true;
		}
		redirectAttributes.addFlashAttribute("username", vo.getUsername());
		if (vo.getUsername().equals("")) {
			redirectAttributes.addFlashAttribute("usernameErrMsg", MessageUtils.getMessage("register.errMsg.002"));
			chkFlg = true;
		}
		redirectAttributes.addFlashAttribute("password", vo.getPassword());
		if (vo.getPassword().equals("")) {
			redirectAttributes.addFlashAttribute("passwordErrMsg", MessageUtils.getMessage("register.errMsg.003"));
			chkFlg = true;
		}
		redirectAttributes.addFlashAttribute("passwordChk", vo.getPasswordChk());
		if (vo.getPasswordChk().equals("")) {
			redirectAttributes.addFlashAttribute("passwordChkErrMsg", MessageUtils.getMessage("register.errMsg.004"));
			chkFlg = true;
		}
		if (!vo.getPassword().equals(vo.getPasswordChk())) {
			redirectAttributes.addFlashAttribute("passwordChkErrMsg", MessageUtils.getMessage("register.errMsg.005"));
			chkFlg = true;
		}

		return chkFlg;
	}

}
