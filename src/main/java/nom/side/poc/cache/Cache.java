package nom.side.poc.cache;

public interface Cache extends Runnable {

	public String add(Object obj);
	
	public Object get(String key);
	
	public void delete(String key);
}
