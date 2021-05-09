package cn.jy.stork.dao;

//辖区：不带outline，用于一般获取辖区信息。
public class Region {
	private Integer id;
	private String name;
	private String alias;

	public final Integer getId() {
		return id;
	}

	public final void setId(Integer id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getAlias() {
		return alias;
	}

	public final void setAlias(String alias) {
		this.alias = alias;
	}

}
