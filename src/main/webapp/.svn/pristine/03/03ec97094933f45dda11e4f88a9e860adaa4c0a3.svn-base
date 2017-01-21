package com.sck.common.security;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

import com.sck.model.User;

@Component
public class HttpSessionListener implements ApplicationListener<ApplicationEvent>
{
	final static private Logger logger = LoggerFactory.getLogger(HttpSessionListener.class);
	
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		HttpSession session = null;
		User user = null;
		if(applicationEvent instanceof HttpSessionCreatedEvent)
		{
			session = ((HttpSessionCreatedEvent)applicationEvent).getSession();
			user = getUser(session);
			
			if(user != null)
			{
				logger.info("session created : " + session.getId() + " : " + user.getUsername());
			}
			else
			{
				logger.info("session created : " + session.getId());
			}
		}
		else if(applicationEvent instanceof HttpSessionDestroyedEvent)
		{
			session = ((HttpSessionDestroyedEvent)applicationEvent).getSession();
			user = getUser(session);
			if(user != null)
			{
				logger.info("session destroyed : " + session.getId() + " : " + user.getUsername());
			}
			else
			{
				logger.info("session destroyed : " + session.getId());
			}
		}
		
	}
	
	private User getUser(HttpSession session)
	{
		if(session != null)
		{
			SecurityContext ctx = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
			if(ctx == null){
				return null;
			}
			
			Authentication auth = ctx.getAuthentication();
			if(auth == null){
				return null;
			}
			User user = null;
			if(auth.getPrincipal() instanceof User)     
	        {
				user = (User) auth.getPrincipal();
				return user;
	        }
		}
		return null;
	}
}
