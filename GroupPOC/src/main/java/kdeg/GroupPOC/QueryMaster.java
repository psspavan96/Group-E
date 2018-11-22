package kdeg.GroupPOC;

public class QueryMaster {
	
	private String question;
	private String query;
	
	
	public QueryMaster() {
		super();
	}
	public QueryMaster(String question, String query) {
		super();
		this.question = question;
		this.query = query;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
}
