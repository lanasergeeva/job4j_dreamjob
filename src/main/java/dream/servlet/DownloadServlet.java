package dream.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("id");
        File file = new File("C:\\images\\" + name + ".png");
        if (!file.exists()) {
            file = new File("C:\\images\\anon.jpg");
        }
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        try (FileInputStream stream = new FileInputStream(file)) {
            resp.getOutputStream().write(stream.readAllBytes());
        }
    }
}
