package comparators;

import model.Card;
import model.CardValue;
import model.Hand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static comparators.ComparisonResult.*;
import static model.CardSuit.*;
import static model.CardValue.*;
import static model.Hand.createHand;
import static org.assertj.core.api.Assertions.assertThat;

class HighCardComparatorTest {

    private final HighCardComparator testee = new HighCardComparator();

    @Test
    void compare_shouldReturnDraw_whenHandsConsistOfEqualValuedCards() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, SEVEN),
                new Card(HEARTS, JACK)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, ACE),
                new Card(HEARTS, TEN),
                new Card(SPADES, JACK),
                new Card(SPADES, SEVEN),
                new Card(DIAMONDS, JACK)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(DRAW);
    }

    @Test
    void compare_shouldReturnFirstHandWin_whenFirstHandHasHighestValuedCard() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, SEVEN),
                new Card(HEARTS, JACK)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, KING),
                new Card(HEARTS, TEN),
                new Card(SPADES, JACK),
                new Card(SPADES, SEVEN),
                new Card(DIAMONDS, JACK)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(FIRST_HAND_WIN);
    }

    @Test
    void compare_shouldReturnSecondHandWin_whenSecondHasHighestValuedCard() {
        var firstHand = createHand(
                new Card(CLUBS, QUEEN),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, SEVEN),
                new Card(HEARTS, JACK)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, KING),
                new Card(HEARTS, TEN),
                new Card(SPADES, JACK),
                new Card(SPADES, SEVEN),
                new Card(DIAMONDS, JACK)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(SECOND_HAND_WIN);
    }

    @Test
    void compare_shouldReturnSecondHandWin_whenAllValuesAreEqualButTheLastOfSecondHandIsHigher() {
        var firstHand = createHand(
                new Card(CLUBS, KING),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, SEVEN),
                new Card(HEARTS, TWO)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, SEVEN),
                new Card(HEARTS, TEN),
                new Card(SPADES, JACK),
                new Card(SPADES, KING),
                new Card(DIAMONDS, THREE)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(SECOND_HAND_WIN);
    }

    @ParameterizedTest
    @CsvSource({
            "'ACE,KING,QUEEN,JACK,TEN'  , 'TEN,JACK,QUEEN,KING,ACE'  , TIE",
            "'NINE,EIGHT,SEVEN,SIX,FIVE', 'SEVEN,TEN,JACK,KING,THREE', SECOND_HAND_WIN",
            "'FOUR,FOUR,THREE,THREE,TWO', 'FOUR,FOUR,THREE,TWO,TWO'  , FIRST_HAND_WIN",
            "'FOUR,FOUR,SIX,THREE,THREE', 'FIVE,FIVE,FOUR,FOUR,TWO'  , FIRST_HAND_WIN",
    })
    void compare_shouldReturnTheCorrectResult(
            String firstCardValues,
            String secondCardValues,
            ComparisonResult expectedResult
    ) {
        var firstHand = createHandFromStringCardValues(firstCardValues);
        var secondHand = createHandFromStringCardValues(secondCardValues);

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "ACE, ACE, TIE",
            "THREE, TWO, FIRST_HAND_WIN",
            "TEN, JACK, SECOND_HAND_WIN",
    })
    void compareValues_shouldReturnSecondHandWin_whenAllValuesAreEqualButTheLastOfSecondHandIsHigher(
            CardValue firstValue,
            CardValue secondValue,
            ComparisonResult expectedResult
    ) {
        var result = testee.compareValues(firstValue, secondValue);

        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "ACE, ACE, TIE",
            "ACE, TEN, FIRST_HAND_WIN",
            "TEN, ACE, SECOND_HAND_WIN",
            "'TEN,JACK,TWO'   , 'JACK,JACK,KING' , SECOND_HAND_WIN",
            "'TEN,TEN,TEN,TEN', 'TEN,TEN,TEN,ACE', SECOND_HAND_WIN",
    })
    void compareValueLists_shouldReturnCorrectResult_whenListSizeIsNotFive(
            String firstValues,
            String secondValues,
            ComparisonResult expectedResult
    ) {
        var firstValueList = createValueListFromStringCardValues(firstValues);
        var secondValueList = createValueListFromStringCardValues(secondValues);

        var result = testee.compareValueLists(firstValueList, secondValueList);

        assertThat(result).isEqualTo(expectedResult);
    }

    private Hand createHandFromStringCardValues(String cardValues) {
        return new Hand(createValueListFromStringCardValues(cardValues).stream()
                .map(cardValue -> new Card(DIAMONDS, cardValue))
                .toList());
    }

    private List<CardValue> createValueListFromStringCardValues(String cardValues) {
        return Arrays.stream(cardValues.split(","))
                .map(CardValue::valueOf)
                .toList();
    }


}