package dream.store;

import dream.model.Candidate;
import dream.model.City;
import dream.model.Post;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class PsqlStoreTest  {

    @Test
    public void whenCreatePost() {
        Store store = PsqlStore.instOf();
        Post post = new Post(0, "Java Job Test", "Some text");
        store.savePost(post);
        Post postInDb = store.findByIdPost(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        Store store = PsqlStore.instOf();
        Post post = new Post(0, "Java Job Test", "Some text");
        store.savePost(post);
        int id = store.findByNamePost(post.getName()).get(0).getId();
        Post post2 = new Post(id, "Update", "Update some text");
        store.savePost(post2);
        Post postInDb = store.findByIdPost(id);
        assertThat(postInDb.getName(), is("Update"));
    }

    @Test
    public void whenDeletePost() {
        Store store = PsqlStore.instOf();
        Post post = new Post(0, "Java Job Test", "Some text");
        store.savePost(post);
        int id = store.findByNamePost(post.getName()).get(0).getId();
        store.deletePost(id);
        assertThat(store.findByIdPost(id), is(nullValue()));
    }


    @Test
    public void whenCreateCandidate() {
        Store store = PsqlStore.instOf();
        City city = new City(0, "Alushta");
        store.saveCity(city);
        Candidate candidate = new Candidate(0, "Oleg Test", "Junior", city);
        store.saveCandidate(candidate);
        Candidate postInDb = store.findByIdCandidate(candidate.getId());
        assertThat(postInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdateCandidate() {
        Store store = PsqlStore.instOf();
        City city = new City(0, "Alushta");
        store.saveCity(city);
        Candidate candidate = new Candidate(0, "Oleg Test", "Junior", city);
        store.saveCandidate(candidate);
        int id = store.findByNameCandidate(candidate.getName()).get(0).getId();
        Candidate update = new Candidate(id, "Update Oleg", "Junior", city);
        store.saveCandidate(update);
        Candidate postInDb = store.findByIdCandidate(id);
        assertThat(postInDb.getName(), is("Update Oleg"));
    }

    @Test
    public void whenDeleteCandidate() {
        Store store = PsqlStore.instOf();
        City city = new City(0, "Alushta");
        store.saveCity(city);
        Candidate candidate = new Candidate(0, "Oleg Test", "Junior", city);
        store.saveCandidate(candidate);
        int id = store.findByNameCandidate(candidate.getName()).get(0).getId();
        store.deleteCandidate(id);
        assertThat(store.findByIdCandidate(id), is(nullValue()));
    }
}