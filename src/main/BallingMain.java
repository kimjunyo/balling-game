package main;

import game.data.GameInfoPerPlayer;
import game.exception.InputExceptionProcess;
import game.util.PrintOut;
import game.util.ScoreEvaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BallingMain {

    public static List<GameInfoPerPlayer> gameInfoList = new ArrayList<>();

    public static void main(String[] args) {
        PrintOut.startBowling();
        Scanner scanner = new Scanner(System.in);
        int peopleNum = InputExceptionProcess.peopleInputMismatch(scanner);
        String[] people = new String[peopleNum];

        for (int i = 0; i < peopleNum; i++) {
            System.out.print((i + 1) + "번째 플레이어 이름: ");
            people[i] = scanner.next();
            gameInfoList.add(new GameInfoPerPlayer(people[i]));
        }

        PrintOut.printGameInfoList(peopleNum);

        int round = 1;
        while (round != 11) {
            PrintOut.round();
            System.out.println(round + "라운드입니다!");
            for (int i = 0; i < peopleNum; i++) {

                int firstTry = InputExceptionProcess.tryInputMismatch(people[i], scanner);
                int secondTry = 0;
                if (firstTry != 10)
                    secondTry = InputExceptionProcess.trySecondInputMismatch(people[i], firstTry, scanner);

                GameInfoPerPlayer gameInfo = gameInfoList.get(i);

                gameInfo.getFallingDownPin().add(PrintOut.printFallDownPin(firstTry, secondTry));
                gameInfo.getRoundScore().add(ScoreEvaluation.roundScore(firstTry, secondTry));
                ScoreEvaluation.roundScoreCalculation(gameInfo);

                if (round == 10) ScoreEvaluation.lastRoundScore(people[i], gameInfo, scanner);
                gameInfo.setTotalScore(ScoreEvaluation.caculateTotalScore(gameInfo));
            }
            PrintOut.printGameInfoList(peopleNum);
            round++;
        }

    }
}
