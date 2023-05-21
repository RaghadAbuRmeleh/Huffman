package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DataModel {
	  private final SimpleIntegerProperty ascii;
	//  private final SimpleStringProperty character;
	  private final SimpleStringProperty huffCode;
	  private final SimpleIntegerProperty length;
	  private final SimpleIntegerProperty frequency;
	


	
	
	  public DataModel(int ascii, String huffCode,
				int length, int frequency) {
			super();
			this.ascii = new SimpleIntegerProperty(ascii);
		//	this.character =  new SimpleProperty(character);
			this.huffCode =  new SimpleStringProperty(huffCode);
			this.length =new SimpleIntegerProperty(length);
			this.frequency = new SimpleIntegerProperty(frequency);
		}




	public int getAscii() {
	    return ascii.get();
	  }

	  public SimpleIntegerProperty asciiProperty() {
	    return ascii;
	  }

	/*  public String getLength() {
	    return length.get();
	  }

	  public SimpleStringProperty characterProperty() {
	    return character;
	  }*/

	  public int getLength() {
		    return length.get();
		  }

		  public SimpleIntegerProperty lengthProperty() {
		    return length;
		  }
	  
	  public String getHuffCode() {
	    return huffCode.get();
	  }

	  public SimpleStringProperty huffCodeProperty() {
	    return huffCode;
	  }

	  public int getFrequency() {
	    return frequency.get();
	  }

	  public SimpleIntegerProperty frequencyProperty() {
	    return frequency;
	  }
	}
