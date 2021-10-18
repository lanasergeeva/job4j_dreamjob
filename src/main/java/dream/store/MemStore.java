package dream.store;

import dream.model.Candidate;
import dream.model.City;
import dream.model.Post;
import dream.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {

    private static final AtomicInteger POSTID = new AtomicInteger(4);
    private static final AtomicInteger CANDID = new AtomicInteger(4);
    private static final AtomicInteger USID = new AtomicInteger(4);
    private static final MemStore INST = new MemStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final Set<City> cities = new HashSet<>();

    public static MemStore instOf() {
        return INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }


    @Override
    public Collection<Candidate> findAllCandidatesInDay() {
        return candidates.values();
    }

    @Override
    public Collection<Post> findAllPostsInDay() {
        return posts.values();
    }

    @Override
    public Collection<City> findCities() {
        return cities;
    }

    @Override
    public Post findByIdPost(int id) {
        return posts.get(id);
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }

    @Override
    public User findByEmailUser(String name) {
        User rsl = null;
        for (User user : users.values()) {
            if (user.getEmail().equals(name)) {
                rsl = user;
                break;
            }
        }
        return rsl;
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public void savePost(Post post) {
        if (post.getId() == 0) {
            post.setId(POSTID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }


    @Override
    public void saveUser(User user) {
        if (user.getId() == 0) {
            user.setId(USID.incrementAndGet());
        }
        users.put(user.getId(), user);
    }

    @Override
    public void deleteCandidate(int id) {
        Candidate can = findByIdCandidate(id);
        if (can != null) {
            candidates.remove(id);
        }
    }

    @Override
    public void deletePost(int id) {
        Post post = findByIdPost(id);
        if (post != null) {
            posts.remove(id);
        }
    }
}
