package dream.store;

import dream.model.Candidate;
import dream.model.City;
import dream.model.Post;
import dream.model.User;

import java.sql.SQLException;
import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    Collection<Candidate> findAllCandidatesInDay();

    Collection<Post> findAllPostsInDay();

    Collection<City> findCities();

    void savePost(Post post);

    void saveCandidate(Candidate candidate);

    Post findByIdPost(int id);

    Candidate findByIdCandidate(int id);

    void deletePost(int id);

    void deleteCandidate(int id);

    void saveUser(User user) throws SQLException;

    User findByEmailUser(String name);


}
