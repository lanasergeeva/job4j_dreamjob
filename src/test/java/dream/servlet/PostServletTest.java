package dream.servlet;

import dream.model.Post;
import dream.store.MemStore;
import dream.store.PsqlStore;
import dream.store.Store;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class PostServletTest {
    @Test
    public void whenCreatePost() throws IOException {
        Store store = MemStore.instOf();

        PowerMockito.mockStatic(PsqlStore.class);
        PowerMockito.when(PsqlStore.instOf()).thenReturn(store);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        PowerMockito.when(req.getParameter("id")).thenReturn("20");
        PowerMockito.when(req.getParameter("name")).thenReturn("n");
        PowerMockito.when(req.getParameter("text")).thenReturn("d");

        new PostServlet().doPost(req, resp);

        Post result = store.findByIdPost(20);
        Assert.assertThat(result.getName(), Is.is("n"));
        Assert.assertThat(result.getText(), Is.is("d"));
    }
}