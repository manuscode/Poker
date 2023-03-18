package comparators;

import lombok.AllArgsConstructor;
import model.CardValue;
import model.Hand;

import java.util.Optional;

import static comparators.ComparisonResult.*;
import static comparators.FlushComparator.allCardsHaveSameSuit;
import static comparators.StraightComparator.extractStraightValue;

@AllArgsConstructor
public class StraightFlushComparator implements HandComparator {

    private final HighCardComparator highCardComparator;

    @Override
    public ComparisonResult compare(Hand firstHand, Hand secondHand) {
        var firstHandStraightFlushValue = extractStraightFlushValue(firstHand);
        var secondHandStraightFlushValue = extractStraightFlushValue(secondHand);

        var firstHandHasStraightFlush = firstHandStraightFlushValue.isPresent();
        var secondHandHasStraightFlush = secondHandStraightFlushValue.isPresent();

        if (firstHandHasStraightFlush && secondHandHasStraightFlush) {
            return highCardComparator.compareValues(
                    firstHandStraightFlushValue.get(),
                    secondHandStraightFlushValue.get()
            );
        }
        if (firstHandHasStraightFlush) {
            return FIRST_HAND_WIN;
        }
        if (secondHandHasStraightFlush) {
            return SECOND_HAND_WIN;
        }

        return NO_MATCH;
    }

    private Optional<CardValue> extractStraightFlushValue(Hand hand) {
        var handIsFlush = allCardsHaveSameSuit(hand);
        var straightValue = extractStraightValue(hand);

        if (handIsFlush && straightValue.isPresent()) {
            return straightValue;
        }

        return Optional.empty();
    }
}
