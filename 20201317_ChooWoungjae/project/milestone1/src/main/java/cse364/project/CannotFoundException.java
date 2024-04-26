package cse364.project;

class CannotFoundException extends RuntimeException {

    <T> CannotFoundException(String str, T id) {
        super("Could not find " + str + " " + id + "\n");
    }
}