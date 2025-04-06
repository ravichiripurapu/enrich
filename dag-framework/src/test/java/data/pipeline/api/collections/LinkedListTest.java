package data.pipeline.api.collections;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class LinkedListTest {

	@Test
	public void testLinkedList() {
		LinkedList linkedList=new LinkedList();
		assertTrue(linkedList!=null);
		assertTrue(linkedList.isEmpty());
	}
	
	@Test
	public void testAdd() {
		String sampleItem="Sample";
		LinkedList linkedList=new LinkedList<String>();
		linkedList.add(sampleItem);
		assertTrue(!linkedList.isEmpty());
		Iterator<String> iString=linkedList.iterator();
		while (iString.hasNext()) {
			String result=iString.next();
			assertTrue(result.equals(sampleItem));
		}
		assertTrue(linkedList.size()==1);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testRemove() {
		String sampleItem="Sample";
		String sampleItem2="Sample2";
		LinkedList linkedList=new LinkedList<String>();
		linkedList.add(sampleItem);
		linkedList.add(sampleItem2);
		assertTrue(linkedList.size()==2);
		Iterator<String> iterator =  linkedList.iterator();
		iterator.remove();
	}
	
	
	@Test(expected=NoSuchElementException.class)
	public void testNext() {
		String sampleItem="Sample";
		LinkedList linkedList=new LinkedList<String>();
		linkedList.add(sampleItem);
		assertTrue(!linkedList.isEmpty());
		Iterator<String> iString=linkedList.iterator();
		while (iString.hasNext()) {
			String result=iString.next();
			assertTrue(result.equals(sampleItem));
		}
		String result2=iString.next();
	}
}
