import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    public static final AtomicInteger id = new AtomicInteger(0);
    public int playerID;
    public String name;
    private String preference;
    private String barriers;
    private String[] scores ;
    private int roundtotal;
    private int total ;
    private int strikeCount;
    boolean lastStrike;

    public Player(){
        this.playerID = id.incrementAndGet();
        this.name = "";
        this.preference = "Left";
        this.barriers = "Yes";
        this.scores = new String[11] ;
        this.strikeCount = 0;
        this.lastStrike = false;
    }
    public Player(int playerID, String Name, String preference, String barriers, int strikeCount, boolean lastStrike) {
        this.playerID = playerID;
        this.name = Name;
        this.preference = preference;
        this.barriers = barriers;
        this.scores = new String[11] ;
        this.strikeCount = strikeCount;
        this.lastStrike = lastStrike;
    }
    public int getPlayerID(){ return playerID; }
    public void setPlayerID() {this.playerID = playerID;}
    public int getStrikeCount(){ return strikeCount; }
    public void setStrikeCount(int strikeCount) {this.strikeCount = strikeCount;}
    public String getName() {
        return name;
    }
    public void setName(String Name) {
        this.name = Name;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getBarriers() {
        return barriers;
    }

    public void setBarriers(String barriers) {
        this.barriers = barriers;
    }

    public void setScore(int frame, int score1, int score2) {
        String scoreString;
        roundtotal = score1 + score2;
        total = total + roundtotal;
        if(score1 == 10){
            scoreString = "X / " + score2;
        }else if (roundtotal == 10){
            scoreString = "/";
        }else {
            scoreString = String.valueOf(score1 + " / " + score2);
        }
        scores[frame] = scoreString;
    }
    public void updateScore(int frame, int newScore){
        setScore(frame-1, 10 , newScore);
    }
    public String getScore(int whichScore) {
        return scores[whichScore] ;
    }
    public int getTotal() {
        return total ;
    }

    public boolean isLastStrike() {
        return lastStrike;
    }

    public void setLastStrike(boolean lastStrike) {
        this.lastStrike = lastStrike;
    }
    @Override
    public String toString () {
        return "Player{" +
                "id='" + playerID + '\'' +
                "Name='" + name + '\'' +
                ", Preference='" + preference + '\'' +
                ", Strike Count='" + strikeCount + '\'' +
                ", barriers=" + barriers + '\'' +
                ", S1=" + getScore(1) + '\'' +
                ", S2=" + getScore(2) + '\'' +
                ", S3=" + getScore(3) + '\'' +
                ", S4=" + getScore(4) + '\'' +
                ", S5=" + getScore(5) + '\'' +
                ", S6=" + getScore(6) + '\'' +
                ", S7=" + getScore(7) + '\'' +
                ", S8=" + getScore(8) + '\'' +
                ", S9=" + getScore(9) + '\'' +
                ", S10=" + getScore(10) + '\'' +
                ", Total =" + total +
                '}';
    }
}

