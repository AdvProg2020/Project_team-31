package Bank;

import java.util.ArrayList;
import java.util.Date;

public class Token {
    String token;
    String username;
    Date date;

    public Token(String token, String username, Date date) {
        this.token = token;
        this.username = username;
        this.date = date;
    }

    static ArrayList<Token> allToken = new ArrayList<>();

    static Token getTokenByString(String tokenString) {
        for (Token toke : allToken)
            if (toke.token.equals(tokenString))
                return toke;
        return null;
    }

    static void deleteTokenOfUser(String username) {
        for (Token token : allToken) {
            if (token.username.equals(username))
                allToken.remove(token);
            break;
        }
    }
}
