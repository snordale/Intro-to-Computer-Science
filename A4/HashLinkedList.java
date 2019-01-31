package A4;

//Samuel Nordale
//260730745

public class HashLinkedList<K,V>{
	/*
	 * Fields
	 */
	private HashNode<K,V> head;

	private Integer size;

	/*
	 * Constructor
	 */

	HashLinkedList(){
		head = null;
		size = 0;
	}


	/*
	 *Add (Hash)node at the front of the linked list
	 */

	public void add(K key, V value){
		// ADD CODE BELOW HERE

		//adds new node and sets it as head of list
		HashNode<K,V> newNode = head;
		head = new HashNode<K,V>(key, value);
		head.next = newNode;

		this.size++;

		// ADD CODE ABOVE HERE
	}

	/*
	 * Get Hash(node) by key
	 * returns the node with key
	 */

	public HashNode<K,V> getListNode(K key){
		// ADD CODE BELOW HERE

		//makes sure the list isnt empty
		if (isEmpty()) {
			return null;
		}

		HashNode<K,V> node = head;

		//searches through list and returns when node key matches input key
		while (node != null) {

			if (key.equals(node.getKey())) {
				return node;
			}
			else {
				node = node.getNext();
			}
		}

		return null;


		// ADD CODE ABOVE HERE
	}


	/*
	 * Remove the head node of the list
	 * Note: Used by remove method and next method of hash table Iterator
	 */

	public HashNode<K,V> removeFirst(){
		// ADD CODE BELOW HERE

		//makes sure list isn't empty
		if (isEmpty()) {
			return null;
		}

		//moves head and sets previous head to be returned
		HashNode<K,V> node = head;
		head = head.getNext();

		size--;

		return node;

		// ADD CODE ABOVE HERE
	}

	/*
	 * Remove Node by key from linked list 
	 */

	public HashNode<K,V> remove(K key){
		// ADD CODE BELOW HERE

		//checks to make sure list isnt empty
		if (isEmpty()) {
			return null;
		}

		//handles edge-case
		if (key.equals(head.getKey())) {
			return removeFirst();
		}

		HashNode<K,V> node = head;
		HashNode<K,V> nextNode = node.getNext();

		//searches list and returns when node's key equals input key
		while (nextNode != null) {
			if (key.equals(nextNode.getKey())) {
				size--;
				node.next = nextNode.getNext();

				return nextNode;
			} 

			else {
				node = nextNode;
				nextNode = nextNode.getNext();
			}
		}

		return null;

		// ADD CODE ABOVE HERE

	}



	/*
	 * Delete the whole linked list
	 */
	public void clear(){
		head = null;
		size = 0;
	}
	/*
	 * Check if the list is empty
	 */

	boolean isEmpty(){
		return size == 0? true:false;
	}

	int size(){
		return this.size;
	}

	//ADD YOUR HELPER  METHODS BELOW THIS
	
	
	public HashNode<K,V> getHead() {
		return head;
	}
	
	
	//ADD YOUR HELPER METHODS ABOVE THIS


}
