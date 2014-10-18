package merging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class N_way_merge_using_external_merge {
	
	int No_of_files=0;
	String[] listString;
	PrintWriter pw;
	private String fileDir = "D:\\XMLParsing_Files\\Extracted_Data";
	private File[] fileList;
	private BufferedReader[] readers;
	
	public static void main(String[] args) throws IOException {
		
		N_way_merge_using_external_merge nwm=new N_way_merge_using_external_merge();
		 
		long start= System.currentTimeMillis();
		
		try {

			nwm.createFileList();
			
			nwm.createReaders();
			nwm.FinalFile();
		}
		finally {
			nwm.pw.flush();
			nwm.pw.close();
			for (BufferedReader readers : nwm.readers) {
				
				readers.close();

			}
		}
		long end = System.currentTimeMillis();
		System.out.println("Files merged into a single file.\nTime taken: "+((end-start)/1000)+"secs");
	}
	
	public void createFileList() throws IOException {
		//creates a list of sorted files present in a particular directory
		File folder = new File(fileDir);
		fileList = folder.listFiles();
		No_of_files=fileList.length;
		assign();
		System.out.println("No. of files - "+ No_of_files);
		
	}
	
	public void assign() throws IOException
	{
		listString = new String[No_of_files];
		
		pw = new PrintWriter(new BufferedWriter(new FileWriter("D:\\XMLParsing_Files\\Final.txt", true)));
	}

	public void createReaders() throws IOException {
		//creates array of BufferedReaders to read the files
		readers = new BufferedReader[No_of_files];
		
		for(int i=0;i<No_of_files;++i)
		{
			readers[i]=new BufferedReader(new FileReader(fileList[i]));
		}
	}
	
	public void FinalFile() throws IOException {
		
		for(int i=0;i<No_of_files;i++)
		{
			listString[i]=readers[i].readLine();
		}  
		
		WriteToFile(listString);
		
	}
	
	public void WriteToFile(String[] listString) throws IOException{
		
		String min_string="zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
		int min_index=No_of_files;
		while(!(min_string.equals("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz")))
		{
			min_string="zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
			min_index=No_of_files-1;
			for(int i=0;i<No_of_files;++i)
			{
				if(listString[i].compareTo(min_string) < 0)
				{
					min_string=listString[i];
					min_index=i;
				}
			}
			pw.println(min_string);
			//System.out.println(min_string);
			listString[min_index]=readers[min_index].readLine();
			
			
		}	
	}
}
