package com.sck.modules.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sck.model.MenuHierarchy;
import com.sck.model.Page;
import com.sck.model.PageList;
import com.sck.model.User;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private CommonRepository commonRepository;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		if ((username == null) || username.isEmpty()) throw new IllegalArgumentException();
		
		User u = repository.findUserByUsername(username);
		if(u == null)
		{
			throw new UsernameNotFoundException("notfound");
		}
		List<MenuHierarchy> menus = commonRepository.findUserMenu(username);
		u.toAuthority(menus);
		return u;
	}
	
	public PageList findUsers(int page, String userNm)
	{
		if(page == 0)
		{
			page = 1;
		}
		
		Page p = new Page(repository.countUsers(userNm), page);
		List<User> list = repository.findUsers(userNm, p);
		PageList result = new PageList(p, list);
		return result;
	}
	
	public List<User> findUsers(String userNm)
	{
		return repository.findUsers(userNm, null);
	}
	
	public User findUser(Integer userSeq)
	{
		return repository.findUser(userSeq);
	}
	
	public int saveUser(User u)
	{
		int r = 0;
		if(u.getUserSeq() > 0)
		{
			boolean changeRole = false;
			User user = findUser(u.getUserSeq());
			r = repository.updateUser(u);
			if(user.getRoleCd() == null && u.getRoleCd() != null)
			{
				changeRole = true;
			}
			if(user.getRoleCd() != null && u.getRoleCd() == null)
			{
				changeRole = true;
			}
			
			if(user.getRoleCd() != null && u.getRoleCd() != null && !user.getRoleCd().equals(u.getRoleCd()))
			{
				changeRole = true;
			}
			saveUserRole(u.getRoleCd(), u.getUserId());
			
		}
		else
		{
			r = repository.insertUser(u);
			if(u.getRoleCd() != null)
			{
				saveUserRole(u.getRoleCd(), u.getUserId());
			}
		}
		
		
		return r;
	}
	
	private int saveUserRole(String roleCd, String userId)
	{
		commonRepository.deleteRoleUser(null, userId);
		if(roleCd != null)
		{
			commonRepository.insertRoleUser(roleCd, userId);
		}
		return 1;
	}
	
	public int deleteUser(Integer userSeq)
	{
		return repository.deleteUser(userSeq);
	}
}
