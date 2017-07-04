import java.util.PriorityQueue;
import java.util.Random;

public class HeapTest {

    public static void main(String[] args) throws Exception {
        Heap<Integer> impl = new Heap<Integer>();
        PriorityQueue<Integer> standard = new PriorityQueue<Integer>();
        Random rand = new Random();
        int[] opcounts = {0, 0};

        for (int i = 0; i < 1000000; i++) {
            int data = rand.nextInt();
            int op = rand.nextInt(2);
            opcounts[op]++;
            if (op == 0) {
                impl.add(data);
                standard.add(data);
            } else if (op == 1) {
                Integer getImpl = impl.get();
                Integer getStandard = standard.poll();
                if (getImpl != getStandard && !getImpl.equals(getStandard)) {
                    throw new Exception("Implementation disagrees with Standard.");
                }
            }
        }

        for (int n : opcounts) {
            if (n == 0) {
                throw new Exception("Not all operations tested.");
            }
        }

        System.out.println("All tests pass.");
    }
}
