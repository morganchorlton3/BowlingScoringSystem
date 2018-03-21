import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    Player testPlayer = new Player(1,"TestPlayer", "Left", "Yes", 0,false,false);
    /*
        Test Get Methods
    */
    //Test Player Name
    @Test
    public void getName() throws Exception {
        assertEquals("TestPlayer", testPlayer.getName());
    }
    //Test Player Preference
    @Test
    public void testPreference() throws Exception {
        assertEquals("Left", testPlayer.getPreference());
    }
    //Test Player Barriers
    @Test
    public void testbarriers() throws Exception {
        assertEquals("Yes", testPlayer.getBarriers());
    }
    //Test Strike Count
    @Test
    public void testStrikeCount() throws Exception {
        assertEquals(0, testPlayer.getStrikeCount());
    }
    //Test LastStrike Boolean
    @Test
    public void testLastStrike() throws Exception {
        assertEquals(false, testPlayer.isLastStrike());
    }
    //Test ExtraGo Boolean
    @Test
    public void testExtraGo() throws Exception {
        assertEquals(false, testPlayer.isExtraGo());
    }
    /*

        Test Set Methods

    */
    //Player Name
    @Test
    public void testSetName() throws Exception {
        testPlayer.setName("TestPlayerSetName");
        assertEquals("TestPlayerSetName", testPlayer.getName());
    }
    //Test Player Preference
    @Test
    public void testSetPreference() throws Exception {
        testPlayer.setPreference("Right");
        assertEquals("Right", testPlayer.getPreference());
    }
    //Test Player Barriers
    @Test
    public void testSetbarriers() throws Exception {
        testPlayer.setBarriers("No");
        assertEquals("No", testPlayer.getBarriers());
    }
    //Test Strike Count
    @Test
    public void testSetStrikeCount() throws Exception {
        testPlayer.setStrikeCount(2);
        assertEquals(2, testPlayer.getStrikeCount());
    }
    //Test LastStrike Boolean
    @Test
    public void testSetLastStrike() throws Exception {
        testPlayer.setLastStrike(true);
        assertEquals(true, testPlayer.isLastStrike());
    }
    //Test ExtraGo Boolean
    @Test
    public void testSetExtraGo() throws Exception {
        testPlayer.setExtraGo(true);
        assertEquals(true, testPlayer.isExtraGo());
    }
}