public class main {
    public static void main(String[] args) {
        // Polymorphism: ใช้ Array ของคลาสแม่ (instrument) เก็บ Object คลาสลูกต่างชนิดกัน
        instrument[] band = {
            new Guitar("Fender", "Guitar"),
            new Drum("Yamaha", "Drum"),
            new Piano("Kawai", "Piano"),
            new ElectricGuitar("Gibson", "Electric Guitar")
        };
        System.out.println("=======Musical Instruments Information=======");
        // วนลูปสั่งงานแบบ Polymorphism (เรียกใช้ makesound() เดียวกัน แต่ผลลัพธ์ต่างกัน)
        for (instrument i : band) {
            System.out.println("Name: " + i.name);
            i.makesound(); // Polymorphic method call

            // หากต้องการเรียกใช้ Action interface แบบ Polymorphism
            if (i instanceof Action) {
                Action actionObj = (Action) i;
                actionObj.action();
                actionObj.perform();
            }
            // หากต้องการเรียกใช้ Electric interface แบบ Polymorphism
            if (i instanceof Electric) {
                Electric electricObj = (Electric) i;
                electricObj.useElectricity();
            }
            System.out.println("-------------------");
        }
    }
}