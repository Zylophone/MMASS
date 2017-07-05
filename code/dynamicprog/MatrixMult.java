
public class MatrixMult {

    public static void main(String[] args) {
        int len = 5;
        int[] n = new int[len];
        int[] m = new int[len];
        // To test, put some values in n and m.

        int[][] dp = new int[len][len];
        for (int i = 0; i < len; i++) {
            dp[i][i] = 0;
        }

        for (int diff = 1; diff < len; diff++) {
            for (int l = 0; l < len; l++) {
                int r = l + diff;
                if (r >= len) {
                    continue;
                }
                dp[l][r] = Integer.MAX_VALUE;
                for (int i = l; i < r; i++) {
                    int poss = n[l] * m[i] * n[r] + dp[l][i] + dp[i+1][r];
                    if (poss < dp[l][r]) {
                        dp[l][r] = poss;
                    }
                }
            }
        }

        int answer = dp[0][len-1];
        System.out.println(answer);
    }
}
