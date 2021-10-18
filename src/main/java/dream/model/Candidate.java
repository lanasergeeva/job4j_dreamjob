package dream.model;

import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private String position;
    private City city;


    public Candidate(int id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public Candidate(int id, String name, String position, City city) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.city = city;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id && Objects.equals(name, candidate.name) && Objects.equals(position, candidate.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, position);
    }

    @Override
    public String toString() {
        return "Candidate{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", position='" + position + '\''
                + ", city=" + city
                + '}';
    }
}

