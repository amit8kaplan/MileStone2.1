package test;

import java.util.HashMap;

public class DictionaryManager {
    private static DictionaryManager instance;
    private HashMap<String, Dictionary> dictionaries;

    private DictionaryManager() {
        dictionaries = new HashMap<>();
    }

    public static DictionaryManager get() {
        if (instance == null) {
            instance = new DictionaryManager();
        }
        return instance;
    }

    public boolean query(String... args) {
//        System.out.println("DicitoanryMan - strings");
//        for (String arg : args) {
//            System.out.println(arg);
//        }
        boolean checkifexist =  false;
        String word = args[args.length - 1];
//        System.out.println("DicMan - words");
//        System.out.println(args[0]);
//        System.out.println(args[1]);
//        System.out.println(args[2]);
        for (int i = 0; i < args.length - 1; i++) {
            if (!dictionaries.containsKey(args[i])) {
//                System.out.println("a new dictionaries");
//                System.out.println(args[i]);
                dictionaries.put(args[i], new Dictionary(args[i]));
            }

            if(dictionaries.get(args[i]).query(word)){

//                System.out.println("the word is in this dictionary");
//                System.out.println(args[i]);
                checkifexist= true;

            }
        }

        return checkifexist;
    }

    public boolean challenge(String... args) {
        boolean checkifexist = false;
        String word = args[args.length - 1];
        for (int i = 0; i < args.length - 1; i++) {
            if (!dictionaries.containsKey(args[i])) {
                dictionaries.put(args[i], new Dictionary(args[i]));
            }
            if(dictionaries.get(args[i]).challenge(word))
                checkifexist= true;
        }
        return checkifexist;
    }
    public int getSize() {
        return dictionaries.size();
    }

}
