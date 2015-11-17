package server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import server.model.Content;
import server.model.Folder;

public class FolderController {
	ArrayList<Folder> folderList;
	MemberController memberCtrl;
	ContentController contentCtrl;
	
	public FolderController(MemberController memberCtrl, ContentController contentCtrl) {
		this.folderList = new ArrayList<Folder>();
		this.memberCtrl = memberCtrl;
		this.contentCtrl = contentCtrl;
	}
	
	public boolean checkValidity(String[] parts, int lineNumber) {
		String owner = parts[1];
		String name = parts[2];
		String content = parts[3];
		
		if(!memberCtrl.containsName(owner)) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": no such member to have folder");
		} else if(name.equals("")) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": invalid foramt, folder name is empty");
		} else if(duplicate(owner, name)) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": duplicate folder title");
		} else if(content.equals("")) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": invalid format, content field is empty");
		} else if(contentCtrl.getContent(content) == null) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": no such content to go in folder");
		} else if(!contentCtrl.checkOwner(owner, content)) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": user is not the owner of the content");
		} else if(parts.length < 4) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": invalid format, missing fields");
		} else if(parts.length > 4) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": too many fields");
		}
		
		return true;
	}
	
	public boolean duplicate(String owner, String name) {
		for(Folder f : folderList) {
			if(!f.getOwner().equals(owner) && f.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void addFolder(Folder folder) {
		folderList.add(folder);
	}
	
	public Folder getFolder(String title) {
		for(Folder f: folderList) {
			if(f.getName().equals(title)) {
				return f;
			}
		}
		return null;
	}
	
	public void listFolder(String title) {
		ArrayList<Content> contentList = getFolder(title).getContentList();
		Collections.sort(contentList, new Comparator<Content>() {
			@Override
			public int compare(Content c1, Content c2) {
				int v1 = c1.getVisibility().compareTo(c2.getVisibility());
				
				if(v1 == 0) {
					return c1.getTitle().compareTo(c2.getTitle());
				}
				return v1;
			}
		});
		
		for(Content c : contentList) {
			System.out.println(c.getVisibility() + " " + c.getTitle() + "(" + title + ")");
		}
	}
}
