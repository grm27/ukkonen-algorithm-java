package com.grmit.ukkonen;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UkkonenEditDistanceTest {

    private final LevenshteinDistance levenshteinDistance = LevenshteinDistance.getDefaultInstance();
    private final UkkonenEditDistance ukkonenEditDistance = new UkkonenEditDistance();

    @ParameterizedTest(name = "Comparing edit distances between {0} and {1}")
    @CsvSource({
        "ABCDE, FGHIJ",
        "AVERY, GARVEY",
        "ADCROFT, ADDESSI",
        "BAIRD, BAISDEN",
        "BOGGAN, BOGGS",
        "CLAYTON, CLEARY",
        "DYBAS, DYCKMAN",
        "EMINETH, EMMERT",
        "GALANTE, GALICKI",
        "HARDIN, HARDING",
        "KEHOE, KEHR",
        "LOWRY, LUBARSK",
        "MAGALLAN, MAGANA",
        "MAYO, MAYS",
        "MOENY, MOFFETT",
        "PARE, PARENT",
        "RAMEY, RAMFREY",
        "ofosid, daej",
        "of, lisib",
        "nuhijoow, ru",
        "wat, wat",
        "Ukkonen, Levenshtein"
    })
    @DisplayName("Test edit distance comparison between Levenshtein and Ukkonen methods")
    void compareAgainstLevenshtein(String a, String b) {
        long startLevenshtein = System.nanoTime();
        int levenshteinEditDist = levenshteinDistance.apply(a, b);
        long levenshteinElapsed = System.nanoTime() - startLevenshtein;

        long startUkkonen = System.nanoTime();
        int ukkonenEditDist = ukkonenEditDistance.apply(a, b, Math.max(a.length(), b.length()));
        long ukkonenElapsed = System.nanoTime() - startUkkonen;

        assertEquals(levenshteinEditDist, ukkonenEditDist, "Edit distances should match for both algorithms.");
        System.out.printf("Levenshtein time: %d ns, Ukkonen time: %d ns%n", levenshteinElapsed, ukkonenElapsed);
    }


    @ParameterizedTest(name = "Comparing edit distances between {0}")
    @MethodSource("generateRandomStringPair")
    @DisplayName("Random string comparison between Levenshtein and Ukkonen methods with timing")
    public void testUkkonenVsLevenshteinRandom(String[] pair) {
        String a = pair[0];
        String b = pair[1];

        long startLevenshtein = System.nanoTime();
        int levenshteinEditDist = levenshteinDistance.apply(a, b);
        long levenshteinElapsed = System.nanoTime() - startLevenshtein;

        long startUkkonen = System.nanoTime();
        int ukkonenEditDist = ukkonenEditDistance.apply(a, b, Math.max(a.length(), b.length()));
        long ukkonenElapsed = System.nanoTime() - startUkkonen;

        assertEquals(levenshteinEditDist, ukkonenEditDist, "Random test: Edit distances should match for both algorithms.");
        System.out.printf("Random Test - Levenshtein time: %d ns, Ukkonen time: %d ns%n", levenshteinElapsed, ukkonenElapsed);
    }

    private static List<Arguments> generateRandomStringPair() {
        Random random = new Random();
        // Generate a stream of 100 random string pairs
        return IntStream.range(0, 100)
            .mapToObj(i -> {
                String[] stringPair = IntStream.range(0, 2)
                    .mapToObj(j -> {
                        // Generate a random string for each element of the pair
                        return random.ints(97, 123)
                            .limit(10 + new Random().nextInt(90)) // Length between 10 and 100 characters
                            .mapToObj(codePoint -> String.valueOf((char) codePoint))
                            .collect(Collectors.joining());
                    })
                    .toArray(String[]::new);
                return Arguments.of((Object) stringPair);
            })
            .toList();
    }
}
