// CLIENT TICTACTOE.JAVA

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;

//GuiClient Class
public class TicTacToe extends Application {

	TextField enterPort, enterIP;
	Button connect, quit, playAgain, easy, medium, expert;
	Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
	VBox entering, choices, scoreBox, game;
	HBox row1, row2, row3;
	BorderPane startPane;
	Client clientConnection;
	ListView<String> scores;
	Label points, welcome, result, thing, thing2, wait, youAre, pick, topL, turn;
	boolean isConnected = false;
	PauseTransition pause = new PauseTransition(Duration.seconds(1));

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Let's Play Tic Tac Toe!!!");
		this.connect = new Button("Connect");
		playAgain = new Button("Play Again");
		quit = new Button("Quit");
		thing = new Label("Enter port number");
		thing.setFont(new Font("Comic Sans MS", 16));
		thing2 = new Label("\nEnter IP address");
		thing2.setFont(new Font("Comic Sans MS", 16));
		enterPort = new TextField("5555");
		enterIP = new TextField("127.0.0.1");
		wait = new Label("\n\n");
		wait.setFont(new Font("Comic Sans MS", 16));
		turn = new Label("\nYour turn! Pick any open spot...");
		turn.setFont(new Font("Comic Sans MS", 16));
		pick = new Label("Choose a difficulty\n  to start a game");
		pick.setFont(new Font("Comic Sans MS", 16));
		points = new Label();
		points.setFont(new Font("Comic Sans MS", 16));
		welcome = new Label("Welcome to\n Tic Tac Toe\n\n");
		welcome.setFont(new Font("Comic Sans MS", 30));
		welcome.setStyle("-fx-font-weight: bold");
		scores = new ListView<String>();
		//scores.setPrefHeight(130);
		scores.setPrefWidth(190);
		topL = new Label("LEADERBOARD:");
		topL.setFont(new Font("Comic Sans MS", 16));
		easy = new Button("EASY");
		medium = new Button("MEDIUM");
		expert = new Button("EXPERT");
		youAre = new Label();
		youAre.setFont(new Font("Comic Sans MS", 16));
		b1 = new Button(" ");
		b2 = new Button(" ");
		b3 = new Button(" ");
		b4 = new Button(" ");
		b5 = new Button(" ");
		b6 = new Button(" ");
		b7 = new Button(" ");
		b8 = new Button(" ");
		b9 = new Button(" ");
		b1.setPrefSize(60,60);
		b2.setPrefSize(60,60);
		b3.setPrefSize(60,60);
		b4.setPrefSize(60,60);
		b5.setPrefSize(60,60);
		b6.setPrefSize(60,60);
		b7.setPrefSize(60,60);
		b8.setPrefSize(60,60);
		b9.setPrefSize(60,60);
		b1.setFont(new Font("Comic Sans MS", 28));
		b1.setStyle("-fx-font-weight: bold");
		b2.setFont(new Font("Comic Sans MS", 28));
		b2.setStyle("-fx-font-weight: bold");
		b3.setFont(new Font("Comic Sans MS", 28));
		b3.setStyle("-fx-font-weight: bold");
		b4.setFont(new Font("Comic Sans MS", 28));
		b4.setStyle("-fx-font-weight: bold");
		b5.setFont(new Font("Comic Sans MS", 28));
		b5.setStyle("-fx-font-weight: bold");
		b6.setFont(new Font("Comic Sans MS", 28));
		b6.setStyle("-fx-font-weight: bold");
		b7.setFont(new Font("Comic Sans MS", 28));
		b7.setStyle("-fx-font-weight: bold");
		b8.setFont(new Font("Comic Sans MS", 28));
		b8.setStyle("-fx-font-weight: bold");
		b9.setFont(new Font("Comic Sans MS", 28));
		b9.setStyle("-fx-font-weight: bold");

