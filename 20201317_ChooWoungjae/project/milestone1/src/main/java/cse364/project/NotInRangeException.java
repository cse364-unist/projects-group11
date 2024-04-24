package cse364.project;

class NotInRangeException extends RuntimeException {
    NotInRangeException(String str) {
        super("Your " + str + " is not in range\n" );
    }
}