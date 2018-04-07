import java.util.Scanner;

public class BusinessCardParser
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter number of lines: ");   
	    String[] document = new String[sc.nextInt()];
	    sc.nextLine();

	    for (int i = 0; i < document.length; i++) {
	        document[i] = sc.nextLine();
	    }
	    
	    System.out.println("Input complete - Loading initiated");
		ContactInfo info = getContactInfo(document);
	    
	    System.out.println(info.getName());
	    System.out.println(info.getPhoneNumber());
	    System.out.println(info.getEmailAddress());
	}
	
	public static ContactInfo getContactInfo(String[] document){
		return new ContactInfo(document);
	}
}
