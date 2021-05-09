package cn.jy.stork.dao;

import java.util.Date;

/**
 * 完备的文章数据表对象。注意查询文章列表时不要使用本对象，参考{@link ArticleSimplified}
 * 
 * @author jsh
 *
 */
public class Article {
	private Integer id;
	private String title;
	private String content;
	private String category;
	private Date publishTime;
	private String publisher;
	private Integer pageView;
	private String attachImage;
	private String attachName;
	private String attachUrl;

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

	public final String getContent() {
		return content;
	}

	public final void setContent(String content) {
		this.content = content;
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

	public final String getAttachImage() {
		return attachImage;
	}

	public final void setAttachImage(String attachImage) {
		this.attachImage = attachImage;
	}

	public final String getAttachName() {
		return attachName;
	}

	public final void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public final String getAttachUrl() {
		return attachUrl;
	}

	public final void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}

}
