package cn.jy.stork.biz.pojo;

//检测机构对象
public class DetectCorpPojo {
	private String name;
	private String alias;
	private String address;
	private Integer category;

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

	public final String getAddress() {
		return address;
	}

	public final void setAddress(String address) {
		this.address = address;
	}

	public final Integer getCategory() {
		return category;
	}

	public final void setCategory(Integer category) {
		this.category = category;
	}

}
