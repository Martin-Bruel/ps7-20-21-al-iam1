package fr.model.store;

class StoreNotFoundException extends RuntimeException {

    StoreNotFoundException(long id) {
        super("Could not find Store " + id);
    }
}
