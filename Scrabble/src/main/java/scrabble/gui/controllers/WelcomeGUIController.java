package scrabble.gui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import scrabble.gui.DummyGUI;

public class WelcomeGUIController implements Initializable {

    @FXML AnchorPane mainAnchorPane;

    @FXML
    Button btnStart;
    @FXML
    Button btnClickInstructions;
    @FXML
    MenuButton btnPlayerSelect;
    @FXML
    MenuItem menuDefault;
    @FXML
    Pane pane1;
    @FXML
    Pane pane2;
    @FXML
    TextField textPlayer1;
    @FXML
    TextField textPlayer2;

    private DummyGUI helper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void attachHelper(DummyGUI helper) {
        this.helper = helper;
    }

    public void onPlayersChoosen(ActionEvent event) {

        MenuItem menu = (MenuItem) event.getSource();
        btnPlayerSelect.setText(menu.getText());

        switch (menu.getId()) {
            case "menuPlayer1":
                pane1.setVisible(true);
                pane2.setVisible(false);
                btnStart.setDisable(false);
                break;

            case "menuPlayer2":
                pane1.setVisible(true);
                pane2.setVisible(true);
                btnStart.setDisable(false);
                break;
            default:
                pane1.setVisible(false);
                pane2.setVisible(false);
                btnStart.setDisable(true);
                break;
        }
    }

    public void startGame() {

        // Store the names in ArrayList
        ArrayList<String> playerNames = new ArrayList<>();

        if (!textPlayer1.getText().trim().isEmpty())
            playerNames.add(textPlayer1.getText().trim());
        else
            return;

        if (pane2.isVisible() && !textPlayer2.getText().trim().isEmpty())
            playerNames.add(textPlayer2.getText().trim());
        else if (pane2.isVisible())
            return;

        // reset welcome window
        btnPlayerSelect.setText(menuDefault.getText());
        pane1.setVisible(false);
        pane2.setVisible(false);
        btnStart.setDisable(true);

        this.helper.initializePlayers(playerNames);
        this.helper.startGame();

    }


    private void showInfoDialog(String title, String message) {
        
        Dialog<Void> dialog = new Dialog<Void>();
        dialog.setTitle(title);
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().lookupButton(ButtonType.CLOSE).setVisible(false);
        dialog.showAndWait();
    }


    public void showInstructions(ActionEvent event){
        showInfoDialog("Instructions", this.helper.getInstructions());
    }


}
