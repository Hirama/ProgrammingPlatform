package filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by Andrey on 29.10.2016.
 */
@WebFilter("/RequestLoggingFilter")
public class RequestLoggingFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    /**
     * Initialization block for logger servlet
     *
     * @param fConfig
     * @throws ServletException
     */
    public void init(FilterConfig fConfig) throws ServletException {
        logger.info("RequestLoggingFilter initialized");
    }

    /**
     * Log all request
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Enumeration<String> params = req.getParameterNames();
        while (params.hasMoreElements()) {
            String name = params.nextElement();
            String value = request.getParameter(name);
            logger.info(req.getRemoteAddr() + "::Request Params::{" + name + "=" + value + "}");
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                logger.info(req.getRemoteAddr() + "::Cookie::{" + cookie.getName() + "," + cookie.getValue() + "}");
            }
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
