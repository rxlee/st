package cn.jy.stork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleDao {

	
	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_article WHERE id=#{id};"
			+ "update tb_article set page_view=page_view+1 where id=#{id}")
	Article findById(Integer id);

	
	/**
	 * 新增记录
	 * 
	 * @param ac
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({ "INSERT INTO tb_article",
		"(title,content,category,publish_time,publisher,page_view,attach_image,attach_name,attach_url) ",
		"VALUES",
		"(#{title},#{content},#{category},#{publishTime},#{publisher},#{pageView},#{attachImage},#{attachName},#{attachUrl})"})	
	void insert(Article ac);

	/**
	 * 删除记录
	 * 
	 * @param id
	 * 
	 */
	@Select("DELETE FROM tb_article WHERE id=#{id}")
	void delect(Integer id);


	/**
	 * 查询记录
	 * 
	 * @param category
	 * @param pageIndex
	 * @param pageSize
	 * @return List<ArticleSimplified>
	 */
	@Select({ "<script>", 
		"SELECT * FROM tb_article WHERE true ",
		"<when test='category!=null'>",
		" AND category=#{category}",
		"</when>",
		" ORDER BY publish_time DESC",
		" LIMIT #{pageIndex},#{pageSize}",
		"</script>" })
	List<ArticleSimplified> findfamily(String category, Integer pageIndex, Integer pageSize);
	

}
