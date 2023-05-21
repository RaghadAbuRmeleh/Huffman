package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Compress {
	int huffCodeArraySize;
	HuffCode[] huffCodeArray;
	int originalSize;
	int compressedSize;
	String File;

	public Compress (String File ) {
		this.File = File; 	
	}


	public int getHuffCodeArraySize() {
		return huffCodeArraySize;
	}


	public void setHuffCodeArraySize(int huffCodeArraySize) {
		this.huffCodeArraySize = huffCodeArraySize;
	}


	public HuffCode[] getHuffCodeArray() {
		return huffCodeArray;
	}


	public void setHuffCodeArray(HuffCode[] huffCodeArray) {
		this.huffCodeArray = huffCodeArray;
	}


	public int getOriginalSize() {
		return originalSize;
	}


	public void setOriginalSize(int originalSize) {
		this.originalSize = originalSize;
	}


	public int getCompressedSize() {
		return compressedSize;
	}


	public void setCompressedSize(int compressedSize) {
		this.compressedSize = compressedSize;
	}


	public String getFile() {
		return File;
	}


	public void setFile(String file) {
		File = file;
	}


	public void Compressing (String fileName) {
		try {
			///fileName = Path.getText();
			/*if (fileName == "") {
				JOptionPane.showMessageDialog(this,
						"Please Open a File to Compress");
				return;
			}*/
			FileInputStream scan = new FileInputStream(fileName);
			originalSize = scan.available();
			// Reading the file as bytes
			byte[] buffer = new byte[1024];
			int[] tmp = new int[256];
			//	Status.setText("Reading the File");
			int size = scan.read(buffer, 0, 1024);
			int index = -1;
			do {
				for (int i = 0; i < size; i++) {
					index = buffer[i];
					if (index < 0)
						index += 256;
					if (tmp[index] == 0)
						huffCodeArraySize++;// Counting the number of different
					// characters
					tmp[index]++;
				}
				size = scan.read(buffer, 0, 1024);
			} while (size > 0); // Finish Counting

			//tmp sar feha al frequency

			for (int i = 0; i < buffer.length; i++)
				buffer[i] = 0;

			int counter = 0;
			// Shrink the original array, building the huffCodeArray
			//		Status.setText("Compresssing");
			int nbChar = 0;
			huffCodeArray = new HuffCode[huffCodeArraySize];
			for (int i = 0; i < 256; i++)
				if (tmp[i] != 0) { //nonzeroes
					huffCodeArray[counter++] = new HuffCode((char) i, tmp[i]);
					nbChar += tmp[i];
					tmp[i] = 0;
				}// Number of Frequency

			// sart al huffCodeArray hl2 feha ch, frequency 

			if (huffCodeArraySize != 1) {
				TreeNode[] t = new TreeNode[huffCodeArraySize];
				Heap h = new Heap(huffCodeArraySize + 10);
				for (int i = 0; i < huffCodeArraySize; i++) {
					t[i] = new TreeNode(huffCodeArray[i].counter,huffCodeArray[i].ch);
					h.addElt(t[i]);             // 3mlt tree node l kul char m3 freq, b3deen r7t dfthm 3l heap
				}

				for (int i = 1; i <= t.length - 1; i++) {
					TreeNode z = new TreeNode();
					TreeNode x = h.deleteElt();
					TreeNode y = h.deleteElt();
					z.count = x.count + y.count;
					z.left = x;
					z.right = y;
					h.addElt(z);
				} // delete from heap and add a new tree node
				getCodes(h.getElt()[1], "");
			} else {
				huffCodeArray[0].huffCode = "1";
				huffCodeArray[0].codeLength = 1;
			}

			String[] out = new String[256]; //aktr eshe mmkn ykun 256
			for (int i = 0; i < huffCodeArraySize; i++)
				out[(int)huffCodeArray[i].ch] = new String(huffCodeArray[i].huffCode);

			//out sar feha al huffman code
			
			//////////huffcodearray sart jahzeh (feha al ch,huffcode,freq,length).
			
			String outFileName = new StringTokenizer(fileName, ".").nextToken()+ ".huf";
			File f = new File(outFileName);
			FileOutputStream output = new FileOutputStream(outFileName);
			byte[] outbuffer = new byte[1024];
			counter = 0;

			// The Name of The Original File
			for (int i = 0; i < fileName.length(); i++)
				outbuffer[counter++] = (byte) fileName.charAt(i);//sar 3nde bl outbuffer tmtheel al asci lkl char bl file name
			
			outbuffer[counter++] = '\n';
			
			// Number of Characters in the Original File
			String nbchar = String.valueOf(nbChar);
		
			for (int i = 0; i < nbchar.length(); i++) {
				outbuffer[counter++] = (byte) nbchar.charAt(i); //sar kamn bl outbuffer tmtheel 3dd al chars bl orifinal file
			}
			outbuffer[counter++] = '\n';
			
			// Number of Distinct Characters
			for (int i = 0; i < String.valueOf(huffCodeArraySize).length(); i++) //sar kman bl outbuffer tmtheel length al huffCodeArraySize
				outbuffer[counter++] = (byte) String.valueOf(huffCodeArraySize).charAt(i);
			outbuffer[counter++] = '\n';

			
			output.write(outbuffer, 0, counter);
			
			counter = 0;
			for (int i = 0; i < outbuffer.length; i++)
				outbuffer[i] = 0;

			// The HuffCode for Each Character
			for (int i = 0; i < huffCodeArraySize; i++) {
				if (counter == 1024) {
					output.write(outbuffer);
					counter = 0;
				}
				outbuffer[counter++] = (byte) huffCodeArray[i].ch;
				if (counter == 1024) {
					output.write(outbuffer);
					counter = 0;
				}
				// Add the Counter
				outbuffer[counter++] = (byte) huffCodeArray[i].codeLength; //sar kman al code length bl outbuffer
				String res = "";
				Long x ;
				if (huffCodeArray[i].huffCode.length() > 15) { //upper limited to a maximum of 16 bits per code word
					for (int z = 0; z < huffCodeArray[i].huffCode.length() / 2; z++) {
						res += huffCodeArray[i].huffCode.charAt(z) + "";
					}
					x = Long.parseLong(res);
					res = "";
					for (int z = (huffCodeArray[i].huffCode.length() + 1) / 2; z < huffCodeArray[i].huffCode.length(); z++) {
						res += huffCodeArray[i].huffCode.charAt(z) + "";

					}
					x += Long.parseLong(res);

				} else {
					x = Long.parseLong(huffCodeArray[i].huffCode); // x sar 3nde feha al huffman code l kul character
				}
		
				
				byte[] code = new byte[50];
				int l = 0;
				if (x == 0) {
					outbuffer[counter++] = 0;
					if (counter == 1024) {
						output.write(outbuffer);
						counter = 0;
					}
					outbuffer[counter++] = 0;
					if (counter == 1024) {
						output.write(outbuffer);
						counter = 0;
					}
				} else {
					while (x != 0) {
						if (counter == 1024) {
							output.write(outbuffer);
							counter = 0;
						}
						code[l++] = (byte) (x % 256);
						x /= 256;
					}
					outbuffer[counter++] = (byte) l;
					if (counter == 1024) {
						output.write(outbuffer);
						counter = 0;
					}
					for (int j = 0; j < l; j++) {
						outbuffer[counter++] = code[j];
						if (counter == 1024) {
							output.write(outbuffer);
							counter = 0;
						}
					}
				}

				if (counter == 1024) {
					output.write(outbuffer);
					counter = 0;
				}
				outbuffer[counter++] = '\n';
			}// end for
				
			// Print Out The Header 					//hon khallast al headerr w sar mtboo3 bl out file
			output.write(outbuffer, 0, counter);

			// Reinitialize the Output Buffer
			for (int i = 0; i < outbuffer.length; i++)
				outbuffer[i] = 0;

			// Print Out the Data
			//		Status.setText("Writing the Data");
			
			scan.close();
			scan = new FileInputStream(fileName);
			counter = 0;
			size = scan.read(buffer, 0, 1024);
			do {  /// reads block of 1024 byte mn al input and store them bl buffer
				for (int i = 0; i < size; i++) {  // iterates over each byte in the buffer array
					index = buffer[i];
					if (index < 0)// If the Value was negative
						index += 256;
					for (int j = 0; j < out[index].length(); j++) {   //el out 7kena ele feha al huffman code ll nonzeroes chars w null ll ba2e
						char ch = out[index].charAt(j);					//iterates over each char convert it to 1 or 0 7sb eza ho 1 aw 0
						
						if (ch == '1')
							outbuffer[counter / 8] = (byte) (outbuffer[counter / 8] | 1 << 7 - counter % 8);
						counter++;
						
						if (counter / 8 == 1024) {
							output.write(outbuffer);
							for (int k = 0; k < outbuffer.length; k++)
								outbuffer[k] = 0;
							counter = 0;
						}
					}
				}
				size = scan.read(buffer, 0, 1024);
			} while (size > 0);
			scan.close();
			output.write(outbuffer, 0, (counter / 8) + 1);
			output.close();
			scan = new FileInputStream(f);
			compressedSize = scan.available();
			scan.close();
			//		Status.setText("Finished");
			
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// Compress Finish

	public void getCodes(TreeNode t, String st) {
		if (t.left == t.right && t.right == null)
			for (int i = 0; i < huffCodeArraySize; i++) {
				if (huffCodeArray[i].ch == t.ch) {
					huffCodeArray[i].huffCode = st;
					huffCodeArray[i].codeLength = st.length();
				}
			}
		else {
			getCodes(t.left, st + '0');
			getCodes(t.right, st + '1');
		}
	}// Code for each Char


}
