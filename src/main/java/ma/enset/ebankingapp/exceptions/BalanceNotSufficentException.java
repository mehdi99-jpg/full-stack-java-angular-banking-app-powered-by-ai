package ma.enset.ebankingapp.exceptions;

public class BalanceNotSufficentException extends Exception {
    public BalanceNotSufficentException(String message) {
        super(message);
    }
}
