import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here

        int h1 = scanner.nextInt();
        int m1 = scanner.nextInt();
        int s1 = scanner.nextInt();

        int h2 = scanner.nextInt();
        int m2 = scanner.nextInt();
        int s2 = scanner.nextInt();

        int time1 = s1 + 60 * m1 + 60 * 60 * h1;
        int time2 = s2 + 60 * m2 + 60 * 60 * h2;

        System.out.println(time2 - time1);


    }
}