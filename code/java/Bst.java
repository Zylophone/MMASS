
public class Bst<T extends Comparable<T>> {
	public BstNode<T> root;

	public boolean contains(T d) {
        BstNode<T> curr = root;
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
		BstNode<T> prev;
		BstNode<T> curr = root;
		boolean left;
		// Handle special case for root.
		if (curr == null) {
			root = new BstNode<T>(d);
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
			    curr = new BstNode<T>(d);
				if (left) {
					prev.left = curr;
				} else {
					prev.right = curr;
				}
				return;
			}
		}
	}

	public void remove(T d) {
		BstNode<T> prev;
		BstNode<T> curr = root;
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
                // Replace data in the node with data from immediate next element, then
                // prune the immediate next (if leaf) or splice out.
                // To get immediate next: walk one step down the tree right,
                // then walk down to the left as far as possible.
                // To prune or splice out: simply replace previous node's pointer to the
                // node's right child, because nulls.
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
				    // Replace data in the node with data from immediate next element, then
				    // prune the immediate next (if leaf) or splice out (if has right child).
	                // To get immediate next: walk one step down the tree right,
	                // then walk down to the left as far as possible.
				    // To prune or splice out: simply replace previous node's pointer to the
				    // node's right child, because nulls.
					BstNode<T> toRemove = curr;
					prev = curr;
					curr = curr.right;
					while (curr.left != null) {
                        prev = curr;
                        curr = curr.left;
                    }
					// Also handle special case where left was null to begin with.
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
