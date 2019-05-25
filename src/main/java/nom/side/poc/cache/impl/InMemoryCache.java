package nom.side.poc.cache.impl;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import nom.side.poc.cache.Cache;

/**
 * Hello world!
 *
 */
public class InMemoryCache extends Thread implements Cache
{
	private Map<String, Object> cache = new ConcurrentHashMap<String, Object>();
	
	public InMemoryCache() {}
	
	@Override
	public String add(Object obj) {
		String key = UUID.randomUUID().toString();
		this.cache.put(key, obj);
		return key;
	}

	@Override
	public Object get(String key) {
		return this.cache.get(key);
	}

	@Override
	public void delete(String key) {
		this.delete(key);
	}
	
}
