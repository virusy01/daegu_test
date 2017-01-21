package snp.app.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snp.infra.security.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommonService {

	@Autowired
	private CommonRepository repository;
	
	public List<Map<String, Object>> findCodes(String grpCd)
	{
		return repository.findCodes(grpCd);
	}
	
	/**
	 * get all menus hierarchy
	 * @return
	 */
	public List<Map<String, Object>> findAllMenus()
	{
		List<Map<String, Object>> targets = repository.findMenus();
		/*MenuHierarchy root = new MenuHierarchy("ROOT");
		root.setUrl("/");
		buildHierarchyMenu(root, targets);
		setFirstChild(root);*/
		return null;
	}
	

	
	/**
	 * find menu
	 * @param
	 * @return
	 */
	public Map<String, Object> findMenu(Map<String, Object> params)
	{
		return repository.findMenu(params, Security.user());
	}
	
	/**
	 * get menus by parent
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> findMenusByParent(Map<String, Object> params)
	{
		return repository.findMenusByParent(params, Security.user());
	}
	
	/**
	 * get dup menucd count
	 * @param
	 * @return
	 */
	public int findMenuCd(Map<String, Object> params)
	{
		return repository.findMenuCd(params, Security.user());
	}
	
	/**
	 * insert menu
	 * @param
	 * @return
	 */
	public int insertMenu(Map<String, Object> params)
	{
		return repository.insertMenu(params, Security.user());
	}
	
	/**
	 * update menu
	 * @param
	 * @return
	 */
	public int updateMenu(Map<String, Object> params)
	{
		return repository.updateMenu(params, Security.user());
	}
	
	/**
	 * delete menu
	 * @param
	 * @return
	 */
	/*
	public synchronized void deleteMenu(String menuCd)
	{
		List<Menu> childMenus = repository.findMenusByParent(menuCd);
		repository.deleteRoleMenu(null, menuCd);
		repository.deleteMenu(menuCd);
		for(Menu m : childMenus)
		{
			deleteMenu(m.getMenuCd());
		}
	}
	*/
	public int deleteMenu(Map<String, Object> params)
	{
		//deleteChildMenus(repository, menuCd);
		return 1;
	}
	
	private static synchronized void deleteChildMenus(CommonRepository repository, String parentMenuCd)
	{
		/*repository.deleteRoleMenu(null, parentMenuCd);
		repository.deleteMenu(parentMenuCd);
		List<Menu> childMenus = repository.findMenusByParent(parentMenuCd);
		for(Menu m : childMenus)
		{
			deleteChildMenus(repository, m.getMenuCd());
		}*/
	}
	
	/**
	 * get all roles
	 * @return
	 */
	public List<Map<String, Object>> findRoles()
	{
		return repository.findRoles();
	}
	
	/**
	 * get role
	 * @param
	 * @return
	 */
	public Map<String, Object> findRole(Map<String, Object> params)
	{
		return repository.findRole(params, Security.user());
	}
	
	/**
	 * get role menus
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> findRoleMenu(Map<String, Object> params)
	{
		return repository.findRoleMenu(params, Security.user());
	}
	
	/**
	 * get dup rolecd count
	 * @param roleCd
	 * @return
	 */
	public int findRoleCd(String roleCd)
	{
		return repository.findRoleCd(roleCd);
	}
	
	/**
	 * 
	 * @param
	 * @return
	 */
	public int insertRole(Map<String, Object> params)
	{
		/*if(p.isBasic())
		{
			repository.disableBasicRoles();
		}*/
		return repository.insertRole(params, Security.user());
	}
	
	public int updateRole(Map<String, Object> params)
	{
		/*Role r = repository.findRole(p.getRoleCd());
		if(!r.isBasic() && p.isBasic())
		{
			repository.disableBasicRoles();
		}*/
		return repository.updateRole(params, Security.user());
	}
	
	public int deleteRole(Map<String, Object> params)
	{
		/*repository.deleteRoleUser(roleCd, null);
		repository.deleteRoleMenu(roleCd, null);*/
		return repository.deleteRole(params, Security.user());
	}
	
	public int saveRoleMenu(Map<String, Object> params)
	{
		repository.deleteRoleMenu(params, Security.user());
		/*for(String menuCd : p.getMenuCd())
		{
			repository.insertRoleMenu(p.getRoleCd(), menuCd);
		}*/
		return 1;
	}
	
}
