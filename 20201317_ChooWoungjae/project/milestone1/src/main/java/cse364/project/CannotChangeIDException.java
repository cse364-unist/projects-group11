package cse364.project;

class CannotChangeIDException extends RuntimeException {

    CannotChangeIDException(String str) {
        super("You can't change the " + str + "_ID\n");
    }
}