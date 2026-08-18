public abstract class instrument {
    protected String name;
    protected String type;

    public instrument(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public abstract void makesound();
}
