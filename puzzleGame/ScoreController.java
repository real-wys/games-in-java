package puzzleGame;
//name:Yusu Wang
//id: yusuw
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;

public class ScoreController extends WordNerdController {
	
	//this is a placeholder class. Will be completed in HW3
	ScoreView scoreView;
	ScoreChart scoreChart;

	@Override
	void startController() {
		//to be implemented in HW3
		scoreView = new ScoreView();
		scoreChart = new ScoreChart();
		scoreView.setupView();

//		get scores
		wordNerdModel.readScore();
		ObservableList<Score> scores = wordNerdModel.scoreList;
//		put score into Chart
		LineChart<Number, Number> hangmanChart = scoreChart.drawChart(scores).get(0);
		LineChart<Number, Number> twisterChart = scoreChart.drawChart(scores).get(1);
		scoreView.scoreGrid.add(hangmanChart,0,1);
		scoreView.scoreGrid.add(twisterChart,0,2);

		setupBindings();
		WordNerd.root.setCenter(scoreView.scoreGrid);

	}
	@Override
	void setupBindings() {
		//to be implemented in HW3
//		dynamic update?
	}
	
	

}
