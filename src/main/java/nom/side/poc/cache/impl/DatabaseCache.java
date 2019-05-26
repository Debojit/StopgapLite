package nom.side.poc.cache.impl;

import nom.side.poc.cache.Cache;

public class DatabaseCache implements Cache {
	private final int DEFAULT_TIME_TO_LIVE = 60;
	private int timeToLive; //In seconds
	
	private static DatabaseCache instance;
	
	private DatabaseCache() {
		this.timeToLive = DEFAULT_TIME_TO_LIVE;
		Thread cacheThread = new Thread(this);
		cacheThread.setDaemon(true);
		cacheThread.start();
	}
	
	private DatabaseCache(int timeToLive) {
		this.timeToLive = timeToLive;
		Thread cacheThread = new Thread(this);
		cacheThread.setDaemon(true);
		cacheThread.start();
	}
	
	public DatabaseCache loadCache() {
		if(instance == null)
			new DatabaseCache();
		return instance;
	}
	
	public DatabaseCache loadCache(int timeToLive) {
		if(instance == null)
			new DatabaseCache(timeToLive);
		else
			instance.timeToLive = timeToLive;
		return instance;
	}
	
	@Override
	public String add(String key, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String key) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
}
