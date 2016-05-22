package textsummary;

import java.util.ArrayList;

public class Chunk {
	
	private String id;
	private String dependency;
	private ArrayList<String> morphemList;
	
	public Chunk(String id, String dependency, ArrayList<String> morphemList){
		this.id = id;
		this.dependency = dependency;
		this.morphemList = morphemList;
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getDependency(){
		return this.dependency;
	}
	
	public ArrayList<String> getMorphemList(){
		return this.morphemList;
	}
	
	public String toString(){
		return "[Id=" + this.id + ", Dependency=" + this.dependency + ", Morphem=" + this.morphemList + "]";
	}

}
