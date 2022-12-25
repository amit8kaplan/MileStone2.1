package test;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary {
    private CacheManager cacheExistingWords;
    private CacheManager cacheNonExistingWords;
    private BloomFilter bloomFilter;

    public Dictionary(String... fileNames) throws IOException {
        cacheExistingWords = new CacheManager(400, new LRU());
        cacheNonExistingWords = new CacheManager(100, new LFU());
        bloomFilter = new BloomFilter(356, "MD5", "SHA1");

        // Insert all the words from the files into the Bloom filter
        for (String fileName : fileNames) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(fileName));
                String line;
                while ((line = reader.readLine()) != null) {
                    bloomFilter.add(line);
                }
            } finally {
                if (reader != null) {
                    reader.close();
                }
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
}

