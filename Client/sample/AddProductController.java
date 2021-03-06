package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addProductButton;

    @FXML
    private Button stepBackButton1;

    @FXML
    private TextField productName;

    @FXML
    private TextField productPrice;

    @FXML
    private TextField productType;

    @FXML
    private TextField productInfo;

    @FXML
    void initialize() {

        addProductButton.setOnAction(actionEvent -> {
            String name = productName.getText();
            String price = productPrice.getText();
            String type = productType.getText();
            String info = productInfo.getText();

            if(name.length() != 0 && price.length() != 0 && type.length() != 0 && info.length() != 0){
                if(type.equals("animal") || type.equals("food") || type.equals("trash")){
                    int flag = 0;
                    try{
                        Integer.parseInt(price);
                    }catch(Exception e){
                       flag = 1;
                    }
                    if(flag == 0){
                        Connection connect = new Connection();
                        Product product = new Product();

                        product.setProductName(name);
                        product.setProductCost(price);
                        product.setProductType(type);
                        product.setProductInfo(info);

                        Object obj = new Object();

                        try {
                            obj = connect.Connect(product,"newProduct");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Shake button = new Shake(productPrice);
                        button.playAnim();
                    }
                }else{
                    Shake button = new Shake(productType);
                    button.playAnim();
                }
            }else{
                Shake button = new Shake(addProductButton);
                button.playAnim();
            }
        });

        stepBackButton1.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) stepBackButton1.getScene().getWindow();
            stage1.close();
        });
    }
}
