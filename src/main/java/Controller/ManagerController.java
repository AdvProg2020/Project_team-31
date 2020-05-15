package Controller;

import Model.*;

import java.util.ArrayList;
import java.lang.String;
import java.util.Date;
import java.util.HashMap;

public class ManagerController {
    private static ManagerController managerControllerInstance = new ManagerController();

    public ManagerController() {
    }

    public static ManagerController getInstance() {
        return managerControllerInstance;
    }

    public ArrayList<String> showUsers() {
        ArrayList<String> information = new ArrayList<>();
        for (User user : User.getAllUsers()) {
            String[] personalInformation = user.getPersonalInformation();
            information.add("userName: " + user.getUsername() + ", firstName:" + personalInformation[0] + ", lastName:" + personalInformation[1] );
        }
        return information;
    }

    public void deleteUser(String username) {
        User user = LoginController.getUserByUsername(username);
        user.deleteUser();
        if (user instanceof Customer) {
            ((Customer) user).deleteCustomer();
        } else if (user instanceof Seller) {
            ((Seller) user).deleteSeller();
        } else {
            ((Manager) user).deleteManager();
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

    public void createDiscountCode(String discountCode, Date beginTime, Date endTime, Double discountPercent, Double maximumDiscount, HashMap<String, Integer> discountTimesForEachCustomer) {
        HashMap<Customer, Integer> timesForEachCustomer = new HashMap<>();
        for (String s : discountTimesForEachCustomer.keySet()) {
            timesForEachCustomer.put((Customer) (LoginController.getUserByUsername(s)), discountTimesForEachCustomer.get(s));
        }
        DiscountCode newDiscountCode = new DiscountCode(discountCode);
        newDiscountCode.setDiscountCode(beginTime, endTime, discountPercent, maximumDiscount, timesForEachCustomer);
        for (Customer customer : timesForEachCustomer.keySet()) {
            customer.addDiscountCode(newDiscountCode);
            customer.addDiscountCode(newDiscountCode);
        }
    }

    public ArrayList<String> showAllDiscountCodes() {
        ArrayList<DiscountCode> discountCodes = DiscountCode.getAllDiscountCodes();
        ArrayList<String> arrayOfDiscount = new ArrayList<>();
        for (DiscountCode discountCode : discountCodes) {
            arrayOfDiscount.add("code:" + discountCode.getDiscountCode() + ", beginTime:" + discountCode.getBeginTime() + ", endTime:" + discountCode.getEndTime() + ", percent:" + discountCode.getDiscountPercent());
        }
        return arrayOfDiscount;
    }

    public String showDiscount(String discountId) throws Exception {
        DiscountCode discount = getDiscountById(discountId);
        if (discount == null)
            throw new Exception("there is not discount with this code");
        return "code:" + discount.getDiscountCode() + ", beginTime:" + discount.getBeginTime() + ", endTime:" + discount.getEndTime() + ", percent:" + discount.getDiscountPercent();
    }

    private DiscountCode getDiscountById(String discountId) {
        for (DiscountCode discount : DiscountCode.getAllDiscountCodes()) {
            if (discount.getDiscountCode().equals(discountId))
                return discount;
        }
        return null;
    }

    public void editDiscountCode(String discountCode, Date beginTime, Date endTime, Double discountPercent, Double maximumDiscount, HashMap<String, Integer> discountTimesForEachCustomer) {
        DiscountCode discount = getDiscountById(discountCode);
        HashMap<Customer, Integer> timesForEachCustomer = new HashMap<>();
        for (String s : discountTimesForEachCustomer.keySet()) {
            timesForEachCustomer.put((Customer) (LoginController.getUserByUsername(s)), discountTimesForEachCustomer.get(s));
        }
        discount.setDiscountCode(beginTime, endTime, discountPercent, maximumDiscount, timesForEachCustomer);
    }

    public void removeDiscountCode(String discountCode) {
        DiscountCode discount = getDiscountById(discountCode);
        discount.removeDiscountCode();
        for (Customer customer : discount.getDiscountTimesForEachCustomer().keySet()) {
            customer.removeDiscountCode(discount);
        }
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
//        Request request = getRequestById(requestId);
//        if (request instanceof SellerRequest) {
//            String[] information = ((SellerRequest) request).getInformation();
//            new Seller(information[0], information[1], ((SellerRequest)request).getUsername(), information[2], information[3], information[4], information[5])
//        } else if (request instanceof OffRequest) {
//            ((OffRequest)request).getOff().setOffStatus(OffStatus.accepted);
//        } else if (request instanceof ProductRequest) {
//            ((ProductRequest)request).  // ezafe be category
//        }
//        request.deleteRequest();
    }

    public void declineRequest(String requestId) {
        getRequestById(requestId).deleteRequest();
    }

    private Request getRequestById(String requestId) {
        for (Request request : Request.getAllRequest()) {
            if (request.getRequestId().equals(requestId))
                return request;
        }
        return null;
    }

    public ArrayList<String> showAllCategories() {
        ArrayList<Category> allCategories = Category.getAllCategories();
        ArrayList<String> information = new ArrayList<>();
        for (Category category : allCategories) {
            information.add("name : " + category.getName() + ", specialProperties : " + category.getSpecialProperties());
        }
        return information;
    }

    public void addCategory(String name, ArrayList<String> features) {
        new Category(name, features);
    }

    public void removeCategory(String name) {
        Category deletingCategory = getCategoryByName(name);
        for (Product product : deletingCategory.getProducts()) {
            product.removeProduct();
            for (Seller seller : product.getSellersOfThisProduct()) {
                seller.removeProduct(product);
            }
        }
        deletingCategory.deleteCategory();
    }

    public void editCategory(String name, ArrayList<String> newFeatures) {
        getCategoryByName(name).setSpecialProperties(newFeatures);
    }

    public static Category getCategoryByName(String name) {
        for (Category category : Category.getAllCategories()) {
            if (category.getName().equals(name))
                return category;
        }
        return null;
    }

    public ArrayList<String> getCategoryFeaturesOfAProduct(String productId) {
        return ProductController.getProductById(productId).getCategory().getSpecialProperties();
    }

}

class IsThereDiscountCodeWithThisName extends Exception {
    public IsThereDiscountCodeWithThisName(String message) {
        super(message);
    }
}

