package application;

public class HuffCode implements Comparable<HuffCode> {
	char ch;
	int counter;
	String huffCode;
	int codeLength;

	public HuffCode(char ch) {
		this.ch = ch;
	}

	public HuffCode(char ch, int counter) {
		this.ch = ch;
		this.counter = counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public void sethuffCode(String huffCode) {
		this.huffCode = huffCode;
	}

	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}

	public char getCh() {
		return ch;
	}

	public int getCounter() {
		return counter;
	}

	public String getHuffCode() {
		return huffCode;
	}

	public int getCodeLength() {
		return codeLength;
	}

	@Override
	public String toString() {
		return "HuffCode{" + "ch=" + (int) ch + ", counter=" + counter
				+ ", huffCode=" + huffCode + ", codeLength=" + codeLength + '}';
	}

	@Override
	public int compareTo(HuffCode t) {
		return huffCode.compareTo(t.huffCode);
	}

}
