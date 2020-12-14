package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    private TranslateTransition shake;

    public Shake(Node node){
        shake = new TranslateTransition(Duration.millis(70), node);
        shake.setFromX((-10f));
        shake.setByX(10f);
        shake.setCycleCount(3);
        shake.setAutoReverse(true);
        shake.isAutoReverse();
    }

    public void playAnim(){
        shake.playFromStart();
    }
}
