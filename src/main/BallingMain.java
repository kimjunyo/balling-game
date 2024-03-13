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
        int peopleNum = ExceptionProcess.peopleInputMismatch(scanner);
        String[] people = new String[peopleNum];

        for (int i = 0; i < peopleNum; i++) {
            System.out.print((i + 1) + "번째 플레이어 이름: ");
            people[i] = scanner.next();
            gameInfoList.add(new GameInfo(people[i]));
        }

        PrintOut.printGameInfoList(peopleNum);

        int round = 1;
        while (round != 11) {
            System.out.println(round + "라운드입니다!");
            for (int i = 0; i < peopleNum; i++) {

                int firstTry = ExceptionProcess.tryInputMismatch(people[i], scanner);
                int secondTry = 0;
                if (firstTry != 10) {
                    secondTry = ExceptionProcess.trySecondInputMismatch(people[i], firstTry, scanner);
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