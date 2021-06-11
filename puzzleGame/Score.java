package puzzleGame;
//name:Yusu Wang
//id: yusuw
import javafx.beans.property.*;

public class Score {
    IntegerProperty gameId;
    StringProperty puzzleWord;
    IntegerProperty timeStamp;
    FloatProperty score;
    Score(){
        gameId = new SimpleIntegerProperty();
        puzzleWord = new SimpleStringProperty();
        timeStamp = new SimpleIntegerProperty();
        score = new SimpleFloatProperty();
    }
    Score(int gameId, String puzzleWord, int timeStamp, float score){

        this.gameId=new SimpleIntegerProperty(gameId);
        this.puzzleWord=new SimpleStringProperty(puzzleWord);
        this.timeStamp= new SimpleIntegerProperty(timeStamp);
        this.score= new SimpleFloatProperty(score);

    }

    public Integer getGameId() {
        return gameId.getValue();
    }

    public Float getScore() {
        return score.getValue();
    }

    public String getPuzzleWord(){return puzzleWord.getValue();}
    public Number getTimeStamp(){return timeStamp.getValue();}

}
