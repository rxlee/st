package cn.jy.stork.biz.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jy.stork.biz.pojo.ArticleCreatePojo;
import cn.jy.stork.dao.Article;
import cn.jy.stork.dao.ArticleDao;
import cn.jy.stork.dao.ArticleSimplified;

@Service
public class ArticleService {
	@Autowired
	ArticleDao articleDao;

	/**
	 * 按id查找
	 * 
	 * @param id
	 * @return
	 */
	public Article findById(Integer id) {
		Article ac=articleDao.findById(id);
		return ac;
	}
	/**
	 * 添加 
	 * 
	 * @param ArticleCreatePojo
	 * @return
	 */
	public Article add(ArticleCreatePojo pojo) {
		Article ac=new Article();
		ac.setTitle(pojo.getTitle());
		ac.setContent(pojo.getContent());
		ac.setCategory(pojo.getCategory());
		ac.setPublishTime(new Date());
		ac.setPublisher(pojo.getPublisher());
		ac.setPageView(0);
		ac.setAttachImage(pojo.getAttachImage());
		ac.setAttachName(pojo.getAttachName());
		ac.setAttachUrl(pojo.getAttachUrl());
		articleDao.insert(ac);
		return ac;
	}
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public void delect(Integer id) {
		articleDao.delect(id);

	}
	
	/**
	 * 查询 
	 * 
	 * @param category
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<ArticleSimplified> find(String category, Integer pageIndex, Integer pageSize) {
			List<ArticleSimplified> ac=articleDao.findfamily(category,pageIndex,pageSize);
			return ac;

	}

}
