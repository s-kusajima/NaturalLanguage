package textsummary;

//http://www.asahi.com/articles/ASJ5Q4Q6WJ5QUTIL00X.html?iref=comtop_8_05

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import testpackage.DependencyParsingTest;

public class Summarize {
	
	public Summarize(){
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File("test.txt")));
			String line, str = "";
			while((line = br.readLine()) != null){
				str += line;
			}
			br.close();
			
//			str = str.replace("。", "。\n").replace("、", "、\n");
			str = str.replace("。", "。\n");
			
			String[] lines = str.split("\n");
			for(int i = 0; i < lines.length; i++){
				DependencyParsing dp = new DependencyParsing(lines[i]);
				System.out.println(dp.toString(1));
			}
			System.out.println();
			System.out.println(str);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		new Summarize();
	}

}
