package ex4;

import java.util.List;

public class RangeTree {
    private OrderedDeletelessDictionary<Double, Range> byStart;
    private OrderedDeletelessDictionary<Double, Range> byEnd;
    private int size;

    public RangeTree() {
        byStart = new AVLTree<>();
        byEnd = new AVLTree<>();
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Return the Range which starts at the given time
    // The running time is O(log n)
    public Range findByStart(Double start) {
        return byStart.find(start);
    }

    // Return the Range which ends at the given time
    // The running time is O(log n)
    public Range findByEnd(Double end) {
        return byEnd.find(end);
    }

    // Gives a list of Ranges sorted by start time.
    // Useful for testing and debugging.
    // The running time is O(n), so it should not
    // be used for implementing other methods.
    public List<Range> getRanges() {
        return byStart.getValues();
    }

    // Gives a sorted list of start times.
    // Useful for testing and debugging.
    // The running time is O(n), so it should not
    // be used for implementing other methods.
    public List<Double> getStartTimes() {
        return byStart.getKeys();
    }

    // Gives a sorted list of end times.
    // Useful for testing and debugging.
    // The running time is O(n), so it should not
    // be used for implementing other methods.
    public List<Double> getEndTimes() {
        return byEnd.getKeys();
    }

    // Identifies whether or not the given range conflicts with any
    // ranges that are already in the data structure.
    // If the data structure is empty, then it does not conflict
    // with any ranges, so we should return false.
    // The running time of this method should be O(log n)
    public boolean hasConflict(Range query) {
        // TODO
        if (isEmpty()) {
            return false;
        }

        // obtain the current range's start and end
        Double queryStart = query.start;
        Double queryEnd = query.end;

        // hard to explain this so:
        /*
         prevStart got by query end
         ------------------|
                        |__|
                        |-------------------
                           nextStart got by query start
         */
        Double nextStart = byStart.findNextKey(queryStart);
        Double prevStart = byStart.findPrevKey(queryEnd);

        if (prevStart != null) {
            Double prevEnd = findByStart(prevStart).end;
            // it queryStart is smaller than previous end then it's a conflict
            if (queryStart < prevEnd) {
                return true;
            }
        }

        if (nextStart != null) {
            // it's a conflict if query end is bigger than next start
            if (queryEnd > nextStart) {
                return true;
            }
        }

        // no conflict
        return false;
    }

    // Inserts the given range into the data structure if it has no conflict.
    // Does not modify the data structure if it does have a conflict.
    // Return value indicates whether or not the item was successfully
    // added to the data structure.
    // Running time should be O(log n)
    public boolean insert(Range query) {
        // TODO
        if (hasConflict(query)) {
            return false;
        } else {
            // insert query to the byStart and byEnd AVL tree
            byStart.insert(query.start, query);
            byEnd.insert(query.end, query);
            size++;
            return true;
        }
    }
}
