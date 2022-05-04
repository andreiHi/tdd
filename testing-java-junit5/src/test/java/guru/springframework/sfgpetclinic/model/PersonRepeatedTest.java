package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelRepeatedTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;

class PersonRepeatedTest implements ModelRepeatedTests {

    @RepeatedTest(value = 5, name = "{displayName} : {currentRepetition} - {totalRepetitions}" )
    @DisplayName("My Repeated Test")
    void testRepeated() {
        System.out.println("Repeat");
    }

    @RepeatedTest(5)
    void myRepeatedTestWithDi(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        System.out.println(testInfo.getDisplayName() + " : " + repetitionInfo.getCurrentRepetition());
    }

    @RepeatedTest(value = 5, name = "{displayName} : {currentRepetition} | {totalRepetitions}" )
    @DisplayName("My Assigment Repeated Test")
    void myAssigmentRepeated() {

    }
}
