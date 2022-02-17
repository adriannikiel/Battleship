import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here

        int group1 = scanner.nextInt();
        int group2 = scanner.nextInt();
        int group3 = scanner.nextInt();

        int result = 0;

        result += (group1 % 2 == 0) ? (group1 / 2) : (group1 / 2 + 1);
        result += (group2 % 2 == 0) ? (group2 / 2) : (group2 / 2 + 1);
        result += (group3 % 2 == 0) ? (group3 / 2) : (group3 / 2 + 1);

        System.out.println(result);
    }
}