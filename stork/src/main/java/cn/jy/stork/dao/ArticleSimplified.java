package cn.jy.stork.dao;

import java.util.Date;

/**
 * 简化的文章数据表对象，只包含一些简单信息，主要用于文章列表
 * 
 * @author jsh
 *
 */
public class ArticleSimplified {
	private Integer id;
	private String title;
	private String category;
	private Date publishTime;
	private String publisher;
	private Integer pageView;

	public final Integer getId() {
		return id;
	}

	public final void setId(Integer id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public final String getCategory() {
		return category;
	}

	public final void setCategory(String category) {
		this.category = category;
	}

	public final Date getPublishTime() {
		return publishTime;
	}

	public final void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public final String getPublisher() {
		return publisher;
	}

	public final void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public final Integer getPageView() {
		return pageView;
	}

	public final void setPageView(Integer pageView) {
		this.pageView = pageView;
	}

}
