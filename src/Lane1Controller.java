import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;
import java.io.IOException;

import static java.lang.Thread.*;

public class Lane1Controller {
    //Variables
    private int max =0;
    private String message;
    private int frame = 1;
    private static ArrayList<Player> PlayerList = SetupController.getPlayerList();
    private int totalScore = 0;
    private int turn = 0;
    private int score1 = 0;
    private int score2 = 0;
    private int score3 = 0;
    private int count = 0;
    private String winnerName;
    private boolean lastRoundStrike =  false;
    private boolean simulateStrike = false;
    private int id;
    private int lastTurn= 1;
    public Player activePlayer = null;
    //JFX initialization
    @FXML
    Label alertLabel;
    /* Lane 1 */
    @FXML
    Button lane1BowlBtn, orderBtn, simulateStrikeBtn;
    @FXML
    Label lane1score, lane1score2, lane1score3, lane1add, playerName, playerLabel;
    @FXML
    RadioButton lane1Pin1, lane1Pin2, lane1Pin3, lane1Pin4, lane1Pin5, lane1Pin6, lane1Pin7, lane1Pin8, lane1Pin9, lane1Pin10;
    @FXML
    List<RadioButton> radios = new ArrayList<>(Arrays.asList(lane1Pin1, lane1Pin2, lane1Pin3, lane1Pin4, lane1Pin5, lane1Pin6, lane1Pin7, lane1Pin8, lane1Pin9, lane1Pin10));
    @FXML
    TableView scoreboard;
    @FXML
    TableColumn<Player, String> scores1col, scores2col, scores3col, scores4col, scores5col, scores6col, scores7col, scores8col, scores9col, scores10col;

