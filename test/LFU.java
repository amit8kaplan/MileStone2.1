package test;


import java.util.*;

/**
 * Amit Kaplan 209049972
 * @version 2.1 - final work
 */

/**
 * Class implements CacheReplacementPolicy
 * LFU = least frequently used
 * meaning - the word we asked for min times
 * example : [a, b,b,a,b,c] return c
 * Based on LinkedHashMap<String, Integer
 */

public class LFU implements CacheReplacementPolicy{
    LinkedHashMap<String,Integer > set;
    //constructor
    public LFU() { set = new LinkedHashMap<String, Integer>(); }
    //implemented interface

    /**
     * first check if the structure contain word, if true - replace the val of the word in 1++
     * if false - insert a new word into the structure
     * @param word - String
     */
    @Override
    public void add(String word) {
        if(set.containsKey(word)){
            int oldVal = set.get(word);
            set.replace(word, oldVal, oldVal+1);
        }
        else {
            set.put(word, 1);
        }
    }
    /**
     * if structure is empty return null, else buile Map.entry<String, integer, and run over the set and exacute min (key,value)
     * then remove it from the set
     * @return word that we remove in LFU of null
     */
    @Override
    public String remove() {
        String[] res = new String[1];
        res[0] = null;
        Map.Entry<String,Integer> re= null;
        if (!set.isEmpty()){
            re = set.entrySet().stream().min(Comparator.comparingInt(Map.Entry::getValue)).get();
            set.remove(re.getKey());
        }
        return re.getKey();
    }


//    public String remove() {
//        String[] res = new String[1];
//        res[0] = null;
//        if (!set.isEmpty()){
//            int[] min = new int[1];
//            min[0] = Integer.MAX_VALUE;
//            set.forEach((key,value)-> {
//                if( value < min[0]){
//                    min[0] = value;
//                    res[0] = key;
//                }
//            } );
//            set.remove(res[0]);
//        }
//        return res[0];
//    }





}
