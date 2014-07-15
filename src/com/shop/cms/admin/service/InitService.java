/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shop.cms.admin.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class InitService {

	@PostConstruct
	public void init() {
	}


	public static void main(String[] args) {
		InitService init = new InitService();
		init.init();
	}
}
