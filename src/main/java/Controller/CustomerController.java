package Controller;

import Model.*;


import java.sql.Time;
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

    public String showProduct(String productId) {
        return null;
    }

    public void changeNumberOfProductInCard(User user, String productId, int changingNum) throws DoesNotHaveThisProduct {
        HashMap<Product, ProductInCard> products = user.getCard().getProductsInThisCard();
        Product productToChange = null;
        for (Product product : products.keySet()) {
            if (product.getProductId().equals(productId)) {
                productToChange = product;
            }
        }
        if (productToChange == null)
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

    public void addProductToCard(User user, Product product, String sellerUsername) throws invalidUsername, DoesNotHaveThisProduct {
        Card card;
        if (user.getCard() == null) {
            card = new Card();
            user.setCard(card);
        } else
            card = user.getCard();

        User seller = LoginController.getUserByUsername(sellerUsername);

        if (seller instanceof Seller) {
            if (product.getSellersOfThisProduct().contains(seller)) {
                if (product.getAvailable() == 0)
                    throw new DoesNotHaveThisProduct("Inventory of product is not enough");
                card.addProductToCard(new ProductInCard(product, (Seller) seller));
                return;
            }
        }
        throw new invalidUsername("There is not seller with this username for this product");
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

    public void putDiscount(User user, BuyingLog buyingLog, String discountCodeString) throws discountCodeIsInvalid {
        DiscountCode discount = null;
        for (DiscountCode code : ((Customer) user).getAllDiscountCodes()) {
            if (code.getDiscountCode().equals(discountCodeString)) {
                discount = code;
            }
        }
        if (discount == null)
            throw new discountCodeIsInvalid("This user has not this discountCode");
        if (discount.getBeginTime().after(new Date()) || discount.getEndTime().before(new Date()))
            throw new discountCodeIsInvalid("DiscountCode is unavailable this time");
        if (discount.getDiscountTimesForEachCustomer().get(user) == 0)
            throw new discountCodeIsInvalid("User has used this code before");

        buyingLog.setDiscountAmount(Math.min(buyingLog.getTotalPrice()*discount.getDiscountPercent()/100, discount.getMaximumDiscount()));
        discount.decreaseDiscountTimesForEachCustomer((Customer)user);
    }

    public void payMoney(User user, BuyingLog buyingLog) throws canNotPayMoney {
        if (buyingLog.getTotalPrice() - buyingLog.getDiscountAmount() > user.getCredit())
            throw new canNotPayMoney("Credit of money is not enough");
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

    public int showBalanceForCustomer(User user) {
        return 0;
    }

    public String showDiscountCodes(User user) {
        return null;
    }

}

class canNotPayMoney extends Exception {
    public canNotPayMoney(String message) {
        super(message);
    }
}

class discountCodeIsInvalid extends Exception {
    public discountCodeIsInvalid(String message) {
        super(message);
    }
}

class DoesNotHaveThisProduct extends Exception {
    public DoesNotHaveThisProduct(String message) {
        super(message);
    }
}

class invalidUsername extends Exception {
    public invalidUsername(String message) {
        super(message);
    }
}
