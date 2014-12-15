package br.com.lelak.teste.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import br.com.lelak.teste.persistence.ConnectionManager;
import br.com.lelak.teste.util.FileUtils;

@WebFilter("/*")
public class FrontControllerFilter implements Filter{


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		try {
			String id = getSessionID(request);
			FileUtils.setToken(id);
			
			String source = request.getParameter("javax.faces.source");
			if(source == null){
				filterChain.doFilter(request, response);
				return;
			}
			
			ConnectionManager.beginTransaction();
			filterChain.doFilter(request, response);
			ConnectionManager.commit();
		} catch(Exception e){
			e.printStackTrace();
			ConnectionManager.rollback();
		}finally {
			ConnectionManager.close();
		}
	}

	private String getSessionID(ServletRequest request) {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		return servletRequest.getSession().getId();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
