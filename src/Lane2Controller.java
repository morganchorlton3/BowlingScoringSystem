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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Lane2Controller {
    //Variables
    private int strikeCount;
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
    private int id;
    public Player activePlayer = null;
    //JFX initialization
    @FXML
    Label alertLabel;
    /* Lane 1 */
    @FXML
    Button lane2BowlBtn, orderBtn;
    @FXML
    Label lane2score, lane2score2, lane2score3, lane2add, playerName, playerLabel;
    @FXML
    RadioButton lane2Pin1, lane2Pin2, lane2Pin3, lane2Pin4, lane2Pin5, lane2Pin6, lane2Pin7, lane2Pin8, lane2Pin9, lane2Pin10;
    @FXML
    List<RadioButton> radios = new ArrayList<>(Arrays.asList(lane2Pin1, lane2Pin2, lane2Pin3, lane2Pin4, lane2Pin5, lane2Pin6, lane2Pin7, lane2Pin8, lane2Pin9, lane2Pin10));
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

    private static int getScore(int max) {
        Random rand = new Random();
        int randomnum = rand.nextInt(max + 1);
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
    private void lane2BtnHandle(ActionEvent event) throws IOException {
        ScoreCol();
        if (activePlayer == null){
            activePlayer = getPlayer(count);
            lane2BowlBtn.setText("Bowl ball");
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
                //checkForStrike();
            } else if (count <= size) {
                handleScore(frame, score1, score2);
                getPlayer(count);
                //checkForStrike();
            }
            count++;
            turn=1;
        }else if (turn == 10) {
            System.out.println("Exit program");
            //System.exit(0);
        }
    }
    private void handleScore(int frame, int score1, int score2){
        Player activePlayer = PlayerList.get(count);
        activePlayer.setScore(frame, score1, score2);
    }
    private void takeTurn1() {
        score1 = getScore(max);
        String score1String = String.valueOf(score1);
        lane2score.setText(score1String);
        lane2score.setVisible(true);
        max = 10 - score1;
        lane2BowlBtn.setText("Bowl again");
        handlePins(score1);
        turn++;
    }
    private void takeTurn2(){
        score2 = getScore(max);
        //checkScore();
        String score2String =String.valueOf(score2);
        lane2add.setVisible(true);
        lane2score2.setText(score2String);
        lane2score2.setVisible(true);
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
        lane2score.setVisible(false);
        lane2score2.setVisible(false);
        lane2add.setVisible(false);
        lane2BowlBtn.setText("Next player");
        for (RadioButton button : radios) {
            button.setSelected(true);
        }
        updateScoreboard();
        max = 10;
        if (frame == 10) {
            lane2BowlBtn.setText("Finish Game");
            handleWinner();
        }
        totalScore = 0;
    }
    private void checkScore(){
        if(score1 == 10){
            System.out.println("Strike");
            strikeCount++;
            //activePlayer.setStrikeCount(strikeCount);
            //System.out.println(activePlayer);
        }else if(score1+score2 == 10){
            System.out.println("Spare");
        }
        turn++;
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
            }else{
                message = winnerName + " is the winner";
                alert(message, 10);
            }
        }
        System.out.println(highestScore + " Player ID = " + highestScorePlayerID);
        turn = 10;
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
