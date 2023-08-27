package a005_basic_webdriver_and_junit.java;

public class Calculator {

    private int runningTotal;

    public Calculator(){
        runningTotal = 0;
    }

    public void add(final int i) {
        runningTotal = runningTotal + i;
    }

    public void times(final int i) {
        runningTotal = runningTotal * i;
    }

    public void dividedBy(final int i) {
        runningTotal = runningTotal / i;
    }

    public void minus(final int i) {
        runningTotal = runningTotal - i;
    }

    public int getResult() {
        return runningTotal;
    }
}
