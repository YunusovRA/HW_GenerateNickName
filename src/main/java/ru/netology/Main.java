package ru.netology;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

public class Main {

    private static final AtomicInteger COUNT_3 = new AtomicInteger(0);
    private static final AtomicInteger COUNT_4 = new AtomicInteger(0);
    private static final AtomicInteger COUNT_5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread checkPalindromes = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    if (text.length() == 3) {
                        COUNT_3.incrementAndGet();
                    } else if (text.length() == 4) {
                        COUNT_4.incrementAndGet();
                    } else if (text.length() == 5) {
                        COUNT_5.incrementAndGet();
                    }
                }
            }
        });

        Thread checkSameLetters = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetters(text)) {
                    if (text.length() == 3) {
                        COUNT_3.incrementAndGet();
                    } else if (text.length() == 4) {
                        COUNT_4.incrementAndGet();
                    } else if (text.length() == 5) {
                        COUNT_5.incrementAndGet();
                    }
                }
            }
        });

        Thread checkSortedLetters = new Thread(() -> {
            for (String text : texts) {
                if (isSortedLetters(text)) {
                    if (text.length() == 3) {
                        COUNT_3.incrementAndGet();
                    } else if (text.length() == 4) {
                        COUNT_4.incrementAndGet();
                    } else if (text.length() == 5) {
                        COUNT_5.incrementAndGet();
                    }
                }
            }
        });

        checkPalindromes.start();
        checkSameLetters.start();
        checkSortedLetters.start();

        checkPalindromes.join();
        checkSameLetters.join();
        checkSortedLetters.join();

        System.out.println("Красивых слов с длиной 3: " + COUNT_3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + COUNT_4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + COUNT_5.get() + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static boolean isPalindrome(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    private static boolean isSameLetters(String text) {
        if (text.length() == text.chars().filter(c -> c == text.charAt(0)).count()) {
            return true;
        }
        return false;
    }

    private static boolean isSortedLetters(String text) {
        char[] chars = text.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] < chars[i - 1]) {
                return false;
            }
        }
        return true;
    }
}