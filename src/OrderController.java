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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;

public class OrderController {
    @FXML
    TableView table;
    @FXML
    Button addBtn, delBtn, orderBtn, backBtn;
    @FXML
    Label alertLabel;
    @FXML
    ComboBox<String> comboItem, comboQuantity;
    private String message;
    public static ArrayList<Order> priceList = new ArrayList<Order>();
    private static DecimalFormat df2 = new DecimalFormat(".##");
    private Double price, total;
    private int quantity;
    private void alert(String message){
        alertLabel.setText(message);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> alertLabel.setVisible(false)),
                new KeyFrame(Duration.seconds( 0.1), evt -> alertLabel.setVisible(true)));
        timeline.setCycleCount(3);
        timeline.play();
    }
    @FXML
    private void AddBtnhandle(ActionEvent event) {
        if (checkItem((String) comboItem.getValue()) == true && checkQuantity((String) comboQuantity.getValue()) == true) {
            Order newOrder = new Order();
            String item = comboItem.getValue();
            System.out.println(item);
            quantity = Integer.parseInt(comboQuantity.getValue());
            newOrder.setItem(comboItem.getValue());
            newOrder.setQuantity(comboQuantity.getValue());
            newOrder.setPrice(setPrice(item, quantity));
            priceList.add(newOrder);
            table.getItems().add(newOrder);
            System.out.println(newOrder);
        }
    }
    @FXML
    private void delBtnHandle(ActionEvent event) {
        Order selectedItem = (Order)table.getSelectionModel().getSelectedItem();
        priceList.remove(selectedItem);
        ObservableList<Order> productSelected, allOrders;
        allOrders = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();

        productSelected.forEach(allOrders::remove);
    }
    @FXML
    private void startBtnHandle(ActionEvent event)throws IOException {
        if (checkMinItems(table) == true) {
            int orderList = table.getItems().size();
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setHeaderText("Confirm your order");
            confirmAlert.setContentText("You have added " + orderList + " to your order is this correct, your total is £" + df2.format(getTotal()));
            Optional<ButtonType> option = confirmAlert.showAndWait();
            if (option.get() == ButtonType.OK) {
                Stage stage;
                Parent root;
                stage = (Stage) orderBtn.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("Game.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.close();
            }
        }
    }
    @FXML
    private void backBtnHandle(ActionEvent event)throws IOException {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setHeaderText("Cancel order");
        confirmAlert.setContentText("Are you sure you want to cancel your order?");
        Optional<ButtonType> option = confirmAlert.showAndWait();
        if (option.get() == ButtonType.OK) {
            Stage orderStage = new Stage();
            Parent root;
            orderStage = (Stage) backBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Game.fxml"));
            Scene scene = new Scene(root);
            orderStage.setScene(scene);
            orderStage.close();
        }else if (option.get() == ButtonType.CANCEL) {
            confirmAlert.close();
        }
    }
    public boolean checkMinItems(TableView table) {
        int orderList = table.getItems().size();
        if (orderList < 1){
            message = "Please add at least 1 item to your ord";
            alert(message);
            return false;
        }else{
            return true;
        }
    }
    private double setPrice(String item, int quantity){
        if(item.equals("Burger")){
            return price = 1.99 * quantity;
        }else if(item.equals("Fries")){
            return price = 1.25 * quantity;
        }else if(item.equals("Hot Dog")){
            return price = 1.69 * quantity;
        }else if(item.equals("Soda")){
            return price = 0.99 * quantity;
        }else if(item.equals("Coffee")){
            return price = 1.55 * quantity;
        }else if(item.equals("Tea")){
            return price = 1.45 * quantity;
        }else if(item.equals("Water")){
            return price = 0.69 * quantity;
        }else if(item.equals("Slushy")){
            return price = 1.99 * quantity;
        }
        return 0.0;
    }
    private double getTotal(){
        double total = 0;
        for (int i = 0; i < priceList.size(); i++) {
            total += priceList.get(i).getPrice();
        }
        return total;
    }
    private boolean checkItem(String itemInput){
        if (itemInput == null){
            message = "Please select a item via the dropdown menu on the Left.";
            alert(message);
            return false;
        }else{
            return true;
        }
    }
    private boolean checkQuantity(String quantityInput){
        if (quantityInput == null){
            message = "Please select the quantity via the dropdown menu on the Left.";
            alert(message);
            return false;
        }else{
            return true;
        }
    }
}
