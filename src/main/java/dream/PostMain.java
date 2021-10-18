package dream;

import dream.model.Candidate;
import dream.model.Post;
import dream.model.User;
import dream.store.PsqlStore;
import dream.store.Store;

public class PostMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        System.out.println(store.findAllCandidates());
    }
}
