package Model;

import java.util.ArrayList;

public abstract class User {
    private String name;
    private String lastName;
    private  String username;
    private String emailAddress;
    private String phoneNumber;
    private  String password;
    private Double credit;
    private Card card;
    public static ArrayList<User> allUsers = new ArrayList<>();


    public User(String name, String lastName, String username, String emailAddress, String  phoneNumber, String password) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.credit = 0.0;
        allUsers.add(this);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String[] getPersonalInformation() {
        String[] arrayOfInformation = new String[7];
        arrayOfInformation[0] = name;
        arrayOfInformation[1] = lastName;
        arrayOfInformation[2] = username;
        arrayOfInformation[3] = emailAddress;
        arrayOfInformation[4] = phoneNumber;
        arrayOfInformation[5] = password;
        arrayOfInformation[6] = credit.toString();
        return arrayOfInformation;
    }

    public void setNewInformation(String name, String lastName, String emailAddress, String  phoneNumber, String password) {
        this.name = name;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Double getCredit() {
        return credit;
    }

    public void payMoney(double payedMoney) {
        credit -= payedMoney;
    }
}
