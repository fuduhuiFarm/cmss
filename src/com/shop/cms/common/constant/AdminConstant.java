/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shop.cms.common.constant;

/**
 * 管理员表的常量
 * 
 * @author Herbert
 * 
 */
public class AdminConstant {

	public static enum Status {
		/**
		 * 初始化，需要修改密码
		 */
		init, /**
		 * 正常，可以使用
		 */
		normal,
		/**
		 * 冻结，无法使用
		 */
		freeze
	};
}
