package a005_basic_webdriver_and_junit.java;

public class FluentCalculator {

    private int runningTotal;

    public FluentCalculator(){
        runningTotal = 0;
    }

    public FluentCalculator add(final int i) {
        runningTotal = runningTotal + i;
        return this;
    }

    public FluentCalculator times(final int i) {
        runningTotal = runningTotal * i;
        return this;
    }

    public FluentCalculator dividedBy(final int i) {
        runningTotal = runningTotal / i;
        return this;
    }

    public FluentCalculator minus(final int i) {
        runningTotal = runningTotal - i;
        return this;
    }

    public int getResult() {
        return runningTotal;
    }
}
