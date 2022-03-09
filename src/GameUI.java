import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;





public class GameUI {
    JFrame frame;

    JTextField field;

    JButton startButton;
    JButton guessInput;
    JButton rules;

    JRootPane enterKeyEnabler; // RootPane used to accept "ENTER" key as input during gameplay

    JLabel welcomeText;
    JLabel gameText;  // Game text gives player in-game hints and tells them when they win or lose
    JLabel timer;

    int playerGuess;
    int randomNum = 0;
    double startTime;
    double stopTime;
    boolean gameOver = false;




    /**
     This creates the basic JFrame using Java Swing API
     */
    public GameUI() {
        this.frame = new JFrame("Number Hunter");
        this.startButton = new JButton("Start the Game!");
        this.guessInput = new JButton("Guess!");
        this.field = new JTextField("");
        this.rules = new JButton("Info");
        this.welcomeText = new JLabel("Welcome to the game! Press start to play!");
        this.timer = new JLabel();
        this.gameText = new JLabel();

        field.setBounds(100, 100, 200, 50);
        startButton.setBounds(100, 20, 200, 50);
        rules.setBounds(100, 75, 200, 50);
        guessInput.setBounds(150, 250, 100, 50);
        welcomeText.setBounds(75, 300, 400, 50);
        gameText.setBounds(135,200, 400, 50);
        timer.setBounds(180, 160, 100, 50);

        frame.add(startButton);
        frame.add(rules);
        frame.add(welcomeText);

        frame.add(guessInput);
        frame.add(field);
        frame.add(timer);
        frame.add(gameText);
        guessInput.setVisible(false);
        field.setVisible(false);
        timer.setVisible(false);
        gameText.setVisible(false);

        frame.setLayout(null);
        frame.setSize(400, 400);
        frame.setVisible(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer();
                gameLogic();
            }
        });

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "The goal of this game is to guess a random number between 1 and 100!  You have 15 seconds...  after that you lose. Good luck!");
            }
        });
    }





    /**
     This is the games "logic" it creates the random number and calls other required methods
     */
        public void gameLogic(){
            enterKeyEnabler = frame.getRootPane(); // Using this RootPane pressing "ENTER" key is same as pressing "guess" button
            enterKeyEnabler.setDefaultButton(guessInput);

            startButton.setVisible(false);
            welcomeText.setVisible(false);
            rules.setVisible(false);

            guessInput.setVisible(true);
            field.setVisible(true);
            timer.setVisible(true);
            gameText.setVisible(true);

            randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
            System.out.println("randomNum is: " + randomNum); // TESTING PURPOSE
            // Here I generate a random number between 1 and 10 that the player is meant to guess


            guessInput.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Thread(()->{
                        System.out.println("Current amount of active threads: " + Thread.activeCount()); // INFO

                        // First IF-statement checks if players input contains incorrect characters or if it is blank...
                        if (field.getText().matches("[0-9]+")){
                            playerGuess = Integer.parseInt(field.getText());

                            // Second If-statement checks if the guess is right or wrong
                            if (playerGuess == randomNum){
                                gameOver = true;
                            }
                            field.setText(""); // Makes textField empty after guess is made

                            if (playerGuess < randomNum){
                                gameText.setText("Too low. Guess higher!");
                            } else if (playerGuess > randomNum){
                                gameText.setText("Too high. Guess lower!");
                            }

                        } else if (field.getText().isEmpty()){
                            gameText.setText("Nothing was entered...");
                        } else {
                            gameText.setText("YOU CAN ONLY INPUT NUMBERS!!");
                        }

                        try {
                            Thread.currentThread().join(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }

                    }).start();
                }
            });
        }





    /**
     The timer counts up from 0 to 20 and makes sure that the player loses should he run out of time...
     */

    public void timer(){
            new Thread(()->{
                System.out.println("Current amount of active threads: " + Thread.activeCount()); // INFO
                startTime = System.currentTimeMillis();
                stopTime = startTime + 15_000.0;

                // Timer runs until it either hits 10 seconds or a correct guess is made by player
                while (System.currentTimeMillis() <= stopTime && gameOver == false){
                    timer.setText(String.valueOf((System.currentTimeMillis()-startTime)/1000)); // Syntax-error has been corrected!
                }

                if (System.currentTimeMillis() == stopTime || gameOver == false){
                    gameText.setText("       You have lost :c"); // Moved Strings to make coordinates more symmetrical in GUI
                    guessInput.setVisible(false); field.setVisible(false);

                    try {   // Program waits 3 seconds to let user see result and then exits...
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);

                } else {
                    gameText.setText("             Victory!");
                    guessInput.setVisible(false); field.setVisible(false);

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }

                System.out.println("Current amount of active threads: " + Thread.activeCount()); // INFO

                try {
                    Thread.currentThread().join(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }





        public void postGameMenu(){  // Method not finished/implemented
            guessInput.setVisible(false);
            field.setVisible(false);
            timer.setVisible(false);
            gameText.setVisible(false);
            startButton.setVisible(true);
            randomNum = 0;

            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameLogic();
                    timer();
                }
            });
        }

}
