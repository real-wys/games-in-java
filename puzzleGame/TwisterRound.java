package puzzleGame;
//name:Yusu Wang
//id: yusuw
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TwisterRound extends GameRound {
    private ObservableList<String> solutionWordsList;
    private List<String> solutionWordsListOnList;
    private ObservableList<ObservableList<String>> submittedListsByWordLength;
    private ObservableList<ObservableList<String>>  solutionListsByWordLength;

    TwisterRound(){
        List<ObservableList<String>> observableLists = new ArrayList<>();
        for (int i = 0; i < Twister.SOLUTION_LIST_COUNT;i++){
            observableLists.add(FXCollections.observableArrayList(new String[]{}));
        }
        submittedListsByWordLength=FXCollections.observableArrayList(observableLists);
    }
    public void setSolutionWordsList(List<String> solutionWordsList) {
        //solution list not null
        this.solutionWordsListOnList =solutionWordsList;
        this.solutionWordsList = FXCollections.observableArrayList(solutionWordsList);
        System.out.println(solutionWordsListOnList.toString());
    }
    public List<String> getSolutionWordsList(){
        return solutionWordsListOnList;
    }
    public ObservableList<String> solutionWordsListProperty(){
        return solutionWordsList;
    }
    public void setSubmittedListsByWordLength(String word){
//        add word
        int index = word.length()-Twister.TWISTER_MIN_WORD_LENGTH;
        submittedListsByWordLength.get(index).add(word);
    }

    public ObservableList<ObservableList<String>> getSubmittedListsByWordLength(){
        return submittedListsByWordLength;
    }
    public ObservableList<String> getSubmittedListsByWordLength(int letterCount){
        return submittedListsByWordLength.get(letterCount);
    }
    public ObservableList<ObservableList<String>> submittedListsByWordLengthProperty(){
        return submittedListsByWordLength;
    }

    public void setSolutionListsByWordLength(String word){
        //initialize solution lists
        if (solutionListsByWordLength == null){
            List<ObservableList<String>> observableLists = new ArrayList<>();
            for (int i = 0; i < Twister.SOLUTION_LIST_COUNT;i++){
                observableLists.add(FXCollections.observableArrayList(new String[]{}));
            }
            solutionListsByWordLength=FXCollections.observableArrayList(observableLists);
        }
//        add word
        int index = word.length()-Twister.TWISTER_MIN_WORD_LENGTH;
        solutionListsByWordLength.get(index).add(word);
    }
    public ObservableList<ObservableList<String>> getSolutionListsByWordLength(){
        return solutionListsByWordLength;
    }
    public ObservableList<String> getSolutionListsByWordLength(int letterCount){
        return solutionListsByWordLength.get(letterCount);
    }
    public ObservableList<ObservableList<String>> solutionListsByWordLengthProperty(){
        return solutionListsByWordLength;
    }

}
