package com.raisin.controller;

import java.util.Base64;
import java.util.Base64.Encoder;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.raisin.BaseController;
import com.raisin.TestEncode;
import com.raisin.entity.Account;
import com.raisin.service.AccountService;
import com.raisin.utils.DecryptUtil;
import com.raisin.utils.EncryptUtil;
import com.raisin.utils.MessageUtils;
import com.raisin.vo.LoginVo;

/**
 * @author raisin
 * ログイン画面に遷移またはログイン画面から処理を行い、別途の画面に遷移する
 * リクエスト情報を管理するコントローラー
 */
@Controller
public class LoginController extends BaseController {

	static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	AccountService accountService;

	/**
	 * セッションチェックを行い、ログイン画面及びメイン画面に遷移する。（GET）
	 * @param req リクエスト情報
	 * @return Stringリダイレクト及び遷移する画面
	 */
	@RequestMapping( value = "/login", method = RequestMethod.GET)
	public String getLoginActionbyGet(HttpServletRequest req, LoginVo vo) {
		logger.info("ログイン画面！！！！！！");
		if (chkLoginUserSession(req)) {
			return "redirect:board";
		} else {
			return "login/login";
		}

	}

	/**
	 * セッションチェックを行い、ログイン画面及びメイン画面に遷移する。（POST）
	 * @param req リクエスト情報
	 * @return Stringリダイレクト及び遷移する画面
	 */
	@RequestMapping( value = "/login", method = RequestMethod.POST)
	public String getLoginActionbyPost(HttpServletRequest req, RedirectAttributes redirectAttributes, ModelMap map) {
		if (chkLoginUserSession(req)) {
			return "redirect:board";
		} else {
			if (req.getParameter("processFlg").equals("1")) {
				map.addAttribute("userid", req.getParameter("userid"));
			}
			return "login/login";
		}

	}

	/**
	 * 入力されたログイン情報をDBのデータと比べ、存在する場合はセッションに設定、存在しない場合はログイン画面に遷移する。
	 * @param req リクエスト情報
	 * @param redirectAttributes リダイレクトする際に渡したい値を設定する情報
	 *                            ※モデルビューに設定するとリダイレクトする際に値を隠すため
	 * @param vo ログイン情報（ログインID、パスワード）
	 * @return Stringリダイレクト及び遷移する画面
	 */
	@RequestMapping( value = "/chkUserInfo", method = RequestMethod.POST)
	public String chkLoginAction(HttpServletRequest req, RedirectAttributes redirectAttributes, LoginVo vo) {

		Account checkedUserInfo = accountService.getUserInfo(vo);

		if (checkedUserInfo != null) {
			//ユーザー情報をセッションに設定する。
			setSessionValue(req, "userInfo", checkedUserInfo);

			return "redirect:board";
		} else {
			if (vo.getUserid().equals("")) {
				redirectAttributes.addFlashAttribute("errMsg", MessageUtils.getMessage("login.errMsg.001"));
			} else {
				redirectAttributes.addFlashAttribute("userid", vo.getUserid());
				redirectAttributes.addFlashAttribute("errMsg", MessageUtils.getMessage("login.errMsg.002"));
			}
			return "redirect:login";
		}
	}

	/**
	 * セッション内のログイン情報を削除し、ログイン画面に遷移する。
	 * @param req リクエスト情報
	 * @return Stringリダイレクト及び遷移する画面
	 */
	@RequestMapping( value = "/logout", method = RequestMethod.GET)
	public String logoutAction(HttpServletRequest req) {

		disConnSession(req);

		TestEncode test = new TestEncode();
		test.test(null);


		return "redirect:login";
	}



	/**
	 * セッションチェックを行い、ログイン画面及びメイン画面に遷移する。（GET）
	 * @param req リクエスト情報
	 * @return Stringリダイレクト及び遷移する画面
	 */
	@RequestMapping( value = "/test", method = RequestMethod.GET)
	public String endcryptTest(HttpServletRequest req, LoginVo vo) {
		String testStr = "raisin";

		byte[] targetBytes = testStr.getBytes();

		Encoder encoder = Base64.getEncoder();
		byte[] encodedBytes = encoder.encode(targetBytes);
		String publicKey = MessageUtils.getMessage("encode.publicKey");
		String privateKey = MessageUtils.getMessage("encode.privateKey");


		System.out.println(testStr);
		String enTestStr = EncryptUtil.encode(testStr, publicKey);
		System.out.println(enTestStr);
		String enTestStr2 = EncryptUtil.encode(testStr, publicKey);
		System.out.println(enTestStr2);
		String deTestStr = DecryptUtil.decode(enTestStr, privateKey);
		System.out.println(deTestStr);
		String deTestStr2 = DecryptUtil.decode(enTestStr2, privateKey);
		System.out.println(deTestStr2);
		return "login/login";

	}


}
