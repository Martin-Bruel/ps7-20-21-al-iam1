package fr.model.transaction;

public class TransactionNotFoundException extends RuntimeException{

    TransactionNotFoundException(long id) {
        super("Could not find transaction " + id);
    }

}
