package cse364.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    public void testGetAll() {
        giftController.all();
        verify(giftRepository).findAll();
    }

    @Test
    public void testPost() {
        Gift sample1 = new Gift("message", Long.valueOf(1));
        giftController.newGift(sample1);
        verify(giftRepository).save(sample1);
    }
    
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
        when(giftRepository.findById(sample3.getGiftId())).thenReturn(Optional.of(sample3));
        LocalDate today = LocalDate.now();
        LocalDate alreadyExpiredDate = today.minusMonths(6);
        String dateTimeString = dateFormatter(alreadyExpiredDate);
        sample3.setExpireDate(dateTimeString);
        assertThrows(CannotFoundException.class, () -> giftController.one(sample3.getGiftId()));
    }

    public String dateFormatter(LocalDate date) {
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MAX);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String dateTimeString = dateTime.format(formatter);
        return dateTimeString;
    }
}