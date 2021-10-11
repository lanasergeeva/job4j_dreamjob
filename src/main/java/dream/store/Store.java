package dream.store;

import dream.model.Candidate;
import dream.model.Post;
import dream.model.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void savePost(Post post);

    void saveCandidate(Candidate candidate);

    Post findByIdPost(int id);

    Candidate findByIdCandidate(int id);

    void deletePost(int id);

    void deleteCandidate(int id);

    void saveUser(User user);

    User findByEmailUser(String name);


}
