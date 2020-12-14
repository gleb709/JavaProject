package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminOrdersController {

    private ObservableList<Order> orders = FXCollections.observableArrayList();
    private ObservableList<OrderInfo> products = FXCollections.observableArrayList();
    private ObservableList<Product> product = FXCollections.observableArrayList();
    private ArrayList<String> list = new ArrayList<String>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button stepBackButton;

    @FXML
    private Button loadOrderButton;

    @FXML
    private Button finishOrderButton;

    @FXML
    private TableView<OrderInfo> productsSelected;

    @FXML
    private TableColumn<OrderInfo, String> productName1;

    @FXML
    private TableColumn<OrderInfo, String> productPrice1;

    @FXML
    private TableColumn<OrderInfo, String> prodactNumber;

    @FXML
    private TableColumn<OrderInfo, String> productID1;

    @FXML
    private TableColumn<OrderInfo, String> productInfo1;

    @FXML
    private TableColumn<OrderInfo, String> productsCost;

    @FXML
    private TextField addProductId;

    @FXML
    private Button tableButton1;

    @FXML
    private TableView<Order> OrdersTable;

    @FXML
    private TableColumn<Order,String> orderID;

    @FXML
    private TableColumn<Order,String> orderLogin;

    @FXML
    private TableColumn<Order,String> orderPrice;

    @FXML
    private TableColumn<Order,String> orderDate;

    @FXML
    void initialize() throws InterruptedException {

        orderID.setCellValueFactory(new PropertyValueFactory<Order, String>("id"));
        orderPrice.setCellValueFactory(new PropertyValueFactory<Order, String>("cost"));
        orderDate.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));
        orderLogin.setCellValueFactory(new PropertyValueFactory<Order, String>("login"));
        productName1.setCellValueFactory(new PropertyValueFactory<OrderInfo, String>("productName"));
        productID1.setCellValueFactory(new PropertyValueFactory<OrderInfo, String>("productID"));
        prodactNumber.setCellValueFactory(new PropertyValueFactory<OrderInfo, String>("count"));
        productInfo1.setCellValueFactory(new PropertyValueFactory<OrderInfo, String>("productInfo"));
        productsCost.setCellValueFactory(new PropertyValueFactory<OrderInfo, String>("price"));
        productPrice1.setCellValueFactory(new PropertyValueFactory<OrderInfo, String>("productCost"));

        tableFirst();

        loadOrderButton.setOnAction(actionEvent -> {
            productsSelected.getItems().clear();
            Connection connect = new Connection();
            Order order = new Order();
            String id = addProductId.getText();

            order.setId(id);
            int flag = 0;
            int key = 0;
            int flag1 = 0;
            try {
                Integer.parseInt(id);
            } catch(Exception e) {
                flag1 = 1;
            }
            if( flag1 == 0) {
                for(int i = 0; i < list.size(); i++)
                    if (list.get(i).equals(id)){
                        key = i;
                        flag = 1;
                    }
                if(flag == 1) {
                    order.setId(id);
                    order.setLogin(orders.get(key).getLogin());
                    Object obj = new Object();
                    try {
                        obj = connect.Connect(order, "openOrder");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ArrayList<OrderInfo> product1 = (ArrayList<OrderInfo>) obj;
                    Iterator<OrderInfo> iterator = product1.listIterator();
                    int count = 0;
                    while(iterator.hasNext()){
                        products.add(new OrderInfo(product1.get(count).getCount(),product1.get(count).getProductName(),product1.get(count).getProductCost(),
                                product1.get(count).getProductID(), product1.get(count).getProductInfo(), product1.get(count).getPrice()));
                        iterator.next();
                        count++;
                    }
                    productsSelected.setItems(products);
                }else{
                    Shake button = new Shake(addProductId);
                    button.playAnim();
                }
            }else{
                Shake button = new Shake(addProductId);
                button.playAnim();
            }
        });

        stepBackButton.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) stepBackButton.getScene().getWindow();
            stage1.close();
        });

        tableButton1.setOnAction(actionEvent -> {
            Connection connect = new Connection();
            Order order = new Order();
            String id = addProductId.getText();

            order.setId(id);

            int flag = 0;
            int key = 0;
            int flag1 = 0;
            try {
                Integer.parseInt(id);
            } catch(Exception e) {
                flag1 = 1;
            }
            if( flag1 == 0) {
                for(int i = 0; i < list.size(); i++)
                    if (list.get(i).equals(id)){
                        key = i;
                        flag = 1;
                    }
                if(flag == 1) {
                    order.setId(id);
                    order.setLogin(orders.get(key).getLogin());
                    Object obj = new Object();
                    try {
                        obj = connect.Connect(order, "openOrder");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ArrayList<OrderInfo> product1 = (ArrayList<OrderInfo>) obj;
                    Iterator<OrderInfo> iterator = product1.listIterator();
                    int sum = 0;
                    int sumTrash = 0;
                    int sumFood = 0;
                    int sumAnimal = 0;
                    int count = 0;
                    while(iterator.hasNext()){
                        sum += Integer.parseInt(product1.get(count).getPrice());
                        products.add(new OrderInfo(product1.get(count).getCount(),product1.get(count).getProductName(),product1.get(count).getProductCost(),
                                product1.get(count).getProductID(), product1.get(count).getProductInfo(), product1.get(count).getPrice()));
                        iterator.next();
                        count++;
                    }
                    iterator = product1.listIterator();
                    Stage stage = new Stage();
                    Scene scene = new Scene(new Group());
                    stage.setTitle("Заказ");
                    stage.setWidth(500);
                    stage.setHeight(500);
                    ObservableList<PieChart.Data> pieChartData =
                            FXCollections.observableArrayList();
                    count = 0;
                    int sumControl = 0;
                    System.out.println(sum);
                    for(int i = 0; i < product1.size(); i++){
                        System.out.println(sum/Integer.parseInt(product1.get(count).getProductCost()));
                        if(i != product1.size()-1) {
                            pieChartData.add(new PieChart.Data(product1.get(count).getProductName(), sum / Integer.parseInt(product1.get(count).getProductCost())));
                            sumControl += sum/Integer.parseInt(product1.get(count).getProductCost());
                        } else {
                            pieChartData.add(new PieChart.Data(product1.get(count).getProductName(), 100 - sumControl));
                            System.out.println(100-sumControl);
                        }
                        iterator.next();
                        count++;

                    }
                    final PieChart chart = new PieChart(pieChartData);
                    chart.setTitle("Заказ №: " + orders.get(key).getLogin() + id);
                    ((Group) scene.getRoot()).getChildren().add(chart);
                    stage.setScene(scene);
                    stage.show();
                }else{
                    Shake button = new Shake(addProductId);
                    button.playAnim();
                }
            }else{
                Shake button = new Shake(addProductId);
                button.playAnim();
            }

        });

         finishOrderButton.setOnAction(actionEvent -> {
            Connection connect = new Connection();

            int flag = 0;

            String id = addProductId.getText();

            int flag1 = 0;
            try {
                Integer.parseInt(id);
            } catch(Exception e) {
                flag1 = 1;
            }
            int key = 0;
            if( flag1 == 0) {
                for(int i = 0; i < list.size(); i++){
                    if(list.get(i).equals(id)){
                        flag = 1;
                        key = i;
                    }
                }
                if(flag == 1) {
                    orders.clear();
                    Order order = new Order();
                    order.setId(id);
                    list.clear();
                    try {
                        Object obj = connect.Connect(order, "delete");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        tableFirst();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    OrdersTable.getItems().clear();
                    productsSelected.getItems().clear();
                    try {
                        tableFirst();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    Shake button = new Shake(addProductId);
                    button.playAnim();
                }
            }else{
                Shake button = new Shake(addProductId);
                button.playAnim();
            }
        });
    }

    public void tableFirst() throws InterruptedException {

        list.clear();

        Connection connect = new Connection();
        Order order = new Order();

        Object obj = connect.Connect(order, "allOrders");

        ArrayList<Order> product1 = (ArrayList<Order>) obj;
        Iterator<Order> iterator = product1.listIterator();
        int count = 0;
        while(iterator.hasNext()){
            list.add(product1.get(count).getId());
            orders.add(new Order(product1.get(count).getId(),product1.get(count).getLogin(),
                    product1.get(count).getCost(),product1.get(count).getDate(),
                    product1.get(count).getTime()));
            iterator.next();
            count++;
        }
        OrdersTable.setItems(orders);

    }

}
