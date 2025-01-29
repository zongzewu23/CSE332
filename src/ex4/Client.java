package ex4;

import java.util.*;

public class Client {
    public static void main(String[] args){
        testAVLInsert();
        testRangeTree();
        testNextPrev();
//        testRangeTreePerformance();
    }

    public static void testAVLInsert(){
        AVLTree<Integer, String> dict = new AVLTree<>();
        Random r = new Random(332);
        List<Integer> keys = new ArrayList<>();

        for(int i = 0; i < 100; i++){
            int key = r.nextInt(1000);
            dict.insert(key, "A"+key);
            keys.add(key);
        }
        // check the looks reasonable
        List<Integer> dictKeys = dict.getKeys();
        double previousKey = dictKeys.get(0);
        for(int i = 1; i < dictKeys.size(); i++){
            if(previousKey >= dictKeys.get(i)){
                System.out.println("Looks like your AVL tree did not maintain its order!");
                return;
            }
            previousKey = dictKeys.get(i);
        }
        // check the height looks reasonable
        int treeHeight = dict.root.height;
        if(treeHeight != 7){
            System.out.println("Your AVL tree was not the right height!");
            return;
        }
        System.out.println("Super basic testing of AVL insert looks good! Gradescope will test it more rigorously, though!");
    }

    public static void testNextPrev(){
        OrderedDeletelessDictionary<Double, String> dict = new BinarySearchTree<>();
        Random r = new Random(332);
        List<Double> keys = new ArrayList<>();

        for(int i = 0; i < 100; i++){
            double key = r.nextInt(1000);
            dict.insert(key, "A"+key);
            if(!keys.contains(key)){
                keys.add(key);
            }
        }
        Collections.sort(keys);
        for(int i = 0; i < keys.size()-1; i++){
            double queryKey = (keys.get(i) + keys.get(i+1))/2;
            double response = dict.findNextKey(queryKey);
            double correct = keys.get(i+1);
            if(response != correct){
                System.out.println("findNextKey didn't give the right answer!");
                return;
            }
        }
        System.out.println("Super basic testing of findNextKey looks good! Gradescope will test it more rigorously, though!");
        for(int i = 1; i < keys.size(); i++){
            double queryKey = (keys.get(i) + keys.get(i-1))/2;
            double response = dict.findPrevKey(queryKey);
            double correct = keys.get(i-1);
            if(response != correct){
                System.out.println("findPrevKey didn't give the right answer!");
                return;
            }
        }
        System.out.println("Super basic test of findNextKey and findPrevKey looked good! Gradescope will test it more rigorously, though!");
    }

    public static void testRangeTree(){
        Range lecture = new Range(0, 10, "lecture");
        Range concert = new Range(15, 20, "concert");
        Range nap = new Range(-1, 21, "nap time");
        Range dance = new Range(5, 8, "dance recital");
        Range match = new Range(9, 11, "tennis match");



        RangeTree schedule = new RangeTree();

        schedule.insert(concert);
        schedule.insert(dance);

        schedule.insert(lecture);
        schedule.insert(nap);
        schedule.insert(match);


        List<Range> scheduled = schedule.getRanges();

        if(scheduled.size() == 2 && scheduled.contains(lecture) && scheduled.contains(concert)){
            System.out.println(" size= " + scheduled.size());
            System.out.println(" contents are" + scheduled.toString());
            System.out.println("Super basic test of TreeRange looked good! Gradescope will test it more rigorously, though!");
        }
        else{
            System.out.println(" size= " + scheduled.size());
            System.out.println(" contents are" + scheduled.toString());
            System.out.println("TreeRange didn't have the correct contents!");
        }
    }

    public static void testRangeTreePerformance() {
        // 创建一个RangeTree实例

        ArrayList<Range> ranges = new ArrayList<>();
        ranges.add(new Range(0, 10, "lecture"));
        ranges.add(new Range(15, 20, "concert"));
        ranges.add(new Range(-1, 21, "nap time"));
        ranges.add(new Range(5, 8, "dance recital"));
        ranges.add(new Range(9, 11, "tennis match"));
        ranges.add(new Range(-3, 7, "damn"));
        ranges.add(new Range(16, 19, "fxxk"));

        // 打乱顺序
        Collections.shuffle(ranges);

        RangeTree schedule = new RangeTree();

        // 按随机顺序插入
        for (Range range : ranges) {
            schedule.insert(range);
            System.out.println("range:" + range.toString());
        }

        // 检查插入后的内容
        List<Range> rangesAfterInsertion = schedule.getRanges();
        System.out.println("Total ranges scheduled: " + rangesAfterInsertion.size());
        System.out.println("Scheduled ranges: " + rangesAfterInsertion);
        System.out.println("Scheduled ranges count: " + schedule.getRanges().size());
    }



}
