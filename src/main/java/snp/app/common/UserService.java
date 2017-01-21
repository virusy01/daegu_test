package snp.app.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import snp.infra.security.Security;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private CommonRepository commonRepository;
	
	public Map<String, Object> loadUserByUsername(Map<String, Object> params) throws UsernameNotFoundException
	{
		//if ((username == null) || username.isEmpty()) throw new IllegalArgumentException();
		
		Map<String, Object> u = repository.findUserByUsername(params, Security.user());
		/*if(u == null)
		{
			throw new UsernameNotFoundException("notfound");
		}
		List<MenuHierarchy> menus = commonRepository.findUserMenu(username);
		u.toAuthority(menus);*/
		return u;
	}
	
	public List<Map<String, Object>> findUsers(Map<String, Object> params)
	{
		return repository.findUsers(params, Security.user());
	}
	
	public Map<String, Object> findUser(Map<String, Object> params)
	{
		return repository.findUser(params, Security.user());
	}
	
	public int saveUser(Map<String, Object> params)
	{
		int r = 0;
		/*if(u.getUserSeq() > 0)
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
		}*/
		
		
		return r;
	}
	
	private int saveUserRole(Map<String, Object> params)
	{
		commonRepository.deleteRoleUser(params, Security.user());
		if(params.get("ROLE_CDs") != null)
		{
			commonRepository.insertRoleUser(params, Security.user());
		}
		return 1;
	}
	
	public int deleteUser(Map<String, Object> params)
	{
		return repository.deleteUser(params, Security.user());
	}
}