		this.connect.setOnAction(e-> {
			primaryStage.setTitle("This is a client");
			int port = Integer.parseInt(enterPort.getText());
			String ip = enterIP.getText();  //the ip is 127.0.0.1 or "localhost"
			clientConnection = new Client(data->{
				Platform.runLater(()->{
					points.setText("Your Score: " + clientConnection.score + "\n");
					if(data.toString().contains("you are")) {
						youAre.setText("You are Client "+ data.toString().substring(8) + "\n");
						clientConnection.clientnum = Integer.parseInt(data.toString().substring(8));
						primaryStage.setScene(createClientGui());
					}
					else if(data.toString().contains("your turn")) {
						makeMove(false, Integer.parseInt(data.toString().substring(10)));
						if(clientConnection.theGame.checkWinner("X")) {
							clientConnection.theGame.message = "server won";
							clientConnection.sendGame();
							primaryStage.setScene(endGui("server"));
						}
						else if(clientConnection.theGame.isTie()){
							clientConnection.theGame.message = "tie";
							primaryStage.setScene(endGui("tie"));
							clientConnection.sendGame();
						}
						else
							primaryStage.setScene(createGame());
					}
					else if(data.toString().contains("scores")){
						updateScores();
					}
				});
			}, port, ip);
			isConnected = true;
			clientConnection.start();
		});

		easy.setOnAction(e-> {
			clientConnection.theGame.difficulty = 1;
			clientConnection.theGame.message = "choose difficulty";
			clientConnection.sendGame();
			primaryStage.setScene(createGame());
		});
		medium.setOnAction(e-> {
			clientConnection.theGame.difficulty = 2;
			clientConnection.theGame.message = "choose difficulty";
			clientConnection.sendGame();
			primaryStage.setScene(createGame());
		});
		expert.setOnAction(e-> {
			clientConnection.theGame.difficulty = 3;
			clientConnection.theGame.message = "choose difficulty";
			clientConnection.sendGame();
			primaryStage.setScene(createGame());
		});

