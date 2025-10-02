package vn.iostar.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component

public class RoleInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		String uri = request.getRequestURI();

		if (session == null || session.getAttribute("role") == null) {
			response.sendRedirect("/login");
			return false;
		}

		String role = session.getAttribute("role").toString();

		if (uri.startsWith("/admin") && !"ADMIN".equalsIgnoreCase(role)) {
			response.sendRedirect("/access-denied");
			return false;
		}

		if (uri.startsWith("/user") && !(role.equalsIgnoreCase("USER") || role.equalsIgnoreCase("ADMIN"))) {
			response.sendRedirect("/access-denied");
			return false;
		}

		return true;
	}
}
