package Assignment1;

import Task3.*;

public class Test {
    public static void main(String[] args) {
        House h=new House();
        h.insert(new Television(1,1,1,1));
        h.insert(new Ornament(1,1,0.1));
        System.out.println(h.listFurniture());
    }
}
