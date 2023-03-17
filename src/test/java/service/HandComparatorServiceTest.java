package service;

import comparators.ComparisonResult;
import comparators.HandComparator;
import model.Hand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static comparators.ComparisonResult.TIE;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class HandComparatorServiceTest {

    private final Hand firstHand = mock(Hand.class);
    private final Hand secondHand = mock(Hand.class);

    private final HandComparator handComparator1 = mock(HandComparator.class);
    private final HandComparator handComparator2 = mock(HandComparator.class);

    private HandComparatorService testee = new HandComparatorService(List.of(handComparator1, handComparator2));

    @Test
    void compareHands_shouldReturnTie_whenHandComparatorListEmpty() {
        testee = new HandComparatorService(emptyList());

        var result = testee.compareHands(firstHand, secondHand);

        assertThat(result).isEqualTo(TIE);
    }

    @Test
    void compareHands_shouldReturnTie_whenAllHandComparatorsReturnTie() {
        when(handComparator1.compare(eq(firstHand), eq(secondHand))).thenReturn(TIE);
        when(handComparator2.compare(eq(firstHand), eq(secondHand))).thenReturn(TIE);

        var result = testee.compareHands(firstHand, secondHand);

        assertThat(result).isEqualTo(TIE);
        verify(handComparator1).compare(eq(firstHand), eq(secondHand));
        verify(handComparator2).compare(eq(firstHand), eq(secondHand));
    }

    @ParameterizedTest
    @CsvSource({
            "FIRST_HAND_WIN",
            "SECOND_HAND_WIN"
    })
    void compareHands_shouldReturnResultOfFirstHandComparator_whenFirstHandComparatorReturnsWin(
            ComparisonResult comparisonResult
    ) {
        when(handComparator1.compare(eq(firstHand), eq(secondHand))).thenReturn(comparisonResult);

        var result = testee.compareHands(firstHand, secondHand);

        assertThat(result).isEqualTo(comparisonResult);
        verifyNoInteractions(handComparator2);
    }

    @ParameterizedTest
    @CsvSource({
            "FIRST_HAND_WIN",
            "SECOND_HAND_WIN"
    })
    void compareHands_shouldReturnResultOfSecondHandComparator_whenFirstHandComparatorReturnsTie(
            ComparisonResult comparisonResult
    ) {
        when(handComparator1.compare(eq(firstHand), eq(secondHand))).thenReturn(TIE);
        when(handComparator2.compare(eq(firstHand), eq(secondHand))).thenReturn(comparisonResult);

        var result = testee.compareHands(firstHand, secondHand);

        assertThat(result).isEqualTo(comparisonResult);
        verify(handComparator1).compare(eq(firstHand), eq(secondHand));
    }
}