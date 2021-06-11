
//@author:Yusu Wang
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import java.awt.event.ActionListener;
import java.util.Random;


public class Game extends JFrame {
    /**
     * Game.
     * @param serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * random.
     */
    private Random random = new Random();
    /**
     * Down string.
     */
    private static final String DOWN = "   ";
    /**
     * UP string.
     */
    private static final String UP = "O_O";
    /**
     *HITP string.
     */
    private static final String HIT = "X_X";
    /**
     * DOWN_color.
     */
    private static final Color DOWN_COLOR = Color.lightGray;
    /**
     * HIT_COLOR.
     */
    private static final Color HIT_COLOR = Color.pink;
    /**
     * UP color constant.
     */
    private static final Color UP_COLOR = Color.yellow;
    /**
     * STAYINTIMEMILLIE constant.
     */
    private static final int STAY_IN_TIME_MILLIES = 20000;
    /**
     * BUTTON_STAY_MIN constant.
     */
    private static final int BUTTON_STAY_MIN = 500;
    /**
     * BUTTON_STAY_MAX constant.
     */
    private static final int BUTTON_STAY_MAX = 4000;
    /**
     * BUTTON_SLEEP constant.
     */
    private static final int BUTTON_SLEEP = 2000;
    /**
     * NUM_ROWS constant.
     */
    private static final int NUM_ROWS = 8;
    /**
     * NUM_COLS constant.
     */
    private static final int NUM_COLS = 8;
    /**
     * score.
     */
    private int score = 0;
    /**
     * start.
     */
    private JButton start;
    /**
     * timeleftArea.
     */
    private JTextField timeleftArea;
    /**
     * scoreArea.
     */
    private JTextField scoreArea;
    /**
     * buttons.
     */
    private JButton[][] buttons = new JButton[NUM_ROWS][NUM_COLS];
    /**
     * Game.
     */
    public Game() {
        setTitle("Whack-a-mole GUI");
        final int length = 1000;
        final int width = 500;
        setSize(length, width);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pane = new JPanel();
        pane.setLayout(null);
        final int fontSize = 14;
        Font font = new Font(Font.MONOSPACED, Font.BOLD, fontSize);
        //set label
        JLabel timeLabel = new JLabel("Time Left:", JLabel.CENTER);
        JLabel scoreLabel = new JLabel("Score:", JLabel.CENTER);
        //set time and score Area
        timeleftArea = new JTextField("20s");
        timeleftArea.setEditable(false);
        scoreArea = new JTextField("0");
        scoreArea.setEditable(false);
        //set start Button
        start = new JButton("Start");
        final int x = 250;
        final int y = 80;
        final int l = 100;
        final int w = 20;
        final int m = 10;
        final int width1 = 30;
        start.setBounds(x, y / 2, l, width1);
        timeLabel.setBounds(x + l + 2 * w + w, y / 2, y - w, width1);
        timeleftArea.setBounds(x + y + l + 2 * w + m, y / 2, y, width1);
        scoreLabel.setBounds(x + y + 2 * l + 2 * w + w + m,
                y / 2,  y / 2, width1);
        scoreArea.setBounds(x + y + 2 * l + w + y + 2 * m, y / 2, y, width1);
        start.addActionListener(new StartActionListener(timeleftArea,
                scoreArea));
        pane.add(start);
        pane.add(timeLabel);
        pane.add(timeleftArea);
        pane.add(scoreLabel);
        pane.add(scoreArea);

        // create and add components to the container dynamically
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                buttons[i][j] = new JButton(DOWN);
                setDown(buttons[i][j]);
                buttons[i][j].setFont(font);
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBounds(y - w + j * (l + m),
                        y + 2 * w + i * (width1 + m), l, width1);
                pane.add(buttons[i][j]);
            }
        }

        setContentPane(pane);
        setVisible(true);
    }
    /**
     * setDown.
     * @param button
     */
    public void setDown(final JButton button) {
        button.setText(DOWN);
        button.setBackground(DOWN_COLOR);
    }
    /**
     * setUp.
     * @param button
     */
    public void setUp(final JButton button) {
        button.setText(UP);
        button.setBackground(UP_COLOR);
    }
    /**
     * setHIT.
     * @param button
     */
    public void setHIT(final JButton button) {
        button.setText(HIT);
        button.setBackground(HIT_COLOR);
    }
    /**
     * StartActionListener.
     */
    public class StartActionListener implements ActionListener {
        /**
         * timeleftArea.
         */
        private JTextField timeleftArea;
        /**
         * scoreArea.
         */
        private JTextField scoreArea;
        /**
         * StartActionListener.
         * @param timeleftArea1
         * @param scoreArea1
         */
        public StartActionListener(final JTextField timeleftArea1,
                final JTextField scoreArea1) {
            timeleftArea = timeleftArea1;
            scoreArea = scoreArea1;
        }
        /**
         * actionPerformed.
         * @param e
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            score = 0;
//            scoreArea.setText("0");
            Runnable timechange = new TimeRunnable(timeleftArea,
                    scoreArea);
            Thread t1 = new Thread(timechange);
            t1.start();
            for (int i = 0; i < NUM_ROWS; i++) {
                for (int j = 0; j < NUM_COLS; j++) {
                    Runnable buttonsR = new ButtonRunnable(buttons[i][j],
                            timeleftArea);
                    Thread tButton = new Thread(buttonsR);
                    buttons[i][j].addActionListener(
                            new ClickActionListner(buttons[i][j], scoreArea));
                    tButton.start();
                }
            }
        }
    }
    /**
     * clickActionListner.
     */
    public class ClickActionListner implements ActionListener {
        /**
         * button.
         * @param button
         */
        private JButton actionButton;
        /**
         * button.
         * @param button
         */
        private JTextField scoreArea;
        /**
         * clickActionListner.
         * @param button
         * @param textArea2
         */
        ClickActionListner(final JButton button,
                final JTextField textArea2) {
            actionButton = button;
            scoreArea = textArea2;
        }
        /**
         * clickActionListner.
         * @param e
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            if (timeleftArea.getText().equals("0s")) {
                return;
            }
            if (actionButton.getText().equals(UP)) {
                setHIT(actionButton);
                score = score + 1;
//                System.out.println(score);
                synchronized (scoreArea) {
                    scoreArea.setText(Integer.toString(score));
                }
            }
        }
    }
    public class TimeRunnable implements Runnable {
        /**
         * timeleftArea.
         */
        private JTextField timeleftArea;
        /**
         * scoreArea.
         */
        private JTextField scoreArea;
        /**
         * startTime.
         */
        private long startTime = System.currentTimeMillis();
        /**
         * TimeRunnable.
         * @param timeleftArea1
         * @param scoreArea1
         */
        public TimeRunnable(final JTextField timeleftArea1,
                final JTextField scoreArea1) {
            timeleftArea = timeleftArea1;
            scoreArea = scoreArea1;
        }
        /**
         * run.
         */
        @Override
        public void run() {
            try {
                timeleftArea.setText("20s");
                start.setEnabled(false);
                while (true) {
                    final int thousand = 1000;
                    long currentTime = System.currentTimeMillis();
                    double timeleft = (STAY_IN_TIME_MILLIES - (
                            currentTime - startTime)) / thousand;
                    Thread.sleep(thousand);
                    synchronized (timeleftArea) {
                        timeleftArea.setText(
                                Integer.toString(
                                        (int) (Math.ceil(timeleft))) + "s");
                    }
                    if (timeleft <= 0) {
                        break;
                    }
            }
                timeleftArea.setText("0s");
                final int stopTime = 5000;
                Thread.sleep(stopTime);
                start.setEnabled(true);
                synchronized (timeleftArea) {
                    timeleftArea.setText("20s");
                }
                synchronized (scoreArea) {
                    scoreArea.setText("0");
                }
            } catch (InterruptedException e) {
                timeleftArea.setText("Time thread: Interrupted\n");
            }
            }

    }
    public class ButtonRunnable implements Runnable {
        /**
         * button.
         */
        private JButton button;
        /**
         * timeleftArea.
         */
        private JTextField timeleftArea;
        /**
         * ButtonRunnable.
         * @param button1
         * @param timeleftArea1
         */
        public ButtonRunnable(final JButton button1,
                final JTextField timeleftArea1) {
            button = button1;
            timeleftArea = timeleftArea1;
        }
        /**
         * run.
         */
        @Override
        public void run() {
            while (true) {
                try {
                    final int timeWait = 3000;
                    Thread.sleep(random.nextInt(timeWait));
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                if (timeleftArea.getText().equals("0s")) {
                    break;
                }
                setUp(button);
                try {
                    Thread.sleep(random.nextInt(
                            BUTTON_STAY_MAX - BUTTON_STAY_MIN)
                            + BUTTON_STAY_MIN);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setDown(button);
                if (timeleftArea.getText().equals("0s")) {
                    break;
                }
                try {
                    Thread.sleep(BUTTON_SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (timeleftArea.getText().equals("0s")) {
                    break;
                }
            }
        }
    }
    /**
     * main.
     * @param args
     */
    public static void main(final String[] args) {
        new Game();
    }
}
