package scrabble.events;

import javafx.scene.control.Button;

public abstract class GameEvent {
    public String name;
    private Button source;
    public GameEvent(String name, Button source){
        this.name = name;
        this.source = source;
    }

    public Button getSource(){
        return this.source;
    }
}