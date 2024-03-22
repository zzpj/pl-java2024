package com.example.tolkien.service;

import com.example.tolkien.model.TolkienCharacter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.tolkien.model.Race.HOBBIT;
import static org.junit.jupiter.api.Assertions.*;

class DataServiceTest {
    // TODO initialize before each test
    DataService dataService;

    @Test
    @Disabled
    void ensureThatInitializationOfTolkeinCharactorsWorks() {
        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);

        // TODO check that age is 33
        // TODO check that name is "Frodo"
        // TODO check that name is not "Frodon"
    }

    @Test
    @Disabled
    void ensureThatEqualsWorksForCharaters() {
        Object jake = new TolkienCharacter("Jake", 43, HOBBIT);
        Object sameJake = jake;
        Object jakeClone = new TolkienCharacter("Jake", 12, HOBBIT);
        // TODO check that:
        // jake is equal to sameJake
        // jake is not equal to jakeClone
    }

    @Test
    @Disabled
    void checkInheritance() {
        TolkienCharacter tolkienCharacter = dataService.getFellowship().get(0);
        // TODO check that tolkienCharacter.getClass is not a movie class
    }

    @Test
    @Disabled
    void ensureFellowShipCharacterAccessByNameReturnsNullForUnknownCharacter() {
        // TODO imlement a check that dataService.getFellowshipCharacter returns null for an
        // unknow felllow, e.g. "Lars"
    }

    @Test
    @Disabled
    void ensureFellowShipCharacterAccessByNameWorksGivenCorrectNameIsGiven() {
        // TODO imlement a check that dataService.getFellowshipCharacter returns a fellow for an
        // existing felllow, e.g. "Frodo"
    }


    @Test
    @Disabled
    void ensureThatFrodoAndGandalfArePartOfTheFellowsip() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        // TODO check that Frodo and Gandalf are part of the fellowship
    }

    // TODO Use @RepeatedTest(int) to execute this test 1000 times
    @Test
    @Disabled
    @Tag("slow")
    @DisplayName("Minimal stress testing: run this test 1000 times to ")
    void ensureThatWeCanRetrieveFellowshipMultipleTimes() {
        dataService = new DataService();
        assertNotNull(dataService.getFellowship());
    }

    @Test
    @Disabled
    void ensureOrdering() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();

        // ensure that the order of the fellowship is:
        // frodo, sam, merry,pippin, gandalf,legolas,gimli,aragorn,boromir
    }

    @Test
    @Disabled
    void ensureAge() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();

        // TODO test ensure that all hobbits and men are younger than 100 years

        // TODO also ensure that the elfs, dwars the maia are all older than 100 years


        // HINT fellowship.stream might be useful here
    }

    @Test
    @Disabled
    void ensureThatFellowsStayASmallGroup() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        // TODO Write a test to get the 20 element from the fellowship throws an
        // IndexOutOfBoundsException
    }

    @Test
    @Disabled
    void exceptionTesting() {
        DataService dataService = new DataService();
        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> dataService.getFellowship().get(20));
        assertEquals("Index 20 out of bounds for length 9", exception.getMessage());
    }

    @Test
    @Disabled
    public void ensureThatAgeMustBeLargerThanZeroViaSetter() {
        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
        // use assertThrows() rule to check that the message is:
        // Age is not allowed to be smaller than zero
        frodo.setAge(-1);

    }

    @Test
    @Disabled
    public void testThatAgeMustBeLargerThanZeroViaConstructor() {
        // use assertThrows() rule to check that an IllegalArgumentException exception is thrown and
        // that the message is:
        // "Age is not allowed to be smaller than zero"

        TolkienCharacter frodo = new TolkienCharacter("Frodo", -1, HOBBIT);
    }

    @Test
    @Disabled
    public void ensureServiceDoesNotRunToLong() {
        //assertTimeout;
    }

    @Test
    @Disabled
    public void enableTestOnlyOnCertainPlatforms() {
        //
    }
}