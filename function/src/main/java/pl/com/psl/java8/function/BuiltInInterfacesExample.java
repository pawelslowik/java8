package pl.com.psl.java8.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;

/**
 * Created by psl on 22.02.17.
 */
public class BuiltInInterfacesExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuiltInInterfacesExample.class);

    public static void main(String[] args) {
        //Supplier
        Supplier<String> randomLetterSupplier = () -> Character.toString((char) (65 + ThreadLocalRandom.current().nextInt(26)));
        LOGGER.info("This supplier supplies random letter={} without taking any input value", randomLetterSupplier.get());
        //Consumer
        Consumer<String> lowercaseLetterLoggingConsumer = l -> LOGGER.info("This consumer consumes letter={} by printing it in logs", l.toLowerCase());
        lowercaseLetterLoggingConsumer.accept(randomLetterSupplier.get());
        //BiConsumer
        BiConsumer<String, Integer> repeatingLetterLoggingBiConsumer =
                (String l, Integer repeats) -> {
                    for (int i = 1; i <= repeats; i++) {
                        LOGGER.info("This bi consumer consumes letter={} and amount of repeats={} and prints it in logs ({}/{} repeats)",
                                l, repeats, i, repeats);
                    }
                };
        repeatingLetterLoggingBiConsumer.accept(randomLetterSupplier.get(), 3);
        //Predicate
        Predicate<String> vowelPredicate = l ->
        {
            final String[] vowels = new String[]{"A", "E", "I", "O", "U", "Y"};
            for (String vowel : vowels) {
                if (vowel.equalsIgnoreCase(l)) {
                    return true;
                }
            }
            return false;
        };
        String maybeVowel = randomLetterSupplier.get();
        LOGGER.info("This predicate tests if letter={} is a vowel:{}", maybeVowel, vowelPredicate.test(maybeVowel));
        LOGGER.info("The same predicate can be negated to test if letter={} is consonant:{}",
                maybeVowel, vowelPredicate.negate().test(maybeVowel));
        //BiPredicate
        BiPredicate<String, String> equalLettersPredicate = (l1, l2) -> l1.equalsIgnoreCase(l2);
        String maybeEqual = randomLetterSupplier.get();
        LOGGER.info("This bi predicate tests if both letters=[{},{}] are equal:{}",
                maybeVowel, maybeEqual, equalLettersPredicate.test(maybeVowel, maybeEqual));
        //Function
        List<String> letters = getLetters(randomLetterSupplier, 10);
        Function<List<String>, Long> vowelsCounter = (l) -> letters.stream().filter(vowelPredicate).count();
        LOGGER.info("This function takes a list of letters={} and returns the amount of vowels in the list={}",
                letters, vowelsCounter.apply(letters));
        //BiFunction
        List<String> moreLetters = getLetters(randomLetterSupplier, 10);
        BiFunction<List<String>, List<String>, Long> commonLettersCounter =
                (l1, l2) -> l1.stream().distinct().filter(l -> l2.contains(l)).count();
        LOGGER.info("This bi function takes two lists of letters={} and {} and return number of common, distinct letters={}",
                letters, moreLetters, commonLettersCounter.apply(letters, moreLetters));
        //UnaryOperator
        UnaryOperator<String> letterSizeChangingOperator = l -> l.equals(l.toUpperCase()) ? l.toLowerCase() : l.toUpperCase();
        String letter = randomLetterSupplier.get();
        String letterWithChangedSize = letterSizeChangingOperator.apply(letter);
        LOGGER.info("This unary operator changes the size of letter from {} to {} and then back to {}", letter, letterWithChangedSize, letterSizeChangingOperator.apply(letterWithChangedSize));
        //BinaryOperator
        BinaryOperator<String> letterConcatenatingOperator = (l1, l2) -> "[" + l1 + ", " + l2 + "]";
        LOGGER.info("This binary operator concatenates letters={} and {} into {}",
                letter, letterWithChangedSize, letterConcatenatingOperator.apply(letter, letterWithChangedSize));
    }

    private static List<String> getLetters(Supplier<String> supplier, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Expected non-negative amount of letters to get, but received amount=" + amount);
        }
        List<String> letters = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            letters.add(supplier.get());
        }
        return letters;
    }
}
