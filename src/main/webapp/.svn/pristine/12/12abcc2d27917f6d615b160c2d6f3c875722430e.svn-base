package com.sck.model;

import org.springframework.security.core.GrantedAuthority;

public class MenuActionRole implements GrantedAuthority
{
	private static final long serialVersionUID = 2000117716545131556L;
	private String action;
	
	public MenuActionRole(String action)
	{
		this.action = action;
	}
	
	@Override
	public String getAuthority()
	{
		return action;
	}

}
