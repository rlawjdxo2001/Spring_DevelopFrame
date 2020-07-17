package com.raisin.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author raisin
 * ログイン画面で使われるVO
 */
public class LoginVo {

	@Getter
	@Setter
	private String userid;

	@Getter
	@Setter
	private String password;

}
