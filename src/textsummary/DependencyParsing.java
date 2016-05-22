package textsummary;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.*;
import org.w3c.dom.*;

public class DependencyParsing {
	
	private String query;
	private ArrayList<Chunk> chunkArrayList = new ArrayList<Chunk>();

	public DependencyParsing(String query){
		
		this.query = query;

		try{

			String query_utf8 = URLEncoder.encode(query, "UTF-8");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			String uri = "http://jlp.yahooapis.jp/DAService/V1/parse?" + 
					"appid=dj0zaiZpPVpjdnZpNzNza2lFMCZzPWNvbnN1bWVyc2VjcmV0Jng9M2E-" + 
					"&sentence=" + query_utf8;
			Node root = builder.parse(uri);
			Node resultSet = root.getFirstChild();
			Node result = resultSet.getChildNodes().item(1);
			Node chunkList = result.getChildNodes().item(1);
			
			NodeList chunkNodeList = ((Element)chunkList).getElementsByTagName("Chunk");
			
			for(int i = 0; i < chunkNodeList.getLength(); i++){
				Element currentNodeElement = (Element)(chunkNodeList.item(i));
				String id = currentNodeElement.getElementsByTagName("Id").item(0).getFirstChild().getNodeValue();
				String dependency = currentNodeElement.getElementsByTagName("Dependency").item(0).getFirstChild().getNodeValue();
				
				NodeList morphemNodeList = ((Element)(currentNodeElement.getElementsByTagName("MorphemList").item(0))).getElementsByTagName("Morphem");
				ArrayList<String> surfaceArrayList = new ArrayList<String>();
				for(int j = 0; j < morphemNodeList.getLength(); j++){
					String surface = ((Element)morphemNodeList.item(j)).getElementsByTagName("Surface").item(0).getFirstChild().getNodeValue();
					surfaceArrayList.add(surface);
				}
				
				this.chunkArrayList.add(new Chunk(id, dependency, surfaceArrayList));
			}

		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public String getQuery(){
		return this.query;
	}
	
	public ArrayList<Chunk> getChunkArrayList(){
		return this.chunkArrayList;
	}
	
	public String toString(){
		String str = "";
		for(int i = 0; i < chunkArrayList.size(); i++){
			for(int j = 0; j < chunkArrayList.get(i).getMorphemList().size(); j++){
				str += chunkArrayList.get(i).getMorphemList().get(j);
			}
		}
		
		return str;
	}
	
	public String toString(int n){
		
		HashSet<String> dependencySet = new HashSet<String>();
		dependencySet.add("-1");
		int count = 0;
		while(count < n){
			for(int i = 0; i < this.chunkArrayList.size(); i++){
				if(dependencySet.contains(chunkArrayList.get(i).getDependency())){
					dependencySet.add(chunkArrayList.get(i).getId());
				}
			}
			count++;
		}
		
		String str = "";
		for(int i = 0; i < chunkArrayList.size(); i++){
			if(dependencySet.contains(chunkArrayList.get(i).getDependency())){
				for(int j = 0; j < chunkArrayList.get(i).getMorphemList().size(); j++){
					str += chunkArrayList.get(i).getMorphemList().get(j);
				}
			}
		}
		
		return str;
	}
	
	/*
	public static void main(String[] args){
		DependencyParsing dp = new DependencyParsing("こんな私の故郷は非常に良い場所なのです。");
		System.out.println(dp.toString());
	}
	*/

}
