package dream.servlet;

import dream.model.User;
import dream.store.PsqlStore;
import dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = PsqlStore.instOf().findByEmailUser(email);
        if (user != null) {
            req.setAttribute("error", "Пользователь с такой электронной почтой уже есть");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
            return;
        }
        User user2 = new User(0, name, email, password);
        Store store = PsqlStore.instOf();
        store.saveUser(user2);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}



