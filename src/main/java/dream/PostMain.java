package dream;

import dream.store.PsqlStore;
import dream.store.Store;

public class PostMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        System.out.println(store.findAllCandidates());
    }
}
