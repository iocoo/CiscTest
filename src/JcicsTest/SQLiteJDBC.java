/**
 * 
 */
package JcicsTest;


/**
 * @author GIGI WANG
 *
 */
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import JcicsTest.MsgFields;


public class SQLiteJDBC {
	private static Connection c =null ;
	
	
    public static boolean openDB()
	{
	  try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:cicst.db.sqlite");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      return false;
	    }
	    System.out.println("Opened database successfully");
	    return true;
    }
	/*
	 * CREATE TABLE T_CICST(TagName char(32),TagValue char(512), TagDesc char(64),TagFlag int);
	 */
    public List <MsgFields> getTagList(){
    	List <MsgFields> MsgList=null;
    	Statement stmt = null;
    	try{
    		MsgList=new LinkedList<MsgFields> ();
    		stmt = c.createStatement();
    		String sql = "SELECT * FROM T_CICST"; 
    		ResultSet rs =stmt.executeQuery(sql);
    		while ( rs.next() ) {
    			 MsgFields msgtag=new MsgFields();
    			 
    	         msgtag.f_flg = rs.getInt("TagFlag");
    	         msgtag.f_tag = rs.getString("TagName");
    	         msgtag.f_value = rs.getString("TagValue");
    	         msgtag.f_name = rs.getString("TagDesc");
    	         MsgList.add(msgtag);
    	         
    	         System.out.println( "FLAG = " + msgtag.f_flg);
    	         System.out.println( "TAG = " + msgtag.f_tag);
    	         System.out.println( "TagValue = " +msgtag.f_value );
    	         System.out.println( "TagDesc = " +msgtag.f_name );
    	         System.out.println();
    	      }
            stmt.close();
            
      } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
     }
    	return MsgList;
    	
    }
	}
