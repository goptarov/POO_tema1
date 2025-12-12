package board;

public class Position implements Comparable<Position>{
    private char x;
    private int y;

    public Position(char x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Position other) {
        if (this.y != other.y) {
            return this.y - other.y;
        }
        return this.x - other.x;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        if (x == position.x && y == position.y)
            return true;
        else
            return false;
    }

    public char getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString(){
        return "" + x + y;
    }
}
