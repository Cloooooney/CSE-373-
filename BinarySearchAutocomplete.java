package autocomplete;

import java.util.*;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> elements;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        elements = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        elements.addAll(terms);
        Collections.sort(elements,CharSequence::compare);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> result = new ArrayList<>();
        int i = Collections.binarySearch(elements, prefix, CharSequence::compare);
        if (i < 0) {
            i = -i - 1;
        }
        while (Autocomplete.isPrefixOf(prefix, elements.get(i))) {
            result.add(elements.get(i));
            i++;
        }
        return result;
    }

}
