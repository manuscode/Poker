package comparators;

import lombok.AllArgsConstructor;
import model.CardValue;
import model.Hand;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static comparators.ComparisonResult.*;
import static java.util.stream.Collectors.toMap;
import static utils.HandUtil.getCardsByValue;

@AllArgsConstructor
public class FullHouseComparator implements HandComparator {

    private final HighCardComparator highCardComparator;

    @Override
    public ComparisonResult compare(Hand firstHand, Hand secondHand) {
        var firstHandFullHouseValues = extractFullHouseValues(firstHand);
        var secondHandFullHouseValues = extractFullHouseValues(secondHand);

        var firstHandHasFullHouse = firstHandFullHouseValues.isPresent();
        var secondHandHasFullHouse = secondHandFullHouseValues.isPresent();

        if (firstHandHasFullHouse && secondHandHasFullHouse) {
            return highCardComparator.compareSortedValueLists(
                    firstHandFullHouseValues.get(),
                    secondHandFullHouseValues.get()
            );
        }
        if (firstHandHasFullHouse) {
            return FIRST_HAND_WIN;
        }
        if (secondHandHasFullHouse) {
            return SECOND_HAND_WIN;
        }

        return TIE;
    }

    private Optional<List<CardValue>> extractFullHouseValues(Hand hand) {
        var cardsByValue = getCardsByValue(hand);

        if (cardsByValue.size() != 2) {
            return Optional.empty();
        }

        var valueBySize = cardsByValue.entrySet().stream().collect(toMap(
                entry -> entry.getValue().size(),
                Map.Entry::getKey
        ));

        var threeOfAKindValue = valueBySize.get(3);
        var pairValue = valueBySize.get(2);

        return Optional.of(List.of(threeOfAKindValue, pairValue));
    }

}
