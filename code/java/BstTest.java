import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class BstTest {
	public static void main(String[] args) throws Exception {
		Bst<Integer> tree = new Bst<Integer>();
		Set<Integer> set = new TreeSet<Integer>();
		Random rand = new Random();
		int[] opcounts = {0, 0, 0};

		for (int i = 0; i < 1000000; i++) {
			int data = rand.nextInt();
			int op = rand.nextInt(3);
			opcounts[op]++;
			if (op == 0) {
			    if (!tree.contains(data) == set.contains(data)) {
			        throw new Exception("Tree disagrees with Set somewhere.");
			    }
			} else if (op == 1) {
				tree.add(data);
				set.add(data);
			} else if (op == 2) {
				tree.remove(data);
				set.remove(data);
			}
		}

		for (int n : opcounts) {
		    assert n > 0;
		}
		System.out.println("All tests pass!");
	}
}
