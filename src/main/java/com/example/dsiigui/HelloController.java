package com.example.dsiigui;

import javafx.animation.KeyFrame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.Random;

/**
 * HelloController - class for color matching game
 * @author Yael Green
 */

public class HelloController {
    public Button Start;
    public Button MainColor;
    public Button color1;
    public Button color2;
    public Button color3;
    public Button color4;
    public Button color5;
    public Button color6;
    public Button color7;
    public Button color8;
    public Label scoreLabel;
    public AnchorPane anchorPane;
    public ImageView correctImg;
    public ImageView wrongImg;
    public Label instructions;
    Random rand = new Random();
    private final Color[] colors = {Color.HOTPINK, Color.DEEPSKYBLUE, Color.DEEPPINK, Color.GOLD, Color.TURQUOISE, Color.MEDIUMPURPLE, Color.AQUAMARINE};
    private final Timeline timeline = new Timeline();
    private final Timeline imgTimeline = new Timeline();
    Button[] ButtonGroup;
    private int score = 0;
    private static final int MAINLENGTH = 3;
    private static final int SHOWIMG = 1;

    @FXML
    private void initialize() {
        ButtonGroup = new Button[]{color1, color2, color3, color4, color5, color6, color7, color8};
        for(Button button : ButtonGroup) {
            button.setOnMouseClicked(event -> checkColors(button));
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        initializeStage();
        startTimeline();
    }

    private void resetButton(Button button, Color color, double num) {
        button.setDisable(false);

        resetButtonLocation(button);

        button.setBackground(Background.fill(color));
        button.setText(String.valueOf(getScore(num)));
    }

    private void resetButtonLocation(Button button) {
        double newX = rand.nextDouble() * (anchorPane.getWidth() - button.getWidth());
        double newY = rand.nextDouble() * (anchorPane.getHeight() - button.getHeight());

        button.setLayoutX(newX);
        button.setLayoutY(newY);
    }

    private Color changeColor() {
        int colorChoice = rand.nextInt(colors.length);
        return colors[colorChoice];
    }

    private void initializeStage() {
        initializeVisibility();

        initializeMainColor();

        initializeButtonGroup();
    }

    private void initializeVisibility() {
        Start.setVisible(false);
        instructions.setVisible(false);
        scoreLabel.setVisible(true);
    }

    private void initializeMainColor() {
        MainColor.setVisible(true);
        MainColor.setBackground(Background.fill(changeColor()));
    }

    private void initializeButtonGroup() {
        for(Button button : ButtonGroup) {
            resetButton(button, changeColor(), 1);
            button.setVisible(true);
        }
    }

    private void startTimeline() {
        createMainKeyFrame();

        createButtonKeyFrames();

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void createMainKeyFrame() {
        KeyFrame changeMainColor = new KeyFrame(Duration.seconds(MAINLENGTH), event -> {
            MainColor.setBackground(Background.fill(changeColor()));
        });
        timeline.getKeyFrames().add(changeMainColor);
    }

    private void createButtonKeyFrames() {
        KeyFrame[] keyFrames = new KeyFrame[ButtonGroup.length];

        for(int i = 0; i < keyFrames.length; i++) {
            keyFrames[i] = createButtonKeyFrame(i);
            timeline.getKeyFrames().add(keyFrames[i]);
        }
    }

    private KeyFrame createButtonKeyFrame(int buttonIx) {
        return new KeyFrame(Duration.seconds(getNextDouble()), event -> {
            KeyFrame currentFrame = (KeyFrame) event.getSource();
            resetButton(ButtonGroup[buttonIx], changeColor(), currentFrame.getTime().toSeconds());
        });
    }

    private void showImg(ImageView imageView) {
        imageView.setVisible(true);
        imgTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(SHOWIMG), event -> {
            imageView.setVisible(false);
        }));
        imgTimeline.play();
    }

    private void checkColors(Button button) {
        button.setDisable(true);

        Color mainColor = getColor(MainColor);
        Color buttonColor = getColor(button);

        changeScore(Integer.parseInt(button.getText()), mainColor.equals(buttonColor));
    }

    private Color getColor(Button button) {
        return (Color) button.getBackground().getFills().get(0).getFill();
    }

    private void changeScore(int num, boolean positive) {
        if(positive) {
            score += num;
            showImg(correctImg);
        }
        else {
            score -= num;
            showImg(wrongImg);
        }

        scoreLabel.setText("Score: " + score);
    }

    private double getNextDouble() {
        return Math.round((.5 + 2.5 * rand.nextDouble()) * 10) / 10.0;
    }

    private int getScore(double time) {
        int score = 0;

        if(time <= 1) {
            score = 3;
        }
        else if(time <= 2) {
            score = 2;
        }
        else {
            score = 1;
        }

        return score;
    }
}