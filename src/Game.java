import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {

    private String[] titlesArray;
    public int random;
    private char[] randomTitleHidden;
    private char[] wrongLettersArray = new char[10];
    private int wrongLetters = 0;
    private int notGuessLetters = 0;


    public Game(String fileName){
        loadFile(fileName);
        this.random = (int)(Math.random()*this.titlesArray.length)+1;
        this.randomTitleHidden = this.titlesArray[this.random].trim().replaceAll("[a-zA-Z0-9]","_").toCharArray();
        clearWrongLettersArray();
    }

    private void loadFile(String fileName){
        File file = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        String titles = "";
        while (scanner.hasNextLine()){
            titles += scanner.nextLine() + "\n";
        }
        this.titlesArray = titles.split("\n");
    }

    private void clearWrongLettersArray(){
        for(int i = 0; i < this.wrongLettersArray.length; i++){
            this.wrongLettersArray[i] = ' ';
        }
    }

    public void startGame(){
        boolean hasWon = false;

        while (!hasWon) {
            printHiddenMovie();
            printWrongLetters();
            guessLetter();

            if (this.notGuessLetters == 0) {
                hasWon = true;
                break;
            } else if (this.wrongLetters == 10){
                break;
            }
        }
        printResultOfGame(getRandomTitle(), hasWon);
    }

    private void guessLetter(){
        System.out.print("\nGuess a letter:");
        Scanner scannerLetter = new Scanner(System.in);
        String letter = scannerLetter.next();
        if (letter.length() == 1) {
            checkLetter(letter);
        } else {
            System.out.println("Please enter one letter!");
        }
    }

    private void printResultOfGame(String randomTitle, boolean hasWon){
        if (hasWon){
            System.out.println("YOU WIN!");
        } else {
            System.out.println("YOU LOSE!");
        }
        System.out.println("You have guessed '" + randomTitle + "' correctly.");
    }


    private void printHiddenMovie(){
        System.out.print("You are guessing: ");
            this.notGuessLetters = 0;
        for (int i = 0; i < (this.randomTitleHidden.length); i++) {
            System.out.print(this.randomTitleHidden[i]);
            if(this.randomTitleHidden[i] == '_')
                this.notGuessLetters++;
        }
        System.out.println();
    }

    private void printWrongLetters(){
        System.out.print("You have guessed (" + this.wrongLetters + ") wrong letters: ");
        for(int i = 0; i < this.wrongLettersArray.length; i++){
            System.out.print(" " + this.wrongLettersArray[i]);
        }
    }

    private String getRandomTitle(){
        return this.titlesArray[this.random].trim();
    }

    private void checkLetter(String letter){
        String title = getRandomTitle().toLowerCase();
        if(title.contains(letter.toLowerCase())){
            for (int i = 0; i < title.length(); i++){
                if (title.toCharArray()[i] == letter.toLowerCase().toCharArray()[0]){
                    this.randomTitleHidden[i] = this.titlesArray[random].trim().toCharArray()[i];
                    this.notGuessLetters--;
                }
            }
        } else {
            this.wrongLettersArray[this.wrongLetters] = letter.toCharArray()[0];
            this.wrongLetters++;
        }
    }
}
