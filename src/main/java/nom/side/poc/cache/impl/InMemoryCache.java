package nom.side.poc.cache.impl;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import nom.side.poc.cache.Cache;

public class InMemoryCache extends Thread implements Cache {
	private final int DEFAULT_TIME_TO_LIVE = 30; //In seconds

	private Map<String, CacheEntry> store;
	private int timeToLive; //In seconds
	
	private static InMemoryCache instance;

	private InMemoryCache() {
		this.store = new ConcurrentHashMap<String, CacheEntry>(10);
		this.timeToLive = DEFAULT_TIME_TO_LIVE;
		this.setDaemon(true);
		this.start();
	}
	
	private InMemoryCache(int timeToLive) {
		this.store = new ConcurrentHashMap<String, CacheEntry>(10);
		this.timeToLive = timeToLive;
		this.setDaemon(true);
		this.start();
	}

	public static InMemoryCache loadCache() {
		if (instance == null) {
			instance = new InMemoryCache();
		}
		return instance;
	}
	
	public static InMemoryCache loadCache(int timeToLive) {
		if (instance == null) {
			instance = new InMemoryCache(timeToLive);
		}
		else {
			instance.timeToLive = timeToLive;
		}
		return instance;
	}
	
	public static void purgeCache() {
		instance.interrupt();
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
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(2000);
				for(Entry<String, CacheEntry> entry : this.store.entrySet()) {
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
