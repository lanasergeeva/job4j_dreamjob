package dream.servlet;

import dream.model.City;
import dream.model.Post;
import dream.model.User;
import dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", new ArrayList<>(PsqlStore.instOf().findAllPosts()));
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = null;
        if (req.getSession() != null) {
            user = (User) req.getSession().getAttribute("user");
        }
        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().savePost(
                new Post(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name"),
                        req.getParameter("text"),
                        new City(Integer.parseInt(req.getParameter("city_id"))),
                        user));
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}
