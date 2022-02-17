package cz.fel.cvut.ts1;

public class Poledkar {

    public long factorial(int n) {
        long result = 1;
        for (long i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}