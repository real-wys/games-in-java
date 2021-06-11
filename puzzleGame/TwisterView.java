package puzzleGame;
//name:Yusu Wang
//id: yusuw

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TwisterView extends GameView{
	
	static final int PLAY_BUTTON_COUNT = 4; //New word, Twist, Clear, and Submit buttons
	Label[] wordLengthLabels;  //shows letter count on the left of each solution list 
	Label[] wordScoreLabels; //shows score for a letter count on the right of each solution list
	Button[] clueButtons ;  //clue buttons on the top
	Button[] answerButtons; //empty buttons below clue buttons
	Button[] playButtons;  //buttons for New Word, Twise, Clear, and Submit
	ListView<String>[] solutionListViews;  //lists that show he correct guesses made by the player
	Button wordTimeButton;
	
	TwisterView() {
		//initialize member variables
		wordLengthLabels = new Label[Twister.SOLUTION_LIST_COUNT];
		wordScoreLabels = new Label[Twister.SOLUTION_LIST_COUNT];
		playButtons = new Button[TwisterView.PLAY_BUTTON_COUNT];  
		
		setupTopGrid();
		setupBottomGrid();
		setupSizesAlignmentsEtc();
	}
	
	@Override
	void setupTopGrid() {
		topGrid.add(clueGrid, 0, 0);
		topGrid.add(playButtonsGrid, 0, 2);
		
		//setup play buttons grid
		playButtons[0] = new Button("New Word");
		playButtons[1] = new Button("Twist");
		playButtons[2] = new Button("Clear");
		playButtons[3] = new Button("Submit");

		for (int col = 0; col < playButtons.length; col++) {
			playButtons[col].setPrefSize(120, 20);
			playButtons[col].setStyle("-fx-background-color: gray," +
					" linear-gradient(lightblue, gray), " +
					" linear-gradient(lightblue 0%, white 49%, white 50%, lightblue 100%);" + 
					" -fx-background-insets: 0,1,2;"); 
			playButtons[col].setTextFill(Color.BLACK);
			playButtons[col].setFont(Font.font(15));

			playButtonsGrid.add(playButtons[col], col + 1, 0);
		}
		
		wordTimer = new WordTimer(Twister.TWISTER_GAME_TIME);
		wordTimeButton = wordTimer.timerButton;
		playButtonsGrid.add(wordTimer.timerButton, 0, 0);
		playButtonsGrid.add(smileyButton, 5, 0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	void setupBottomGrid() {
		bottomGrid.getChildren().clear();
		bottomGrid.setAlignment(Pos.CENTER);
		bottomGrid.setVgap(5);
		bottomGrid.setMinSize(700, 300);
		//setup letter count buttons
		for (int i = 0; i < wordLengthLabels.length; i++ ) {
			wordLengthLabels[i] = new Label(String.format("%d", i + 3));  //starting with 3
			wordLengthLabels[i].setPrefSize(50, 50);
			
			wordLengthLabels[i].setStyle( "-fx-font: 15px Tahoma;" + 
					" -fx-background-color: lightgray;");
			wordLengthLabels[i].setTextFill(Color.BLACK);
			wordLengthLabels[i].setAlignment(Pos.CENTER);
			bottomGrid.add(wordLengthLabels[i], 0, i);
		}	
		
		//setup solution lists
		solutionListViews = new ListView[Twister.SOLUTION_LIST_COUNT];
		for (int i = 0; i < solutionListViews.length; i++) {
			solutionListViews[i] = new ListView<>();
			solutionListViews[i].setOrientation(Orientation.HORIZONTAL);
			solutionListViews[i].setPrefSize(525, 50);
		}
		
		//setup word score buttons
		wordScoreLabels = new Label[Twister.SOLUTION_LIST_COUNT];
		for (int i = 0; i < wordScoreLabels.length; i++ ) {
			wordScoreLabels[i] = new Label(String.format("%d", i + 3));  //starting with 3
			wordScoreLabels[i].setPrefSize(50, 50);
			wordScoreLabels[i].setStyle("-fx-font: 15px Tahoma;" + 
					" -fx-background-color: lightgray;");
			wordScoreLabels[i].setTextFill(Color.BLACK);
			wordScoreLabels[i].setAlignment(Pos.CENTER);
			bottomGrid.add(wordScoreLabels[i], 2, i);
		}
	}
	
	@Override
	void setupSizesAlignmentsEtc() {
		playButtonsGrid.setHgap(10);
		playButtonsGrid.setVgap(10);
		
		topGrid.setMinSize(WordNerd.GAME_SCENE_WIDTH, 200);
		topGrid.setAlignment(Pos.CENTER);
		topGrid.setHgap(10);
		topGrid.setVgap(10);
		
		bottomGrid.setAlignment(Pos.BASELINE_CENTER);
		clueGrid.setAlignment(Pos.CENTER);
	}

	//setupButtonsLookAndFeel() to be invoked in refreshGameRoundView
	//after new clueButtons and answerButtons are created
	void setupButtonsLookAndFeel() {
		for (int i = 0; i < clueButtons.length; i++) {
			clueButtons[i].setStyle("-fx-background-color: white," +
					" linear-gradient(lightblue, blue)," +
					" linear-gradient(lightblue 0%, white 49%, white 50%, lightblue 100%);" +
					" -fx-font: 20px Arial;" +
					" -fx-background-insets: 0,1,1;" +
					" -fx-padding: 3 30 3 30;" +
					" -fx-background-radius: 5em; " );
			clueButtons[i].setMinSize(75, 75);
		}
		for (int i = 0; i < answerButtons.length; i++) {
			answerButtons[i].setStyle("-fx-background-color: white," +
					" linear-gradient(lightgray, gray)," +
					" linear-gradient(lightgray 0%, white 49%, white 50%, lightgray 100%);" +
					" -fx-font: 20px Arial;" +
					" -fx-background-insets: 0,1,1;" +
					" -fx-padding: 3 30 3 30;" +
					" -fx-background-radius: 5em; " );
			answerButtons[i].setMinSize(75, 75);
		}
	}

	/**refreshGameRoundView() clears up previous game round and 
	 * refreshes all components with info in the new gameRound */
	void refreshGameRoundView(GameRound gameRound) {
//		clear previous game round
		TwisterRound t = (TwisterRound) gameRound;
		clueGrid.getChildren().clear();
//		bottomGrid.getChildren().clear();

		String clue = t.getClueWord();
		int len = clue.length();
		answerButtons = new Button[len];
		clueButtons = new Button[len];

//		add clue buttons
		for(int i=0;i<len;i++){
			answerButtons[i] = new Button();
			clueButtons[i] = new Button();
			clueButtons[i].setText(clue.substring(i,i+1));
			clueGrid.add(answerButtons[i],i,1);
			clueGrid.add(clueButtons[i],i,0);
		}

		setupButtonsLookAndFeel();
		setupBottomGrid();

//		add solutionListViews
		for (int i=0;i< solutionListViews.length;i++){
			if (t.getSolutionListsByWordLength(i).size()==0){
				solutionListViews[i].setVisible(false);
				solutionListViews[i].setManaged(false);
			} else {
				solutionListViews[i].setItems(t.getSubmittedListsByWordLength(i));
			}
			bottomGrid.add(solutionListViews[i],1,i);
		}

		/*add labels*/
		int i=0;
		while(i<Twister.SOLUTION_LIST_COUNT){
			if (t.getSolutionListsByWordLength(i).size()==0) {
				wordLengthLabels[i].setVisible(false);
				wordLengthLabels[i].setManaged(false);
				wordScoreLabels[i].setVisible(false);
				wordScoreLabels[i].setManaged(false);
				i++;
				continue;
			}
			wordScoreLabels[i].setText(wordScore(t, i));
			i++;
		}
	}
	private String wordScore(TwisterRound t,int i){
		int submitted;
		int solution;
		if (t.getSubmittedListsByWordLength(i)==null) submitted=0;
		else submitted=t.getSubmittedListsByWordLength(i).size();
		solution=t.getSolutionListsByWordLength(i).size();
		return submitted+"/"+solution;
	}
}
