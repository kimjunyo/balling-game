package game.util;

import game.data.GameInfo;
import game.exception.ExceptionProcess;

import java.util.List;
import java.util.Scanner;

public class ScoreEvaluation {
    public static String fallDownPin(int firstTry, int secondTry) {
        if (firstTry == 10) {
            return "X";
        } else if (firstTry + secondTry == 10) {
            return firstTry + "|/";
        } else if (firstTry + secondTry == 0) {
            return "-";
        } else {
            return zeroPoint(firstTry) + "|" + zeroPoint(secondTry);
        }
    }

    public static int roundScore(int firstTry, int secondTry) {
        if (firstTry + secondTry != 10) {
            return 0;
        } else if (firstTry == 10) {
            return -2;
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
            } else if (score.contains("/")) {
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
            } else {
                int frame2 = frame2(score);
                roundScore.remove(i);
                roundScore.add(i, beforeScore + frame2);
            }
        }
    }

    public static void lastRoundScore(String person, GameInfo gameInfo, Scanner scanner) {
        String fallingDownPin = gameInfo.getFallingDownPin().get(9);
        int firstTry = 0;
        int secondTry;
        if (fallingDownPin.contains("X")) {
            System.out.print(person + "님의 추가 1프레임 점수 입력: ");
            firstTry = ExceptionProcess.tryInputMismatch(firstTry, scanner);
            if (firstTry == 10) {
                System.out.print(person + "추가 2프레임 점수 입력: ");
                secondTry = ExceptionProcess.tryInputMismatch(firstTry, scanner);
            } else {
                System.out.print(person + "추가 2프레임 점수 입력: ");
                secondTry = ExceptionProcess.trySecondInputMismatch(firstTry, scanner);
            }
            String fallDownPin = firstTry + "|" + secondTry;
            gameInfo.getFallingDownPin().add(fallDownPin);
            gameInfo.getRoundScore().add(0);
        } else if (fallingDownPin.contains("/")) {
            System.out.println(person + "추가 1프레임 점수 입력: ");
            firstTry = ExceptionProcess.tryInputMismatch(firstTry, scanner);
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

    private static int frame1(String score) {
        String[] split = score.split("\\|");
        if (split[0].equals("-")) return 0;
        return Integer.parseInt(split[0]);
    }

    private static int frame2(String score) {
        String[] split = score.split("\\|");
        if (split[0].equals("-")) return 0;
        if (split[1].equals("-")) return Integer.parseInt(split[0]);
        if (split[1].equals("/")) return 10;
        return Integer.parseInt(split[0]) + Integer.parseInt(split[1]);
    }

    private static String zeroPoint(int tryScore) {
        if (tryScore == 0) {
            return "-";
        } else {
            return tryScore + "";
        }
    }
}
