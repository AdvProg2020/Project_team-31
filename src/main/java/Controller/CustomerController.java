package Controller;

import Model.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.lang.String;

public class CustomerController {
    private static CustomerController customerControllerInstance = new CustomerController();

    private CustomerController() {
    }

    public static CustomerController getInstance() {
        return customerControllerInstance;
    }

    public ArrayList<String> showCard(User user) {
        Card card = user.getCard();
        ArrayList<String> arrayOfInformation = new ArrayList<>();
        for (Product productInThisCard : card.getProductsInThisCard().keySet()) {
            String productInformation = "productId: " + productInThisCard.getProductId() + " name: " + productInThisCard.getName() + " price: " + productInThisCard.getPrice() + " numberOfProduct: " + card.getProductsInThisCard().get(productInThisCard).getNumber();
            arrayOfInformation.add(productInformation);
        }
        arrayOfInformation.add(String.valueOf(showTotalPrice(user)));

        return arrayOfInformation;
    }

    public String[] showProductInCard(User user) {
        HashMap<Product, ProductInCard> products = user.getCard().getProductsInThisCard();
        String[] arrayOfProducts = new String[products.size()];
        int i = 0;
        for (Product product : products.keySet()) {
            arrayOfProducts[i++] = i + 1 + ". productId: " + product.getProductId() + " ,nameOfProduct: " + product.getName() + " ,number: " + products.get(product).getNumber() + " ,sellerUsername: " + products.get(product).getSeller().getUsername();
        }
        return arrayOfProducts;
    }

    public String showProduct(String productId, User user) throws Exception {
        Product product = getProductById(productId);
        if(user != null) {
            if(!product.getSellersOfThisProduct().contains((Seller)user)) {
                throw new Exception("Seller does'nt have this product");
            }
        }
        return "name=" + product.getName() + ", price=" + product.getPrice() + ", rate=" + product.getMeanOfCustomersRate();
    }

    public void changeNumberOfProductInCard(User user, String productId, int changingNum) throws DoesNotHaveThisProduct {
        HashMap<Product, ProductInCard> products = user.getCard().getProductsInThisCard();
        Product productToChange = getProductById(productId);

        if (productToChange == null || !products.containsKey(productToChange))
            throw new DoesNotHaveThisProduct("This user does not have this product");
        else {
            ProductInCard productInCard = products.get(productToChange);
            if (productToChange.getAvailable() < productInCard.getNumber() + changingNum) {
                throw new DoesNotHaveThisProduct("Inventory of product is not enough");
            }
            productInCard.changeNumberOfProduct(changingNum);
            if (productInCard.getNumber() == 0) {
                products.remove(productToChange, productInCard);
            }
        }
    }

    private Product getProductById(String productId) {
        for (Product product : Product.allProducts) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public void addProductToCard(User user, Product product, String sellerUsername) throws InvalidUsername, DoesNotHaveThisProduct {
        Card card = user.getCard();
        if (card == null) {
            card = new Card();
            user.setCard(card);
        }
        User seller = LoginController.getUserByUsername(sellerUsername);

        if (seller instanceof Seller) {
            if (product.getSellersOfThisProduct().contains(seller)) {
                if (product.getAvailable() == 0)
                    throw new DoesNotHaveThisProduct("Inventory of product is not enough");
                card.addProductToCard(new ProductInCard(product, (Seller) seller));
                return;
            }
        }
        throw new InvalidUsername("There is not seller with this username for this product");
    }

    double showTotalPrice(User user) {
        HashMap<Product, ProductInCard> productsInThisCard = user.getCard().getProductsInThisCard();
        double totalPrice = 0.0;
        for (Product product : productsInThisCard.keySet()) {
            totalPrice += (product.getPrice() * productsInThisCard.get(product).getNumber());
        }
        return totalPrice;
    }

    public BuyingLog createBuyingLog(User user, String[] information) {
        return new BuyingLog(customerControllerInstance.showTotalPrice(user), (Customer) user, user.getCard().getProductsInThisCard(), information);

    }

    public void putDiscount(User user, BuyingLog buyingLog, String discountCodeString) throws DiscountCodeIsInvalid {
        DiscountCode discount = null;
        for (DiscountCode code : ((Customer) user).getAllDiscountCodes()) {
            if (code.getDiscountCode().equals(discountCodeString)) {
                discount = code;
            }
        }
        if (discount == null)
            throw new DiscountCodeIsInvalid("This user has not this discountCode");
        if (discount.getBeginTime().after(new Date()) || discount.getEndTime().before(new Date()))
            throw new DiscountCodeIsInvalid("DiscountCode is unavailable this time");
        if (discount.getDiscountTimesForEachCustomer().get(user) == 0)
            throw new DiscountCodeIsInvalid("User has used this code before");

        buyingLog.setDiscountAmount(Math.min(buyingLog.getTotalPrice() * discount.getDiscountPercent() / 100, discount.getMaximumDiscount()));
        discount.decreaseDiscountTimesForEachCustomer((Customer) user);
    }

    public void payMoney(User user, BuyingLog buyingLog) throws CannotPayMoney {
        if (buyingLog.getTotalPrice() - buyingLog.getDiscountAmount() > user.getCredit())
            throw new CannotPayMoney("Credit of money is not enough");
        buyingLog.finishBuying("BuyingLog" + BuyingLog.getAllBuyingLog().size() + 1, new Date());
        user.payMoney(buyingLog.getTotalPrice() - buyingLog.getDiscountAmount());
        ((Customer) user).addBuyingLog(buyingLog);
        ((Customer) user).addRecentShoppingProducts(buyingLog.getBuyingProducts().keySet());
        createSellingLog(buyingLog);
    }

    private void createSellingLog(BuyingLog buyingLog) {
        ArrayList<ProductInCard> products = new ArrayList<>(buyingLog.getBuyingProducts().values());
        for (ProductInCard product : products) {
            product.getSeller().addSellingLog(new SellingLog(buyingLog.getDate(), buyingLog.getTotalPrice(), 0.0, product.getProduct(), buyingLog.getCustomer()));
            product.getProduct().decreaseNumberOfProduct(product.getNumber());
        }
    }

    public String showOrder(String orderId) {
        return null;
    }

    public String showAllOrders(User user) {
        return null;
    }

    public void rateProduct(User user, String productId, int rate) {

    }

    public Double showBalanceForCustomer(User user) {
        return user.getCredit();
    }

    public ArrayList<String> showDiscountCodes(User user) {
        ArrayList<String> allDiscount = new ArrayList<>();
        for (DiscountCode discountCode : ((Customer) user).getAllDiscountCodes()) {
            allDiscount.add("Code=" + discountCode.getDiscountCode() + ", percent=" + discountCode.getDiscountPercent() + ", maximum=" + discountCode.getMaximumDiscount());
        }
        return allDiscount;
    }

}

class CannotPayMoney extends Exception {
    public CannotPayMoney(String message) {
        super(message);
    }
}

class DiscountCodeIsInvalid extends Exception {
    public DiscountCodeIsInvalid(String message) {
        super(message);
    }
}

class DoesNotHaveThisProduct extends Exception {
    public DoesNotHaveThisProduct(String message) {
        super(message);
    }
}

class InvalidUsername extends Exception {
    public InvalidUsername(String message) {
        super(message);
    }
}



