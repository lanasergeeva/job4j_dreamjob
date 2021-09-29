package dream;

import dream.model.Candidate;
import dream.model.Post;
import dream.store.PsqlStore;
import dream.store.Store;

public class PostMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Python Junior"));
        store.save(new Post(0, "Abbap разработчик"));
        store.save(new Candidate(0, "Ivan Olegovich"));
        store.save(new Candidate(0, "Mark Petrov"));
        System.out.println(store.findAllPosts());
        System.out.println(store.findAllCandidates());
        System.out.println(store.findByIdPost(3));
        System.out.println(store.findByIdCandidate(1));

    }
}
