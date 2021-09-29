package dream.store;

import dream.model.NewData;
import org.apache.commons.dbcp2.BasicDataSource;
import dream.model.Candidate;
import dream.model.Post;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;


public class PsqlStore implements Store {
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
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public void save(NewData newData) {
        if (newData.getId() == 0) {
            create(newData);
        } else {
            update(newData.getId(), newData);
        }
    }


    private void create(NewData newData) {
        try (Connection cn = pool.getConnection()) {
            if (newData.getClass().equals(Post.class)) {
                PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name) VALUES (?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, newData.getName());
                ps.execute();
                try (ResultSet id = ps.getGeneratedKeys()) {
                    if (id.next()) {
                        newData.setId(id.getInt(1));
                    }
                }
            }
            if (newData.getClass().equals(Candidate.class)) {
                PreparedStatement ps = cn.prepareStatement("INSERT INTO candidate(name) VALUES (?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, newData.getName());
                ps.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean update(int id, NewData newData) {
        boolean result = false;
        try (Connection cn = pool.getConnection()) {
            if (newData.getClass().equals(Post.class)) {
                PreparedStatement ps = cn.prepareStatement("update post set name = ? where id = ? ",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, newData.getName());
                ps.setInt(2, id);
                result = ps.executeUpdate() > 0;
            }
            if (newData.getClass().equals(Candidate.class)) {
                PreparedStatement ps = cn.prepareStatement("update post set name = ? where id = ? ",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, newData.getName());
                ps.setInt(2, id);
                result = ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
                            resultSet.getString("name")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                            resultSet.getString("name")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }
}
