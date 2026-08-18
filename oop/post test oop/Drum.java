public class Drum extends instrument implements Action {
    public Drum(String name, String type) {
        super(name, type);
    }
    public void type() {
        System.out.println("Type: " + type);
    }
    @Override
    public void makesound() {
        System.out.println("Sound: Boom Boom!");
    }

    @Override
    public void action() {
        System.out.println("Action: " + name + " is playing.");
    }

    @Override
    public void perform() {
        System.out.println("Action: " + name + " is hitting.");
    }
}
