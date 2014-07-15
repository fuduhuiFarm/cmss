/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shop.cms.common.constant;

/**
 * @author 目录属性常量
 * 
 */
public class FolderConstant {

	public static enum Status {
		hidden, display
	};

	/**
	 * @author Herbert
	 * 
	 */
	public static enum Rank {
		/**
		 * 任何人
		 */
		everyone, /**
		 * 登录用户
		 */
		login, /**
		 * vip用户
		 */
		vip, /**
		 * 管理员
		 */
		admin
	};

	/**
	 * @author Herbert
	 * 
	 */
	public static enum Owner {
		/**
		 * 系统创建
		 */
		system, /**
		 * 应用创建
		 */
		app
	};

	public static enum Type {
		/**
		 * 相册
		 */
		photo,
		/**
		 * 列表
		 */
		article, /**
		 * 目录
		 */
		folder
	};
}
