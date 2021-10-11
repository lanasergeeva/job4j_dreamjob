package dream.servlet;

import dream.model.User;
import dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = PsqlStore.instOf().findByEmailUser(email);
        if (user == null || !user.getPassword().equals(password)) {
            req.setAttribute("error", "Вы ввели некорректный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
        HttpSession sc = req.getSession();
        sc.setAttribute("user", user);
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}
