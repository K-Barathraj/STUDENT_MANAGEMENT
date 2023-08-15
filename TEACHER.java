package student;
import java.sql.*;
import java.util.Scanner;
public class TEACHER {
	    private String name;
	    private Connection connection1, connection2;
	    private Statement statement1, statement2;
	    private PreparedStatement ps;
	    private ResultSet  attend, students, notifi, traverse;
	    private Scanner s;
	  public TEACHER(){
		   try {
	          connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/STUDENT_MANAGEMENT","root","2004");
		      statement1 =connection1.createStatement();
		      connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/STUDENT_MANAGEMENT","root","2004");
		      statement2 =connection2.createStatement();
		      s = new Scanner(System.in);}
		   catch(Exception e) {e.printStackTrace();}}

	    public void menu(String n) {
	    	 name = n;
	    	 System.out.println("1.Take attendence\n2.Mark Absents\n3.clr attendence\n4.give attendence\n5.Notify students\n6.Delete notifications\n"
	    	 		+ "7.change password\n0.signout");
	    	 while(true) {
	         System.out.print("\nChoice? ");
	    	 int choice = s.nextInt();
	    	 switch(choice) {
	    	    case 1: takeAttendence(); break;
	    	    case 2: markAbsents(); break;
	    	    case 3: clearAttendence(); break;
	    	    case 4: giveAttendence(); break;
	    	    case 5: Notify(name); break;
	    	    case 6: deleteNotify(name); break;
	    	    case 7: changepassword(name); break;
	    	    case 0: return;}}}
	     
	     private  void takeAttendence() {
	    	 System.out.println("Enter date: ");
	    	 String date = s.next();
	    	 System.out.println("Enter hour: ");
	    	 String hr = s.next();
	    	 System.out.println("1.present\n0.absent");
	    	 try {
	    	 students = statement2.executeQuery("select * from student");
	    	 while(students.next()) {
	    		 int roll = Integer.parseInt(students.getString(1).substring(6,8));
	    		 boolean  value = true;
	    			 String result = "";
	     	         System.out.print(students.getString(3)+ "  RollNo " +roll+" :");
	     		     int r = s.nextInt();
	     		     if(r==1) { result="PRESENT";}
	    		     else {result="ABSENT";}
	    			
	//check whether new or already there
	     		     String query="SELECT * FROM ATTEND"+roll;
	    			 attend = statement1.executeQuery(query);
	    			 while(attend.next()) {
	    				 if(attend.getString(1).equals(date))
	    					 value = false;}
	//create if needs
	    			 if(value) {
	    			 ps=connection1.prepareStatement("insert into attend"+roll+ " values(?,'NE','NE','NE','NE','NE')");
	      			 ps.setString(1, date);
	   			     ps.executeUpdate();}
	//update absent or present
	    				String q = "UPDATE ATTEND"+roll+" SET HR"+hr+" = ? WHERE _DATE = ?";
	    			    ps=connection1.prepareStatement(q);
	    			    ps.setString(1, result);
	    			    ps.setString(2, date);
	    			    ps.executeUpdate();}}
	    		 catch(Exception e) {e.printStackTrace();}}
	     
	    private  void markAbsents(){
	    	 System.out.println("Enter date: ");
	    	 String date = s.next();
	    	 System.out.println("Enter hour: ");
	    	 int hr = s.nextInt();
	    	 System.out.print("Enter absents: ");
	    	 boolean   value=true;
	    	 try {
	//check presence of date
		        attend = statement1.executeQuery("select * from ATTEND1");
		        while(attend.next()) {
			    if(attend.getString(1).equals(date))
				      value = false;}
	//to create if needs
		        if(value) {
		             attend = statement2.executeQuery("select * from student");
		    	     while(attend.next()) {
		    	     int roll = Integer.parseInt(attend.getString(1).substring(6,8));
		        	 ps=connection1.prepareStatement("insert into attend"+roll+ " values(?,'NE','NE','NE','NE','NE')");
	      			 ps.setString(1, date);
	   			     ps.executeUpdate();}}
	//update absents	        
		            students = statement2.executeQuery("select * from student");
		    	    while(students.next()) {
		        	int rollNo = s.nextInt();
		        	if(rollNo<0) {break;}
		        	String sql = "UPDATE ATTEND"+rollNo+" SET HR"+hr+" = ? WHERE _DATE = ?";
	 			    ps=connection1.prepareStatement(sql);
	 			    ps.setString(1,  "ABSENT");
	 			    ps.setString(2, date);
	 			    ps.executeUpdate();}
	//others present	    	    
		    	    students = statement2.executeQuery("select * from student");
		    	    hr++;
		       	    while(students.next()) {
		        	int roll = Integer.parseInt(students.getString(1).substring(6,8));
		        	attend = statement1.executeQuery("select * from ATTEND"+roll);
		        	while(attend.next()){
		        	if(attend.getString(hr).equals("NE")&&attend.getString(1).equals(date)) {
		        		String q = "UPDATE ATTEND"+roll+" SET HR"+(hr-1)+" = ? WHERE _DATE = ?";
		 			    ps=connection1.prepareStatement(q);
		 			    ps.setString(1,  "PRESENT");
		 			    ps.setString(2, date);
		 			    ps.executeUpdate();}}}}
		         	 catch(Exception e) {e.printStackTrace();}}
	    		
	    	 
	     
