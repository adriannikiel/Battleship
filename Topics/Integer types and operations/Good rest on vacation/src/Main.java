import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int durationInDays = scanner.nextInt();
        int foodCost = scanner.nextInt();
        int flightCost = scanner.nextInt();
        int hotelCost = scanner.nextInt();

        System.out.println(durationInDays * foodCost + 2 * flightCost + (durationInDays - 1) * hotelCost);

    }
}