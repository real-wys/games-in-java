package puzzleGame;
//name:Yusu Wang
//id: yusuw
import java.util.*;

public class Twister extends Game{
    static final int SOLUTION_LIST_COUNT=5;
    static final int TWISTER_MAX_WORD_LENGTH=7;
    static final int TWISTER_MIN_WORD_LENGTH=3;
    static final int NEW_WORD_BUTTON_INDEX=0;
    static final int TWIST_BUTTON_INDEX=1;
    static final int CLEAR_BUTTON_INDEX=2;
    static final int SUBMIT_BUTTON_INDEX=3;
    static final int CLUE_BUTTON_SIZE=75;
    static final int TWISTER_GAME_TIME=120;
    static final int MIN_SOLUTION_WORDCOUNT=120;
    private int solutionNum;
    private int rightNum;

    TwisterRound twisterRound;

    @Override
    TwisterRound setupRound() {
        solutionNum=0;
        rightNum=0;
        int index = WordNerdModel.wordsFromFile.length;
        //randomly get puzzle word from words from file
        Random r = new Random();
        String puzzle;
        //check if in the range
        do {
            puzzle = WordNerdModel.wordsFromFile[r.nextInt(index)];
        } while (puzzle.length()>TWISTER_MAX_WORD_LENGTH||puzzle.length()<TWISTER_MIN_WORD_LENGTH);

        List<String> solutionWords = new ArrayList<>();
        twisterRound = new TwisterRound();
        twisterRound.setPuzzleWord(puzzle);
        twisterRound.setClueWord(makeAClue(puzzle));
//        int min_length = 0;
        for (int i = 0; i < index; i++){
            Boolean solution = false;
            String word = WordNerdModel.wordsFromFile[i];
            int len = word.length();
            if (len<TWISTER_MIN_WORD_LENGTH || len > puzzle.length()) continue;
            int j = 0;
            String s=puzzle;
            while (j < len){
                if (!s.contains(word.substring(j,j+1))) break;
                s=s.replaceFirst(word.substring(j,j+1),"-");
                j++;
            }

            if (j==len){
                solutionWords.add(word);
                twisterRound.setSolutionListsByWordLength(word);
                solutionNum++;
            }
        }
        twisterRound.setSolutionWordsList(solutionWords);
        return twisterRound;
    }

    @Override
    String makeAClue(String puzzleWord) {
        List<Character> characters = new ArrayList<Character>();
        for(char c:puzzleWord.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(puzzleWord.length());
        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }

    @Override
    String getScoreString() {
        //at first it should be find %D words
        boolean start = false;
        for (int i=0;i< Twister.SOLUTION_LIST_COUNT;i++){
            if (twisterRound.getSubmittedListsByWordLength().get(i).size()!=0) start=true;
        }
        if(!start) return String.format("Twist to find %d words", solutionNum);
        return String.format("Twist to find %d of %d words",solutionNum-rightNum,solutionNum);
    }

    @Override
    int nextTry(String guess) {
        if (guess.length() < TWISTER_MIN_WORD_LENGTH) return TwisterView.THUMBS_DOWN_INDEX;
        int letterCount=guess.length()-TWISTER_MIN_WORD_LENGTH;
        if (twisterRound.getSubmittedListsByWordLength(letterCount)!=null
                &&twisterRound.getSubmittedListsByWordLength(letterCount).contains(guess)){
            return TwisterView.REPEAT_INDEX;
        }
        if (twisterRound.getSolutionListsByWordLength(letterCount).size()==0) return TwisterView.THUMBS_DOWN_INDEX;
        if (!twisterRound.getSolutionListsByWordLength(letterCount).contains(guess)) return TwisterView.THUMBS_DOWN_INDEX;
        twisterRound.getSubmittedListsByWordLength(letterCount).add(guess);
        rightNum++;
        boolean complete = true;
        for (int i=0;i<twisterRound.getSolutionListsByWordLength().size();i++){
            if(twisterRound.getSolutionListsByWordLength(i).size()!=twisterRound.getSubmittedListsByWordLength(i).size()){
                complete=false;
                break;
            }
        }
        if (complete) return TwisterView.SMILEY_INDEX;
        return TwisterView.THUMBS_UP_INDEX;
    }
}
