package com.m12i.svn.commit.as.is;

public class Parameters {
	private String workingCopyPath;
	private String repositoryPath;
	private String userName;
	private String password;
	private String commitMessage;
	public String getWorkingCopyPath() {
		return workingCopyPath;
	}
	public void setWorkingCopyPath(String workingCopyPath) {
		this.workingCopyPath = workingCopyPath;
	}
	public String getRepositoryPath() {
		return repositoryPath;
	}
	public void setRepositoryPath(String repositoryPath) {
		this.repositoryPath = repositoryPath;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCommitMessage() {
		return commitMessage;
	}
	public void setCommitMessage(String commitMessage) {
		this.commitMessage = commitMessage;
	}
}
