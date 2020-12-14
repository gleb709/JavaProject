package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTariffController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addTariffButton;

    @FXML
    private Button stepBackButton1;

    @FXML
    private TextField tariffName;

    @FXML
    private TextField tariffPrice;

    @FXML
    private TextField tariffSale;

    @FXML
    private TextField tariffInfo;

    @FXML
    void initialize() {

        addTariffButton.setOnAction(actionEvent -> {
            String name = tariffName.getText();
            String price = tariffPrice.getText();
            String sale = tariffSale.getText();
            String info = tariffInfo.getText();

            if(name.length() != 0 && price.length() != 0 && sale.length() != 0 && info.length() != 0){
                if(Integer.parseInt(sale) < 100 || Integer.parseInt(sale) > 0){
                    int flag = 0;
                    try{
                        Integer.parseInt(price);
                    }catch(Exception e){
                        flag = 1;
                    }
                    if(flag == 0){
                        Connection connect = new Connection();
                        Tariff tariff = new Tariff();

                        tariff.setTariffName(name);
                        tariff.setTariffPrice(price);
                        tariff.setTariffInfo(info);
                        tariff.setTariffSale(sale);

                        Object obj = new Object();

                        try {
                            obj = connect.Connect(tariff,"addTariff");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Shake button = new Shake(tariffPrice);
                        button.playAnim();
                    }
                }else{
                    Shake button = new Shake(tariffSale);
                    button.playAnim();
                }
            }else{
                Shake button = new Shake(addTariffButton);
                button.playAnim();
            }
        });

        stepBackButton1.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) stepBackButton1.getScene().getWindow();
            stage1.close();
        });
    }
}
