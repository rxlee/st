package cn.jy.stork.dao;

//辖区，带有outline
public class RegionWithOutline extends Region {
	private String outline;

	public final String getOutline() {
		return outline;
	}

	public final void setOutline(String outline) {
		this.outline = outline;
	}

}
