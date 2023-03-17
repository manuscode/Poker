package comparators;

import lombok.AllArgsConstructor;
import model.CardValue;
import model.Hand;

import java.util.Map;
import java.util.Optional;

import static comparators.ComparisonResult.*;
import static utils.HandUtil.getCardsByValue;

@AllArgsConstructor
public class FourOfAKindComparator implements HandComparator {

    private final HighCardComparator highCardComparator;

    @Override
    public ComparisonResult compare(Hand firstHand, Hand secondHand) {
        var firstHandFourOfAKindValue = extractFourOfAKindValue(firstHand);
        var secondHandFourOfAKindValue = extractFourOfAKindValue(secondHand);

        var firstHandHasFourOfAKind = firstHandFourOfAKindValue.isPresent();
        var secondHandHasFourOfAKind = secondHandFourOfAKindValue.isPresent();

        if (firstHandHasFourOfAKind && secondHandHasFourOfAKind) {
            return highCardComparator.compareValues(
                    firstHandFourOfAKindValue.get(),
                    secondHandFourOfAKindValue.get()
            );
        }
        if (firstHandHasFourOfAKind) {
            return FIRST_HAND_WIN;
        }
        if (secondHandHasFourOfAKind) {
            return SECOND_HAND_WIN;
        }

        return TIE;
    }

    private static Optional<CardValue> extractFourOfAKindValue(Hand hand) {
        return getCardsByValue(hand).entrySet().stream()
                .filter(entry -> entry.getValue().size() == 4)
                .findFirst()
                .map(Map.Entry::getKey);
    }
}
