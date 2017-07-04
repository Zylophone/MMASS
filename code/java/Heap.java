import java.util.ArrayList;
import java.util.Collections;

public class Heap<T extends Comparable<T>> {

    /*
     * Invariant: min-heap.
     * Implementation: binary heap.
     */

    ArrayList<T> heap = new ArrayList<T>();

    public void add(T elem) {
        // Add to bottom, then bubble up.
        heap.add(elem);
        int current = heap.size() - 1;
        int parent = (current - 1) / 2;
        while (current != 0
                && heap.get(current).compareTo(heap.get(parent)) < 0) {
            Collections.swap(heap, current, parent);
            current = parent;
            parent = (current - 1) / 2;
        }
    }

    public T get() {
        if (heap.isEmpty()) {
            return null;
        }
        T min = heap.get(0);
        // Put last element at the top, then bubble down.
        int last = heap.size() - 1;
        heap.set(0, heap.get(last));
        heap.remove(last);
        int currIdx = 0;
        while (!heap.isEmpty()) {
            int leftIdx = 2*currIdx + 1;
            int rightIdx = 2*currIdx + 2;
            T curr = heap.get(currIdx);
            if (leftIdx < heap.size() && rightIdx < heap.size()) {
                T left = heap.get(leftIdx);
                T right = heap.get(rightIdx);
                if (left.compareTo(curr) < 0 && right.compareTo(curr) < 0) {
                    if (left.compareTo(right) < 0) {
                        Collections.swap(heap, currIdx, leftIdx);
                        currIdx = leftIdx;
                    } else {
                        Collections.swap(heap, currIdx, rightIdx);
                        currIdx = rightIdx;
                    }
                } else if (left.compareTo(curr) < 0) {
                    Collections.swap(heap, currIdx, leftIdx);
                    currIdx = leftIdx;
                } else if (right.compareTo(curr) < 0) {
                    Collections.swap(heap, currIdx, rightIdx);
                    currIdx = rightIdx;
                } else {
                    break;
                }
            } else if (leftIdx < heap.size()) {
                T left = heap.get(leftIdx);
                if (left.compareTo(curr) < 0) {
                    Collections.swap(heap, currIdx, leftIdx);
                    currIdx = leftIdx;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return min;
    }
}
