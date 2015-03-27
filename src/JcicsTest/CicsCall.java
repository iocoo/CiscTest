package JcicsTest;
//import EciPublic;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import JcicsTest.MsgObject;

import com.ibm.ctg.client.*;

public class CicsCall
{
   public static final int     BUFFER_SIZE_MAX         = 10240;
   public static String        strJGateURL             = "127.0.0.1";
   public static int           iJGatePort              = 2006;
   public static String        strServerName           = "RG01";
   public static String        strUserName             = "CICSUSER";
   public static String        strPassWord             = "";
   public static String        strProgramName          ="ONLSCH";
   public static String        strBody;
   public static String        strTrxCd ;
   public static String        strMsgType ;
   public static String        strPcFlag ;
   public static String        strProId ;
   
   String szHeaderBody="<?xml version=\"1.0\" encoding=\"gb2312\"?>";
   
   public CicsCall( MsgObject msgObj){
	   
	   
	   strMsgType=msgObj.msgType;
	   strTrxCd=msgObj.trxcd;
	   strPcFlag=msgObj.pcflag;
	   strBody=msgObj.body;
	   strProId=msgObj.prodid;
	   
   }

   public String  Call (String szMsg)
   {
	    
	    
	    String              strCommArea             ;
	    int                 iReturnCode;
	    int                 iIndex;
	    byte                byteNull                = 0x00;
	    JavaGateway         javaGateway             = null;
	    ECIRequest          eciRequest;
	    byte                abCommArea []           = new byte [BUFFER_SIZE_MAX];
	    Date now = new Date(); 
	    SimpleDateFormat currentDateTime = new SimpleDateFormat("yyyyMMddHHmmss");
	    String currentDTime = currentDateTime.format(now);
	    String curDate=currentDTime.substring(0,8);
	    String curTime=currentDTime.substring(8);
	    strCommArea=strMsgType+strPcFlag+strTrxCd+"00"+strProId
	    		+"                "+curDate+"         "+" "+curDate+curTime+"    "+"                     "+"FFFFFFFF";
	    
	    int comm_len=strBody.length()+szHeaderBody.length();
	    strCommArea+=String.format("%04d", comm_len); 
	    strCommArea+=szHeaderBody;
	    strCommArea+=strBody;
	    System.out.println(strCommArea);
	    try
	    {
	      System.arraycopy (
	                          strCommArea.getBytes ("ASCII"),
	                          0,
	                          abCommArea,
	                          0,
	                          strCommArea.length ()
	                        );
	    }
	    catch (UnsupportedEncodingException uee)
	    {
	    	return ("Encoding error: " + uee);
	    }
	    
	    try
	    {
	    
	      javaGateway = new JavaGateway ();
	      javaGateway.setURL (strJGateURL);
	      javaGateway.setPort (iJGatePort);
	      javaGateway.open ();
	    }
	    catch (IOException eJavaGateway)
	    {
	      return ("JavaGateway IOException: " + eJavaGateway);
	    }
	    
	    eciRequest = new ECIRequest
	            (
	            	ECIRequest.ECI_SYNC,
	                strServerName,
	                strUserName,
	                strPassWord,
	                strProgramName,
	                null,
	                abCommArea,
	                BUFFER_SIZE_MAX,
	                ECIRequest.ECI_NO_EXTEND,
	                ECIRequest.ECI_LUW_NEW
	            );
	    try
	    {
	      // ***** Call program *****

	      iReturnCode = javaGateway.flow (eciRequest);
	      if (iReturnCode == ECIRequest.ECI_NO_ERROR)
	      {
	    	// ***** Call program success *****
	    	  for (iIndex = 0;  iIndex < eciRequest.Commarea.length;  iIndex ++)
	              if (eciRequest.Commarea [iIndex] == byteNull)
	                break;

	             return ("[" + new String (eciRequest.Commarea, 0, iIndex) + "]");
	      } 
	      else
	      {
	        
	    	  return ("Error: JavaGateway flow return[" + iReturnCode + "]");
	        
	      }
	    }
	    catch (IOException eECICall)
	    {
	      // ***** Call program fail *****

	    	return ("JavaGateway IOException: " + eECICall);
	    }
	    finally
	    {
	      try
	      {
	        if (javaGateway != null)
	          javaGateway.close ();
	      }
	      catch (IOException e)
	      {
	    	  return ("Error: javaGateway.close ()" + e);
	      }
	    }
   }
}