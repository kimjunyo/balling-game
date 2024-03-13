package game.util;

import game.data.GameInfo;
import game.exception.InputExceptionProcess;

import java.util.List;
import java.util.Scanner;

public class ScoreEvaluation {

    public static int roundScore(int firstTry, int secondTry) {
        if (firstTry + secondTry != 10) {
            return 0;
        } else {
            return -1;
        }
    }

    public static void roundScoreCalculation(GameInfo gameInfo) {
        List<String> fallingDownPin = gameInfo.getFallingDownPin();
        List<Integer> roundScore = gameInfo.getRoundScore();
        int size = fallingDownPin.size();
        for (int i = 0; i < size; i++) {
            String score = fallingDownPin.get(i);
            int beforeScore = 0;
            if (i != 0) beforeScore = roundScore.get(i - 1);
            if (score.contains("X")) {
                ifScoreContainX(fallingDownPin, roundScore, beforeScore, i);
            } else if (score.contains("/")) {
                ifScoreContainSlash(fallingDownPin, roundScore, beforeScore, i);
            } else {
                int frame2 = frame2(score);
                roundScore.remove(i);
                roundScore.add(i, beforeScore + frame2);
            }
        }
    }

    public static void lastRoundScore(String person, GameInfo gameInfo, Scanner scanner) {
        String fallingDownPin = gameInfo.getFallingDownPin().get(9);
        int firstTry;
        int secondTry;
        if (fallingDownPin.contains("X")) {
            System.out.println("두 개의 추가 프레임입니다!");
            firstTry = InputExceptionProcess.tryInputMismatch(person, scanner);
            if (firstTry == 10) {
                secondTry = InputExceptionProcess.tryInputMismatch(person, scanner);
            } else {
                secondTry = InputExceptionProcess.trySecondInputMismatch(person, firstTry, scanner);
            }
            String fallDownPin = firstTry + "|" + secondTry;
            gameInfo.getFallingDownPin().add(fallDownPin);
            gameInfo.getRoundScore().add(0);
        } else if (fallingDownPin.contains("/")) {
            System.out.println("한 개의 추가 프레임입니다!");
            firstTry = InputExceptionProcess.tryInputMismatch(person, scanner);
            gameInfo.getFallingDownPin().add(firstTry + "");
            gameInfo.getRoundScore().add(0);
        }
        roundScoreCalculation(gameInfo);
    }

    public static int caculateTotalScore(GameInfo gameInfo) {
        List<Integer> roundScore = gameInfo.getRoundScore();
        int totalScore = 0;
        int size = roundScore.size();
        for (int i = 0; i < size && i != 10; i++) {
            Integer score = roundScore.get(i);
            if (score < 0) break;
            totalScore = score;
        }
        return totalScore;
    }

    private static void ifScoreContainX(List<String> fallingDownPin, List<Integer> roundScore, int beforeScore, int i) {
        try {
            String nextScore = fallingDownPin.get(i + 1);
            if (nextScore.contains("X")) {
                String nextNextScore = fallingDownPin.get(i + 2);
                if (nextNextScore.contains("X")) {
                    roundScore.remove(i);
                    roundScore.add(i, beforeScore + 30);
                } else {
                    int frame1 = frame1(nextNextScore);
                    roundScore.remove(i);
                    roundScore.add(i, beforeScore + frame1 + 20);
                }
            } else {
                int frame2 = frame2(nextScore);
                roundScore.remove(i);
                roundScore.add(i, beforeScore + frame2 + 10);
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    private static void ifScoreContainSlash(List<String> fallingDownPin, List<Integer> roundScore, int beforeScore, int i) {
        try {
            String nextScore = fallingDownPin.get(i + 1);
            if (nextScore.contains("X")) {
                roundScore.remove(i);
                roundScore.add(i, beforeScore + 20);
            } else {
                int frame1 = frame1(nextScore);
                roundScore.remove(i);
                roundScore.add(i, beforeScore + frame1 + 10);
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    private static int frame1(String score) {
        String[] split = score.split("\\|");
        if (split[0].equals("-")) return 0;
        return Integer.parseInt(split[0]);
    }

    private static int frame2(String score) {
        String[] split = score.split("\\|");
        if (split[0].equals("-")) return Integer.parseInt(split[1]);
        if (split[1].equals("-")) return Integer.parseInt(split[0]);
        if (split[1].equals("/")) return 10;
        return Integer.parseInt(split[0]) + Integer.parseInt(split[1]);
    }


}
