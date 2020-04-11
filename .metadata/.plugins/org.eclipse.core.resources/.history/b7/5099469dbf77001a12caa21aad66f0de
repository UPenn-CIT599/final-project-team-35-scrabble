import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUI extends Application implements EventHandler<ActionEvent>{
	 Button gameStart;
	@Override
	public void start(Stage gameWindow) throws Exception {
		gameStart = new Button("Play");
		
		StackPane layout = new StackPane();
		layout.getChildren().add(gameStart);
		
		Scene scene = new Scene(layout,800,800);
		gameWindow.setScene(scene);
		gameWindow.show();
		
	}
	
	
	public void handle(ActionEvent event) {
		if(event.getSource()==gameStart) {
			
		}
	}
	
}
