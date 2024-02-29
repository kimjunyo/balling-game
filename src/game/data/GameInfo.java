package game.data;

import java.util.ArrayList;
import java.util.List;

public class GameInfo {
    String player;
    ArrayList<String> fallingDownPin;
    ArrayList<Integer> roundScore;
    int totalScore;

    public GameInfo(String player, ArrayList<String> fallingDownPin, ArrayList<Integer> roundScore, int totalScore) {
        this.player = player;
        this.fallingDownPin = fallingDownPin;
        this.roundScore = roundScore;
        this.totalScore = totalScore;
    }


    public List<String> getFallingDownPin() {
        return fallingDownPin;
    }

    public List<Integer> getRoundScore() {
        return roundScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        StringBuilder roundArray = new StringBuilder();
        StringBuilder fallingDownPinArray = new StringBuilder();
        StringBuilder roundScoreArray = new StringBuilder();
        int size = fallingDownPin.size();
        for (int i = 1; i < 11; i++) {
            if (i != 10) {
                roundArray.append(i).append("\t|\t");
            } else {
                roundArray.append(i).append("\t\t|");
            }
        }
        for (int i = 0; i < size; i++) {
            String s = fallingDownPin.get(i);
            if (i < 9) {
                fallingDownPinArray.append(s).append("\t\t");
            } else if (i == 9) {
                fallingDownPinArray.append(s).append("|");
            } else {
                fallingDownPinArray.append(changeToBallingWord(s));
            }
        }
        for (int i = 0; i < size && i != 10; i++) {
            int score = roundScore.get(i);
            if (score < 0) {
                roundScoreArray.append(" " + "\t\t");
            } else {
                roundScoreArray.append(score).append("\t\t");
            }
        }
        return "________________________________________________________________________________________________________________________" +
                "\n|\t" + roundArray +
                "\t\t\t\t| PLAYER | " + player +
                "\n\t" + fallingDownPinArray +
                "\n\t" + roundScoreArray +
                "\n총 점수: " + totalScore +
                "\n------------------------------------------------------------------------------------------------------------------------";
    }

    private String changeToBallingWord(String fallingPin) {
        String[] split = fallingPin.split("\\|");
        int firstFrame = Integer.parseInt(split[0]);
        int secondFrame = Integer.parseInt(split[1]);
        if (firstFrame == 10) {
            if (secondFrame == 10) {
                return "X|X";
            } else if (secondFrame == 0) {
                return "X|-";
            } else {
                return "X|" + secondFrame;
            }
        } else if (firstFrame + secondFrame == 10) {
            return firstFrame + "|/";
        } else {
            return firstFrame + "|" + secondFrame;
        }
    }
}
