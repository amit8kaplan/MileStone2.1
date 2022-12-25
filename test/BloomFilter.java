package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.BitSet;

public class BloomFilter {
    private int numHashFunctions;
    private MessageDigest[] hashFunctions;
    private BitSet bits;
    private int numBits;

    public BloomFilter(int numBits, String... hashFunctionNames) {
        this.numHashFunctions = hashFunctionNames.length;
        this.hashFunctions = new MessageDigest[numHashFunctions];
        this.numBits=numBits;
        this.bits = new BitSet(this.numBits);

        // Initialize the hash functions
        for (int i = 0; i < numHashFunctions; i++) {
            try {
                hashFunctions[i] = MessageDigest.getInstance(hashFunctionNames[i]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void add(String word) {
        // Hash the word using each of the hash functions
        for (int i = 0; i < numHashFunctions; i++) {
            hashFunctions[i].update(word.getBytes());
            byte[] hash = hashFunctions[i].digest();
            BigInteger hashInt = new BigInteger(hash);

            int val = hashInt.intValue();
            if (val>0)
                bits.set(val % this.numBits);
            else
                bits.set((-val) % this.numBits);

        }
    }

    public boolean mightContain(String word) {
        // Check if the word might be in the bloom filter by hashing it and checking
        // if the relevant bits are set
        for (int i = 0; i < numHashFunctions; i++) {
            hashFunctions[i].update(word.getBytes());
            byte[] hash = hashFunctions[i].digest();
            BigInteger hashInt = new BigInteger(hash);
            int val = hashInt.intValue();
            if (val<0)
                val*=-1;
            if (!bits.get(val%this.numBits))
                return false;

        }
        return true;
    }
    public boolean contains(String word) {
        return mightContain(word);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits.length(); i++) {
            sb.append(bits.get(i) ? "1" : "0");
        }
        return sb.toString();
    }


}
