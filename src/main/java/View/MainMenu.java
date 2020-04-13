package View;

import Model.*;

public class MainMenu extends Menu {
    public static MainMenu instance = null;


    public static MainMenu getInstance() {
        if (instance == null)
            instance = new MainMenu();
        return instance;
    }

    private MainMenu() {
        super();
    }

    @Override
    public void run() {
        while (!end) {
            if (user instanceof Seller)
                sellerMenu();
            else if (user instanceof Manager);
            //managerMenu();
            else if (user instanceof Customer || user == null);
            // customerMenu();
        }
    }

    private void sellerMenu() {
        String command;
//        while (true) {
//            command = scanner.nextLine().trim();
//            if (getMatcher("^(?i)view\\s+personal\\s+info$", command).find())
//                viewPersonalInformation();
//            else if (getMatcher("^(?i)view\\s+company\\s+information$", command).find())
//                viewCompanyInformation();
//            else if (getMatcher("^(?i)view\\s+sales\\s+history$", command).find())
//
//             else if (getMatcher("^(?i)manage\\s+products$", command).find())
//
//             else if (getMatcher("^(?i)add\\s+product$", command).find())
//
//             else if (getMatcher("^(?i)remove\\s+product\\s+(\\S+)$", command).find())
//
//             else if (getMatcher("^(?i)show\\s+categories$", command).find())
//
//             else if (getMatcher("^(?i)view\\s+offs$", command).find())
//
//             else if (getMatcher("^(?i)view\\s+balance\n$", command).find())
//
//             else if (getMatcher("^(?i)end$", command).find())
//                break;
//            else System.out.println("invalid command");
//        }
    }


    private void viewPersonalInformation() {
        //call the controllers
    }

    private void viewCompanyInformation() {
        //call the controller
    }

}
