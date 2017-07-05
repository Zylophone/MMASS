
public class Stairs {
    /*
     * Problem: you can take 1, 2, or 3 steps on a staircase. How many possible
     * ways are there to go up (or down) the staircase?
     * Solution: dynamic programming.
     */
    public static void main(String[] args) {
        int n = 10;
        int[] solns = new int[n+1];
        solns[0] = 1;
        for (int i = 1; i <= n; i++) {
            if (i-1 >= 0) {
                solns[i] += solns[i-1];
            }
            if (i-2 >= 0) {
                solns[i] += solns[i-2];
            }
            if (i-3 >= 0) {
                solns[i] += solns[i-3];
            }
        }
        System.out.println(solns[n]);
    }
}
