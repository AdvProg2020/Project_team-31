import View.LoginMenu;
import View.MainMenu;
import View.Menu;

public class Main {
    public static void main(String[] args) {
        Menu.initialize();
        MainMenu.getInstance().run();
    }
}
