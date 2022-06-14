package dream.store;

import dream.model.Candidate;
import dream.model.City;
import dream.model.Post;
import dream.model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class PsqlStore implements Store {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MM yyyy HH:mm");

    private static final String QUERY_CANDIDATES = "select c.id as c_id, c.name as c_name, "
            + " c.position as c_pos, c.skills as c_sk, c.created_date as c_date, "
            + " cy.id as cy_id, cy.name as cy_name, u.id as u_id, u.name as u_name, u.email as u_em, "
            + " u.password as u_pas  from candidates c join users u on c.user_id = u.id "
            + "join city cy on cy.id = c.city_id ";

    private static final String QUERY_POSTS =
            "select p.id as p_id, p.name as p_name, p.description as p_desc, p.created_date as p_date, "
                    + " cy.id as cy_id, cy.name as cy_name, u.id as u_id, u.name as u_name, u.email as u_em, "
                    + " u.password as u_pas from post p join users u on p.user_id = u.id join city cy "
                    + "on cy.id = p.city_id ";

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(PsqlStore.class.getClassLoader()
                                .getResourceAsStream("db.properties"))
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }


    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }


    @Override
    public Collection<City> findCities() {
        List<City> city = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM city order by name")
        ) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    city.add(getCityFromResultSet(resultSet));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findCities ", e);
        }
        return city;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(QUERY_POSTS)
        ) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(getPostFromResultSet(resultSet));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findAllPosts ", e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(QUERY_CANDIDATES)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(getCandidateFromResultSet(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findAllCandidates ", e);
        }
        return candidates;
    }

    @Override
    public Collection<Post> findAllPostsInDay() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(String.format("%s%s", QUERY_POSTS,
                     " where p.created_date between now()- interval '1 DAY' and now() "))) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(getPostFromResultSet(resultSet));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findAllPostsInDay ", e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidatesInDay() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(String.format("%s%s", QUERY_CANDIDATES,
                     "where c.created_date between now() - interval '1 DAY' and now() "))) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    candidates.add(getCandidateFromResultSet(resultSet));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findAllCandidatesInDay ", e);
        }
        return candidates;
    }

    @Override
    public void savePost(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post.getId(), post);
        }
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate.getId(), candidate);
        }
    }

    public void saveCity(City city) {
        if (city.getId() == 0) {
            create(city);
        }
    }

    @Override
    public void saveUser(User user) throws SQLException {
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user.getId(), user);
        }
    }

    private void create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO post(name, description, user_id, city_id) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getText());
            ps.setInt(3, post.getUser().getId());
            ps.setInt(4, post.getCity().getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (SQLException throwables) {
            LOG.error("SQL Exception in create Post ", throwables);
        }
    }

    private void create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO candidates(name, position, skills, city_id, user_id) "
                             + "VALUES (?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPosition());
            ps.setString(3, candidate.getSkills());
            ps.setInt(4, candidate.getCity().getId());
            ps.setInt(5, candidate.getUser().getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (SQLException throwables) {
            LOG.error("SQL Exception in create Candidate ", throwables);
        }
    }

    private void create(User user) throws SQLException {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users(name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        }
    }

    private void create(City city) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO city(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, city.getCity());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    city.setId(id.getInt(1));
                }
            } catch (Exception e) {
                LOG.error("Exception in create City ", e);
            }
        } catch (SQLException throwables) {
            LOG.error("SQLException in create City ", throwables);
        }
    }

    private boolean update(int id, Post post) {
        boolean result = false;
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(
                    "update post set (name, description, city_id) = (?, ?, ?) where id = ? ");
            ps.setString(1, post.getName());
            ps.setString(2, post.getText());
            ps.setInt(3, post.getCity().getId());
            ps.setInt(4, id);
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOG.error("Exception in update Post ", e);
        }
        return result;
    }

    private boolean update(int id, Candidate candidate) {
        boolean result = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "update candidates set (name, position, skills, city_id) = (?, ?, ?, ?) where id = ? ")) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPosition());
            ps.setString(3, candidate.getSkills());
            ps.setInt(4, candidate.getCity().getId());
            ps.setInt(5, id);
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOG.error("Exception in update Candidate ", e);
        }
        return result;
    }

    private boolean update(int id, User user) {
        boolean result = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "update users set (name, email, password) = (?, ?, ?) where id = ? ")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, id);
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOG.error("Exception in update User ", e);
        }
        return result;
    }

    @Override
    public User findByEmailUser(String name) {
        User rsl = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from users where email like ?")) {
            ps.setString(1, name);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    rsl = getUserFromResultSet(resultSet);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findByEmailUser", e);
        }
        return rsl;
    }

    @Override
    public Post findByIdPost(int id) {
        Post rsl = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(String.format("%s%s", QUERY_POSTS, "where p.id = ? "))) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    rsl = getPostFromResultSet(resultSet);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findByIdPost ", e);
        }
        return rsl;
    }

    @Override
    public Collection<Post> findAllPostByUserIdPost(int id) {
        List<Post> rsl = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(String.format("%s%s", QUERY_POSTS, "where u.id = ? "))) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rsl.add(getPostFromResultSet(resultSet));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findAllPostByUserIdPost ", e);
        }
        return rsl;
    }


    @Override
    public Candidate findByIdCandidate(int id) {
        Candidate rsl = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(String.format("%s%s", QUERY_CANDIDATES, "where c.id = ? "))) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    rsl = getCandidateFromResultSet(resultSet);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findByIdCandidate ", e);
        }
        return rsl;
    }

    @Override
    public Collection<Candidate> findByUserIdCandidate(int userId) {
        List<Candidate> rsl = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(String.format("%s%s", QUERY_CANDIDATES, "where u.id = ? "))) {
            ps.setInt(1, userId);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rsl.add(getCandidateFromResultSet(resultSet));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findByUserIdCandidate ", e);
        }
        return rsl;
    }

    private Post getPostFromResultSet(ResultSet resultSet) throws SQLException {
        return new Post(
                resultSet.getInt("p_id"),
                resultSet.getString("p_name"),
                resultSet.getString("p_desc"),
                new City(resultSet.getInt("cy_id"), resultSet.getString("cy_name")),
                new User(resultSet.getInt("u_id"), resultSet.getString("u_name"),
                        resultSet.getString("u_em"), resultSet.getString("u_pas")),
                formatDate(resultSet.getTimestamp("p_date")));
    }


    private Candidate getCandidateFromResultSet(ResultSet resultSet) throws SQLException {
        return new Candidate(
                resultSet.getInt("c_id"),
                resultSet.getString("c_name"),
                resultSet.getString("c_pos"),
                resultSet.getString("c_sk"),
                new City(resultSet.getInt("cy_id"), resultSet.getString("cy_name")),
                new User(resultSet.getInt("u_id"), resultSet.getString("u_name"),
                        resultSet.getString("u_em"), resultSet.getString("u_pas")),
                formatDate(resultSet.getTimestamp("c_date")));
    }

    private String formatDate(Timestamp timestamp) {
        LocalDateTime localeDateTime = timestamp.toLocalDateTime();
        return localeDateTime.format(FORMATTER);
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"));
    }

    private City getCityFromResultSet(ResultSet resultSet) throws SQLException {
        return new City(resultSet.getInt("id"),
                resultSet.getString("name"));
    }

    @Override
    public void deleteCandidate(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement =
                     cn.prepareStatement("delete from candidates where id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            LOG.error("Exception in deleteCandidate ", e);
        }
    }

    @Override
    public void deletePost(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement =
                     cn.prepareStatement("delete from post where id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            LOG.error("Exception in deletePost ", e);
        }
    }

    private void deleteAllPostForTest() {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement =
                     cn.prepareStatement("delete from post")) {
            statement.execute();
        } catch (Exception e) {
            LOG.error("Exception in deleteAllPostForTest ", e);
        }
    }

    private void deleteAllCandidatesForTest() {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement =
                     cn.prepareStatement("delete from candidates")) {
            statement.execute();
        } catch (Exception e) {
            LOG.error("Exception in deleteAllCandidatesForTest ", e);
        }
    }

    private void deleteAllUsersForTest() {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement =
                     cn.prepareStatement("delete from users")) {
            statement.execute();
        } catch (Exception e) {
            LOG.error("Exception in deleteAllUsersForTest ", e);
        }
    }

    private void deleteAllCitiesForTest() {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement =
                     cn.prepareStatement("delete from city")) {
            statement.execute();
        } catch (Exception e) {
            LOG.error("Exception in deleteAllCitiesForTest ", e);
        }
    }

    public void deleteAllForTest() {
        deleteAllPostForTest();
        deleteAllCandidatesForTest();
        deleteAllUsersForTest();
        deleteAllCitiesForTest();
    }
}
