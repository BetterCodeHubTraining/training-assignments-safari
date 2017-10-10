/**
 * This file originates from: https://github.com/oreillymedia/building_maintainable_software
 */
package eu.sig.safari.exercises.automate_tests.binarytrees;

import eu.sig.safari.exercises.stubs.binarytrees.BinaryTreeNode;
import eu.sig.safari.exercises.stubs.binarytrees.TreeException;

public class BinarySearchTree {

    public static int calculateDepth(BinaryTreeNode<Integer> node, int nodeValue) {
        int depth = 0;

        if (node == null) {
            throw new TreeException("Value not found in tree!");
        }

        if (node.getValue() == nodeValue) {
            return depth;
        }

        if (nodeValue < node.getValue()) {
            return 1 + calculateDepth(node.getLeft(), nodeValue);
        } else {
            return 1 + calculateDepth(node.getRight(), nodeValue);
        }
    }
}
