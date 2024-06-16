package cse364.project;

import java.util.List;

public interface MovieService {
    List<Long> getMovieStats(Long movieId);
}