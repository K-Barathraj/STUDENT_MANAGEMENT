package student;
import java.sql.*;
import java.util.Scanner;

public class STUDENT {
	    private String roll;
		private Connection connection;
		private Statement statement;
		private PreparedStatement preparedStatement;
		private ResultSet studentInfo;
		private ResultSet attend;
		private Scanner s;
		public STUDENT(){
			      try {
		          connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/STUDENT_MANAGEMENT","root","2004");
			      statement =connection.createStatement();
			      s = new Scanner(System.in);}
			      catch(Exception e) { e.printStackTrace();}}

		public void studentInfo(String r) {
			  try {
			  roll =  r;
			  System.out.print("1.Profile\n2.Everyday Attendence \n3.overall Attendence\n4.Attendence Percentage\n5.Notificatons\n6.change password\n0.signout");
			  while(true) {
				  System.out.print("\nChoice? ");
				  int choice = s.nextInt();
			  switch(choice) {
			     case 1: profile(); break;
			     case 2: everyDayattendence();break;
			     case 3: attendence(); break;
			     case 4: percentage(); break;
			     case 5: notifications();break;
			     case 6: changepassword(); break;
			     default: System.out.println("Invalid Choice...");}}}
		         catch(Exception e) { e.printStackTrace();}}
		      
			
		 private void profile() {
			      try {
				  studentInfo = statement.executeQuery("select * FROM STUDENT");
				  while(studentInfo.next()){
					  if(studentInfo.getString(1).equals(roll)) {
						   System.out.println();
				           System.out.println(
				        		"Name : "+ studentInfo.getString(3)+ 
				                "\nRoll No: "+ studentInfo.getString(1)+
				                 "\nMobile: "+ studentInfo.getString(4)+
				                  "\nGmail: "+ studentInfo.getString(5));
				           System.out.println();}}}
			      catch(Exception e) { e.printStackTrace();}}
			  
		  
		 private void everyDayattendence() {
			      try {
				  int rollNo =Integer.parseInt(roll.substring(6, 8));
				  String s = "SELECT * FROM ATTEND"+rollNo;
				  attend = statement.executeQuery(s);
				  if(!attend.next()) {System.out.println("\tNo data found...");return;}
				  attend = statement.executeQuery(s);
				  System.out.println("\n");
				  System.out.println("DATE\t\tHour1\tHour2\tHour3\tHour4\tHour5\n");
				     while(attend.next()) {
				     System.out.println(
		        	     attend.getString(1)+"\t"+
		        	     attend.getString(2)+"\t"+
		        	     attend.getString(3)+"\t"+
		        	     attend.getString(4)+"\t"+
		        	     attend.getString(5)+"\t"+
		        	     attend.getString(6));}}
			      catch(Exception e) { e.printStackTrace();}}
		  
		private  void attendence() {
			  try {
			  int rollNo = Integer.parseInt(roll.substring(6,8));
			  String sql = "SELECT * FROM ATTEND"+rollNo;
			  attend = statement.executeQuery(sql);
			  if(!attend.next()) {System.out.println("\tNo data found...");return;}
			  attend = statement.executeQuery(sql);
			  String p = "PRESENT";
			  System.out.println("\tDATE\t\tResult");
			  while(attend.next()) {
				  String result =" ";
				     
				      if(attend.getString(2).equals(p)&&attend.getString(3).equals(p)&&attend.getString(4).equals(p)&&attend.getString(5).equals(p)&&attend.getString(6).equals(p))
				    	  result ="\tFull day";
					  else if(attend.getString(2).equals(p)&&attend.getString(3).equals(p)&&attend.getString(4).equals(p)||attend.getString(5).equals(p)&&attend.getString(6).equals(p))
						  result = "\tHalf day";
					  else
						  result = "\tabsent";
					  System.out.println(attend.getString(1)+"\t"+result);}}
			  catch(Exception e) { e.printStackTrace();}}
			  
		 private void percentage() {
			      try {
			      int rollNo = Integer.parseInt(roll.substring(6,8));
				  String sql = "SELECT * FROM ATTEND"+rollNo;
				  attend = statement.executeQuery(sql);
				  if(!attend.next()) {System.out.println("\tNo data found...");return;}
				  attend = statement.executeQuery(sql);
				  String p = "PRESENT";
				  float attends=0;
				  float totaldays=0;
				  while(attend.next()) {
					      totaldays++;
						  if(attend.getString(2).equals(p)&&attend.getString(3).equals(p)&&attend.getString(4).equals(p)&&attend.getString(5).equals(p)&&attend.getString(6).equals(p))
						  attends++;
					      else if(attend.getString(2).equals(p)&&attend.getString(3).equals(p)&&attend.getString(4).equals(p)||attend.getString(5).equals(p)&&attend.getString(6).equals(p))
						  attends+=0.5;}
				  int per = (int)((attends/totaldays)*1000);
				  float percentage = per/10;
				  
				  System.out.println("Percentage--->>>"+percentage);
				  System.out.print("Remarks: ");
				  if(percentage>95) {System.out.println("Excellent");}
				  else if(percentage>80) {System.out.println("Good");}
				  else if(percentage>75) {System.out.println("poor");}
				  else {System.out.println("Not Eligible for exam");}}
			      catch(Exception e) { e.printStackTrace();}}
		  
		 private void notifications() {
			  try {
			  ResultSet notify = statement.executeQuery("SELECT * FROM NOTIFI"); 
			  if(!notify.next()) {
				  System.out.println("  ---->>>>No notifications...");return;}
			  notify = statement.executeQuery("SELECT * FROM NOTIFI");
			  System.out.println("\nNotifications");
			  while(notify.next()) {
				  System.out.println("   ---->>>> "+notify.getString(2)+"     -"+notify.getString(3));}}
			  catch(Exception e) { e.printStackTrace();}}
			  
		private  void changepassword() {
				try {
			    	 boolean access = verify();
			    	 if(!access) 
			    		 System.out.println("Wrong Password..");
			    	 else {
			    		 while(true) {
			    		 System.out.print("Enter your new Password: ");
			    		 String pass1 = s.next();
			    		 System.out.print("Re Enter the password: ");
			    		 String pass2 = s.next();
			    		 if(!(pass1.equals(pass2))) {
			    			 System.out.println("Password mismatch: "); continue;}
			    		 preparedStatement =connection.prepareStatement("UPDATE STUDENT SET PASSWORD = ? WHERE ROLL_NO = ?");
			    		 preparedStatement.setString(1, pass1);
			    		 preparedStatement.setString(2, roll);
			    		 preparedStatement.executeUpdate();
			    		 System.out.println("Updated successfully...");
			    		 break;}}}
				catch(Exception e) { e.printStackTrace();}}
			     

			 private boolean verify() {
			    	  try {
			    	  System.out.println("Enter your old password");
			    	  String pass = s.next();
			   			String sql = "SELECT * FROM STUDENT WHERE ROLL_NO = \""+roll+"\" AND password=\""+pass+"\"";
			   			studentInfo = statement.executeQuery(sql);
			   			if(studentInfo.next()) return true;}
			   			catch(Exception e) { e.printStackTrace();}
			   	    return false;}}
		  

