import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class GameController {
    //Variables
    private String message;
    private int round = 1;
    private int count = 0;
    private static ArrayList<Player> PlayerList = SetupController.getPlayerList();
    private int totalScore = 0;
    private static int turn = 0;
    private int max = 10;
    private int score1 = 0;
    private int score2 = 0;
    private int score3 = 0;
    //JFX initialization
    @FXML
    Label alertLabel;
    /* Lane 1 */
    @FXML
    Button lane1BowlBtn,orderBtn;
    @FXML
    Label lane1score,lane1score2,lane1add, playerName,playerLabel;
    @FXML
    RadioButton lane1Pin1, lane1Pin2, lane1Pin3, lane1Pin4, lane1Pin5, lane1Pin6, lane1Pin7, lane1Pin8, lane1Pin9,lane1Pin10;
    @FXML
    List<RadioButton> radios = new ArrayList<>(Arrays.asList(lane1Pin1, lane1Pin2, lane1Pin3, lane1Pin4, lane1Pin5, lane1Pin6, lane1Pin7, lane1Pin8, lane1Pin9, lane1Pin10));
    @FXML
    TableView scoreboard;
    @FXML
    TableColumn <Player, String> scores1col, scores2col, scores3col, scores4col, scores5col, scores6col, scores7col, scores8col, scores9col, scores10col;
    private void alert(String message){
        alertLabel.setText(message);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> alertLabel.setVisible(false)),
                new KeyFrame(Duration.seconds( 0.1), evt -> alertLabel.setVisible(true)));
        timeline.setCycleCount(3);
        timeline.play();

    }
    private static int getscore(int max) {
        Random rand = new Random();
        int  randomnum = rand.nextInt(max+1) + 0;
        return randomnum;
    }
    @FXML
    private void lane1BtnHandle(ActionEvent event)throws IOException {
        while(round <= 10){
            if (turn == 0){
                lane1BowlBtn.setText("Bowl ball");
                Player activePlayer = PlayerList.get(0);
                playerLabel.setVisible(true);
                playerName.setVisible(true);
                String name = activePlayer.getName();
                playerName.setText(name);
                turn = 1;
            }else if (turn == 1) {
                takeTurn1();
                break;
            }else if (turn == 2){
                takeTurn2();
                break;
            }else if (turn == 3){
                takeTurn3();
                break;
            }
        }
        if (turn == 4){
            takeLastTurn();
        }else if (turn == 5){
            takeLastTurn2();
        }else if(turn == 6){
            takeLastTurn3();
        }else if (turn == 7){
            System.out.println("Game Over");
        }
    }
    private void takeTurn1(){
        score1 = getscore(max);
        String score1String = String.valueOf(score1);
        //handleSound(score1, score2);
        lane1score.setText(score1String);
        lane1score.setVisible(true);
        max = 10 - score1;
        lane1BowlBtn.setText("Bowl again");
        int count = 0;
        while(score1 >= count ){
            RadioButton button = radios.get(count);
            if (count == score1) {
                break;
            }else if (button.isSelected() == true){
                button.setSelected(false);
                count ++;
            }else if( button.isSelected() == false) {
                button.setSelected(false);
                System.out.println("Error");
                count ++;
            }
        }
        if (score1 == 10) {
            handleStrike();
        }
        turn++;
    }
    private void takeTurn2(){
        score2 = getscore(max);
        String score2String =String.valueOf(score2);
        lane1add.setVisible(true);
        //handleSound(score1, score2);
        lane1score2.setText(score2String);
        lane1score2.setVisible(true);
        lane1BowlBtn.setText("Next player");
        int count2 = 0;
        for (RadioButton button : radios) {
            button.setSelected(true);
        }
        if (totalScore == 10){
            handleSpare();
        }
        while(totalScore >= count2 | totalScore != 10){
            RadioButton button = radios.get(count2);
            if (count2 == totalScore) {
                break;
            }else if (button.isSelected() == true){
                button.setSelected(false);
                count2 ++;
            }else if( button.isSelected() == false) {
                button.setSelected(false);
                count2 ++;
            }
        }
        turn ++;
    }
    private void takeTurn3(){
        lane1score.setVisible(false);
        lane1score2.setVisible(false);
        lane1add.setVisible(false);
        lane1BowlBtn.setText("Bowl ball");
        for (RadioButton button : radios) {
            button.setSelected(true);
        }
        int size = PlayerList.size();
        if (count == size){
            round++;
            count = 0;
            handleScore(round, score1, score2);
            selectPlayer(count);
        }else if(count <= size){
            handleScore(round, score1, score2);
            selectPlayer(count);
        }
        updateScoreboard();
        count++;
        max = 10;
        turn = 1;
        totalScore=0;
        score1 = 0;
        score2 = 0;
        if(round == 11){
            turn =4;
        }
    }
    public void takeLastTurn(){
        score1 = getscore(max);
        String score1String = String.valueOf(score1);
        //handleSound(score1, score2);
        lane1score.setText(score1String);
        lane1score.setVisible(true);
        max = 10 - score1;
        lane1BowlBtn.setText("Bowl again");
        int count = 0;
        if (score1 == 10) {
            handleStrike();
        }
        while(score1 >= count ){
            RadioButton button = radios.get(count);
            if (count == score1) {
                break;
            }else if (button.isSelected() == true){
                button.setSelected(false);
                count ++;
            }else if( button.isSelected() == false) {
                button.setSelected(false);
                System.out.println("Error");
                count ++;
            }
        }
    }
    public void takeLastTurn2(){
        score2 = getscore(max);
        String score2String =String.valueOf(score2);
        lane1add.setVisible(true);
        //handleSound(score1, score2);
        lane1score2.setText(score2String);
        lane1score2.setVisible(true);
        lane1BowlBtn.setText("Next player");
        int count2 = 0;
        for (RadioButton button : radios) {
            button.setSelected(true);
        }
        if (totalScore == 10){
            handleSpare();
        }
        while(totalScore >= count2 | totalScore != 10){
            RadioButton button = radios.get(count2);
            if (count2 == totalScore) {
                break;
            }else if (button.isSelected() == true){
                button.setSelected(false);
                count2 ++;
            }else if( button.isSelected() == false) {
                button.setSelected(false);
                count2 ++;
            }
        }
        turn=7;
    }
    public void takeLastTurn3(){
        score3 = getscore(10);
        String score2String =String.valueOf(score2);
        lane1add.setVisible(true);
        //handleSound(score1, score2);
        lane1score2.setText(score2String);
        lane1score2.setVisible(true);
        handleFinalTotal(score3);
        endGame();
    }
    private void endGame(){
        for (int i = 0; i < PlayerList.size(); i++) {
            scoreboard.getItems().add(PlayerList.get(i));
        }
    }
    private void handleScore(int round, int score1, int score2){
        Player activePlayer = PlayerList.get(count);
        activePlayer.setScore(round, score1, score2);
        System.out.println(activePlayer);
        System.out.println(round);
    }
    private void handleFinalTotal(int score3){
        Player activePlayer = PlayerList.get(count);
        activePlayer.setScore(10, score3, 0);
    }
    private void handleSound(int score1, int score2){
        try {
            if(score1 == 0 | score1 == 0){
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:/Music/bowling1.wav").getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }else if(score1 > 0 | score2 > 0){
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:/Music/bowling3.wav").getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }

    }
    private void handleStrike(){
        message = "Strike";
        alert(message);
        Player activePlayer = PlayerList.get(0);
        activePlayer.setScore(round, 10,10);
        turn++;
    }
    private void handleSpare(){
        message = "Spare";
        alert(message);
        turn = 3;
        lane1BowlBtn.setText("Next player");
    }
    public void initialize() {
        lane1BowlBtn.setText("Start Game");
        for (RadioButton button : radios) {
            button.setDisable(true);
            button.setOpacity(1);
        }
        for (int i = 0; i < PlayerList.size(); i++) {
            scoreboard.getItems().add(PlayerList.get(i));
        }
    }
    public void selectPlayer(int count){
        Player activePlayer = PlayerList.get(count);
        playerLabel.setVisible(true);
        playerName.setVisible(true);
        String name = activePlayer.getName();
        playerName.setText(name);
        ScoreCol();
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
        int size = PlayerList.size();
        System.out.println("Size =  " + size + " + Count = " + count );
    }


    public void main(String[] args) {
        for (RadioButton button : radios) {
            button.setDisable(true);
            button.setOpacity(0);
        }
    }
}
