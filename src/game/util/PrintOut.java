package game.util;

import game.data.GameInfo;
import main.BallingMain;

import java.util.List;

public class PrintOut {

    public static String printFallDownPin(int firstTry, int secondTry) {
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

    public static void printGameInfoList(int peopleNum) {
        List<GameInfo> gameInfoList = BallingMain.gameInfoList;
        for (int i = 0; i < peopleNum; i++) {
            System.out.println(gameInfoList.get(i));
        }
    }
}
