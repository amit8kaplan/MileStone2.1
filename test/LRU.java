package test;


import java.util.*;


public class LRU implements CacheReplacementPolicy{

    LinkedHashSet<String> set;


    //constructor
    public LRU() {

        set = new LinkedHashSet<String>();

    }


    //implemented interface
    public void add(String word) {

        if(set.contains(word)) {
            set.remove(word);
        }

        set.add(word);

    }

    public String remove() {

        String tmp = null;

        if(!set.isEmpty()) {

            Iterator<String> iter = set.iterator();

            tmp =  iter.next();
            set.remove(iter);

        }

        return tmp;

    }


}
