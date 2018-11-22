package kdeg.GroupPOC;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

public class QueryGUI {

	private JFrame frmSparqlQueryEngine;
	private static String[] questions;
	private static String[] queries;
	private static String queryFilePath="queryFiles//";
	private static String ONTOLOGY_PATH="resource//groupE_Ontology.ttl";
	//	private static Map queryMap = new ConcurrentHashMap<Integer,QueryMaster>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initQuestions();
					QueryGUI window = new QueryGUI();
					window.frmSparqlQueryEngine.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void initializeAppGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initQuestions();
					QueryGUI window = new QueryGUI();
					window.frmSparqlQueryEngine.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public QueryGUI() {
		initialize();
	}

	public static void initQuestions() {
		try {

			/*			questions = new String[20];
			queries = new String[20];
			Path path = Paths.get(queryFilePath);
			byte[] bytes = Files.readAllBytes(path);
			String queryTxt = new String(bytes);

			String readLines[] = queryTxt.split("\n");
			for(int i=0;i<readLines.length;i++)
			{
				questions[i]= readLines[i].split(" ")[1];
				queries[i]= readLines[i].split(" ")[2];
			}
			 */

			File dir = new File(queryFilePath);
			File[] files = dir.listFiles();
			Arrays.sort(files);

			questions = new String[files.length];
			queries = new String[files.length];
			for (int i = 0; i < files.length; i++) {
				//				QueryMaster queryMasterObj = new QueryMaster();
				Path pathObj = Paths.get(files[i].getPath());
				byte[] bytesRead = Files.readAllBytes(pathObj);
				String queryFileData = new String(bytesRead);
				String dataArray[] = queryFileData.split("<queryStart>");
				/*queryMasterObj.setQuestion(dataArray[0]);
				queryMasterObj.setQuery(dataArray[1]);*/	
				questions[i]= dataArray[0];
				queries[i]= dataArray[1];
				//				queryMap.put(i, queryMasterObj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSparqlQueryEngine = new JFrame();
		frmSparqlQueryEngine.setTitle("Query Engine");
		frmSparqlQueryEngine.setBounds(100, 100, 1000, 600);
		frmSparqlQueryEngine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSparqlQueryEngine.getContentPane().setLayout(null);
		frmSparqlQueryEngine.setResizable(false);

		JLabel lblNewLabel = new JLabel("Select Query");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		lblNewLabel.setToolTipText("Select Query");
		lblNewLabel.setBounds(20, 0, 104, 26);
		frmSparqlQueryEngine.getContentPane().add(lblNewLabel);

		JList list = new JList(questions);

		list.setBounds(20, 29, 850, 169);
		list.setVisibleRowCount(0);
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		/*JScrollPane pane = new JScrollPane();
		pane.setViewportView(list);*/

		frmSparqlQueryEngine.getContentPane().add(list);

		JButton btnNewButton = new JButton("Execute");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(880, 29, 104, 169);

		frmSparqlQueryEngine.getContentPane().add(btnNewButton);
		/*frmSparqlQueryEngine.getContentPane().add(txtrOutput);*/

		/*	JTextArea txtrOutput = new JTextArea();
		txtrOutput.setEditable(false);
		txtrOutput.setAutoscrolls(true);
		txtrOutput.setBounds(10, 232, 974, 341);
		frmSparqlQueryEngine.getContentPane().add(txtrOutput);
		 */

		JLabel lblOutput = new JLabel("Result");
		lblOutput.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		lblOutput.setBounds(20, 209, 80, 17);
		frmSparqlQueryEngine.getContentPane().add(lblOutput);


		/*JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(20, 229, 850, 319);
		frmSparqlQueryEngine.getContentPane().add(scrollPane1);
*/
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 229, 850, 319);
		frmSparqlQueryEngine.getContentPane().add(scrollPane);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		/*JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 202, 146, 14);
		frmSparqlQueryEngine.getContentPane().add(progressBar);
		 */
		/*JTable output = new JTable();
		scrollPane.setViewportView(output);*/
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*				int i=5;
				progressBar.setValue(i);
				 */				int selectedQuery = list.getSelectedIndex();

				 String output = runQuery(selectedQuery);
				/* JTable output = runQuery(selectedQuery);
				 output.setVisible(true);
				 scrollPane1.setViewportView(output);*/
				 textArea.setText(output);
			/*	 JScrollPane scrollPane1 = new JScrollPane(output);
				 scrollPane1.setBounds(20, 229, 850, 319);
			*/	
//				 frmSparqlQueryEngine.getContentPane().add(scrollPane1);
				 /* frmSparqlQueryEngine.getContentPane().add(scrollPane);
				 scrollPane.setViewportView(output);
				 scrollPane.getViewport ().add (output);*/
				 //				 scrollPane.setViewportView(output);
			}
		});
	}

	private String runQuery(int index) {
		String output = "";
		JTable table =null;
		try
		{
			String queryString = queries[index];
			OntModel ontModelObj = ModelFactory.createOntologyModel();
			ontModelObj.read(ONTOLOGY_PATH);
			Query query = QueryFactory.create(queryString);
			QueryExecution queryExecutor = QueryExecutionFactory.create(query, ontModelObj);
			ResultSet results = queryExecutor.execSelect();

			//For output as text
						output = ResultSetFormatter.asText(results);

			//for tabular result
		/*	DefaultTableModel model1 = map(results,index);
			table = new JTable(model1);
*/
			/*			while(results.hasNext())
			{
				QuerySolution sol =results.nextSolution();
				Iterator<String> it = sol.varNames();
				DefaultTableModel model1 = (DefaultTableModel) table.getModel();
								if(index==0)
				{
					Resource county = sol.getResource("county"); 
					Resource noOfSecondarySchool = sol.getResource("noOfSecondarySchool");
					Resource noOfPrimarySchool = sol.getResource("noOfPrimarySchool");
					Resource noOfCommunitySchool = sol.getResource("noOfCommunitySchool");
					Resource noOfCommunityCollegeSchool = sol.getResource("noOfCommunityCollegeSchool");
					Resource noOfOthersSchool = sol.getResource("noOfOthersSchool");

					model1.addRow(new Object[]{county, noOfSecondarySchool,noOfPrimarySchool,noOfCommunitySchool,noOfCommunityCollegeSchool,noOfOthersSchool});

				}

						}
			 */

			queryExecutor.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			return output;
		/*return table;*/

	}


	//for tabular result
	public DefaultTableModel map(ResultSet resultSet,int index) 
	{
		DefaultTableModel defaultTableModel = new DefaultTableModel();
		int counter=0;
		/*ResultSetMetaData meta = resultSet.getMetaData();*/
		/*		ResultSet temp = resultSet;
		QuerySolution sol1 =null;
		Iterator<String> tempIt =null;
		while(temp.hasNext())
		{
		sol1 =temp.nextSolution();
		}
		tempIt =sol1.varNames();
		int j=0;
		while(tempIt.hasNext()) {
			j++;
			tempIt.next();
		}*/
		//		int numberOfColumns = j;
		while (resultSet.hasNext())
		{
			QuerySolution sol =resultSet.nextSolution();
			Iterator<String> it = sol.varNames();
			Object [] rowData = null;
			if(index==0)
			{
				rowData = 	new Object[6];
				counter=6;
			}
			else if(index==1)
			{
				rowData = 	new Object[4];
				counter=4;
			}
			else if(index==2)
			{
				rowData = 	new Object[3];
				counter=3;
			}
				else if(index==3)
				{
					rowData = 	new Object[3];
					counter=3;
				}
			else if(index==4)
			{
				rowData = 	new Object[6];
				counter=6;
			}
				else if(index==5)
				{
					rowData = 	new Object[3];
					counter=3;
				}
			else if(index==6)
			{
				rowData = 	new Object[4];
				counter=4;
			}
				else if(index==7)
				{
					rowData = 	new Object[5];
					counter=5;
				}


			for (int i = 0; i < counter; ++i)
			{
				int count=0;
				while(it.hasNext()) {
					String element = it.next();
					if(count==0)
					{
						rowData[count] = sol.getResource(element);
					}
					else
					{
						rowData[count] = sol.getLiteral(element).getString();
					}
					++count;
				}
			}
			defaultTableModel.addRow(rowData);
		}

		return defaultTableModel;
	}


}
