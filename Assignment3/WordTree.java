package A3;

//COMP 250 - Introduction to Computer Science - Fall 2017
//Samuel Nordale
//260730745
//Assignment #3 - Question 1

import java.util.*;

/*
 *  WordTree class.  Each node is associated with a prefix of some word 
 *  stored in the tree.   (Any string is a prefix of itself.)
 */


public class WordTree
{
	WordTreeNode root;

	// Empty tree has just a root node.  All the children are null.

	public WordTree() 
	{
		root = new WordTreeNode();
	}


	/*
	 * Insert word into the tree.  First, find the longest 
	 * prefix of word that is already in the tree (use getPrefixNode() below). 
	 * Then, add TreeNode(s) such that the word is inserted 
	 * according to the specification in PDF.
	 */
	public void insert(String word)
	{
		//  ADD YOUR CODE BELOW HERE
		WordTreeNode mid = getPrefixNode(word);
		//this loop adds missing nodes
		for (int measure = mid.depth; measure < word.length(); measure++) {
			mid.createChild(word.charAt(measure));
			mid = mid.children[(int) word.charAt(measure)];
		}

		mid.setEndOfWord(true);


		//		for (int i = 0; i < mid.chil)
		//  ADD YOUR ABOVE HERE
	}

	// insert each word in a given list 

	public void loadWords(ArrayList<String> words)
	{
		for (int i = 0; i < words.size(); i++)
		{
			insert(words.get(i));
		}
		return;
	}

	/*
	 * Given an input word, return the TreeNode corresponding the longest prefix that is found. 
	 * If no prefix is found, return the root. 
	 * In the example in the PDF, running getPrefixNode("any") should return the
	 * dashed node under "n", since "an" is the longest prefix of "any" in the tree. 
	 */
	WordTreeNode getPrefixNode(String word)
	{
		//   ADD YOUR CODE BELOW HERE
		WordTreeNode sol = root;
		int i = 0;
		//this loop returns the longest prefix of the word

		//this for loop allows for comparing between the tree and word
		for (i = 0; i < word.length(); i++) {
			//this handles situations when the current node is the end of the word
			if (sol.getChild(word.charAt(i)) != null && sol.getChild(word.charAt(i)).isEndOfWord() && i == word.length() - 1) {
				sol = sol.getChild(word.charAt(i));
				return sol;
			}
			//this handles situations when the current node isn't the end of the word
			else if (sol.getChild(word.charAt(i)) != null) {
				sol = sol.getChild(word.charAt(i));
			}	
			//this handles the other situations
			else {

				return sol;
			}

		}
		return sol; 

		//   ADD YOUR CODE ABOVE HERE

	}

	/*
	 * Similar to getPrefixNode() but now return the prefix as a String, rather than as a TreeNode.
	 */

	public String getPrefix(String word)
	{
		return getPrefixNode(word).toString();
	}


	/*
	 *  Return true if word is contained in the tree (i.e. it was added by insert), false otherwise.
	 *  Hint:  any string is a prefix of itself, so you can use getPrefixNode().
	 */
	public boolean contains(String word)
	{  
		//   ADD YOUR CODE BELOW HERE
		boolean sol = false;
		if (getPrefixNode(word).isEndOfWord()) {
			sol = true;
		}

		return sol;

		//   ADD YOUR CODE ABOVE HERE
	}

	/*
	 *  Return a list of all words in the tree that have the given prefix. 
	 *  For example,  getListPrefixMatches("") should return all words in the tree.
	 */
	public ArrayList<String> getListPrefixMatches( String prefix )
	{
		//  ADD YOUR CODE BELOW 
		ArrayList<String> sol = new ArrayList<String>();
		WordTreeNode base = new WordTreeNode();
		base = getPrefixNode(prefix);
		System.out.println(base);

		//this handles situations when if the input string is nothing
		if (prefix.length() == 0) {
			helperMethod(sol, base);
		}
		//this handles situations when the input string has no matches
		else if (root.getChild(prefix.charAt(0)) == null) {
			return sol;
		}
		//this handles the other situations
		else {
			helperMethod(sol, base);
		}

		return sol;

		//  ADD YOUR CODE ABOVE HERE
	}


