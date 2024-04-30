package cse364.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

@ExtendWith(MockitoExtension.class)
public class GiftControllerTest {
    @InjectMocks
    GiftController giftController;

    @Mock
    GiftRepository giftRepository;
    @Mock
    MongoTemplate mongoTemplate;
    
    @Test
    public void testGet() {
        // test whether giftController.one() returns the target Gift correctly when the target Gift exists
        Gift sample1 = new Gift("happy birthday", Long.valueOf(1));
        when(giftRepository.findById(sample1.getGiftId())).thenReturn(Optional.of(sample1));
        Gift result = giftController.one(sample1.getGiftId());
        assertEquals(sample1, result);

        // test whether giftController.one() throws proper exception when the target Gift doesn't exist
        Optional<Gift> sample2 = Optional.empty();
        String invalidGiftId = "invalid-gift-id";
        when(giftRepository.findById(invalidGiftId)).thenReturn(sample2);
        assertThrows(CannotFoundException.class, () -> giftController.one(invalidGiftId));

        // test whether giftController.one() calls giftRepository.delete() when the target Gift is already expired
        Gift sample3 = new Gift("happy birthday", Long.valueOf(1));
        // expiredate 만료된 testcase 만들어야 함
    }
}