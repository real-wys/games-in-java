package puzzleGame;
//name:Yusu Wang
//id: yusuw
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WordNerdModel {
    static String[] wordsFromFile;

    static final String WORDS_FILE_NAME = "data/wordsfile.txt";
    static final String SCORE_FILE_NAME = "data/scores.csv";
    ObservableList<Score> scoreList;

    public void writeScore(String scoreString){
        String[] score = scoreString.split(",");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(SCORE_FILE_NAME,true));
            bufferedWriter.append("\n");
            bufferedWriter.write(score[0]);
            bufferedWriter.append(',');
            bufferedWriter.write(score[1]);
            bufferedWriter.append(',');
            bufferedWriter.write(score[2]);
            bufferedWriter.append(',');
            bufferedWriter.write(score[3]);
            bufferedWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void readScore(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(SCORE_FILE_NAME));
            String line;
            scoreList = FXCollections.observableArrayList(new ArrayList<Score>());
            while ((line=bufferedReader.readLine()) != null){
                String[] args = line.split(",");
                if (args.length < 4||args[0].length()==0) continue;
                Integer id = Integer.parseInt(args[0]);
                String puzzleWord = args[1];
                Integer timeStamp = Integer.parseInt(args[2]);
                Float score = Float.parseFloat(args[3]);
                Score s = new Score(id, puzzleWord, timeStamp, score);
                scoreList.add(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void readWordsFile(String wordsFilename){
        StringBuilder s = new StringBuilder();
        try {
            Scanner sc = new Scanner(new File(wordsFilename));
            if (!sc.hasNext()) {
                InvalidWordSourceException sourceException = new InvalidWordSourceException("Check word source format!");
                sourceException.showAlert();
            }
            while (sc.hasNext()){
                String word = sc.next();
                if (!word.matches("^[0-9a-zA-Z]+$")) {
                    InvalidWordSourceException sourceException = new InvalidWordSourceException("Check word source format!");
                    sourceException.showAlert();
                }
                s.append(word+"\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        wordsFromFile = s.toString().split("\n");
    }
}
