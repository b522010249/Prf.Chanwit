package week4;

public class Main {
    
    public static void main(String[] args) {
        Dog dog = new Dog("Buddy", 3, "Shih Tzu");

        System.out.println("----------- Dog -----------");
        dog.showInfo();
        dog.showBreed();
        dog.bark();
        dog.eat();
        dog.sleep();
        dog.excrete();
        
        System.out.println();
        
        Cat cat = new Cat("Kitty", 2, "White");
        System.out.println("----------- Cat -----------");
        cat.showInfo();
        cat.showcolor();
        cat.meow();
        cat.eat();
        cat.sleep();

        System.out.println();

        Hamster hamster = new Hamster("Hammy", 1, "Grey");
        System.out.println("----------- Hamster -----------");
        hamster.showInfo();
        hamster.showColor();
        hamster.squeak();
        hamster.eat();
        hamster.runOnWheel();

        System.out.println();

        Squirrel squirrel = new Squirrel("Nutty", 2, "Brown");
        System.out.println("----------- Squirrel -----------");
        squirrel.showInfo();
        squirrel.showColor();
        squirrel.eat();
        squirrel.sleep();
        squirrel.climbTree();
    }
}