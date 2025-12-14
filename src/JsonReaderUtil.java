import Game.*;
import board.Board;
import board.ChessPair;
import board.Colors;
import board.Position;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pieces.*;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reading JSON documents using JSON.simple ("simple-json").
 *
 * ###### IMPORTANT: This is just an example of how to read JSON documents using the library.
 * Your classes might differ slightly, so don’t hesitate to update this class as needed.
 *
 * Expected structures:
 * - accounts.json: an array of objects with fields: email (String), password (String), points (Number), games (array of numbers)
 * - games.json: an array of objects with fields matching the JSON provided:
 *   id (Number), players (array of {email, color}), currentPlayerColor (String),
 *   board (array of {type, color, position}), moves (array of {playerColor, from, to})
 */
public final class JsonReaderUtil {

    private JsonReaderUtil() {
    }

    /**
     * Reads the accounts from the given JSON file path.
     *
     * @param path path to accounts.json
     * @return list of Account objects (empty list if file not found or array empty)
     * @throws IOException    if I/O fails
     * @throws ParseException if JSON is invalid
     */
    public static List<User> readUsers(Path path, Map<Integer, Game> allGames) throws IOException, ParseException {
        if (path == null || !Files.exists(path)) {
            return new ArrayList<>();
        }
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JSONParser parser = new JSONParser();
            Object root = parser.parse(reader);
            JSONArray arr = asArray(root);
            List<User> result = new ArrayList<>();

            if (arr == null) {
                return result;
            }

            for (Object item : arr) {
                JSONObject obj = asObject(item);
                if (obj == null) {
                    continue;
                }

                User user = new User(asString(obj.get("email")), asString(obj.get("password")));
                user.setPoints(asInt(obj.get("points"), 0));
                JSONArray gamesArr = asArray(obj.get("games"));
                if (gamesArr != null) {
                    for (Object gid : gamesArr) {
                        int gameId = asInt(gid, -1);

                        Game game = allGames.get(gameId);

                        if (game != null) {
                            user.addGame(game);
                        }
                    }
                }
                result.add(user);
            }
            return result;
        }
    }

    /**
     * Reads the games from the given JSON file path and returns them as a map by id.
     * The structure strictly follows games.json as provided (no title/genre).
     *
     * @param path path to games.json
     * @return map id -> Game (empty if file missing or array empty)
     * @throws IOException    if I/O fails
     * @throws ParseException if JSON is invalid
     */
    public static Map<Integer, Game> readGamesAsMap(Path path) throws IOException, ParseException {
        Map<Integer, Game> map = new HashMap<>();
        if (path == null || !Files.exists(path)) {
            return map;
        }
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JSONParser parser = new JSONParser();
            Object root = parser.parse(reader);
            JSONArray arr = asArray(root);
            if (arr == null) return map;
            for (Object item : arr) {
                JSONObject obj = asObject(item);
                if (obj == null) {
                    continue;
                }
                int id = asInt(obj.get("id"), -1);
                if (id < 0) {
                    continue;
                }// skip invalid

                Board board = new Board();
                // board array
                JSONArray boardArr = asArray(obj.get("board"));
                if (boardArr != null) {
                    for (Object bItem : boardArr) {
                        JSONObject bObj = asObject(bItem);
                        if (bObj == null) {
                            continue;
                        }

                        String type = asString(bObj.get("type"));
                        Colors color = asColor(bObj.get("color"));
                        String position = asString(bObj.get("position"));
                        Position pos = parsePositionString(position);

                        Piece piece = null;
                        piece = createPiece(type, color, pos);

                        if (piece != null) board.pieces.add(new ChessPair<>(piece.getPosition(), piece));
                    }
                }
                // players array
                JSONArray playersArr = asArray(obj.get("players"));
                List<Player> players = new ArrayList<>();
                if (playersArr != null) {
                    for (Object pItem : playersArr) {
                        JSONObject pObj = asObject(pItem);
                        String email = asString(pObj.get("email"));
                        Colors color = asColor(pObj.get("color"));
                        players.add(new Player(email, color));
                    }
                }

                Game g = new Game(id, players.get(0), players.get(1));

                Colors currColor = asColor(obj.get("currentPlayerColor"));
                g.setCurrentPlayerColor(currColor);

                // Parse optional moves array
                JSONArray movesArr = asArray(obj.get("moves"));
                if (movesArr != null) {
                    for (Object mItem : movesArr) {
                        JSONObject mObj = asObject(mItem);
                        Colors pColor = asColor(mObj.get("playerColor"));
                        String from = asString(mObj.get("from"));
                        String to = asString(mObj.get("to"));

                        JSONObject capObj = asObject(mObj.get("captured"));
                        Piece captured = null;
                        if (capObj != null) {
                            String cType = asString(capObj.get("type"));
                            Colors cColor = asColor(capObj.get("color"));
                            captured = createPiece(cType, cColor, parsePositionString(to));
                        }

                        if (pColor == players.get(0).getColor())
                            g.addMove(players.get(0), parsePositionString(from), parsePositionString(to), captured);
                        if (pColor == players.get(1).getColor())
                            g.addMove(players.get(1), parsePositionString(from), parsePositionString(to), captured);
                    }
                }
                map.put(id, g);
            }
        }
        return map;
    }

    // -------- helper converters --------

    private static Colors asColor(Object o) {
        if (String.valueOf(o).equals("WHITE")) return Colors.WHITE;
        if (String.valueOf(o).equals("BLACK")) return Colors.BLACK;
        return null;
    }

    private static JSONArray asArray(Object o) {
        return (o instanceof JSONArray) ? (JSONArray) o : null;
    }

    private static JSONObject asObject(Object o) {
        return (o instanceof JSONObject) ? (JSONObject) o : null;
    }

    private static String asString(Object o) {
        return o == null ? null : String.valueOf(o);
    }

    private static int asInt(Object o, int def) {
        if (o instanceof Number) return ((Number) o).intValue();
        try {
            return o != null ? Integer.parseInt(String.valueOf(o)) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static long asLong(Object o, long def) {
        if (o instanceof Number) return ((Number) o).longValue();
        try {
            return o != null ? Long.parseLong(String.valueOf(o)) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static Piece createPiece(String type, Colors color, Position pos) {
        return switch (type) {
            case "K" -> new King(color, pos);
            case "Q" -> new Queen(color, pos);
            case "R" -> new Rook(color, pos);
            case "B" -> new Bishop(color, pos);
            case "N" -> new Knight(color, pos);
            case "P" -> new Pawn(color, pos);
            default -> null;
        };
    }

    private static Position parsePositionString(String s) {
        if (s == null || s.length() < 2) return new Position('A', 1);
        return new Position(s.charAt(0), Character.getNumericValue(s.charAt(1)));
    }
}
