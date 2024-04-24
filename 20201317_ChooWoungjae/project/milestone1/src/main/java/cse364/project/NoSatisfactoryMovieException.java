package cse364.project;

class NoSatisfactoryMovieException extends RuntimeException {

    NoSatisfactoryMovieException() {
        super("There is no satisfactory movie\n");
    }
}