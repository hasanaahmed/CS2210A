/**
 * 
 * @author hasanahmed Student ID: 250897473
 * this class implements an ordered dictionary using a binary search tree
 *
 */
public class OrderedDictionary implements OrderedDictionaryADT {
	// declare variables
	private Node<Record> root;

	/**
	 * constructor method
	 */
	public OrderedDictionary() {
		root = new Node<Record>();
	}

	/**
	 * 
	 * @param k
	 * @return the Record object with key k, or it returns null if such a record is not in the dictionary
	 */
	public Record get(Pair k) {
		Node<Record> current = root;

		while ((current.hasChildren()) && (current.getElement().getKey().compareTo(k) != 0)) {
			if (current.getElement().getKey().compareTo(k) < 0) {
				current = current.getRightChild();
			} else {
				current = current.getLeftChild();
			}
		}
		return current.getElement();
	}

	/**
	 * inserts r into the ordered dictionary. It throws a DictionaryException if a record with the same key as r is already in the dictionary
	 */
	public void put(Record r) throws DictionaryException {
		Node<Record> current = root;
		Node<Record> parent = new Node<Record>();
		Node<Record> lChild = new Node<Record>();
		Node<Record> rChild = new Node<Record>();

		if (root.getElement() == null) {
			root.setElement(r);
			root.setLeftChild(lChild);
			root.setRightChild(rChild);
		} else if (get(r.getKey()) != null) {
			throw new DictionaryException("Could not put key; already exists.");
		} else {
			while (current.hasChildren()) {
				parent = current;
				if (current.getElement().getKey().compareTo(r.getKey()) < 0) {
					current = current.getRightChild();
				} else {
					current = current.getLeftChild();
				}
			}
			current.setElement(r);
			current.setParent(parent);
			current.setLeftChild(lChild);
			current.setRightChild(rChild);
		}
	}

	/**
	 * removes the record with key k from the dictionary. It throws a DictionaryException if the record is not in the dictionary
	 */
	public void remove(Pair k) throws DictionaryException {
		Node<Record> current = root;
		Node<Record> parent = current;
		Node<Record> rem;
		Node<Record> child = new Node<Record>();
		Node<Record> small = new Node<Record>();
		Node<Record> smallParent;

		rem = findNode(k);
		current = rem;
		parent = rem.getParent();

		if (rem.isLeaf()) {
			throw new DictionaryException("Error; entry not in dictionary.");
		} else {
			if ((rem.getLeftChild().isLeaf()) || (rem.getRightChild().isLeaf())) {
				if (rem.getLeftChild().isLeaf()) {
					child = rem.getRightChild();
				} else {
					child = rem.getLeftChild();
				}
				if (rem.getParent() == null) {
					child.setLeftChild(root.getLeftChild());
					child.setRightChild(root.getRightChild());
					root.setElement(child.getElement());
					return;
				} else if ((parent.getRightChild().getElement() != null) && (parent.getRightChild().getElement()
						.getKey().compareTo(current.getElement().getKey()) == 0)) {
					parent.setRightChild(child);
				} else {
					parent.setLeftChild(child);
				}
			} else {
				small = small(rem.getRightChild());
				small.setRightChild(rem.getRightChild());
				rem.getRightChild().setParent(small);
				small.setLeftChild(rem.getLeftChild());
				rem.getLeftChild().setParent(small);
				smallParent = small.getParent();
				if (parent == null) {
					root.setElement(small.getElement());
				} else if (parent.getLeftChild().getElement().getKey().compareTo(rem.getElement().getKey()) == 0) {
					parent.setLeftChild(small);
				} else {
					parent.setRightChild(small);
				}
				if (smallParent.getLeftChild().getElement().getKey().compareTo(small.getElement().getKey()) == 0) {
					smallParent.setLeftChild(new Node<Record>());
				}
			}
		}
	}

	/**
	 * returns the successor of k (the record from the ordered dictionary with smallest key larger than k); it returns null if the given key has no successor. The given key DOES NOT need to be in the dictionary.
	 */
	public Record successor(Pair k) {
		Node<Record> current = root;
		Node<Record> parent = new Node<Record>();
		Node<Record> prev;

		if (current.isLeaf()) {
			return null;
		}

		current = findNode(k);

		if (current.getElement() == null) {
			current = root;
			prev = current;
			while ((current.hasChildren()) && (current.getElement().getKey().compareTo(k) != 0)) {
				if (current.getElement().getKey().compareTo(k) < 0) {
					prev = current;
					current = current.getRightChild();
				} else {
					prev = current;
					current = current.getLeftChild();
				}

			}
			current = prev;
			if ((current.getElement().getKey().compareTo(k) > 0) && (current.getRightChild().getElement() == null)) {
				return current.getElement();
			}
		}

		if (!current.getRightChild().isLeaf()) {
			return smallest(current.getRightChild());
		} else {
			parent = current.getParent();
			while ((parent.getParent() != null) && (current.isRightChild())) {
				current = parent;
				parent = parent.getParent();
			}
			if ((parent == root) && (current.isRightChild())) {
				return null;
			} else {
				return parent.getElement();
			}
		}
	}

