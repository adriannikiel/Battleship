import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // start coding here

        int charAsNumber = reader.read();

        StringBuilder builder = new StringBuilder();

        while (charAsNumber != -1 && charAsNumber != 10) {  // (char) charAsNumber) != '\n'
            builder.append((char) charAsNumber);
            charAsNumber = reader.read();
        }
        reader.close();

        System.out.println(builder.reverse());

    }
}