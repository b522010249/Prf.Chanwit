package week4;

public class Squirrel extends Animal {

    private String color;

    public Squirrel(String name, int age, String color) {
        super(name, age);
        this.color = color;
    }

    public void climbTree() {
        System.out.println(name + " is climbing a tree.");
    }

    public void showColor() {
        System.out.println("Squirrel color: " + color);
    }
}