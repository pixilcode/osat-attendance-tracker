package model;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import nu.xom.*;

public class Roster {
	
	//The location of the xml file
	String location;
	ArrayList<Person> members;
	
	public Roster(String location) {
		this.location = location;
		readXMLfile(location);
	}

	private void readXMLfile(String location) {
		
	}
	
	//Throws IOException so that GUI can alert person of error
	public void writeXMLfile() throws IOException {
		
		//Create the root element
		Element roster = new Element("roster");
		
		//Add XML Schema attributes (xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" and xsi:schemaLocation="urn:MyData http://www.example.com/MyData.xsd") 
		Attribute schemaInstance = new Attribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		Attribute schemaLocation = new Attribute("xsi:noNamespaceSchemaLocation", "roster.xsd");
		
		roster.addAttribute(schemaInstance);
		roster.addAttribute(schemaLocation);
		
		//Add each person into the root element
		for(int i = 0; i < members.size(); i++) {
			roster.appendChild(members.get(i).toXMLelement());
		}
		
		//Create a document to hold the root element
		Document doc = new Document(roster);
		
		//Write the XML file
		try {
			
			//Format the file
			FileOutputStream fos = new FileOutputStream(location);
			Serializer output = new Serializer(fos, "ISO-8859-1");
			output.setIndent(2);
			output.write(doc);
			
		} catch (UnsupportedEncodingException e) {
			
			//If the encoding is not supported, don't format the file
			FileWriter fw = new FileWriter(location);
			BufferedWriter out = new BufferedWriter(fw);
			out.write(doc.toXML());
			out.close();
			
		}
		
	}
	
}
