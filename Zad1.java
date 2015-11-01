import java.io.*;
import java.util.*;

public class Zad1 {
	
	final static int maxLength = 91;
	static File file = new File("C:/Users/Sakumiak/Desktop/kryptJava/Krypto/src/kryptogramy.txt");
	static int cryptograms[][] = new int [21][];
	static int tempKeys[][][] = new int[20][maxLength][];
	static int keys[][] = new int[maxLength][];
	
	static public void readFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String tab[][] = new String[21][];
			String line;
			
			for(int i = 0; (line = br.readLine()) != null; i++) {
				tab[i] = line.split(" ");
				cryptograms[i] = new int[tab[i].length];
				for(int j = 0; j < tab[i].length; j++)
					cryptograms[i][j] = Integer.parseInt(tab[i][j],2);
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void findTempKeys() {
		int xor;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		
		for(int k = 0; k < maxLength; k++)
			for(int w = 0; w < 20; w++) {
				
				for(int sign = 0; sign < 255; sign++) {
					xor = sign ^ cryptograms[w][k];
					if(xor >= 'a' && xor <= 'z' || xor >= 'A' && xor <= 'Z' || xor == '\'' || xor == ',' || xor == ' ' || xor == '?' || xor == '.' || xor == '-')
						temp.add(sign);
				}
				
				tempKeys[w][k] = new int[temp.size()];
				for(int i = 0; i < temp.size(); i++)
					tempKeys[w][k][i] = temp.get(i).intValue();
				temp.clear();
			}
				
	}
	
	public static Integer[] toInteger(int[] array) {
		Integer[] res = new Integer[array.length];
		
		for(int i = 0; i < array.length; i++)
			res[i] = Integer.valueOf(array[i]);
		
		return res;
	}
	
	public static Integer[] searchCommon(Integer[] tab1, Integer[] tab2) {
		
		Integer[] hash; 
		Integer[] search;
		ArrayList<Integer> hashList = new ArrayList<Integer>();
		ArrayList<Integer> searchList = new ArrayList<Integer>();
		
		if(tab1.length > tab2.length) {
			hash = tab1;
			search = tab2;
		}
		else {
			hash = tab2;
			search = tab1;
		}
		
		for (Integer i : hash)
			hashList.add(i);
		for(Integer i : search)
			if(hashList.contains(i))
				searchList.add(i);
		
		return searchList.toArray(new Integer[0]);
	}
	
	public static int[] toIntArray (Integer[] tab) {
		int[] res = new int[tab.length];
		
		for(int i = 0; i < tab.length; i++)
			res[i] = tab[i].intValue();
		
		return res;
	}
	
	public static void findKeys() {
		
		int xor;

		for(int w = 0; w < 20; w++)
			for(int k = 0; k < maxLength; k++) {
				if(tempKeys.length == 1)
					break;
				tempKeys[0][k] = toIntArray(searchCommon(toInteger(tempKeys[0][k]), toInteger(tempKeys[w][k])));
				
			}
		
		/*PrintWriter writer = null;
        try {
            writer = new PrintWriter("/Users/Sakumiak/Desktop/kryptJava/Krypto/src/klucze.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < maxLength; i++) {
            writer.println("nr:" + i);
            for (int j = 0; j < tempKeys[0][i].length; j++) {
                xor = cryptograms[20][i] ^ keys[i][j];
                if (xor >= 'a' && xor <= 'z' || xor >= 'A' && xor <= 'Z' || xor == '\'' || xor == ',' || xor == ' ' || xor == '?' || xor == '.' || xor == '-') {
                    writer.print((char) tempKeys[0][i][j] + " ");
                }
            }
            writer.println();
        }
        writer.close();*/
				
	}
	

	public static void main(String[] args) {
		readFile();
		findTempKeys();
		findKeys();
		
		//System.out.println(keys[0][0]);
		
		for(int i = 0; i < maxLength; i++) {
			if(tempKeys[0][i].length == 1) {
				System.out.print((char)(cryptograms[20][i] ^ tempKeys[0][i][0]));
			}
			else {
				System.out.print("[");
				for(int j = 0; j < tempKeys[0][i].length; j++)
					System.out.print((char)(cryptograms[20][i] ^ tempKeys[0][i][j]));
				System.out.print("]");
			}
		}

	}

}
