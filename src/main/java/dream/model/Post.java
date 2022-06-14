package dream.model;

import java.util.Objects;

public class Post {
    private int id;
    private String name;
    private String text;
    private User user;
    private City city;
    private String date;

    public Post() {
    }

    public Post(int id, String name, String text, City city) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.city = city;
    }

    public Post(int id, String name, String text, City city, User user, String date) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.user = user;
        this.city = city;
        this.date = date;
    }

    public Post(int id, String name, String text, City city, User user) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.user = user;
        this.city = city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public City getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", text='" + text + '\''
                + ", user=" + user
                + ", city=" + city
                + '}';
    }
}
