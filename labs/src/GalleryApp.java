import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GalleryApp extends Application{
	
	/** constants */
	public static final double IMG_WIDTH = 322;
	public static final double IMG_HEIGHT = 643;
	public static final double APP_WIDTH = IMG_WIDTH + 100;
	public static final double APP_HEIGHT = IMG_HEIGHT + 50;
	public static final int N_IMAGES = 30;
	
	public static final double INTERVAL = .5;  // 2 seconds
	
	/** GUI variables  */
	private BorderPane rootPane;
	private HBox	   controlPanel;
	private Button	   bnPlay, bnStop, bnNext, bnPrev;
	private Timeline   timer;
	private ImageView[] images;
	
	private int currFN;      // 1 - 30
	
	
	@Override
	public void start(Stage mainStage) {
		setupLayout();
		setupButtons();
		setupImages();
		setupTimer();
		
		Scene myScene = new Scene(rootPane, APP_WIDTH, APP_HEIGHT);
		
		mainStage.setScene(myScene);
		mainStage.setTitle("Gallery CS216 S23");
		mainStage.show();
	}
	
	private void setupImages() {
		images = new ImageView[N_IMAGES];
		for (int i = 0; i < N_IMAGES; i++) {
			String fname = "file:" + (i + 1) + ".png";
			Image img = new Image(fname);
			ImageView iv = new ImageView(img);
			
			iv.setFitWidth(IMG_WIDTH);
			iv.setFitHeight(IMG_HEIGHT);
			
			images[i] = iv;
		}
		
		currFN = 1;
		showImage(currFN);
	}
	
	private void showImage(int fn) {
		rootPane.setCenter(images[fn - 1]);
	}
	
	private void setupLayout() {
		rootPane = new BorderPane();
		controlPanel = new HBox();
		
		rootPane.setTop(controlPanel);
	}
	
	private void setupButtons() {
		bnPlay = new Button("PLAY");
		bnStop = new Button("STOP");
		bnPrev = new Button("<");
		bnNext = new Button(">");
		
		bnPrev.setPrefHeight(IMG_HEIGHT);
		bnNext.setPrefHeight(IMG_HEIGHT);
		
		// add listner to each button
		bnNext.setOnAction(e -> {
			showNext();
		});
		
		bnPrev.setOnAction(e -> {
			showPrev();
		});
		
		bnPlay.setOnAction(e -> {
			timer.play();
		});
		
		bnStop.setOnAction(e -> {
			timer.stop();
		});
		
		// add them to proper locations
		controlPanel.getChildren().addAll(bnPlay, bnStop);
		
		rootPane.setLeft(bnPrev);
		rootPane.setRight(bnNext);
	}
	
	private void setupTimer() {
		// create keyframe object
		KeyFrame kf = new KeyFrame(Duration.seconds(INTERVAL), e -> {
			showNext();
		}); // use of Duration Class
		
		timer = new Timeline(kf);
		timer.setCycleCount(Animation.INDEFINITE);  // Animation.INDEFINITE	
	}
	
	private void showNext() {
		++currFN;
		
		if (currFN > N_IMAGES) {
			currFN = 1;
		}
		showImage(currFN);
	}
	
	private void showPrev() {
		--currFN;
		
		if (currFN < 1) {
			currFN = N_IMAGES;
		}
		
		showImage(currFN);
	}
	
	
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}