	/*
	 *  Below is the inner class defining a node in a Tree (prefix) tree.  
	 *  A node contains an array of children: one for each possible character.  
	 *  The children themselves are nodes.
	 *  The i-th slot in the array contains a reference to a child node which corresponds 
	 *  to character  (char) i, namely the character with  ASCII (and UNICODE) code value i. 
	 *  Similarly the index of character c is obtained by "casting":   (int) c.
	 *  So children[97] = children[ (int) 'a']  would reference a child node corresponding to 'a' 
	 *  since (char)97 == 'a'   and  (int)'a' == 97.
	 * 
	 *  To learn more:
	 * -For all unicode charactors, see http://unicode.org/charts
	 *  in particular, the ascii characters are listed at http://unicode.org/charts/PDF/U0000.pdf
	 * -For ascii table, see http://www.asciitable.com/
	 * -For basic idea of converting (casting) from one type to another, see 
	 *  any intro to Java book (index "primitive type conversions"), or google
	 *  that phrase.   We will cover casting of reference types when get to the
	 *  Object Oriented Design part of this course.
	 */

	public class WordTreeNode
	{
		/*  
		 *   Highest allowable character index is NUMCHILDREN-1
		 *   (assuming one-byte ASCII i.e. "extended ASCII")
		 *   
		 *   NUMCHILDREN is constant (static and final)
		 *   To access it, write "TreeNode.NUMCHILDREN"
		 *   
		 *   For simplicity,  we have given each WordTree node 256 children. 
		 *   Note that if our words only consisted of characters from {a,...,z,A,...,Z} then
		 *   we would only need 52 children.   The WordTree can represent more general words
		 *   e.g.  it could also represent many special characters often used in passwords.
		 */

		public static final int NUMCHILDREN = 256;

		WordTreeNode     parent;
		WordTreeNode[]   children;
		int              depth;            // 0 for root, 1 for root's children, 2 for their children, etc..

		char             charInParent;    // Character associated with the tree edge from this node's parent 
		// to this node.
		// See comment above for relationship between an index in 0 to 255 and a char value.

		boolean endOfWord;   // Set to true if prefix associated with this node is also a word.


		// Constructor for new, empty node with NUMCHILDREN children.  
		//  All the children are automatically initialized to null. 

		public WordTreeNode()
		{
			children = new WordTreeNode[NUMCHILDREN];

			//   These assignments below are unnecessary since they are just the default values.

			endOfWord = false;
			depth = 0; 
			charInParent = (char)0; 
		}


		/*
		 *  Add a child to current node.  The child is associated with the character specified by
		 *  the method parameter.  Make sure you set as many fields in the child node as you can.
		 *  
		 *  To implement this method, see the comment above the inner class TreeNode declaration.  
		 *  
		 */

		public WordTreeNode createChild(char  c) 
		{	   
			WordTreeNode child       = new WordTreeNode();

			// ADD YOUR CODE BELOW HERE
			child.parent = this;			
			child.depth = this.depth + 1;
			child.charInParent = c;
			this.children[c] = child;						

			// ADD YOUR CODE ABOVE HERE

			return child;
		}

		// Get the child node associated with a given character, i.e. that character is "on" 
		// the edge from this node to the child.  The child could be null.  

		public WordTreeNode getChild(char c) 
		{
			return children[ c ];
		}

		// Test whether the path from the root to this node is a word in the tree.  
		// Return true if it is, false if it is prefix but not a word.

		public boolean isEndOfWord() 
		{
			return endOfWord;
		}

		// Set to true for the node associated with the last character of an input word

		public void setEndOfWord(boolean endOfWord)
		{
			this.endOfWord = endOfWord;
		}

		/*  
		 *  Return the prefix (as a String) associated with this node.  This prefix
		 *  is defined by descending from the root to this node.  However, you will
		 *  find it is easier to implement by ascending from the node to the root,
		 *  composing the prefix string from its last character to its first.  
		 *
		 *  This overrides the default toString() method.
		 */

		public String toString()
		{
			// ADD YOUR CODE BELOW HERE
			String word = "";
			WordTreeNode temp = this;

			while (temp.depth > 0) {
				word = temp.charInParent + word;
				temp = temp.parent;
			}

			return word;

			// ADD YOUR CODE ABOVE HERE
		}
	}
	public void helperMethod(ArrayList<String> list, WordTreeNode node) {
		//this loop searches for the children of the node
		for (int i = 0; i < 256; i++) {
			//this handles situations when there is a child of the node and calls the function recursively for that child
			if (node.children[i] != null) {
				helperMethod(list, node.children[i]);
			}	
		}
		//this handles situations when the nod is the end of the word and it needs to be stringified and added to list
		if (node.isEndOfWord()) {
			list.add(node.toString());
		}
	}
}
