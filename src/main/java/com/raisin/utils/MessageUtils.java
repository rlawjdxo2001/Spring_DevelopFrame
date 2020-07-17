package com.raisin.utils;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtils {

	private static MessageSourceAccessor msAcc = null;

	public void setMessageSourceAccessor(MessageSourceAccessor msAcc) {
		MessageUtils.msAcc = msAcc;
	}

	public static String getMessage(String key) {
		return msAcc.getMessage(key, Locale.getDefault());
	}

	public static String getMessage(String key, Object[] objs) {
		return msAcc.getMessage(key, objs, Locale.getDefault());
	}

}
