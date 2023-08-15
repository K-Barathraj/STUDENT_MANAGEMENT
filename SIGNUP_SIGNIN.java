package student;
import java.sql.*;
import java.util.Scanner;
public class SIGNUP_SIGNIN {
	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private Scanner s;
	SIGNUP_SIGNIN(){
		try {
	       connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/STUDENT_MANAGEMENT","root","2004");
		   statement =connection.createStatement();
		   s = new Scanner(System.in);}
		catch(Exception e) {e.printStackTrace();}}
	
	public void studentSignup() {
		System.out.print("Enter your rollNo: ");
		String roll = s.nextLine();
		System.out.print("Enter password: ");
		String pass = s.nextLine();
		System.out.print("Enter your name: ");
		String name = s.nextLine();
		System.out.print("Enter mobile No:");
		String mob = s.nextLine();
		System.out.print("Enter your Email: ");
		String gmail = s.nextLine();
		try {
		   String sql = "INSERT INTO STUDENT VALUES(?,?,?,?,?)";
		   preparedStatement = connection.prepareStatement(sql);
		   preparedStatement.setString(1, roll);
		   preparedStatement.setString(2, pass);
		   preparedStatement.setString(3, name);
		   preparedStatement.setString(4, mob);
		   preparedStatement.setString(5, gmail);
		   preparedStatement.executeUpdate();
		   System.out.println("Now signin....");}
		catch(Exception e) {e.printStackTrace();}}
	
	public void teacherSignup() {
		System.out.print("Enter your username: ");
		String user = s.nextLine();
		System.out.print("Enter password: ");
		String pass = s.nextLine();
		try {
		   String str = "INSERT INTO TEACHER VALUES(?,?)";
		   preparedStatement = connection.prepareStatement(str);
		   preparedStatement.setString(1, user);
		   preparedStatement.setString(2, pass);
		   preparedStatement.executeUpdate();
		   System.out.println("Now signin....\n");}
		catch(Exception e) {e.printStackTrace();}}
	
	public String studentSignin() {
		System.out.print("Enter your rollNo: ");
		String roll = s.nextLine();
		System.out.print("Enter Password: ");
		String pass = s.nextLine();
		try {
			String sql = "SELECT NAMEE FROM STUDENT WHERE ROLL_NO = \""+roll+"\" AND PASSWORD=\""+pass+"\"";
   			resultSet = statement.executeQuery(sql);
   			if(resultSet.next()) { System.out.println("Welcome "+ resultSet.getString(1) );  return roll;}
		}catch(Exception e) {e.printStackTrace();}
		return "0";}
	
	public String teacherSignin() {
		System.out.print("Enter your UserName: ");
		String name = s.nextLine();
		System.out.print("Enter Password: ");
		String pass = s.nextLine();
		try {
			String sql = "SELECT * FROM TEACHER WHERE USERNAME = \""+name+"\" AND PASSWORD=\""+pass+"\"";
   			resultSet = statement.executeQuery(sql);
   			if(resultSet.next()) System.out.println("Welcome "+ name);return name;}
			catch(Exception e) {e.printStackTrace();}
		return "0";}
	
}


