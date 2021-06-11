package puzzleGame;
//name:Yusu Wang
//id: yusuw

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.util.ArrayList;
import java.util.List;


public class SearchController extends WordNerdController {
	SearchView searchView;
	
	//this is a placeholder class. Will be completed in HW3

	@Override
	void startController() {
		searchView = new SearchView();
		setupBindings();
		searchView.setupView();


	}

	@Override
	void setupBindings() {
//		add gameComboBox
		ObservableList<String> options =
				FXCollections.observableArrayList("All games","Hangman","Twister");

		searchView.gameComboBox.setItems(options);
		searchView.gameComboBox.getSelectionModel().selectFirst();


		searchView.searchTextField.textProperty().addListener(e -> {
			String key = searchView.searchTextField.getText().trim();
			int select = searchView.gameComboBox.getSelectionModel().getSelectedIndex();
			ObservableList<Score> data = wordNerdModel.scoreList;
			if (select == 1) {
				List<Score> list = new ArrayList<>();
				for (int i=0;i<data.size();i++){
					if(data.get(i).getGameId()==0) list.add(data.get(i));
				}
				data = FXCollections.observableArrayList(list);

			} else if (select == 2){
				List<Score> list = new ArrayList<>();
				for (int i=0;i<data.size();i++){
					if(data.get(i).getGameId()==1) list.add(data.get(i));
				}
				data = FXCollections.observableArrayList(list);

			}
			List<Score> list = new ArrayList<Score>();
			for (int i=0;i< data.size();i++){
				String puzzle = data.get(i).getPuzzleWord();
				boolean contains = true;
				for (int j =0; j<key.length();j++){
					boolean found = false;
					for (int k=0;k<puzzle.length();k++){
						if (puzzle.charAt(k) == key.charAt(j)) {
							found = true;
							break;
						}
					}
					if (!found) contains=false;
				}
				if (contains) list.add(data.get(i));
			}
			searchView.searchTableView.setItems(FXCollections.observableArrayList(list));
		});
		searchView.gameComboBox.setOnAction(e -> {
			int select = searchView.gameComboBox.getSelectionModel().getSelectedIndex();
			ObservableList<Score> data = wordNerdModel.scoreList;
			if (select == 0){
				searchView.searchTableView.setItems(data);
				return;
			} else if (select == 1){
				List<Score> list = new ArrayList<>();
				for (int i=0;i<data.size();i++){
					if(data.get(i).getGameId()==0) list.add(data.get(i));
				}
				searchView.searchTableView.setItems(FXCollections.observableArrayList(list));
			} else {
				List<Score> list = new ArrayList<>();
				for (int i=0;i<data.size();i++){
					if(data.get(i).getGameId()==1) list.add(data.get(i));
				}
				searchView.searchTableView.setItems(FXCollections.observableArrayList(list));
			}

		});


		//search data show
		wordNerdModel.readScore();
		ObservableList<Score> data = wordNerdModel.scoreList;
		searchView.searchTableView.setItems(data);
	}

}
