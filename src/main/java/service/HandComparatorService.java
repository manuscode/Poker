package service;

import comparators.ComparisonResult;
import comparators.HandComparator;
import lombok.AllArgsConstructor;
import model.Hand;

import java.util.List;

import static comparators.ComparisonResult.DRAW;

@AllArgsConstructor
public class HandComparatorService {

    private final List<HandComparator> orderedHandComparatorList;

    public ComparisonResult compareHands(Hand firstHand, Hand secondHand) {

        for (HandComparator handComparator : orderedHandComparatorList) {
            var comparisonResult = handComparator.compare(firstHand, secondHand);
            if (comparisonResult != DRAW) {
                return comparisonResult;
            }
        }

        return DRAW;
    }
}
