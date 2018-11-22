package kdeg.GroupPOC;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import java.awt.Font;
import javax.swing.JProgressBar;

public class QueryGUI {

	private JFrame frmSparqlQueryEngine;
	private static String[] questions;
	private static String[] queries;
	private static String queryFilePath="queryFile//";
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
		frmSparqlQueryEngine.setBounds(100, 100, 1000, 800);
		frmSparqlQueryEngine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSparqlQueryEngine.getContentPane().setLayout(null);
		frmSparqlQueryEngine.setResizable(false);

		JLabel lblNewLabel = new JLabel("Select Query");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setToolTipText("Select Query");
		lblNewLabel.setBounds(10, 10, 97, 31);
		frmSparqlQueryEngine.getContentPane().add(lblNewLabel);

		JList list = new JList(questions);

		list.setBounds(124, 11, 860, 169);
		list.setVisibleRowCount(0);
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		/*JScrollPane pane = new JScrollPane();
		pane.setViewportView(list);*/

		frmSparqlQueryEngine.getContentPane().add(list);

		JButton btnNewButton = new JButton("Execute");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(10, 60, 97, 71);


		frmSparqlQueryEngine.getContentPane().add(btnNewButton);

		JTextArea txtrOutput = new JTextArea();
		txtrOutput.setEditable(false);
		txtrOutput.setAutoscrolls(true);
		txtrOutput.setBounds(10, 232, 974, 341);
		frmSparqlQueryEngine.getContentPane().add(txtrOutput);
		
		JLabel lblOutput = new JLabel("Result");
		lblOutput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOutput.setBounds(389, 202, 74, 14);
		frmSparqlQueryEngine.getContentPane().add(lblOutput);
		
		/*JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 202, 146, 14);
		frmSparqlQueryEngine.getContentPane().add(progressBar);
*/
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
/*				int i=5;
				progressBar.setValue(i);
*/				int selectedQuery = list.getSelectedIndex();
				
				String output = runQuery(queries[selectedQuery]);
				txtrOutput.setText(output);
			}
		});
	}

	private String runQuery(String queryString) {
		String output = "";
		try
		{
		OntModel ontModelObj = ModelFactory.createOntologyModel();
		ontModelObj.read(ONTOLOGY_PATH);
		Query query = QueryFactory.create(queryString);
		QueryExecution queryExecutor = QueryExecutionFactory.create(query, ontModelObj);
		ResultSet results = queryExecutor.execSelect();
		output = ResultSetFormatter.asText(results);
		queryExecutor.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return output;

	}
}
