package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    public void addAll(Collection<? extends CharSequence> terms) {
        for (CharSequence term : terms) {
            overallRoot = add(overallRoot, term, 0);
        }
    }

    /**
     * Recursively adds a term to the ternary search tree, character by character.
     */
    private Node add(Node node, CharSequence term, int index) {
        char currentChar = term.charAt(index);

        if (node == null) {
            node = new Node(currentChar);
        }
        if (currentChar < node.data) {
            node.left = add(node.left, term, index);
        } else if (currentChar > node.data) {
            node.right = add(node.right, term, index);
        } else {
            if (index + 1 < term.length()) {
                node.mid = add(node.mid, term, index + 1);
            } else {
                node.isTerm = true;
            }
        }

        return node;
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> result = new ArrayList<>();
        Node prefixNode = getNode(overallRoot, prefix, 0);

        if (prefixNode == null) {
            return result;
        }
        if (prefixNode.isTerm) {
            result.add(prefix);
        }
        collectMatches(prefixNode.mid, prefix.toString(), result);
        return result;
    }

    /**
     * Recursively finds the node corresponding to the last character of the prefix.
     */
    private Node getNode(Node node, CharSequence prefix, int index) {
        if (node == null) {
            return null;
        }

        char currentChar = prefix.charAt(index);
        if (currentChar < node.data) {
            return getNode(node.left, prefix, index);
        } else if (currentChar > node.data) {
            return getNode(node.right, prefix, index);
        } else {
            if (index + 1 == prefix.length()) {
                return node;
            }
            return getNode(node.mid, prefix, index + 1);
        }
    }

    /**
     * Recursively collects all matches starting from the given node, adding valid terms to the result list.
     */
    private void collectMatches(Node node, String prefix, List<CharSequence> result) {
        if (node == null) {
            return;
        }

        collectMatches(node.left, prefix, result);
        String newPrefix = prefix + node.data;
        if (node.isTerm) {
            result.add(newPrefix);
        }
        collectMatches(node.mid, newPrefix, result);
        collectMatches(node.right, prefix, result);
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}
