public class Guitar extends instrument implements Action {
    public Guitar(String name, String type) {
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
        System.out.println("Action:"+ name + " is plucking the strings.");
    }
    @Override
    public void makesound() {
        System.out.println("Sound:Strum Strum!");
        
    }
}
