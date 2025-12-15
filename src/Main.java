import board.ChessPair;
import board.Colors;
import board.Position;
import exceptions.InvalidCommandException;
import exceptions.InvalidMoveException;
import game.Game;
import game.Player;
import game.User;
import board.Board;
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
    Random rand = new Random();

    public void read(){
        try {
            this.games = JsonReaderUtil.readGamesAsMap(Paths.get("input/games.json"));
            this.users = JsonReaderUtil.readUsers(Paths.get("input/accounts.json"), games);
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

        try (FileWriter file = new FileWriter("input/accounts.json")) {
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
            for (Player p : g.getPlayers()) {
                JSONObject pObj = new JSONObject();
                pObj.put("email", p.getName() != null ? p.getName() : p.getName());
                pObj.put("color", p.getColor().toString());
                playersArr.add(pObj);
            }
            gameObj.put("players", playersArr);

            JSONArray boardArr = new JSONArray();
            for (ChessPair<Position, Piece> pair : g.getBoard().pieces) {
                JSONObject pieceObj = new JSONObject();
                Piece p = pair.getValue();

                pieceObj.put("type", String.valueOf(p.type()));
                pieceObj.put("color", p.getColor().toString());
                pieceObj.put("position", pair.getKey().toString());

                boardArr.add(pieceObj);
            }
            gameObj.put("board", boardArr);

            JSONArray movesArr = new JSONArray();
            for (game.Move m : g.getMoves()) {
                JSONObject moveObj = new JSONObject();
                moveObj.put("playerColor", m.getColor().toString());
                moveObj.put("from", m.getFrom().toString());
                moveObj.put("to", m.getTo().toString());

                if (m.getCaptured() != null) {
                    JSONObject capObj = new JSONObject();
                    capObj.put("type", String.valueOf(m.getCaptured().type()));
                    capObj.put("color", m.getCaptured().getColor().toString());
                    moveObj.put("captured", capObj);
                }
                movesArr.add(moveObj);
            }
            gameObj.put("moves", movesArr);

            gamesList.add(gameObj);
        }

        try (FileWriter file = new FileWriter("input/games.json")) {
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
        read();
        boolean isRunning = true;

        System.out.println("Welcome to the chess!");

        while(isRunning){

            while (currentUser == null && isRunning){
                System.out.println("--- LOGIN MENU ---");
                System.out.println("1. Login");
                System.out.println("2. Register (New Account)");
                System.out.println("3. Exit");
                System.out.print("Choose: ");

                String choice = sc.nextLine();
                String email;
                String password;

                if (choice.equals("1")){
                    System.out.print("Enter email: ");
                    email = sc.nextLine();
                    System.out.print("Enter password: ");
                    password = sc.nextLine();
                    currentUser = login(email, password);

                    if (currentUser != null)
                        System.out.println("Login successful!");
                    else{
                        System.out.println("Invalid email or password!");
                    }

                }
                else if (choice.equals("2")){
                    System.out.print("Enter email: ");
                    email = sc.nextLine();
                    System.out.print("Enter password: ");
                    password = sc.nextLine();
                    newAccount(email, password);
                }
                else if (choice.equals("3")){
                    isRunning = false;
                    break;
                }
                else{
                    System.out.println("Invalid choice!");
                }
            }

            if (isRunning && currentUser != null) {
                System.out.println("Welcome " + currentUser.getEmail());

                while(currentUser != null) {
                    System.out.println("--- MAIN MENU ---");
                    System.out.println("1. New Game");
                    System.out.println("2. Load Game");
                    System.out.println("3. Log Out");
                    System.out.print("Choose: ");

                    String choice = sc.nextLine();
                    if (choice.equals("1")) {
                        System.out.print("Enter name: ");
                        String playerName = sc.nextLine();
                        System.out.print("Choose color(White/Black): ");
                        String colorAsString = sc.nextLine();

                        Colors playerColor = Colors.WHITE;
                        if (colorAsString.equalsIgnoreCase("BLACK")) playerColor = Colors.BLACK;

                        Colors computerColor = (playerColor.equals(Colors.WHITE)) ? Colors.BLACK : Colors.WHITE;

                        Player human = new Player(playerName, playerColor);
                        Player computer = new Player("Computer", computerColor);

                        int id = this.games.size() + 1;

                        Board board = new Board();

                        Game game = new Game(id, human, computer, board);
                        if (playerColor == Colors.BLACK)
                            game = new Game(id, computer, human, board);

                        game.start();
                        currentUser.addGame(game);
                        games.put(id, game);

                        playGame(game);
                        currentUser.setPoints(currentUser.getPoints() + human.getPoints());
                    }
                    else if (choice.equals("2")) {
                        List<Game> activeGames = currentUser.getActiveGames();
                        if (activeGames.isEmpty()) {
                            System.out.println("You have no active games!");
                            break;
                        }

                        System.out.println("Choose which game to load:");
                        for(Game g : currentUser.getActiveGames()){
                            System.out.println(g.getId());
                        }
                        try {
                            int input = Integer.parseInt(sc.nextLine().trim());

                            Game selectedGame = null;
                            for (Game g : activeGames) {
                                if (g.getId() == input) {
                                    selectedGame = g;
                                    break;
                                }
                            }
                            if (selectedGame != null) {
                                selectedGame.resume();
                                playGame(selectedGame);
                            } else {
                                System.out.println("Game ID not found!");
                            }
                        } catch (NumberFormatException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    else if (choice.equals("3")) {
                        currentUser = null;
                        System.out.println("Logged out!");
                    }
                    else{
                        System.out.println("Invalid command!");
                    }
                }
            }
        }
        write();
    }

    private void playGame(Game game){
        boolean playing = true;

        while(playing) {

            Player currentPlayer = game.getCurrentPlayer();
            printBoard(game.getBoard(), currentPlayer.getColor());
            System.out.println(currentPlayer.getColor() + "'s turn");

            if(game.checkForCheckMate()) {
                System.out.println("Checkmate! Game over");
                currentPlayer.setPoints(currentPlayer.getPoints() - 300);
                game.switchPlayer();
                currentPlayer = game.getCurrentPlayer();
                currentPlayer.setPoints(currentPlayer.getPoints() + 300);
                currentUser.removeGame(game);

                playing = false;
                break;
            }

            if(currentPlayer.getName().equalsIgnoreCase("computer")) {
                List<Map.Entry<Position, Position>> allPossibleMoves = new ArrayList<>();
                Player computer = game.getCurrentPlayer();

                List<ChessPair<Position, Piece>> piecesList = computer.getOwnedPieces(game.getBoard());
                for (ChessPair<Position, Piece> pair : piecesList){
                    Position from = pair.getKey();
                    for (Position to : pair.getValue().getPossibleMoves(game.getBoard())) {
                        if (game.getBoard().isValidMove(from, to))
                            allPossibleMoves.add(new AbstractMap.SimpleEntry<>(from, to));
                    }
                }
                Random rand = new Random();
                Map.Entry<Position, Position> selectedMove = allPossibleMoves.get(rand.nextInt(allPossibleMoves.size()));

                Position from = selectedMove.getKey();
                Position to = selectedMove.getValue();
                try{
                    Piece captured = game.getBoard().getPieceAt(to);
                    System.out.println("Computer moves " + from + "-" + to);
                    computer.makeMove(from, to, game.getBoard());
                    game.addMove(computer, from, to, captured);
                } catch(InvalidMoveException e){
                    System.out.println(e.getMessage());
                }
            }
            else{
                String input = sc.nextLine().trim();
                if(input.equalsIgnoreCase("quit")){
                    playing = false;
                    break;
                }
                else if(input.equalsIgnoreCase("resign")){
                    currentUser.setPoints(currentUser.getPoints() + currentPlayer.getPoints() - 150);
                    game.switchPlayer();
                    currentUser.setPoints(currentPlayer.getPoints() + 150);
                    System.out.println("You reisgned and lost 150 points!");
                    currentUser.removeGame(game);
                    playing = false;
                    break;
                }
                else if (input.contains("-")){
                    String[] parts = input.split("-");
                    if (parts.length != 2) continue;
                    Position from = new Position(parts[0].charAt(0), Character.getNumericValue(parts[0].charAt(1)));
                    Position to = new Position(parts[1].charAt(0), Character.getNumericValue(parts[1].charAt(1)));

                    try{
                        Piece p = game.getBoard().getPieceAt(from);
                        if (p == null || p.getColor() != currentPlayer.getColor()) {
                            System.out.println("That is not your piece!");
                            continue;
                        }

                        Piece captured = game.getBoard().getPieceAt(to);
                        currentPlayer.makeMove(from, to, game.getBoard());
                        game.addMove(currentPlayer, from, to, captured);
                    } catch (InvalidCommandException | InvalidMoveException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else {
                    Position from = new Position(input.charAt(0), Character.getNumericValue(input.charAt(1)));

                    try {
                        Piece p = game.getBoard().getPieceAt(from);

                        if (p == null || p.getColor() != currentPlayer.getColor()) {
                            System.out.println("That is not your piece!");
                            continue;
                        }
                        for (Position posTo : game.getBoard().getPieceAt(from).getPossibleMoves(game.getBoard())) {
                            if (game.getBoard().isValidMove(from, posTo))
                                System.out.println(" -> " + posTo);
                        }

                        String toAsString = sc.nextLine().trim();
                        Position to = new Position(toAsString.charAt(0), Character.getNumericValue(toAsString.charAt(1)));
                        Piece captured = game.getBoard().getPieceAt(to);
                        currentPlayer.makeMove(from, to, game.getBoard());
                        game.addMove(currentPlayer, from, to, captured);
                    }catch (InvalidCommandException | InvalidMoveException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            game.switchPlayer();
        }
    }

    private void printBoard(Board board, Colors perspective) {
        if (perspective == Colors.WHITE) {
            for (int row = 8; row >= 1; row--) {
                System.out.print(row + " ");
                for (char col = 'A'; col <= 'H'; col++) {
                    Piece p = board.getPieceAt(new Position(col, row));
                    if (p == null) {
                        System.out.print(" .. ");
                    } else {
                        char type = p.type();
                        char color = (p.getColor() == Colors.WHITE) ? 'W' : 'B';
                        System.out.print(type + "-" + color + " ");
                    }
                }
                System.out.println();
            }
            System.out.println("   A   B   C   D   E   F   G   H");
        }
        else if (perspective == Colors.BLACK) {
            System.out.println("   H   G   F   E   D   C   B   A");
            for (int row = 1; row <= 8; row++) {
                System.out.print(row + " ");
                for (char col = 'H'; col >= 'A'; col--) { // Iterate backwards H -> A
                    Piece p = board.getPieceAt(new Position(col, row));
                    if (p == null) {
                        System.out.print(" .. ");
                    } else {
                        char type = p.type();
                        char color = (p.getColor() == Colors.BLACK) ? 'B' : 'W';
                        System.out.print(type + "-" + color + " ");
                    }
                }
                System.out.println(" " + row);
            }
            System.out.println("   H   G   F   E   D   C   B   A");
        }
    }

    public static void main(String[] args){
        Board board = new Board();

        Main app = new Main();
        app.run();

    }
}