package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class OpenOrderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button stepBackButton;

    @FXML
    private Button openOrderButton;

    @FXML
    private TextField orderId;

    @FXML
    private TableView<?> ordersTable;

    @FXML
    private TableColumn<?, ?> productName;

    @FXML
    private TableColumn<?, ?> productPrice;

    @FXML
    private TableColumn<?, ?> productID;

    @FXML
    private TableColumn<?, ?> productID1;

    @FXML
    void initialize() {


    }
}
