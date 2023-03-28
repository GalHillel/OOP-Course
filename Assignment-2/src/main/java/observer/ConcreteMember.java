package observer;

public class ConcreteMember implements Member {

    private UndoableStringBuilder usb = new UndoableStringBuilder();
    private String name;

    public ConcreteMember() {
        this.name = null;
        this.usb = null;

    }

    public ConcreteMember(UndoableStringBuilder usb, String name) {
        this.usb = usb;
        this.name = name;
    }

    /**
     * Updates the StringBuilder.
     *
     * @param usb
     */
    @Override
    public void update(UndoableStringBuilder usb) {
        this.usb = usb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UndoableStringBuilder getUsb() {
        return usb;
    }

    @Override
    public String toString() {
        return "ConcreteMember:\nUSB: " + usb + "\nName: " + name;
    }
}
