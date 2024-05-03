package cse364.project;

class InvalidUserException extends RuntimeException {

    InvalidUserException(String str, String val) {
        super("Invalid user (" + str + ": " + val + ")\n");
    }
}