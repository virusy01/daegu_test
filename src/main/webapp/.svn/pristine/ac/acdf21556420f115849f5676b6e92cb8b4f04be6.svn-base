package com.sck.common.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sck.model.MenuActionRole;
import com.sck.model.MenuRole;

@Component("menuPermissionEvaluator")
public class MenuPermissionEvaluator implements PermissionEvaluator
{
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission)
	{
		boolean hasPermission = false;
		Object principal = authentication.getPrincipal(); 
		if (principal instanceof UserDetails) 
		{
			String menuId = (String)targetDomainObject;
			UserDetails userDetails = (UserDetails)principal;
			MenuRole role = getMenuRole(userDetails.getAuthorities(), menuId);
			if(role == null)
			{
				return false;
			}
			hasPermission = hasPermission(role.getAllowedActions(), (String)permission);
		}
		return hasPermission;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object obj)
	{
		return false;
	}
	
	public MenuRole getMenuRole(Collection<? extends GrantedAuthority> authorities,String menuId)
	{
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		MenuRole role = null;
		while(iter.hasNext())
		{
			role = (MenuRole)iter.next();
			if(role.getAuthority().equals(menuId))
			{
				break;
			}
		}
		return role;
	}
	
	public boolean hasPermission(Collection<? extends GrantedAuthority> authorities, String permission)
	{
		boolean hasPermission = false;
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		MenuActionRole action = null;
		while(iter.hasNext())
		{
			action = (MenuActionRole)iter.next();
			if(action.getAuthority().equals(permission))
			{
				hasPermission = true;
				break;
			}
		}
		return hasPermission;
	}

}
