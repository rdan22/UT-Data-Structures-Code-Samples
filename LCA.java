/*
Finding the least common ancestor of two nodes in a bst.
*/
import java.util.Stack 

public class LCA {

    public static void main(String[] args) {
        
    }
    
    public Node lca(Node tree, Node n1, Node n2) {
        if (n1.equals(n2)) return n1;

        Stack pathToN1 = pathTo(tree, n1);
        Stack pathToN2 = pathTo(tree, n2);
        if (pathToN1 == null || pathToN2 == null) return null;

        Node prev = null;
        while (!pathToN1.isEmpty() && !pathToN2.isEmpty()) {
            Node s = pathToN1.pop();
            Node t = pathToN2.pop();
            if (s.equals(t)) prev = s;
            else break;
        }

        return prev;
    }

    public Stack<Node> pathTo(Node tree, Node n) {
        if (tree == null) return null;
        if (tree.equals(n)) {
            Stack<Node> s = new Stack<Node>();
            s.push(tree);
            return s;
        }

        Stack<Node> left = pathTo(tree.left, n);
        Stack<Node> right = pathTo(tree.right, n);

        if (left != null) {
            left.push(tree);
            return left;
        }

        if (right != null) {
            right.push(tree);
            return right;
        }

        return null;
    }
}