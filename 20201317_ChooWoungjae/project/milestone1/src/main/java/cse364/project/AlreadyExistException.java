package cse364.project;

class AlreadyExistException extends RuntimeException {

    AlreadyExistException(String str) {
        super(str + " is already exist!\n");
    }
}