public class ElectricGuitar extends Guitar implements Electric {

    public ElectricGuitar(String name, String type) {
        super(name, type);
    }

    @Override
    public void useElectricity() {
        System.out.println("=======Electric instruments========");
        System.out.println(name + " can use electricity");
    }
}