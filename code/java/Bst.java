
public class Bst<T extends Comparable<T>> {

    private class Node {
        private T data;
        private Node left, right;
        private Node (T data) {
            this.data = data;
        }
    }

    private Node root;

	public boolean contains(T d) {
        Node curr = root;
        while (curr != null) {
        	if (d.compareTo(curr.data) == 0) {
                return true;
            } else if (d.compareTo(curr.data) < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return false;
    }

	public void add(T d) {
		Node prev;
		Node curr = root;
		boolean left;
		// Handle special case for root.
		if (curr == null) {
			root = new Node(d);
			return;
		}
		// General case after root.
		while (true) {
			prev = curr;
			if (d.compareTo(curr.data) < 0) {
				curr = curr.left;
				left = true;
			} else {
				curr = curr.right;
				left = false;
			}
			if (curr == null) {
			    curr = new Node(d);
				if (left) {
					prev.left = curr;
				} else {
					prev.right = curr;
				}
				return;
			}
		}
	}

	/*
	 * Removal strategy for a node, x, with two children:
	 * Replace data in x with data from immediate next node, x', then
	 *     prune x' (if it's a leaf) or splice out (otherwise).
	 * To find x' from x: walk one step down the tree right, then
	 *     walk down to the left as far as possible.
	 * To prune or splice out: simply change pointer to x' to point at
	 *     right child of x'.
	 */
	public void remove(T d) {
		Node prev;
		Node curr = root;
		boolean left;
		if (curr == null) {
			return;
		}
		// Handle special case for root.
		if (d.compareTo(curr.data) == 0) {
			if (curr.left == null && curr.right == null) {
				root = null;
			} else if (curr.left == null) {
				root = root.right;
			} else if (curr.right == null) {
				root = root.left;
			} else {
			    prev = curr;
				curr = curr.right;
				while (curr.left != null) {
                    prev = curr;
                    curr = curr.left;
                }
                root.data = curr.data;
                prev.left = curr.right;
			}
			return;
		}
		// General case after root.
		while (true) {
			prev = curr;
			if (d.compareTo(curr.data) < 0) {
				curr = curr.left;
				left = true;
			} else {
				curr = curr.right;
				left = false;
			}
			if (curr == null) {
				return;
			}
			if (d.compareTo(curr.data) == 0) {
				if (curr.left == null && curr.right == null) {
					if (left) {
						prev.left = null;
					} else {
						prev.right = null;
					}
				} else if (curr.left == null) {
					if (left) {
						prev.left = curr.right;
					} else {
						prev.right = curr.right;
					}
				} else if (curr.right == null) {
					if (left) {
						prev.left = curr.left;
					} else {
						prev.right = curr.left;
					}
				} else {
					Node toRemove = curr;
					prev = curr;
					curr = curr.right;
					while (curr.left != null) {
                        prev = curr;
                        curr = curr.left;
                    }
					// Handle special case where left was null to begin with.
					// (Note this special case does not need to be handled above,
					// because the root special case "cancels out" this one.)
					if (curr.left == null) {
					    toRemove.data = curr.data;
					    toRemove.right = curr.right;
					} else {
						toRemove.data = curr.data;
						prev.left = curr.right;
					}
				}
				return;
			}
		}
	}
}
