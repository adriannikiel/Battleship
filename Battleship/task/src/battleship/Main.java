package battleship;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Write your code here

        String[][] gameField = initGameField();

        drawGameField(gameField);

        chooseAircraftCarrier(gameField);
        drawGameField(gameField);

        chooseBattleship(gameField);
        drawGameField(gameField);

        chooseSubmarine(gameField);
        drawGameField(gameField);

        chooseCruiser(gameField);
        drawGameField(gameField);

        chooseDestroyer(gameField);
        drawGameField(gameField);

    }

    private static void chooseAircraftCarrier(String[][] gameField) {
        System.out.println("\nEnter the coordinates of the Aircraft Carrier (5 cells):\n");
        chooseShip(gameField, 5);
    }

    private static void chooseBattleship(String[][] gameField) {
        System.out.println("\nEnter the coordinates of the Battleship (4 cells):");
        chooseShip(gameField, 4);
    }

    private static void chooseSubmarine(String[][] gameField) {
        System.out.println("\nEnter the coordinates of the Submarine (3 cells):");
        chooseShip(gameField, 3);
    }

    private static void chooseCruiser(String[][] gameField) {
        System.out.println("\nEnter the coordinates of the Cruiser (3 cells):");
        chooseShip(gameField, 3);
    }

    private static void chooseDestroyer(String[][] gameField) {
        System.out.println("\nEnter the coordinates of the Destroyer (2 cells):");
        chooseShip(gameField, 2);
    }

    private static void chooseShip(String[][] gameField, int lengthOfShip) {

        while (true) {

            int validator = 0;
            String[] coord1 = getCoords();
            String[] coord2 = getCoords();

            validator = validateCoords(gameField, coord1, coord2, lengthOfShip);

            if (validator == 0) {
                setShip(gameField, coord1, coord2);
                return;
            } else {
                switch (validator) {
                    case 1:
                        System.out.println("\nError! Wrong ship location! Try again:\n");
                        break;
                    case 2:
                        System.out.println("\nError! Wrong length of the Submarine! Try again:\n");
                        break;
                    case 3:
                        System.out.println("\nError! You placed it too close to another one. Try again:\n");
                        break;
                    default:
                        System.out.println("\nError!\n");
                        break;
                }
            }
        }
    }

    private static String[] getCoords() {

        String[] input = scanner.next().split("");
        String[] coords = new String[2];

        coords[0] = input[0];

        if (input.length == 2) {
            coords[1] = input[1];
        } else if (input.length == 3) {
            coords[1] = input[1] + input[2];
        }

        return coords;
    }

    private static int validateCoords(String[][] gameField, String[] coord1, String[] coord2, int lengthOfShip) {

        boolean isHorizontal = false;

        try {
            isHorizontal = checkHorizontal(coord1, coord2);
        } catch (DiagonalPositionException e) {
            return 1;
        }

        // check ship location
        if (!checkShipLocation(coord1, coord2)) {
            return 1;
        }

        // check length of ship
        if (!checkShipLength(coord1, coord2, lengthOfShip, isHorizontal)) {
            return 2;
        }

        // check closeness to other ships
        if (!checkShipCloseness(gameField, coord1, coord2, isHorizontal)) {
            return 3;
        }

        return 0;
    }

    private static boolean checkShipLocation(String[] coord1, String[] coord2) {

        if (coord1[0].charAt(0) < 'A' || coord1[0].charAt(0) > 'J' ||
                coord2[0].charAt(0) < 'A' || coord2[0].charAt(0) > 'J') {
            return false;
        }

        if (Integer.parseInt(coord1[1]) < 1 || Integer.parseInt(coord1[1]) > 10 ||
                Integer.parseInt(coord2[1]) < 1 || Integer.parseInt(coord2[1]) > 10) {
            return false;
        }

        return true;
    }

    private static boolean checkShipLength(String[] coord1, String[] coord2, int lengthOfShip, boolean isHorizontal) {

        int[] locationData = getNewLocationData(coord1, coord2, isHorizontal);

        int first = locationData[0];
        int second = locationData[1];

        return (second - first + 1) == lengthOfShip;
    }

    private static boolean checkShipCloseness(String[][] gameField, String[] coord1, String[] coord2, boolean isHorizontal) {

        int[] locationData = getNewLocationData(coord1, coord2, isHorizontal);

        int first = locationData[0];
        int second = locationData[1];
        int rowOrColumn = locationData[2];

        if (isHorizontal) {
            for (int i = first; i <= second; i++) {
                if (!checkShipFieldCloseness(gameField, rowOrColumn, i)) {
                    return false;
                }
            }
        } else {
            for (int i = first; i <= second; i++) {
                if (!checkShipFieldCloseness(gameField, i, rowOrColumn)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean checkShipFieldCloseness(String[][] gameField, int fieldRow, int fieldColumn) {

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int row = fieldRow + i;
                int column = fieldColumn + j;

                if (row >= 1 && row <= 10 && column >= 1 && column <= 10) {
                    if (gameField[row][column].equals("O")) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static void setShip(String[][] gameField, String[] coord1, String[] coord2) {

        boolean isHorizontal = checkHorizontal(coord1, coord2);

        int[] locationData = getNewLocationData(coord1, coord2, isHorizontal);

        int first = locationData[0];
        int second = locationData[1];
        int rowOrColumn = locationData[2];

        if (isHorizontal) {
            for (int i = first; i <= second; i++) {
                gameField[rowOrColumn][i] = "O";
            }
        } else {
            for (int i = first; i <= second; i++) {
                gameField[i][rowOrColumn] = "O";
            }
        }

    }

    private static int[] getNewLocationData(String[] coord1, String[] coord2, boolean isHorizontal) {

        int first;
        int second;
        int rowOrColumn;

        if (isHorizontal) {
            rowOrColumn = coord1[0].charAt(0) - 'A' + 1;
            first = Integer.parseInt(coord1[1]);
            second = Integer.parseInt(coord2[1]);

        } else {
            rowOrColumn = Integer.parseInt(coord1[1]);
            first = coord1[0].charAt(0) - 'A' + 1;
            second = coord2[0].charAt(0) - 'A' + 1;
        }

        if (first > second) {
            int temp = first;
            first = second;
            second = temp;
        }

        return new int[]{first, second, rowOrColumn};
    }


    private static boolean checkHorizontal(String[] coord1, String[] coord2) throws DiagonalPositionException {

        if (coord1[0].equals(coord2[0])) {
            return true;
        } else if (coord1[1].equals(coord2[1])) {
            return false;
        } else {
            throw new DiagonalPositionException("Should be horizontal or vertical!");
        }
    }

    private static String[][] initGameField() {

        String[][] gameField = new String[11][11];

        // first row
        gameField[0][0] = " ";

        for (int i = 1; i <= 10; i++) {
            gameField[0][i] = String.valueOf(i);
        }

        // first column

        for (int j = 1; j <= 10; j++) {
            gameField[j][0] = String.valueOf((char) ('A' + j - 1));
        }

        // foggy battlefield

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                gameField[i][j] = "~";
            }
        }

        return gameField;
    }

    private static void drawGameField(String[][] gameField) {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                System.out.print(gameField[i][j] + " ");
            }
            System.out.println();
        }
    }
}
