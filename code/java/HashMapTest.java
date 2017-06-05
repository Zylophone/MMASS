import java.util.Random;

public class HashMapTest {
    public static void main(String[] args) throws Exception {
        HashMap<Integer, Integer> impl = new HashMap<Integer, Integer>(16);
        java.util.Map<Integer, Integer> standard = new java.util.HashMap<Integer, Integer>();
        Random rand = new Random();
        int[] opcounts = {0, 0, 0};

        for (int i = 0; i < 1000000; i++) {
            int data = rand.nextInt();
            int op = rand.nextInt(3);
            opcounts[op]++;
            if (op == 0) {
                if (impl.get(data) == null) {
                    if (standard.get(data) != null) {
                        throw new Exception("Implementation disagrees with Standard somewhere.");
                    }
                } else if (standard.get(data) == null) {
                    if (impl.get(data) != null) {
                        throw new Exception("Implementation disagrees with Standard somewhere.");
                    }
                } else if (!impl.get(data).equals(standard.get(data))) {
                    throw new Exception("Implementation disagrees with Standard somewhere.");
                }

            } else if (op == 1) {
                impl.put(data, data);
                standard.put(data, data);
            } else if (op == 2) {
                impl.remove(data);
                standard.remove(data);
            }
        }

        for (int n : opcounts) {
            if (n == 0) {
                throw new Exception("Not all methods tested.");
            }
        }
        System.out.println("All tests pass!");
    }
}
