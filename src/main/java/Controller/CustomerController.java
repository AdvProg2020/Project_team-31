package Controller;

import Model.BuyingLog;
import Model.User;
import org.graalvm.compiler.lir.LIRInstruction;

import java.nio.DoubleBuffer;

public class CustomerController {
    private static CustomerController customerControllerInstance = new CustomerController();

    private CustomerController() {
    }

    public static CustomerController getInstance(){
        return  customerControllerInstance;
    }

    public String showCard(User user){
        return null;
    }

    public String showProductInCard(User user){
        return null;
    }

    public String showProduct(String productId){
        return null;
    }

    public void increaseProductInCard(User user, String productId){

    }     // misheh id card ro ham gerft

    public void decreaseProductInCard(User user, String productId){

    }

    public int showTotalPrice(User user) {
        return 0;
    }

    public BuyingLog createASaleLog(User user, String[] information){
        return null;
    }

    public void putDiscount(User user, BuyingLog buyingLog, String discountCode){

    }

    public void payMoney(User user, BuyingLog buyingLog){

    }

    public String showOrder(String orderId){
        return null;
    }

    public String showAllOrders(User user){
        return null;
    }

    public void rateProduct(User user, String productId, int rate){

    }

    public int showBalanceForCustomer(User user){
        return 0;
    }

    public String showDiscountCodes(User user){
        return null;
    }

}
