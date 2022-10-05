public class LinearProbingHashTable<K,V> {

    //    constructor
    private int size, entries;
    Entry<K, V> table[];
    private static final int DEFAULT_TABLE_SIZE = 11;
    public LinearProbingHashTable(int size) {
        this.size = size;
        table = new Entry[size];

    }

    public LinearProbingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    //It should contain a private static class, Entry<K,V>, which contains both K and V.
    private static class Entry<K, V> {
        K key;
        V value;
        public boolean isActive;

        public Entry(K k, V v) {
            key = k;
            value = v;
            isActive = true;
        }
    }

    private boolean isActive(int currPos) {
        return table[currPos] != null && table[currPos].isActive;
    }
    public boolean insert(K key, V value) {
//        inserts entry, rehashes if half full
        if (key == null) {
            System.err.println("KEY IS NULL");
        }

        int currPos = getLocation(key);
        if (isActive(currPos)) {
            return false;
        }
        ++entries;
        table[currPos] = new Entry<>(key, value);

        if (entries > size / 2)
            rehash();
        return true;

    }

    //    private boolean isActive(int currPos) {
//        return
//    }
    public V find(K key) {
//        returns value for key or null if not found
        for (int i = 0; i < size; i++) {
            if (table[i] != null && table[i].isActive) {
                return table[i].value;
            }
//            return -1;
        }
        return null;
    }

    public boolean delete(K key) {
//        marks entry deleted & leave it
        int currPos = getLocation(key);
//        returns true if deleted
        if (table[currPos].isActive) {
            table[currPos].isActive = false;
            size--;
            return true;

        } else {
            //        else not found
            return false;
        }
    }

    private void rehash() {
//        doubles the table,
        size *= 2;
//        hashes everything to new table
        Entry<K, V> newTable[] = table;
        table = new Entry[size];
        for (Entry<K, V> entry : newTable) {
            if (entry != null && entry.isActive) {
                insert(entry.key, entry.value);
            }
        }
    }


    public int getHashValue(K key) {
//        returns the hash val for given key
        return myhash(key);
    }

    public int getLocation(K key) {
//        returns location of given key or -1 if not found
        int offset = 1;
//        int hashValue = getHashValue(key);
        int currPos = myhash(key);

        while(table[currPos] != null && !table[currPos].key.equals(key)) {
            currPos += offset;
            if(currPos >= table.length)
                currPos -= table.length;
        }

        return currPos;

    }
    private int myhash(K key) {
        int hashVal = key.hashCode();

        hashVal %= table.length;
        if(hashVal < 0)
            hashVal += table.length;
        return hashVal;
    }

    public String toString() {

        String results = "";
        String check = "";
        for (int i = 0; i < size; i++)
        {
            Entry<K,V> tablestring = table[i];
            if(tablestring !=null)
            {
                if(tablestring.isActive)
                    check = " ";
                if(tablestring.isActive== false)
                    check = " deleted";
                results += i + " " + tablestring.key + ", " +  tablestring.value + " " + check + "\n";
            }
            else
                results += i + "\n";
        }
        return results;
    }

    public static void main(String args[]) throws Exception {
        LinearProbingHashTable lpht = new LinearProbingHashTable<>();

        System.out.println("Hash Table Before\n" + lpht.toString());

        System.out.println("Inserting : " + lpht.insert(0,5));
        System.out.println("Inserting : " + lpht.insert(1,2));
        System.out.println("Inserting Duplicate : " + lpht.insert(1,2));

        System.out.println("Inserting : " + lpht.insert(2,7));

        System.out.println("Inserting : " + lpht.insert(20,10));

        System.out.println("Hash Table After Insertion\n" + lpht.toString());

        System.out.println("Find val of 0 : " + lpht.find(0));

        System.out.println("Find val of 25 : " + lpht.find(20));
        System.out.println("FINDING location of 0 : " + lpht.find(0));
        System.out.println("Deleting val of 25 : " + lpht.delete(20));

        System.out.println("Get location of 0 : " + lpht.getLocation(1));
        System.out.println("Get location of 0 : " + lpht.getLocation(20));
        System.out.println("Get Hash Val of 25 : " +  lpht.getHashValue( 20) );
        System.out.println("FINDING location of 25 : " + lpht.find(20));
        System.out.println("Deleting ");
        System.out.println("Hash Table After\n" + lpht.toString());
        System.out.println("Get Hash Val of 0 : " +  lpht.getHashValue( 0) );

        System.out.println("Get Hash Val of 25 : " +  lpht.getHashValue( 25) );

        System.out.println("Inserting value : " + lpht.insert(10,9));

        System.out.println("Inserting balue : " + lpht.insert(10,3));

        System.out.println("Inserting value : " + lpht.insert(7,6));
        System.out.println("Inserting value : " + lpht.insert(7,2));
        System.out.println("Inserting value : " + lpht.insert(1,1));
        System.out.println("Hash Table After\n" + lpht.toString());
    }
}
