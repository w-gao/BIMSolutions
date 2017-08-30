package space.wgao.wilsolutions;

import space.wgao.wilsolutions.api.BIMAPI;
import space.wgao.wilsolutions.api.data.BIMJBook;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * BIMSolutions
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class Controller implements Initializable {

    public ComboBox<String> programChoiceBox;

    public ComboBox<String> bookChoiceBox;

    public TextField chapterInput;

    public TextField sectionInput;

    public TextField exerciseInput;

    public Button viewButton;

    public ImageView answerDisplay;

    private BIMAPI api;

    private String prvUrl = "";

    /**
     * Initialize function, overridden method, calls by JavaFX
     * loads all the necessary data and boots up the program
     *
     * @param location URL location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.initControls();

        // load programs
        this.api = new BIMAPI();
        this.api.loadSources();

        programChoiceBox.setItems(FXCollections.observableArrayList(this.api.getPrograms()));

        System.out.println("View is now loaded!");
    }

    private void initControls(){

        chapterInput.textProperty().addListener((obs, o, n) -> {
            if(!n.matches("^(?:[1-9]||[1][0-2])$")){ // 1-12
                chapterInput.setText(o);
            }
        });

        sectionInput.textProperty().addListener((obs, o, n) -> {
            if(!n.matches("^(?:[1-9]|)$")){ // 1-9
                sectionInput.setText(o);
            }
        });

        exerciseInput.textProperty().addListener((obs, o, n) -> {
            if(!n.matches("^[1-9][0-9]?$||^100$")){ // 1-100
                exerciseInput.setText(o);
            }
        });
    }

    /**
     * calls when the user selects a program
     */
    public void programSelected() {
        // clean
        this.clean();

        // load books
        bookChoiceBox.setItems(FXCollections.observableArrayList(this.api.getTextbookListByProgram(programChoiceBox.getValue())));
    }

    /**
     * calls when the user selects a book
     */
    public void bookSelected() {
        // clean
        this.cleanChapter();
    }

    private boolean checkInput(){
        boolean p = !(programChoiceBox.getValue() == null || bookChoiceBox.getValue() == null);
        boolean ex = !(chapterInput.getText().equals("") || sectionInput.getText().equals("") || exerciseInput.getText().equals(""));

        return p && ex;
    }

    /**
     * calls when the user clicks the view button
     *
     * check the validation of the input & display the answer.
     */
    public void viewButtonClicked(){

        if(!checkInput()) return;

        int chp = Integer.parseInt(chapterInput.getText());
        int sec = Integer.parseInt(sectionInput.getText());
        int exc = Integer.parseInt(exerciseInput.getText());

        BIMJBook textbook = this.api.getTextBook(programChoiceBox.getValue(), bookChoiceBox.getValue());
        if(textbook == null){
            return;
        }

        String awrUrl = textbook.getExercise(chp, sec, exc);

        if(awrUrl == null){
            System.out.println("Invalid request.");
            answerDisplay.setImage(new Image("/invalid.png"));
            return;
        }

        if(awrUrl.equals(prvUrl)){
            System.out.println("Please do not harm the program.");
            return;
        }
        prvUrl = awrUrl;

        Image img = new Image(awrUrl, false);

        if(img.isError()){
            System.out.println("Unable to connect to the hosting server.. Please check your internet connection.");
            answerDisplay.setImage(new Image("/failed.png"));
            return;
        }
        answerDisplay.setImage(img);
    }

    /**
     * calls when the user clicks the previous button
     */
    public void prvExercise() {
        if(!checkInput()) return;

        exerciseInput.setText(String.valueOf(Integer.parseInt(exerciseInput.getText()) - 1));
        viewButtonClicked();
    }

    /**
     * calls when the user clicks the next button
     */
    public void nxtExercise() {
        if(!checkInput()) return;

        exerciseInput.setText(String.valueOf(Integer.parseInt(exerciseInput.getText()) + 1));
        viewButtonClicked();
    }

    /**
     * user KeyEvent
     * checks keyboard inputs
     *
     * @param keyEvent event
     */
    public void keyPressed(KeyEvent keyEvent) {

        if(keyEvent.getCode() == KeyCode.LEFT){
            prvExercise();
        }else if(keyEvent.getCode() == KeyCode.RIGHT){
            nxtExercise();
        }
    }

    private void clean(){
        if(this.bookChoiceBox.getValue() != null){
            this.cleanTextbook();
        }
    }

    private void cleanTextbook(){
        bookChoiceBox.setItems(FXCollections.observableArrayList());
        this.cleanChapter();
    }

    private void cleanChapter(){
        chapterInput.setText("");
        this.cleanSection();
    }

    private void cleanSection(){
        sectionInput.setText("");
        this.cleanExercise();
    }

    private void cleanExercise(){
        exerciseInput.setText("");
        answerDisplay.setImage(null);
    }
}
