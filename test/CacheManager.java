package test;
import java.util.HashSet;

public class CacheManager {
	private int maxSize;
	private CacheReplacementPolicy crp;
	private HashSet<String> cache;

	public CacheManager(int maxSize, CacheReplacementPolicy crp) {
		this.maxSize = maxSize;
		this.crp = crp;
		this.cache = new HashSet<>();
	}
	public boolean query(String word) {
		return cache.contains(word);
	}
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


