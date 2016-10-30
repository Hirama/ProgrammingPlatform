package logout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Andrey on 29.10.2016.
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(LogoutServlet.class);

    /**
     * Delete cookies and session data
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    System.out.println("JSESSIONID=" + cookie.getValue());
                    break;
                }
            }
        }
        HttpSession session = request.getSession(false);
        session.invalidate();
        logger.info("User = " + session.getAttribute("username") +" exit from system!");
        response.sendRedirect("login.html");

    }
}
