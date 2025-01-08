package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class OptimizedHeapMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of element-priority pairs.
     */
    private final List<PriorityNode<E>> elements;
    /**
     * {@link Map} of each element to its associated index in the {@code elements} heap.
     */
    private final Map<E, Integer> elementsToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        elements = new ArrayList<>();
        elementsToIndex = new HashMap<>();
        elements.add(null);
    }

    /**
     * Constructs an instance containing all the given elements and their priority values.
     *
     * @param elementsAndPriorities each element and its corresponding priority.
     */
    public OptimizedHeapMinPQ(Map<E, Double> elementsAndPriorities) {
        elements = new ArrayList<>(elementsAndPriorities.size());
        elementsToIndex = new HashMap<>(elementsAndPriorities.size());
        // TODO: Replace with your code
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
        PriorityNode<E> newNode = new PriorityNode<>(element, priority);
        elements.add(newNode);
        int index = elements.size() - 1;
        elementsToIndex.put(element, index);
        swim(index);
    }

    @Override
    public boolean contains(E element) {
        // TODO: Replace with your code
        return elementsToIndex.containsKey(element);
    }

    @Override
    public double getPriority(E element) {
        // TODO: Replace with your code
        if (!contains(element)) {
            throw new NoSuchElementException("There is no such element");
        }
        return elements.get(elementsToIndex.get(element)).getPriority();
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        return elements.get(1).getElement();
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        E minElement = elements.get(1).getElement();
        swap(1, elements.size() - 1);
        elements.remove(elements.size() - 1);
        elementsToIndex.remove(minElement);
        sink(1);
        return minElement;
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        // TODO: Replace with your code
        int index = elementsToIndex.get(element);
        double oldPriority = elements.get(index).getPriority();
        elements.get(index).setPriority(priority);
        if (priority < oldPriority) {
            swim(index);
        } else {
            sink(index);
        }
    }

    @Override
    public int size() {
        // TODO: Replace with your code
        return elements.size() - 1;
    }

    /**
     * Swim the element with the index to its new position
     *
     * @param index the index of the element
     */
    private void swim(int index) {
        while (index > 1 && elements.get(index).getPriority() < elements.get(index / 2).getPriority()) {
            swap(index, index / 2);
            index /= 2;
        }
    }

    /**
     * Sink the element with the index to its new position
     *
     * @param index the index of the element
     */
    private void sink(int index) {
        int n = elements.size();
        while (2 * index < n) {
            int j = 2 * index;
            if (j + 1 < n && elements.get(j + 1).getPriority() < elements.get(j).getPriority()) {
                j++;
            }
            if (elements.get(index).getPriority() <= elements.get(j).getPriority()) {
                break;
            }
            swap(index, j);
            index = j;
        }
    }

    /**
     * Swap the position of two elements on the two indices
     *
     * @param i the index of the first element
     * @param j the index of the second element
     */
    private void swap(int i, int j) {
        PriorityNode<E> temp = elements.get(i);
        elements.set(i, elements.get(j));
        elements.set(j, temp);
        elementsToIndex.put(elements.get(i).getElement(), i);
        elementsToIndex.put(elements.get(j).getElement(), j);
    }
}
