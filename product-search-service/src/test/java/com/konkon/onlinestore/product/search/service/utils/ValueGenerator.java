package com.konkon.onlinestore.product.search.service.utils;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.stream.IntStream;

public class ValueGenerator {

    /**
     * 文字列 ランダム生成
     *
     * @param size 生成文字数
     * @return 文字列
     */
    public static String generateString(int size) {

        if (size >= 20) return generateContent();

        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        StringBuilder result = new StringBuilder();

        IntStream.range(1, size).boxed()
                .map(e -> {
                            return alphabet.charAt(random.nextInt(alphabet.length()));
                        }
                )
                .forEach(result::append);

        return result.toString();
    }

    /**
     * 数値 ランダム生成
     *
     * @param max    数値: MAX値
     * @param isPast 符号: true - [-], false - [+]
     * @return 数値
     */
    public static int generateInteger(int max, boolean isPast) {
        Random random = new Random();
        return isPast ? random.nextInt(max) * -1 : random.nextInt(max);
    }

    /**
     * 電話番号 生成
     *
     * @param type 1 - 携帯, 2 - 自宅
     * @return
     */
    public static String generateContactNumber(int type) {
        // [-]を除く電話番号
        int size = type == 1 ? 12 : 11;

        final String numbers = "0123456789";
        StringBuilder result = new StringBuilder();

        IntStream.range(1, size).boxed()
                .forEach(e -> {
                    Random random = new Random();
                    result.append(
                            numbers.charAt(random.nextInt(numbers.length()))
                    );
                });
        switch (type) {
            case 1:
                result.insert(3, "-");
                result.insert(result.length() - 4, "-");
                return result.toString();
            case 2:
                result.insert(4, "-");
                result.insert(result.length() - 4, "-");
                return result.toString();
            default:
                // REVIEW: 独自Exceptionの作成
                throw new RuntimeException("Invalid Contact number, 1 or 2");
        }
    }

    /**
     * E-mail 生成
     *
     * @return
     */
    public static String generateEmail() {
        return new Faker().internet().safeEmailAddress();
    }

    private static String generateContent() {
        return new Faker().lorem().sentence(new Random().nextInt(10));
    }

    public static String generateName() {
        return new Faker().name().firstName();
    }

    public static LocalDate generateBirthDay() {
        return new Faker().date().birthday().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
