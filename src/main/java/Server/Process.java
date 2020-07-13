package Server;

import com.google.gson.JsonObject;

public class Process {
    public String answerClient(JsonObject jsonObject) {
        String answer = null;
        String controller = jsonObject.get("controller").toString();
        if (controller.equals("customer")) {
            answer = customerConrollerProcess(jsonObject);
        } else if (controller.equals("login")) {
            answer = loginConrollerProcess(jsonObject);
        } else if (controller.equals("manager")) {
            answer = managerConrollerProcess(jsonObject);
        } else if (controller.equals("product")) {
            answer = productConrollerProcess(jsonObject);
        } else if (controller.equals("seller")) {
            answer = sellerConrollerProcess(jsonObject);
        } else System.out.println("controller not found!!!");
        return answer;

    }

    private String customerConrollerProcess(JsonObject jsonObject) {
        return null;
    }

    private String loginConrollerProcess(JsonObject jsonObject) {
        return null;
    }

    private String managerConrollerProcess(JsonObject jsonObject) {
        return null;
    }

    private String productConrollerProcess(JsonObject jsonObject) {
        return null;
    }

    private String sellerConrollerProcess(JsonObject jsonObject) {
        return null;
    }

}
