package a005_basic_webdriver_and_junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BasicJunitTest {

    public static int beforeAllCount = 0;
    public static int afterAllCount = 0;
    public static int beforeEachCount = 0;
    public static int afterEachCount = 0;
    public static int testCount = 0;

    @BeforeAll
    public static void runOncePerTestClass(){
        beforeAllCount++;
    }

    @BeforeEach
    public void runBeforeEveryTest(){
        beforeEachCount++;
    }

    @Test
    public void testMethodsAreAnnotatedWithTest() {
        testCount++;

        // Given
        int num1 = 1;

        // When
        int num2 = num1 + num1;

        // Then
        assertEquals(2, num2,
                "expected 1+1 to equal 2");
    }

    @Test
    public void basicAssertionsOnTrueFalse() {
        testCount++;

        // Junit assertions can be used as static methods
        Assertions.assertTrue(1+1==2);

        // Or can be imported statically to make code more readable
        assertFalse(1+3==13);
    }

    @Test
    public void basicAssertionsOnEquality(){
        testCount++;

        assertEquals(1+1, 2);

        assertNotEquals(2+2, 5);
    }

    @AfterEach
    public void runAfterEveryTest(){
        // code here runs after every @Test method
        // will run this even if the test fails
        afterEachCount++;
    }

    @AfterAll
    public static void runOncePerTestClassAfterAll(){
        afterAllCount++;

        // all tests are finished now so output the totals
        System.out.println("Totals");
        System.out.println("======");
        System.out.println("@BeforeAll run " + beforeAllCount);
        System.out.println("@BeforeEach run " + beforeEachCount);
        System.out.println("@Test run " + testCount);
        System.out.println("@AfterEach run " + afterEachCount);
        System.out.println("@AfterAll run " + afterAllCount);
    }
}
