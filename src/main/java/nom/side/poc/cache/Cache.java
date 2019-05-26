package nom.side.poc.cache;

public interface Cache extends Runnable {

	public String add(String key, Object obj);
	
	public Object get(String key);
	
	public void delete(String key);
}
