package Task1;

public class Shapes {
    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.out.println("Usage: java Shapes <shape> <size>");
            return;
        }
        int size;
        try {
            size = Integer.parseInt(args[1]);
            if (size <= 0) {
                System.out.println("Invalid size - it must be a positive integer.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Invalid size - it must be a positive integer.");
            return;
        }
        if (args[0].equals("square")) {
            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    for (int j = 0; j < 2 * size + 1; j++) {
                        if (j == 0) {
                            System.out.print(" ");
                        } else {
                            System.out.print("_");
                        }
                    }
                    System.out.println();
                }//first line
                if (i == size - 1) {
                    for (int j = 0; j < 2 * size + 2; j++) {
                        if (j == 0 || j == (2 * size) + 1) {
                            System.out.print("|");
                        } else {
                            System.out.print("_");
                        }
                    }
                } //last line
                else {
                    for (int j = 0; j < 2 * size + 2; j++) {
                        if (j == 0 || j == (2 * size) + 1) {
                            System.out.print("|");
                        } else {
                            System.out.print(" ");
                        }
                    }
                }//others
                System.out.println();
            }
            return;
        }
        if (args[0].equals("triangle")) {
            for (int i = 0; i <= size; i++) {
                if (i == size) {
                    for (int j = 0; j < 2 * size + 1; j++) {
                        if (j == 0) {
                            System.out.print("/");
                        }
                        if (j == 2 * size) {
                            System.out.println("\\");
                        } else {
                            System.out.print("_");
                        }
                    }
                } else {
                    for (int j = 0; j < 2 * size + 2; j++) {
                        if (j == size - i) {
                            System.out.print("/");
                        }
                        if (j == size + i) {
                            System.out.print("\\");
                        } else if (j < size + i) {
                            System.out.print(" ");
                        }
                    }
                    System.out.println();
                }
            }
            return;
        }
        if (args[0].equals("circle")) {
            double smallRadius = Math.round(Math.pow(size - 0.5, 2));
            double bigRadius = Math.round(Math.pow(size + 0.5, 2));
            for (double i = -size; i <= size; i++) {
                for (double j = -2 * size; j <= 2 * size; j++) {
                    double x = Math.pow(i, 2) + Math.pow(0.5 * j, 2);
                    if (x >= smallRadius && x <= bigRadius) {
                        System.out.print("X");
                    }
                    if (x < smallRadius) {
                        System.out.print(" ");
                    }
                    if (x > bigRadius && j < 0) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
            return;
        }
        if (args[0].equals("rhombus")) {
            double lengthOfSide=Math.round(Math.sqrt(5) * (size + 1));
            for (int i = 0; i <= size; i++) {
                if (i == 0) {
                    for (int j = 0; j < size + 2 + lengthOfSide; j++) {
                        if (j < size + 1) {
                            System.out.print(" ");
                        } else {
                            System.out.print("_");
                        }
                    }
                    System.out.println();
                }//first line
                if (i == size) {
                    for (int j = 0; j < 2 + lengthOfSide; j++) {
                        if (j == 0 || j == 1 + lengthOfSide) {
                            System.out.print("/");
                        } else {
                            System.out.print("_");
                        }
                    }
                } //last line
                else {
                    for (int j = 0; j < 2 + size + lengthOfSide; j++) {
                        if (j == size - i || j == 1 + size - i + lengthOfSide) {
                            System.out.print("/");
                        } else if (j < 1 + size + lengthOfSide- i) {
                            System.out.print(" ");
                        }
                    }
                }
                System.out.println();
            }
        } else {
            System.out.println("Invalid shape, must be either square, triangle, circle or rhombus.");
        }
    }
}