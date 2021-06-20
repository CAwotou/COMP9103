package Task3;

public class Chair extends Furniture{

    private boolean occupied;
    private double comfortRating;

    public Chair(int x, int y,double comfortRating) {
        super(x, y);
        this.occupied = false;
        if(0<=comfortRating&&comfortRating<=100){
            this.comfortRating = comfortRating;
        }
        else {
            if(comfortRating<0){
                this.comfortRating=0;
            }
            if(comfortRating>100){
                this.comfortRating=100;
            }
        }
    }

    @Override
    public String display() {
        String s="A chair with comfort rating "+comfortRating;
        return s;
    }

    public boolean isOccupied(){
        return occupied;
    }

    public void occupy(Ornament o){
        if(o!=null){
            if(o.getValuation()>=100){
                occupied=true;
            }
        }
    }
}
