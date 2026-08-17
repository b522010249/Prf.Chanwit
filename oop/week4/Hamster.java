package week4;

public class Hamster extends Animal {

    private String color;

    public Hamster(String name, int age, String color) {
        super(name, age);
        this.color = color;
    }

    public void squeak() {
        System.out.println(name + " says: Squeak squeak!");
    }

    public void runOnWheel() {
        System.out.println(name + " is running on the wheel.");
    }

    public void showColor() {
        System.out.println("Hamster color: " + color);
    }
}