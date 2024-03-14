package game.exception;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputExceptionProcess {
    public static int peopleInputMismatch(Scanner scanner) {
        int peopleNum;
        while (true) {
            try {
                System.out.print("플레이어 수 입력: ");
                peopleNum = scanner.nextInt();
                if (peopleNum < 0) throw new NegativeArraySizeException("잘못된 입력값입니다. 자연수를 입력해주세요.");
                break;
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력값입니다. 자연수를 입력해주세요.");
                scanner.nextLine();
            } catch (NegativeArraySizeException e) {
                System.out.println(e.getMessage());
            }
        }
        return peopleNum;
    }

    public static int tryInputMismatch(String player, Scanner scanner) {
        int gameTry;
        while (true) {
            try {
                System.out.print(player + "님의 첫 번째 쓰러트린 핀의 개수 : ");
                gameTry = scanner.nextInt();
                if (gameTry > 10 || gameTry < 0) throw new RangeException("0 이상 10 이하 숫자를 입력하세요.");
                break;
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력값입니다. 자연수를 입력해주세요.");
                scanner.nextLine();
            } catch (RangeException e) {
                System.out.println(e.getMessage());
            }
        }
        return gameTry;
    }

    public static int trySecondInputMismatch(String player, int firstTry, Scanner scanner) {
        int secondTry;
        while (true) {
            try {
                System.out.print(player + "님의 두 번째 쓰러트린 핀의 개수 : ");
                secondTry = scanner.nextInt();
                if (secondTry > 10 || secondTry < 0) throw new RangeException("0 이상 10 이하 숫자를 입력하세요.");
                if (firstTry + secondTry > 10) throw new RangeException("총 쓰러트린 핀의 개수가 10개를 초과할 수 없습니다.");
                break;
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력값입니다. 자연수를 입력해주세요.");
                scanner.nextLine();
            } catch (RangeException e) {
                System.out.println(e.getMessage());
            }
        }
        return secondTry;
    }
}
