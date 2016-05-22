package testpackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class MeCabTest {

	public MeCabTest () {
		try{
			String[] cmd = {"mecab.exe", "test.txt"};
			ProcessBuilder pb = new ProcessBuilder(cmd);
			Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				/*
				if(!line.equals("EOS")){
					String surface = line.split("\t")[0];
					String pos = line.split("\t")[1].split(",")[0];
					if(!pos.equals("連体詞"))
					System.out.print(surface);
				}else{
					System.out.println();
				}
				*/
			}
			br.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void main (String[] args) {
		new MeCabTest();
	}

}