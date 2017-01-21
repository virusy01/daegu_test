package snp.app.common;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import snp.infra.user.model.User;

import java.util.List;
import java.util.Map;

@Repository
public interface CommonRepository {
	List<Map<String, Object>> findCodes(String grpCd);
	
	List<Map<String, Object>> findMenus();
	List<Map<String, Object>> findUserMenu(@Param("p") Map<String, Object> params, @Param("u") User user);
	Map<String, Object> findMenu(@Param("p") Map<String, Object> params, @Param("u") User user);
	
	List<Map<String, Object>> findMenusByParent(@Param("p") Map<String, Object> params, @Param("u") User user);
	List<Map<String, Object>> findRoleMenu(@Param("p") Map<String, Object> params, @Param("u") User user);
	int findMenuCd(@Param("p") Map<String, Object> params, @Param("u") User userd);
	int insertMenu(@Param("p") Map<String, Object> params, @Param("u") User user);
	int updateMenu(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteMenu(@Param("p") Map<String, Object> params, @Param("u") User user);
	
	List<Map<String, Object>> findRoles();
	Map<String, Object> findRole(@Param("p") Map<String, Object> params, @Param("u") User user);
	int findRoleCd(String roleCd);
	int insertRole(@Param("p") Map<String, Object> params, @Param("u") User user);
	int updateRole(@Param("p") Map<String, Object> params, @Param("u") User user);
	int disableBasicRoles();
	int deleteRole(@Param("p") Map<String, Object> params, @Param("u") User user);
	
	int insertRoleMenu(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteRoleMenu(@Param("p") Map<String, Object> params, @Param("u") User user);
	
	int insertRoleUser(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteRoleUser(@Param("p") Map<String, Object> params, @Param("u") User user);
}
