

package test;
import java.util.*;
/**
 * Amit Kaplan 209049972
 * @version 2.1 - final work
 */

/**
 * Class implements CacheReplacementPolicy
 * LRU = least recently used
 * meaning - the eldiest we asked about
 * example : [a,b,c,a] return b;
 * Based on LinkedHashSet<String
 */
public class LRU implements CacheReplacementPolicy{
    LinkedHashSet<String> set;
    public LRU() {
        set = new LinkedHashSet<String>();
    }
    /**
     * method check if the structure already contains the word, if so - remove it
     * finaly - edit the word
     * this presudere makes promis that the last time i asked for it will be saved
     * @param word - String to check on it
     */
    public void add(String word) {
        if(set.contains(word)) {
            set.remove(word);
        }
        set.add(word);
    }
    /**
     * valid - check if the structure isnt empty - if so return null
     * remove the first (iter) from the structure
     * @return String word of LRU (least recently used)
     */
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