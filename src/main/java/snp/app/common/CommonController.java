package snp.app.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import snp.infra.helper.RequestParams;

import java.util.List;
import java.util.Map;

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
	@RequestMapping(value="/{grpCd}/code", method= RequestMethod.GET)
	public List<Map<String, Object>> findCodes(@PathVariable("grpCd") String grpCd)
	{
		return service.findCodes(grpCd); 
	}
	
	/**
	 * 전체 메뉴 조회
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00013','VIEW') or hasPermission('ADM00014','VIEW')")
	@RequestMapping(value="/menus", method= RequestMethod.GET)
	public List<Map<String, Object>> findAllMenus()
	{
		return service.findAllMenus();
	}
	
	/**
	 * 하위 메뉴 목록
	 * @param parentMenuCd
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00013','VIEW')")
	@RequestMapping(value="/menus/{parentMenu}", method= RequestMethod.GET)
	public List<Map<String, Object>> findMenusByParent(@PathVariable("parentMenu") String parentMenuCd)
	{
		if(parentMenuCd.equals("ROOT"))
		{
			parentMenuCd = null;
		}
		return service.findMenusByParent(RequestParams.map());
	}
	
	/**
	 * 메뉴 목록 저장
	 * @param menus
	 * @return
	 */
	@RequestMapping(value="/menus", method= RequestMethod.POST)
	public int saveMenus(@RequestBody List<Map<String, Object>> menus)
	{
		/*for(Menu p : menus)
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
		return new Result(0, "OK");*/
		return -1;
	}
	
	/**
	 * 메뉴등록
	 * @param p
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00013','VIEW')")
	@RequestMapping(value="/menu", method= RequestMethod.PUT)
	public int insertMenu(@RequestBody Map<String, Object> p)
	{
		return service.insertMenu(p);
	}
	
	/**
	 * 메뉴수정
	 * @param menuCd
	 * @param p
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00013','VIEW')")
	@RequestMapping(value="/menu/{menuCd}", method= RequestMethod.POST)
	public int updateMenu(@PathVariable("menuCd") String menuCd, @RequestBody Map<String, Object> p)
	{
		/*Menu menu = service.findMenu(menuCd);
		if(!menuCd.equals(p.getMenuCd()))
		{
			return new Result(-1, "WRONG");
		}*/
		return service.updateMenu(RequestParams.map());
		//return new Result(0, "OK");
	}
	
	/**
	 * 메뉴삭제
	 * @param menuCd
	 * @param
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00013','VIEW')")
	@RequestMapping(value="/menu/{menuCd}", method= RequestMethod.DELETE)
	public int deleteMenu(@PathVariable("menuCd") String menuCd)
	{
		return service.deleteMenu(RequestParams.map());
		//return new Result(0, "OK");
	}
	
	/**
	 * 역할목록 조회
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00014','VIEW')")
	@RequestMapping(value="/roles", method= RequestMethod.GET)
	public List<Map<String, Object>> findRoles()
	{
		return service.findRoles();
	}
	
	/**
	 * 역할별 메뉴
	 * @param roleCd
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00014','VIEW')")
	@RequestMapping(value="/role/{roleCd}/menus", method= RequestMethod.GET)
	public List<Map<String, Object>> findRoleMenus(@PathVariable("roleCd") String roleCd)
	{
		return service.findRoleMenu(RequestParams.map());
	}
	
	/**
	 * 역할 등록
	 * @param p
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00014','VIEW')")
	@RequestMapping(value="/role", method= RequestMethod.PUT)
	public int insertRole(@RequestBody Map<String, Object> p)
	{
		/*int dupCount = service.findRoleCd(p.getRoleCd());
		if(dupCount > 0)
		{
			return new Result(-1, "DUPLICATE");
		}*/
		service.insertRole(p);
		return service.saveRoleMenu(p);
		//return new Result(0, "OK");
	}
	
	/**
	 * 역할수정
	 * @param
	 * @param p
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00014','VIEW')")
	@RequestMapping(value="/role/{roleCd}", method= RequestMethod.POST)
	public int updateRole(@PathVariable("roleCd") String roleCd, @RequestBody Map<String, Object> p)
	{
		/*Role r = service.findRole(roleCd);
		if(!roleCd.equals(p.getRoleCd()))
		{
			return new Result(-1, "WRONG");
		}*/
		service.updateRole(p);
		return service.saveRoleMenu(p);
		//return new Result(0, "OK");
	}
	
	/**
	 * 역할삭제
	 * @param
	 * @param
	 * @return
	 */
	@PreAuthorize("hasPermission('ADM00014','VIEW')")
	@RequestMapping(value="/role/{roleCd}", method= RequestMethod.DELETE)
	public int deleteRole(@PathVariable("roleCd") String roleCd)
	{
		return service.deleteRole(RequestParams.map());
		//return new Result(0, "OK");
	}
	
}
