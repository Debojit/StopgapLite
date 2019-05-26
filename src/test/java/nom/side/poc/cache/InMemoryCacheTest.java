package nom.side.poc.cache;

import org.junit.Assert;
import org.junit.Test;

import nom.side.poc.cache.impl.InMemoryCache;

public class InMemoryCacheTest {
	@Test
	public void testCacheLoading() throws InterruptedException {
		Cache cache = InMemoryCache.loadCache(5);
		String key1 = cache.add("Entry 1");
		Assert.assertTrue(key1 != null);
		Thread.sleep(1000);
		String key2 = cache.add("Entry 2");
		Assert.assertTrue(key2 != null);
		Thread.sleep(1000);
		String key3 = cache.add("Entry 3");
		Assert.assertTrue(key3 != null);
		Thread.sleep(1000);
		
		Thread.sleep(30000);
	}
}
