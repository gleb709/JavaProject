package sample;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static java.lang.Integer.parseInt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DeleteOrderController {

    private ObservableList<Order> orders = FXCollections.observableArrayList();

    private String userName;

    private ArrayList<String> list = new ArrayList<String>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button stepBackButton;

    @FXML
    private Button deleteOrderButton;

    @FXML
    private TextField orderId;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, String> productName;

    @FXML
    private TableColumn<Order, String> productPrice;

    @FXML
    private TableColumn<Order, String> productID;

    @FXML
    void initialize() throws InterruptedException {
        productName.setCellValueFactory(new PropertyValueFactory<Order, String>("id"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Order, String>("cost"));
        productID.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));

        stepBackButton.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) stepBackButton.getScene().getWindow();
            stage1.close();
        });

        table();

        deleteOrderButton.setOnAction(actionEvent -> {
            Connection connect = new Connection();

            int flag = 0;

            String id = orderId.getText();

            int flag1 = 0;
            try {
                Integer.parseInt(id);
            } catch(Exception e) {
                flag1 = 1;
            }
            if( flag1 == 0) {
                for(int i = 0; i < list.size(); i++){
                    if(list.get(i).equals(id))
                        flag = 1;
                }
               if(flag == 1) {
                   Order order = new Order();
                   order.setId(id);
                   try {
                       Object obj = connect.Connect(order, "delete");
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   try {
                       table();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   ordersTable.getItems().clear();
                   try {
                       table();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }else{
                   Shake button = new Shake(deleteOrderButton);
                   button.playAnim();
               }
            }else{
                Shake button = new Shake(deleteOrderButton);
                button.playAnim();
            }
        });



    }
    public void table() throws InterruptedException {

        list.clear();

        Connection connect = new Connection();
        Order order = new Order();

        order.setCost(getUserName());

        Object obj = connect.Connect(order, "userOrders");

        ArrayList<Order> product1 = (ArrayList<Order>) obj;
        Iterator<Order> iterator = product1.listIterator();
        int count = 0;
        while(iterator.hasNext()){
            list.add(product1.get(count).getId());
            orders.add(new Order(product1.get(count).getId(),
                    product1.get(count).getCost(),product1.get(count).getDate(),
                    product1.get(count).getTime()));
            iterator.next();
            count++;
        }
        ordersTable.setItems(orders);

    }
}
