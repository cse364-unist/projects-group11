package cse364.project;

class CannotFoundException extends RuntimeException {

    CannotFoundException(String str, Long id) {
        super("Could not find " + str + " " + id + "\n");
    }
}