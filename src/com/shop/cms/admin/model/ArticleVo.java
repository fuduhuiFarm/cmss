/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shop.cms.admin.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Herbert
 * 
 */
public class ArticleVo extends Article {

	private Admin admin;

	private FolderVo folder;

	private List<AttachmentVo> attachmentList = new ArrayList<AttachmentVo>();

	private List<Folder> folderPathList = new ArrayList<Folder>();

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public FolderVo getFolder() {
		return folder;
	}

	public void setFolder(FolderVo folder) {
		this.folder = folder;
	}

	public List<AttachmentVo> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<AttachmentVo> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<Folder> getFolderPathList() {
		return folderPathList;
	}

	public void setFolderPathList(List<Folder> folderPathList) {
		this.folderPathList = folderPathList;
	}

	public long getFolderId() {
		if (this.getFourthFolderId() != 0) {
			return this.getFourthFolderId();
		} else if (this.getThirdFolderId() != 0) {
			return this.getThirdFolderId();
		} else if (this.getSecondFolderId() != 0) {
			return this.getSecondFolderId();
		} else {
			return this.getFirstFolderId();
		}
	}

}
