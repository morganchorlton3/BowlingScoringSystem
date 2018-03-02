import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    public static final AtomicInteger id = new AtomicInteger(0);
    public int playerID;
    public String name;
    private String preference;
    private String barriers;
    private int[] scores ;
    private int total ;

    public Player(){
        this.playerID = id.incrementAndGet();
        this.name = "";
        this.preference = "Left";
        this.barriers = "Yes";
        this.scores = new int[11] ;
    }

    public Player( int playerID, String Name, String preference, String barriers) {
        this.playerID = playerID;
        this.name = Name;
        this.preference = preference;
        this.barriers = barriers;
        this.scores = new int[11] ;

    }
    public int getPlayerID(){ return playerID; }
    public void setPlayerID() {this.playerID = playerID;}
    public String getName() {
        return name;
    }
    public void setName(String Name) {
        this.name = Name;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String prefrence) {
        this.preference = prefrence;
    }

    public String getBarriers() {
        return barriers;
    }

    public void setBarriers(String barriers) {
        this.barriers = barriers;
    }

    public void setScore(int turn, int score) {
        int change = score - scores[turn] ;
        scores[turn] = score ;
        total = total + change ;
    }
    public int getScore(int whichScore) {
        return scores[whichScore] ;
    }
    public int getTotal() {
        return total ;
    }
    @Override
    public String toString () {
        return "Player{" +
                "id='" + playerID + '\'' +
                "Name='" + name + '\'' +
                ", Preference='" + preference + '\'' +
                ", barrires=" + barriers + '\'' +
                ", Total =" + total +
                '}';
    }
}