	/**
	 * returns the predecessor of k (the record from the ordered dictionary with largest key smaller than k; it returns null if the given key has no predecessor. The given key DOES NOT need to be in the dictionary
	 */
	public Record predecessor(Pair k) {
		Node<Record> current = root;
		Node<Record> parent = new Node<Record>();
		Node<Record> prev;

		if (current.isLeaf()) {
			return null;
		}
		current = findNode(k);
		if (current.getElement() == null) {
			current = root;
			prev = current;
			while ((current.hasChildren()) && (current.getElement().getKey().compareTo(k) != 0)) {
				if (current.getElement().getKey().compareTo(k) < 0) {
					prev = current;
					current = current.getRightChild();
				} else {
					prev = current;
					current = current.getLeftChild();
				}
			}
			current = prev;
			if ((current.getElement().getKey().compareTo(k) < 0) && (current.getRightChild().getElement() == null)) {
				return current.getElement();
			}
		}
		if (!current.getLeftChild().isLeaf()) {
			return largest(current.getLeftChild());
		} else {
			parent = current.getParent();
			while ((parent.getParent() != null) && (current.isLeftChild())) {
				current = parent;
				parent = parent.getParent();
			}
			if ((parent == root) && (current.isLeftChild())) {
				return null;
			} else {
				return parent.getElement();
			}
		}
	}

	/**
	 * returns the record with smallest key in the ordered dictionary. Returns null if the dictionary is empty
	 */
	public Record smallest() {
		Node<Record> current = root;
		Node<Record> previous = current;

		if (root.getElement() == null) {
			return null;
		} else {
			while ((current.hasChildren()) && (current.getElement() != null)) {
				previous = current;
				current = current.getLeftChild();
			}
			return previous.getElement();
		}
	}

	/**
	 * returns the record with largest key in the ordered dictionary. Returns null if the dictionary is empty
	 */
	public Record largest() {
		Node<Record> current = root;
		Node<Record> previous = current;

		if (root.getElement() == null) {
			return null;
		} else {
			while ((current.hasChildren()) && (current.getElement() != null)) {
				previous = current;
				current = current.getRightChild();
			}
			return previous.getElement();
		}
	}

	/**
	 * 
	 * @param r
	 * @return smallest used as helper method
	 */
	private Record smallest(Node<Record> r) {
		Node<Record> current = r;
		Node<Record> previous = current;

		if (root.getElement() == null) {
			return null;
		} else {
			while ((current.hasChildren()) && (!current.isLeaf())) {
				previous = current;
				current = current.getLeftChild();
			}
			return previous.getElement();
		}
	}

	/**
	 * 
	 * @param r
	 * @return largest used as helper method
	 */
	private Record largest(Node<Record> r) {
		Node<Record> current = r;
		Node<Record> previous = current;

		if (root.getElement() == null) {
			return null;
		} else {
			while ((current.hasChildren()) && (current.getElement() != null)) {
				previous = current;
				current = current.getRightChild();
			}
			return previous.getElement();
		}
	}

	/**
	 * 
	 * @param r
	 * @return small helper method
	 */
	private Node<Record> small(Node<Record> r) {
		Node<Record> current = r;
		Node<Record> previous = current;

		if (root.getElement() == null) {
			return null;
		} else {
			while ((current.hasChildren()) && (!current.isLeaf())) {
				previous = current;
				current = current.getLeftChild();
			}
			return previous;
		}
	}

	/**
	 * 
	 * @param k
	 * @return find the node private node
	 */
	private Node<Record> findNode(Pair k) {
		Node<Record> current = root;

		while ((current.hasChildren()) && (current.getElement().getKey().compareTo(k) != 0)) {
			if (current.getElement().getKey().compareTo(k) < 0) {
				current = current.getRightChild();
			} else {
				current = current.getLeftChild();
			}
		}
		return current;
	}

	/**
	 * used to do preOrder 
	 */
	public void preOrder() {
		preOrder(root);
	}

	/**
	 * 
	 * @param node
	 * preOrder helper method 
	 */
	private void preOrder(Node<Record> node) {
		if (node == null) {
			return;
		}
	}
}
