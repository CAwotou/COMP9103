package Task3;

public abstract class Furniture {
    protected int x;
    protected int y;

    public Furniture(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract String display();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
