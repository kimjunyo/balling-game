package game.util;

import game.data.GameInfo;
import main.BallingMain;

import java.util.List;

public class PrintOut {
    public static void printGameInfoList(int peopleNum) {
        List<GameInfo> gameInfoList = BallingMain.gameInfoList;
        for (int i = 0; i < peopleNum; i++) {
            System.out.println(gameInfoList.get(i));
        }
    }
}
