//package test;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//public class MainTrain {
//
//	public static void testLRU() {
//		CacheReplacementPolicy lru=new LRU();
//		lru.add("a");
//		lru.add("b");
//		lru.add("c");
//		lru.add("a");
//
//		if(!lru.remove().equals("b"))
//			System.out.println("wrong implementation for LRU (-10)");
//	}
//
//	public static void testLFU() {
//		CacheReplacementPolicy lfu=new LFU();
//		lfu.add("a");
//		lfu.add("b");
//		lfu.add("b");
//		lfu.add("c");
//		lfu.add("a");
//
//		if(!lfu.remove().equals("c"))
//			System.out.println("wrong implementation for LFU (-10)");
//	}
//
//	public static void testCacheManager() {
//		CacheManager exists=new CacheManager(3, new LRU());
//		boolean b = exists.query("a");
//		b|=exists.query("b");
//		b|=exists.query("c");
//
//		if(b)
//			System.out.println("wrong result for CacheManager first queries (-5)");
//
//		exists.add("a");
//		exists.add("b");
//		exists.add("c");
//
//		b=exists.query("a");
//		b&=exists.query("b");
//		b&=exists.query("c");
//
//		if(!b)
//			System.out.println("wrong result for CacheManager second queries (-5)");
//
//		boolean bf = exists.query("d"); // false, LRU is "a"
//		exists.add("d");
//		boolean bt = exists.query("d"); // true
//		bf|= exists.query("a"); // false
//		exists.add("a");
//		bt &= exists.query("a"); // true, LRU is "b"
//
//		if(bf || ! bt)
//			System.out.println("wrong result for CacheManager last queries (-10)");
//
//	}
//
//	public static void testBloomFilter() {
//		BloomFilter bf =new BloomFilter(256,"MD5","SHA1");
//		String[] words = "the quick brown fox jumps over the lazy dog".split(" ");
//		for(String w : words)
//			bf.add(w);
//		String str11 = "0010010000000000000000000000000000000000000100000000001000000000000000000000010000000001000000000000000100000010100000000010000000000000000000000000000000110000100000000000000000000000000010000000001000000000000000000000000000000000000000000000000000001";
//		byte[] a =str11.getBytes();
////		for (int i=0; i< a.length; i++)
////			if(a[i]== 49)
////				System.out.println(i);
//		if(!bf.toString().equals("0010010000000000000000000000000000000000000100000000001000000000000000000000010000000001000000000000000100000010100000000010000000000000000000000000000000110000100000000000000000000000000010000000001000000000000000000000000000000000000000000000000000001"))
//			System.out.println("problem in the bit vector of the bloom filter (-10)");
//		boolean spec;
//		boolean found=true;
//		for(String w : words) {
//			spec = bf.contains(w);
////			System.out.println(w);
////			System.out.println(spec);
//			found &= bf.contains(w);
//
//		}
//		if(!found)
//			System.out.println("problem finding words that should exist in the bloom filter (-15)");
//
//		found=false;
//		for(String w : words)
//			found |= bf.contains(w+"!");
//
//		if(found)
//			System.out.println("problem finding words that should not exist in the bloom filter (-15)");
//	}
//
//	public static void testIOSearch() throws Exception{
//		String words1 = "the quick brown fox \n jumps over the lazy dog";
//		String words2 = "A Bloom filter is a space efficient probabilistic data structure, \n conceived by Burton Howard Bloom in 1970";
//		PrintWriter out = new PrintWriter(new FileWriter("text1.txt"));
//		out.println(words1);
//		out.close();
//		out = new PrintWriter(new FileWriter("text2.txt"));
//		out.println(words2);
//		out.close();
//
//		if(!IOSearcher.search("is", "text1.txt","text2.txt"))
//			System.out.println("oyur IOsearch did not found a word (-5)");
//		if(IOSearcher.search("cat", "text1.txt","text2.txt"))
//			System.out.println("your IOsearch found a word that does not exist (-5)");
//	}
//
//	public static void testDictionary()  {
//		Dictionary d = new Dictionary("text1.txt","text2.txt");
//		if(!d.query("is"))
//			System.out.println("problem with dictionarry in query (-5)");
//		else
//			System.out.println("okeyDic");
//		if(!d.query("filter"))
//			System.out.println("problem with dictionarry in query (-5)");
//		if(!d.challenge("lazy"))
//			System.out.println("problem with dictionarry in query (-5)");
//		else
//			System.out.println("okeyDic2");
//	}
//
//	public static void main(String[] args) {
////		testLRU();
//		testLFU();
//		System.out.println("done1");
//
//		testCacheManager();
//		System.out.println("done2");
//		testBloomFilter();
//		try {
//			testIOSearch();
//		} catch(Exception e) {
//			System.out.println("you got some exception (-10)");
//		}
//		System.out.println("done3");
//		testDictionary();
//		System.out.println("done");
//	}
//}













