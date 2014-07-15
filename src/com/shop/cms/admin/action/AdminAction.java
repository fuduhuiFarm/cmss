/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shop.cms.admin.action;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shop.cms.admin.model.ArticleVo;
import com.shop.cms.common.constant.ArticleConstant;
import com.shop.cms.core.exception.FolderNotFoundException;

/**
 * @author lqq
 * @author 进入网站后台首页
 * 
 */

@Controller
@RequestMapping("/admin")
public class AdminAction extends AdminBaseAction {

	@RequestMapping(value = "/index.htm", method = RequestMethod.GET)
	public String login(ModelMap modelMap) throws FolderNotFoundException {
		modelMap.put("articleCount", 0);
		modelMap.put("downloadCount", 0);
		modelMap.put("userCount", 0);
		modelMap.put("folderAll", folderService.getAllFolderList(0, null));
		List<ArticleVo> articleList = articleService.getArticleListByStatus(0,
				0, 0, 0, ArticleConstant.Status.display, 0, 10);
		modelMap.put("articleList", articleList);
		return "system/index";
	}

}
