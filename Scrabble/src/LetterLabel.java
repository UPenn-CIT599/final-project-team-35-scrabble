
import javafx.scene.control.Label;

public class LetterLabel extends Label{
	
	
	public LetterLabel(String l, int x, int y) {
	
		Label letter = new Label(l);
		letter.relocate(x * GUI.TILE_SIZE, y * GUI.TILE_SIZE);
	}
}