	    private void clearAttendence() {
	    	 for(int i=1; i<=50; i++) {
	    		 String str = "DELETE FROM ATTEND"+i;
	    		 try {
	    		 ps=connection1.prepareStatement(str);
	    		 ps.executeUpdate();}
	    		 catch(Exception e) {e.printStackTrace();}}}
	    	 
	     
	     
	   private  void giveAttendence() {
	    	try {
	    	 students = statement2.executeQuery("select * from student");
	    	 System.out.println("Enter the date: ");
	    	 String date = s.next();
	    	 while(students.next()) {
	    		 int rollNo = Integer.parseInt(students.getString(1).substring(6,8));
	    		String str = "INSERT INTO ATTEND"+rollNo+" values(?,?,?,?,?,?)";
	    	    ps = connection1.prepareStatement(str);
	    	    ps.setString(1, date);
	    	    ps.setString(2, "PRESENT");
	    	    ps.setString(3, "PRESENT");
	    	    ps.setString(4, "PRESENT");
	    	    ps.setString(5, "PRESENT");
	    	    ps.setString(6, "PRESENT");
	    	    ps.executeUpdate();}}
	    	catch(Exception e) {e.printStackTrace();}}
	    
	    
	    
	    
	   private void Notify(String name) {
	    	try {
	    	 System.out.println("Enter Message:  ");
	    	 s.nextLine();
	    	 String msg = s.nextLine();
	    	 notifi = statement1.executeQuery("SELECT * FROM NOTIFI");
	    	 int count =1;
	    	 while(notifi.next())
	    		 count++;
	   		 ps = connection1.prepareStatement("INSERT INTO NOTIFI  VALUES (?,?,?)");
	   		 ps.setInt(1, count);
	   		 ps.setString(2, msg );
	   		 ps.setString(3, name);
	   		 ps.executeUpdate();
	   		 System.out.println("Notified Successfully");
	    	}catch(Exception e) {e.printStackTrace();}}
	    
	   private void deleteNotify(String name){
	    	try {
	        notifi = statement1.executeQuery("SELECT * FROM NOTIFI");
	    	while(notifi.next()) {
	    		System.out.println(
	    				notifi.getInt(1)
	    				+"\t"+
	    				notifi.getString(2)+
	    				"\t"+
	    				notifi.getString(3));}
	    	System.out.println("Enter the index to delete: ");
	    	int index = s.nextInt();
	    	ps=connection1.prepareStatement("DELETE FROM NOTIFI WHERE _NO = ?");
	    	ps.setInt(1, index);
	    	ps.executeUpdate();
	    	System.out.println("Deleted Sucessfully");
	    	}catch(Exception e) {e.printStackTrace();}}
	    

	     private void changepassword(String name) {
	    	 try {
	    	 boolean access = verify(name);
	    	 if(!access) 
	    		 System.out.println("Wrong Password..");
	    	 if(access) {
	    		 while(true) {
	    		 System.out.print("Enter your new Password: ");
	    		 String pass1 = s.next();
	    		 System.out.print("Re Enter the password: ");
	    		 String pass2 = s.next();
	    		 if(!(pass1.equals(pass2))) {
	    			 System.out.println("Password mismatch: "); continue;}
	    		 ps = connection1.prepareStatement("UPDATE TEACHER SET PASSWORD = ? WHERE USERNAME = ?");
	    		 ps.setString(1, pass1);
	    		 ps.setString(2, name);
	    		 ps.executeUpdate();
	    		 System.out.println("Updated successfully...");
	    		 break;}}
	    	 }catch(Exception e) {e.printStackTrace();}}


	     private boolean verify(String name) {
	    	  System.out.println("Enter your old password");
	    	  String pass = s.next();
	   		try {
	   			traverse = statement1.executeQuery("SELECT * FROM TEACHER WHERE USERNAME = \""+name+"\" AND password=\""+pass+"\"");
	   			while(traverse.next())  return true;
	   		   }catch(Exception e) {e.printStackTrace();}
	   	    return false;}
	}
	     