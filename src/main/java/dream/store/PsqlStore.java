package dream.store;

import dream.model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import dream.model.Candidate;
import dream.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;


public class PsqlStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("C:\\projects\\mvn_project\\job4j_dreamjob\\db.properties")
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
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description")));
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
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"),
                            it.getString("name"),
                            it.getString("position")));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findAllCandidates ", e);
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

    @Override
    public void saveUser(User user) {
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user.getId(), user);
        }
    }


    private void create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name, description) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getText());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            } catch (Exception e) {
                LOG.error("Exception in create Post ", e);
            }
        } catch (SQLException throwables) {
            LOG.error("SQL Exception in create Post ", throwables);
        }
    }

    private void create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO candidate(name, position) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPosition());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            } catch (Exception e) {
                LOG.error("Exception in create Candidate ", e);
            }
        } catch (SQLException throwables) {
            LOG.error("SQL Exception in create Candidate ", throwables);
        }
    }

    private void create(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO users(name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            } catch (Exception e) {
                LOG.error("Exception in create User ", e);
            }
        } catch (SQLException throwables) {
            LOG.error("SQL Exception in create User ", throwables);
        }
    }


    private boolean update(int id, Post post) {
        boolean result = false;
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("update post set (name, description) = (?, ?) where id = ? ",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getName());
            ps.setString(2, post.getText());
            ps.setInt(3, id);
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOG.error("Exception in update Post ", e);
        }
        return result;
    }

    private boolean update(int id, Candidate candidate) {
        boolean result = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("update candidate set (name, position) = (?, ?) where id = ? ",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPosition());
            ps.setInt(3, id);
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOG.error("Exception in update Candidate ", e);
        }
        return result;
    }

    private boolean update(int id, User user) {
        boolean result = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("update users set (name, email, password) = (?, ?, ?) where id = ? ",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
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
             PreparedStatement ps = cn.prepareStatement("select * from users where email like ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    rsl = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"
                            ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Post findByIdPost(int id) {
        Post rsl = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from post where id = ? ",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    rsl = new Post(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findByIdPost ", e);
        }
        return rsl;
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        Candidate rsl = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from candidate where id = ? ",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    rsl = new Candidate(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("position")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findByIdCandidate ", e);
        }
        return rsl;
    }

    @Override
    public void deleteCandidate(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement =
                     cn.prepareStatement("delete from candidate where id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
