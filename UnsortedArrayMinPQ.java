package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class UnsortedArrayMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the element-priority pairs in no specific order.
     */
    private final List<PriorityNode<E>> elements;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        elements = new ArrayList<>();
    }

    /**
     * Constructs an instance containing all the given elements and their priority values.
     *
     * @param elementsAndPriorities each element and its corresponding priority.
     */
    public UnsortedArrayMinPQ(Map<E, Double> elementsAndPriorities) {
        elements = new ArrayList<>(elementsAndPriorities.size());
        for (Map.Entry<E, Double> entry : elementsAndPriorities.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        // TODO: Replace with your code
        elements.add(new PriorityNode<>(element, priority));
    }

    @Override
    public boolean contains(E element) {
        // TODO: Replace with your code
        for (PriorityNode<E> entry : elements) {
            if (entry.getElement().equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double getPriority(E element) {
        // TODO: Replace with your code
        for (PriorityNode<E> entry : elements) {
            if (entry.getElement().equals(element)) {
                return entry.getPriority();
            }
        }
        throw new java.util.NoSuchElementException("There is no such element");
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        PriorityNode<E> min = elements.get(0);
        for (PriorityNode<E> entry : elements) {
            if (entry.getPriority() < min.getPriority()) {
                min = entry;
            }
        }
        return min.getElement();
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        int minIndex = 0;
        for (int i = 1; i < elements.size(); i++) {
            if (elements.get(i).getPriority() < elements.get(minIndex).getPriority()) {
                minIndex = i;
            }
        }
        E min = elements.get(minIndex).getElement();
        elements.remove(minIndex);
        return min;
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        // TODO: Replace with your code
        for (PriorityNode<E> entry : elements) {
            if (entry.getElement().equals(element)) {
                entry.setPriority(priority);
            }
        }
    }

    @Override
    public int size() {
        // TODO: Replace with your code
        return elements.size();
    }
}