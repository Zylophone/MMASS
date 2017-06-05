import java.util.Random;

public class BstTest {
	public static void main(String[] args) throws Exception {
		Bst<Integer> impl = new Bst<Integer>();
		java.util.Set<Integer> standard = new java.util.TreeSet<Integer>();
		Random rand = new Random();
		int[] opcounts = {0, 0, 0};

		for (int i = 0; i < 1000000; i++) {
			int data = rand.nextInt();
			int op = rand.nextInt(3);
			opcounts[op]++;
			if (op == 0) {
			    if (!impl.contains(data) == standard.contains(data)) {
			        throw new Exception("Implentation disagrees with Standard somewhere.");
			    }
			} else if (op == 1) {
				impl.add(data);
				standard.add(data);
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
