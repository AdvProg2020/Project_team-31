package Server;

import com.google.gson.JsonObject;

public class Process {
    CustomerControllerProcess customerControllerProcess = CustomerControllerProcess.getInstance();
    LoginControllerProcess loginControllerProcess = LoginControllerProcess.getInstance();
    ManagerControllerProcess managerControllerProcess = ManagerControllerProcess.getInstance();
    ProductControllerProcess productControllerProcess = ProductControllerProcess.getInstance();
    SellerControllerProcess sellerControllerProcess = SellerControllerProcess.getInstance();

    public String answerClient(JsonObject jsonObject) {
        String answer = null;
        String controller = jsonObject.get("controller").toString();
        switch (controller) {
            case "customer":
                answer = customerControllerProcess(jsonObject);
                break;
            case "login":
                answer = loginControllerProcess(jsonObject);
                break;
            case "manager":
                answer = managerControllerProcess(jsonObject);
                break;
            case "product":
                answer = productControllerProcess(jsonObject);
                break;
            case "seller":
                answer = sellerControllerProcess(jsonObject);
                break;
            default:
                System.out.println("controller not found!!!");
        }
        return answer;

    }

    private String customerControllerProcess(JsonObject jsonObject) {
        String answer = null;
        String command = jsonObject.get("command").toString();
        if (command.equals("")) {

        }
        return answer;
    }

    /////////////////////////////////////////////////////////////////////////////////
    private String loginControllerProcess(JsonObject jsonObject) {
        String answer = null;
        String command = jsonObject.get("command").toString();
        if (command.equals("isThereAnyManager")) {
            return loginControllerProcess.managerStatus();
        }
        return answer;
    }

    /////////////////////////////////////////////////////////////////////////////////
    private String managerControllerProcess(JsonObject jsonObject) {
        String answer = null;
        String command = jsonObject.get("command").toString();
        if (command.equals("")) {
        }
        return answer;
    }

    /////////////////////////////////////////////////////////////////////////////////
    private String productControllerProcess(JsonObject jsonObject) {
        String answer = null;
        String command = jsonObject.get("command").toString();
        if (command.equals("")) {
        }
        return answer;
    }

    /////////////////////////////////////////////////////////////////////////////////
    private String sellerControllerProcess(JsonObject jsonObject) {
        String answer = null;
        String command = jsonObject.get("command").toString();
        if (command.equals("")) {
        }
        return answer;
    }

}
