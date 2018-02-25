import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    public static final AtomicInteger id = new AtomicInteger(0);
    public int playerID;
    public String name;
    private String prefrence;
    private String barriers;

    public Player(){
        this.playerID = id.incrementAndGet();
        this.name = "";
        this.prefrence = "Left";
        this.barriers = "Yes";
    }

    public Player( int playerID, String Name, String preference, String barriers) {
        this.playerID = playerID;
        this.name = Name;
        this.prefrence = preference;
        this.barriers = barriers;
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
        return prefrence;
    }

    public void setPreference(String prefrence) {
        this.prefrence = prefrence;
    }

    public String getBarriers() {
        return barriers;
    }

    public void setBarriers(String barriers) {
        this.barriers = barriers;
    }

    @Override
    public String toString () {
        return "Player{" +
                "id='" + playerID + '\'' +
                "Name='" + name + '\'' +
                ", Preference='" + prefrence + '\'' +
                ", barrires=" + barriers +
                '}';
    }
}

