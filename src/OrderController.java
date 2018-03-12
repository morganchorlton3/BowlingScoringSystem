import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class OrderController {
    @FXML
    TableView orderTable;
    @FXML
    ComboBox<String> comboItem, comboQuantity;
    @FXML
    private void addItemBtnhandle(ActionEvent event){
        if(comboItem.getValue() != "" && comboQuantity.getValue() != ""){
            String item = comboItem.getValue();
            String Quantity = comboQuantity.getValue();
        }
    }
    @FXML
    private void remItemBtnHandle(ActionEvent event) {
        ObservableList<Player> productSelected, allPlayers;
        allPlayers = orderTable.getItems();
        productSelected = orderTable.getSelectionModel().getSelectedItems();

        productSelected.forEach(allPlayers::remove);
    }
}
