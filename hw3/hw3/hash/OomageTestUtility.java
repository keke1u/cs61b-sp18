package hw3.hash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /*
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        Map<Integer, Integer> bucketNumMap = new HashMap<>();
        int N = oomages.size();
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            if (bucketNumMap.containsKey(bucketNum)) {
                int bucketNumTimes = bucketNumMap.get(bucketNum);
                bucketNumMap.put(bucketNum, bucketNumTimes + 1); // the value of existing key will be replaced
            } else {
                bucketNumMap.put(bucketNum, 1);
            }
        }

        for (Integer bucket : bucketNumMap.keySet()) { // .keySet() to be iterable
            int num = bucketNumMap.get(bucket);
            if (num < N / 50 || num > N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
