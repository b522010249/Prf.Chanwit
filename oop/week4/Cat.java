package week4;

public class Cat extends Animal {

    private String color;

    public Cat(String name, int age, String color) {
        super(name, age);
        this.color = color;
    }

    public void meow() {
        System.out.println(name + " says: Meow meow!");
    }

    public void showcolor() {
        System.out.println("Cat color: " + color);
    }
}