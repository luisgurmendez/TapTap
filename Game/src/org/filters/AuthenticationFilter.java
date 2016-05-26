package org.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.manager.SessionManager;

public class AuthenticationFilter implements Filter{
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
        boolean sessionExists = SessionManager.getInstance().containsUser(request.getSession().getId());
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        // There's not a user logged in with that Session.
        if (!sessionExists){
        	response.sendRedirect("/Game/loginPage.html");
        }
        chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}
	
}
