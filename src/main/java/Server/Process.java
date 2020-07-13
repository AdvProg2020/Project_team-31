package Server;

import com.google.gson.JsonObject;

public class Process {
    public String answerClient(JsonObject jsonObject) {
        String answer = null;
        String controller = jsonObject.get("controller").toString();
        if (controller.equals("customer")) {
            answer = customerControllerProcess(jsonObject);
        } else if (controller.equals("login")) {
            answer = loginControllerProcess(jsonObject);
        } else if (controller.equals("manager")) {
            answer = managerControllerProcess(jsonObject);
        } else if (controller.equals("product")) {
            answer = productControllerProcess(jsonObject);
        } else if (controller.equals("seller")) {
            answer = sellerControllerProcess(jsonObject);
        } else System.out.println("controller not found!!!");
        return answer;

    }

    private String customerControllerProcess(JsonObject jsonObject) {
        return null;
    }

    private String loginControllerProcess(JsonObject jsonObject) {
        return null;
    }

    private String managerControllerProcess(JsonObject jsonObject) {
        return null;
    }

    private String productControllerProcess(JsonObject jsonObject) {
        return null;
    }

    private String sellerControllerProcess(JsonObject jsonObject) {
        return null;
    }

}
