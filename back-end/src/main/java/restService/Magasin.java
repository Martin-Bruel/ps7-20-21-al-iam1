package restService;

public class Magasin {
	private final long id;
	private String name;
	
	public Magasin(long id,String name) {
		this.id=id;
		this.name=name;
		
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
