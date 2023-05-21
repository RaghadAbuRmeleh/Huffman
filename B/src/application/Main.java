package application;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.table.DefaultTableModel;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Main extends Application {
	Compress compressing;
	Decompress decompressing;
	File file;
	String filePath;
	Label label1 = new Label();
	Label label2 = new Label();
	TextArea ta1=new TextArea();
	TextArea ta2=new TextArea();
	int huffCodeArraySize;
	HuffCode[] huffCodeArray;

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1538,788);

			Image test2 = new Image ("background2.png");
			BackgroundImage bImg2 = new BackgroundImage (test2,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
			Background bground2 = new Background (bImg2);
			root.setBackground(bground2);
			Button start = new Button (" Move To" + "\n" + "Main Page");
			start.setFont(Font.font(50));
			start.setPadding(new Insets(0,0,0,200));
			start.setStyle("-fx-background: transparent; -fx-background-color: transparent;"); 
			root.setCenter(start);

			VBox vb = new VBox();
			HBox hb = new HBox();
			Label lbrowse = new Label (" Browse: ");
			lbrowse.setPrefHeight(45);
			//lbrowse.setBorder(Border.stroke(Color.BLACK));
			//	lbrowse.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
			lbrowse.setFont(Font.font(22));
			TextArea txtbrose = new TextArea ();
			txtbrose.setPrefHeight(5);
			txtbrose.setFont(Font.font(11.8));
			txtbrose.setEditable(false);
			//	txtbrose.setPadding(new Insets (0,0,5,0));
			//txtbrose.setBorder(Border.stroke(Color.BLACK));
			Button bbrowse = new Button();
			//bbrowse.setBorder(Border.stroke(Color.BLACK));
			bbrowse.setPrefHeight(35);
			bbrowse.setPrefWidth(35);
			bbrowse.setPadding(new Insets (2.7,10,5,10));
			hb.setPadding(new Insets (55,60,0,60));
			hb.setAlignment(Pos.BASELINE_CENTER);
			hb.getChildren().addAll(lbrowse,txtbrose,bbrowse);
			HBox hb1 = new HBox();
			hb1.setPadding(new Insets (0,60,0,60));
			hb1.setAlignment(Pos.BASELINE_CENTER);
			hb1.setSpacing(280);
			Button compress = new Button ("  Compress  ");
			compress.setFont(Font.font(24));
			compress.setDisable(true);
			Button decompress = new Button("Decompress");
			decompress.setFont(Font.font(24));
			decompress.setDisable(true);
			hb1.getChildren().addAll(compress,decompress);

			HBox hhs = new HBox ();
			Button headerbtn = new Button("Header");
			headerbtn.setFont(Font.font(24));
			Button headerbtn2 = new Button("Header");
			headerbtn2.setFont(Font.font(24));
			Button statbtn = new Button ("Statistics");
			statbtn.setFont(Font.font(24));
			hhs.setPadding(new Insets (0,60,0,60));
			hhs.setAlignment(Pos.BASELINE_CENTER);
			hhs.setSpacing(80);
			hhs.getChildren().addAll(headerbtn,headerbtn2,statbtn);
			hhs.setVisible(false);
			
		

			vb.getChildren().addAll(hb,hb1, hhs);
			vb.setSpacing(20);

			Image test1 = new Image ("open-folder (1).png");
			BackgroundImage bImg1 = new BackgroundImage (test1,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
			Background bground1 = new Background (bImg1);
			bbrowse.setBackground(bground1);

			start.setOnAction(e -> {
				root.setBackground(new Background(new BackgroundFill(Color.web("#8DBDBF"), new CornerRadii(0), Insets.EMPTY)));
				root.setCenter(null);
				root.setTop(vb);
			});

			bbrowse.setOnAction(e ->{
				root.setCenter(null);
				hhs.setVisible(false);
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open File");File 
				file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					txtbrose.setText(file.getPath());
				}

				if (file.getPath().endsWith(".huf")) {
					decompress.setDisable(false);
					compress.setDisable(true);
				}

				else {
					decompress.setDisable(true);
					compress.setDisable(false);
				}

			});

			compress.setOnAction(l ->{
				root.setCenter(null);
				compress.setDisable(true);
				headerbtn.setVisible(true);
				statbtn.setVisible(true);
				hhs.setVisible(true);
				headerbtn2.setVisible(false);
				compressing = new Compress(txtbrose.getText()); 

				try {
					compressing.Compressing(txtbrose.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}			});


			decompress.setOnAction(l ->{
				root.setCenter(null);
				decompress.setDisable(true);
				headerbtn.setVisible(false);
				headerbtn2.setVisible(true);
				hhs.setVisible(true);
				statbtn.setVisible(false);
				decompressing = new Decompress(txtbrose.getText()); 

				try {
					decompressing.Decompressing(txtbrose.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}		
			});

			TextArea htxt = new TextArea();
			htxt.setMaxHeight(500);
			htxt.setMaxWidth(300);

			headerbtn.setOnAction(o ->{


				htxt.setText("File Name: "+txtbrose.getText()+"\nFile Size: "+compressing.getOriginalSize()+"Byte");
				htxt.setText(htxt.getText()+"\n\nCharacter \t\t Huffman Code \n");
				for (int i =0 ; i <compressing.getHuffCodeArraySize() ; i++)
					htxt.setText(htxt.getText() + 
							Byte.toUnsignedInt((byte) compressing.getHuffCodeArray()[i].ch) + "\t\t\t\t  " +
							compressing.getHuffCodeArray()[i].huffCode + "\n");

				root.setCenter(htxt);
			});
			
			headerbtn2.setOnAction(o ->{

				htxt.setText("File Name: "+txtbrose.getText()+"\nFile Size: "+decompressing.getOriginalSize()+"Byte");
				htxt.setText(htxt.getText()+"\n\nCharacter \t\t Huffman Code \n");
				for (int i =0 ; i <decompressing.getHuffCodeArraySize() ; i++)
					htxt.setText(htxt.getText() + 
							Byte.toUnsignedInt((byte) decompressing.getHuffCodeArray()[i].ch) + "\t\t\t\t  " +
							decompressing.getHuffCodeArray()[i].huffCode + "\n");

				root.setCenter(htxt);
			});

			statbtn.setOnAction(e ->{

				
				
				htxt.setText("ASCII" + " \t " + "Character " +  "\t" + "HuffCode" + "\t\t" + "Freqency" + "\n" );
				for (int i = 0; i < compressing.getHuffCodeArraySize(); i++) 
					htxt.setText(htxt.getText() + 
							Byte.toUnsignedInt((byte) compressing.getHuffCodeArray()[i].ch) + "\t\t\t  " +
							compressing.getHuffCodeArray()[i].ch + "\t\t\t  " +
							compressing.getHuffCodeArray()[i].huffCode +"\t\t\t  " + 
							compressing.getHuffCodeArray()[i].counter + "\n");

			//	root.setCenter(htxt);
				
				
				// Create a TableView object and add it to the scene
				TableView<DataModel> table = new TableView<>();
				//root.getChildren().add(table);

				// Create a TableColumn for each column in the table
				TableColumn<DataModel, Integer> asciiColumn = new TableColumn<>("ASCII");
				TableColumn<DataModel, Integer> characterColumn = new TableColumn<>("Length");
				TableColumn<DataModel, String> huffCodeColumn = new TableColumn<>("HuffCode");
				TableColumn<DataModel, Integer> frequencyColumn = new TableColumn<>("Frequency");

				// Set the cell value factory for each column to extract the data from the data model
				asciiColumn.setCellValueFactory(cellData -> cellData.getValue().asciiProperty().asObject());
				characterColumn.setCellValueFactory(cellData -> cellData.getValue().lengthProperty().asObject());
				huffCodeColumn.setCellValueFactory(cellData -> cellData.getValue().huffCodeProperty());
				frequencyColumn.setCellValueFactory(cellData -> cellData.getValue().frequencyProperty().asObject());

				// Add the columns

				table.getColumns().add(asciiColumn);
				table.getColumns().add(huffCodeColumn);
				table.getColumns().add(characterColumn);
				table.getColumns().add(frequencyColumn);
				// Create a list of data model objects
				List<DataModel> data = new ArrayList<>();
				for (int i = 0; i < compressing.getHuffCodeArraySize(); i++) {
				  int ascii = Byte.toUnsignedInt((byte) compressing.getHuffCodeArray()[i].ch);
				//  char chara = (char)Byte.toUnsignedInt((byte) compressing.getHuffCodeArray()[i].ch);
				  int length = compressing.getHuffCodeArray()[i].codeLength;
				  String huffCode = compressing.getHuffCodeArray()[i].huffCode;
				  int frequency = compressing.getHuffCodeArray()[i].counter;
				  data.add(new DataModel(ascii, huffCode, length, frequency));
				}

				// Set the items of the TableView
				table.setItems(FXCollections.observableArrayList(data));

				root.setCenter(table);
				
				table.setMaxWidth(400);
				table.setMaxHeight(500);
				});


			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}


}
