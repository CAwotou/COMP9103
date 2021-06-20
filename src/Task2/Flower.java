package Task2;

public class Flower {
    private String colour;
    private String species;
    private double age;
    private int maxAge;
    private double maxHeight;

    public Flower(String colour, String species, int maxAge, double maxHeight) {
        this.colour = colour;
        this.species = species;
        this.age = 0;
        this.maxAge = maxAge;
        this.maxHeight = maxHeight;
    }

    public boolean grow() {
        if (isAlive()) {
            age++;
        }
        return isAlive();
    }

    public boolean growInside(Pot p) {
        int space = p.size() - p.count();
        if (isAlive()) {
            if (space >= 0 && space <= 5) {
                age = age + (space) * 0.5 + 1;
            } else if (space > 5) {
                age = age + 3.5;
            }
        }
        System.out.println("space" + space);
        System.out.println("grow" + age);
        return isAlive();
    }

    public boolean isAlive() {
        if (age > maxAge || age < 0) {
            return false;
        }
        return true;
    }

    public String getColour() {
        return colour;
    }

    public String getSpecies() {
        return species;
    }

    public double getAge() {
        return age;
    }

    public double getHeight() {
        return (2 / Math.PI) * Math.atan(age / maxHeight) * maxHeight;
    }

    public void die() {
        age = -1;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public Flower clone() {
        Flower f = new Flower(colour, species, maxAge, maxHeight);
        f.setAge(this.getAge());
        return f;
    }

    public boolean equals(Flower f) {
//        if (this == null && f == null) {
//            return true;
//        }
        if (this != null && f != null) {
            boolean c1 = false, c2 = false;
            if (this.getColour() == null || f.getColour() == null) {
                c1 = this.getColour() == null && f.getColour() == null;
            } else if (this.getColour() != null && f.getColour() != null) {
                c1 = this.getColour().equalsIgnoreCase(f.getColour());
            }
            if (this.getSpecies() == null || f.getSpecies() == null) {
                c2 = this.getSpecies() == null && f.getSpecies() == null;
            } else if (this.getSpecies() != null && f.getSpecies() != null) {
                c2 = this.getSpecies().equalsIgnoreCase(f.getSpecies());
            }

            if (Math.round(this.getAge()) == Math.round(f.getAge())) {
                if (Math.abs(this.getHeight() - f.getHeight()) <= 0.001) {
                    if (c1 && c2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
