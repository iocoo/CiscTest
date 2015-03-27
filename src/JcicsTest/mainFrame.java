package JcicsTest;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Window.Type;
import java.awt.event.*;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
//import javax.swing.JTextPane;



import JcicsTest.CicsCall;
import JcicsTest.MsgObject;
import JcicsTest.XmlFormatter;
import JcicsTest.SQLiteJDBC;

import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import org.sqlite.*;


public class mainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5044598292109985213L;
	private JPanel contentPane;
	private JTextField textField_Trxcd;
	private JTable table;
	private JLabel label_1;
	private JComboBox comboBox;
	private JLabel lbll;
	private JComboBox comboBox_msgTp;
	private JTextField textField_Poid;
	//private JTextArea textReturn;
	private JTextArea textArea;
	public JTextArea textReturn;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private static List<MsgFields> msgListField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame frame = new mainFrame();
					frame.setVisible(true);
					SQLiteJDBC dbs=new SQLiteJDBC();
					dbs.openDB();
					msgListField= dbs.getTagList();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainFrame() {
		setTitle("\u4EE3\u6536\u4ED8\u4EA4\u6613\u6D4B\u8BD5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u4EA4\u6613\u7801");
		label.setBounds(365, 36, 46, 14);
		contentPane.add(label);
		
		textField_Trxcd = new JTextField();
		textField_Trxcd.setText("4401");
		textField_Trxcd.setBounds(409, 33, 53, 20);
		contentPane.add(textField_Trxcd);
		textField_Trxcd.setColumns(10);
		
		JButton btnNewButton = new JButton("\u786E\u5B9A");
		btnNewButton.setBounds(576, 77, 89, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ButtonClickListener()); 
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 111, 676, 4);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(382, 118, 13, 393);
		contentPane.add(separator_1);
		
		label_1 = new JLabel("\u65B9\u5411");
		label_1.setBounds(149, 36, 46, 14);
		contentPane.add(label_1);
		
		comboBox = new JComboBox();
		comboBox.setToolTipText("");
		comboBox.setBounds(190, 33, 46, 20);
		comboBox.addItem("P");
		comboBox.addItem("C");
		contentPane.add(comboBox);
		
		lbll = new JLabel("\u62A5\u6587\u7C7B\u578B");
		lbll.setBounds(10, 36, 69, 14);
		contentPane.add(lbll);
		
		comboBox_msgTp = new JComboBox();
		comboBox_msgTp.setBounds(75, 33, 53, 20);
		comboBox_msgTp.addItem("8583");
		comboBox_msgTp.addItem("XML");
		contentPane.add(comboBox_msgTp);
		
		textField_Poid = new JTextField();
		textField_Poid.setText("0001");
		textField_Poid.setBounds(309, 33, 46, 20);
		contentPane.add(textField_Poid);
		textField_Poid.setColumns(10);
		
		JLabel lblid = new JLabel("\u4EA7\u54C1ID");
		lblid.setBounds(261, 36, 53, 14);
		contentPane.add(lblid);
		
		JButton btnNewButton_1 = new JButton("\u751F\u6210");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowCount=table.getRowCount();
				  String xmlStr="<CSys></CSys>";
				  //int colCount=table.getColumnCount();
				  int Count=(rowCount<msgListField.size())?rowCount:msgListField.size();
				  for(int i=0;i<Count;i++){
					  table.setValueAt(msgListField.get(i).f_name, i, 0);
					  table.setValueAt(msgListField.get(i).f_tag, i, 1);
					  table.setValueAt(msgListField.get(i).f_value, i, 2);
					  table.setValueAt(msgListField.get(i).f_flg, i, 3);
					  
				  }
				  xmlStr="<CSys>";
				  for( int i=0;i<rowCount;i++){
					  if(table.getValueAt(i, 3)==null)
					  {
						  break;
					  }
					  int flg=Integer.parseInt(table.getValueAt(i, 3).toString());
					  if(flg==1)
					  {
					      
						  xmlStr+="<"+table.getValueAt(i, 1).toString()+">"
					            +table.getValueAt(i, 2).toString()
					            +"</"+table.getValueAt(i, 1).toString()+">";
								
					  }
					
					  
				  }
		     xmlStr +="</CSys>";
		     System.out.println(xmlStr);
		     String fmtXML="";
				//textArea.setText("");
			XmlFormatter xmlFmt=new XmlFormatter();
			try {
				fmtXML=xmlFmt.formatXML(xmlStr);
			} catch (Exception XMLe) {
				
				XMLe.printStackTrace();
			}
			 
			  textArea.setText(fmtXML);
			}
		});
		
		
		
		btnNewButton_1.setBounds(447, 77, 89, 23);
		contentPane.add(btnNewButton_1);
		
		
		 
		 
		textReturn = new JTextArea();
		textReturn.setEditable(false);
		textReturn.setLineWrap(true);
		textReturn.setWrapStyleWord(true);
		
		textReturn.setBounds(405, 126, 281, 385);
		textReturn.setText("Ready for send..");
		contentPane.add(textReturn);
		
		scrollPane_2 = new JScrollPane(textReturn);
		 scrollPane_2.setBounds(392, 126, 302, 395);
		 contentPane.add(scrollPane_2);
		
		JPanel panel = new JPanel();
		panel.setBounds(16, 126, 333, 152);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 333, 152);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);  
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);  
		panel.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setText("<CSys></CSys>");
		
	/*	
		
	    for(int n=0;n<msgListField.size();n++) {
			String f_name = msgListField.get(n).f_name;
			String f_tag = msgListField.get(n).f_tag;
			String f_value = msgListField.get(n).f_value;
			int f_flg = msgListField.get(n).f_flg;
		}
		
		*/
		 
		 table = new JTable();
		 table.setBounds(20, 33, 295, 950);
		 
		 table.setModel(new DefaultTableModel(
		 	new Object[][] {
	                {" ", "F000", " "},
			 		{" ", "F002", " "},
			 		{" ", "F003", " "},
			 		{" ", "F004", " "},
			 		{" ", "F005", " "},
			 		{" ", "F006", " "},
			 		{" ", "F007", " "},
			 		{" ", "F008", " "},
			 		{" ", "F009", " "},
			 		{" ", "F010", " "},
			 		{" ", "F011", " "},
			 		{" ", "F012", " "},
			 		{" ", "F013", " "},
			 		{" ", "F015", " "},
			 		{" ", "F016", " "},
			 		{" ", "F022", " "},
			 		{" ", "F025", " "},
			 		{" ", "F026", " "},
			 		{" ", "F031", " "},
			 		{" ", "F032", " "},
			 		{" ", "F033", " "},
			 		{" ", "F039", " "},
			 		{" ", "F041", " "},
			 		{" ", "F042", " "},
			 		{" ", "F046", " "},
			 		{" ", "F048", " "},
			 		{" ", "F049", " "},
			 		{" ", "F050", " "},
			 		{" ", "F054", " "},
			 		{" ", "F057", " "},
			 		{" ", "F060", " "},
			 		{" ", "F061", " "},
			 		{" ", "F063", " "},
			 		{" ", "F090", " "},
			 		{" ", "F100", " "},
			 		{" ", "F102", " "},
			 		{" ", "F103", " "},
			 		{" ", "F128", " "}
		 	},
		 	new String[] {
		 		"Name", "Tag", "Value","Flag"
		 	}
		 ));

		 table.getColumnModel().getColumn(0).setPreferredWidth(2);
		 table.getColumnModel().getColumn(1).setPreferredWidth(10);
		 table.getColumnModel().getColumn(2).setPreferredWidth(100);
		 table.getColumnModel().getColumn(3).setPreferredWidth(2);
		 //table.setColumnSelectionAllowed(true);
		 //table.setCellSelectionEnabled(true);
		 table.setFillsViewportHeight(true);
		 //table.setAutoResizeMode(DISPOSE_ON_CLOSE);

		 
		 contentPane.add(table);
		 //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		 scrollPane_1 = new JScrollPane(table);
		 scrollPane_1.setBounds(16, 299, 333, 222);
		 scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		 scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		 contentPane.add(scrollPane_1);
		 
		
		
	}
	
	private class ButtonClickListener implements ActionListener{
	      public void actionPerformed(ActionEvent e) {
	      
	        	 //String msgTP= comboBox_msgTp.getSelectedItem().toString();
	        	 //String pcFlag= comboBox.getSelectedItem().toString();
	      	     textReturn.setText("");
	        	 int msgTP=comboBox_msgTp.getSelectedIndex();
	        	 int pcFlag=comboBox.getSelectedIndex();
	        	 //String trxCd=textField.getText(); 
	        	 //System.out.println(msgTP+pcFlag+trxCd);
	        	 MsgObject msgObj =new MsgObject();
	        	 if(msgTP==0)
	        	 {
	        		 msgObj.msgType="1001";
		        	 
	        	 }
	        	 else
	        	 {
	        		 msgObj.msgType="2001";
	        	 }
	        	 if(pcFlag==0)
	        	 {
	        		 
		        	 msgObj.pcflag="P"; 
	        	 }
	        	 else
	        	 {
	        		 msgObj.pcflag="C"; 
	        	 }
	        	 
	        	 msgObj.trxcd=textField_Trxcd.getText();
	        	 msgObj.prodid=textField_Poid.getText();
	        	 msgObj.body=textArea.getText();
	        	 CicsCall CicsCall = new CicsCall(msgObj);
	        	 String retData =CicsCall.Call(" ");
	        	 System.out.println(retData);
	        	 textReturn.setText(retData);	
					
	      }		
	   }
}

