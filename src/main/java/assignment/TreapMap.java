package assignment;
import java.util.*;


public class TreapMap<K extends Comparable<K>, V> implements Treap<K, V> {

    //keeps track of the root which contains pointers to the rest of the tree
    private TreapNode root;
    //numChanges is to be used in the iterator
    private int numChanges;

    TreapMap(){
        numChanges = 0;
    }
    TreapMap(TreapNode root){
        this.root = root;
        numChanges = 0;
    }

    //node data structure where the nodes of the tree are created
    private class TreapNode{
        K key;
        V value;
        int priority;
        TreapNode left, right;

        TreapNode(K key, V value)
        {
            this.priority = (int) (Math.random()*MAX_PRIORITY);
            this.key = key;
            this.value = value;
            this.left = this.right = null;
        }
        //used for inserting a TreapNode with a set priority
        TreapNode(K key, V value, int priority)
        {
            this.priority = priority;
            this.key = key;
            this.value = value;
            this.left = this.right = null;
        }
    }

    //this method rotates the tree right around a pivot,
    // and if the pivot is the root it changes the root to point to the correct code
    private TreapNode rotateRight(TreapNode pivot){
        TreapNode left = pivot.left;
        TreapNode swap = pivot.left.right;

        left.right = pivot;
        pivot.left = swap;

        //This block of code changes the parent to point at the new head of the rotation
        TreapNode parent = parentLookup(pivot.key);

        if(parent == null)
            this.root = left;
        else {
            if (parent.right == pivot)
                parent.right = left;
            else {
                parent.left = left;
            }
        }
        numChanges++;
        return left;
    }

    //this method rotates the tree left around a pivot,
    // and if the pivot is the root it changes the root to point to the correct code
    private TreapNode rotateLeft(TreapNode pivot){
        TreapNode right = pivot.right;
        TreapNode swap = pivot.right.left;


        right.left = pivot;
        pivot.right = swap;

        //This block of code changes the parent to point at the new head of the rotation
        TreapNode parent = parentLookup(pivot.key);

        if(parent==null)
            this.root = right;
        else{
            if(parent.right==pivot)
                parent.right=right;
            else {
                parent.left = right;
            }
        }

        numChanges++;
        return right;
    }



    //traverses through and returns the value associated with the key
    @Override
    public V lookup(K key) {
        //smartLookup returns the node associated with a key
        TreapNode curr = smartLookup(key);
        if(curr==null)
            return null;
        return curr.value;

    }


    //inserts the element in the tree or changes the value if it already is there
    @Override
    public void insert(K key, V value) {
        if(key==null)
            return;
        if(value==null)
            return;
        if(smartLookup(key)!=null){
            smartLookup(key).value = value;
            return;
        }

        TreapNode insert = new TreapNode(key, value);
        recursiveInsert(root,insert);
        numChanges++;
    }

    //recursive method to do the Insertion, very similar to a BST approach
    private TreapNode recursiveInsert(TreapNode curr,TreapNode insert){

        //Case 1: We have found the spot the node should be inserted
        if(curr==null) {
            curr = insert;
            if(root==null)
                root = insert;
            return insert;
        }
        //Case 2: We are at a node with a key too large so we move left,
        //once the node is inserted we rotate it through the recursive stack to maintain heap properties
        if(curr.key.compareTo(insert.key)>0){
            curr.left = recursiveInsert(curr.left, insert);
            if(curr.left!=null && curr.left.priority> curr.priority)
                curr = rotateRight(curr);
        }
        //Case 3: We are at a node with a key too small so we move right
        //once the node is inserted we rotate it through the recursive stack to maintain heap properties
        else{
            curr.right = recursiveInsert(curr.right, insert);
            if(curr.right!=null && curr.right.priority> curr.priority)
                curr = rotateLeft(curr);
        }
        return curr;
    }

    //traverses through the Treap and returns the node associated with the key
    private TreapNode smartLookup(K key) {
        if(root==null)
            return null;
        if(key==null)
            return null;
        TreapNode curr = root;
        while(curr.key.compareTo(key)!=0){
            if(curr.key.compareTo(key)>0) {
                if (curr.left == null)
                    return null;
                else{
                    curr = curr.left;
                }
            }
            else {
                if (curr.right == null)
                    return null;
                else{
                    curr = curr.right;
                }
            }
        }
        return curr;

    }

