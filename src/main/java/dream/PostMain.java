package dream;

import dream.model.Candidate;
import dream.model.Post;
import dream.store.PsqlStore;
import dream.store.Store;

public class PostMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        /*store.savePost(new Post(0, "Python Junior"));
        store.savePost(new Post(0, "Abbap разработчик"));
        store.saveCandidate(new Candidate(0, "Ivan Olegovich"));
        store.saveCandidate(new Candidate(0, "Mark Petrov"));*/
        System.out.println(store.findByIdPost(150));
       /* System.out.println(store.findAllPosts());
        System.out.println(store.findAllCandidates());
        System.out.println(store.findByIdPost(3));
        System.out.println(store.findByIdCandidate(1));*/

    }
}
