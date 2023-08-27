package a005_basic_webdriver_and_junit;

import a005_basic_webdriver_and_junit.java.Calculator;
import a005_basic_webdriver_and_junit.java.FluentCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicJavaConceptsTest {

    @Test
    public void classesEncapsulateStateAndFunctionality(){
        Calculator calculator = new Calculator();

        calculator.add(6);
        calculator.times(3);
        calculator.dividedBy(2);
        calculator.minus(2);

        assertEquals(7, calculator.getResult());
    }

    @Test
    public void aFluentObjectReturnsThisToAllowChaining(){

        FluentCalculator calculator = new FluentCalculator();

        calculator.
            add(10).
            times(4).
            dividedBy(2).
            minus(3);

        assertEquals(17, calculator.getResult());
    }
}
