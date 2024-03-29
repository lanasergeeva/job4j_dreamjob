package dream.servlet;

import dream.model.Candidate;
import dream.model.City;
import dream.model.User;
import dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = null;
        if (req.getSession() != null) {
            user = (User) req.getSession().getAttribute("user");
        }
        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().saveCandidate(
                new Candidate(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name"),
                        req.getParameter("position"),
                        req.getParameter("skills"),
                        new City(Integer.parseInt(req.getParameter("city_id"))),
                        user));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
