package BookScrabbleApp.View;

import BookScrabbleApp.ViewModel.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.util.*;

public class GuestController {
    //start method
    @FXML
    private Label invalidIPorPort;
    @FXML
    private TextField IpTextFiled;
    @FXML
    private TextField PortTextFiled;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label welcomeText;
    @FXML
    private Button submitBtn;
    @FXML
    private Button switchToGame;
    @FXML
    public TextField nameTextFiled;

    public static String name;

    String ip;
    int port;
    BS_Guest_ViewModel guest = new BS_Guest_ViewModel();

    @FXML
    public void onPressSubmit() throws Exception {
        if (nameTextFiled.getText().equals("")) {
            name = "Guest" + UUID.randomUUID().toString().substring(0, 4);
            name = "Guest" + UUID.randomUUID().toString().substring(0, 4);
        } else {
            name = nameTextFiled.getText();
        }
        guest.setPlayerProperties(name);
        ip = IpTextFiled.getText();
        port = Integer.parseInt(PortTextFiled.getText());
        if (ip.equals("") || port == 0) {
            invalidIPorPort.setText("Please enter IP and Port");
        } else {
            boolean connected = true;
            guest.hostIp.bindBidirectional(IpTextFiled.textProperty());
            guest.hostPort.bindBidirectional(PortTextFiled.textProperty());
            try {
                guest.openSocket();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Connect Error");
                alert.setHeaderText("Connect Error");
                alert.setContentText("The game server is not connected\n" + "Connect the game server and click submit again");
                alert.showAndWait();
                connected = false;
            }
            if (connected) {
//                invalidIPorPort.setText("Game server connected");
                switchToGame.setVisible(true);
                submitBtn.onActionProperty().set(null);
            }
        }
    }

    @FXML
    public void switchToGameWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BookScrabbleApp.View/gameMainWindow.fxml"));
        root = loader.load();
        GameWindowController controller = loader.getController();
        controller.setViewModel(guest);
        stage = (Stage) welcomeText.getScene().getWindow();
        stage.setOnCloseRequest(e -> Platform.exit());
        scene = new Scene(root);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }
}