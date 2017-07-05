import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Subsets {
    /*
     * Problem: given a set of numbers, print all possible subsets.
     * Solution: dynamic programming.
     */
    public static void main(String[] args) {
        int n = 5;
        int[] solns = new int[n + 1];
        solns[0] = 1;
        for (int i = 1; i <= n; i++) {
            solns[i] = 2 * solns[i - 1];
        }
        System.out.println("Number of subsets: " + solns[n]);

        int[] nums = {0, 1, 2, 3, 4};
        List<List<Integer>> sets = new ArrayList<>();
        sets.add(Collections.emptyList());
        for (int i = 0; i < n; i++) {
            int numSets = sets.size();
            for (int j = 0; j < numSets; j++) {
                List<Integer> next = new ArrayList<>(sets.get(j));
                next.add(nums[i]);
                sets.add(next);
            }
        }
        for (List<Integer> set : sets) {
            System.out.println(set.toString());
        }
    }
}
