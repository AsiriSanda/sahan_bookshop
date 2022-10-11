package util;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static Object validate(LinkedHashMap<TextField, Pattern> map, Button btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.setStyle("-fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 0px 0px 2px 0px; -fx-text-fill: red");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-background-color: transparent; -fx-border-color: #655CC2; -fx-border-width: 0px 0px 2px 0px; -fx-text-fill: green");
        }
        btn.setDisable(false);
        return true;
    }
}
