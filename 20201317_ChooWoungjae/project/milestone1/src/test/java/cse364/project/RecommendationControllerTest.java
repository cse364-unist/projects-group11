package cse364.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RecommendationControllerTest {

    @InjectMocks
    RecommendationController recommendationController;

    @Mock
    SimpleUserRepository repository;
    @Mock
    CosineSimilarityRepository similarityRepository;
    @Mock
    RatingRepository ratingRepository;

    @Test
    public void testTargetMovie() {
    }
    
}
