package nom.side.poc.cache.impl;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import nom.side.poc.cache.Cache;

/**
 * Hello world!
 *
 */
public class InMemoryCache extends Thread implements Cache {
	private Map<String, CacheEntry> store;
	private int timeToLive;
	
	private static InMemoryCache cacheObj;

	private InMemoryCache() {
		this.store = new ConcurrentHashMap<String, CacheEntry>(10);
		this.timeToLive = 30;
		this.setDaemon(true);
		cacheObj = this;
		System.out.println("New cache created..");
	}

	public static InMemoryCache loadCache() {
		if (cacheObj == null) {
			(new InMemoryCache()).start();
		}
		return cacheObj;
	}

	@Override
	public String add(Object obj) {
		String key = UUID.randomUUID().toString();
		this.store.put(key, new CacheEntry(obj));
		return key;
	}

	@Override
	public Object get(String key) {
		return this.store.get(key).content;
	}

	@Override
	public void delete(String key) {
		this.store.remove(key);
	}

	@Override
	public void run() {
		cacheObj = new InMemoryCache();

		while (!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(5000);
				for(Entry<String, CacheEntry> entry : store.entrySet()) {
					if(entry.getValue().getAge(new Date()) > this.timeToLive)
						this.delete(entry.getKey());
				}
			} catch (InterruptedException e) {/*Do Nothing*/}
		}
	}
	
	class CacheEntry {
		private Object content;
		private Date createdOn;

		private CacheEntry(Object content) {
			this.content = content;
			this.createdOn = new Date();
		}

		private long getAge(Date currentdate) {
			return (currentdate.getTime() - createdOn.getTime())/1000;
		}
	}
}
