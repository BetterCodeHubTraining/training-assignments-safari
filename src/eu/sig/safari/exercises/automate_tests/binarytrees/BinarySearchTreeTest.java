package eu.sig.safari.exercises.automate_tests.binarytrees;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import eu.sig.safari.exercises.stubs.binarytrees.BinaryTreeNode;
import eu.sig.safari.exercises.stubs.binarytrees.TreeException;

public class BinarySearchTreeTest {

    BinaryTreeNode<Integer> root;

    @Before
    public void setUp() {
        root = new BinaryTreeNode<>(10);
    }

    /** Here is an example of how to use an assertion to check equality between two values */
    @Test
    public void testBasicExample() {
        int actual = 1;
        int expected = 0;
        assertEquals("failure message is printed if actual is not what was expected", expected, actual);
    }

    /** These are the actual tests to implement */
    @Test
    public void testTreeValueInRoot() {
        fail("Not yet implemented");
    }

    public void testValueInLeftSubtree() {
        fail("Not yet implemented");
    }

    public void testValueInRightSubtree() {
        fail("Not yet implemented");
    }

    @Test(expected = TreeException.class)
    public void testValueNotFound() {
        fail("Not yet implemented");
    }
}
