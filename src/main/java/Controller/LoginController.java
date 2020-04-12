package Controller;

import Model.User;
import org.graalvm.compiler.lir.LIRInstruction;

public class LoginController {
    private static LoginController loginControllerInstance = new LoginController();

    private LoginController() {
    }

    public static LoginController getInstance() {
        return loginControllerInstance;
    }

    public void register(String username, String password , String role){

    }

    public User login(String username, String password){
        return null;
    }

    public void setInformationOfUser(User user , String[] Information){

    }

    public void setCompanyOfSeller(User User , String companyName){

    }

    public String showPersonalInformation(User user){
        return null;
    }

    public void editPersonalInformation(User user, String[] newInformation){

    }
}
