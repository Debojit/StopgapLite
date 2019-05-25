package nom.side.poc.cache;

public interface Cache {

	public String add(Object obj);
	
	public Object get(String key);
	
	public void delete(String key);
}
