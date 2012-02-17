package models.utils;

public class TaskDetails {

	private String issueKey = null;
	private String issueDesc = null;
	
	public TaskDetails(String issueKey, String issueDesc) {
		this.issueKey = issueKey;
		this.issueDesc = issueDesc;
	}
	
	public String getIssueKey() {
		return issueKey;
	}
	
	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}
	
	public String getIssueDesc() {
		return issueDesc;
	}
	
	public void setIssueDesc(String issueDesc) {
		this.issueDesc = issueDesc;
	}
	
	
}
