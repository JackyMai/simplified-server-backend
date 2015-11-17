package server.controller;

import java.util.ArrayList;

public class ErrorController {
	private ArrayList<String> errorList;
	
	public ErrorController() {
		this.errorList = new ArrayList<String>();
	}
	
	public void addError(String error) {
		errorList.add(error);
	}
	
	public void printError() {
		for(String str : errorList) {
			System.out.println(str);
		}
	}
}
