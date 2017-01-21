package com.sck.model;

import java.rmi.server.Operation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public class MenuRole implements GrantedAuthority
{
	private static final long serialVersionUID = -4766776526710187422L;
	
	private String menuCd;
	private Set<GrantedAuthority> actions;
	
	public MenuRole(String menuCd)
	{
		this.menuCd = menuCd;
		Set<GrantedAuthority> set = new HashSet<GrantedAuthority>();
		set.add(new MenuActionRole("VIEW"));
		this.actions = set;
	}
	
	public MenuRole(String menuCd, Set<GrantedAuthority> actions)
	{
		this.menuCd = menuCd;
		this.actions = actions; 
	}
	
	@Override
	public String getAuthority()
	{
		return menuCd;
	}
	
	public void setActions(Set<GrantedAuthority> actions)
	{
		this.actions = actions;
	}

	public Collection<GrantedAuthority> getAllowedActions()
	{
		return actions;
	}

}
