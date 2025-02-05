package ex5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ChainingHashTableTest {

    static class CustomKey {
        String key;

        public CustomKey(String key) {
            this.key = key;
        }

        @Override
        public int hashCode() {
            return key.length();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            CustomKey that = (CustomKey) obj;
            return this.key.equals(that.key);
        }

        @Override
        public String toString() {
            return key;
        }
    }

    @org.junit.Test
    @Test
    public void testCustomHashCode() {
        ChainingHashTable<CustomKey, Integer> table = new ChainingHashTable<>();

        table.insert(new CustomKey("hi"), 10);
        table.insert(new CustomKey("hello"), 20);
        table.insert(new CustomKey("world"), 30);


        assertTrue(table.contains(new CustomKey("hi")));
        assertTrue(table.contains(new CustomKey("hello")));
        assertTrue(table.contains(new CustomKey("world")));
    }
}
