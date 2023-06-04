package BookScrabbleApp.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GUIController {
    @FXML
    private Label welcomeText;
    @FXML
    private ImageView imageView;
    @FXML
    Button pass;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to Scrabble Application!!!");
    }

    public void initialize (URL url, ResourceBundle rb){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/Logo.JPG")));
        imageView.setImage(image);
    }

    public void initialize(ActionEvent actionEvent) {
        System.out.println("Hello World");
    }
}