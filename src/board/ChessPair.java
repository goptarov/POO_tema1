package board;

import pieces.ChessPiece;
import pieces.Piece;

public class ChessPair<K extends Comparable<K>, V> implements Comparable<ChessPair<K,V>>{

    Position key;
    Piece value;


    public ChessPair(Position key, Piece value) {
        this.key = key;
        this.value = value;
    }

    public Position getKey() {
        return key;
    }

    public Piece getValue() {
        return value;
    }

    @Override
    public int compareTo(ChessPair<K,V> o) {
        return this.key.compareTo(o.key);
    }

    @Override
    public String toString() {
        return "ChessPair{" + "key=" + key + ", value=" + value + '}';
    }
}
