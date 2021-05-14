package com;

import java.sql.*;

public class Order 
{
private Connection connect() 
{
		Connection con = null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/order", "root", "");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return con;
}

public String readItems()
{
				 String output = "";
				 try
				 {
					 Connection con = connect();
				 if (con == null)
				 {
					 return "Error while connectingto the database for reading.";
				 }
				 // Prepare the html table to be displayed
				 output = "<table border='1'><tr><th>Name</th><th>Email</th><th>Address</th>"
				 + "<th>Contact Number</th><th>Item Code</th><th>Update</th><th>Remove</th></tr>";
				 
				 String query = "select * from orders";
				 
				 Statement stmt = con.createStatement();
				 ResultSet rs = stmt.executeQuery(query);
				 // iterate through the rows in the result set
				 while (rs.next())
				 {
					 String Order_ID  = Integer.toString(rs.getInt("Order_ID"));
					 String CusName = rs.getString("CusName");
					 String CusEmail = rs.getString("CusEmail");
					 String CusAdress = rs.getString("CusAdress");
					 String CusPhone = Integer.toString(rs.getInt("CusPhone"));
					 String itemID = Integer.toString(rs.getInt("itemID"));
					 
				 // Add into the html table
				 output += "<tr><td><input id='hidItemIDUpdate'name='hidItemIDUpdate'type='hidden' value='" + Order_ID 
				 + "'>" + CusName + "</td>";
				 output += "<td>" + CusEmail + "</td>";
				 output += "<td>" + CusAdress + "</td>";
				 output += "<td>" + CusPhone + "</td>";
				 output += "<td>" + itemID + "</td>";
				 // buttons
				output += "<td><input name='btnUpdate'type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
				 + "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger'data-itemid='"
				 + Order_ID  + "'>" + "</td></tr>";
				 }
				 con.close();
				 // Complete the html table
				 output += "</table>";
				 }
				 catch (Exception e)
				 {
					 output = "Error while reading the items.";
					 System.err.println(e.getMessage());
				 }
				 return output;
}

public String insertItem(String name, String email,String address, String phone, String icode)
{
	
		 String output = "";
		 try
		 {
				 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connectingto the database for inserting.";
			 }
			 
			 // create a prepared statement
			 String query = " insert into orders(`Order_ID`,`CusName`,`CusEmail`,`CusAdress`,`CusPhone`,`itemID`)"
			 + " values (?, ?, ?, ?, ?, ?)";
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, name);
			 preparedStmt.setString(3, email);
			 preparedStmt.setString(4, address);
			 preparedStmt.setDouble(5, Integer.parseInt(phone));
			 preparedStmt.setDouble(6, Integer.parseInt(icode));
			 
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 
			 String newItems = readItems();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newItems + "\"}";
		 }
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
			 System.err.println(e.getMessage());
		 }
		 return output;
}

public String updateItem(String ID, String name, String email,String address, String phone, String icode)
{
		 String output = "";
		 try
		 {
			    Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connectingto the database for updating.";
			 }
			 // create a prepared statement
			 String query = "UPDATE orders SET CusName=?,CusEmail=?,CusAdress=?,CusPhone=?,itemID=?  WHERE Order_ID=?";
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setString(1, name);
			 preparedStmt.setString(2, email);
			 preparedStmt.setString(3, address);
			 preparedStmt.setDouble(4, Integer.parseInt(phone));
			 preparedStmt.setDouble(5, Integer.parseInt(icode));
			 preparedStmt.setInt(6, Integer.parseInt(ID));
			 
			// execute the statement
			 preparedStmt.execute();
			 con.close();
			 String newItems = readItems();
			 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		 }
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
			 System.err.println(e.getMessage());
		 }
		 return output;
}

public String deleteItem(String Order_ID)
		 {
		 	String output = "";
		 try
		 {
			 Connection con = connect();
			 if (con == null)
			 {
			 return "Error while connectingto the database for deleting.";
			 }
			 
			 // create a prepared statement
			 String query = "delete from orders where Order_ID =?";
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(Order_ID));
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 String newItems = readItems();
			 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		 }
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
			 System.err.println(e.getMessage());
		 }
		 return output;
		 }
}
