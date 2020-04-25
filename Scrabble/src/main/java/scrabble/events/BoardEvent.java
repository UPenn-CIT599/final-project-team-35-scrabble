package scrabble.events;

import javafx.scene.control.Button;

public class BoardEvent extends GameEvent {

    private int x, y;
    private String id;

    public BoardEvent(Button source) {
        super("BoredEvent", source);
        
        this.id = source.getId();
        String coord[] = this.id.split(",");
        this.x = Integer.parseInt(coord[0]);
        this.y = Integer.parseInt(coord[1]);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getId() {
        return this.id;
    }
}