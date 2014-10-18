package sorting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class N_Way_MergeSort {
	List<String> list;
	private int BUFFERSIZE = 1;
	private String fileDir =  "D:\\123";;
	private File[] fileList;
	private List<ComparisonArrayElement> comparisonArray;
	private List<BufferedReader> readers;
	private List<File> fileArrayList;
	private PrintWriter out;
	//private String debugVariable = "";

	public void createFileList() {
		//creates a list of sorted files present in a particular directory
		File folder = new File(fileDir);
		fileList = folder.listFiles();
		fileArrayList = Arrays.asList(fileList);
	}// createFileList

	public void createReaders() throws IOException {
		//creates BufferedReaders to read the files
		readers = new ArrayList<BufferedReader>();
		Iterator<File> iterFile = fileArrayList.iterator();
		while (iterFile.hasNext()) {
			readers.add(new BufferedReader(new FileReader(iterFile.next())));
		}// while
	}// createReaders

	public void createComparisonArray() throws IOException {
		int index;
		BufferedReader tempReader;
		comparisonArray = new ArrayList<ComparisonArrayElement>();
		Iterator<BufferedReader> iterReader = readers.iterator();
		while (iterReader.hasNext()) {
			tempReader = iterReader.next();
			comparisonArray.add(new ComparisonArrayElement(tempReader
					.readLine(), readers.indexOf(tempReader)));
		}// while
		while (comparisonArray.size() != 0) {
			index = comparator();

			if (index >= readers.size())
				continue;
			String temp = readers.get(index).readLine();
			if (temp == null)
				continue;
			// readers.remove(index);

			else
				comparisonArray.add(new ComparisonArrayElement(temp, index));
		}// while

	}// createComparisonArray()

	private int comparator() {
		String min = comparisonArray.get(0).str;
		int minIndex = 0;
		int fileIndex = comparisonArray.get(0).fileIndex;
		for (int i = 0; i < comparisonArray.size(); i++) {
			if (comparisonArray.get(i).str.compareTo(min) < 0) {
				min = comparisonArray.get(i).str;
				minIndex = i;
				fileIndex = comparisonArray.get(i).fileIndex;

			}// if
		}// for
		//debugVariable = min;
		writeToFinalFile(min);
		comparisonArray.remove(minIndex);
		return fileIndex;
	}// comparator()

	private void writeToFinalFile(String str) {

		list.add(str);
		if (list.size() == BUFFERSIZE) {
			for (String s : list) {
				out.println(s);
			}
			list.clear();

		}// if

	}// writeToFinalFile

	public static void main(String[] args) throws IOException {
		long start= System.currentTimeMillis();
		N_Way_MergeSort nwm = new N_Way_MergeSort();
		try {

			nwm.createFileList();
			nwm.out = new PrintWriter(new BufferedWriter(new FileWriter(
					"D:\\Final.txt", true)));
			nwm.list = new ArrayList<String>();
			nwm.createReaders();
			nwm.createComparisonArray();
		}// try
		finally {
			nwm.out.flush();
			nwm.out.close();
			for (BufferedReader readers : nwm.readers) {
				
				readers.close();

			}// for
		}// finally
		long end = System.currentTimeMillis();
		System.out.println("Files merged into a single file.\nTime taken: "+((end-start)/1000)+"secs");
	}// main

}// NWayMerge

class ComparisonArrayElement {
	public String str;
	public int fileIndex;

	ComparisonArrayElement(String str, int fileIndex) {
		this.str = str;
		this.fileIndex = fileIndex;

	}// constructor

}// ComparisonArrayElement

