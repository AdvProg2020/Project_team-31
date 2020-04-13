package View;

public class OffMenu extends Menu {
    public static OffMenu instance = null;

    private OffMenu() {
        super();
    }

    public static OffMenu getInstance() {
        if (instance == null)
            instance = new OffMenu();
        return instance;
    }

    @Override
    public void run() {

    }
}
