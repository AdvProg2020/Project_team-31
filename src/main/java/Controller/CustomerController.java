package Controller;

import Model.*;


import java.util.ArrayList;
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

    public String showProductInCard(User user) {
        return null;
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
            productInCard.changeNumberOfProduct(changingNum);
            if (productInCard.getNumber() == 0) {
                products.remove(productToChange, productInCard);
            }
        }
    }

    public void addProductToCard(User user, Product product, String sellerUsername) throws invalidUsername {
        Card card;
        if (user.getCard() == null) {
            card = new Card();
            user.setCard(card);
        } else
            card = user.getCard();

        User seller = LoginController.getUserByUsername(sellerUsername);

        if (seller instanceof Seller) {
            if (product.getSellersOfThisProduct().contains(seller)) {
                card.addProductToCard(new ProductInCard(product, (Seller) seller));
                return;
            }
        }
        throw new invalidUsername("There is not seller with this username");
    }

    public double showTotalPrice(User user) {
        HashMap <Product, ProductInCard> productsInThisCard = user.getCard().getProductsInThisCard();
        double totalPrice = 0.0;
        for (Product product : productsInThisCard.keySet()) {
            totalPrice += (product.getPrice() * productsInThisCard.get(product).getNumber());
        }
        return totalPrice;
    }

    public BuyingLog createBuyingLog(User user, String[] information) {
        return null;
    }

    public void putDiscount(User user, BuyingLog buyingLog, String discountCode) {

    }

    public void payMoney(User user, BuyingLog buyingLog) {

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
