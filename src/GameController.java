import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.*;
import java.io.IOException;

public class GameController {
    /* Universal */
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
    private int max = 10;
    private static int getscore(int max) {
        Random rand = new Random();
        int  randomnum = rand.nextInt(max) + 0;
        return randomnum;
    }
    /* Lane 1 */
    private int totalScore = 0;
    @FXML
    Button lane1BowlBtn,orderBtn;
    @FXML
    Label lane1score,lane1score2,lane1add;
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
    private TableColumn<Integer, Integer> s1;
    private int turn = 1;
    @FXML
    private void lane1BtnHandle(ActionEvent event)throws IOException {
        if (turn == 1) {
            int score1 = getscore(max);
            String score1String = String.valueOf(score1);
            totalScore = totalScore + score1;
            int score2 = score1;
            handleSound(score1, score2);
            lane1score.setText(score1String);
            lane1score.setVisible(true);
            max = 10 - score1;
            lane1BowlBtn.setText("Bowl again");
            int count = 0;
            if (score1 == 10) {
                message = "Strike";
                alert(message);
                turn = 3;
                lane1BowlBtn.setText("Next player");
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
                else{
                    break;
                }
            }
            count = 1;
            turn ++;
        }else if (turn == 2){
            lane1add.setVisible(true);
            int score2 = getscore(max);
            String score2String =String.valueOf(score2);
            totalScore = totalScore + score2;
            int score1 = score2;
            handleSound(score1, score2);
            lane1score2.setText(score2String);
            lane1score2.setVisible(true);
            lane1BowlBtn.setText("Next player");
            int count2 = 0;
            for (RadioButton button : radios) {
                button.setSelected(true);
            }
            if (totalScore == 10){
                message = "Spare";
                alert(message);
                turn = 3;
                lane1BowlBtn.setText("Next player");
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
            count2 = 0;
            turn ++;

        }else if (turn == 3){
            lane1score.setVisible(false);
            lane1score2.setVisible(false);
            lane1add.setVisible(false);
            lane1BowlBtn.setText("Bowl ball");
            max = 10;
            turn = 1;
            totalScore = 0;
            for (RadioButton button : radios) {
                button.setSelected(true);
            }
        }
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
    /* Order Food */
    @FXML
    private void orderBtnHandle(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        stage = (Stage) orderBtn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Order.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void initialize() {
        for (RadioButton button : radios) {
            button.setDisable(true);
            button.setOpacity(1);
        }
        Player newPlayer = new Player();
        newPlayer.setName("Morgan");
        scoreboard.getItems().add(newPlayer);
    }
}
