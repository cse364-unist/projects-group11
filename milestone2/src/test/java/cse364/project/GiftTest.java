package cse364.project;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class GiftTest {
    @Test
    @DisplayName("testing Gift constructor & get methods")
    public void testGiftConstructor() {
        // test gift constructor
        Gift sample = new Gift("happy birthday", Long.valueOf(5));

        // test whether message and movieId are initialized correctly when a Gift object is created
        assertEquals(sample.getMessage(), "happy birthday");
        assertEquals(sample.getMovieId(), Long.valueOf(5));
        // test whether giftId and expireDate are set when a Gift object is created
        assertNotNull(sample.getGiftId());
        assertNotNull(sample.getExpireDate());
    }

    @Test
    @DisplayName("testing Gift default constructor & set methods")
    public void testGiftDefaultConstructor() {
        // test default constructor
        Gift defaultGift = new Gift();
        assertNotNull(defaultGift);

        // test set methods
        defaultGift.setMessage("happy new year");
        defaultGift.setMovieId(Long.valueOf(3));
        defaultGift.setGiftId("gift id");
        defaultGift.setExpireDate("expiration date");
        assertEquals(defaultGift.getMessage(), "happy new year");
        assertEquals(defaultGift.getMovieId(), Long.valueOf(3));
        assertEquals(defaultGift.getGiftId(), "gift id");
        assertEquals(defaultGift.getExpireDate(), "expiration date");
    }
}