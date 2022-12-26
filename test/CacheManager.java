package test;
import java.util.HashSet;
/**
 * A class that implements a cache for storing a fixed number of items. The cache
 * uses a CacheReplacementPolicy to determine which items to remove when the cache
 * is full.
 *
 * @author Amit Kaplan 209049972
 * @version submit 2.1 26.12.22
 */

public class CacheManager {
	/** The maximum size of the cache. */
	private int maxSize;
	/** LRU / LFU - HOW TO replace and remove when the cache is full. */
	private CacheReplacementPolicy crp;
	/** The cache as a set of strings. */
	private HashSet<String> cache;
	/**
	 * Constructs a new CacheManager with the given maximum size and cache
	 * replacement policy.
	 *
	 * @param maxSize the maximum size of the cache
	 * @param crp the cache replacement policy to use when the cache is full
	 */
	public CacheManager(int maxSize, CacheReplacementPolicy crp) {
		this.maxSize = maxSize;
		this.crp = crp;
		// Initialize the cache as a new hash set
		this.cache = new HashSet<>();
	}
	/**
	 * Queries the cache for the given word.
	 *
	 * @param word the word to search for in the cache
	 * @return true if the word is in the cache, false otherwise
	 */
	public boolean query(String word) {
		return cache.contains(word);
	}
	/**
	 * Adds the given word to the cache. If the cache is full, removes the least
	 * frequently used/least recently used word according to the cache replacement
	 * policy.
	 *
	 * @param word the word to add to the cache
	 */
	public void add(String word) {
			// Update the usage frequency/position of the word in the cache
			crp.add(word);

			// Add the word to the cache
			cache.add(word);

			// If the cache is full, remove the least frequently used/least recently used word
			if (cache.size() > maxSize) {
				String removedWord = crp.remove();
				cache.remove(removedWord);
			}
		}

	}