package test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class MainTrain {

	public static class ClientHandler1 implements ClientHandler{
		PrintWriter out;
		Scanner in;
		@Override
		public void handleClient(InputStream inFromclient, OutputStream outToClient) {
			out=new PrintWriter(outToClient);
			in=new Scanner(inFromclient);
			String text = in.next();
			out.println(new StringBuilder(text).reverse().toString());
			out.flush();
		}

		@Override
		public void close() {
			in.close();
			out.close();
		}

	}


	public static void client1(int port) throws Exception{
		Socket server=new Socket("localhost", port);
		Random r=new Random();
		String text = ""+(1000+r.nextInt(100000));
		String rev=new StringBuilder(text).reverse().toString();
		PrintWriter outToServer=new PrintWriter(server.getOutputStream());
		Scanner in=new Scanner(server.getInputStream());
		outToServer.println(text);
		outToServer.flush();
		String response=in.next();
		if(response==null || !response.equals(rev))
			System.out.println("problem getting the right response from your server, cannot continue the test (-100)");
		in.close();
		outToServer.println(text);
		outToServer.close();
		server.close();
	}

	public static boolean testServer() {
		boolean ok=true;
		Random r=new Random();
		int port=6000+r.nextInt(1000);
		MyServer s=new MyServer(port, new ClientHandler1());
		int c = Thread.activeCount();
		s.start(); // runs in the background
		try {
			client1(port);
		}catch(Exception e) {
			System.out.println("some exception was thrown while testing your server, cannot continue the test (-100)");
			ok=false;
		}
		s.close();

		try {Thread.sleep(2000);} catch (InterruptedException e) {}

		if (Thread.activeCount()!=c) {
			System.out.println("you have a thread open after calling close method (-100)");
			ok=false;
		}
		return ok;
	}


	public static String[] writeFile(String name) {
		Random r=new Random();
		String txt[]=new String[10];
		for(int i=0;i<txt.length;i++)
			txt[i]=""+(10000+r.nextInt(10000));

		try {
			PrintWriter out=new PrintWriter(new FileWriter(name));
			for(String s : txt) {
				out.print(s+" ");
			}
			out.println();
			out.close();
		}catch(Exception e) {}

		return txt;
	}

	public static void testDM() {
		String t1[]=writeFile("t1.txt");
		String t2[]=writeFile("t2.txt");
		String t3[]=writeFile("t3.txt");

		DictionaryManager dm=DictionaryManager.get();

		if(!dm.query("t1.txt","t2.txt",t2[4]))
			System.out.println("problem for Dictionary Manager query (-5)");
		if(!dm.query("t1.txt","t2.txt",t1[9]))
			System.out.println("problem for Dictionary Manager query (-5)");
		if(dm.query("t1.txt","t3.txt","2"+t3[2]))
			System.out.println("problem for Dictionary Manager query (-5)");
		if(dm.query("t2.txt","t3.txt","3"+t2[5]))
			System.out.println("problem for Dictionary Manager query (-5)");
		if(!dm.challenge("t1.txt","t2.txt","t3.txt",t3[2]))
			System.out.println("problem for Dictionary Manager challenge (-5)");
		if(dm.challenge("t2.txt","t3.txt","t1.txt","3"+t2[5]))
			System.out.println("problem for Dictionary Manager challenge (-5)");

		if(dm.getSize()!=3)
			System.out.println("wrong size for the Dictionary Manager (-10)");

	}

	public static void runClient(int port,String query,boolean result) {
		try {
			Socket server=new Socket("localhost",port);
			PrintWriter out=new PrintWriter(server.getOutputStream());
			Scanner in=new Scanner(server.getInputStream());
			out.println(query);
			out.flush();
			String res=in.next();
			if((result && !res.equals("true")) || (!result && !res.equals("false")))
				System.out.println("problem getting the right answer from the server (-10)");
			in.close();
			out.close();
			server.close();
		} catch (IOException e) {
			System.out.println("your code ran into an IOException (-10)");
		}
	}

	public static void testBSCH() {
		String s1[]=writeFile("s1.txt");
		String s2[]=writeFile("s2.txt");
//		for (String s : s1) {
//			System.out.println(s);
//		}
//
//		System.out.println("s2");
//		for (String s : s2) {
//			System.out.println(s);
//		}
		Random r=new Random();
		int port=6000+r.nextInt(1000);
		MyServer s=new MyServer(port, new BookScrabbleHandler());
		s.start();
		System.out.println(1);
		runClient(port, "Q,s1.txt,s2.txt,"+s1[1], true);
		System.out.println(2);
		runClient(port, "Q,s1.txt,s2.txt,"+s2[4], true);
		System.out.println(3);
		runClient(port, "Q,s1.txt,s2.txt,2"+s1[1], false);
		System.out.println(4);
		runClient(port, "Q,s1.txt,s2.txt,3"+s2[4], false);
		System.out.println(5);
		runClient(port, "C,s1.txt,s2.txt,"+s1[9], true);
		System.out.println(6);
		runClient(port, "C,s1.txt,s2.txt,#"+s2[1], false);
		s.close();
	}

	public static void main(String[] args) {
		if(testServer()) {
			testDM();
     		testBSCH();

//			System.out.println(testServer());

		}
		System.out.println("done");
	}

}
