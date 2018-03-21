import org.junit.Test;

import static org.junit.Assert.*;

public class OrderTest {
    Order testOrder = new Order("burger", "1");
    @Test
    public void getItem() throws Exception {
        assertEquals("burger", testOrder.getItem());
    }

    @Test
    public void setItem() throws Exception {
        testOrder.setItem("HotDog");
        assertEquals("HotDog", testOrder.getItem());
    }

    @Test
    public void getQuantity() throws Exception {
        assertEquals("1", testOrder.getQuantity());
    }

    @Test
    public void setQuantity() throws Exception {
        testOrder.setQuantity("5");
        assertEquals("5", testOrder.getQuantity());
    }
}