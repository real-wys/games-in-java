package puzzleGame;
//name:Yusu Wang
//id: yusuw
import java.text.DecimalFormat;
import java.util.Random;

public class Hangman extends Game{
	static final int MIN_WORD_LENGTH = 5; //minimum length of puzzle word
	static final int MAX_WORD_LENGTH = 10; //maximum length of puzzle word
	static final int HANGMAN_TRIALS = 10;  // max number of trials in a game
	static final int HANGMAN_GAME_TIME = 30; // max time in seconds for one round of game
	
	HangmanRound hangmanRound;
	
	/** setupRound() is a replacement of findPuzzleWord() in HW1. 
	 * It returns a new HangmanRound instance with puzzleWord initialized randomly drawn from wordsFromFile.
	* The puzzleWord must be a word between HANGMAN_MIN_WORD_LENGTH and HANGMAN_MAX_WORD_LEGTH. 
	* Other properties of Hangmanround are also initialized here. 
	*/
	@Override
	HangmanRound setupRound() {
		int index = WordNerdModel.wordsFromFile.length;
		//randomly get puzzle word from words from file
		Random r = new Random();
		String puzzle;
		//check if in the range
		do {
			puzzle = WordNerdModel.wordsFromFile[r.nextInt(index)];
		} while (puzzle.length()>MAX_WORD_LENGTH||puzzle.length()<MIN_WORD_LENGTH);
		hangmanRound = new HangmanRound();
		hangmanRound.setPuzzleWord(puzzle);
		hangmanRound.setClueWord(makeAClue(puzzle));
		hangmanRound.setHitCount(0);
		hangmanRound.setMissCount(0);
//		hangmanRound.setIsRoundComplete(false);
		return hangmanRound;
	}
	
	
	/** Returns a clue that has at least half the number of letters in puzzleWord replaced with dashes.
	* The replacement should stop as soon as number of dashes equals or exceeds 50% of total word length. 
	* Note that repeating letters will need to be replaced together.
	* For example, in 'apple', if replacing p, then both 'p's need to be replaced to make it a--le */
	@Override
	String makeAClue(String puzzleWord) {
		int dash = 0;
		char[] p = puzzleWord.toCharArray();
		int length = p.length;
		Random r = new Random();
		while (dash < length / 2) {
			int pos = r.nextInt(length);
			char replaced = p[pos];
			if (replaced == '-') continue;
			for (int i = 0; i < length; i++) {
				if (i == pos) continue;
				if (p[i] == replaced) {
					dash++;
					p[i] = '-';
				}
			}
			p[pos] = '-';
			dash++;
		}
		return new String(p);
	}


	/** countDashes() returns the number of dashes in a clue String */ 
	int countDashes(String word) {
		int numOfDashes = 0;
		char[] chars = word.toCharArray();
		int num = chars.length;
		for (int i = 0; i < num; i++){
			if(chars[i]== '_') numOfDashes++;
		}
		return numOfDashes;
	}
	
	/** getScoreString() returns a formatted String with calculated score to be displayed after
	 * each trial in Hangman. See the handout and the video clips for specific format of the string. */
	@Override
	String getScoreString() {
		int hit = hangmanRound.getHitCount();
		int miss = hangmanRound.getMissCount();
		DecimalFormat df = new DecimalFormat("0.00");
		String score;
		if (miss==0) score=df.format((float)hit);
		else score = df.format((float)hit/miss);
		String display = "Hit: "+hangmanRound.getHitCount()+" Miss: "+hangmanRound.getMissCount()
				+" Score: "+score;
		return (display);
	}

	/** nextTry() takes next guess and updates hitCount, missCount, and clueWord in hangmanRound. 
	* Returns INDEX for one of the images defined in GameView (SMILEY_INDEX, THUMBS_UP_INDEX...etc. 
	* The key change from HW1 is that because the keyboardButtons will be disabled after the player clicks on them, 
	* there is no need to track the previous guesses made in userInputs*/
	@Override
	int nextTry(String guess) {
		String puzzle = hangmanRound.getPuzzleWord();
		if (puzzle.contains(guess)) {
			hangmanRound.setHitCount(hangmanRound.getHitCount()+1);
			char[] chars = hangmanRound.getClueWord().toCharArray();
			char[] puzzles = puzzle.toCharArray();
			for (int i=0; i < chars.length;i++){
				if (puzzles[i] == guess.charAt(0)) chars[i] = guess.charAt(0);
			}
			hangmanRound.setClueWord(new String(chars));
			if (hangmanRound.getClueWord().equals(hangmanRound.getPuzzleWord())) return GameView.SMILEY_INDEX;
			return GameView.THUMBS_UP_INDEX;
		}
		hangmanRound.setMissCount(hangmanRound.getMissCount()+1);
		return GameView.THUMBS_DOWN_INDEX;
	}
}
