import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRedBlackTree {
    private RedBlackTree<Integer> tester;


    @BeforeEach
    public void setup() {
        //replicating the tree from the Gradescope tests so I can better debug my work
        //Known to be an improper RBT, just so I can test these scenarios
        tester = new RedBlackTree<Integer>();
        RedBlackTree.Node<Integer> root = new RedBlackTree.Node<Integer>(45);
        RedBlackTree.Node<Integer> leftChild = new RedBlackTree.Node<Integer>(26);
        RedBlackTree.Node<Integer> rightChild = new RedBlackTree.Node<Integer>(72);
        tester.root = root;
        root.leftChild = leftChild;
        leftChild.parent = root;
        root.rightChild = rightChild;
        rightChild.parent = root;
        tester.root.isBlack = true;
        tester.root.rightChild.isBlack = true;
    }

    @Test
    public void testCaseOne(){
        //testing case 1 violation of RBT rules -- black sibling on opposite side of inserted node
        tester.insert(18);
        assertEquals("[26, 18, 45, 72]", tester.root.toString());
        System.out.println(tester.root.toStringWithColor());
    }
    @Test
    public void testCaseTwo(){
        //testing case 2 of RBT rules -- black sibling on same side of inserted node
        tester.insert(38);
        assertEquals("[38, 26, 45, 72]", tester.root.toString());
        System.out.println(tester.root.toStringWithColor());

    }
    @Test
    public void testCaseThree(){
        //testing ase 3 of RBT rules -- red sibling
        tester.root.rightChild.isBlack = false;
        tester.insert(18);
        assertEquals("[45, 26, 72, 18]", tester.root.toString());
        System.out.println(tester.root.toStringWithColor());
    }

    //now, verify that code works for both sides of the tree
    @Test
    public void testCaseThreeRightSide(){
        tester.root.leftChild.isBlack = true;
        tester.insert(54);
        tester.insert(88);
        tester.insert(79);
        assertEquals("[45, 26, 72, 54, 88, 79]", tester.root.toString());
        System.out.println(tester.root.toStringWithColor()); 

    }

    @Test
    public void testCaseTwoRightSide(){
    tester.root.leftChild.isBlack = true;
    tester.root.rightChild.isBlack = false;
    tester.insert(54);
    assertEquals("[54, 45, 72, 26]", tester.root.toString());
    System.out.println(tester.root.toStringWithColor());
    }
}
