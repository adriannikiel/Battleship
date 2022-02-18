import java.io.InputStream;

class Main {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;

        byte[] array = inputStream.readAllBytes();

        for (byte b : array) {
            System.out.print(b);
        }

        inputStream.close();
    }
}