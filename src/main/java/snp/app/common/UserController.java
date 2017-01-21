package snp.app.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import snp.infra.helper.RequestParams;
import snp.infra.user.model.User;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService service;

	@RequestMapping(value="", method= RequestMethod.GET)
	public User getLoggedInUser()
	{
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return u;
	}
	/**
	 * 사용자 상세
	 * from user_detail.php
	 * @param userSeq
	 * @return
	 */
	@RequestMapping(value="/{userSeq}", method= RequestMethod.GET)
	public Map<String, Object> findUsers(@PathVariable("userSeq") Integer userSeq)
	{
		return service.findUser(RequestParams.map());
	}

	/**
	 * 사용자 목록
	 * from user_info_count.php , user_info.php
	 * @param page
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value="/find/{page}/{keyword}", method= RequestMethod.GET)
	public List<Map<String, Object>> findUsers(@PathVariable("page") Integer page, @RequestParam(value="keyword",required=false) String keyword)
	{
		return service.findUsers(RequestParams.map());
	}

	@RequestMapping(value="/find", method= RequestMethod.GET)
	public List<Map<String, Object>> findUsers(@RequestParam(value="keyword",required=false) String keyword)
	{
		return service.findUsers(RequestParams.map());
	}

	/**
	 * 사용자 저장(입력,수정)
	 * from user_save.php
	 * @param u
	 * @return
	 */
	@RequestMapping(value="", method={RequestMethod.POST, RequestMethod.PUT})
	public int saveUser(@RequestBody Map<String, Object> params)
	{
		return service.saveUser(RequestParams.map());
	}
	
	/**
	 * 사용자 삭제
	 * @param userSeq
	 * @return
	 */
	@RequestMapping(value="/{userSeq}", method={RequestMethod.DELETE})
	public int deleteUser(@PathVariable("userSeq") Integer userSeq)
	{
		return service.deleteUser(RequestParams.map());
	}
	
	
}
