package cn.jy.stork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;



@Mapper
public interface ConversitionDao {
	
	
	
	
	@Select({ "<script>", 
		"SELECT * FROM tb_conversition WHERE true ",
		"<when test='category!=null'>",
		" AND category=#{category}",
		"</when>",
		" ORDER BY post_time DESC",
		" LIMIT #{pageIndex},#{pageSize}",
		"</script>" })
	List<ConversitionSimplified> find(String category, Integer pageIndex, Integer pageSize);
	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_conversition WHERE id=#{id}")
	Conversition findById(Integer id);
	
	
	
	
	/**
	 * 新增记录
	 * 
	 * @param ac
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({ "INSERT INTO tb_conversition",
		"(title,content,category,post_time,poster) ",
		"VALUES",
		"(#{title},#{content},#{category},#{postTime},#{poster})"})	
	void insert(Conversition cc);
	
	
	
	@Select({
		"UPDATE tb_conversition SET "
		+ "reply_content=#{cc.replyContent},reply_time=#{cc.replyTime}"
		+ " WHERE id=#{id}"
	})
	void update(Conversition cc, int id);
	
	
	
	/**
	 * 删除记录
	 * 
	 * @param id
	 */
	@Select("DELETE FROM tb_conversition WHERE id=#{id}")
	void delect(Integer id);
	

}
