
public class BstNode<T extends Comparable<T>> {
	public T data;
	public BstNode<T> left, right;
	BstNode (T data) {
        this.data = data;
    }
}