    private void alert(String message, int duration) {
        alertLabel.setText(message);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> alertLabel.setVisible(false)),
                new KeyFrame(Duration.seconds(0.1), evt -> alertLabel.setVisible(true)));
        timeline.setCycleCount(duration);
        timeline.play();

    }

    private int getScore(int max) {
        Random rand = new Random();
        int randomnum = rand.nextInt(max + 1);
        if (simulateStrike){
            randomnum = 10;
            simulateStrike = false;
        }
        return randomnum;
    }
    private Player getPlayer(int count) {
        Player activePlayer = PlayerList.get(count);
        playerLabel.setVisible(true);
        playerName.setVisible(true);
        String name = activePlayer.getName();
        playerName.setText(name);
        return activePlayer;
    }
    @FXML
    private void lane1BtnHandle(ActionEvent event) throws IOException {
        ScoreCol();
        if (activePlayer == null){
            activePlayer = getPlayer(count);
            lane1BowlBtn.setText("Bowl ball");
            turn = 1;
        }else if (turn == 1){
            takeTurn1();
        }else if (turn == 2){
            takeTurn2();
        }else if (turn == 3){
            takeTurn3();
            int size = PlayerList.size();
            if (count == size) {
                count = 0;
                frame++;
                handleScore(frame, score1, score2);
                getPlayer(count);
            } else if (count <= size) {
                handleScore(frame, score1, score2);
                getPlayer(count);
            }
            count++;
        }else if (lastTurn == 1){
            takeTurn1();
            lastTurn++;
        }else if (lastTurn == 2){
            takeTurn2();
            lastTurn++;
        }else if (lastTurn == 3){
            handleWinner();
        }else if (turn == 10) {
            handleWinner();
            lane1BowlBtn.setText("Exit Application");
            turn++;
        }else if (turn==11){
            System.out.println("Exit program");
            System.exit(0);
        }
    }
    private void handleScore(int frame, int score1, int score2){
        Player activePlayer = PlayerList.get(count);
        activePlayer.setScore(frame, score1, score2);
        int newScore = score1+score2;
        lastRoundStrike = activePlayer.isLastStrike();
        boolean ExtraGo = activePlayer.isExtraGo();
        if (lastRoundStrike){
            activePlayer.setScore(frame-1, 10, newScore);
            activePlayer.setLastStrike(false);
        }else if (lastRoundStrike && frame == 10){
            lastTurn=1;
        }else if (ExtraGo){
            int LastnewScore = score1 + score2;
            activePlayer.setScore(frame-1, 10, LastnewScore);
        }
    }
    private void takeTurn1() {
        score1 = getScore(max);
        String score1String = String.valueOf(score1);
        lane1score.setText(score1String);
        lane1score.setVisible(true);
        max = 10 - score1;
        lane1BowlBtn.setText("Bowl again");
        handlePins(score1);
        turn++;
        if(score1 == 10){
            message = "Strike";
            alert(message, 1);
        }
    }
    private void takeTurn2(){
        score2 = getScore(max);
        //checkScore();
        String score2String =String.valueOf(score2);
        lane1add.setVisible(true);
        lane1score2.setText(score2String);
        lane1score2.setVisible(true);
        for (RadioButton button : radios) {
            button.setSelected(true);
        }
        int size = PlayerList.size();
        totalScore = score1 + score2;
        if(totalScore == 10){
            message = "Spare";
            alert(message, 1);
        }
        handlePins(totalScore);
        //handleLastGo();
        turn++;
    }
    private void takeTurn3() {
        System.out.println(frame);
        lane1score.setVisible(false);
        lane1score2.setVisible(false);
        lane1add.setVisible(false);
        lane1BowlBtn.setText("Next player");
        for (RadioButton button : radios) {
            button.setSelected(true);
        }
        updateScoreboard();
        max = 10;
        if (frame == 10) {
            SetLastGo();
            CheckLastGo();
            lane1BowlBtn.setText("Finish Game");
            turn=10;
        }else{
            turn = 1;
        }
        totalScore = 0;
    }
    private void SetLastGo(){
        for (int i = 0; i < PlayerList.size(); i++) {
            String lastGoScore = activePlayer.getScore(10);
            if (lastGoScore.contains("X")) {
                activePlayer.setLastStrike(true);
            } else {
                activePlayer.setLastStrike(false);
            }
        }
    }
    private void CheckLastGo(){
        for (int i = 0; i < PlayerList.size(); i++) {
            boolean lastRoundStrike = activePlayer.isLastStrike();
            if (lastRoundStrike){
                System.out.println(activePlayer);
            }
        }
    }
    private void handlePins(int score){
        int counter = 0;
        while(score > counter ){
            RadioButton button = radios.get(counter);
            if (counter == score) {
                counter = 0;
                break;
            }else if (button.isSelected() == true){
                button.setSelected(false);
                counter ++;
            }else if( button.isSelected() == false) {
                button.setSelected(false);
                System.out.println("Error");
                counter ++;
            }
        }
    }
    private void handleWinner(){
        int highestScore = 0;
        int highestScorePlayerID = 0;
        for (int i = 0; i < PlayerList.size(); i++) {
            Player activePlayer = PlayerList.get(i);
            if (activePlayer.getTotal() > highestScore){
                highestScore = activePlayer.getTotal();
                highestScorePlayerID = activePlayer.getPlayerID();
                winnerName = activePlayer.getName();
            }else if(activePlayer.getTotal() == highestScore){
                message = "It's a tie";
                alert(message, 10);
            }
        }
        message = winnerName + " is the winner";
        alert(message, 10);
        System.out.println(highestScore + " Player ID = " + highestScorePlayerID);
        lane1BowlBtn.setText("Finish Game");
    }
    public void initialize() {
        for (RadioButton button : radios) {
            button.setDisable(true);
            button.setOpacity(1);
        }
        for (int i = 0; i < PlayerList.size(); i++) {
            scoreboard.getItems().add(PlayerList.get(i));
        }
    }
    public void updateScoreboard(){
        scoreboard.getItems().clear();
        for (int i = 0; i < PlayerList.size(); i++) {
            scoreboard.getItems().add(PlayerList.get(i));
        }
    }
    /* Table View */
    @FXML
    public void ScoreCol(){
        scores1col.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getScore(1)));
        scores2col.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getScore(2)));
        scores3col.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getScore(3)));
        scores4col.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getScore(4)));
        scores5col.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getScore(5)));
        scores6col.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getScore(6)));
        scores7col.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getScore(7)));
        scores8col.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getScore(8)));
        scores9col.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getScore(9)));
        scores10col.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getScore(10)));
    }
    /* Order Food */
    @FXML
    private void SimulateStrike(ActionEvent event) throws IOException{
        simulateStrike = true;
    }
    @FXML
    private void orderBtnHandle(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Order.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Catering Service");
        stage.setScene(new Scene(root1));
        stage.show();
    }


    public void main(String[] args) {
        for (RadioButton button : radios) {
            button.setDisable(true);
            button.setOpacity(0);
        }
    }
}
