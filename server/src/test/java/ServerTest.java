import classes.ThreadClientTest;

import java.io.IOException;

class ServerTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        int j = 0;

        while (j < 10) {
            j++;
            int finalJ = j;
            new Thread(() -> {
                ThreadClientTest client = new ThreadClientTest(finalJ);
                client.run();

            }).start();
            Thread.sleep(1000);
        }
    }
}