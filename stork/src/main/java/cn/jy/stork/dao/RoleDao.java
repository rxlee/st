package cn.jy.stork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户角色数据库操作对象
 * 
 * @author jsh
 *
 */
@Mapper
public interface RoleDao {
	
	/**
	 * 根据账号id查询用户的角色
	 * 
	 * @param accountId 账号id
	 * @return
	 */
	@Select("SELECT role FROM tb_role WHERE account_id=#{accountId}") 
	List<String> findByAccountId(int accountId);

	/**
	 * 给指定用户增加一个角色
	 * 
	 * @param accountId 账号id
	 * @param role 新角色
	 * @return
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({"INSERT INTO tb_role",
		"(account_id,role)",
		"VALUES",
		"(#{r.accountId},#{r.role})"})
	int add(@Param("r") Role role);

	/**
	 * 给指定用户删除一个角色
	 * 
	 * @param accountId 账号id
	 * @param role 角色
	 * @return
	 */
	@Delete("DELETE FROM tb_role WHERE account_id=#{accountId} AND role=#{role}")
	int delete(int accountId, String role);
	

	/**
	 * 删除指定用户所有角色
	 * 
	 * @param accountId
	 * @return
	 */
	@Delete("DELETE FROM tb_role WHERE account_id={accountId}")
	Account deleteAll(@Param("accountId") int accountId);
	
	/**
	 * 给指定用户增加一批角色（MyBatis 批量插入  可以百度）
	 * 
	 * @param accountId 账号id
	 * @param roles 新角色
	 * @return
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({"<script>",
		"INSERT INTO tb_role",
		"(account_id,role)",
		"VALUES",
		"<foreach collection='roles' item='role' separator=','>",
		"(#{role.accountId},#{role.role})",
		"</foreach>",
		"</script>"})
	int addBatch(@Param("roles") List<Role> roles);

}
