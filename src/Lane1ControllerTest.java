import org.junit.Test;

import static org.junit.Assert.*;

public class Lane1ControllerTest {
    @Test
    public void testScore() throws Exception {
        int score = Lane1Controller.getScore(10);
        assertTrue("Error, random is too high", 10 >= score);
        assertTrue("Error, random is too low",  0  <= score);
        System.out.println("Random Number is " + score);
    }

}