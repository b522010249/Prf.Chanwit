public class Piano extends instrument implements Action {
    public Piano(String name, String type) {
        super(name, type);
    }
    public void type() {
        System.out.println("Type: " + type);
    }
    @Override
    public void action() {
        System.out.println("Action: " + name + " is playing.");
    }
    @Override
    public void perform() {
        System.out.println("Action: " + name + " is pressing the keys.");
    }
    @Override
    public void makesound() {
        System.out.println("Sound: Ting Ting!");
    }
    
}
