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
        for (Product product : card.getProductsInThisCard().keySet()) {
            ProductInCard productInCard = card.getProductsInThisCard().get(product);
            String productInformation = "productId: " + product.getProductId() + " name: " + product.getName() + " price: " + product.getSellersOfThisProduct().get(productInCard.getSeller()) + " numberOfProduct: " + productInCard.getNumber();
            arrayOfInformation.add(productInformation);
        }
        arrayOfInformation.add(String.valueOf(showTotalPrice(user)));

        return arrayOfInformation;
    }

    public ArrayList<String> showProductInCard(User user) {
        HashMap<Product, ProductInCard> products = user.getCard().getProductsInThisCard();
        ArrayList<String> arrayOfProducts = new ArrayList<>();
        int i = 0;
        for (Product product : products.keySet()) {
            arrayOfProducts.add( (++i) + ". productId: " + product.getProductId() + " ,nameOfProduct: " + product.getName() + " ,number: " + products.get(product).getNumber() + " ,sellerUsername: " + products.get(product).getSeller().getUsername());
        }
        return arrayOfProducts;
    }

    public Boolean doesSellerHaveThisProduct(String productId, User user) {
        Product product = ProductController.getProductById(productId);
        if (user != null) {
            if (product.getSellersOfThisProduct().keySet().contains((Seller) user)) {
                return true;
            }
        }
        return false;
    }

    public void changeNumberOfProductInCard(User user, String productId, int changingNum) throws Exception {
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
        if (product.getProductStatus() == ProductAndOffStatus.creating)
            throw new Exception("Product is in creating progress yet!");
        if(product.getProductStatus() == ProductAndOffStatus.editing)
            throw new Exception("product is in editing progress!");

        User seller = LoginController.getUserByUsername(sellerUsername);

        if (seller instanceof Seller) {
            if (product.getSellersOfThisProduct().keySet().contains(seller)) {
                if (product.getAvailable() == 0)
                    throw new Exception("Inventory of product is not enough");
                card.addProductToCard(new ProductInCard(product, (Seller) seller));
                return;
            }
        }
        throw new Exception("There is not seller with this username for this product");
    }

    int showTotalPrice(User user) {
        SellerController.getInstance().checkTimeOfOffs();
        HashMap<Product, ProductInCard> productsInThisCard = user.getCard().getProductsInThisCard();
        List<ProductInCard> products = new ArrayList<>(productsInThisCard.values());
        int totalPrice = 0;
        for (ProductInCard product : products) {
            ArrayList<Off> offs = product.getProduct().getOffs();
            int percent = 100;
            for (Off off : offs) {
                if (off.getSeller().equals((Seller) user)) {
                    percent -= off.getOffAmount();
                    break;
                }
            }
            totalPrice += (product.getProduct().getSellersOfThisProduct().get((Seller) user) * product.getNumber() * percent / 100);
        }
        return totalPrice;
    }

    public BuyingLog createBuyingLog(User user, String[] information) throws Exception {
        for (Product product : user.getCard().getProductsInThisCard().keySet()) {
            if(product.getAvailable() < user.getCard().getProductsInThisCard().get(product).getNumber())
                throw new Exception("number of " + product.getName() + "is more than it's availability.");
        }
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

        buyingLog.setDiscountAmount(Math.min(buyingLog.getTotalPrice(), discount.getMaximumDiscount()) * discount.getDiscountPercent() / 100);
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
        for (ProductInCard productInCard : products) {
            Seller seller = productInCard.getSeller();
            Product product = productInCard.getProduct();
            ArrayList<Off> offs = product.getOffs();
            int percent = 0;
            for (Off off : offs) {
                if (off.getSeller().equals(seller)) {
                    percent = off.getOffAmount();
                    break;
                }
            }
            int originalPrice = product.getSellersOfThisProduct().get(seller) * productInCard.getNumber();
            int offAmount = originalPrice * percent / 100;
            seller.getMoney(originalPrice - offAmount);
            seller.addSellingLog(new SellingLog(buyingLog.getDate(),originalPrice - offAmount ,offAmount, product, buyingLog.getCustomer()));
            product.decreaseNumberOfProduct(productInCard.getNumber());
        }
    }

    public String showOrder(User user, String orderId) {
        for (BuyingLog buyingLog : ((Customer) user).getAllBuyingLogs()) {
            if (buyingLog.getLogId().equals(orderId)) {
                return "Id: " + buyingLog.getLogId() + ", Date: " + buyingLog.getDate() + ", Price: " + buyingLog.getTotalPrice();
            }
        }
        return null;
    }

    public ArrayList<String> showAllOrders(User user) {
        return (ArrayList<String>) ((Customer) user).getAllBuyingLogs().stream()
                .map(buyingLog -> "Id: " + buyingLog.getLogId() + ", Date: " + buyingLog.getDate() + ", Price: " + buyingLog.getTotalPrice())
                .collect(Collectors.toList());
    }

    public void rateProduct(User user, String productId, Double rate) throws Exception {
        Product product = ProductController.getProductById(productId);
        if (!((Customer) user).getRecentShoppingProducts().contains(product))
            throw new Exception("Customer Does'nt buy this Product");
        product.addNumberOfCustomerWhoRated();
        product.addSumOfCustomersRate(rate);
    }

    public int showBalanceForCustomer(User user) {
        return user.getCredit();
    }

    public ArrayList<String> showDiscountCodes(User user) {
        return (ArrayList<String>) ((Customer) user).getAllDiscountCodes().stream()
                .map(discountCode -> "Code=" + discountCode.getDiscountCode() + ", percent=" + discountCode.getDiscountPercent() + ", maximum=" + discountCode.getMaximumDiscount())
                .collect(Collectors.toList());
    }

}



