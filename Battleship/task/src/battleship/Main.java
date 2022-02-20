package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Write your code here

        String[][] myGameField = initGameField();
        drawGameField(myGameField, false);
        prepareGameField(myGameField);

        String[][] enemyGameField = new String[myGameField.length][];
        for (int i = 0; i < myGameField.length; i++) {
            enemyGameField[i] = Arrays.copyOf(myGameField[i], myGameField[i].length);
        }

        takeShot(enemyGameField);

    }

    private static void prepareGameField(String[][] gameField) {
        chooseAircraftCarrier(gameField);
        chooseBattleship(gameField);
        chooseSubmarine(gameField);
        chooseCruiser(gameField);
        chooseDestroyer(gameField);
    }

    private static void takeShot(String[][] gameField) {
        System.out.println("\nThe game starts!\n");
        drawGameField(gameField, true);
        System.out.println("\nTake a shot!\n");

        int counter = 0;

        while (counter < 5) {
            ShotState state = chooseShipField(gameField);
            drawGameField(gameField, true);

            switch (state) {
                case HIT:
                    System.out.println("\nYou hit a ship! Try again:\n");
                    break;
                case MISSED:
                    System.out.println("\nYou missed. Try again:\n");
                    break;
                case SUNK:
                    System.out.println("You sank a ship! Specify a new target:");
                    counter++;
                    break;
            }

            System.out.println("You sank the last ship. You won. Congratulations!");

        }
    }

    private static void chooseAircraftCarrier(String[][] gameField) {
        System.out.println("\nEnter the coordinates of the Aircraft Carrier (5 cells):\n");
        chooseShip(gameField, 5);
        drawGameField(gameField, false);
    }

    private static void chooseBattleship(String[][] gameField) {
        System.out.println("\nEnter the coordinates of the Battleship (4 cells):");
        chooseShip(gameField, 4);
        drawGameField(gameField, false);
    }

    private static void chooseSubmarine(String[][] gameField) {
        System.out.println("\nEnter the coordinates of the Submarine (3 cells):");
        chooseShip(gameField, 3);
        drawGameField(gameField, false);
    }

    private static void chooseCruiser(String[][] gameField) {
        System.out.println("\nEnter the coordinates of the Cruiser (3 cells):");
        chooseShip(gameField, 3);
        drawGameField(gameField, false);
    }

    private static void chooseDestroyer(String[][] gameField) {
        System.out.println("\nEnter the coordinates of the Destroyer (2 cells):");
        chooseShip(gameField, 2);
        drawGameField(gameField, false);
    }

    private static ShotState chooseShipField(String[][] gameField) {

        ShotState state = ShotState.MISSED;

        while (true) {

            String[] coord = getCoords();

            if (checkShipFieldLocation(coord)) {
                System.out.println();
                state = shot(gameField, coord);
                break;
            } else {
                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            }
        }

        return state;
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
        if (!(checkShipFieldLocation(coord1) && checkShipFieldLocation(coord2))) {
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

    private static boolean checkShipFieldLocation(String[] coord) {

        if (coord[0].charAt(0) < 'A' || coord[0].charAt(0) > 'J') {
            return false;
        }

        if (Integer.parseInt(coord[1]) < 1 || Integer.parseInt(coord[1]) > 10) {
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

    private static ShotState shot(String[][] gameField, String[] coord) {

        ShotState state = ShotState.MISSED;

        int row = coord[0].charAt(0) - 'A' + 1;
        int column = Integer.parseInt(coord[1]);

        if (gameField[row][column].equals("O")) {
            gameField[row][column] = "X";
            state = checkSunk(gameField, row, column);  // HIT OR SUNK
        } else if (gameField[row][column].equals("~")) {
            gameField[row][column] = "M";
            state = ShotState.MISSED;
        }

        return state;

    }

    private static ShotState checkSunk(String[][] gameField, int fieldRow, int fieldColumn) {

        boolean isShipHorizontal = checkShipHorizontal(gameField, fieldRow, fieldColumn);

        if (isShipHorizontal) {

            for (int column = fieldColumn - 1; column >= 1; column--) {
                String field = gameField[fieldRow][column];

                if (field.equals("O")) {
                    return ShotState.HIT;
                } else if (field.equals("~") || field.equals("M")) {
                    break;
                }
            }

            for (int column = fieldColumn + 1; column <= 10; column++) {
                String field = gameField[fieldRow][column];

                if (field.equals("O")) {
                    return ShotState.HIT;
                } else if (field.equals("~") || field.equals("M")) {
                    break;
                }
            }

        } else {

            for (int row = fieldRow - 1; row >= 1; row--) {
                String field = gameField[row][fieldColumn];

                if (field.equals("O")) {
                    return ShotState.HIT;
                } else if (field.equals("~") || field.equals("M")) {
                    break;
                }
            }

            for (int row = fieldRow + 1; row <= 10; row++) {
                String field = gameField[row][fieldColumn];

                if (field.equals("O")) {
                    return ShotState.HIT;
                } else if (field.equals("~") || field.equals("M")) {
                    break;
                }
            }

        }

        return ShotState.SUNK;
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


    private static boolean checkShipHorizontal(String[][] gameField, int row, int column) {

        boolean isShipHorizontal = false;

        if (column > 1 && (gameField[row][column - 1].equals("X") || gameField[row][column - 1].equals("O"))) {
            isShipHorizontal = true;
        } else if (column < 10 && (gameField[row][column + 1].equals("X") || gameField[row][column + 1].equals("O"))) {
            isShipHorizontal = true;
        }

        return isShipHorizontal;
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

    private static void drawGameField(String[][] gameField, boolean isFog) {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {

                String field = gameField[i][j];

                if (isFog && field.equals("O")) {
                    System.out.print("~ ");
                } else {
                    System.out.print(field + " ");
                }
            }
            System.out.println();
        }
    }
}
