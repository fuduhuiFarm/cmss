/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shop.cms.admin.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shop.cms.admin.dao.AttachmentDao;
import com.shop.cms.admin.model.Attachment;
import com.shop.cms.admin.model.AttachmentVo;
import com.shop.cms.admin.model.PageVo;
import com.shop.cms.common.constant.AttachmentConstant;
import com.shop.cms.common.constant.SystemConstant;
import com.shop.cms.common.util.UploadUtils;
import com.shop.cms.core.exception.UploadException;

@Service
public class AttachmentService {

	@Autowired
	private AttachmentDao attachmentDao;

	/*
	 * 上传附件
	 */
	@CacheEvict(value = "article", allEntries = true)
	public Attachment addUploadFile(MultipartFile multipartFile,
			String fileName, long kindId, AttachmentConstant.Kind kind,
			AttachmentConstant.Status status) throws IllegalStateException,
			IOException, UploadException {
		AttachmentConstant.Type type = AttachmentConstant.Type.photo;
		if (UploadUtils.isFileType(fileName, UploadUtils.PHOTO_TYPE)) {
			type = AttachmentConstant.Type.photo;
		} else if (UploadUtils.isFileType(fileName, UploadUtils.FILE_TYPE)) {
			type = AttachmentConstant.Type.file;
		} else {
			throw new UploadException("文件类型有误");
		}
		Date now = new Date();
		String uploadPath = UploadUtils.getUploadPath(fileName, now.getTime());
		Attachment attachment = new Attachment();
		attachment.setKindId(kindId);
		attachment.setDescription("");
		attachment.setName(fileName);
		attachment.setPath(uploadPath);
		attachment.setType(type);
		attachment.setLink("javascript:void(0);");
		attachment.setSize(multipartFile.getSize());
		attachment.setKind(kind);
		attachment.setStatus(status);
		attachment.setCreateTime(now);
		attachmentDao.addAttachment(attachment);
		multipartFile.transferTo(new java.io.File(
				SystemConstant.SHISHUO_CMS_ROOT + uploadPath));
		return attachment;
	}

	/**
	 * 
	 * 删除附件通过附件ID
	 */
	@CacheEvict(value = "article", allEntries = true)
	public void deleteAttachment(long attachmentId, String path) {
		attachmentDao.deleteAttachment(attachmentId);
		UploadUtils.deleteFile(path);
	}

	/**
	 * @param attachmentId
	 * @param articleId
	 * @param name
	 * @param path
	 * @param size
	 * @param type
	 * @param status
	 * @return
	 */
	@CacheEvict(value = "article", allEntries = true)
	public boolean updateStatusByAttachmentId(long attachmentId,
			AttachmentConstant.Status status) {
		attachmentDao.updateStatusByAttachmentId(attachmentId, status);
		return true;

	}

	/**
	 * @param attachmentId
	 * @return
	 */
	public Attachment getAttachmentById(long attachmentId) {
		return attachmentDao.getAttachmentById(attachmentId);
	}

	/**
	 * @param attachmentId
	 * @param link
	 * @return
	 */
	@CacheEvict(value = "article", allEntries = true)
	public int updateLinkByAttachmentId(long attachmentId, String link) {
		return attachmentDao.updateLinkByAttachmentId(attachmentId, link);
	}

	/**
	 * @param folderId
	 * @return
	 */
	@Cacheable(value = "article", key = "'getAttachmentPageByFolderId_'+#kindId+'_'+#kind+'_'+#status+'_'+#pageNum")
	public PageVo<AttachmentVo> getAttachmentPageByKindId(long kindId,
			AttachmentConstant.Kind kind, int rows, int pageNum) {
		PageVo<AttachmentVo> pageVo = new PageVo<AttachmentVo>(pageNum);
		pageVo.setRows(rows);
		pageVo.setCount(attachmentDao.getAttachmentCountByKindId(kindId, kind,
				AttachmentConstant.Status.display));
		pageVo.setList(attachmentDao.getAttachmentListByKindId(kindId, kind,
				AttachmentConstant.Status.display, pageVo.getOffset(),
				pageVo.getRows()));
		return pageVo;
	}

	/**
	 * @param kindId
	 * @param kind
	 * @param stauts
	 * @return
	 */
	@Cacheable(value = "article", key = "'getAttachmentListByKindId_'+#kindId+'_'+#kind+'_'+#status")
	public List<AttachmentVo> getAttachmentListByKindId(long kindId,
			AttachmentConstant.Kind kind, AttachmentConstant.Status status) {
		return attachmentDao.getAttachmentListByKindId(kindId, kind, status, 0,
				1000);

	}
}
