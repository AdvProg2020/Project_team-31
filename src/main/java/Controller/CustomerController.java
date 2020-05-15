package Controller;

import Model.*;


import java.util.*;
import java.lang.String;
import java.util.stream.Collectors;

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

    public Boolean doesSellerHaveThisProduct (String productId, User user) {
        Product product = ProductController.getProductById(productId);
        if(user != null) {
            if(product.getSellersOfThisProduct().contains((Seller)user)) {
                return true;
            }
        }
        return false;
    }

    public void changeNumberOfProductInCard(User user, String productId, int changingNum) throws Exception{
        HashMap<Product, ProductInCard> products = user.getCard().getProductsInThisCard();
        Product productToChange = ProductController.getProductById(productId);

        if (productToChange == null || !products.containsKey(productToChange))
            throw new Exception("This user does not have this product");
        else {
            ProductInCard productInCard = products.get(productToChange);
            if (productToChange.getAvailable() < productInCard.getNumber() + changingNum) {
                throw new Exception("Inventory of product is not enough");
            }
            productInCard.changeNumberOfProduct(changingNum);
            if (productInCard.getNumber() == 0) {
                products.remove(productToChange, productInCard);
            }
        }
    }

    public void addProductToCard(User user, Product product, String sellerUsername) throws Exception {
        Card card = user.getCard();
        if (card == null) {
            card = new Card();
            user.setCard(card);
        }
        User seller = LoginController.getUserByUsername(sellerUsername);

        if (seller instanceof Seller) {
            if (product.getSellersOfThisProduct().contains(seller)) {
                if (product.getAvailable() == 0)
                    throw new Exception("Inventory of product is not enough");
                card.addProductToCard(new ProductInCard(product, (Seller) seller));
                return;
            }
        }
        throw new Exception("There is not seller with this username for this product");
    }

    double showTotalPrice(User user) {
        HashMap<Product, ProductInCard> productsInThisCard = user.getCard().getProductsInThisCard();
        List<ProductInCard> products = new ArrayList<ProductInCard>(productsInThisCard.values());
        return products.stream()
                .map(product -> (product.getProduct().getPrice()*product.getNumber()))
                .reduce((a,b) -> a+b)
                .orElse(0.0);
    }

    public BuyingLog createBuyingLog(User user, String[] information) {
        return new BuyingLog(customerControllerInstance.showTotalPrice(user), (Customer) user, user.getCard().getProductsInThisCard(), information);

    }

    public void putDiscount(User user, BuyingLog buyingLog, String discountCodeString) throws Exception {
        DiscountCode discount = null;
        for (DiscountCode code : ((Customer) user).getAllDiscountCodes()) {
            if (code.getDiscountCode().equals(discountCodeString)) {
                discount = code;
            }
        }
        if (discount == null)
            throw new Exception("This user has not this discountCode");
        if (discount.getBeginTime().after(new Date()) || discount.getEndTime().before(new Date()))
            throw new Exception("DiscountCode is unavailable this time");
        if (discount.getDiscountTimesForEachCustomer().get(user) == 0)
            throw new Exception("User has used this code before");

        buyingLog.setDiscountAmount(Math.min(buyingLog.getTotalPrice() * discount.getDiscountPercent() / 100, discount.getMaximumDiscount()));
        discount.decreaseDiscountTimesForEachCustomer((Customer) user);
    }

    public void payMoney(User user, BuyingLog buyingLog) throws Exception {
        if (buyingLog.getTotalPrice() - buyingLog.getDiscountAmount() > user.getCredit())
            throw new Exception("Credit of money is not enough");
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

    public String showOrder(User user, String orderId) {
        for (BuyingLog buyingLog : ((Customer) user).getAllBuyingLogs()) {
            if(buyingLog.getLogId().equals(orderId)){
                return "Id: " + buyingLog.getLogId() + ", Date: " + buyingLog.getDate() + ", Price: " + buyingLog.getTotalPrice();
            }
        }
        return null;
    }

    public ArrayList<String> showAllOrders(User user) {
        return (ArrayList<String>) ((Customer)user).getAllBuyingLogs().stream()
                .map(buyingLog -> "Id: " + buyingLog.getLogId() + ", Date: " + buyingLog.getDate() + ", Price: " + buyingLog.getTotalPrice())
                .collect(Collectors.toList());
    }

    public void rateProduct(User user, String productId, Double rate) throws Exception {
        Product product = ProductController.getProductById(productId);
        if(!((Customer)user).getRecentShoppingProducts().contains(product))
            throw new Exception("Customer Does'nt buy this Product");
        product.addNumberOfCustomerWhoRated();
        product.addSumOfCustomersRate(rate);
    }

    public Double showBalanceForCustomer(User user) {
        return user.getCredit();
    }

    public ArrayList<String> showDiscountCodes(User user) {
        return (ArrayList<String>) ((Customer)user).getAllDiscountCodes().stream()
                .map(discountCode -> "Code=" + discountCode.getDiscountCode() + ", percent=" + discountCode.getDiscountPercent() + ", maximum=" + discountCode.getMaximumDiscount())
                .collect(Collectors.toList());
    }

}



