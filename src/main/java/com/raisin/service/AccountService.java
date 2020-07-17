package com.raisin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raisin.entity.Account;
import com.raisin.repository.AccountRepository;
import com.raisin.utils.DecryptUtil;
import com.raisin.utils.EncryptUtil;
import com.raisin.utils.MessageUtils;
import com.raisin.vo.LoginVo;
import com.raisin.vo.RegistVo;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repo;

    public Account getUserInfo(LoginVo vo) {

    	Account searchCond = new Account();

    	searchCond.setUserId(vo.getUserid());

    	searchCond.setPassword(EncryptUtil.encode(vo.getPassword(), MessageUtils.getMessage("encode.publicKey")));

        List<Account> userInfos = repo.findByUserId(searchCond.getUserId());



        if (userInfos.size() == 1
        		&& vo.getPassword().equals(DecryptUtil.decode(userInfos.get(0).getPassword(), MessageUtils.getMessage("encode.privateKey")))) {
            return userInfos.get(0);
        } else {
            return null;
        }
    }

    public int registUser(RegistVo vo) {

    	Account insertInfo = new Account();

		Date currDate = new Date();


		insertInfo.setUserId(vo.getUserid());
		insertInfo.setUsername(vo.getUsername());
		insertInfo.setPassword(EncryptUtil.encode(vo.getPassword(), MessageUtils.getMessage("encode.publicKey")));
		insertInfo.setCreateDt(currDate);
		insertInfo.setCreateUser("system");
		insertInfo.setModiDt(currDate);
		insertInfo.setModiUser("system");

		if (repo.findByUserId(insertInfo.getUserId()).size() == 0) {
    		repo.save(insertInfo);
    		return 1;
		} else {
			return 0;
		}
    }


}
