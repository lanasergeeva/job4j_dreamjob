package dream.store;

import dream.model.Candidate;
import dream.model.City;
import dream.model.Post;
import dream.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MemStore implements Store {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MM yyyy HH:mm");

    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private static final AtomicInteger CAND_ID = new AtomicInteger(4);
    private static final AtomicInteger US_ID = new AtomicInteger(4);

    private static final AtomicInteger CITY_ID = new AtomicInteger(4);
    private static final MemStore INST = new MemStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    private static Set<City> cities = new HashSet<>();

    public static MemStore instOf() {
        Set<City> set = Set.of(new City(1, "Воронеж"),
                new City(2, "Курск"), new City(3, "Брянск"), new City(4, "Псков"));
        cities = new HashSet<>(set);
        return INST;
    }

    @Override
    public void deleteAllForTest() {
        posts.clear();
        candidates.clear();
        users.clear();
        cities.clear();
    }

    public static void main(String[] args) {
        MemStore m = MemStore.instOf();
        m.deleteAllForTest();
    }

    @Override
    public void saveCity(City city) {
        if (city.getId() == 0) {
            city.setId(CITY_ID.incrementAndGet());
        }
        cities.add(city);
    }

    @Override
    public Collection<Post> findAllPostByUserIdPost(int id) {
        return posts.values().stream()
                .filter(post -> post.getUser().getId() == id)
                .collect(Collectors.toList());
    }


    @Override
    public Collection<Candidate> findByUserIdCandidate(int userId) {
        return candidates.values().stream()
                .filter(candidate -> candidate.getUser().getId() == userId)
                .collect(Collectors.toList());
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
    public Collection<City> findCities() {
        return cities;
    }

    @Override
    public Collection<Candidate> findAllCandidatesInDay() {
        return candidates.values();
    }

    @Override
    public Collection<Post> findAllPostsInDay() {
        return posts.values().stream()
                .filter(post -> LocalDateTime.parse(post.getDate(), FORMATTER)
                        .toLocalDate().equals(LocalDate.now()))
                .collect(Collectors.toList());
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
        return users.values().stream()
                .filter(user -> user.getEmail().equals(name))
                .findAny()
                .orElse(null);
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CAND_ID.incrementAndGet());
            candidate.setDate(LocalDateTime.now().format(FORMATTER));
            candidate.setCity(cities.stream()
                    .filter(city -> city.getId() == candidate.getCity().getId())
                    .findAny()
                    .orElse(new City(9999, "Пропащинск")));
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public void savePost(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
            post.setDate(LocalDateTime.now().format(FORMATTER));
            post.setCity(cities.stream()
                    .filter(city -> city.getId() == post.getCity().getId())
                    .findAny()
                    .orElse(new City(9999, "Пропащинск")));
        }
        posts.put(post.getId(), post);
    }


    @Override
    public void saveUser(User user) {
        if (user.getId() == 0) {
            user.setId(US_ID.incrementAndGet());
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
