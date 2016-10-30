package login;

import models.UserPOJO;
import models.UserWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;

/**
 * Created by Andrey on 29.10.2016.
 */

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static UserPOJO userPOJOTemp = new UserPOJO();
    private UserWorker userWorker = new UserWorker();
    private static Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    public LoginServlet() throws SQLException, ClassNotFoundException {
    }

    /**
     * Servlet for which check if email and password exists in db
     * Create Cookies
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userPOJOTemp.setUserEmail(request.getParameter("email"));
        userPOJOTemp.setUserPassword(request.getParameter("pwd"));

        try {
            if (userWorker.checkIfCredentialsCorrect(userPOJOTemp)) {

                UserPOJO userPOJO = userWorker.getUser(userPOJOTemp.getUserEmail());
                HttpSession session = request.getSession();
                session.setAttribute("useremail", userPOJO.getUserEmail());
                session.setMaxInactiveInterval(20 * 60);
                Cookie userName = new Cookie("username", URLEncoder.encode(userPOJO.getUserName(), "UTF-8"));
                userName.setMaxAge(20 * 60);
                response.addCookie(userName);
                response.sendRedirect("profile.jsp");
                logger.info("User login success");
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                PrintWriter out = response.getWriter();
                out.println("<font color=red>Either user name or password is wrong.</font>");
                rd.include(request, response);
                logger.info("User login fail");
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.trace("Exception ", e.toString());
        }
    }
}
