/**
 * This file originates from: https://github.com/oreillymedia/building_maintainable_software
 */
package eu.sig.safari.exercises.unit_size.binarytrees;

import eu.sig.safari.exercises.stubs.binarytrees.BinaryTreeNode;
import eu.sig.safari.exercises.stubs.binarytrees.TreeException;

public class BinarySearchTree {

    public static int calculateDepth(BinaryTreeNode<Integer> node, int nodeValue) {
        int depth = 0;
        if (node.getValue() == nodeValue) {
            return depth;
        } else {
            if (nodeValue < node.getValue()) {
                BinaryTreeNode<Integer> left = node.getLeft();
                if (left == null) {
                    throw new TreeException("Value not found in tree!");
                } else {
                    return 1 + calculateDepth(left, nodeValue);
                }
            } else {
                BinaryTreeNode<Integer> right = node.getRight();
                if (right == null) {
                    throw new TreeException("Value not found in tree!");
                } else {
                    return 1 + calculateDepth(right, nodeValue);
                }
            }
        }
    }
}
