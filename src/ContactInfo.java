import java.io.IOException;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreLabel;



public class ContactInfo {
	
	private String[] document;
	private String serializedClassifier = "ner/english.all.3class.distsim.crf.ser.gz";
	private AbstractSequenceClassifier<CoreLabel> classifier;
	
	public ContactInfo(String[] document){
		this.document = document;
		try {
			classifier = CRFClassifier.getClassifier(serializedClassifier);
		} catch (ClassCastException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	
	public String getName(){
		for(String str:document){
			String classified = classifier.classifyToString(str, "slashTags", false);
			if(classified.indexOf("PERSON")!=-1 && !isJobTitle(str.toLowerCase())){
				return "Name: " + str;
			}
			
		}
		return "No name found";
	}
	
	private boolean isJobTitle(String str){
		String [] jobTitles = {"engineer","developer", "software","manager", "senior"};
		for(String job:jobTitles){
			if(str.indexOf(job)!=-1){
				return true;
			}
		}
		return false;
	}
	
	public String getPhoneNumber(){
		for(String str: document){
			String numberBuilder = "";
			for(int i=0;i<str.length();i++){
				char c = str.charAt(i);
				if(c>=48 && c<=57){ //The digits 0-9 correspond to ASCII 48-57
					numberBuilder+=c;
				}
			}
			
			if(numberBuilder.length()>=10){
				str = str.toLowerCase();
				if(str.indexOf("fax")==-1){
					return "Phone: " + numberBuilder;
				}
			}
		}
		return "No phone number found";
	}
	
	public String getEmailAddress(){
		for(String str: document){
			int i=0;
			boolean foundPeriod = false;
			boolean foundAtSign = false;
	
			while(i<str.length() && !foundAtSign){
				if(str.substring(i, i+1).equals("@")){
					foundAtSign = true;
					i++;
					break;
				}
				i++;
			}
			while(i<str.length() && !foundPeriod){
				if(str.substring(i, i+1).equals(".")){
					foundPeriod = true;
					i++;
					break;
				}
				i++;
			}
			
			if(foundPeriod && foundAtSign){
				str = str.toLowerCase();
				return "Email: " + str;
			}
		}
		return "No email found";
	}
}
