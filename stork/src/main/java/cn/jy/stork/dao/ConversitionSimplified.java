package cn.jy.stork.dao;

import java.util.Date;

/**
 * 政民互动数据表对象，简化版，略去了正文等部分，主要用于生成列表
 * 
 * @author jsh
 *
 */
public class ConversitionSimplified {
	private Integer id;
	private String title;
	private String category;
	private Date postTime;
	private String poster;
	private Date replyTime;

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

	public final Date getPostTime() {
		return postTime;
	}

	public final void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public final String getPoster() {
		return poster;
	}

	public final void setPoster(String poster) {
		this.poster = poster;
	}

	public final Date getReplyTime() {
		return replyTime;
	}

	public final void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

}
