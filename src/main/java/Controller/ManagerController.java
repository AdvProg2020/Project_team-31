package Controller;

import Model.*;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.lang.String;

public class ManagerController {
    private static ManagerController managerControllerInstance = new ManagerController();

    public ManagerController() {
    }

    public static ManagerController getInstance() {
        return managerControllerInstance;
    }

    public String showUsers() {
        return null;
    }

    public void deleteUser(String username) {
        User user = LoginController.getUserByUsername(username);
        user.deleteUser();
        if(user instanceof Customer) {
            ((Customer)user).deleteCustomer();
        } else if (user instanceof Seller) {
            ((Seller)user).deleteSeller();
        } else {
            ((Manager)user).deleteManager();
        }
    }

    public void removeProduct(String productId) {
        Product deletingProduct = ProductController.getProductById(productId);
        ArrayList<Seller> sellers = deletingProduct.getSellersOfThisProduct();
        deletingProduct.removeProduct();
        for (Seller seller : sellers) {
            seller.removeProduct(deletingProduct);
        }
    }

    public void createDiscountCode(String[] information) {

    }

    public ArrayList<String> showAllDiscountCodes() {
        ArrayList<DiscountCode> discountCodes = DiscountCode.getAllDiscountCodes();
        ArrayList<String> arrayOfDiscount = new ArrayList<>();
        for (DiscountCode discountCode : discountCodes) {
            arrayOfDiscount.add("code:" + discountCode.getDiscountCode() + ", beginTime:" + discountCode.getBeginTime() + ", endTime:" + discountCode.getEndTime() + ", percent:" + discountCode.getDiscountPercent());
        }
        return arrayOfDiscount;
    }

    public String showDiscount(String discountId) {
        DiscountCode discount = getDiscountById(discountId);
        return "code:" + discount.getDiscountCode() + ", beginTime:" + discount.getBeginTime() + ", endTime:" + discount.getEndTime() + ", percent:" + discount.getDiscountPercent();
    }

    private DiscountCode getDiscountById(String discountId) {
        for (DiscountCode discount : DiscountCode.getAllDiscountCodes()) {
            if(discount.getDiscountCode().equals(discountId))
                return discount;
        }
        return null;
    }

    public void editDiscountCode(String discountCode, String[] information) {

    }

    public void removeDiscountCode(String DiscountCode) {

    }

    public ArrayList<String> showAllRequests() {
        ArrayList<String> arrayOfRequest = new ArrayList<>();
        for (Request request : Request.getAllRequest()) {
            arrayOfRequest.add(request.showDetail());
        }
        return arrayOfRequest;
    }

    public String showRequestDetails(String requestId) {
        return getRequestById(requestId).showDetail();
    }

    public void acceptRequest(String requestId) {
        Request request = getRequestById(requestId);
        if (request instanceof SellerRequest) {
            String[] information = ((SellerRequest) request).getInformation();
            new Seller(information[0], information[1], ((SellerRequest)request).getUsername(), information[2], information[3], information[4], information[5])
        } else if (request instanceof OffRequest) {
            ((OffRequest)request).getOff().setOffStatus(OffStatus.accepted);
        } else if (request instanceof ProductRequest) {
            ((ProductRequest)request).
        }
        request.deleteRequest();
    }

    public void declineRequest(String requestId) {
        getRequestById(requestId).deleteRequest();
    }

    private Request getRequestById(String requestId) {
        for (Request request : Request.getAllRequest()) {
            if(request.getRequestId().equals(requestId))
                return request;
        }
        return null;
    }

    public String showAllCategories() {
        return null;
    }

    public void addCategory(String name, ArrayList<String> features) {

    }

    public void removeCategory(String name) {

    }

    public void editCategory(String name, ArrayList<String> newFeatures) {
    }

}

class IsThereDiscountCodeWithThisName extends Exception {
    public IsThereDiscountCodeWithThisName(String message) {
        super(message);
    }
}

