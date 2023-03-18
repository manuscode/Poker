package service;

import comparators.*;
import model.Card;
import model.Hand;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static comparators.ComparisonResult.*;
import static model.CardSuit.*;
import static model.CardValue.*;
import static model.Hand.createHand;
import static org.assertj.core.api.Assertions.assertThat;

class ModuleTest {

    private final HighCardComparator highCardComparator = new HighCardComparator();
    private final PairComparator pairComparator = new PairComparator(highCardComparator);
    private final TwoPairsComparator twoPairsComparator = new TwoPairsComparator(highCardComparator);
    private final ThreeOfAKindComparator threeOfAKindComparator = new ThreeOfAKindComparator(highCardComparator);
    private final StraightComparator straightComparator = new StraightComparator(highCardComparator);
    private final FlushComparator flushComparator = new FlushComparator(highCardComparator);
    private final FullHouseComparator fullHouseComparator = new FullHouseComparator(highCardComparator);
    private final FourOfAKindComparator fourOfAKindComparator = new FourOfAKindComparator(highCardComparator);
    private final StraightFlushComparator straightFlushComparator = new StraightFlushComparator(highCardComparator);

    private final HandComparatorService handComparatorService = new HandComparatorService(List.of(
            straightFlushComparator,
            fourOfAKindComparator,
            fullHouseComparator,
            flushComparator,
            straightComparator,
            threeOfAKindComparator,
            twoPairsComparator,
            pairComparator,
            highCardComparator
    ));

