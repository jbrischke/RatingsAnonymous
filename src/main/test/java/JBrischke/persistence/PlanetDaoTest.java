package JBrischke.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class PriceDaoTest {

    @Test
    public void getPlanetSuccess() throws Exception {
        PriceDao dao = new PriceDao();
        String name = "minecraft";
        double expectedPriceValue = 16.29;
        assertEquals(expectedPriceValue, dao.getPrice(name).getCurrentLowestPrice());
    }

}
