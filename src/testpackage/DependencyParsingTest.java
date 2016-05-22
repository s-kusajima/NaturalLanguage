package testpackage;

import java.net.URLEncoder;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class DependencyParsingTest {

	public DependencyParsingTest (String query) {

		try{
			
			ArrayList<Chunk> _chunkList = new ArrayList<Chunk>();
			
//			String query = "こんな私の故郷は非常に良い場所なのです。";
			
			String query_utf8 = URLEncoder.encode(query, "UTF-8");

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			String uri = "http://jlp.yahooapis.jp/DAService/V1/parse?appid=dj0zaiZpPVpjdnZpNzNza2lFMCZzPWNvbnN1bWVyc2VjcmV0Jng9M2E-&sentence=" + query_utf8;
			Node root = builder.parse(uri);

			Node resultSet = root.getFirstChild();
			Node result = resultSet.getChildNodes().item(1);
			Node chunkList = result.getChildNodes().item(1);
			
			NodeList chunks = ((Element)chunkList).getElementsByTagName("Chunk");
			
			for(int i = 0; i < chunks.getLength(); i++){
				Node currentNode = chunks.item(i);

				String id = ((Element)currentNode).getElementsByTagName("Id").item(0).getFirstChild().getNodeValue();
				String dependency = ((Element)currentNode).getElementsByTagName("Dependency").item(0).getFirstChild().getNodeValue();

				NodeList morphems = ((Element)(((Element)currentNode).getElementsByTagName("MorphemList").item(0))).getElementsByTagName("Morphem");
				ArrayList<String> surfaceList = new ArrayList<String>();
				for(int j = 0; j < morphems.getLength(); j++){
					String surface = ((Element)morphems.item(j)).getElementsByTagName("Surface").item(0).getFirstChild().getNodeValue();

					surfaceList.add(surface);
				}
				Chunk c = new Chunk(id, dependency, surfaceList);
				System.out.println(c);
				_chunkList.add(c);
			}
			
			System.out.println("");
			
			String dep = "";
			for(int i = 0; i < _chunkList.size(); i++){
				if(_chunkList.get(i).getDependency().equals("-1")) dep = _chunkList.get(i).getId();
			}
			for(int i = 0; i < _chunkList.size(); i++){
				if(_chunkList.get(i).getDependency().equals(dep) || _chunkList.get(i).getDependency().equals("-1")){
					for(int j = 0; j < _chunkList.get(i).getMorphemList().size(); j++){
						System.out.print(_chunkList.get(i).getMorphemList().get(j));
					}
				}
			}
			
			System.out.println();

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public static void main (String[] args) {
		new DependencyParsingTest("こんな私の故郷は非常に良い場所なのです。");
	}

}
