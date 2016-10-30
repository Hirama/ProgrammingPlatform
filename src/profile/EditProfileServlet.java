package profile;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrey on 30.10.2016.
 */
@WebServlet("/EditProfileServlet")
public class EditProfileServlet extends HttpServlet {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static Logger logger = LoggerFactory.getLogger(EditProfileServlet.class);
    private static UserPOJO userPOJOUpdated = new UserPOJO();
    private UserWorker userWorker = new UserWorker();

    public EditProfileServlet() throws SQLException, ClassNotFoundException {
    }

    /**
     * Check if email is correct
     *
     * @param emailStr
     * @return true if email is valid
     */
    private static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * Update user information in db
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userPOJOUpdated.setUserEmail(req.getParameter("email"));
        userPOJOUpdated.setUserPassword(req.getParameter("pwd"));
        userPOJOUpdated.setUserName(req.getParameter("username"));
        HttpSession session = req.getSession();

        try {
            if (userPOJOUpdated.getUserName().length() > 4 & validate(userPOJOUpdated.getUserEmail()) & !userPOJOUpdated.getUserPassword().isEmpty()) {
                userWorker.updateUserProfile(userPOJOUpdated, session.getAttribute("useremail").toString());
                session.setAttribute("useremail", userPOJOUpdated.getUserEmail());
                //setting session to expiry in 20 mins
                session.setMaxInactiveInterval(20 * 60);
                Cookie userName = new Cookie("username", URLEncoder.encode(userPOJOUpdated.getUserName(), "UTF-8"));
                userName.setMaxAge(20 * 60);
                resp.addCookie(userName);
                resp.sendRedirect("profile.jsp");
                logger.info("Entered user parameters are correct");
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/editprofile.jsp");
                PrintWriter out = resp.getWriter();
                out.println("<font color = red>Data is wrong try again!</font>");
                rd.forward(req, resp);
                logger.info("Entered user parameters are wrong");
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.trace("SQL exception ", e.toString());
        }
    }
}
