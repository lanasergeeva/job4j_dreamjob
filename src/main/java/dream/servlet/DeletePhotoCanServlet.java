package dream.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DeletePhotoCanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("id");
        File file = new File("C:\\images\\" + name + ".png");
        if (file.exists()) {
            Files.delete(file.toPath());
        }
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
