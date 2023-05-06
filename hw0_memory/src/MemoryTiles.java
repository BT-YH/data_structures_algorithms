import java.util.Collections;
import java.util.Stack;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author Barry Tang
 * @fileName MemoryTiles.java
 * 
 * This program generates a playable MemoryTiles game 
 * using JavaFX
 *
 */
public class MemoryTiles extends Application {
	
	/** Constants */
	public static final int IMG_WIDTH = 55;
	public static final int IMG_HEIGHT = 92;
	public static final int APP_WIDTH = 280;
	public static final int APP_HEIGHT = 388;
	public static final int GRID_HEIGHT = 368;
	public static final int N_ROWS = 4;
	public static final int N_COLS = 5;
	public static final int N_IMAGES = 10;
	public static final int DURATION = 60;
	public static final double INTERVAL = .3;
	public static final Font myFont = Font.font("serif", FontWeight.BOLD,
			FontPosture.ITALIC, 8);
	
	/** GUI variables  */
	private BorderPane  rootPane;
	private HBox	    controlPane;
	private GridPane    gamePane;
	private Button      bnPlay;
	private Label	    lblScore;
	private Label		lblTime;
	private Timeline    globalTimer;
	private Timeline    timer;
	
	private Stack<Character> chars = new Stack<>();
	private Stack<MyButton> bnStack = new Stack<>();
	private char[][] charArray;
	private MyButton[][] buttons;
	private int score;
	private int remainingTime = DURATION;
	private int nTurnedTiles;
    private StringProperty n = new SimpleStringProperty("#: ");
	private StringProperty scoreStrProp = new SimpleStringProperty("Score: " + score);
	private StringProperty timeStrProp = new SimpleStringProperty("Time Remaining: " + remainingTime);
	
	{
		for (int i = 97; i <= 106; i++) {
			chars.add((char)i);
			chars.add((char)i);
		}
	}
	
