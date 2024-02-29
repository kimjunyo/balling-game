package game.exception;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionProcess {
    public static int peopleInputMismatch(int peopleNum, Scanner scanner) {
        while (true) {
            try {
                peopleNum = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력값입니다. 자연수를 입력해주세요.");
            }
        }
        return peopleNum;
    }

    public static int tryInputMismatch(int gameTry, Scanner scanner) {
        while (true) {
            try {
                gameTry = scanner.nextInt();
                if (gameTry > 10 || gameTry < 0) throw new RangeException("0 이상 10 이하 숫자를 입력하세요.");
                break;
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력값입니다. 자연수를 입력해주세요.");
            } catch (RangeException e) {
                System.out.println(e.getMessage());
            }
        }
        return gameTry;
    }

    public static int trySecondInputMismatch(int firstTry, Scanner scanner) {
        int secondTry = 0;
        while (true) {
            try {
                secondTry = scanner.nextInt();
                if (secondTry > 10 || secondTry < 0) throw new RangeException("0 이상 10 이하 숫자를 입력하세요.");
                if (firstTry + secondTry > 10) throw new RangeException("총 쓰러트린 핀의 개수가 10개를 초과할 수 없습니다.");
                break;
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력값입니다. 자연수를 입력해주세요.");
            } catch (RangeException e) {
                System.out.println(e.getMessage());
            }
        }
        return secondTry;
    }
}
