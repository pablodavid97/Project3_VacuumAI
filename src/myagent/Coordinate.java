package myagent;

public class Coordinate implements Comparable<Coordinate> {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getXcoor() {
        return x;
    }

    public void setXcoor(int x) {
        this.x = x;
    }

    public int getYcoor() {
        return y;
    }

    public void setYcoor(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o){
        Coordinate coor = (Coordinate) o;
        return this.toString().equals(coor.toString());
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Coordinate o) {
        return Integer.compare(getXcoor(), o.getXcoor());
    }
}
