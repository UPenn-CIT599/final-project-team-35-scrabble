package scrabble.events;

import javafx.scene.control.Button;

public class LetterEvent extends GameEvent {

    private String letterName;
    private String id;
    
    public LetterEvent(Button source){
        super("LetterEvent", source);
        this.id = source.getId();
        this.letterName = Character.toString(this.id.charAt(this.id.length()-1));
    }

    public String getLetter(){
        return this.letterName;
    }
}