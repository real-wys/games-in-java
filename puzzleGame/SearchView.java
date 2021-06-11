package puzzleGame;
//name:Yusu Wang
//id: yusuw
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.text.DecimalFormat;

public class SearchView{

	ComboBox<String> gameComboBox = new ComboBox<>(); //shows drop down for filtering the tableView data
	TextField searchTextField = new TextField();  //for entering search letters
	TableView<Score> searchTableView = new TableView<>();  //displays data from scores.csv

	Callback<TableColumn.CellDataFeatures<Score,String>, ObservableValue<String>> scoreCallBack;
	Callback<TableColumn.CellDataFeatures<Score,String>, ObservableValue<String>> gameNameCallBack;
	/**setupView() sets up the GUI components
	 * for Search functionality
	 */
	void setupView() {
		
		VBox searchVBox = new VBox();  //searchVBox contains searchLabel and searchHBox
		Text searchLabel = new Text("Search");
		searchVBox.getChildren().add(searchLabel);

		HBox searchHBox = new HBox();  //searchHBox contain gameComboBox and searchTextField
		searchHBox.getChildren().add(gameComboBox);
		searchHBox.getChildren().add(new Text("Search letters"));
		searchHBox.getChildren().add(searchTextField);
		searchVBox.getChildren().add(searchHBox);
		
		searchLabel.setStyle( "-fx-font: 30px Tahoma;" + 
				" -fx-fill: linear-gradient(from 0% 50% to 50% 100%, repeat, lightgreen 0%, lightblue 50%);" +
				" -fx-stroke: gray;" +
				" -fx-background-color: gray;" +
				" -fx-stroke-width: 1;") ;
		searchHBox.setPrefSize(WordNerd.GAME_SCENE_WIDTH, WordNerd.GAME_SCENE_HEIGHT / 3);
		gameComboBox.setPrefWidth(200);
		searchTextField.setPrefWidth(300);
		searchHBox.setAlignment(Pos.CENTER);
		searchVBox.setAlignment(Pos.CENTER);
		searchHBox.setSpacing(10);
		
		setupSearchTableView();
		
		WordNerd.root.setPadding(new Insets(10, 10, 10, 10));
		WordNerd.root.setTop(searchVBox);
		WordNerd.root.setCenter(searchTableView);
		WordNerd.root.setBottom(WordNerd.exitButton);
	}

	void setupSearchTableView() {
		//write your code here
		TableColumn gameCol = new TableColumn("Game");
		TableColumn wordCol = new TableColumn("Word");
		TableColumn timeCol = new TableColumn("Time(sec)");
		TableColumn scoreCol = new TableColumn("Score");
		gameCol.setMinWidth(150);
		wordCol.setMinWidth(200);
		timeCol.setMinWidth(200);
		scoreCol.setMinWidth(150);


		searchTableView.getColumns().addAll(gameCol,wordCol,timeCol,scoreCol);
		gameCol.setCellValueFactory(new PropertyValueFactory<>("gameId"));
		wordCol.setCellValueFactory(new PropertyValueFactory<>("puzzleWord"));
		timeCol.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
		scoreCol.setCellValueFactory(new PropertyValueFactory<>("Score"));

		gameCol.setCellValueFactory(cellDataFeatures -> {
			Score s = (Score) ((TableColumn.CellDataFeatures)cellDataFeatures).getValue();
			if (s.gameId.getValue()==0) return new SimpleStringProperty("Hangman");
			return new SimpleStringProperty("Twister");
		});

		scoreCol.setCellValueFactory(cellDataFeatures -> {
			Score s = (Score) ((TableColumn.CellDataFeatures)cellDataFeatures).getValue();
			DecimalFormat df = new DecimalFormat("#.##");
			return new SimpleStringProperty(
					df.format(s.getScore())
			);
		});

	}
}
