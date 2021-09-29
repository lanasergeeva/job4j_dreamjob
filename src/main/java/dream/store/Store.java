package dream.store;

import dream.model.Candidate;
import dream.model.NewData;
import dream.model.Post;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(NewData newData);

    Post findByIdPost(int id);

    Candidate findByIdCandidate(int id);
}
