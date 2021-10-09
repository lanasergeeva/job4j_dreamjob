package dream.servlet;

import dream.store.PsqlStore;
import dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteCandidateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("id");
        Store store = PsqlStore.instOf();
        store.deleteCandidate(Integer.parseInt(name));
        Path path = Paths.get("C:\\images\\" + name + ".png");
        if (Files.exists(path) || path.toFile().exists()) {
            Files.delete(path);
        }
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

}
