package com.sck.modules.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sck.model.Code;
import com.sck.model.Menu;
import com.sck.model.MenuHierarchy;
import com.sck.model.Result;
import com.sck.model.Role;
import com.sck.model.RoleMenu;

@RestController
@RequestMapping("/api/common")
public class CommonController {
	
	@Autowired
	private CommonService service;
	
	/**
	 * 공통코드 목록
	 * from code.php
	 * @param grpCd
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/{grpCd}/code", method=RequestMethod.GET)
	public List<Code> findCodes(@PathVariable("grpCd") String grpCd)
	{
		return service.findCodes(grpCd); 
	}
	
	/**
	 * 전체 메뉴 조회
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00013','VIEW') or hasPermission('ADM00014','VIEW')")
	@RequestMapping(value="/menus", method=RequestMethod.GET)
	public MenuHierarchy findAllMenus()
	{
		return service.findAllMenus();
	}
	
	/**
	 * 하위 메뉴 목록
	 * @param parentMenuCd
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00013','VIEW')")
	@RequestMapping(value="/menus/{parentMenu}", method=RequestMethod.GET)
	public List<Menu> findMenusByParent(@PathVariable("parentMenu") String parentMenuCd)
	{
		if(parentMenuCd.equals("ROOT"))
		{
			parentMenuCd = null;
		}
		return service.findMenusByParent(parentMenuCd);
	}
	
	/**
	 * 메뉴 목록 저장
	 * @param menus
	 * @return
	 */
	@RequestMapping(value="/menus", method=RequestMethod.POST)
	public Result saveMenus(@RequestBody List<Menu> menus)
	{
		for(Menu p : menus)
		{
			Menu old = service.findMenu(p.getMenuCd());
			if(p.isNew())
			{
				if(old != null)
				{
					return new Result(-1, "DUPLICATE");
				}
				service.insertMenu(p);
			}
			else
			{
				if(!old.getMenuCd().equals(p.getMenuCd()))
				{
					return new Result(-1, "WRONG");
				}
				service.updateMenu(p);
			}
		}
		return new Result(0, "OK");
	}
	
	/**
	 * 메뉴등록
	 * @param p
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00013','VIEW')")
	@RequestMapping(value="/menu", method=RequestMethod.PUT)
	public Result insertMenu(@RequestBody Menu p)
	{
		int dupCount = service.findMenuCd(p.getMenuCd());
		if(dupCount > 0)
		{
			return new Result(-1, "DUPLICATE");
		}
		service.insertMenu(p);
		return new Result(0, "OK");
	}
	
	/**
	 * 메뉴수정
	 * @param menuCd
	 * @param p
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00013','VIEW')")
	@RequestMapping(value="/menu/{menuCd}", method=RequestMethod.POST)
	public Result updateMenu(@PathVariable("menuCd") String menuCd, @RequestBody Menu p)
	{
		Menu menu = service.findMenu(menuCd);
		if(!menuCd.equals(p.getMenuCd()))
		{
			return new Result(-1, "WRONG");
		}
		service.updateMenu(p);
		return new Result(0, "OK");
	}
	
	/**
	 * 메뉴삭제
	 * @param menuCd
	 * @param p
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00013','VIEW')")
	@RequestMapping(value="/menu/{menuCd}", method=RequestMethod.DELETE)
	public Result deleteMenu(@PathVariable("menuCd") String menuCd)
	{
		service.deleteMenu(menuCd);
		return new Result(0, "OK");
	}
	
	/**
	 * 역할목록 조회
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00014','VIEW')")
	@RequestMapping(value="/roles", method=RequestMethod.GET)
	public List<Role> findRoles()
	{
		return service.findRoles();
	}
	
	/**
	 * 역할별 메뉴
	 * @param roleCd
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00014','VIEW')")
	@RequestMapping(value="/role/{roleCd}/menus", method=RequestMethod.GET)
	public List<MenuHierarchy> findRoleMenus(@PathVariable("roleCd") String roleCd)
	{
		return service.findRoleMenu(roleCd);
	}
	
	/**
	 * 역할 등록
	 * @param p
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00014','VIEW')")
	@RequestMapping(value="/role", method=RequestMethod.PUT)
	public Result insertRole(@RequestBody RoleMenu p)
	{
		int dupCount = service.findRoleCd(p.getRoleCd());
		if(dupCount > 0)
		{
			return new Result(-1, "DUPLICATE");
		}
		service.insertRole(p);
		service.saveRoleMenu(p);
		return new Result(0, "OK");
	}
	
	/**
	 * 역할수정
	 * @param menuCd
	 * @param p
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00014','VIEW')")
	@RequestMapping(value="/role/{roleCd}", method=RequestMethod.POST)
	public Result updateRole(@PathVariable("roleCd") String roleCd, @RequestBody RoleMenu p)
	{
		Role r = service.findRole(roleCd);
		if(!roleCd.equals(p.getRoleCd()))
		{
			return new Result(-1, "WRONG");
		}
		service.updateRole(p);
		service.saveRoleMenu(p);
		return new Result(0, "OK");
	}
	
	/**
	 * 역할삭제
	 * @param menuCd
	 * @param p
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00014','VIEW')")
	@RequestMapping(value="/role/{roleCd}", method=RequestMethod.DELETE)
	public Result deleteRole(@PathVariable("roleCd") String roleCd)
	{
		service.deleteRole(roleCd);
		return new Result(0, "OK");
	}
	
}
