package dream.servlet;

import dream.model.User;
import dream.store.PsqlStore;
import dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Store store = PsqlStore.instOf();
        User user = new User(0, name, email, password);
        try {
            store.saveUser(user);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } catch (SQLException throwables) {
            req.setAttribute("error", "Пользователь с такой электронной почтой уже есть");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}



