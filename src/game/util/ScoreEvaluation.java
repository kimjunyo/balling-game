package game.util;

import game.data.GameInfoPerPlayer;
import game.exception.InputExceptionProcess;

import java.util.List;
import java.util.Scanner;

/**
 * 볼링 점수 계산하는 클래스
 */
public class ScoreEvaluation {

    /**
     * 스트라이크나 스페어가 났을 때 음수로 점수 저장, 아니면 양수 저장
     * @param firstTry 첫 번째 쓰러트린 핀의 개수
     * @param secondTry 두 번째 쓰러트린 핀의 개수
     * @return 0, -1 중 둘 중 반환
     */
    public static int roundScore(int firstTry, int secondTry) {
        if (firstTry + secondTry != 10) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * roundScore 계산하는 메서드
     * - fallingDownPin 배열을 하나씩 보는데 만약 X 문자열이 포함되면, ifScoreContainX로 이동
     * - fallingDownPin 배열을 하나씩 보는데 만약 / 문자열이 포함되면, ifScoreContain/로 이동
     * @param gameInfo 게임정보
     */
    public static void roundScoreCalculation(GameInfoPerPlayer gameInfo) {
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

    /**
     * 마지막 라운드 점수 계산
     * @param person 플레이어명
     * @param gameInfo 플레이어마다 게임정보
     * @param scanner 메인에서의 scanner
     */
    public static void lastRoundScore(String person, GameInfoPerPlayer gameInfo, Scanner scanner) {
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
            gameInfo.getFallingDownPin().add(firstTry + "|@");
            gameInfo.getRoundScore().add(0);
        }
        roundScoreCalculation(gameInfo);
    }

    /**
     * 총 점수 계산하는 메서드
     * @param gameInfo 플레이어마다 게임정보
     * @return 총 점수
     */
    public static int caculateTotalScore(GameInfoPerPlayer gameInfo) {
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

    /**
     * fallingDownPin의 요소 중 X를 포함하고 있으면 점수 계산하는 메서드
     * @param fallingDownPin fallingDownPin 문자열 배열
     * @param roundScore 라운드마다 스코어
     * @param beforeScore 해당 인덱스 전의 점수
     * @param i 해당 인덱스
     */
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

    /**
     * fallingDownPin의 요소 중 /를 포함하고 있으면 점수 계산하는 메서드
     * @param fallingDownPin fallingDownPin 문자열 배열
     * @param roundScore 라운드마다 스코어
     * @param beforeScore 해당 인덱스 전의 점수
     * @param i 해당 인덱스
     */
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

    /**
     * 한 프레임에서의 점수를 가져오는 메서드
     * @param score 두 프레임의 점수 문자열
     * @return 한 프레임의 점수
     */
    private static int frame1(String score) {
        String[] split = score.split("\\|");
        if (split[0].equals("-")) return 0;
        return Integer.parseInt(split[0]);
    }

    /**
     * 두 프레임에서의 점수를 가져오는 메서드
     * @param score 두 프레임의 점수 문자열
     * @return 두 프레임의 점수
     */
    private static int frame2(String score) {
        String[] split = score.split("\\|");
        if (score.equals("-")) return 0;
        if (score.contains("@")) return Integer.parseInt(split[0]);
        if (split[0].equals("-")) return Integer.parseInt(split[1]);
        if (split[1].equals("-")) return Integer.parseInt(split[0]);
        if (split[1].equals("/")) return 10;
        return Integer.parseInt(split[0]) + Integer.parseInt(split[1]);
    }


}
