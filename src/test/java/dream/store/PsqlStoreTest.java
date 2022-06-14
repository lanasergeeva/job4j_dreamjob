package dream.store;

import dream.model.Candidate;
import dream.model.City;
import dream.model.Post;

import dream.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class PsqlStoreTest {

    private Store store;
    private Post firstPost;
    private Post secondPost;
    private Candidate firstCandidate;
    private Candidate secondCandidate;
    private User user;

    @Before
    public void init() throws SQLException {
        store = PsqlStore.instOf();
        City city = new City(0, "Moscow");
        store.saveCity(city);
        user = new User(0, "Ivan", "ivan1569@gmail.com", "123456");
        store.saveUser(user);
        firstPost = new Post(0, "Java Job Test", "Some text", city, user);
        secondPost = new Post(0, "Update", "Update some text", city, user);
        firstCandidate = new Candidate(0, "Oleg Test",
                "Junior", "Skills", city, user);
        secondCandidate = new Candidate(0, "Update Oleg",
                "Update Position", "New", city, user);
    }

    @After
    public void drop() {
        store.deleteAllForTest();
    }

    @Test
    public void whenCreatePost() {
        store.savePost(firstPost);
        Post postInDb = store.findByIdPost(firstPost.getId());
        assertThat(postInDb.getName(), is("Java Job Test"));
        assertThat(postInDb.getText(), is("Some text"));
        assertThat(postInDb.getCity().getCity(), is("Moscow"));
        assertThat(postInDb.getUser().getName(), is("Ivan"));
    }

    @Test
    public void whenUpdatePost() {
        store.savePost(firstPost);
        int id = firstPost.getId();
        secondPost.setId(id);
        store.savePost(secondPost);
        Post postInDb = store.findByIdPost(id);
        assertThat(postInDb.getName(), is("Update"));
        assertThat(postInDb.getText(), is("Update some text"));
    }

    @Test
    public void whenDeletePost() {
        store.savePost(firstPost);
        int id = firstPost.getId();
        store.deletePost(id);
        assertThat(store.findByIdPost(id), is(nullValue()));
    }

    @Test
    public void whenFindAllPost() {
        store.savePost(firstPost);
        store.savePost(secondPost);
        assertThat(store.findAllPosts(), is(List.of(firstPost, secondPost)));
    }

    @Test
    public void whenFindAllPostByUserId() {
        store.savePost(firstPost);
        store.savePost(secondPost);
        assertThat(store.findAllPostByUserIdPost(user.getId()),
                is(List.of(firstPost, secondPost)));
    }

    @Test
    public void whenCreateCandidate() {
        store.saveCandidate(firstCandidate);
        Candidate postInDb = store.findByIdCandidate(firstCandidate.getId());
        assertThat(postInDb.getName(), is("Oleg Test"));
        assertThat(postInDb.getSkills(), is("Skills"));
        assertThat(postInDb.getPosition(), is("Junior"));
        assertThat(postInDb.getCity().getCity(), is("Moscow"));
        assertThat(postInDb.getUser().getName(), is("Ivan"));
    }

    @Test
    public void whenUpdateCandidate() {
        store.saveCandidate(firstCandidate);
        int id = firstCandidate.getId();
        secondCandidate.setId(id);
        store.saveCandidate(secondCandidate);
        Candidate postInDb = store.findByIdCandidate(id);
        assertThat(postInDb.getName(), is("Update Oleg"));
        assertThat(postInDb.getPosition(), is("Update Position"));
        assertThat(postInDb.getSkills(), is("New"));
    }

    @Test
    public void whenDeleteCandidate() {
        store.saveCandidate(firstCandidate);
        int id = firstCandidate.getId();
        store.deleteCandidate(id);
        assertThat(store.findByIdCandidate(id), is(nullValue()));
    }

    @Test
    public void whenFindAllCandidate() {
        store.saveCandidate(firstCandidate);
        store.saveCandidate(secondCandidate);
        assertThat(store.findAllCandidates(), is(List.of(firstCandidate, secondCandidate)));
    }

    @Test
    public void whenFindAllCandidateByUserId() {
        store.saveCandidate(firstCandidate);
        store.saveCandidate(secondCandidate);
        assertThat(store.findByUserIdCandidate(user.getId()), is(List.of(firstCandidate, secondCandidate)));
    }
}
