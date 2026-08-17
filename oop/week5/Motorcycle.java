package week5;

public class Motorcycle extends Vehicle {

    public Motorcycle(String brand) {
        super(brand);
    }

    @Override
    public void startEngine() {
        System.out.println(brand + ": Motorcycle Engine Started");
    }
    
    public void fireEx() {
        System.out.println(brand + ": There is an exhaust pipe at the rear.");
    }

    public void twoWheels() {
    System.out.println(brand + ": It has two wheels.");
    }
}
