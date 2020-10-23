package lib.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AccountDto implements Serializable {
    private int id;

    private String username;
    private String password;

    private Set<Integer> eventsId = new HashSet<>();

    public AccountDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Integer> getEventsId() {
        return eventsId;
    }

    public void setEventsId(Set<Integer> eventsId) {
        this.eventsId = eventsId;
    }

    @Override
    public String toString() {
        return "ContDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", evenimente=" + eventsId +
                '}';
    }
}
