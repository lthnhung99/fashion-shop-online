package controller.admin;

import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
@WebServlet(name="CustomerManagerServlet",urlPatterns = "/customerManager")
public class CustomerManagerServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {

        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user.getRole().equalsIgnoreCase("true")) {
                List<User> user1 = userDAO.getUser();
                request.setAttribute("user", user1);
                request.getRequestDispatcher("admin/customer.jsp").forward(request, response);
            } else {
                response.sendRedirect("user?action=login");
            }
        } catch (Exception e) {
            response.sendRedirect("404.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "update":
                updateUser(request, response);
                break;
            default:
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String user_id = request.getParameter("user_id");
            String isAdmin = request.getParameter("permission");
            int id = Integer.parseInt(user_id);
            UserDAO dao = new UserDAO();
            dao.setAdmin(id, isAdmin);
            response.sendRedirect("customerManager");
        } catch (Exception e) {
            response.sendRedirect("404.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}
