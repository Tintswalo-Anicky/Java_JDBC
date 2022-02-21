/*  # TASK 7
	# Compulsory Task 2
	# Bookstore.java
	# edited by Tintswalo Anicky Makhubele
	# date:  25 June 2020
	# Function: This program allows a bookstore clerk to enter new books,update book information,delete books and search specific books from into the database.
	*/

import java.sql.*;
import java.util.Scanner;

public class Bookstore {

    public static void main(String[] args) {
        try(
        		// Connect to the library_db database, via the jdbc:mysql: channel on localhost (this PC)
                // Use username "otheruser", password "swordfish".
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/ebookstore?useSSL=false",
                        "root",
                        "Lindokuhle05"
                        );

        		// Create a direct line to the database for running our queries
                Statement statement = connection.createStatement();
                ){

        		int selection = 0;
	            //Menu for action selection and user input
	            while(selection != 5) {
	                
		        	Scanner input = new Scanner(System.in);
		            int rowsAffected;
		            
	                System.out.println("\nPlease enter the number corresponding to the action you would like to take:\n"
	                        + "1. Enter book\n"
	                        + "2. Update book\n"
	                        + "3. Delete book\n"
	                        + "4. Search books\n"
	                        + "5. Exit");
	                selection = input.nextInt();
	
	                //Selection sorting
	                //enter new books into the database
	                if(selection == 1) {
	                	
	                    //Collecting book information
	                	System.out.println("\nPlease enter the ID number of the book you would like to put into the system: ");
	                    int id = input.nextInt();
	                    
	                    input.nextLine();
	                    System.out.println("\nPlease enter the Title of the book you would like to put into the system: ");
	                    String title = input.nextLine();
	                    
	                    System.out.println("\nPlease enter the Author of said book: ");
	                    String author = input.nextLine();
	                   
	                    System.out.println("\nPlease enter the number of said book currently in stock: ");
	                    int qty = input.nextInt();
	                    
	                 // Add a new book:
	                    String query = "INSERT INTO books VALUES("+ id +", '"+ title +"', '"+ author +"', "+ qty +");";
	                    rowsAffected = statement.executeUpdate(query);
	                    
	                    System.out.println("\n");
			            System.out.println("Query complete, " + rowsAffected + " rows added.");
			            System.out.println("\nUPDATED TABLE:");
			            printAllFromTable(statement);
	
	                } //update book information
	                else if(selection == 2) {
	                	System.out.println("BOOKS TABLE: ");
	                	printAllFromTable(statement);
	                	
	                    //Collecting book information
	                    System.out.println("\nPlease enter the ID of the book you would like to update: ");
	                    int id = input.nextInt();
	                    
	                    System.out.println("\nPlease enter the new number of said book currently in stock: ");
	                    int qty = input.nextInt();

	                    String query = "UPDATE books SET qty = " + qty + " WHERE id = " + id + ";";
	                    System.out.println(query);
	                    rowsAffected = statement.executeUpdate(query);
	                    
	                    System.out.println("\n");
			            System.out.println("Query complete, " + rowsAffected + " rows added.");
			            System.out.println("\nUPDATED TABLE: ");
			            printAllFromTable(statement);
	
	                } else if(selection == 3) {
	                	//delete books from the database
	                	
	                	System.out.println("BOOKS TABLE: ");
	                	printAllFromTable(statement);
	                    
	                	//Collecting book information
	                    System.out.print("\nPlease enter the id of the book you would like to delete from the system: ");
	                    int id = input.nextInt();
	                    
	                    String query = "DELETE FROM books WHERE id = " + id + ";";
	                    System.out.println(query);
	                    rowsAffected = statement.executeUpdate(query);
	                    
	                    System.out.println();
			            System.out.println("Query complete, " + rowsAffected + " rows added.");
			            System.out.println("\nUPDATED TABLE: ");
			            printAllFromTable(statement);
	                    
	                } 
	                //search the database to find a specific book.
	                else if(selection == 4) {
	                	
	                	Boolean control_edit = true;
	                	input.nextLine();
	                	while (control_edit) {
	                		
		                	System.out.println("\nPlease enter the Title of the book you are searching for: ");
		                    String title = input.nextLine();
		                    
		                    // executeQuery: runs a SELECT statement and returns the results.
		                    String query = "SELECT * FROM books WHERE title = '" + title + "';";
		                    ResultSet results = statement.executeQuery(query);
		                    	
			                    if (results.next())
			                    {
			                    	System.out.println("SEARCH RESULTS:\n");
			                    	System.out.println(
			                    			"Book ID: " + results.getInt("id") +
			        	            		"Book Title: " + results.getString("title") + 
			        	                    "Book Author: " + results.getString("author") + 
			        	                    "Book Quantity: " + results.getInt("qty")
			        	                );
			                    	break;
		                    	}else {
		                    		// book not found message
		                    		System.out.println("NO RESULTS FOUND FOR: '" + title + "'");
		                    		control_edit = true;
		                    		}
			                    results.close();}
	                      
	                } else if(selection == 5) {
	                	//exit message
	                    System.out.println("Goodbye");
	                    input.close();
	                
	                } else { 
	                	//Invalid entry handler
	                    System.out.println("Sorry, that isn't a valid selection.");
	                }
	            }
	         // Close up our connections
	            statement.close();
	            connection.close();
	
	        } catch(SQLException ex) {
	        	// We only want to catch a SQLException - anything else is off-limits for now.
	            ex.printStackTrace();
	        }
	    }
    /**
     * Method printing all values in all rows.
     * Takes a statement to try to avoid spreading DB access too far.
     * 
     * @param a statement on an existing connection
     * @throws SQLException
     */
	public static void printAllFromTable(Statement statement) throws SQLException{
		try {
	        
	        ResultSet results = statement.executeQuery("SELECT id, title, author, qty FROM books");
	        while (results.next()) {
	            System.out.println(
	            		"\nBook ID : " + results.getInt("id") +
	            		"\nTitle   : " + results.getString("title") + 
	                    "\nAuthor  : " + results.getString("author") + 
	                    "\nQuantity: " + results.getInt("qty")
	                );
	        }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
//***********************************************************END**********************************************************************
