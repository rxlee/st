package cn.jy.stork.util;

public abstract class APageablePojo {
	private Integer pageIndex;
	private Integer pageSize;

	public final Integer getPageIndex() {
		return pageIndex;
	}

	public final void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public final Integer getPageSize() {
		return pageSize;
	}

	public final void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
