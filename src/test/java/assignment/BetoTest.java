package assignment;

import org.junit.jupiter.api.RepeatedTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;


class BetoTest {

    @RepeatedTest(1000)
    void remove() {
        {
            {
                TreapMap<Integer, Integer> test = new TreapMap<>();
                int n = 100;
                HashSet<Integer> keys = new HashSet<>();
                for (int i = 0; i < n; i++) {
                    int key = (int) (Math.random() * 400);
                    keys.add(key);
                    test.insert(key, key + 1);
                }
                System.out.println(test);
                for (int key : keys) {
                    assertEquals(key + 1, test.lookup(key));
                }
                // System.out.println(test);
                System.out.println(keys.size());
                int i = 0;
                for (int key : keys) {
                    // System.out.println(key);
                    i++;
                    System.out.println(i);
                    System.out.println(key);
                    assertEquals(key + 1, test.remove(key));
                    assertNull(test.lookup(key));
                    assertNull(test.remove(key));
                    assertNull(test.lookup(key));
                    assertNull(test.remove(key));
                }
                for (int key : keys) {
                    test.insert(key, key - 1);
                    assertEquals(key - 1, test.lookup(key));
                    assertEquals(key - 1, test.remove(key));
                    assertNull(test.lookup(key));
                    assertNull(test.remove(key));
                    assertNull(test.lookup(key));
                    assertNull(test.remove(key));
                    test.insert(key, key);
                    assertEquals(key, test.lookup(key));
                    assertEquals(key, test.remove(key));
                    assertNull(test.lookup(key));
                    assertNull(test.remove(key));
                    assertNull(test.lookup(key));
                    assertNull(test.remove(key));
                }
            }
        }
    }




    @RepeatedTest(1000)
    void split() {
        int maxKey = 100;
        for (int splitKey = -1; splitKey <= maxKey + 1; splitKey++) {
            TreapMap<Integer, Integer> test = new TreapMap<>();
            int n = 100;
            for (int i = 0; i < n; i++) {
                int key = (int) (Math.random() * maxKey);
                test.insert(key, key);
            }
            // System.out.println(test);


            Treap<Integer, Integer>[] splits = test.split(splitKey);
            TreapMap<Integer, Integer> lessSplit = (TreapMap<Integer, Integer>) (splits[0]);
            TreapMap<Integer, Integer> greaterSplit = (TreapMap<Integer, Integer>) (splits[1]);

            /*
            System.out.println(splitKey);


            System.out.println(lessSplit);
            System.out.println("here");
            System.out.println(greaterSplit);
            System.out.println("done");

             */
            for (int key : lessSplit) {
                assertTrue(key < splitKey);
            }
            for (int key : greaterSplit) {
                assertTrue(key >= splitKey);
            }


            //System.out.println(test);
        }
    }

    @RepeatedTest(1000)
    void join() {
        {
            int maxKey = 20;
            for (int splitKey = 0; splitKey <= maxKey + 2; splitKey++) {
                TreapMap<Integer, Integer> lessSplit = new TreapMap<>();
                TreapMap<Integer, Integer> greaterSplit = new TreapMap<>();
                HashSet<Integer> allKeys =  new HashSet<>();
                int n = 20;
                for (int i = 0; i < Math.random() * n; i++) {
                    int lessKey = (int) (Math.random() * splitKey);
                    int greaterKey = (int) (Math.random() * (maxKey - splitKey)) + splitKey + 1;
                    lessSplit.insert(lessKey, lessKey);
                    greaterSplit.insert(greaterKey, greaterKey);
                    allKeys.add(lessKey);
                    allKeys.add(greaterKey);
                }
                System.out.println(lessSplit);
                System.out.println(greaterSplit);

                lessSplit.join(greaterSplit);
                // System.out.println(lessSplit);
                int lastKey = -1;
                for (int key : lessSplit) {
                    assertTrue(allKeys.contains(key));
                    System.out.println(key);
                    System.out.println(lastKey);
                    assertTrue(key > lastKey);
                    lastKey = key;
                }
            }

        }
    }
}