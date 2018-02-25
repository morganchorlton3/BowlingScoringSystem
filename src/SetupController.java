import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
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
import java.util.Iterator;
import java.util.Optional;


public class SetupController{

    private String barriersResult;
    private String preferenceResult;
    private static final int MAX_Players = 8;
    @FXML
    Label alertLabel;
    @FXML
    Button addButton, delButton;
    @FXML
    TableView table;
    @FXML
    TextField nameInput;
    @FXML
    ComboBox<String> comboPreference;
    @FXML
    RadioButton radioButtonYes, radioButtonNo ;
    @FXML
    private TableColumn<String, String> nameCol;
    @FXML
    private String message;
    private void alert(String mesage){
        alertLabel.setText(message);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> alertLabel.setVisible(false)),
                new KeyFrame(Duration.seconds( 0.1), evt -> alertLabel.setVisible(true)));
        timeline.setCycleCount(3);
        timeline.play();

    }
    @FXML
    private void AddBtnhandle(ActionEvent event) {
        while (checkMaxPlayers(table)) {
            if (checkName(nameInput.getText()) == true && checkPreference((String) comboPreference.getValue()) == true && checkBarriers(getBarriersResult()) == true) {
                Player newPlayer = new Player();
                newPlayer.setName(nameInput.getText());
                newPlayer.setPreference(comboPreference.getValue());
                newPlayer.setBarriers(getBarriersResult());
                nameInput.clear();
                table.getItems().add(newPlayer);
                ArrayList<Player> players = new ArrayList<Player>();
                players.add(newPlayer);
                System.out.println(newPlayer);
                Iterator itr=players.iterator();

                //traverse elements of ArrayList object
                while(itr.hasNext()){
                    Player st=(Player)itr.next();
                    System.out.println(st.id + " " + st.name);
                }
                break;
            }else {
                break;
            }
        }
    }
    @FXML
    private void delBtnHandle(ActionEvent event) {
        ObservableList<Player> productSelected, allPlayers;
        allPlayers = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();

        productSelected.forEach(allPlayers::remove);
    }
    @FXML
    private void startBtnHandle(ActionEvent event)throws IOException {
        if (checkMinPlayers(table) == true) {

            int playerList = table.getItems().size();
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setHeaderText("Are you sure you want to start the game?");
            confirmAlert.setContentText("You have added " + playerList + " players to the game is this correct?");
            Optional<ButtonType> option = confirmAlert.showAndWait();
            if (option.get() == ButtonType.OK) {
                String playerName = nameCol.toString().split(",")[0].substring(1);
                System.out.println(playerName);
                Stage stage;
                Parent root;
                stage = (Stage) addButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("Game.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
                stage.setWidth(1400);

            } else if (option.get() == ButtonType.CANCEL) {
                confirmAlert.close();
            }
        }
    }
    public String getPreferenceResult(){
        if (comboPreference.getValue() == "Left"){
            preferenceResult = "Left";
        } else if (comboPreference.getValue() == "Right"){
            preferenceResult = "Right";
        }
        return preferenceResult;
    }
    public String getBarriersResult(){
        if (radioButtonYes.isSelected()){
            barriersResult = "Yes";
        } else if (radioButtonNo.isSelected()) {
            barriersResult = "No";
        }else {
            System.out.println("No button selected");
        }
        return barriersResult;
    }
    public boolean checkMinPlayers(TableView table) {
        int playerList = table.getItems().size();
        if (playerList < 2){
            message = "Please add at least 2 players to start a game";
            alert(message);
            return false;
        }else{
            return true;
        }
    }
    public boolean checkMaxPlayers(TableView table){
        int playerList = table.getItems().size();

        if (playerList == MAX_Players){
            message = "You have reached the maximum ammount of players for one game.";
            alert(message);
            return false;
        }else{
            return true;
        }
    }
    public boolean checkName(String nameInput){
        if (nameInput.equals("")){
            message = "The player name can't be blank";
            alert(message);
            return false;
        }else if (nameInput.length() > 22){
            message = "The player name can't be longer than 22 characters";
            alert(message);
            return false;
        }
        else{
            return true;
        }
    }
    public boolean checkPreference(String preferenceInput){
        if (preferenceInput == null){
            message = "Please select a preference via the dropdown menu on the Left.";
            alert(message);
            return false;
        }else{
            return true;
        }
    }
    public boolean checkBarriers (String barriersResult){
        if (barriersResult == null){
            message = "Please select whether you want barriers up or down to play the game";
            alert(message);
            return false;
        }else{
            return true;
        }
    }
}
