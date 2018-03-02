import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    int round = 0;
    public int count = 1;
    public static ArrayList<Player> PlayerList = SetupController.getPlayerList();
    int totalScore = 0;
    private static int turn = 1;
    private int max = 10;
    //JFX initialization
    @FXML
    Label alertLabel;

    String message;
    private void alert(String mesage){
        alertLabel.setText(message);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> alertLabel.setVisible(false)),
                new KeyFrame(Duration.seconds( 0.1), evt -> alertLabel.setVisible(true)));
        timeline.setCycleCount(3);
        timeline.play();

    }
    private static int getscore(int max) {
        Random rand = new Random();
        int  randomnum = rand.nextInt(max) + 0;
        return randomnum;
    }
    /* Lane 1 */
    public ArrayList<Integer> roundScore = new ArrayList<>();
    @FXML
    Button lane1BowlBtn,orderBtn;
    @FXML
    Label lane1score,lane1score2,lane1add, playerName;
    @FXML
    RadioButton lane1Pin1, lane1Pin2, lane1Pin3, lane1Pin4, lane1Pin5, lane1Pin6, lane1Pin7, lane1Pin8, lane1Pin9,lane1Pin10;
    @FXML
    List<RadioButton> radios = new ArrayList<>(Arrays.asList(lane1Pin1, lane1Pin2, lane1Pin3, lane1Pin4, lane1Pin5, lane1Pin6, lane1Pin7, lane1Pin8, lane1Pin9, lane1Pin10));
    public void main(String[] args) {
        for (RadioButton button : radios) {
            button.setDisable(true);
            button.setOpacity(0);
        }
    }
    @FXML
    TableView scoreboard;
    @FXML
    private void lane1BtnHandle(ActionEvent event)throws IOException {
        takeTurn();
    }
    public void takeTurn(){
        if (turn == 1) {
            taketurn1();
        }else if (turn == 2){
            taketurn2();
        }else if (turn == 3){
            taketurn3();
        }
    }
    public void taketurn1(){
        int score1 = getscore(max);
        String score1String = String.valueOf(score1);
        roundScore.add(score1);
        totalScore = totalScore + score1;
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
            }else{
                break;
            }
        }
        int score = score1;
        turn ++;
    }
    public void taketurn2(){
        lane1add.setVisible(true);
        int score2 = getscore(max);
        String score2String =String.valueOf(score2);
        roundScore.add(score2);
        totalScore = totalScore + score2;
        int score1 = score2;
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
        int score = score2;
        turn ++;
    }
    public void taketurn3(){
        lane1score.setVisible(false);
        lane1score2.setVisible(false);
        lane1add.setVisible(false);
        lane1BowlBtn.setText("Bowl ball");
        for (RadioButton button : radios) {
            button.setSelected(true);
        }
        int size = PlayerList.size();
        if (count == size){
            count = 0;
            selectPlayer(count);
        }else if(count <= size){
            selectPlayer(count);
        }
        count++;
        max = 10;
        turn = 1;
        totalScore = 0;
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
        turn = 3;
        lane1BowlBtn.setText("Next player");
    }
    private void handleSpare(){
        message = "Spare";
        alert(message);
        turn = 3;
        lane1BowlBtn.setText("Next player");
    }
    public void initialize() {
        for (RadioButton button : radios) {
            button.setDisable(true);
            button.setOpacity(1);
        }
        for (int i = 0; i < PlayerList.size(); i++) {
            scoreboard.getItems().add(PlayerList.get(i));
        }
        selectPlayer(0);
    }
    public void selectPlayer(int count){
        Player activePlayer = PlayerList.get(count);
        playerName.setVisible(true);
        String name = activePlayer.getName();
        playerName.setText(name);
    }
    /* Order Food */
    @FXML
    private void orderBtnHandle(ActionEvent event) throws IOException{
        int size = PlayerList.size();
        System.out.println("Size =  " + size + " + Count = " + count );
    }
}
