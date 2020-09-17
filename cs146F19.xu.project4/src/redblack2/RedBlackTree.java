package redblack2;

public class RedBlackTree<Key extends Comparable<Key>> {
	private static RedBlackTree.Node<String> root;
	private static final int RED = 0;
	private static final int BLK = 1;
	
	public static class Node<Key extends Comparable<Key>> { // changed to static
		
		
		Key key;
		Node<Key> parent;
		Node<Key> leftChild;
		Node<Key> rightChild;
		boolean isRed;
		int color;

		public Node(Key data) {
			this.key = data;
			leftChild = null;
			rightChild = null;
		}

		public int compareTo(Node<Key> n) { // this < that <0
			return key.compareTo(n.key); // this > that >0
		}

		public boolean isLeaf() {
			if (this.equals(root) && this.leftChild == null && this.rightChild == null)
				return true;
			if (this.equals(root))
				return false;
			if (this.leftChild == null && this.rightChild == null) {
				return true;
			}
			return false;
		}
	}

	public boolean isLeaf(RedBlackTree.Node<String> n) {
		if (n.equals(root) && n.leftChild == null && n.rightChild == null)
			return true;
		if (n.equals(root))
			return false;
		if (n.leftChild == null && n.rightChild == null) {
			return true;
		}
		return false;
	}

	public interface Visitor<Key extends Comparable<Key>> {
		/**
		 * This method is called at each node.
		 * 
		 * @param n the visited node
		 */
		void visit(Node<Key> n);
	}

	public void visit(Node<Key> n) {
		System.out.println(n.key);
	}

	public void printTree() { // preorder: visit, go left, go right
		RedBlackTree.Node<String> currentNode = root;
		printTree(currentNode);
	}

	public void printTree(RedBlackTree.Node<String> node) {
		System.out.print(node.key);
		if (node.isLeaf()) {
			return;
		}
		printTree(node.leftChild);
		printTree(node.rightChild);
	}

	// place a new node in the RB tree with data the parameter and color it red.
	/**
	 * place a new node in the RB tree with data the parameter and color it red.
	 * @param data a string
	 * @return true if the node was added, false if not
	 */
	public boolean addNode(String data) { // this < that <0. this > that >0
		RedBlackTree.Node<String> curr = root;
		RedBlackTree.Node<String> trail;
		RedBlackTree.Node<String> temp = new RedBlackTree.Node<String>(data);
		temp.color = RED;
		if (root == null) {
			root = temp;
			root.color = BLK;
			return true; // added a root to an empty tree
		}
		while (curr != null) {
			trail = curr;
			if (data.compareTo(curr.key) < 0) { // go left
				curr = curr.leftChild;
				if (curr == null) {
					trail.leftChild = temp;
					temp.parent = trail;
					fixTree(temp);
					return true;
				}
			} else if (data.compareTo(curr.key) > 0) { // go right
				curr = curr.rightChild;
				if (curr == null) {
					trail.rightChild = temp;
					temp.parent = trail;
					fixTree(temp);
					return true;
				}
			} else if (data.compareTo(curr.key) == 0) {
				return false; // key already exists
			}
		}
		return false;
		
		
	}

	public void insert(String data) {
		addNode(data);
	}

	public RedBlackTree.Node<String> lookup(String k) {
		RedBlackTree.Node<String> curr = root;
		while (curr != null && !k.equals(curr.key)) {
			if (k.compareTo(curr.key) < 0) {
				curr = curr.leftChild;
			} else {
				curr = curr.rightChild;
			}
		}
		return curr;
	}

	public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n) {
		RedBlackTree.Node<String> pie = n.parent;
		if (pie == null) {
			return null;
		}
		if (pie.leftChild == n) {
			return pie.rightChild;
		} else {
			return pie.leftChild;
		}
	}

	public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n) {
		return getSibling(n.parent);
	}

	public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n) {
		return n.parent.parent;
	}

	public void rotateLeft(RedBlackTree.Node<String> x) {
		RedBlackTree.Node<String> y = x.rightChild;
		x.rightChild = y.leftChild;
		if (y.leftChild != null) {
			y.leftChild.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			root = y;
		} else if (x == x.parent.leftChild) {
			x.parent.leftChild = y;
		} else {
			x.parent.rightChild = y;
		}
		y.leftChild = x;
		x.parent = y;
	}

	public void rotateRight(RedBlackTree.Node<String> y) {
		RedBlackTree.Node<String> x = y.leftChild;
		y.leftChild = x.rightChild;
		if (x.rightChild != null) {
			x.rightChild.parent = y;
		}
		x.parent = y.parent;
		if (y.parent == null) {
			root = x;
		} else if (y == y.parent.rightChild) {
			y.parent.rightChild = x;
		} else {
			y.parent.leftChild = x;
		}
		x.rightChild = y;
		y.parent = x;
	}

	public void fixTree(RedBlackTree.Node<String> curr) {
		if (curr == root) {
			curr.color = BLK;
			return;
		} else if (curr.parent.color == BLK) {
			return;
		} else if (curr.color == RED && curr.parent.color == RED) {
			if (getAunt(curr) == null || getAunt(curr).color == BLK) {
				if (getGrandparent(curr).leftChild == curr.parent && curr.parent.leftChild == curr) { // CASE 3L
					curr.parent.color = BLK;
					getGrandparent(curr).color = RED;
					rotateRight(getGrandparent(curr));
					return;
				} else if (getGrandparent(curr).rightChild == curr.parent && curr.parent.rightChild == curr) { // CASE 3R
					curr.parent.color = BLK;
					getGrandparent(curr).color = RED;
					rotateLeft(getGrandparent(curr));
					return;
				} else if (getGrandparent(curr).leftChild == curr.parent && curr.parent.rightChild == curr) { // CASE 3L
					RedBlackTree.Node<String> par = curr.parent;
					rotateLeft(curr.parent);
					fixTree(par);
				} else if (getGrandparent(curr).rightChild == curr.parent && curr.parent.leftChild == curr) { // CASE 3R
					RedBlackTree.Node<String> par = curr.parent;
					rotateRight(curr.parent);
					fixTree(par);
				}
			} else if (getAunt(curr).color == RED) {
				curr.parent.color = BLK;
				getAunt(curr).color = BLK;
				getGrandparent(curr).color = RED;
				fixTree(getGrandparent(curr));
			}
		}
	}

	public boolean isEmpty(RedBlackTree.Node<String> n) {
		if (n.key == null) {
			return true;
		}
		return false;
	}

	public boolean isLeftChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child) {
		if (child.compareTo(parent) < 0) {// child is less than parent
			return true;
		}
		return false;
	}

	public void preOrderVisit(Visitor<String> v) {
		preOrderVisit(root, v);
	}

	private static void preOrderVisit(RedBlackTree.Node<String> n, Visitor<String> v) {
		if (n == null) {
			return;
		}
		v.visit(n);
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	}
}
