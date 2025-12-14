import Game.Game;
import Game.User;
import board.Board;
import board.Position;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import pieces.Piece;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Main{
    List<User> users = new ArrayList<>();
    Map<Integer, Game> games = new HashMap<>();
    User currentUser;
    Scanner sc = new Scanner(System.in);

    public void read(){
        try {
            this.games = JsonReaderUtil.readGamesAsMap(Paths.get("games.json"));
            this.users = JsonReaderUtil.readUsers(Paths.get("accounts.json"), games);
            System.out.println("Successfully loaded " + users.size() + " users and " + games.size() + " games.");
        } catch (IOException | ParseException e){
            System.out.println(e.getMessage());
        }
    }

    public void write(){
        JSONArray usersList = new JSONArray();
        for(User u : users){
            JSONObject userObj = new JSONObject();
            userObj.put("email", u.getEmail());
            userObj.put("password", u.getPassword());
            userObj.put("points", u.getPoints());

            JSONArray gameIds = new JSONArray();
            for(Game g : u.getActiveGames()){
                gameIds.add(g.getId());
            }
            userObj.put("games", gameIds);

            usersList.add(userObj);
        }

        try (FileWriter file = new FileWriter("accounts.json")) {
            file.write(usersList.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray gamesList = new JSONArray();

        for (Game g : games.values()) {
            JSONObject gameObj = new JSONObject();
            gameObj.put("id", g.getId());
            gameObj.put("currentPlayerColor", g.getCurrentPlayer().getColor().toString());

            JSONArray playersArr = new JSONArray();
            gameObj.put("players", playersArr);

            JSONArray boardArr = new JSONArray();
            gameObj.put("board", boardArr);

            JSONArray movesArr = new JSONArray();
            gameObj.put("moves", movesArr);

            gamesList.add(gameObj);
        }

        try (FileWriter file = new FileWriter("games.json")) {
            file.write(gamesList.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Data saved successfully!");
    }

    public User login(String email, String password){
        for(User user : users){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                this.currentUser = user;
                return user;
            }
        }
        return null;
    }

    public User newAccount(String email, String password){
        User user = new User(email, password);
        users.add(user);
        currentUser = user;
        return user;
    }

    public void run(){
        boolean isRunning = true;

        System.out.println("Welcome to the chess!");

        while(isRunning){

            if (currentUser == null){
                System.out.println("\n--- LOGIN MENU ---");
                System.out.println("1. Login");
                System.out.println("2. Register (New Account)");
                System.out.println("3. Exit");
                System.out.print("Choose: ");
            }

            String choice = sc.nextLine();

        }
    }

    public static void main(String[] args){
        Board board = new Board();
        board.initialize();

    }
}