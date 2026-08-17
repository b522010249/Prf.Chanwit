package week5;

public class Car extends Vehicle {
    
    public Car(String brand) {
        super(brand);
    }

    @Override
    public void startEngine(){
        System.out.print(brand +": Car Engine Started");
    }

    public void TurnLeft() {
    System.out.println(brand + ": Turn Left");
    }

    public void TurnRight() {
    System.out.println(brand + ": Turn Right");
    }
}
