package main;

import game.data.GameInfo;
import game.exception.ExceptionProcess;
import game.util.PrintOut;
import game.util.ScoreEvaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BallingMain {

    public static List<GameInfo> gameInfoList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int peopleNum = 0;
        peopleNum = ExceptionProcess.peopleInputMismatch(peopleNum, scanner);
        String[] people = new String[peopleNum];

        for (int i = 0; i < peopleNum; i++) {
            System.out.print((i + 1) + "번째 플레이어 이름: ");
            people[i] = scanner.next();
            gameInfoList.add(new GameInfo(people[i], new ArrayList<>(), new ArrayList<>(), 0));
        }

        PrintOut.printGameInfoList(peopleNum);

        int round = 1;
        while (round != 11) {
            System.out.println(round + "라운드입니다!");
            for (int i = 0; i < peopleNum; i++) {

                System.out.print(people[i] + "님의 첫 번째 쓰러트린 핀의 개수 : ");
                int firstTry = 0;
                firstTry = ExceptionProcess.tryInputMismatch(firstTry, scanner);
                int secondTry = 0;
                if (firstTry != 10) {
                    System.out.print(people[i] + "님의 두 번째 쓰러트린 핀의 개수 : ");
                    secondTry = ExceptionProcess.trySecondInputMismatch(firstTry, scanner);
                }

                GameInfo gameInfo = gameInfoList.get(i);

                String fallDownPin = ScoreEvaluation.fallDownPin(firstTry, secondTry);
                gameInfo.getFallingDownPin().add(fallDownPin);
                int roundScore = ScoreEvaluation.roundScore(firstTry, secondTry);
                gameInfo.getRoundScore().add(roundScore);
                ScoreEvaluation.roundScoreCalculation(gameInfo);

                if (round == 10) ScoreEvaluation.lastRoundScore(people[i], gameInfo, scanner);
                gameInfo.setTotalScore(ScoreEvaluation.caculateTotalScore(gameInfo));
            }
            PrintOut.printGameInfoList(peopleNum);
            round++;
        }

    }
}
