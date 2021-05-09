package cn.jy.stork.dao;

import java.util.Date;

public class Conversition {
	private Integer id;
	private String title;
	private String content;
	private String category;
	private Date postTime;
	private String poster;
	private String replyContent;
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

	public final String getReplyContent() {
		return replyContent;
	}

	public final void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public final Date getReplyTime() {
		return replyTime;
	}

	public final void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

}
