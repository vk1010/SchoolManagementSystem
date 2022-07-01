package org.school;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class Main {
	
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static Connection conn=null;
	static int teacherid,studentid;
	public static int home()
	{
		int choice=0;
		do {
		try {
			System.out.println("\t\t1)Admin Login.\r\n"
					+ "\t\t2)Teacher Login.\r\n"
					+ "\t\t3)Student Login.\r\n"
					+ "\t\t4)Exit.");
			System.out.println("Enter Your Choice :");
			choice=Integer.parseInt(br.readLine());
			switch (choice) {
			case 1:
				if(adminLogin())
				{
					adminDashboard();
				}			  
				else
				{
					throw new Exception("User not authenticated!!");
				}
				break;
			case 2:
				if(teacherLogin())
				{
					teacherDashboard();
				}			  
				else
				{
					throw new Exception("User not authenticated!!");
				}
				break;
			case 3:
				if(studentLogin())
				{
					studentDashboard();
				}			  
				else
				{
					throw new Exception("User not authenticated!!");
				}
				break;
			case 4:
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + choice);
			}
		}
		catch(IllegalArgumentException e)
		{
			System.out.println(e);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		}while(choice!=4);
		System.out.println("====================================================================");
		return 0;
		
	}
	//Admin Login
	synchronized public static boolean adminLogin() throws NumberFormatException, IOException
	{
		
		System.out.println("====================================================================");
		System.out.println("Admin Login :");
		System.out.println("Enter Admin ID:");
		long AdminId=Integer.parseInt(br.readLine());
		System.out.println("Enter Admin Password:");
		String password=br.readLine();
		if(AdminId==123 &&   password.equals("Admin"))
			return true;
		else
			return false;
	}
	
	synchronized public static boolean teacherLogin() throws NumberFormatException, IOException, SQLException
	{
		
		System.out.println("====================================================================");
		System.out.println("Teacher Login :");
		System.out.println("Enter Teacher ID:");
		int teacherId=Integer.parseInt(br.readLine());
		System.out.println("Enter Teacher Password:");
		String teacherPassword=br.readLine();
		String st="select count(*),password from Teacher Where id="+teacherId;
		Statement stmt=conn.createStatement();
        ResultSet result=stmt.executeQuery(st);
        int flag=0;
        while(result.next())
        {
        	flag = 1;
        	if(result.getInt(1)==1 && teacherPassword.equals(result.getString(2)))
        	{
        		teacherid=teacherId;
        		return true;
        	}
        	else
        		return false;
        }
        if(flag==0)
        	System.out.println("Teacher is not exist..!!");
        return false;
	}
	synchronized public static boolean studentLogin() throws NumberFormatException, IOException, SQLException
	{
		
		System.out.println("====================================================================");
		System.out.println("Student Login :");
		System.out.println("Enter Student ID:");
		int studentId=Integer.parseInt(br.readLine());
		System.out.println("Enter Student Password:");
		String studentPassword=br.readLine();
		String st="select count(*),password from Student Where id="+studentId;
		Statement stmt=conn.createStatement();
        ResultSet result=stmt.executeQuery(st);
        int flag=0;
        while(result.next())
        {
        	flag = 1;
        	if(result.getInt(1)==1 && studentPassword.equals(result.getString(2)))
        	{
        		studentid=studentId;
        		return true;
        	}
        	else
        		return false;
        }
        if(flag==0)
        	System.out.println("Student is not exist..!!");
        return false;
	}
	
	static int adminDashboard()
	{
		System.out.println("====================================================================");
		System.out.println("                     Administrator Portal");
		System.out.println("====================================================================");
		int choice = 0;
		do
		{
			try {
			System.out.println("Dashbord:");
			System.out.println("\t\t 1)Manage Teacher.\r\n"
					+ "\t\t 2)Manage Student.\r\n"
					+ "\t\t 3)Display Student And Teacher Record.\r\n"
					+ "\t\t 4)Display School Status.\r\n"
					+ "\t\t 5)Logout.");
			System.out.println("====================================================================");
			System.out.println("Enter your choice:");
			choice=Integer.parseInt(br.readLine());
			
			switch(choice)
			{
				case 1: 
						manageTeacher();
					    break;
				case 2:
						manageStudent();
						break;
				case 3:
						display();
						break;
				case 4:
						schoolStatus();
						break;
				case 5:
						break;
			    default:
			    	throw new Exception("Wrong choice!!"); 		   
			}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}while(choice!=5);
			
		System.out.println("====================================================================");
		System.out.println("Bye...");
		System.out.println("====================================================================");
		return 0;
	}
	
	public static void teacherDashboard()
	{
		System.out.println("====================================================================");
		System.out.println("                     Teacher Portal\r\n");
		System.out.println("====================================================================");
		int choice = 0;
		do
		{
			try {
			System.out.println("Dashbord:");
			System.out.println("\t\t 1)Check Salary Status\r\n"
					+ "\t\t 2)Change Password\r\n"
					+ "\t\t 3)Logout");
			System.out.println("====================================================================");
			System.out.println("Enter your choice:");
			choice=Integer.parseInt(br.readLine());
			
			switch(choice)
			{
				case 1: 
						teacherStatus();
					    break;
				case 2:
						changeTeacherPassword();
						break;
				case 3:
						break;
			    default:
			    	throw new Exception("Wrong choice!!"); 		   
			}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}while(choice!=3);
			
		System.out.println("====================================================================");
		System.out.println("Bye...");
		System.out.println("====================================================================");
		return ;
	}
	
	public static void studentDashboard()
	{
		System.out.println("====================================================================");
		System.out.println("                     Student Portal\r\n");
		System.out.println("====================================================================");
		int choice = 0;
		do
		{
			try {
			System.out.println("Dashbord:");
			System.out.println("\t\t 1)Check fees Status\r\n"
					+ "\t\t 2)Change Password\r\n"
					+ "\t\t 3)Logout");
			System.out.println("====================================================================");
			System.out.println("Enter your choice:");
			choice=Integer.parseInt(br.readLine());
			
			switch(choice)
			{
				case 1: 
						studentStatus();
					    break;
				case 2:
						changeStudentPassword();
						break;
				case 3:
						break;
			    default:
			    	throw new Exception("Wrong choice!!"); 		   
			}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}while(choice!=3);
			
		System.out.println("====================================================================");
		System.out.println("Bye...");
		System.out.println("====================================================================");
		return ;
	}
	//
	public static int manageTeacher()
	{
		System.out.println("====================================================================");
		int choice = 0;
		do {
		try {
			System.out.println("Menu:");
			System.out.println("\t\t 1)Add Teacher.\r\n"
					+ "\t\t 2)Update Teacher.\r\n"
					+ "\t\t 3)Delete Teacher.\r\n"
					+ "\t\t 4)Search Teacher.\r\n"
					+ "\t\t 5)Pay the Salary\r\n"
					+ "\t\t 6)Payment Status.\r\n"
					+ "\t\t 7)Return back.");
			System.out.println("====================================================================");
			System.out.println("Enter your choice:");
			choice=Integer.parseInt(br.readLine());
			
			switch(choice)
			{
				case 1: 
						addTeacher();
					    break;
				case 2:
						updateTeacher();
						break;
				case 3:
						deleteTeacher();
						break;
				case 4:
						searchTeacher();
						break;
				case 5:
						payTeacher();
						break;
				case 6:
						paymentStatus();
						break;
				case 7:
						break;
			    default:
			    	throw new Exception("Wrong choice!!"); 		   
			
			}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}while(choice!=7);
		return 0;
	}

	//
	public static int manageStudent()
	{
		System.out.println("====================================================================");
		int choice=0;
		do {
		try {
			System.out.println("Menu:");
			System.out.println("\t\t 1)Add Student.\r\n"
					+ "\t\t 2)Update Student.\r\n"
					+ "\t\t 3)Delete Student.\r\n" 
					+ "\t\t 4)Search Student.\r\n"
					+ "\t\t 5)Receive Fees.\r\n"
					+ "\t\t 6)Fees Status.\r\n"
					+ "\t\t 7)Return back.");
			System.out.println("====================================================================");
			System.out.println("Enter your choice:");
			choice=Integer.parseInt(br.readLine());
			
			switch(choice)
			{
				case 1: 
						addStudent();
					    break;
				case 2:
						updateStudent();
						break;
				case 3:
						deleteStudent();
						break;
				case 4:
						searchStudent();
						break;
				case 5:
						receiveFees();
						break;
				case 6:
						feesStatus();
						break;
				case 7:
						break;
			    default:
			    	throw new Exception("Wrong choice!!"); 		   
			
			}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}while(choice!=7);
		return 0;
	}
	
	
	public static void display()
	{
		System.out.println("====================================================================");
		int choice=0;
		do {
		try {
			System.out.println("Menu:");
			System.out.println("\t\t 1)Display Teachers Record.\r\n"
					+ "\t\t 2)Display Students Record.\r\n"
					+ "\t\t 3)Return back.");
			System.out.println("====================================================================");
			System.out.println("Enter your choice:");
			choice=Integer.parseInt(br.readLine());
			
			switch(choice)
			{
				case 1: 
						displayTeacher();
					    break;
				case 2:
						displayStudent();
						break;
				case 3:
						break;
			    default:
			    	throw new Exception("Wrong choice!!"); 		   
			
			}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}while(choice!=3);
		return;
	}
	
	public static void schoolStatus()
	{	
		System.out.println("====================================================================");
		int choice=0;
		do {
		try {
			System.out.println("Menu:");
			System.out.println("\t\t 1)Display Number of Student.\r\n"
					+ "\t\t 2)Display Number of Teacher.\r\n"
					+ "\t\t 3)Display Profit and Loss.\r\n"
					+ "\t\t 4)Return back.");
			System.out.println("====================================================================");
			System.out.println("Enter your choice:");
			choice=Integer.parseInt(br.readLine());
			
			switch(choice)
			{
				case 1: 
						countStudent();
					    break;
				case 2:
						countTeacher();
						break;
				case 3:
						profitLoss();
				case 4:
						break;
			    default:
			    	throw new Exception("Wrong choice!!"); 		   
			
			}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}while(choice!=4);
		return;
	}
	//
	public static void addTeacher() throws NumberFormatException, IOException
	{
		System.out.println("====================================================================");
		try {
			System.out.println("Enter Teacher Id: ");
			int teacherId=Integer.parseInt(br.readLine());
			System.out.println("Enter Teacher Name: ");
			String teacherName=br.readLine();
			System.out.println("Enter Teacher Salary: ");
			double teacherSalary=Double.parseDouble(br.readLine());
			try
			{
			PreparedStatement stmt=conn.prepareStatement("Insert into Teacher(id,name,salary) values(?,?,?)");
	        stmt.setInt(1, teacherId);
	        stmt.setString(2, teacherName);
	        stmt.setDouble(3, teacherSalary);
	        
	        if(stmt.executeUpdate()>0)
	        {
	            System.out.println("Teacher record inserted successfully..!!");
	        }
	        else
	        {
	            System.out.println("teacher record not inserted!!..");
	        }
			}catch(SQLIntegrityConstraintViolationException e)
			{
				System.out.println("Teacher id '"+teacherId +"' is Already exist..!!");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	
		System.out.println("====================================================================");
	}
	
	public static void addStudent() throws NumberFormatException, IOException
	{
		System.out.println("====================================================================");
		try {
			System.out.println("Enter Student Id: ");
			int studentId=Integer.parseInt(br.readLine());
			System.out.println("Enter Student Name: ");
			String studentName=br.readLine();
			System.out.println("Enter Student Grade: ");
			int studentGrade=Integer.parseInt(br.readLine());
			try
			{
			PreparedStatement stmt=conn.prepareStatement("Insert into Student(id,name,grade) values(?,?,?)");
	        stmt.setInt(1, studentId);
	        stmt.setString(2, studentName);
	        stmt.setInt(3, studentGrade);
	        if(stmt.executeUpdate()>0)
	        {
	            System.out.println("Student record inserted successfully..!!");
	        }
	        else
	        {
	            System.out.println("studunt record not inserted!!..");
	        }
		}
		catch(SQLIntegrityConstraintViolationException e)
		{
			System.out.println("Student id '"+studentId +"' is Already exist..!!");
		}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	
		System.out.println("====================================================================");
	}
	
	public static void updateTeacher()
	{
		System.out.println("====================================================================");
		try {
			System.out.println("Enter teacher id:");
			int teacherId=Integer.parseInt(br.readLine());
			    String st="select * from Teacher where id="+teacherId;
				Statement stmt=conn.createStatement();
		        ResultSet result=stmt.executeQuery(st);
		        System.out.println("Teacher Information:");
		        System.out.printf("%5s%10s%10s%15s","id","Name","Salary","SalaryEarn");
		        System.out.println("\n----------------------------------------------------");
		        int flag=0,choice=0;
		        while(result.next())
		        {
		        	flag=1;
		        	System.out.printf("%5s%10s%10s%15s",""+result.getInt(1),""+result.getString(2),""+result.getDouble(3),""+result.getDouble(4));
		        	System.out.println("\ndue you want to update Salary of Teacher, if yes then press 1,else press 0");
		        	choice=Integer.parseInt(br.readLine());
		        }		
		        if(flag==1)
		        {
					if(choice==1)
					{
						System.out.println("Enter Teacher Salary: ");
					
					double teacherSalary=Double.parseDouble(br.readLine());
					PreparedStatement stmt1=conn.prepareStatement("update Teacher set salary=? where id=? ");
			        stmt1.setDouble(1,teacherSalary);
					stmt1.setInt(2, teacherId);
			        if(stmt1.executeUpdate()>0)
			        {
			            System.out.println("Teacher record updated successfully..!!");
			        }
			        else
			        {
			            System.out.println("Teacher record not updated..!!");
			        }
					}
		        }
				else
		        	System.out.println("Teacher doesn't exist..!!");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("\n====================================================================");
		
	}
	
	public static void updateStudent()
	{
		System.out.println("====================================================================");
		try {
			System.out.println("Enter student id:");
			int studentId=Integer.parseInt(br.readLine());
			    String st="select * from Student where id="+studentId;
				Statement stmt=conn.createStatement();
		        ResultSet result=stmt.executeQuery(st);
		        System.out.println("Student Information:");
		        System.out.printf("%5s%15s%10s%15s%15s","id","Name","Grade","Fees Paid","Total Fees");
		        System.out.println("\n--------------------------------------------------------------");
		        int flag=0,choice=0;
		        while(result.next())
		        {
		        	flag = 1;
		        	System.out.printf("%5s%15s%10s%15s%15s",""+result.getInt(1),""+result.getString(2),""+result.getInt(3),""+result.getInt(4),""+result.getInt(5));
		        	System.out.println("\ndue you want to update: \n\t1)Grade of Student\n\t2)Fees of student\n\t"
		        			+ "3)Grade and Fees of Student\n\t4)No");
		        	System.out.println("Enter your choice:");
		        	choice=Integer.parseInt(br.readLine());
		        }		
		        if(flag==1)
		        {
					switch(choice)
					{
					case 1:
						System.out.println("Enter Studet Grade to update:");
						String studentGrade=br.readLine();
						PreparedStatement stmt1=conn.prepareStatement("update Student set grade=? where id=? ");
				        stmt1.setString(1,studentGrade);
						stmt1.setInt(2, studentId);
				        if(stmt1.executeUpdate()>0)
				        {
				            System.out.println("Student Grade updated successfully..!!");
				        }
				        else
				        {
				            System.out.println("Student Grade not updated..!!");
				        }
						break;
					case 2:
						System.out.println("Enter Student Fees to update: ");
						Double studentFees=Double.parseDouble(br.readLine());
						PreparedStatement stmt2=conn.prepareStatement("update Student set feesTotal=? where id=? ");
				        stmt2.setDouble(1,studentFees);
						stmt2.setInt(2, studentId);
				        if(stmt2.executeUpdate()>0)
				        {
				            System.out.println("Student Fees updated successfully..!!");
				        }
				        else
				        {
				            System.out.println("Student Fees not updated..!!");
				        }
						break;
					case 3:
						System.out.println("Enter Student Grade to update: ");
						String studentGrade1=br.readLine();
						System.out.println("Enter Student Fees to update: ");
						Double studentFees1=Double.parseDouble(br.readLine());
						PreparedStatement stmt3=conn.prepareStatement("update Student set grade=?,feesTotal=? where id=? ");
				        stmt3.setString(1,studentGrade1);
				        stmt3.setDouble(2, studentFees1);
						stmt3.setInt(3, studentId);
				        if(stmt3.executeUpdate()>0)
				        {
				            System.out.println("Student Grade and Fees updated successfully..!!");
				        }
				        else
				        {
				            System.out.println("Student Grade and Fees not updated..!!");
				        }
						break;
					case 4:
						break;
					}
		        	
		        }
				else
					System.out.println("Student doesn't exist..!!");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("\n====================================================================");
		
	}
	
	public static void deleteTeacher()
	{
		System.out.println("====================================================================");
		try {
			System.out.println("Enter teacher id:");
			int teacherId=Integer.parseInt(br.readLine());
			    String st="select * from Teacher where id="+teacherId;
				Statement stmt=conn.createStatement();
		        ResultSet result=stmt.executeQuery(st);
		        System.out.println("Teacher Information:");
		        System.out.printf("%5s%10s%10s%15s","id","Name","Salary","SalaryEarn");
		        System.out.println("\n----------------------------------------------------");
		        int flag=0;
		        while(result.next())
		        {
		        	flag=1;
		        	System.out.println();
		        	System.out.printf("%5s%10s%10s%15s",""+result.getInt(1),""+result.getString(2),""+result.getDouble(3),""+result.getDouble(4));
		        	System.out.println();
		        }		
		        if(flag==1)
		        {
		        	PreparedStatement stmt1=conn.prepareStatement("delete from Teacher where id=? ");
					stmt1.setInt(1, teacherId);
			        if(stmt1.executeUpdate()>0)
			        {
			            System.out.println("Teacher record deleted successfully..!!");
			        }
			        else
			        {
			            System.out.println("Unsuccessful to delete Teacher Record..!!");
			        }
		        }
				else
		        	System.out.println("Teacher doesn't exist..!!");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("\n====================================================================");
	}
	public static void deleteStudent()
	{
		System.out.println("====================================================================");
		try {
			System.out.println("Enter student id:");
			int studentId=Integer.parseInt(br.readLine());
			    String st="select * from Student where id="+studentId;
				Statement stmt=conn.createStatement();
		        ResultSet result=stmt.executeQuery(st);
		        System.out.println("Student Information:");
		        System.out.printf("%5s%15s%10s%15s%15s","id","Name","Grade","Fees Paid","Total Fees");
		        System.out.println("\n--------------------------------------------------------------");
		        int flag=0;
		        while(result.next())
		        {
		        	flag = 1;
		        	System.out.printf("%5s%15s%10s%15s%15s",""+result.getInt(1),""+result.getString(2),""+result.getInt(3),""+result.getInt(4),""+result.getInt(5));
		        	System.out.println();
		        }		
		        if(flag==1)
		        {
					PreparedStatement stmt1=conn.prepareStatement("delete from Student where id=? ");
					stmt1.setInt(1, studentId);
			        if(stmt1.executeUpdate()>0)
			        {
			            System.out.println("Student record deleted successfully..!!");
			        }
			        else
			        {
			            System.out.println("Unsuccessful to delete Student Record..!!");
			        }
	        	
		        }
				else
					System.out.println("Student doesn't exist..!!");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("\n====================================================================");
	}
	//
	public static void searchTeacher() throws IOException
	{
		System.out.println("====================================================================");
		try {
			System.out.println("Enter teacher id:");
			int teacherId=Integer.parseInt(br.readLine());
			    String st="select * from Teacher where id="+teacherId;
				Statement stmt=conn.createStatement();
		        ResultSet result=stmt.executeQuery(st);
		        System.out.println("Teacher Information:");
		        System.out.printf("%5s%10s%10s%15s","id","Name","Salary","SalaryEarn");
		        System.out.println("\n----------------------------------------------------");
		        int flag=0;
		        while(result.next())
		        {
		        	flag = 1;
		        	System.out.printf("%5s%10s%10s%15s",""+result.getInt(1),""+result.getString(2),""+result.getDouble(3),""+result.getDouble(4));
		        }		
		        if(flag==0)
		        	System.out.println("Teacher doesn't exist..!!");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("\n====================================================================");
	    
	}
	public static void searchStudent() throws IOException
	{
		System.out.println("====================================================================");
		try {
				System.out.println("Enter student id:");
				int studentId=Integer.parseInt(br.readLine());
			    String st="select * from Student where id="+studentId;
				Statement stmt=conn.createStatement();
		        ResultSet result=stmt.executeQuery(st);
		        System.out.println("Student Information:");
		        System.out.printf("%5s%15s%10s%15s%15s","id","Name","Grade","Fees Paid","Total Fees");
		        System.out.println("\n--------------------------------------------------------------");
		        int flag=0;
		        while(result.next())
		        {
		        	flag = 1;
		        	System.out.printf("%5s%15s%10s%15s%15s",""+result.getInt(1),""+result.getString(2),""+result.getInt(3),""+result.getInt(4),""+result.getInt(5));
		        }		
		        if(flag==0)
		        	System.out.println("Student doesn't exist..!!");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("\n====================================================================");
	    
	}
	public static void payTeacher()
	{
		System.out.println("====================================================================");
		try {
			System.out.println("Enter teacher id:");
			int teacherId=Integer.parseInt(br.readLine());
			String st="select count(*) from Teacher where id="+teacherId+"&& salary=salaryEarned" ;
			Statement stmt=conn.createStatement();
	        ResultSet result=stmt.executeQuery(st);
	        int flag=0;
	        while(result.next())
	        {
	        	flag = 1;
	        	if(result.getInt(1)==1)
	        	{
	        		System.out.println("Salary Already Paid..!");
	        	}
	        	else
	        	{
	        		PreparedStatement stmt1=conn.prepareStatement("update Teacher set salaryEarned=salary where id=?");
			        stmt1.setInt(1,teacherId);
			        if(stmt1.executeUpdate()>0)
			        {
			            String st1="select salaryEarned from Teacher";
			        	Statement stmt2=conn.createStatement();
			        	ResultSet result1=stmt2.executeQuery(st1);
			        	int salaryPaid=0;
			        	while(result1.next())
			        	{
			        		salaryPaid=result1.getInt(1);
			        	}
			        	PreparedStatement stmt3=conn.prepareStatement("update School set totalMoneySpent=totalMoneySpent+?,totalMoneyEarned =totalMoneyEarned-?");
				        stmt3.setInt(1, salaryPaid);
				        stmt3.setInt(2, salaryPaid);
				        if(stmt3.executeUpdate()>0)
				        {		        	
				        	System.out.println("Salary Payment successfull..!!");
				        }
				        else
				        {	
				        	PreparedStatement stmt4=conn.prepareStatement("update Teacher set salaryEarned=0 where id=?");
					        stmt4.setInt(1,teacherId);
					        stmt4.execute();
				        	System.out.println("Salary Payment Unsuccessfull..!!");
				        }
			        }
			        else
			        {
			            System.out.println("Salary Payment Unsuccessfull....!!");
			        }
	        	}
	        }		
	        if(flag==0)
	        	System.out.println("Teacher doesn't exist..!!");
		}
		catch(Exception e)
		{
			System.out.println("");
			System.out.println(e);
		}
		System.out.println("====================================================================");
	}
	public static void receiveFees()
	{
		System.out.println("====================================================================");
		try {
			System.out.println("Enter student id:");
			int studentId=Integer.parseInt(br.readLine());
			
			String st="select count(*) from Student where id="+studentId+"&& feesPaid=feesTotal";
			Statement stmt=conn.createStatement();
	        ResultSet result=stmt.executeQuery(st);
	        int flag=0;
	        while(result.next())
	        {
	        	flag = 1;
	        	if(result.getInt(1)==1)
	        		System.out.println("Fee already complete..!!");
	        	else
	        	{
	        		PreparedStatement stmt1=conn.prepareStatement("update Student set feesPaid=feesTotal where id=?");
	    	        stmt1.setInt(1,studentId);
	    	        if(stmt1.executeUpdate()>0)
	    	        {
	    	            String st1="select feesPaid from Student where id="+studentId;
	    	        	Statement stmt2=conn.createStatement();
	    	        	ResultSet result1=stmt2.executeQuery(st1);
	    	        	int feesPaid=0;
	    	        	while(result1.next())
	    	        	{
	    	        		feesPaid=result1.getInt(1);
	    	        	}
	    	        	PreparedStatement stmt3=conn.prepareStatement("update School set totalMoneyEarned =totalMoneyEarned+?");
	    		        stmt3.setInt(1, feesPaid);
	    		        if(stmt3.executeUpdate()>0)
	    		        {		        	
	    		        	System.out.println(" Fees received successfull..!!");
	    		        }
	    		        else
	    		        {	
	    		        	PreparedStatement stmt4=conn.prepareStatement("update Student set feesPaid=0 where id=?");
	    			        stmt4.setInt(1,studentId);
	    			        stmt4.execute();
	    		        	System.out.println("Unsuccessfull to receive fees..!!");
	    		        }
	    	        }
	    	        else
	    	        {
	    	        	System.out.println("Unsuccessfull to receive fees..!!");
	    	        }

	        	}
	        }		
	        if(flag==0)
	        	System.out.println("Student doesn't exist..!!");
			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("====================================================================");

	}
	public static void paymentStatus()
	{
		System.out.println("====================================================================");
		try {
				System.out.println("Enter teacher id:");
				int teacherId=Integer.parseInt(br.readLine());
			    String st="select count(*) from Teacher where id="+teacherId+"&& salary=salaryEarned" ;
				Statement stmt=conn.createStatement();
		        ResultSet result=stmt.executeQuery(st);
		        int flag=0;
		        while(result.next())
		        {
		        	flag = 1;
		        	if(result.getInt(1)==1)
		        		System.out.println(" Salary Paid..!!");
		        	else
		        	{
		        		System.out.println(" Salary not paid..!!");
		        	}
		        }		
		        if(flag==0)	
		        	System.out.println("Teacher doesn't exist..!!");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("\n====================================================================");
	}
	public static void feesStatus()
	{
		System.out.println("====================================================================");
		try {
				System.out.println("Enter Student id:");
				int studentId=Integer.parseInt(br.readLine());
			    String st="select count(*) from Student where id="+studentId+"&& feesPaid=feesTotal";
				Statement stmt=conn.createStatement();
		        ResultSet result=stmt.executeQuery(st);
		        int flag=0;
		        while(result.next())
		        {
		        	flag = 1;
		        	if(result.getInt(1)==1)
		        		System.out.println("Fee complete..!!");
		        	else
		        	{
		        		System.out.println("Fee is pending..!!");
		        	}
		        }		
		        if(flag==0)
		        	System.out.println("Student doesn't exist..!!");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("\n====================================================================");
	}
	
	public static void displayTeacher()
	{
		
		System.out.println("====================================================================");
		try {
			String st="select * from Teacher";
			Statement stmt=conn.createStatement();
	        ResultSet result=stmt.executeQuery(st);
	        System.out.println("Teacher Information:");
	        System.out.printf("%5s%10s%10s%15s","id","Name","Salary","SalaryEarn");
	        System.out.println("\n----------------------------------------------------");
	        int flag=0;
	        while(result.next())
	        {
	        	flag = 1;
	        	System.out.println();
	        	System.out.printf("%5s%10s%10s%15s",""+result.getInt(1),""+result.getString(2),""+result.getDouble(3),""+result.getDouble(4));
	        }		
	        if(flag==0)
	        	System.out.println("Teacher doesn't exist..!!");
		}
		catch(Exception e)
		{
		System.out.println(e);	
		}
		System.out.println("\n====================================================================");
		
		
	}
	//student information display area
	public static void displayStudent()
	{
	   
		System.out.println("====================================================================");
		try {
			Statement stmt=conn.createStatement();
	        ResultSet result=stmt.executeQuery("select * from Student");
	        System.out.println("All Schools Record:");
	        System.out.printf("%5s%10s%10s%10s%10s","id","Name","Grade","Fees Paid","Total Fees");
	        System.out.println("\n--------------------------------------------------------------");
	        int flag=0;
	        while(result.next())
	        {
	            flag=1;
	        	System.out.println();
	        	System.out.printf("%5s%10s%10s%10s%10s",""+result.getInt(1),""+result.getString(2),""+result.getString(3),""+result.getString(4),""+result.getLong(5));
	        }
	        if(flag==0)
	        	System.out.println("Table is Empty..!!");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("\n====================================================================");
	}
	public static void countStudent()
	{
		System.out.println("\n====================================================================");
		try {
			String st="Select count(*) as TotalStudent,count(if(feesPaid=feesTotal,1,null))as FeesPaid,count(if(feesPaid=0,1,null))as FeesNotPaid from Student;";
			Statement stmt=conn.createStatement();
			ResultSet result=stmt.executeQuery(st);
			while(result.next())
			{
				System.out.println("\t\tTotal Number of Student :"+result.getInt(1)+"\r\n"
								+ "\t\t"+result.getInt(2)+" students paid Fees.\r\n"
								+ "\t\t"+result.getInt(3)+" students not Paid Fees.");
			}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		System.out.println("====================================================================");
	}
	
	public static void countTeacher()
	{
		System.out.println("\n====================================================================");
		System.out.println("Teachers Count :");
		try {
			String st="Select count(*) as TotalTeacher,count(if(salaryEarned=salary,1,null)) as SalaryPaid,count(if(salaryEarned=0,1,null)) as SalaryPending from Teacher";
			Statement stmt=conn.createStatement();
			ResultSet result=stmt.executeQuery(st);
			while(result.next())
			{
				System.out.println("\t\tTotal Number of Teacher :"+result.getInt(1)+"\r\n"
								+ "\t\tPayment of "+result.getInt(2)+" teachers complete.\r\n"
								+ "\t\tPayment of "+result.getInt(3)+" teachers pending.");
			}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		System.out.println("====================================================================");
	}
	public static void profitLoss()
	{
		System.out.println("====================================================================");
		System.out.println("Profit And Loss :");
		try {
		String st="Select * from School";
		Statement stmt=conn.createStatement();
		ResultSet result=stmt.executeQuery(st);
		while(result.next())
		{
			System.out.println("\t\tTotal Money spent on teachers salary :"+result.getDouble(2)+"\r\n"
							 + "\t\tTotal Earning                        :"+result.getDouble(1));
			if(result.getDouble(1)>0)
				System.out.println("Profit of "+result.getDouble(1));
			else
				System.out.println("Loss of "+result.getDouble(1));
		}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("====================================================================");
	}
	public static void changeTeacherPassword() throws IOException, SQLException
	{
		System.out.println("Enter Password for update:");
		String newPassword=br.readLine();
		PreparedStatement stmt4=conn.prepareStatement("update Teacher set password=? where id=?");
        stmt4.setString(1,newPassword);
		stmt4.setInt(2,teacherid);
        if(stmt4.executeUpdate()>0)
        	System.out.println("Password updated successfully..!!");
        else
        	System.out.println("Password not updated..!!");
	}
	public static void changeStudentPassword() throws IOException, SQLException
	{
		System.out.println("Enter Password for update:");
		String newPassword=br.readLine();
		PreparedStatement stmt4=conn.prepareStatement("update Student set password=? where id=?");
        stmt4.setString(1,newPassword);
		stmt4.setInt(2,studentid);
        if(stmt4.executeUpdate()>0)
        	System.out.println("Password updated successfully..!!");
        else
        	System.out.println("Password not updated..!!");
	}
	
	public static void teacherStatus()
	{
		System.out.println("====================================================================");
		try {
				int teacherId=teacherid;
			    String st="select count(*) from Teacher where id="+teacherId+"&& salary=salaryEarned" ;
				Statement stmt=conn.createStatement();
		        ResultSet result=stmt.executeQuery(st);
		        int flag=0;
		        while(result.next())
		        {
		        	flag = 1;
		        	if(result.getInt(1)==1)
		        		System.out.println(" Salary Paid..!!");
		        	else
		        	{
		        		System.out.println(" Salary not paid..!!");
		        	}
		        }		
		        if(flag==0)
		        	System.out.println("Teacher doesn't exist..!!");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("\n====================================================================");
	}
	public static void studentStatus()
	{
		System.out.println("====================================================================");
		try {
				
				int studentId=studentid;
			    String st="select count(*) from Student where id="+studentId+"&& feesPaid=feesTotal";
				Statement stmt=conn.createStatement();
		        ResultSet result=stmt.executeQuery(st);
		        int flag=0;
		        while(result.next())
		        {
		        	flag = 1;
		        	if(result.getInt(1)==1)
		        		System.out.println("Fee complete..!!");
		        	else
		        	{
		        		System.out.println("Fee is pending..!!");
		        	}
		        }		
		        if(flag==0)
		        	System.out.println("Student doesn't exist..!!");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("\n====================================================================");
	}
	
    public static void main(String[] args) throws SQLException {
    	
		try 
		{
	         Class.forName("com.mysql.cj.jdbc.Driver");//Setting the driver class
	         conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/SchoolManagement", "root", "root");//connecting with database
	    	 if(conn!=null)
	         {
	    		 //System.out.println("Connection Successfull!!");
	    		 System.out.println("\n====================================================================");
	    		 System.out.println("------------------  School Management Application ------------------");
	    		 System.out.println("====================================================================");
	    		 home();
	         } 
	    	 else
	    	 {
	    		 System.out.println("Not connected..!!");
	    	 }
		} 
	    catch (ClassNotFoundException e) {     
	    	e.printStackTrace();
	    }
		catch (SQLException e) {
			System.out.println(e);
			System.out.println("Problem in connection..!!");
		}
		catch (Exception e) { 
			System.out.println(e);
			System.out.println("Something went worng..!!");
		}
		finally
		{
			if(conn!=null)
				conn.close();
		}
		    }
}