package puzzleGame;
//name:Yusu Wang
//id: yusuw

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TwisterController extends WordNerdController {
    TwisterView twisterView;
    Twister twister;

    @Override
    void startController() {
        //view
        twisterView = new TwisterView();
        twister = new Twister();
        twister.twisterRound = twister.setupRound();
        twisterView.refreshGameRoundView(twister.twisterRound);

        //setup top message text
        twisterView.topMessageText.setText(twister.getScoreString());
        WordNerd.root.setTop(twisterView.topMessageText);

        //setup top grid
        WordNerd.root.setCenter(twisterView.topGrid);

        //setup bottom grid, add exit button
        VBox lowerPanel = new VBox();
        lowerPanel.getChildren().add(twisterView.bottomGrid);
        lowerPanel.getChildren().add(WordNerd.exitButton);
        lowerPanel.setAlignment(Pos.CENTER);
        WordNerd.root.setBottom(lowerPanel);
        setActions();
        setupBindings();
        }
    void setActions(){
        /*add play buttons action listeners*/
        twisterView.playButtons[0].setOnAction(new NewButtonHandler());
        twisterView.playButtons[1].setOnAction(new TwistButtonHandler());
        twisterView.playButtons[2].setOnAction(new ClearButtonHandler());
        twisterView.playButtons[3].setOnAction(new SubmitButtonHandler());

        /*add clue and answer button action listener*/
        for (int i=0; i<twisterView.clueButtons.length;i++){
            twisterView.answerButtons[i].setOnAction(new AnswerButtonHandler());
            twisterView.clueButtons[i].setOnAction(new ClueButtonHandler());
        }

    }



    @Override
    void setupBindings() {

        GameView.wordTimer.timeline.setOnFinished(event -> {
            System.out.println("Finish");
            twisterView.smileyButton.setGraphic(twisterView.smileyImageViews[GameView.SADLY_INDEX]);
            String scoreString = "1,"+twister.twisterRound.getPuzzleWord()+","
                    +120 +","
                    +Float.toString(twister.twisterRound.getSubmittedListsByWordLength().size()
                    /(float)twister.twisterRound.getSolutionListsByWordLength().size());
            wordNerdModel.writeScore(scoreString);
            twister.twisterRound.setIsRoundComplete(true);
        });
        //when time runs out, disable buttons(clue, answer, twist, clear, submit)
        twisterView.clueGrid.disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());
        twisterView.playButtons[3].disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());
        twisterView.playButtons[2].disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());
        twisterView.playButtons[1].disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());
    }

    private void clear(){
        for (int i=0; i< twisterView.answerButtons.length;i++) {
            if(twisterView.answerButtons[i].getText().trim().length()!=0){
                int j = 0;
                while (twisterView.clueButtons[j].getText().trim().length() != 0){
                    j++;
                }
                twisterView.clueButtons[j].setText(twisterView.answerButtons[i].getText());
            }
            twisterView.answerButtons[i].setText("");
        }
    }

    class NewButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            twister.twisterRound = twister.setupRound();
            twisterView.refreshGameRoundView(twister.twisterRound);
            GameView.wordTimer.restart(Twister.TWISTER_GAME_TIME);
            twisterView.topMessageText.setText(twister.getScoreString());
            setActions();
            setupBindings();
        }
    }
    class TwistButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            StringBuilder s = new StringBuilder();
            for (int i=0; i< twisterView.clueButtons.length;i++){
                if (twisterView.clueButtons[i].getText().trim().length()!=0){
                    s.append(twisterView.clueButtons[i].getText());
                }
            }
            String word = twister.makeAClue(s.toString());
            int i=0;
            while(i<word.length()){
                twisterView.clueButtons[i].setText(word.substring(i,i+1));
                i++;
            }
            while(i<twisterView.clueButtons.length){
                twisterView.clueButtons[i].setText("");
                i++;
            }

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
    class SubmitButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            StringBuilder ans = new StringBuilder();
            int i = 0;
            System.out.println(i);
            while (i < twisterView.answerButtons.length
                    && twisterView.answerButtons[i].getText().trim().length()!= 0){
                ans.append(twisterView.answerButtons[i].getText());
                i++;
            }
            String solution = ans.toString();
            int index = twister.nextTry(solution);
            twisterView.smileyButton.setGraphic(twisterView.smileyImageViews[index]);
            if (index==TwisterView.THUMBS_UP_INDEX) {
                clear();
                twisterView.topMessageText.setText(twister.getScoreString());
                twisterView.wordScoreLabels[solution.length()-3].setText(wordScore(twister.twisterRound,solution.length()-3));
                twisterView.solutionListViews[solution.length()-3].setItems(
                        twister.twisterRound.getSubmittedListsByWordLength(solution.length()-3));
            }
            if (index==TwisterView.SMILEY_INDEX) {
                twister.twisterRound.setIsRoundComplete(true);
                String scoreString = "1,"+twister.twisterRound.getPuzzleWord()+","
                        + (120-Integer.parseInt(twisterView.wordTimeButton.getText()))+","
                        +Float.toString(twister.twisterRound.getSubmittedListsByWordLength().size()
                        /(float)twister.twisterRound.getSolutionListsByWordLength().size());
                wordNerdModel.writeScore(scoreString);

                GameView.wordTimer.timeline.stop();
                twisterView.topMessageText.setText(twister.getScoreString());
                twisterView.wordScoreLabels[solution.length()-3].setText(wordScore(twister.twisterRound,solution.length()-3));
            }
        }
    }
    class ClearButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            clear();
        }
    }
/*     Takes the alphabet in clicked clueButton,
     finds the next empty slot starting from left in answerButtons,
     puts the alphabet in it. Clears the clicked clueButton */
    class ClueButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            Button b = (Button)actionEvent.getSource();
            String text = b.getText();
            int i = 0;
            while (twisterView.answerButtons[i].getText().trim().length() != 0){
                i++;
            }
            twisterView.answerButtons[i].setText(text);
            b.setText("");
        }
    }
/*        Takes the alphabet in clicked answerButton,
        finds the next empty slot starting from left in clueButtons
        puts the alphabet in it. Clears the clicked answerButton*/
    class AnswerButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            Button b = (Button)actionEvent.getSource();
            String text = b.getText();
            int i = 0;
            while (twisterView.clueButtons[i].getText().trim().length() != 0){
                i++;
            }
            twisterView.clueButtons[i].setText(text);
            b.setText("");

        }
    }


}
