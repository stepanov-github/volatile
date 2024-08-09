import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static Random random = new Random();
    public static AtomicInteger count3, count4, count5;

    public static void main(String[] args) throws InterruptedException {
        count3 = new AtomicInteger();
        count4 = new AtomicInteger();
        count5 = new AtomicInteger();

        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                boolean bool = true;
                for (int j = 0; j < texts[i].length() / 2; j++) {
                    if (texts[i].charAt(j) != texts[i].charAt(texts[i].length() - 1 - j)) {
                        bool = false;
                        break;
                    }
                }
                if (bool) addcounter(texts[i].length());
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                char ch = texts[i].charAt(0);
                boolean bool = true;
                for (int j = 0; j < texts[i].length(); j++) {
                    if (texts[i].charAt(j) != ch) {
                        bool = false;
                        break;
                    }
                }
                if (bool) addcounter(texts[i].length());
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                boolean bool = true;
                for (int j = 0; j < texts[i].length() - 1; j++) {
                    if (texts[i].charAt(j) > texts[i].charAt(j + 1)) {
                        bool = false;
                        break;
                    }
                }
                if (bool) addcounter(texts[i].length());
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.printf("Красивых слов с длиной 3: %d шт \n", count3.get());
        System.out.printf("Красивых слов с длиной 4: %d шт \n", count4.get());
        System.out.printf("Красивых слов с длиной 5: %d шт \n", count5.get());
    }

    public static String generateText(String letters, int length) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void addcounter(int i) {
        switch (i) {
            case 3:
                count3.getAndIncrement();
                break;
            case 4:
                count4.getAndIncrement();
                break;
            case 5:
                count5.getAndIncrement();
                break;
        }
    }
}
