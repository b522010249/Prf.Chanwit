package week4;

public class Animal {

    protected String name;
    protected int age;
    
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void eat() {
        System.out.println("Eating...");
    }

    public void sleep() {
        System.out.println("Sleeping...");
    }

    public void excrete() {
        System.out.println("Excreting...");
    }

    public void showInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age + " years old");
    }
}