package dream;

import dream.model.Post;
import dream.store.PsqlStore;
import dream.store.Store;

public class PostMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "DBA"));
        store.save(new Post(0, "Web"));
        System.out.println(store.findAllPosts());
    }
}
