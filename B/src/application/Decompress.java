package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Decompress {
	int huffCodeArraySize;
	HuffCode[] huffCodeArray;
	int originalSize;
	int compressedSize;
	String File;
	String name = "";


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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Decompress (String File ) {
		this.File = File; 	
	}
	
	public void Decompressing (String fileName) {
		try {
		
			int size = 0;
			int tracker = 0;
			int bufferTracker = 0;
			boolean flag = true;
			String originalFileName = "";
			java.io.File file = new java.io.File(fileName);

			@SuppressWarnings("resource")
			FileInputStream scan = new FileInputStream(file);
			originalSize = scan.available();
			byte[] buffer = new byte[1024];

			// Get The File Name
			size = scan.read(buffer, 0, 1024);
			char[] tmp = new char[200];
			while (flag) {
				if (buffer[tracker] != '\n')
					tmp[tracker++] = (char) buffer[bufferTracker++];
				else
					flag = false;
			}
			bufferTracker++;
			originalFileName = String.valueOf(tmp, 0, tracker); // l hon bt5ls reading the file name 

			// Get the Number of Characters in the file
			long nbChar = 0;
			tracker = 0;
			flag = true;
			while (flag) {
				if (bufferTracker == 1024) {
					size = scan.read(buffer, 0, 1024);
					bufferTracker = 0;
				}
				if (buffer[bufferTracker] != '\n')
					tmp[tracker++] = (char) buffer[bufferTracker++];
				else
					flag = false;
			}
			nbChar = Integer.parseInt(String.valueOf(tmp, 0, tracker));
			bufferTracker++;

			// Get the Number of Distinct characters
			int loopSize = 0;  // hay bt5znle al # of distinct charsbe used as the loop limit for 
								//reading the huffman code for each character in the original file.
			tracker = 0;
			flag = true;
			while (flag) {
				if (bufferTracker == 1024) {
					size = scan.read(buffer, 0, 1024);
					bufferTracker = 0;
				}
				if (buffer[bufferTracker] != '\n')
					tmp[tracker++] = (char) buffer[bufferTracker++];
				else
					flag = false;
			}
			loopSize = Integer.parseInt(String.valueOf(tmp, 0, tracker));
			bufferTracker++;

			// Reading the huff Code
			huffCodeArray = new HuffCode[loopSize]; // reads the char & code length lkl huffman code
			huffCodeArraySize = loopSize;
			for (int i = 0; i < loopSize; i++) {
				huffCodeArray[i] = new HuffCode((char) Byte.toUnsignedInt(buffer[bufferTracker++]));
				if (bufferTracker == 1024) {
					size = scan.read(buffer, 0, 1024);
					bufferTracker = 0;
				}
				huffCodeArray[i].codeLength = buffer[bufferTracker++]; //is set to the value of the next byte in the buffer
				if (bufferTracker == 1024) {
					size = scan.read(buffer, 0, 1024);
					bufferTracker = 0;
				}
				int l = buffer[bufferTracker++]; // reads the length of the huffman code string from the buffer array:
				if (bufferTracker == 1024) {
					size = scan.read(buffer, 0, 1024);
					bufferTracker = 0;
				}
				if (l == 0)
					bufferTracker++;
				if (bufferTracker == 1024) {
					size = scan.read(buffer, 0, 1024);
					bufferTracker = 0;
				}
				long x = 0;
				for (int j = 0; j < l; j++) { // reads the huffman code string from the buffer array:
					x += Byte.toUnsignedLong(buffer[bufferTracker++]) * (1 << 8 * j);
					if (bufferTracker == 1024) {
						size = scan.read(buffer, 0, 1024);
						bufferTracker = 0;
					}
				}
				
				System.out.println(x);
				
				huffCodeArray[i].huffCode = String.valueOf(x);
				if (huffCodeArray[i].huffCode.length() != huffCodeArray[i].codeLength) {
					String s = "";
					for (int j = 0; j < huffCodeArray[i].codeLength - huffCodeArray[i].huffCode.length(); j++)
						s += "0";
					
					s += huffCodeArray[i].huffCode;
					huffCodeArray[i].huffCode = s;
				}
				bufferTracker++;
				if (bufferTracker == 1024) {
					size = scan.read(buffer, 0, 1024);
					bufferTracker = 0;
				}
			}


			DecompressTree tree = new DecompressTree();
			for (int i = 0; i < huffCodeArraySize; i++) {
				tree = DecompressTree.addElt(tree, huffCodeArray[i].huffCode, 0, huffCodeArray[i].ch);
			}
			for (int i = 0; i < tmp.length; i++)
				tmp[i] = '\0';

			if (bufferTracker == 1024) {
				size = scan.read(buffer, 0, 1024);
				bufferTracker = 0;
			}

			int index = bufferTracker;
			bufferTracker = 0;
			byte[] outputBuffer = new byte[1024];
			tracker = 0;
			java.io.File n = new java.io.File(name);

			if (n.exists())
				n.delete();

			FileOutputStream output = new FileOutputStream(originalFileName);
			DecompressTree root = tree;
			long count = 0;
			flag = false;
			do {
				while (tree.left != null || tree.right != null) {
					if ((buffer[index] & (1 << 7 - bufferTracker)) == 0)
						tree = tree.left;
					else
						tree = tree.right;
					bufferTracker++;
					if (bufferTracker == 8) {
						bufferTracker = 0;
						index++;
						if (index == 1024) {
							size = scan.read(buffer, 0, 1024);
							index = 0;
							if (size == -1)
								flag = true;
						}
					}
				} // hon bttl3 mn al loop lma tosl leaf
				if (flag)
					break;
				outputBuffer[tracker++] = (byte) tree.ch;
				if (tracker == 1024) {
					output.write(outputBuffer);
					tracker = 0;
				}
				count++;
				tree = root;
				if (count == nbChar)
					break;
			} while (size != -1);
			output.write(outputBuffer, 0, tracker);
			output.close();

		} catch (Exception e) {
		}
	}// Decompress Finish
}
