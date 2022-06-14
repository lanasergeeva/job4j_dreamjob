package dream.servlet;

import dream.model.User;
import dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OwnServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (User) req.getSession().getAttribute("user");
        int id = user.getId();
        req.setAttribute("candidates", PsqlStore.instOf().findByUserIdCandidate(id));
        req.setAttribute("posts", PsqlStore.instOf().findAllPostByUserIdPost(id));
        req.setAttribute("user", user);
        req.getRequestDispatcher("own.jsp").forward(req, resp);
    }
}
