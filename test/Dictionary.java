package test;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Dictionary {
    private CacheManager cacheExistingWords;
    private CacheManager cacheNonExistingWords;
    private BloomFilter bloomFilter;
    private String[] fileNames;


    public Dictionary(String... fileNames) {
        this.fileNames = fileNames;
        cacheExistingWords = new CacheManager(400, new LRU());
        cacheNonExistingWords = new CacheManager(100, new LFU());
        bloomFilter = new BloomFilter(256, "MD5", "SHA1");


        // Insert all the words from the files into the Bloom filter
        for (String fileName : fileNames) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(fileName));
                for (String line : lines) {
                    // Split the line into words
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        bloomFilter.add(word);
                    }
                }
            } catch (IOException e) {
                // Handle the exception here
                return ;
            }
        }
    }
    public boolean query(String word) {
        // Check if the word is in the cache of existing words
        if (cacheExistingWords.query(word)) {
            cacheExistingWords.add(word);
            return true;
        }

        // Check if the word is in the cache of non-existing words
        if (cacheNonExistingWords.query(word)) {
            return false;
        }

        // Check if the word is in the Bloom filter
        boolean exists = bloomFilter.mightContain(word);
        if (exists) {
            cacheExistingWords.add(word);
        } else {
            cacheNonExistingWords.add(word);
        }
        return exists;
    }

    public boolean challenge(String word)  {
        try {
            boolean exists = IOSearcher.search(word, this.fileNames);
            if (exists) {
                cacheExistingWords.add(word);
            } else {
                cacheNonExistingWords.add(word);
            }
            return exists;
        } catch (IOException e) {
            return false;
        }
    }

}

