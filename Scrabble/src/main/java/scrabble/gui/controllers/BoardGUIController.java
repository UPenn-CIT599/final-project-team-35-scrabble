package scrabble.gui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import scrabble.Cell;
import scrabble.Letter;
import scrabble.events.BoardEvent;
import scrabble.events.LetterEvent;
import scrabble.gui.Animations;
import scrabble.gui.DummyGUI;

public class BoardGUIController implements Initializable {

    private DummyGUI helper;
    private Stage stage;
    private boolean swapMode, tileBagActive;


    private Set<String> selectedLetters;
    private String selectedLetter;

    @FXML
    AnchorPane boardAnchor, anchorPaneTileBag;

    @FXML
    VBox vBox1, vBox2, vBoxTileBag;

    @FXML
    Button btn1, btn2, btn3, btn4, btn5, btnSave, btnExit, btnCloseTileBag;

    @FXML
    HBox hBoxLetters, board;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        swapMode = false;
        tileBagActive = false;
        selectedLetters = new HashSet<>();
        selectedLetter = null;

    }

    public void attachHelper(DummyGUI helper, Stage stage) {
        this.helper = helper;
        this.stage = stage;
    }

    public void initGUI() {

        initializeBoard();
        initializeLetterRack(this.helper.getLettersTray());
        initializeScoreCard();
    }

    private void initializeScoreCard() {

        ArrayList<String> players = this.helper.getPlayerNames();
        for (String n : players) {
            Label name = new Label(n);
            name.getStyleClass().add("scoreboard");
            Label score = new Label("0");
            score.getStyleClass().add("scoreboard");
            HBox card = new HBox(6, name, score);
            card.getStyleClass().add("scorecard");
            card.setId(n);
            HBox.setHgrow(name, Priority.ALWAYS);
            HBox.setHgrow(score, Priority.ALWAYS);
            vBox1.getChildren().add(card);
        }

        higlightCurrentPlayer(this.helper.getCurrentPlayerName());
    }

    // Set a whole new LetterRack
    public void initializeLetterRack(ArrayList<Letter> letterTray) {

        // DEMO :
        // letterTray = new ArrayList<>();
        // letterTray.add(new Letter("A"));
        // letterTray.add(new Letter("B"));
        // letterTray.add(new Letter("C"));
        // letterTray.add(new Letter("D"));
        // DEMO END
        btn4.setDisable(true);

        int id = 0;
        hBoxLetters.getChildren().clear();

        for (Letter l : letterTray) {

            Button btn = new Button();
            btn.getStyleClass().add("letters");
            String style = "-fx-background-image: url('/images/pieces/" + l.getLetterName()
                    + ".png'); -fx-background-size: 64px 64px;";
            btn.setStyle(style);
            btn.setId(id + l.getLetterName());
            id++;
            HBox.setHgrow(btn, Priority.ALWAYS);
            btn.setPrefWidth(100000);
            btn.setPrefHeight(100000);
            btn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Button btn = (Button) event.getSource();
                    System.out.println("Swap : " + swapMode);
                    // System.out.println(btn.getId());

                    if (!swapMode) {
                        LetterEvent le = new LetterEvent(btn);
                        selectedLetter = le.getLetter();
                        helper.onLetterTrayEvent(le);
                    } else {
                        selectedLetters.add(btn.getId());
                    }
                }
            });
            
            hBoxLetters.getChildren().add(btn);
        }
        Animations.BounceInTransition(hBoxLetters);
    }

    private void initializeBoard() {

        double boardWidth = boardAnchor.getWidth();
        double tileSize = boardWidth / 30;
        vBox2.setSpacing(2);

        // ArrayList<Cell[]> board = this.helper.getBoard();

        for (int i = 0; i < 15; i++) {
            HBox row = new HBox(2);
            for (int j = 0; j < 15; j++) {
                Button tile = getTile(tileSize);
                tile.setId(i + "," + j);
                this.updateBoard(tile, getTileColor(i, j));
                // tile.setText(board.get(i)[j].getCellValues());
                tile.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        Button tile = (Button) event.getSource();
                        if (!swapMode && selectedLetter != null) {
                            btn3.setDisable(true);
                            btn4.setDisable(false);
                            System.out.println(tile.getId());
                            helper.onClickBoardEvent(new BoardEvent(tile));
                        }
                    }
                });
                row.getChildren().add(tile);
            }
            vBox2.getChildren().add(row);
        }
    }

    private Button getTile(double size) {

        Button tile = new Button();
        tile.setPrefHeight(size);
        tile.setPrefWidth(size);
        return tile;

    }

    // --------------------------------------------------------------- //
    // Functions to be called for updating the GUI
    // Called from DummyGUI

    // TileBagClick
    public void handleTileBag(ActionEvent event) {
        // 1. Get the tile bag using helper
        // 2. Show the tile bag
        // TODO : SHOW THE TILE BAG
        if (tileBagActive) {
            tileBagActive = false;
            anchorPaneTileBag.setVisible(false);
            boardAnchor.setVisible(true);
            boardAnchor.toFront();
        } else {

            tileBagActive = true;
            anchorPaneTileBag.setVisible(true);
            boardAnchor.setVisible(false);
            boardAnchor.toBack();

            vBoxTileBag.setSpacing(2);
            vBoxTileBag.getChildren().clear();
            
            int numOfCols = 12;
            double tileSize = (anchorPaneTileBag.getWidth() / numOfCols);

            ArrayList<Letter> bag = this.helper.getBagOfLetters();

            int k = 0;
            for (int i = 0; k < bag.size(); i++) {
                HBox row = new HBox(2);
                for (int j = 0; k < bag.size() && j < numOfCols; j++) {
                    Button tile = getTile(tileSize);
                    updateBoard(tile, bag.get(k++).getLetterName());
                    tile.getStyleClass().add("tilebag");
                    // Label label = new Label(bag.get(k++).getLetterName());
                    // label.setPrefSize(tileSize, tileSize);
                    // label.setPadding(new Insets(10,10,10,10));
                    // label.getStyleClass().add("tilebag");
                    // label.setTextAlignment(TextAlignment.CENTER);
                    // HBox.setHgrow(label, Priority.ALWAYS);
                    // row.getChildren().add(label);
                    row.getChildren().add(tile);
                }
               
                vBoxTileBag.getChildren().add(row);
            }
        }
    }

    public void handleCloseTileBag(ActionEvent e) {
        handleTileBag(e);
    }

    // TileBagClick
    public void handleSubmit(ActionEvent event) {
        // 1. submit
        this.helper.submit();
        btn3.setDisable(false);
    }

    // TileBagClick
    public void handleSwap(ActionEvent event) {

        if (!swapMode) {
            swapMode = true;
            btn4.setDisable(true);
            btn5.setDisable(true);
            btn3.getStyleClass().add("btnActive");
            btn2.setDisable(true);
        } else {

            swapMode = false;
            btn3.getStyleClass().remove("btnActive");
            btn2.setDisable(false);
            btn4.setDisable(false);
            btn5.setDisable(false);
            // No letters selected to swap
            if (selectedLetters.size() == 0)
                return;

            System.out.println(selectedLetters.toString());
            ArrayList<String> toSwap = new ArrayList<>();
            for (String id : selectedLetters) {
                String l = Character.toString(id.charAt(id.length() - 1));
                toSwap.add(l);
            }

            this.helper.swapLetters(toSwap);
            selectedLetters.clear();
        }

    }

    // Handle pass
    public void handlePass(ActionEvent e) {
        handleUndo(e);
        this.helper.changePlayer(false);
    }

    // Handle Undo
    public void handleUndo(ActionEvent e) {
        this.selectedLetter = null;
        btn2.setDisable(false);
        btn3.setDisable(false);
        btn5.setDisable(false);
        this.helper.changePlayer(true);
    }

    /**
     * 
     * @param source : Source on the board/ letterTray which is clicked.
     * @param letter : Next Letter to be placed on board/ letterTray
     */
    public void updateBoard(Button source, String letter) {

        String style = "-fx-background-image: url('/images/pieces/" + letter
                + ".png'); -fx-background-size: 40px 50px;";

        // source.getStyleClass().clear();
        source.setStyle(null);
        source.setStyle(style);
    }

    public void updateBoard(String id, String letter) {

        String coord[] = id.split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);

        HBox row = (HBox) vBox2.getChildren().get(x);
        Button btn = (Button) row.getChildren().get(y);

        if (letter == null)
            letter = getTileColor(id);

        String style = "-fx-background-image: url('/images/pieces/" + letter
                + ".png'); -fx-background-size: 40px 50px;";
        // System.out.println(row);
        // System.out.println(btn);

        // btn.getStyleClass().clear();
        btn.setStyle(null);
        btn.setStyle(style);
    }

    public void updateLetterTray(String id, String letter) {
        for (Node n : hBoxLetters.getChildren()) {

            Button b = (Button) n;
            if (id.equals(b.getId())) {
                String style = "-fx-background-image: url('/images/" + letter
                        + ".png'); -fx-background-size: 64px 80px;";

                b.setStyle(style);
                b.setText("");
            }
        }
    }

    // Clears the tile on Board
    public void clearBoard(Button source) {
        String style = "-fx-background-image: url('/images/tile.png'); -fx-background-size: 40px 40px;";
        source.setStyle(style);
        source.setText("");
    }

    public String getTileColor(String id) {

        String cd[] = id.split(",");
        int x = Integer.parseInt(cd[0]);
        int y = Integer.parseInt(cd[1]);
        return getTileColor(x, y);
    }

    public String getTileColor(int x, int y) {

        Cell cell = this.helper.getBoard().get(x)[y];

        String color = "";

        switch (cell.getCellValues()) {
            case "ST":
                color = "beige";
                break;
            case "TW":
                color = "cyan";
                break;
            case "DL":
                color = "lightblue";
                break;
            case "DW":
                color = "orange";
                break;
            case "TL":
                color = "turquoise";
                break;
            default:
                color = "taupe";
        }
        return color;
    }

    // Scores passed in integer
    public void updateScores(String name, int score) {
        String s = String.valueOf(score);
        updateScores(name, s);
    }

    // Scored passed in String
    public void updateScores(String name, String score) {
        for (Node child : vBox1.getChildren()) {

            if (child instanceof HBox) {
                String id = child.getId();
                if (id == null)
                    continue;
                if (id.compareTo(name) == 0) {
                    HBox box = (HBox) child;
                    Label scoreLabel = (Label) box.getChildren().get(1);
                    scoreLabel.setText(score);
                }
            }
        }
    }

    // Highlights the currentplayer
    public void higlightCurrentPlayer(String playerName) {
        for (Node child : vBox1.getChildren()) {

            if (child != null && child instanceof HBox) {
                System.out.println(child);
                String id = child.getId();
                if (id == null)
                    continue;
                HBox box = (HBox) child;
                if (id.compareTo(playerName) == 0) {
                    if (!box.getStyleClass().contains("current"))
                        box.getStyleClass().add("current");
                } else
                    box.getStyleClass().remove("current");
            }
        }
    }

    // On save click
    public void handleSave(ActionEvent event) {
        this.helper.saveGame();
    }

    // On Exit Click
    public void handleExit(ActionEvent event) {
        this.helper.endGame(this.stage);
    }

    // On Search
    public void handleSearch(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Word");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText("Word :");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String word = result.get();
            if(word.isEmpty())
                return ;

            boolean found  = this.helper.searchWord(word);
            System.out.println("Found : " +found);
            if(found)
                showInfoDialog("Success", "Success : " + word + " is in dictionary.");
            else 
                showInfoDialog("Failed", "Failed : " + word + " is not in dictionary.");
        }
    }

    public void showInfoDialog(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText(message);
        alert.showAndWait();
        
    }

    public void close(){
        stage.close();
    }

}
