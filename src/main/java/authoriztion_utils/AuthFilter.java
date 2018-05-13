package authoriztion_utils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String name = (String) httpServletRequest.getSession().getAttribute("name");
        String path=httpServletRequest.getServletPath();
        if (name != null) {
            if (path.equals("/"))
                ((HttpServletResponse) servletResponse).sendRedirect("all");
            else filterChain.doFilter(servletRequest, servletResponse);

        } else if (!(path.equals("/")||path.equals("/authVk")||path.equals("/home")||path.equals("/authGit")))
            followError(servletRequest,servletResponse,"401, You are not authorized");
        else filterChain.doFilter(servletRequest, servletResponse);
        // followError(servletRequest,servletResponse,"Ti pidr");
    }

    public void followError(ServletRequest request, ServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher("Error.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("destroy filter");
    }
}
