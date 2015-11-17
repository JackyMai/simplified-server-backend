package server.model;

import java.util.ArrayList;

public class Folder {
	private String owner;
	private String name;
	private ArrayList<Content> contentList = new ArrayList<Content>();
	
	public Folder(String owner, String name, Content content) {
		this.owner = owner;
		this.name = name;
		addContent(content);
	}
	
	public String getOwner() {
		return owner;
	}
	
	public String getName() {
		return name;
	}
	
	public void addContent(Content content) {
		this.contentList.add(content);
	}
	
	public ArrayList<Content> getContentList() {
		return contentList;
	}
}
