package puzzleGame;
//name:Yusu Wang
//id: yusuw
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class HangmanRound extends GameRound{
    private IntegerProperty hitCount = new SimpleIntegerProperty(0);
    private IntegerProperty missCount = new SimpleIntegerProperty(0);

    public int getHitCount(){
        return hitCount.intValue();
    }
    public void setHitCount(int hitCount){
        this.hitCount.setValue(hitCount);
    }
    public IntegerProperty hitCountProperty(){
        return hitCount;
    }
    public int getMissCount(){
        return missCount.intValue();
    }
    public void setMissCount(int hitCount){
        this.missCount.setValue(hitCount);
    }
    public IntegerProperty missCountProperty(){
        return missCount;
    }
}
