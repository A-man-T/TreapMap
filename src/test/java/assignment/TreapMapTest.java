package assignment;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class TreapMapTest {

/*
    @Test
    void parentLookup(){
        TreapMap<Integer,Integer> test = new TreapMap();
        for(int i=0;i<4;i++)
            test.insert(i,0);
        System.out.println(test);
        System.out.println((test.parentLookup(3)).key);
    }

 */

    @RepeatedTest(1)
    void testSplit() {
        TreapMap<Integer,Integer> testTreap = new TreapMap();
        for(int i=0;i<10;i++)
            testTreap.insert(i,i);
        System.out.println(testTreap);
        Treap<Integer, Integer>[] splits = testTreap.split(5);
        TreapMap<Integer, Integer> lessSplit = (TreapMap<Integer, Integer>) (splits[0]);
        TreapMap<Integer, Integer> greaterSplit = (TreapMap<Integer, Integer>) (splits[1]);
        System.out.println("here's the first one");
        System.out.println(lessSplit);
        System.out.println("here's the second one");
        System.out.println(greaterSplit);
    }


    @RepeatedTest(100)
    void testJoin() {
        TreapMap<Integer,Integer> testTreap = new TreapMap<>();
        for(int i=0;i<3;i++)
            testTreap.insert(i,i);
        TreapMap<Integer,Integer> test = new TreapMap<>();
        for(int i=3;i<6;i++)
            test.insert(i,i);
        testTreap.join(test);
        System.out.println(testTreap);
        for(int i=0;i<6;i++)
            assertTrue(testTreap.lookup(i)==i);
    }


    @RepeatedTest(100)
    void testTreap() {
        TreapMap<Integer,Integer> testTreap = new TreapMap();
        for(int i=0;i<1000;i++)
            testTreap.insert(i,0);
        System.out.println(testTreap);
        check(testTreap.root);
    }

    private void check(TreapMap.TreapNode curr) {
        System.out.println(curr.key);
       if(curr.left!=null){
           assertTrue(curr.key.compareTo(curr.left.key)>0);
           assertTrue(curr.priority>=curr.left.priority);
           check(curr.left);
       }
        if(curr.right!=null){
            assertTrue(curr.right.key.compareTo(curr.key)>0);
            assertTrue(curr.priority>=curr.right.priority);
            check(curr.right);
        }
    }


    @Test
    void testIterator() {
        TreapMap<Integer,Integer> test = new TreapMap();

        for(int i=0;i<10;i++)
            test.insert(i,0);

        Iterator<Integer> iter = test.iterator();
        System.out.println(test);
        test.remove(9);
        System.out.println(iter.next());
        while(iter.hasNext())
           System.out.println(iter.next());
        System.out.println(iter.hasNext());
        //System.out.println(iter.next());
    }


    @Test
    void insertWpriority() {
        TreapMap<Integer,Integer> test = new TreapMap();
        for(int i=0;i<10;i++)
            test.insert(i,0);

        test.insertWPriority(1,11,Treap.MAX_PRIORITY);
        check(test.root);
        System.out.println(test);
    }


/*
    @Test
    void testMeld() {
        TreapMap<Integer,Integer> test = new TreapMap();
        TreapMap<Integer,Integer> test1 = new TreapMap();
        test.meld(test1);
    }

 */

    @RepeatedTest(100)
    void testDelete() {
        TreapMap<Integer,Integer> test = new TreapMap();
        test.insert(1,100);
        test.insert(2,100);
        test.insert(3,14);
        test.insert(4,17);
        System.out.println(test);
        System.out.println("here");
        test.remove(4);
        test.remove(3);
        test.remove(2);
        test.remove(1);

        System.out.println(test);
        assertTrue(test.lookup(4)==null);
        assertTrue(test.lookup(3)==null);
        assertTrue(test.lookup(2)==null);
        assertTrue(test.lookup(1)==null);
    }

    @RepeatedTest(100)
    void demoTestMethod() {
        TreapMap<Integer,Integer> test = new TreapMap();
        test.insert(null,100);
        test.insert(2,100);
        test.insert(3,14);
        test.insert(4,17);
        //System.out.println(test.lookup(4));
        assertTrue(test.lookup(1)==null);
    }

    @RepeatedTest(100)
    void lookup() {
        TreapMap<Integer,Integer> test = new TreapMap();
        test.insert(1,69);
        test.insert(2,100000);
        test.insert(2,10);
        System.out.println(test);
        assertTrue(test.lookup(2)==10);
    }


    @RepeatedTest(100)
    void insertnull() {
        TreapMap<Integer,Integer> test = new TreapMap();
        test.insert(null,69);
        test.insert(3,null);
        test.insert(2,100000);
        System.out.println(test);
        //test.lookup(null);
        assertTrue((test.lookup(null))==null);
        assertTrue((test.lookup(3))==null);
        //assertTrue(true);
    }

    @RepeatedTest(100)
    void joinTest() {
        TreapMap<Integer,Integer> test = new TreapMap();
        TreapMap<Integer,Integer> test1 = new TreapMap();
        //test.join(test1);
        System.out.println(test.lookup(null));
        //assertTrue(true);
    }

    @Test
    void testToString() {
        TreapMap<Integer,Integer> test = new TreapMap();
        for(int i=0;i<10;i++)
            test.insert(i,0);

        System.out.println(test);

    }

}