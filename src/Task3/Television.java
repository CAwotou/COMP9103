package Task3;

public class Television extends Furniture implements Electric {
    private boolean on;
    private int height;
    private int width;

    public Television(int x, int y, int width, int height) {
        super(x, y);
        this.on = false;
        this.height = height;
        this.width = width;
    }

    @Override
    public void togglePowerState() {
        on=!on;
    }

    @Override
    public boolean getPowerState() {
        return on;
    }

    @Override
    public String display() {
        String state=null;
        if(getPowerState()==true){
            state="on";
        }else {
            state="off";
        }
        String s = "A " + getWidth() + "x" + getHeight() + " television in the "+state+" state.";
        return s;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