		b1.setOnAction(e-> {
			makeMove(true, 1);
			if(clientConnection.theGame.checkWinner("O")){
				clientConnection.score++;
				clientConnection.theGame.message = "client won";
				primaryStage.setScene(endGui("client"));
				clientConnection.sendGame();
			}else if(clientConnection.theGame.isTie()){
				clientConnection.theGame.message = "tie";
				primaryStage.setScene(endGui("tie"));
				clientConnection.sendGame();
			}else
				primaryStage.setScene(createGame());
		});
		b2.setOnAction(e-> {
			makeMove(true, 2);
			if(clientConnection.theGame.checkWinner("O")){
				clientConnection.score++;
				clientConnection.theGame.message = "client won";
				primaryStage.setScene(endGui("client"));
				clientConnection.sendGame();
			}else if(clientConnection.theGame.isTie()){
				clientConnection.theGame.message = "tie";
				primaryStage.setScene(endGui("tie"));
				clientConnection.sendGame();
			}else
				primaryStage.setScene(createGame());
		});
		b3.setOnAction(e-> {
			makeMove(true, 3);
			if(clientConnection.theGame.checkWinner("O")){
				clientConnection.score++;
				clientConnection.theGame.message = "client won";
				primaryStage.setScene(endGui("client"));
				clientConnection.sendGame();
			}else if(clientConnection.theGame.isTie()){
				clientConnection.theGame.message = "tie";
				primaryStage.setScene(endGui("tie"));
				clientConnection.sendGame();
			}else
				primaryStage.setScene(createGame());
		});
		b4.setOnAction(e-> {
			makeMove(true, 4);
			if(clientConnection.theGame.checkWinner("O")){
				clientConnection.score++;
				clientConnection.theGame.message = "client won";
				primaryStage.setScene(endGui("client"));
				clientConnection.sendGame();
			}else if(clientConnection.theGame.isTie()){
				clientConnection.theGame.message = "tie";
				primaryStage.setScene(endGui("tie"));
				clientConnection.sendGame();
			}else
				primaryStage.setScene(createGame());
		});
		b5.setOnAction(e-> {
			makeMove(true, 5);
			if(clientConnection.theGame.checkWinner("O")){
				clientConnection.score++;
				clientConnection.theGame.message = "client won";
				primaryStage.setScene(endGui("client"));
				clientConnection.sendGame();
			}else if(clientConnection.theGame.isTie()){
				clientConnection.theGame.message = "tie";
				primaryStage.setScene(endGui("tie"));
				clientConnection.sendGame();
			}else
				primaryStage.setScene(createGame());
		});
		b6.setOnAction(e-> {
			makeMove(true, 6);
			if(clientConnection.theGame.checkWinner("O")){
				clientConnection.score++;
				clientConnection.theGame.message = "client won";
				primaryStage.setScene(endGui("client"));
				clientConnection.sendGame();
			}else if(clientConnection.theGame.isTie()){
				clientConnection.theGame.message = "tie";
				primaryStage.setScene(endGui("tie"));
				clientConnection.sendGame();
			}else
				primaryStage.setScene(createGame());
		});
		b7.setOnAction(e-> {
			makeMove(true, 7);
			if(clientConnection.theGame.checkWinner("O")){
				clientConnection.score++;
				clientConnection.theGame.message = "client won";
				primaryStage.setScene(endGui("client"));
				clientConnection.sendGame();
			}else if(clientConnection.theGame.isTie()){
				clientConnection.theGame.message = "tie";
				primaryStage.setScene(endGui("tie"));
				clientConnection.sendGame();
			}else
				primaryStage.setScene(createGame());
		});
		b8.setOnAction(e-> {
			makeMove(true, 8);
			if(clientConnection.theGame.checkWinner("O")) {
				clientConnection.score++;
				clientConnection.theGame.message = "client won";
				primaryStage.setScene(endGui("client"));
				clientConnection.sendGame();
			}else if(clientConnection.theGame.isTie()){
				clientConnection.theGame.message = "tie";
				primaryStage.setScene(endGui("tie"));
				clientConnection.sendGame();
			}else
				primaryStage.setScene(createGame());
		});
		b9.setOnAction(e-> {
			makeMove(true, 9);
			if(clientConnection.theGame.checkWinner("O")){
				clientConnection.score++;
				clientConnection.theGame.message = "client won";
				primaryStage.setScene(endGui("client"));
				clientConnection.sendGame();
			}else if(clientConnection.theGame.isTie()){
				clientConnection.theGame.message = "tie";
				primaryStage.setScene(endGui("tie"));
				clientConnection.sendGame();
			}else
				primaryStage.setScene(createGame());
		});

