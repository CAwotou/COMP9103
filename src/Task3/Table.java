package Task3;

import java.util.Iterator;

public class Table extends Furniture implements Iterable<Chair> {
    private Chair[] chairSet;
    private String tableclothColour;

    public Table(int x, int y, String colour, int chairs) {
        super(x, y);
        this.tableclothColour = colour;
        if(chairs<=0){
            this.chairSet = new Chair[0];
        }
        else {
            this.chairSet = new Chair[chairs];
        }
        for (int i = 0; i < chairs; i++) {
            if (colour != null) {
                chairSet[i] = new Chair(x, y, Math.abs((colour + i).hashCode() % 101));
            } else {
                chairSet[i] = new Chair(x, y, 0);
            }
        }
    }

    public int getChairs(){
        if(chairSet!=null){
            return chairSet.length;
        }
        else {
            return 0;
        }
    }

    @Override
    public String display() {
        String s=null;
        if(tableclothColour!=null){
            s = "A table with a " + tableclothColour + " tablecloth";
        }
        else {
            s="A table with no tablecloth";
        }
        ChairIterator<Chair> chairIterator = new ChairIterator<>(chairSet);
        while (chairIterator.hasNext()) {
            Chair chair = chairIterator.next();
            s = s +"\n"+" - "+ chair.display();
        }
        return s;
    }

    public Iterator<Chair> iterator() {
        return new ChairIterator<Chair>(chairSet);
    }
}

class ChairIterator<Chair> implements Iterator<Chair> {
    Chair[] chairs;
    int index;

    public ChairIterator(Chair[] chairSet) {
        chairs = chairSet;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < chairs.length;
    }

    @Override
    public Chair next() {
        return this.hasNext() ? chairs[index++] : null;
    }

}