    //traverses through and returns the parent associated with the key
    private TreapNode parentLookup(K key) {
        if(root==null)
            return null;
        TreapNode curr = root;
        TreapNode prev = null;
        while(curr.key.compareTo(key)!=0){
            if(curr.key.compareTo(key)>0) {
                if (curr.left == null)
                    return null;
                else{
                    prev = curr;
                    curr = curr.left;
                }
            }
            else {
                if (curr.right == null)
                    return null;
                else{
                    prev = curr;
                    curr = curr.right;
                }
            }
        }

        return prev;

    }

    //removes the node from the tree
    @Override
    public V remove(K key) {

        if(key==null)
            return null;
        TreapNode toDelete = smartLookup(key);
        if(toDelete==null)
            return null;
        V val = toDelete.value;

        removeNode(toDelete);
        numChanges++;
        return val;
    }

    //splits the treap into two treaps such that the first treap only contains keys
    // less than the parameter KEY and the other contains the rest
    @Override
    public Treap[] split(K key) {
        //input validation
        TreapMap<K,V>[] splits= new TreapMap[2];
        if(key==null)
            return splits;
        if(root==null)
            return splits;

        V value;
        //if the key is in the tree, move it to the top and take the left and right subtrees
        if(smartLookup(key)!=null){
            value = lookup(key);
            remove(key);
            insertWPriority(key, value, MAX_PRIORITY);
            splits[0] = new TreapMap<>(root.left);
            root.left = null;
            splits[1] = new TreapMap<>(root);
        }
        //if the key isn't in the tree, put it at the top then take the left and right subtrees
        else {
            insertWPriority(key, root.value, MAX_PRIORITY);
            splits[0] = new TreapMap<>(root.left);
            splits[1] = new TreapMap<>(root.right);
        }

        if(splits[0]==null)
            splits[0] = new TreapMap<>();
        if(splits[1]==null)
            splits[1] = new TreapMap<>();
        numChanges++;
        root = null;
        return splits;
    }

    //inserts a node with a given priority
    private void insertWPriority(K key, V value, int priority) {
        if(key==null)
            return;
        if(value==null)
            return;
        if(smartLookup(key)!=null){
            smartLookup(key).value = value;
            return;
        }
        //add the edge case where the key is already in the treap and just replace the value here and return
        TreapNode insert = new TreapNode(key, value, priority);
        recursiveInsert(root,insert);
        numChanges++;
    }

    //join as described: Two treaps, T1 and T2, with all keys in T1 being smaller
    //than all keys in T2, are merged to form a new treap T
    @Override
    public void join(Treap<K,V> t) {
        if(!(t instanceof TreapMap))
            return;
        if(t==null)
            return;
        if(root==null)
            if(t instanceof TreapMap) {
                root = ((TreapMap<K, V>) t).root;
                return;
            }

        //find the node with the largest key in this treap, store its values and delete it
        TreapNode highestNode = root;
        while(highestNode.right!=null)
            highestNode = highestNode.right;
        K maxKey = highestNode.key;
        V maxKeyValue = highestNode.value;
        remove(maxKey);
        //make a new TreapNode with max priority and set its right and left pointers to the other two treaps
        int maxKeyPriority = highestNode.priority;
        TreapNode temp = new TreapNode(maxKey, maxKeyValue, MAX_PRIORITY);
        temp.left = root;
        if(t instanceof TreapMap) {
            temp.right = ((TreapMap) t).root;
            t = null;
        }
        root = temp;

        //remove the temporary node and reinsert the original one
        removeNode(temp);
        insertWPriority(maxKey,maxKeyValue,maxKeyPriority);
        numChanges++;
    }

//recursively removes a node from the treap
    private void removeNode(TreapNode node){

        TreapNode parent = parentLookup(node.key);
        //Case 1: the node is a leaf, if so the pointer of the parent is changed
        if(node.left==null&&node.right==null){
            //checks if it needs to change the root
            if(root.left==null && root.right==null) {
                root = null;
                return;
            }
            if(parent.left==node){
                parent.left=null;
                return;
            }
            if(parent.right==node){
                parent.right=null;
                return;
            }
        }
        //case 2: the node has two children and needs to be rotated down
        else if(node.left != null && node.right != null){
            if (node.left.priority < node.right.priority)
               rotateLeft(node);
            else {
                rotateRight(node);
            }
            removeNode(node);
            return;
        }
        //case 3: the node has 1 child and the pointer of the parent can simply be changed
        else{
            if(parent==null){
                if(node.right!=null){
                    root = node.right;
                }
                if(node.left!=null){
                    root = node.left;
                }
            }
            else if(parent.right==node){
                if(node.right!=null){
                    parent.right = node.right;
                    return;
                }
                if(node.left!=null){
                    parent.right = node.left;
                    return;
                }
            }
            else if(parent.left==node){
                if(node.right!=null){
                    parent.left = node.right;
                    return;
                }
                if(node.left!=null){
                    parent.left = node.left;
                    return;
                }
            }
        }
    }