	public void start(Stage primaryStage) throws Exception {
		setUp();
		
		Scene myScene = new Scene(rootPane, APP_WIDTH, APP_HEIGHT);
		
		primaryStage.setScene(myScene);
		primaryStage.setTitle("MemoryTiles");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	private void setUp() {
		setupPanes();
		setupControl();
		setupTiles();
		setupTimers();
		
		disableButtons(true);
	}
	
	private void reset() {
		reShuffle();
		bnStack.clear();
		
		bnPlay.setText("Play");
		score = 0;
		scoreStrProp.set("Score: " + score);
		remainingTime = DURATION;
	}

	private void reShuffle() {
		// Re-shuffle Buttons
		Stack<MyButton> temp = new Stack<>();
		for (int i = 0; i < N_ROWS; i++) 
			for (int j = 0; j < N_COLS; j++) {
				temp.push(buttons[i][j]);
			}

		Collections.shuffle(temp);
		gamePane = new GridPane();
		gamePane.setStyle("-fx-background-color: #C0C0C0;");
		gamePane.setVgap(1);
		gamePane.setHgap(1);
		rootPane.setCenter(gamePane);
		for (int i = 0; i < N_ROWS; i++) 
			for (int j = 0; j < N_COLS; j++) {
				buttons[i][j] = temp.pop();
				buttons[i][j].isTurned = false;
				buttons[i][j].setGraphic(null);
				gamePane.add(buttons[i][j], j, i);
			}
	}
	
	private void setupPanes() {
		rootPane = new BorderPane();
		
		controlPane = new HBox();
		controlPane.setAlignment(Pos.CENTER);
		controlPane.setSpacing(12);
		
		gamePane = new GridPane();
		gamePane.setStyle("-fx-background-color: #C0C0C0;");
		gamePane.setVgap(1);
		gamePane.setHgap(1);
		//gamePane.setGridLinesVisible(true);
		
		rootPane.setTop(controlPane);
		rootPane.setCenter(gamePane);
	}
	
	private void setupControl() {
		bnPlay = new Button("Play");
		bnPlay.setFont(myFont);
		lblScore = new Label();
		lblScore.setFont(myFont); 
		lblScore.textProperty().bind(scoreStrProp);
		
		lblTime = new Label("Remaining Time: " + remainingTime);
		lblTime.setFont(myFont); 
		lblTime.textProperty().bind(timeStrProp);
		
		Label test = new Label();
		test.setFont(myFont);
		test.textProperty().bind(n);
		
		bnPlay.setOnAction(e -> {
			if (bnPlay.getText().equals("Play")) {
				disableButtons(false);
				globalTimer.play();
				bnPlay.setText("Pause");
			} else if (bnPlay.getText().equals("Pause")) {
				disableButtons(true);
				globalTimer.pause();
				bnPlay.setText("Resume");
			} else if (bnPlay.getText().equals("Resume")) {
				disableButtons(false);
				globalTimer.play();
				bnPlay.setText("Pause");
			} else {
				reset();
			}
		});
		controlPane.getChildren().addAll(bnPlay, lblScore, lblTime);
	}
	
	private void setupTiles() {
		charArray = new char[N_ROWS][N_COLS];
		buttons = new MyButton[N_ROWS][N_COLS];
		Collections.shuffle(chars);
		
		while (!chars.isEmpty()) {
			for (int i = 0; i < N_ROWS; i++) 
				for (int j = 0; j < N_COLS; j++)
					charArray[i][j] = chars.pop();
		}

		for (int i = 0; i < N_ROWS; i++) 
			for (int j = 0; j < N_COLS; j++) {
				
				MyButton bn = new MyButton(i, j, charArray[i][j]);
				bn.setPrefHeight(IMG_HEIGHT);
				bn.setPrefWidth(IMG_WIDTH);
				
				bn.setOnAction(e -> {
					buttonAction(bn);
				});
				
				gamePane.add(bn, j, i);
				buttons[i][j] = bn;
			}
	}
	
	private void setupTimers() {
		KeyFrame kf1 = new KeyFrame(Duration.seconds(INTERVAL), e -> {
		}); // use of Duration Class
		timer = new Timeline(kf1);
		
		KeyFrame kf2 = new KeyFrame(Duration.seconds(1), e ->  {
			remainingTime--;
			timeStrProp.set("Time Remaining: " + remainingTime);
		}); // use of Duration Class
		globalTimer = new Timeline(kf2);
		globalTimer.setCycleCount(DURATION);
		globalTimer.setOnFinished(e -> {
			disableButtons(true);
			bnPlay.setText("Start New Game");
		});
	}
	
	private void disableButtons(boolean b) {
		for (int r = 0; r < buttons.length; r++) {
			for (int c = 0; c < buttons[0].length; c++) {
				buttons[r][c].setDisable(b);
			}
		}
	}
	
	private ImageView getImage(char c) {
		String fname = "file:" + c + ".png";
		Image img = new Image(fname);
		ImageView iv = new ImageView(img);
		iv.setFitHeight(IMG_HEIGHT);
		iv.setFitWidth(IMG_WIDTH);
		return iv;
	}
	
	private void buttonAction(MyButton bn) {
		if (bnStack.size() > 1) {
			return;
		}
		
		if (nTurnedTiles % 2 == 1) {
			boolean pairFound = false;
			if (!bn.isTurned) {
				ImageView iv = getImage(bn.name);
				iv.setFitWidth(IMG_WIDTH);
				iv.setFitHeight(IMG_HEIGHT);

				if (bn.name == bnStack.peek().name && !bn.equals(bnStack.peek())) {
					pairFound = true;
				}
				
				bn.setGraphic(iv);
				bn.isTurned = true;
				if (pairFound) {
					score += 20;
					nTurnedTiles++;
					scoreStrProp.set("Score: " + score);
					bn.setDisable(true);
					bnStack.pop().setDisable(true);
				} else if (!bn.equals(bnStack.peek())){
					timer.play();
					score -= 5;
					scoreStrProp.set("Score: " + score);
					timer.setOnFinished(e -> {
						bn.setGraphic(null);
						bn.isTurned = false;
						MyButton prevBn = bnStack.pop();
						prevBn.setGraphic(null);
						prevBn.isTurned = false;
						nTurnedTiles--;
						n.set("#: " + nTurnedTiles);
					});
				}
				
			}
		}
		else {
			bnStack.push(bn);
			ImageView iv = getImage(charArray[bn.row][bn.col]);
			iv.setFitWidth(IMG_WIDTH);
			iv.setFitHeight(IMG_HEIGHT);
			bn.setGraphic(iv);
			nTurnedTiles++;
			n.set("#: " + nTurnedTiles);
		}
	}
	
	class MyButton extends Button {
		boolean isTurned = false;
		int row, col;
		char name;
		
		public MyButton(int row, int col, char name) {
			super();
			this.row = row;
			this.col = col;
			this.name = name;
			this.setPadding(new Insets(0));
		}
	}
	
	public static void main(String[] args) {
		launch();
	}
}
