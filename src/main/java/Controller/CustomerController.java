package Controller;

import Model.Auction;
import Model.*;

import java.util.*;
import java.lang.String;
import java.util.stream.Collectors;

public class CustomerController {
    private static CustomerController customerControllerInstance = new CustomerController();

    private CustomerController() {
    }

    public void addCredit(User user, int credit) {
        user.getMoney(credit);
    }

    public static CustomerController getInstance() {
        return customerControllerInstance;
    }

    public Card createCard() {
        return (new Card());
    }

    public Customer getCustomerByUsername(String username) {
        for (Customer customer : Customer.getAllCustomers())
            if (customer.getUsername().equals(username))
                return customer;
        return null;
    }

    public ArrayList<String> showCard(User user, Card savedCard) {
        Card card = savedCard;
        if (user != null) {
            card = user.getCard();
        }
        ArrayList<String> arrayOfInformation = new ArrayList<>();
        int i = 0;
        for (Product product : card.getProductsInThisCard().keySet()) {
            ProductInCard productInCard = card.getProductsInThisCard().get(product);
            String productInformation = (++i) + "productId: " + product.getProductId() + ", name: " + product.getName() + ", price: " + product.getSellersOfThisProduct().get(productInCard.getSeller()) + ", numberOfProduct: " + productInCard.getNumber() + ", seller" + productInCard.getSeller().getUsername();
            arrayOfInformation.add(productInformation);
        }
        arrayOfInformation.add("totalPrice: " + showTotalPrice(card));

        return arrayOfInformation;
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

    public void addProductToCard(User user, Card savedCard, Product product, String sellerUsername) throws Exception {
        Card card = savedCard;
        if (user != null) {
            card = user.getCard();
        }
        if (product.getProductStatus() == ProductAndOffStatus.CREATING)
            throw new Exception("Product is in creating progress yet!");
        if (product.getProductStatus() == ProductAndOffStatus.EDITING)
            throw new Exception("product is in editing progress!");
        if (card.getProductsInThisCard().containsKey(product)) {
            throw new Exception("You have add this product to card before!");
        }
        User seller = LoginController.getUserByUsername(sellerUsername);
        if (seller == null) {
            throw new Exception("There is not Seller with this userName");
        }
        SellerController.getInstance().checkTimeOfAuctions();
        for (Auction auction : product.getAuctions()) {
            if(auction.getSeller().equals(sellerUsername)) {
                throw new Exception("This product is in auction");
            }
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

    public int showTotalPrice(Card card) {
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
        return new BuyingLog("BuyingLog" + new Date(), showTotalPrice(user.getCard()), (Customer) user, user.getCard().getProductsInThisCard(), information);
    }

    public String isAvailabilityOk(User user) {
        for (Product product : user.getCard().getProductsInThisCard().keySet()) {
            if (product.getAvailable() < user.getCard().getProductsInThisCard().get(product).getNumber())
                return "number of " + product.getName() + "is more than it's availability.";
        }
        return "Ok";
    }

    public void putDiscount(User user, String logId, String discountCodeString) throws Exception {
        DiscountCode discount = ManagerController.getInstance().getDiscountById(discountCodeString);
        if (discount.getBeginTime().after(new Date()) || discount.getEndTime().before(new Date()))
            throw new Exception("DiscountCode is unavailable this time");
        if (discount.getDiscountTimesForEachCustomer().get(user) == 0)
            throw new Exception("User has used this code before");

        BuyingLog buyingLog = getBuyingLogById(logId);
        buyingLog.setDiscountAmount(Math.min(buyingLog.getTotalPrice(), discount.getMaximumDiscount()) * discount.getDiscountPercent() / 100);
        discount.decreaseDiscountTimesForEachCustomer((Customer) user);
    }

    public void payMoney(User user, String  logId) throws Exception {
        BuyingLog buyingLog = getBuyingLogById(logId);
        if (buyingLog.getTotalPrice() - buyingLog.getDiscountAmount() > user.getCredit())
            throw new Exception("Credit of money is not enough");
        buyingLog.finishBuying(new Date());
        BuyingLog.notCompleted.remove(buyingLog);
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
            seller.addSellingLog(new SellingLog("SellingLog" + new Date(), buyingLog.getDate(), originalPrice - offAmount, offAmount, product, buyingLog.getCustomer()));
            product.decreaseNumberOfProduct(productInCard.getNumber());
        }
    }

    public void createBuyingLogForAuction(Auction auction) {
        Customer customer = (Customer) LoginController.getUserByUsername(auction.getLastCustomer());
        Product product = ProductController.getProductById(auction.getProductId());
        Seller seller = (Seller) LoginController.getUserByUsername(auction.getSeller());
        HashMap<Product, ProductInCard> products = new HashMap<>();
        products.put(product, new ProductInCard(product, seller));
        String[] information = new String[2];
        information[0] = customer.getPersonalInformation()[3];
        information[1] = customer.getPersonalInformation()[4];
        BuyingLog buyingLog = new BuyingLog("BuyingLog" + new Date(), auction.getOfferedPrice(), customer, products,information);
        buyingLog.finishBuying(new Date());
        BuyingLog.notCompleted.remove(buyingLog);
        customer.addBuyingLog(buyingLog);
        customer.addRecentShoppingProducts(products.keySet());
        seller.getMoney(auction.getOfferedPrice());
        seller.addSellingLog(new SellingLog("SellingLog" + new Date(), buyingLog.getDate(), auction.getOfferedPrice(), 0 , product, customer));
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

    public ArrayList<BuyingLog> showAllOrdersByList(User user) {
        return ((Customer) user).getAllBuyingLogs();
    }

    public void rateProduct(User user, String productId, int rate) throws Exception {
        Product product = ProductController.getProductById(productId);
        if (product == null) {
            throw new Exception("There is not product with this id");
        }
        if (!((Customer) user).getRecentShoppingProducts().contains(product))
            throw new Exception("You have'nt buy this Product");
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

    private BuyingLog getBuyingLogById(String id) {
        for (BuyingLog buyingLog : BuyingLog.notCompleted) {
            if(buyingLog.getLogId().equals(id))
                return buyingLog;
        }
        return null;
    }
}