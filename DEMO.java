package student;
import java.util.*;
public class DEMO {
 		public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		SIGNUP_SIGNIN signupSignin = new SIGNUP_SIGNIN();
	    STUDENT student = new STUDENT();
	    TEACHER teacher = new TEACHER();
	    while(true) {
	    System.out.println("1.Student...\n2.Teacher....\n0.Exit...");
	    System.out.print("Choice? ");
	    int choice = s.nextInt();
	    if(choice==0) {System.out.println("Thank You....");System.exit(0);}
	    String roll="0";
	    if(choice==1) {
	    	System.out.println("\t1.signin\n\t2.signup");
	    	System.out.print("\tChoice? ");
	    	int ch = s.nextInt();
	    	if(ch==1) {
	    	    roll = signupSignin.studentSignin();
	    	    if(roll=="0") {System.out.println("invalid RollNo or password...");}
	    	    else  student.studentInfo(roll);}
	    	if(ch==2)
	    		signupSignin.studentSignup();}
	    if(choice==2) {
	    	String str="0";
	    	System.out.println("\t1.signin\n\t2.signup");
	    	System.out.print("\tChoice? " );
	    	int ch = s.nextInt();
	    	if(ch==1) {
	    		str = signupSignin.teacherSignin();
	    	    if(str=="0") {System.out.println("Invalid username or password.....");}
	    	    else teacher.menu(str);}
     	if(ch==2)
     		signupSignin.teacherSignup();}}
 		}}
	
 			
 			
 			
 			

	
