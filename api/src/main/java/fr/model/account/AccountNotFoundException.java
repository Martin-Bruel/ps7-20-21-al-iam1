package fr.model.account;

public class AccountNotFoundException extends RuntimeException {

	AccountNotFoundException(long id) {
        super("Could not find Account " + id);
    }
}
