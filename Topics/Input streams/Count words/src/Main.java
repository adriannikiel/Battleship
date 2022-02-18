import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // start coding here

        String[] line = reader.readLine().trim().split("\\s+");

        if (line.length == 1 && line[0].equals("")) {
            System.out.println(0);
        } else {
            System.out.println(line.length);
        }

        reader.close();
    }
}