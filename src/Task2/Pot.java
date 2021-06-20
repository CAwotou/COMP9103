package Task2;


import java.util.*;

public class Pot {
    private Flower[] flowers;
    private int capacity;
    private String shape;

    public Pot(String shape, int capacity) {
        this.capacity = capacity;
        this.shape = shape;
        flowers = new Flower[capacity];
    }

    public Pot(int capacity, String shape) {
        this.capacity = capacity;
        this.shape = shape;
        flowers = new Flower[capacity];
    }

    public boolean insert(Flower f) {
        if (f == null) {
            return false;
        }
        if (count() < size()) {
            for (int i = 0; i < size(); i++) {
                if (flowers[i] == null) {
                    flowers[i] = f;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contains(Flower f) {
        if (f == null) {
            return (size() - count() > 0);
        }
        for (Flower flower : flowers) {
            if (flower != null) {
                if (flower.getColour().equalsIgnoreCase(f.getColour()) && flower.getSpecies().equalsIgnoreCase(f.getSpecies()) && Math.round(flower.getAge()) == Math.round(f.getAge()) && Math.abs(flower.getHeight() - f.getHeight()) < 0.001) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean containsType(String f) {
        for (Flower flower : flowers) {
            if (flower != null && flower.isAlive()) {
                if (flower.getSpecies() == null && f == null) {
                    return true;
                } else if (flower.getSpecies().equalsIgnoreCase(f)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsColour(String f) {
        for (Flower flower : flowers) {
            if (flower != null && flower.isAlive()) {
                if (flower.getColour() == null && f == null) {
                    return true;
                }
                if (flower.getColour() != null) {
                    if (flower.getColour().equalsIgnoreCase(f)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int size() {
        return capacity;
    }

    public int count() {
        int counter = 0;
        for (int i = 0; i < size(); i++) {
            if (flowers[i] != null) {
                counter++;
            }
        }
        return counter;
    }

    public String getShape() {
        return shape;
    }

    public int countType(String t) {
        int counter = 0;
        for (Flower flower : flowers) {
            if (flower != null && flower.getSpecies() == null && t == null) {
                counter++;
            }
            if (flower != null && flower.getSpecies() != null) {
                if (flower.getSpecies().equalsIgnoreCase(t)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public int countColour(String t) {
        int counter = 0;
        for (Flower flower : flowers) {
            if (flower != null && flower.getColour() == null && t == null) {
                counter++;
            }
            if (flower != null && flower.getColour() != null) {
                if (flower.getColour().equalsIgnoreCase(t)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public String view() {
        HashMap<String, Integer> colours = new HashMap<>();
        int count = 0;
        String s = "";
        Integer counter = null;
        for (int i = 0; i < size(); i++) {
            if (flowers[i] != null && flowers[i].getColour() != null) {
                counter = colours.get(flowers[i].getColour().toLowerCase(Locale.ROOT));
                if (counter == null) {
                    counter = 0;
                    count++;
                }
                colours.put(flowers[i].getColour().toLowerCase(Locale.ROOT), counter + 1);
            }
        }
//        System.out.println(colours);
//        System.out.println(count);
        String[] colour = new String[count];
        Set set = colours.keySet();
        Iterator it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            colour[i++] = (String) it.next();
        }
        Arrays.sort(colour);
        for (i = 0; i < count; i++) {
            //System.out.println(i);
            //System.out.println(colour[i]);
            s = s + colours.get(colour[i]) + "x " + colour[i] + "\n";
            //s = s + colour[i] + "x " + colour[i] + "\n";
            //System.out.println(i);
        }
        return s;
    }

    public void water() {
        for (int i = 0; i < size(); i++) {
            if (flowers[i] != null) {
                flowers[i].growInside(this);
                //System.out.println("water");
            }
        }
    }

    public int rearrange() {
        int count = 0;
        for (int i = 0; i < size(); i++) {
            if (flowers[i] != null) {
                if (!flowers[i].isAlive()) {
                    count++;
                    flowers[i] = null;
                }
            }
        }
        return count;
    }

    public double averageAge() {
        if (count() == 0) {
            return -1;
        }
        double totalAge = 0;
        int count = 0;
        for (int i = 0; i < size(); i++) {
            if (flowers[i] != null && flowers[i].isAlive()) {
                if (flowers[i].isAlive()) {
                    totalAge = totalAge + flowers[i].getAge();
                    count++;
                }
            }
        }
        if (count == 0) {
            return -1;
        }
        return totalAge / count;
    }

    public double averageHeight() {
        if (count() == 0) {
            return -1;
        }
        double totalHeight = 0;
        int count = 0;
        for (int i = 0; i < size(); i++) {
            if (flowers[i] != null && flowers[i].isAlive()) {
                if (flowers[i].isAlive()) {
                    totalHeight = totalHeight + flowers[i].getHeight();
                    count++;
                }
            }
        }
        if (count == 0) {
            return -1;
        }
        return totalHeight / count;
    }

    public Pot filterType(String t) {
        int count = 0;
        Flower[] temp = new Flower[size()];
        if (t == null) {
            for (int i = 0; i < size(); i++) {
                if (flowers[i] != null) {
                    if (flowers[i].getSpecies() == null) {
                        temp[count] = flowers[i].clone();
                        count++;
                    }
                }
            }
        }
        if (t != null) {
            for (int i = 0; i < size(); i++) {
                if (flowers[i] != null && flowers[i].getSpecies() != null) {
                    if (flowers[i].getSpecies().equalsIgnoreCase(t)) {
                        temp[count] = flowers[i].clone();
                        count++;
                    }
                }
            }
        }
        Pot p = new Pot(size(), shape);
        for (int i = 0; i < count; i++) {
            p.insert(temp[i]);
        }
        return p;
    }

    public Pot filterColour(String t) {
        int count = 0;
        Flower[] temp = new Flower[size()];
        if (t == null) {
            for (int i = 0; i < size(); i++) {
                if (flowers[i] != null) {
                    if (flowers[i].getColour() == null) {
                        temp[count] = flowers[i].clone();
                        count++;
                    }
                }
            }
        }
        if (t != null) {
            for (int i = 0; i < size(); i++) {
                if (flowers[i] != null && flowers[i].getColour() != null) {
                    if (flowers[i].getColour().equalsIgnoreCase(t)) {
                        temp[count] = flowers[i].clone();
                        count++;
                    }
                }
            }
        }
        Pot p = new Pot(size(), shape);
        for (int i = 0; i < size(); i++) {
            p.insert(temp[i]);
        }
        return p;
    }

    public int replace(Flower f1, Flower f2) {
        if (f1 == null && f2 == null) {
            return 0;
        }
        int counter = 0;
        if (f1 == null) {
            for (int i = 0; i < size(); i++) {
                if (flowers[i] == null) {
                    flowers[i] = f2.clone();
                    counter++;
                }
            }
            return counter;
        }
        if (f2 == null) {
            for (int i = 0; i < count(); i++) {
                if (flowers[i] != null && flowers[i].equals(f1)) {
                    flowers[i] = null;
                    counter++;
                }
            }
            return counter;
        }
        for (int i = 0; i < count(); i++) {
            if (flowers[i] != null && flowers[i].equals(f1)) {
                flowers[i] = f2.clone();
                counter++;
            }
        }
        return counter;

    }

    public int replaceType(String type, Flower f) {
        int counter = 0;
        for (int i = 0; i < size(); i++) {
            if (f == null) {
                if (flowers[i] != null) {
                    if (flowers[i].getSpecies() == null) {
                        if (type == null) {
                            flowers[i] = null;
                            counter++;
                        }
                    }
                }
                if (flowers[i] != null) {
                    if (flowers[i].getSpecies() != null) {
                        if (flowers[i].getSpecies().equalsIgnoreCase(type)) {
                            flowers[i] = null;
                            counter++;
                        }
                    }
                }
            }
            if (f != null && flowers[i] != null) {
                if (flowers[i].getSpecies() != null) {
                    if (flowers[i].getSpecies().equalsIgnoreCase(type)) {
                        flowers[i] = f.clone();
                        counter++;
                    }
                }
                if (flowers[i].getSpecies() == null) {
                    if (type == null) {
                        flowers[i] = f.clone();
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    public int replaceColour(String colour, Flower f) {
        int counter = 0;
        for (int i = 0; i < size(); i++) {
            if (f == null && flowers[i] != null) {
                if (flowers[i].getColour() == null) {
                    if (colour == null) {
                        flowers[i] = null;
                        counter++;
                    }
                }
                if (flowers[i].getColour() != null) {
                    if (flowers[i].getColour().equalsIgnoreCase(colour)) {
                        flowers[i] = null;
                        counter++;
                    }
                }
            } else if (f != null && flowers[i] != null) {
                if (flowers[i].getColour() != null) {
                    if (flowers[i].getColour().equalsIgnoreCase(colour)) {
                        flowers[i] = f.clone();
                        counter++;
                    }
                }
                if (flowers[i].getColour() == null) {
                    if (colour == null) {
                        flowers[i] = f.clone();
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    public static Pot combine(Pot p1, Pot p2) {
        Pot p = new Pot(p1.size() + p2.size(), p1.shape);
        if (p1 == null && p2 == null) {
            return null;
        }
        if (p1 == null && p2 != null) {
            return p2;
        }
        if (p1 != null && p2 == null) {
            return p1;
        } else {
            for (int i = 0; i < p1.size() + p2.size(); i++) {
                for (int j = 0; j < p1.size() + p2.size(); j++) {
                    if (j < p1.size()) {
                        if (p1.flowers[j] != null) {
                            if (!p.containsColour(p1.flowers[j].getColour())) {
                                if (p1.flowers[j].getColour() != null) {
                                    p.flowers[i] = p1.flowers[j].clone();
                                }
                            }
                        }
                    }
                    if (j >= p1.size()) {
                        if (p2.flowers[j - p1.size()] != null) {
                            if (!p.containsColour(p2.flowers[j - p1.size()].getColour())) {
                                if (p2.flowers[j - p1.size()].getColour() != null) {
                                    p.flowers[i] = p2.flowers[j - p1.size()].clone();
                                }
                            }
                        }
                    }
                }
            }
        }
        Pot result = new Pot(p.count(), p1.getShape());
        for (int i = 0; i < p.count(); i++) {
            result.insert(p.flowers[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        Pot p2 = new Pot("Circle", 12);
        Flower f = new Flower("yellow", "dandelion", 12, 23);
        Flower f2 = new Flower("white", "orchid", 2, 53.4);
        Flower f3 = new Flower("green", "parsely", 2, 53.4);
        Flower f4 = new Flower("orange", "tulip", 6, 53.4);
        Flower f5 = new Flower("red", "rose", 15, 53.4);
        Flower f6 = new Flower("yellow", "dandelion", 12, 23);
        Flower f7 = new Flower("white", "rose", 4, 53.4);
        Flower f8 = new Flower(null, "rose", 46, 34);
        Flower f9 = new Flower(null, null, 46, 34);
        p2.insert(f);
        p2.insert(f2);
        p2.insert(f3);
        p2.insert(f4);
        p2.insert(f5);
        p2.insert(f6);
        p2.insert(f7);
        p2.insert(f8);
        p2.insert(f9);
        System.out.println(p2.count());
        System.out.println(p2.replaceType("rose", null));
        System.out.println(p2.countColour("white"));
        System.out.println(p2.countType("rose"));
        System.out.println(p2.count());
        Flower f10 = new Flower("green", "parsnip", 1, 2);
        System.out.println(p2.replaceType(null, f10));
        System.out.println(p2.replaceType(null, f5));
        System.out.println(p2.count());
        f10.grow();
        System.out.println(p2.averageAge());
        System.out.println(p2.countColour("green"));
    }

}