    @ParameterizedTest
    @MethodSource("testCaseProvider")
    void compareHands(Hand firstHand, Hand secondHand, ComparisonResult expectedResult) {
        var result = handComparatorService.compareHands(firstHand, secondHand);

        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> testCaseProvider() {
        return Stream.of(

                // HighCard
                Arguments.of(
                        createHand(
                                new Card(CLUBS, TEN),
                                new Card(SPADES, NINE),
                                new Card(DIAMONDS, JACK),
                                new Card(HEARTS, SEVEN),
                                new Card(HEARTS, TWO)),
                        createHand(
                                new Card(SPADES, NINE),
                                new Card(CLUBS, TEN),
                                new Card(DIAMONDS, TWO),
                                new Card(SPADES, JACK),
                                new Card(DIAMONDS, SEVEN)),
                        DRAW
                ),
                Arguments.of(
                        createHand(
                                new Card(CLUBS, TEN),
                                new Card(SPADES, NINE),
                                new Card(DIAMONDS, JACK),
                                new Card(HEARTS, SEVEN),
                                new Card(HEARTS, TWO)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, TEN),
                                new Card(DIAMONDS, THREE),
                                new Card(SPADES, SEVEN),
                                new Card(DIAMONDS, FOUR)),
                        FIRST_HAND_WIN
                ),
                Arguments.of(
                        createHand(
                                new Card(CLUBS, TEN),
                                new Card(SPADES, NINE),
                                new Card(DIAMONDS, JACK),
                                new Card(HEARTS, SEVEN),
                                new Card(HEARTS, TWO)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, TEN),
                                new Card(DIAMONDS, THREE),
                                new Card(SPADES, SEVEN),
                                new Card(DIAMONDS, QUEEN)),
                        SECOND_HAND_WIN
                ),

                // Pair
                Arguments.of(
                        createHand(
                                new Card(CLUBS, TEN),
                                new Card(SPADES, TEN),
                                new Card(DIAMONDS, TWO),
                                new Card(HEARTS, SEVEN),
                                new Card(HEARTS, QUEEN)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, TEN),
                                new Card(DIAMONDS, TEN),
                                new Card(SPADES, SEVEN),
                                new Card(DIAMONDS, QUEEN)),
                        DRAW
                ),
                Arguments.of(
                        createHand(
                                new Card(CLUBS, TEN),
                                new Card(SPADES, TEN),
                                new Card(DIAMONDS, TWO),
                                new Card(HEARTS, SEVEN),
                                new Card(HEARTS, QUEEN)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, NINE),
                                new Card(DIAMONDS, NINE),
                                new Card(SPADES, SEVEN),
                                new Card(DIAMONDS, QUEEN)),
                        FIRST_HAND_WIN
                ),
                Arguments.of(
                        createHand(
                                new Card(CLUBS, TEN),
                                new Card(SPADES, NINE),
                                new Card(DIAMONDS, TWO),
                                new Card(HEARTS, SEVEN),
                                new Card(HEARTS, QUEEN)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, NINE),
                                new Card(DIAMONDS, NINE),
                                new Card(SPADES, SEVEN),
                                new Card(DIAMONDS, QUEEN)),
                        SECOND_HAND_WIN
                ),

                // TwoPairs
                Arguments.of(
                        createHand(
                                new Card(CLUBS, TEN),
                                new Card(SPADES, TEN),
                                new Card(DIAMONDS, TWO),
                                new Card(HEARTS, TWO),
                                new Card(CLUBS, QUEEN)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, TWO),
                                new Card(DIAMONDS, TEN),
                                new Card(HEARTS, TEN),
                                new Card(DIAMONDS, QUEEN)),
                        DRAW
                ),
                Arguments.of(
                        createHand(
                                new Card(CLUBS, TEN),
                                new Card(SPADES, TEN),
                                new Card(DIAMONDS, TWO),
                                new Card(HEARTS, TWO),
                                new Card(CLUBS, KING)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, TWO),
                                new Card(DIAMONDS, TEN),
                                new Card(HEARTS, TEN),
                                new Card(DIAMONDS, QUEEN)),
                        FIRST_HAND_WIN
                ),
                Arguments.of(
                        createHand(
                                new Card(CLUBS, TEN),
                                new Card(SPADES, TEN),
                                new Card(DIAMONDS, TWO),
                                new Card(HEARTS, TWO),
                                new Card(CLUBS, KING)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, TWO),
                                new Card(DIAMONDS, JACK),
                                new Card(HEARTS, JACK),
                                new Card(DIAMONDS, QUEEN)),
                        SECOND_HAND_WIN
                ),

                // ThreeOfAKind
                Arguments.of(
                        createHand(
                                new Card(CLUBS, TEN),
                                new Card(SPADES, TEN),
                                new Card(DIAMONDS, TEN),
                                new Card(HEARTS, TWO),
                                new Card(CLUBS, KING)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, TWO),
                                new Card(DIAMONDS, TWO),
                                new Card(HEARTS, JACK),
                                new Card(DIAMONDS, QUEEN)),
                        FIRST_HAND_WIN
                ),
                Arguments.of(
                        createHand(
                                new Card(CLUBS, THREE),
                                new Card(SPADES, TEN),
                                new Card(DIAMONDS, TEN),
                                new Card(HEARTS, TWO),
                                new Card(CLUBS, KING)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, TWO),
                                new Card(DIAMONDS, TWO),
                                new Card(HEARTS, JACK),
                                new Card(DIAMONDS, QUEEN)),
                        SECOND_HAND_WIN
                ),

                //Straight
                Arguments.of(
                        createHand(
                                new Card(CLUBS, THREE),
                                new Card(SPADES, FOUR),
                                new Card(DIAMONDS, FIVE),
                                new Card(HEARTS, SIX),
                                new Card(CLUBS, SEVEN)),
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(CLUBS, FOUR),
                                new Card(HEARTS, FIVE),
                                new Card(CLUBS, SIX),
                                new Card(DIAMONDS, SEVEN)),
                        DRAW
                ),
                Arguments.of(
                        createHand(
                                new Card(CLUBS, THREE),
                                new Card(SPADES, FOUR),
                                new Card(DIAMONDS, FIVE),
                                new Card(HEARTS, SIX),
                                new Card(CLUBS, SEVEN)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, FOUR),
                                new Card(HEARTS, FIVE),
                                new Card(CLUBS, SIX),
                                new Card(DIAMONDS, SEVEN)),
                        FIRST_HAND_WIN
                ),
                Arguments.of(
                        createHand(
                                new Card(CLUBS, THREE),
                                new Card(SPADES, FOUR),
                                new Card(DIAMONDS, FIVE),
                                new Card(HEARTS, SIX),
                                new Card(CLUBS, SEVEN)),
                        createHand(
                                new Card(SPADES, EIGHT),
                                new Card(CLUBS, FOUR),
                                new Card(HEARTS, FIVE),
                                new Card(CLUBS, SIX),
                                new Card(DIAMONDS, SEVEN)),
                        SECOND_HAND_WIN
                ),

                // Flush
                Arguments.of(
                        createHand(
                                new Card(CLUBS, THREE),
                                new Card(CLUBS, FOUR),
                                new Card(CLUBS, FIVE),
                                new Card(CLUBS, SIX),
                                new Card(CLUBS, EIGHT)),
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(SPADES, FOUR),
                                new Card(SPADES, FIVE),
                                new Card(SPADES, SIX),
                                new Card(SPADES, EIGHT)),
                        DRAW
                ),
                Arguments.of(
                        createHand(
                                new Card(CLUBS, THREE),
                                new Card(CLUBS, FOUR),
                                new Card(CLUBS, FIVE),
                                new Card(CLUBS, SIX),
                                new Card(CLUBS, NINE)),
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(SPADES, FOUR),
                                new Card(SPADES, FIVE),
                                new Card(SPADES, SIX),
                                new Card(SPADES, EIGHT)),
                        FIRST_HAND_WIN
                ),
                Arguments.of(
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(CLUBS, FOUR),
                                new Card(CLUBS, FIVE),
                                new Card(CLUBS, SIX),
                                new Card(CLUBS, NINE)),
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(SPADES, FOUR),
                                new Card(SPADES, FIVE),
                                new Card(SPADES, SIX),
                                new Card(SPADES, EIGHT)),
                        SECOND_HAND_WIN
                ),

                // FullHouse
                Arguments.of(
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(CLUBS, THREE),
                                new Card(DIAMONDS, THREE),
                                new Card(CLUBS, SIX),
                                new Card(HEARTS, SIX)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, TWO),
                                new Card(DIAMONDS, TWO),
                                new Card(CLUBS, SEVEN),
                                new Card(HEARTS, SEVEN)),
                        FIRST_HAND_WIN
                ),
                Arguments.of(
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(CLUBS, EIGHT),
                                new Card(DIAMONDS, FIVE),
                                new Card(CLUBS, SIX),
                                new Card(HEARTS, ACE)),
                        createHand(
                                new Card(SPADES, FOUR),
                                new Card(CLUBS, FOUR),
                                new Card(DIAMONDS, FOUR),
                                new Card(CLUBS, SEVEN),
                                new Card(HEARTS, SEVEN)),
                        SECOND_HAND_WIN
                ),

                // FourOfAKind
                Arguments.of(
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(CLUBS, THREE),
                                new Card(DIAMONDS, THREE),
                                new Card(HEARTS, THREE),
                                new Card(HEARTS, ACE)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, TWO),
                                new Card(DIAMONDS, TWO),
                                new Card(HEARTS, TWO),
                                new Card(HEARTS, SEVEN)),
                        FIRST_HAND_WIN
                ),
                Arguments.of(
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(CLUBS, FOUR),
                                new Card(DIAMONDS, FIVE),
                                new Card(HEARTS, SIX),
                                new Card(HEARTS, ACE)),
                        createHand(
                                new Card(SPADES, TWO),
                                new Card(CLUBS, TWO),
                                new Card(DIAMONDS, TWO),
                                new Card(HEARTS, TWO),
                                new Card(HEARTS, SEVEN)),
                        SECOND_HAND_WIN
                ),

                // StraightFlush
                Arguments.of(
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(SPADES, FOUR),
                                new Card(SPADES, FIVE),
                                new Card(SPADES, SIX),
                                new Card(SPADES, SEVEN)),
                        createHand(
                                new Card(CLUBS, TWO),
                                new Card(CLUBS, THREE),
                                new Card(CLUBS, FOUR),
                                new Card(CLUBS, FIVE),
                                new Card(CLUBS, SIX)),
                        FIRST_HAND_WIN
                ),
                Arguments.of(
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(SPADES, FOUR),
                                new Card(SPADES, FIVE),
                                new Card(SPADES, SIX),
                                new Card(DIAMONDS, EIGHT)),
                        createHand(
                                new Card(CLUBS, TWO),
                                new Card(CLUBS, THREE),
                                new Card(CLUBS, FOUR),
                                new Card(CLUBS, FIVE),
                                new Card(CLUBS, ACE)),
                        SECOND_HAND_WIN
                ),


                // Mixed

                // TwoPairs > Pair
                Arguments.of(
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(HEARTS, THREE),
                                new Card(SPADES, FOUR),
                                new Card(HEARTS, FOUR),
                                new Card(DIAMONDS, EIGHT)),
                        createHand(
                                new Card(CLUBS, FIVE),
                                new Card(DIAMONDS, FIVE),
                                new Card(CLUBS, FOUR),
                                new Card(CLUBS, SIX),
                                new Card(CLUBS, ACE)),
                        FIRST_HAND_WIN
                ),

                // TwoPairs < ThreeOfAKind
                Arguments.of(
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(HEARTS, THREE),
                                new Card(SPADES, FOUR),
                                new Card(HEARTS, FOUR),
                                new Card(DIAMONDS, EIGHT)),
                        createHand(
                                new Card(CLUBS, FIVE),
                                new Card(DIAMONDS, FIVE),
                                new Card(CLUBS, FOUR),
                                new Card(HEARTS, FIVE),
                                new Card(CLUBS, ACE)),
                        SECOND_HAND_WIN
                ),

                // Straight > TwoPairs
                Arguments.of(
                        createHand(
                                new Card(CLUBS, FIVE),
                                new Card(DIAMONDS, SIX),
                                new Card(CLUBS, SEVEN),
                                new Card(HEARTS, NINE),
                                new Card(CLUBS, EIGHT)),
                        createHand(
                                new Card(SPADES, THREE),
                                new Card(HEARTS, THREE),
                                new Card(SPADES, FOUR),
                                new Card(HEARTS, FOUR),
                                new Card(DIAMONDS, EIGHT)),
                        FIRST_HAND_WIN
                ),

                // FullHouse < StraightFlush
                Arguments.of(
                        createHand(
                                new Card(CLUBS, TWO),
                                new Card(DIAMONDS, TWO),
                                new Card(CLUBS, TWO),
                                new Card(HEARTS, NINE),
                                new Card(CLUBS, NINE)),
                        createHand(
                                new Card(CLUBS, THREE),
                                new Card(CLUBS, FOUR),
                                new Card(CLUBS, FIVE),
                                new Card(CLUBS, SIX),
                                new Card(CLUBS, SEVEN)),
                        SECOND_HAND_WIN
                ),

                // Flush > ThreeOfAKind
                Arguments.of(
                        createHand(
                                new Card(CLUBS, THREE),
                                new Card(CLUBS, FOUR),
                                new Card(CLUBS, FIVE),
                                new Card(CLUBS, SIX),
                                new Card(CLUBS, EIGHT)),
                        createHand(
                                new Card(CLUBS, TWO),
                                new Card(DIAMONDS, TWO),
                                new Card(CLUBS, TWO),
                                new Card(HEARTS, NINE),
                                new Card(CLUBS, TEN)),
                        FIRST_HAND_WIN
                ),

                // Pair > FourOfAKind
                Arguments.of(
                        createHand(
                                new Card(CLUBS, THREE),
                                new Card(CLUBS, EIGHT),
                                new Card(CLUBS, FIVE),
                                new Card(CLUBS, SIX),
                                new Card(HEARTS, EIGHT)),
                        createHand(
                                new Card(CLUBS, TWO),
                                new Card(DIAMONDS, TWO),
                                new Card(SPADES, TWO),
                                new Card(HEARTS, TWO),
                                new Card(CLUBS, TEN)),
                        SECOND_HAND_WIN
                )
        );
    }
}