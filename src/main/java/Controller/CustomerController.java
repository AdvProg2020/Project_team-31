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

    private Card createCard() {
        return (new Card());
    }

    public ArrayList<String> showCard(User user, Card savedCard) {
        Card card = savedCard;
        if(user != null) {
            card = user.getCard();
        }
        ArrayList<String> arrayOfInformation = new ArrayList<>();
        for (Product product : card.getProductsInThisCard().keySet()) {
            ProductInCard productInCard = card.getProductsInThisCard().get(product);
            String productInformation = "productId: " + product.getProductId() + " name: " + product.getName() + " price: " + product.getSellersOfThisProduct().get(productInCard.getSeller()) + " numberOfProduct: " + productInCard.getNumber();
            arrayOfInformation.add(productInformation);
        }
        arrayOfInformation.add(String.valueOf(showTotalPrice(card)));

        return arrayOfInformation;
    }

    public ArrayList<String> showProductInCard(User user, Card card) {
        HashMap<Product, ProductInCard> products;
        if(user == null) {
            products = card.getProductsInThisCard();
        } else {
            products = user.getCard().getProductsInThisCard();
        }
        ArrayList<String> arrayOfProducts = new ArrayList<>();
        int i = 0;
        for (Product product : products.keySet()) {
            arrayOfProducts.add((++i) + ". productId: " + product.getProductId() + " ,nameOfProduct: " + product.getName() + " ,number: " + products.get(product).getNumber() + " ,sellerUsername: " + products.get(product).getSeller().getUsername());
        }
        return arrayOfProducts;
    }

    public void changeNumberOfProductInCard(User user, Card card, String productId, int changingNum) throws Exception {
        Card newCard = card;
        if (user != null) {
            newCard = user.getCard();
        }
        HashMap<Product, ProductInCard> products = newCard.getProductsInThisCard();

        Product productToChange = ProductController.getProductById(productId);
        if (productToChange == null) {
            throw new Exception("There is not any product with this id");
        }
        if (!products.containsKey(productToChange)) {
            throw new Exception("customer doesn't add this product to card yet");
        }
        ProductInCard productInCard = products.get(productToChange);
        if (productToChange.getAvailable() < productInCard.getNumber() + changingNum) {
            throw new Exception("Inventory of product is not enough");
        }
        if (productInCard.getNumber() + changingNum < 0) {
            throw new Exception("number has entered is invalid");
        }
        productInCard.changeNumberOfProduct(changingNum);
        if (productInCard.getNumber() == 0) {
            newCard.removeProductFromCard(productToChange);
        }
    }

    public void addProductToCard(User user,Card savedCard, Product product, String sellerUsername) throws Exception {
        Card card = savedCard;
        if(user != null) {
            card = user.getCard();
        }

        if (product.getProductStatus() == ProductAndOffStatus.creating)
            throw new Exception("Product is in creating progress yet!");
        if (product.getProductStatus() == ProductAndOffStatus.editing)
            throw new Exception("product is in editing progress!");
        if (card.getProductsInThisCard().containsKey(product)) {
            throw new Exception("You have add this product to card before!");
        }

        User seller = LoginController.getUserByUsername(sellerUsername);
        if (seller == null) {
            throw new Exception("There is not Seller with this userName");
        }
        if (seller instanceof Seller) {
            if (product.getSellersOfThisProduct().containsKey(seller)) {
                if (product.getAvailable() == 0)
                    throw new Exception("Inventory of product is not enough");
                card.addProductToCard(new ProductInCard(product, (Seller) seller));
                return;
            }
        }
        throw new Exception("There is not seller with this username for this product");
    }

    int showTotalPrice(Card card) {
        SellerController.getInstance().checkTimeOfOffs();
        HashMap<Product, ProductInCard> productsInThisCard = card.getProductsInThisCard();
        List<ProductInCard> products = new ArrayList<>(productsInThisCard.values());
        int totalPrice = 0;
        for (ProductInCard product : products) {
            ArrayList<Off> offs = product.getProduct().getOffs();
            int percent = 100;
            for (Off off : offs) {
                if (off.getSeller().equals(product.getSeller())) {
                    percent -= off.getOffPercent();
                    break;
                }
            }
            totalPrice += (product.getProduct().getSellersOfThisProduct().get(product.getSeller()) * product.getNumber() * percent / 100);
        }
        return totalPrice;
    }

    public BuyingLog createBuyingLog(User user, String[] information) throws Exception {
        for (Product product : user.getCard().getProductsInThisCard().keySet()) {
            if (product.getAvailable() < user.getCard().getProductsInThisCard().get(product).getNumber())
                throw new Exception("number of " + product.getName() + "is more than it's availability.");
        }
        return new BuyingLog("BuyingLog" + (Log.getNumberOfLogCreated() +1) , showTotalPrice(user.getCard()), (Customer) user, user.getCard().getProductsInThisCard(), information);
    }

    public void putDiscount(User user, BuyingLog buyingLog, String discountCodeString) throws Exception {
        DiscountCode discount = ManagerController.getInstance().getDiscountById(discountCodeString);
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
        buyingLog.finishBuying(new Date());
        user.payMoney(buyingLog.getTotalPrice() - buyingLog.getDiscountAmount());
        ((Customer) user).addBuyingLog(buyingLog);
        ((Customer) user).addRecentShoppingProducts(buyingLog.getBuyingProducts().keySet());
        createSellingLog(buyingLog);
        user.setCard(new Card());
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
                    percent = off.getOffPercent();
                    break;
                }
            }
            int originalPrice = product.getSellersOfThisProduct().get(seller) * productInCard.getNumber();
            int offAmount = originalPrice * percent / 100;
            seller.getMoney(originalPrice - offAmount);
            seller.addSellingLog(new SellingLog("SellingLog" + (Log.getNumberOfLogCreated() +1), buyingLog.getDate() ,originalPrice - offAmount, offAmount, product, buyingLog.getCustomer()));
            product.decreaseNumberOfProduct(productInCard.getNumber());
        }
    }

    public String showOrder(User user, String orderId) throws Exception {
        for (BuyingLog buyingLog : ((Customer) user).getAllBuyingLogs()) {
            if (buyingLog.getLogId().equals(orderId)) {
                return "Id: " + buyingLog.getLogId() + ", Date: " + buyingLog.getDate() + ", Price: " + buyingLog.getTotalPrice() + ", Products: " + buyingLog.getBuyingProducts().keySet();
            }
        }
        throw new Exception("There is not order with this id!");
    }

    public ArrayList<String> showAllOrders(User user) {
        return (ArrayList<String>) ((Customer) user).getAllBuyingLogs().stream()
                .map(buyingLog -> "Id: " + buyingLog.getLogId() + ", Date: " + buyingLog.getDate() + ", Price: " + buyingLog.getTotalPrice())
                .collect(Collectors.toList());
    }

    public void rateProduct(User user, String productId, int rate) throws Exception {
        Product product = ProductController.getProductById(productId);
        if(product == null) {
            throw new Exception("There is not product with this id");
        }
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
                .map(discountCode -> "Code=" + discountCode.getDiscountCode() + ", percent=" + discountCode.getDiscountPercent() + ", maximum=" + discountCode.getMaximumDiscount() + ", BeginTime=" + discountCode.getBeginTime() + ", EndTime=" + discountCode.getEndTime())
                .collect(Collectors.toList());
    }

}