    //pre-order output of the treap
    public String toString(){
        StringBuilder output = new StringBuilder();
        int depth = 0;
        preOrder(root, output, depth);



        return output.toString();
    }

    private void preOrder(TreapNode node, StringBuilder output, int depth) {
        if(node==null)
            return;
        for(int i = 0; i<depth;i++)
            output.append("\t");
        output.append("["+node.priority+"]"+" <"+node.key+", "+node.value+">");
        output.append("\n");
        depth++;
        preOrder(node.left,output,depth);
        preOrder(node.right,output,depth);
    }

    @Override
    public Iterator<K> iterator() {
        return new TreapMapIterator<K>(root,numChanges);
    }

    //Iterator class, initialized with the number of changes variable
    //Put the nodes into a queue and prepopulates it through an in-order traversal
    class TreapMapIterator<K> implements Iterator<K> {
        TreapNode root;
        int changesBeforeCreation;
        Queue<TreapNode> queue;
        public TreapMapIterator(TreapNode root, int numChanges) {
            this.root = root;
            changesBeforeCreation = numChanges;
            queue = new LinkedList<TreapNode>();
            inOrder(root,queue);
        }

        private void inOrder(TreapNode node, Queue<TreapNode> queue) {
            if(node == null)
                return;
            inOrder(node.left,queue);
            queue.add(node);
            inOrder(node.right,queue);
        }

        //Number of changes is used here to check if exceptions should be thrown
        @Override
        public boolean hasNext() throws ConcurrentModificationException {
            if(numChanges!=changesBeforeCreation)
                throw new ConcurrentModificationException();

            return (queue.peek()!=null);
        }

        @Override
        public K next() throws ConcurrentModificationException {
            if(numChanges!=changesBeforeCreation)
                throw new ConcurrentModificationException();
            if(hasNext())
                return (K) queue.remove().key;
            throw new NoSuchElementException();
        }
    }



    @Override
    public void meld(Treap t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void difference(Treap t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }



    @Override
    public double balanceFactor() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}

//old iterative implementation
/*
    private void removeNode(TreapNode node) {

        TreapNode toDelete = node;
        while(toDelete.left!=null&&toDelete.right!=null){
            if(toDelete.left.priority<toDelete.right.priority)
                rotateLeft(toDelete);
            else{
                rotateRight(toDelete);
            }
        }
        if(toDelete.left==null&&toDelete.right!=null)
            rotateLeft(toDelete);
        if(toDelete.right==null&&toDelete.left!=null)
            rotateRight(toDelete);
        //V val = toDelete.value;
        //K key = toDelete.key;

        if(root.left==null&&root.right==null)
            root = null;
        //else if(toDelete.left==null&&toDelete.right!=null)
            //removeNode(node);
        else if(parentLookup(node.key).left!=null&&parentLookup(node.key).left.key.compareTo(node.key)==0)
            parentLookup(node.key).left = null;
        else if(parentLookup(node.key).right!=null&&parentLookup(node.key).right.key.compareTo(node.key)==0)
            parentLookup(node.key).right = null;
        return;
    }


 */


//old remove
/*
        while(toDelete.left!=null&&toDelete.right!=null){
            if(toDelete.left.priority<toDelete.right.priority)
                rotateLeft(toDelete);
            else{
                rotateRight(toDelete);
            }
        }
        if(toDelete.left==null&&toDelete.right!=null)
            rotateLeft(toDelete);
        if(toDelete.right==null&&toDelete.left!=null)
            rotateRight(toDelete);


        if(root.left==null&&root.right==null)
            root = null;
        else if(parentLookup(key).left!=null&&parentLookup(key).left.key.compareTo(key)==0)
            parentLookup(key).left = null;
        else if(parentLookup(key).right!=null&&parentLookup(key).right.key.compareTo(key)==0)
            parentLookup(key).right = null;

         */

//old lookup

        /*
        if(key == null)
            return null;
        if(root==null)
            return null;
        TreapNode curr = root;
        while(curr.key.compareTo(key)!=0){
            if(curr.key.compareTo(key)>0) {
                if (curr.left == null)
                    return null;
                else{
                    curr = curr.left;
                }
            }
           else {
                if (curr.right == null)
                    return null;
                else{
                    curr = curr.right;
                }
            }
        }

         */