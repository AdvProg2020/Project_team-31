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
            information.add("userName: " + user.getUsername() + ", firstName:" + personalInformation[0] + ", lastName:" + personalInformation[1]);
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

    public void createDiscountCode(String discountCode, Date beginTime, Date endTime, int discountPercent, int maximumDiscount, HashMap<String, Integer> discountTimesForEachCustomer) throws Exception {
        HashMap<Customer, Integer> timesForEachCustomer = changeNameToCustomer(discountTimesForEachCustomer);
        DiscountCode newDiscountCode = new DiscountCode(discountCode);
        newDiscountCode.setDiscountCode(beginTime, endTime, discountPercent, maximumDiscount, timesForEachCustomer);
        for (Customer customer : newDiscountCode.getDiscountTimesForEachCustomer().keySet()) {
            customer.addDiscountCode(newDiscountCode);
        }
    }

    private HashMap<Customer, Integer> changeNameToCustomer(HashMap<String, Integer> discountTimesForEachCustomer) throws Exception {
        HashMap<Customer, Integer> timesForEachCustomer = new HashMap<>();
        for (String s : discountTimesForEachCustomer.keySet()) {
            User user = LoginController.getUserByUsername(s);
            if (user == null) {
                throw new Exception("User with userName \"" + s + "\" doesn't exist");
            }
            if (user instanceof Customer) {
                timesForEachCustomer.put((Customer) user, discountTimesForEachCustomer.get(s));
            } else {
                throw new Exception("User with userName \"" + s + "\" isn't customer");
            }
        }
        return timesForEachCustomer;
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

    public DiscountCode getDiscountById(String discountId) {
        for (DiscountCode discount : DiscountCode.getAllDiscountCodes()) {
            if (discount.getDiscountCode().equals(discountId))
                return discount;
        }
        return null;
    }

    public void editDiscountCode(String discountCode, Date beginTime, Date endTime, int discountPercent, int maximumDiscount, HashMap<String, Integer> discountTimesForEachCustomer) throws Exception {
        DiscountCode discount = getDiscountById(discountCode);
        if (discount == null) {
            throw new Exception("There is not discount with this code");
        }
        HashMap<Customer, Integer> timesForEachCustomer = changeNameToCustomer(discountTimesForEachCustomer);
        discount.setDiscountCode(beginTime, endTime, discountPercent, maximumDiscount, timesForEachCustomer);
    }

    public void removeDiscountCode(String discountCode) {
        DiscountCode discount = getDiscountById(discountCode);
        discount.removeDiscountCode();
        for (Customer customer : discount.getDiscountTimesForEachCustomer().keySet()) {
            if (customer.getAllDiscountCodes().contains(discount))
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
        Request request = getRequestById(requestId);
        if (request instanceof SellerRequest) {
            String[] information = ((SellerRequest) request).getInformation();
            new Seller(information[0], information[1], ((SellerRequest) request).getUsername(), information[2], information[3], information[4], information[5]);
        } else if (request instanceof OffRequest) {
            ((OffRequest) request).getOff().setOffStatus(ProductAndOffStatus.accepted);
            if (((OffRequest) request).getIsEditing()) {
                completeEditingOff((OffRequest) request);
            }
        } else if (request instanceof ProductRequest) {
            ((ProductRequest) request).getProduct().setProductStatus(ProductAndOffStatus.accepted);
            if (((ProductRequest) request).isEditing()) {
                completeEditingProduct((ProductRequest) request);
            }
        } else if (request instanceof  SellerOfProductRequest) {
            ((SellerOfProductRequest) request).getProduct().addSeller(((SellerOfProductRequest) request).getSeller(), ((SellerOfProductRequest) request).getPrice());
            ((SellerOfProductRequest) request).getSeller().addProduct(((SellerOfProductRequest) request).getProduct());
            if(((SellerOfProductRequest) request).getProduct().getMinimumPrice() > ((SellerOfProductRequest) request).getPrice()) {
                ((SellerOfProductRequest) request).getProduct().setMinimumPrice(((SellerOfProductRequest) request).getPrice());
            }
        }
        request.deleteRequest();
    }

    private void completeEditingOff(OffRequest offRequest) {
        Off off = offRequest.getOff();
        off.setBeginTime(offRequest.getBeginTime());
        off.setEndTime(offRequest.getEndTime());
        off.setOffPercent(offRequest.getOffPercent());
        off.setOnSaleProducts(offRequest.getOnSaleProducts());
    }

    private void completeEditingProduct(ProductRequest productRequest) {
        Product product = productRequest.getProduct();
        product.setAvailable(productRequest.getAvailable());
        product.setInformation(productRequest.getInformation());
        product.setSpecialPropertiesRelatedToCategory(productRequest.getSpecialPropertiesRelatedToCategory());
        product.removeSeller(productRequest.getSeller());
        product.addSeller(productRequest.getSeller(), productRequest.getPrice());
        if (productRequest.getPrice() < product.getMinimumPrice()) {
            product.setMinimumPrice(productRequest.getPrice());
        }
    }

    public void declineRequest(String requestId) {
        Request request = getRequestById(requestId);
        if (request instanceof OffRequest) {
            if (!((OffRequest) request).getIsEditing()) {
                ((OffRequest) request).getOff().getSeller().removeOffFromThisSeller(((OffRequest) request).getOff());
                ((OffRequest) request).getOff().removeOff();
            } else {
                ((OffRequest) request).getOff().setOffStatus(ProductAndOffStatus.accepted);
            }
        } else if (request instanceof ProductRequest) {
            if (!((ProductRequest) request).isEditing()) {
                Product product = ((ProductRequest) request).getProduct();
                product.removeProduct();
                product.getCategory().removeProduct(product);
                for (Seller seller : product.getSellersOfThisProduct().keySet()) {
                    seller.removeProduct(product);
                }
            } else {
                ((ProductRequest) request).getProduct().setProductStatus(ProductAndOffStatus.accepted);
            }
        }
        request.deleteRequest();
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
            for (Seller seller : product.getSellersOfThisProduct().keySet()) {
                seller.removeProduct(product);
            }
        }
        deletingCategory.deleteCategory();
    }

    public void editCategory(String name, ArrayList<String> newFeatures) {
        Category category = getCategoryByName(name);
        category.setSpecialProperties(newFeatures);
        for (Product product : category.getProducts()) {
            for (String s : product.getSpecialPropertiesRelatedToCategory().keySet()) {
                if (!newFeatures.contains(s)) {
                    product.removeSpecialFeature(s);
                }
            }
        }
    }

    public void changeFeatureOfCategory(String categoryName, HashMap<String, String> changedFeatured) {
        Category category = getCategoryByName(categoryName);
        ArrayList<String> features = category.getSpecialProperties();
        for (String old : changedFeatured.keySet()) {
            features.remove(old);
            features.add(changedFeatured.get(old));
        }
        category.setSpecialProperties(features);
        for (Product product : category.getProducts()) {
            for (String oldName : changedFeatured.keySet()) {
                if (product.getSpecialPropertiesRelatedToCategory().keySet().contains(oldName)) {
                    String value = product.getSpecialPropertiesRelatedToCategory().get(oldName);
                    product.removeSpecialFeature(oldName);
                    product.addSpecialFeature(changedFeatured.get(oldName), value);
                }
            }
        }
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

