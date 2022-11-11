package assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class MethodsTreapMapTest {

    @RepeatedTest(1000)
    void testGeneric() {
        TreapMap<String, String> test = new TreapMap();
        test.insert("1", "1");
        test.insert("apple", "1");
        test.insert("LMAO", "xd");
        test.insert("dreaming","popsmoke");
        System.out.println(test);
        test.remove("LMAO");
        assertTrue(test.lookup("LMAO") ==null);
        assertTrue(test.lookup("dreaming") =="popsmoke");
    }



    @RepeatedTest(1000)
    void removeNode() {
        TreapMap<Integer,Integer> test = new TreapMap();
        test.insert(1,1);
        test.insert(2,1);
        test.insert(3,1);
        test.insert(4,1);
        System.out.println(test);
        Iterator<Integer> iter = test.iterator();
        String print = test.toString();
        int root = Integer.parseInt(print.substring(print.indexOf('<')+1,print.indexOf(',')));
        System.out.println(root);
        test.remove(root);
        System.out.println(test);
        assertNull(test.lookup(root));
        //assertTrue()
    }



    @RepeatedTest(1000)
    void testRemove() {
        TreapMap<Integer,Integer> test = new TreapMap();
        test.insert(1,100);
        test.insert(2,100);
        test.insert(3,14);
        test.insert(4,17);
        System.out.println(test);

        test.remove(4);
        test.remove(3);
        test.remove(2);
        test.remove(1);
        System.out.println(test);
        System.out.println("here");
        assertTrue(test.lookup(4)==null);
        assertTrue(test.lookup(3)==null);
        assertTrue(test.lookup(2)==null);
        assertTrue(test.lookup(1)==null);
    }


    @RepeatedTest(1000)
    void testLookup() {
        TreapMap<Integer,Integer> test = new TreapMap();
        assertTrue(test.lookup(4)==null);
        assertTrue(test.lookup(3)==null);
        assertTrue(test.lookup(2)==null);
        assertTrue(test.lookup(1)==null);
        test.insert(1,100);
        test.insert(2,100);
        test.insert(3,14);
        test.insert(4,17);
        System.out.println(test);
        System.out.println("there");
        assertTrue(test.lookup(1)==100);
        assertTrue(test.lookup(2)==100);
        assertTrue(test.lookup(3)==14);
        assertTrue(test.lookup(4)==17);
        test.insert(1,15);
        System.out.println(test);
        System.out.println("here");
        assertTrue(test.lookup(1)==15);
    }

    @RepeatedTest(1000)
    void testIterator() {
        TreapMap<Integer,Integer> test = new TreapMap();
        Iterator<Integer> iter = test.iterator();
        test.insert(1,100);
        test.insert(2,100);
        test.insert(3,14);
        test.insert(4,17);
        assertThrows(ConcurrentModificationException.class, iter::next);
        iter = test.iterator();
        while(iter.hasNext())
            System.out.println(iter.next());
        assertThrows(NoSuchElementException.class, iter::next);
    }

    @Test
    void testMeld() {
        TreapMap<Integer,Integer> test = new TreapMap();
        TreapMap<Integer,Integer> test1 = new TreapMap();
        test.insert(1,100);
        test.insert(2,100);
        test.insert(3,14);
        test.insert(4,17);
        test1.insert(1,15);
        //test.difference(test1);
        System.out.println(test);
        //test.meld(test1);
        //assertThrows(UnsupportedOperationException.class, test.meld(test1));

    }

}