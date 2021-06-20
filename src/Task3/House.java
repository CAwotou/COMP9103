package Task3;

import java.util.ArrayList;
import java.util.List;

public class House {
    private ArrayList<Furniture> furniture=new ArrayList<>();

    public String listFurniture(){
        StringBuffer sb=new StringBuffer();
        sb.append("This house contains:");
        if(furniture!=null){
            for(Furniture f:furniture){
                sb.append("\n"+f.display());
            }
        }
        return sb.toString();
    }

    public void insert(Furniture item){
        if(item==null){
            return;
        }
        if (furniture==null){
            return;
        }
        furniture.add(item);
    }

    public boolean containsTelevision(){
        if(furniture!=null){
            for(Furniture f:furniture){
                Furniture item;
                item=f instanceof Television ? ((Television) f) : null;
                if(item!=null){
                    return true;
                }
            }
        }
        return false;
    }

    public int countChairs(){
        int singleChair=0;
        int chairFromTable=0;
        for(Furniture f:furniture){
            Furniture chair;
            Furniture table;
            chair=f instanceof Chair ? ((Chair) f) : null;
            if(chair!=null){
                singleChair++;
            }
            table=f instanceof Table ? ((Table) f) : null;
            if(table!=null){
                chairFromTable=chairFromTable+((Table) f).getChairs();
            }
        }
        return singleChair+chairFromTable;
    }
}
