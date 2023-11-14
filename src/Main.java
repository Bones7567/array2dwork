import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board b = new Board(8, 9);
        while (true){
            b.printBoard(true);
            System.out.println("Enter w to go up, s to go down, a to go left, and d to go right >> ");
            char userInput = scanner.next().toLowerCase().charAt(0);
            b.move(userInput);
        }
    }
}