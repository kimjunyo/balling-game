package game.data;

import java.util.ArrayList;
import java.util.List;

/**
 * > GameInfoPerPlayer Class
 * - 플레이어마다 게임정보를 저장하는 클래스
 * - 게임 플레이어, 프레임마다 쓰러트린 핀의 개수, 각 라운드마다 점수, 총 점수를 필드로 가지고 있음
 */
public class GameInfoPerPlayer {
    String player;
    ArrayList<String> fallingDownPin;
    ArrayList<Integer> roundScore;
    int totalScore;

    /**
     * > 생성자
     * @param player 콘솔 입력값으로 받으면 GamaeInfoPerPlayer 생성
     */
    public GameInfoPerPlayer(String player) {
        this.player = player;
        this.fallingDownPin = new ArrayList<>();
        this.roundScore = new ArrayList<>();
        this.totalScore = 0;
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

    /**
     * > toString method
     * - 라운드 1부터 10까지 출력, 10라운드 출력 시 탭을 한번 더줌.
     * - 쓰러트린 핀, 라운드마다 점수 출력
     * @return 볼링 게임판 출력
     */
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
                "\n________________________________________________________________________________________________________________________";
    }

    /**
     * 점수를 볼링표기법으로 바꾸는 메서드
     * @param fallingPin 쓰러트린 핀의 문자열
     * @return 볼링 표기법대로 바꾼 문자열
     */
    private String changeToBallingWord(String fallingPin) {
        String[] split = fallingPin.split("\\|");
        int firstFrame = Integer.parseInt(split[0]);
        if(split[1].equals("@")) {
            if(firstFrame==10) return "X";
            if(firstFrame==0) return "-";
            return firstFrame + "";
        }
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
