package cn.jy.stork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 用户账号数据库操作对象
 * 
 * @author jsh
 *
 */
@Mapper
public interface AccountDao {
	
	/**
	 * 通用条件查询（排除了admin账号，结果中不包含admin）
	 * 
	 * @param name
	 * @param role
	 * @param status
	 * @param pageIndex 页码，不可以为null。但如果没有输入每页条数，那么页码也就被忽略了。
	 * @param pageSize 每页条数。若输入null，则表示不分页，取全部数据（同时忽略页码）
	 * @return
	 */
	@Select({
		"<script>",
		"SELECT * FROM tb_account WHERE telephone&lt;&gt;'admin' ",
		"<when test='name!=null'>",
		" AND name=#{name}",
		"</when>",
		"<when test='role!=null'>",
		" AND role=#{role}",
		"</when>",
		"<when test='status!=null'>",
		" AND status=#{status}",
		"</when>",
		" ORDER BY name",
		"<when test='pageSize!=null'>",
		" LIMIT #{pageIndex},#{pageSize}",
		"</when>",
		"</script>"
	})
	List<Account> find(
			String name,
			String role, 
			Integer status,
			int pageIndex,
			Integer pageSize);
	
	/**
	 * 
	 * 根据role查询账号。只会查出status=1的账号（也就是正常账号），同时，也排除了admin超级管理员账号。
	 * 
	 * @param role	角色。不应为null，但这里并不检查，外部应该检查并确保。
	 * @param pageIndex 页码，不应为null。但如果每页条数设置为null，则页码就被忽略了。
	 * @param pageSize	每页条数，输入null则取所有（忽略页码）
	 * @return
	 */
	@Select({
		"<script>",
		"SELECT * FROM tb_account WHERE telephone&lt;&gt;'admin' AND status=1 ",
		"<when test='role!=null'>",
		" AND role LIKE #{role}",
		"</when>",
		" ORDER BY name",
		"<when test='pageSize!=null'>",
		" LIMIT #{pageIndex},#{pageSize}",
		"</when>",
		"</script>"
	})
	List<Account> findByRole(
			String role, 
			int pageIndex,
			Integer pageSize);

	/**
	 * 新增一个账号
	 * 
	 * @param account
	 * @return
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({"INSERT INTO tb_account",
			"(name, telephone, password, salt, role, status) ",
			"VALUES",
			"( #{name}, #{telephone}, #{password}, #{salt}, #{role}, #{status} )"})
	int add(Account account);

	/**
	 * 用电话号码查找一个账号
	 * 
	 * @param telephone
	 * @return
	 */
	@Select("SELECT * FROM tb_account WHERE telephone=#{telephone}")
	Account findByTelephone(String telephone);
	

	/**
	 * 用id查找一个账号
	 * 
	 * @param telephone
	 * @return
	 */
	@Select("SELECT * FROM tb_account WHERE id=#{id}")
	Account findById(int id);
	
	/**
	 * 刷新一个用户的角色
	 * 
	 * @param accountId
	 * @param role
	 * @return
	 */
	@Update("UPDATE tb_account SET role=#{role} WHERE id=#{id} AND telephone<>'admin'")
	int updateRole(@Param("id") int accountId,@Param("role") String role);
	
	
}
