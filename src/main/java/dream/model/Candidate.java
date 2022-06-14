package dream.model;

import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private String position;

    private String skills;
    private City city;
    private User user;

    private String date;

    public Candidate() {
    }

    public Candidate(int id, String name, String position, String skills, City city) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.skills = skills;
        this.city = city;
    }


    public Candidate(int id, String name, String position, String skills, City city, User user) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.skills = skills;
        this.city = city;
        this.user = user;
    }

    public Candidate(int id, String name, String position, String skills, City city, User user, String date) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.skills = skills;
        this.city = city;
        this.user = user;
        this.date = date;
    }

    public String getDate() {
        return date;
    }


    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
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
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Candidate{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", position='" + position + '\''
                + ", skills='" + skills + '\''
                + ", city=" + city
                + ", user=" + user
                + '}';
    }
}

