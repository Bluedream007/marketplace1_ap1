package com.bluedream.cliapp.marketplace1.cmd;

public class ExeInfo {
	
	private String exeCommand;	

	private String[] exeDatas;
	
	public ExeInfo(String inCommand, String[] inDatas) {
		this.exeCommand = inCommand;
		this.exeDatas = new String[inDatas.length];
		
		System.arraycopy(inDatas, 0, exeDatas, 0, inDatas.length);
	}
	
	public String getExeCommand() {
		return exeCommand;
	}

	public void setExeCommand(String exeCommand) {
		this.exeCommand = exeCommand;
	}

	public String[] getExeDatas() {
		return exeDatas;
	}

	public void setExeDatas(String[] exeDatas) {
		this.exeDatas = exeDatas;
	}

	public String toString() {
		return "Command: " + this.exeCommand + ", ExeDatas: " + this.exeCommand;
	}
	
	
}
