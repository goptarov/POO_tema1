package game;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String password;
    private List<Game> game;
    private int points;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.game = new ArrayList<>();
        this.points = 0;
    }

    public void addGame(Game game) {
        this.game.add(game);
    }

    public void removeGame(Game game) {
        this.game.remove(game);
    }

    public List<Game> getActiveGames() {
        return this.game;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
