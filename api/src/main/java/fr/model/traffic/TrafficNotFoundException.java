package fr.model.traffic;

class TrafficNotFoundException extends RuntimeException {

    TrafficNotFoundException(long id) {
        super("Could not find Traffic " + id);
    }
}
