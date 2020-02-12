import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.io.IOException;

//GuiServer Class
public class TicTacToe extends Application {

	TextField enterPort;
	Button serverChoice;
	VBox buttonBox;
	BorderPane startPane;
	Server serverConnection;
	ListView<String> listItems;
	Label con, winner, portLab, server;
	ImageView p1pic, p2pic;
	int count = 0;
	PauseTransition pause = new PauseTransition(Duration.seconds(3));


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Let's Play Tic Tac Toe!!!");

		portLab = new Label("Enter port number");
		portLab.setFont(new Font("Comic Sans MS", 16));
		enterPort = new TextField("5555");
		serverChoice = new Button("Start");
		con = new Label("Clients Connected: "  + count);
		con.setFont(new Font("Comic Sans MS", 15));
        server = new Label("Tic Tac Toe Server");
        server.setFont(new Font("Comic Sans MS", 20));
        server.setStyle("-fx-font-weight: bold");
		winner = new Label("");
		winner.setFont(new Font("Comic Sans MS", 15));
		listItems = new ListView<String>();
		listItems.setPrefHeight(300);

		this.serverChoice.setOnAction(e->{ primaryStage.setScene(createServerGui());
			primaryStage.setTitle("This is the Server");
			int port = Integer.parseInt(enterPort.getText());
			serverConnection = new Server(data -> {
				Platform.runLater(()->{
					listItems.getItems().add(data.toString());
					if(data.toString().equals("bye") || data.toString().equals("again")){
						p1pic = new ImageView();
						p2pic = new ImageView();
					}
					count = serverConnection.counter - serverConnection.left;
					if(count < 0 )
						count = 0;
					con.setText("Clients Connected: "  + count);
					if(data.toString().contains("wins") || data.toString().contains("draw"))
						winner.setText(data.toString());
					else
						winner.setText("");
					primaryStage.setScene(createServerGui());
					pause.play();
				});
			}, port);
		});

		buttonBox = new VBox(40, server, portLab, enterPort,serverChoice);
		buttonBox.setAlignment(Pos.CENTER);
		startPane = new BorderPane();
		startPane.setPadding(new Insets(30));
		startPane.setCenter(buttonBox);
		startPane.setStyle("-fx-background-color: ivory");
		startPane.setPadding(new Insets(10));
		Scene scene = new Scene(startPane, 250,300);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
			//	System.exit(0);
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Scene createServerGui() {
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(10));
		pane.setStyle("-fx-background-color: mistyrose");
		pane.setTop(con);
		pane.setCenter(listItems);
		return new Scene(pane, 250, 300);
	}
}
