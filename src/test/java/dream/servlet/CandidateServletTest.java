package dream.servlet;

import dream.model.Candidate;
import dream.model.User;
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
public class CandidateServletTest {
    @Test
    public void whenCreateCandidate() throws IOException {
        Store store = MemStore.instOf();

        PowerMockito.mockStatic(PsqlStore.class);
        PowerMockito.when(PsqlStore.instOf()).thenReturn(store);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        PowerMockito.when(req.getParameter("id")).thenReturn("20");
        PowerMockito.when(req.getParameter("name")).thenReturn("Nikita");
        PowerMockito.when(req.getParameter("position")).thenReturn("Java Junior Developer");
        PowerMockito.when(req.getParameter("skills")).thenReturn("My skills");
        PowerMockito.when(req.getParameter("city_id")).thenReturn("2");

        new CandidateServlet().doPost(req, resp);

        Candidate result = store.findByIdCandidate(20);
        Assert.assertThat(result.getName(), Is.is("Nikita"));
        Assert.assertThat(result.getPosition(), Is.is("Java Junior Developer"));
        Assert.assertThat(result.getSkills(), Is.is("My skills"));
        Assert.assertThat(result.getCity().getId(), Is.is(2));
    }
}
