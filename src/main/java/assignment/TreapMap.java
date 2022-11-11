package assignment;
import java.util.*;

//i know there's a lot of reused code, that will be addressed by the final deadline.

public class TreapMap<K extends Comparable<K>, V> implements Treap<K, V> {
    public TreapNode root;
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
    public class TreapNode
    {
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
        TreapNode(K key, V value, int priority)
        {
            this.priority = priority;
            this.key = key;
            this.value = value;
            this.left = this.right = null;
        }
    }

    //this method rotates the tree right around a pivot, and if the pivot is the root it changes the root to point to the correct code
    private TreapNode rotateRight(TreapNode pivot){
        TreapNode left = pivot.left;
        TreapNode swap = pivot.left.right;


        left.right = pivot;
        pivot.left = swap;


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

    //this method rotates the tree left around a pivot, and if the pivot is the root it changes the root to point to the correct code
    private TreapNode rotateLeft(TreapNode pivot){
        TreapNode right = pivot.right;
        TreapNode swap = pivot.right.left;


        right.left = pivot;
        pivot.right = swap;

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

    //recursive method to actually insert
    private TreapNode recursiveInsert(TreapNode curr,TreapNode insert){
        if(curr==null) {
            curr = insert;
            if(root==null)
                root = insert;
            return insert;
        }
        if(curr.key.compareTo(insert.key)>0){
            curr.left = recursiveInsert(curr.left, insert);
            if(curr.left!=null && curr.left.priority> curr.priority)
                curr = rotateRight(curr);
        }
        else{
            curr.right = recursiveInsert(curr.right, insert);
            if(curr.right!=null && curr.right.priority> curr.priority)
                curr = rotateLeft(curr);
        }
        return curr;
    }

    //traverses through and returns the nodes associated with the key
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
    //traverses through and returns the parent associated with the node
    public TreapNode parentLookup(K key) {
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
        numChanges++;
        return val;
    }

    //splits as described
    @Override
    public Treap[] split(K key) {



        TreapMap<K,V>[] splits= new TreapMap[2];
        if(key==null)
            return splits;
        if(root==null)
            return splits;

        V value;
        if(smartLookup(key)!=null){
            value = lookup(key);
            remove(key);
            insertWPriority(key, value, MAX_PRIORITY);
            splits[0] = new TreapMap<>(root.left);
            root.left = null;
            splits[1] = new TreapMap<>(root);
        }
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
    public void insertWPriority(K key, V value, int priority) {
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

    //join as described
    @Override
    public void join(Treap<K,V> t) {

        if(!(t instanceof TreapMap<K,V>))
            return;
        if(t==null)
            return;
        if(root==null)
            if(t instanceof TreapMap<K,V>) {
                root = ((TreapMap<K, V>) t).root;
                return;
            }

        TreapNode highestNode = root;
        while(highestNode.right!=null)
            highestNode = highestNode.right;

        K maxKey = highestNode.key;
        V maxKeyValue = highestNode.value; ;
        int maxKeyPriority = highestNode.priority;
        TreapNode temp = new TreapNode(maxKey, maxKeyValue, MAX_PRIORITY);
        remove(maxKey);

        temp.left = root;
        if(t instanceof TreapMap<K,V>) {
            temp.right = ((TreapMap<K, V>) t).root;
            t = null;
        }
        root = temp;

        removeNode(temp);

        insertWPriority(maxKey,maxKeyValue,maxKeyPriority);
        numChanges++;
    }


    private void removeNode(TreapNode node){

        if(node.left==null&&node.right==null){
            if(root.left==null && root.right==null) {
                root = null;
                return;
            }
            if(parentLookup(node.key).left==node){
                parentLookup(node.key).left=null;
                return;
            }
            if(parentLookup(node.key).right==node){
                parentLookup(node.key).right=null;
                return;
            }
        }

        else if(node.left != null && node.right != null){
            if (node.left.priority < node.right.priority)
               rotateLeft(node);
            else {
                rotateRight(node);
            }
            removeNode(node);
            return;
        }
        else{
            //error
            if(parentLookup(node.key)==null){
                if(node.right!=null){
                    root = node.right;
                }
                if(node.left!=null){
                    root = node.left;
                }
            }
            else if(parentLookup(node.key).right==node){
                if(node.right!=null){
                    parentLookup(node.key).right = node.right;
                    return;
                }
                if(node.left!=null){
                    parentLookup(node.key).right = node.left;
                    return;
                }
            }
            else if(parentLookup(node.key).left==node){
                if(node.right!=null){
                    parentLookup(node.key).left = node.right;
                    return;
                }
                if(node.left!=null){
                    parentLookup(node.key).left = node.left;
                    return;
                }
            }
        }



    }



    //removes by a node rather than a key
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
        //consider numchanges and throw concurrent modification exception
        // do i need to implement remove or the other one?
        return new TreapMapIterator<K>(root,numChanges);
    }
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
