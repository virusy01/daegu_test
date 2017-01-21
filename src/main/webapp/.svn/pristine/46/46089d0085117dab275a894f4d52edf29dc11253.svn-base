package com.sck.modules.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sck.model.Code;
import com.sck.model.Menu;
import com.sck.model.MenuHierarchy;
import com.sck.model.Role;

@Repository
public interface CommonRepository {
	List<Code> findCodes(String grpCd);
	
	List<MenuHierarchy> findMenus();
	List<MenuHierarchy> findUserMenu(String userId);
	Menu findMenu(String menuCd);
	
	List<Menu> findMenusByParent(String parentMenuCd);
	List<MenuHierarchy> findRoleMenu(String roleCd);
	int findMenuCd(String menuCd);
	int insertMenu(Menu p);
	int updateMenu(Menu p);
	int deleteMenu(String menuCd);
	
	List<Role> findRoles();
	Role findRole(String roleCd);
	int findRoleCd(String roleCd);
	int insertRole(Role p);
	int updateRole(Role p);
	int disableBasicRoles();
	int deleteRole(String roleCd);
	
	int insertRoleMenu(@Param("roleCd") String roleCd, @Param("menuCd") String menuCd);
	int deleteRoleMenu(@Param("roleCd") String roleCd, @Param("menuCd") String menuCd);
	
	int insertRoleUser(@Param("roleCd") String roleCd, @Param("userId") String userId);
	int deleteRoleUser(@Param("roleCd") String roleCd, @Param("userId") String userId);
	
	
	
}