		playAgain.setOnAction(e-> {
			clientConnection.theGame.message = "again";
			clientConnection.theGame = new GameInfo();
			points.setText("Your Score: " + clientConnection.score + "\n");
			clientConnection.sendGame();
			resetButtons();
			primaryStage.setScene(createClientGui());
		});
		quit.setOnAction(e-> {
			if(isConnected) {
				clientConnection.theGame.message = "bye";
				clientConnection.sendGame();
				try {
					clientConnection.socketClient.close();
				} catch (IOException e2) {
				}
			}
			Platform.exit();
			System.exit(0);
		});
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				if(isConnected) {
					clientConnection.theGame.message = "bye";
					clientConnection.sendGame();
					try {
						clientConnection.socketClient.close();
					} catch (IOException e) {
					}
				}
				Platform.exit();
				System.exit(0);
			}
		});

		this.entering = new VBox(10, welcome, thing, enterPort, thing2, enterIP, connect);
		entering.setAlignment(Pos.CENTER);
		startPane = new BorderPane();
		startPane.setStyle("-fx-background-color: honeydew");
		startPane.setPadding(new Insets(20));
		startPane.setCenter(entering);

		Scene scene = new Scene(startPane, 400,400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void resetButtons() {
		wait.setText("\n\n");
		b1.setText(" ");
		b2.setText(" ");
		b3.setText(" ");
		b4.setText(" ");
		b5.setText(" ");
		b6.setText(" ");
		b7.setText(" ");
		b8.setText(" ");
		b9.setText(" ");

		b1.setDisable(false);
		b2.setDisable(false);
		b3.setDisable(false);
		b4.setDisable(false);
		b5.setDisable(false);
		b6.setDisable(false);
		b7.setDisable(false);
		b8.setDisable(false);
		b9.setDisable(false);
	}

	EventHandler<ActionEvent> update = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if(!b1.getText().equals("X") && !b1.getText().equals("O"))
				b1.setDisable(false);
			if(!b2.getText().equals("X") && !b2.getText().equals("O"))
				b2.setDisable(false);
			if(!b3.getText().equals("X") && !b3.getText().equals("O"))
				b3.setDisable(false);
			if(!b4.getText().equals("X") && !b4.getText().equals("O"))
				b4.setDisable(false);
			if(!b5.getText().equals("X") && !b5.getText().equals("O"))
				b5.setDisable(false);
			if(!b6.getText().equals("X") && !b6.getText().equals("O"))
				b6.setDisable(false);
			if(!b7.getText().equals("X") && !b7.getText().equals("O"))
				b7.setDisable(false);
			if(!b8.getText().equals("X") && !b8.getText().equals("O"))
				b8.setDisable(false);
			if(!b9.getText().equals("X") && !b9.getText().equals("O"))
				b9.setDisable(false);
			if(clientConnection.theGame.checkWinner("X") || clientConnection.theGame.checkWinner("O"))
				disableButtons();
			clientConnection.sendGame();
			turn.setText("\nYour turn! Pick any open spot...");
		}
	};

	public void makeMove(boolean isClient, int n) {
		if(n == 1) {
			if(isClient)
				b1.setText("O");
			else
				b1.setText("X");
			b1.setDisable(true);
		}
		else if(n == 2) {
			if(isClient)
				b2.setText("O");
			else
				b2.setText("X");
			b2.setDisable(true);
		}
		else if(n == 3) {
			if(isClient)
				b3.setText("O");
			else
				b3.setText("X");
			b3.setDisable(true);
		}
		else if(n == 4){
			if(isClient)
				b4.setText("O");
			else
				b4.setText("X");
			b4.setDisable(true);
		}
		else if(n == 5){
			if(isClient)
				b5.setText("O");
			else
				b5.setText("X");
			b5.setDisable(true);
		}
		else if(n == 6) {
			if(isClient)
				b6.setText("O");
			else
				b6.setText("X");
			b6.setDisable(true);
		}
		else if(n == 7) {
			if(isClient)
				b7.setText("O");
			else
				b7.setText("X");
			b7.setDisable(true);
		}
		else if(n == 8) {
			if(isClient)
				b8.setText("O");
			else
				b8.setText("X");
			b8.setDisable(true);
		}
		else if(n == 9){
			if(isClient)
				b9.setText("O");
			else
				b9.setText("X");
			b9.setDisable(true);
		}

		if(isClient) {
			clientConnection.theGame.board[n-1] = "O";
			clientConnection.theGame.message = "go";
			wait.setText("You played in spot " + n + "\n\n");
			turn.setText("\nServer's turn");
			b1.setDisable(true);
			b2.setDisable(true);
			b3.setDisable(true);
			b4.setDisable(true);
			b5.setDisable(true);
			b6.setDisable(true);
			b7.setDisable(true);
			b8.setDisable(true);
			b9.setDisable(true);
			pause.setOnFinished(update);
			pause.play();
		}
		else {
			clientConnection.theGame.board[n-1] = "X";
			clientConnection.theGame.message = "";
			wait.setText("Server played in spot " + n + "\n\n");
		}
	}

	public void updateScores(){
		scores.getItems().clear();
		int clientNum = clientConnection.clientnum;
		int score = clientConnection.score;
		if(score > clientConnection.p1s && clientConnection.p1 != clientNum){
			int temp = clientConnection.p2s;
			int tempp = clientConnection.p2;
			clientConnection.p2s = clientConnection.p1s;
			clientConnection.p2 = clientConnection.p1;
			clientConnection.p3s = temp;
			clientConnection.p3 = tempp;
			clientConnection.p1 = clientNum;
			clientConnection.p1s = score;

		}

		else if(score > clientConnection.p1s && clientConnection.p1 == clientNum){
			clientConnection.p1s = score;
		}

		if(score > clientConnection.p2s && clientConnection.p1 != clientNum && clientConnection.p2 != clientNum){
			int temp = clientConnection.p2s;
			int tempp = clientConnection.p2;

			clientConnection.p2s = score;
			clientConnection.p2 = clientNum;
			clientConnection.p3s = temp;
			clientConnection.p3 = tempp;
		}
		else if(score > clientConnection.p2s && clientConnection.p1 != clientNum && clientConnection.p2 == clientNum){
			clientConnection.p2s = score;
		}
		if(score > clientConnection.p3s && clientConnection.p1 != clientNum && clientConnection.p2 != clientNum){

			clientConnection.p3s = score;
			clientConnection.p3 = clientNum;
		}
		if(clientConnection.p1 != -1){
			String s1s = "Client #" + clientConnection.p1 + " Score: " + clientConnection.p1s;
			scores.getItems().add(s1s);
		}
		if(clientConnection.p2 != -1){
			String s1s = "Client #" + clientConnection.p2 + " Score: " + clientConnection.p2s;
			scores.getItems().add(s1s);
		}
		if(clientConnection.p3 != -1){
			String s1s = "Client #" + clientConnection.p3 + " Score: " + clientConnection.p3s;
			scores.getItems().add(s1s);
		}

	}

	public Scene createClientGui() {
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(10));
		scores.setPrefHeight(75);
		updateScores();
		scoreBox = new VBox(topL, scores);
		VBox left = new VBox(40, wait, youAre, points, scoreBox);
		choices = new VBox(40, pick, easy, medium, expert);
		choices.setAlignment(Pos.CENTER);
		pane.setStyle("-fx-background-color: lightblue");
		pane.setLeft(left);
		pane.setCenter(choices);
		return new Scene(pane, 400, 400);
	}
	public Scene createGame() {
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(40));
		pane.setStyle("-fx-background-color: lavender");
		row1 = new HBox(5, b1, b2, b3);
		row2 = new HBox(5, b4, b5, b6);
		row3 = new HBox(5, b7, b8, b9);
		row1.setAlignment(Pos.CENTER);
		row2.setAlignment(Pos.CENTER);
		row3.setAlignment(Pos.CENTER);
		game = new VBox(5, row1, row2, row3);
		game.setStyle("-fx-background-color: black");
		game.setAlignment(Pos.CENTER);
		pane.setTop(wait);
		pane.setBottom(turn);
		pane.setCenter(game);
		return new Scene(pane, 400, 400);
	}
	public Scene endGui(String won) {
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(30));
		pane.setStyle("-fx-background-color: ivory");
		if(won.equals("tie"))
			result = new Label("It's a Tie!");
		else if(won.equals("client"))
			result = new Label("You won!");
		else
			result = new Label("You lost :(");
		result.setFont(new Font("Comic Sans MS", 20));
		VBox right = new VBox(40, youAre, points, result, playAgain, quit);
		scores.setPrefHeight(75);
		disableButtons();
		row1 = new HBox(5, b1, b2, b3);
		row2 = new HBox(5, b4, b5, b6);
		row3 = new HBox(5, b7, b8, b9);
		updateScores();
		row1.setAlignment(Pos.CENTER);
		row2.setAlignment(Pos.CENTER);
		row3.setAlignment(Pos.CENTER);
		game = new VBox(5, row1, row2, row3);
		VBox end = new VBox(10, topL, scores, game);
		right.setAlignment(Pos.CENTER);
		pane.setCenter(right);
		pane.setLeft(end);
		return new Scene(pane, 400, 400);
	}

	private void disableButtons() {
		b1.setDisable(true);
		b2.setDisable(true);
		b3.setDisable(true);
		b4.setDisable(true);
		b5.setDisable(true);
		b6.setDisable(true);
		b7.setDisable(true);
		b8.setDisable(true);
		b9.setDisable(true);
	}
}