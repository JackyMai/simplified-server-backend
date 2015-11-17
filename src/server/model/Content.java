package server.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Content {
	public enum Type {
		TEXT,
		MUSIC
	}
	
	public enum Visibility {
		PUBLIC,
		FRIENDS,
		PRIVATE
	}
	
	private Type type;
	private Member owner;
	private String title;
	private Visibility visibility;
	private ArrayList<String> tagList = new ArrayList<String>();
	
	public Content(Type type, Member owner, String title, Visibility visibility, String tags) {
		this.type = type;
		this.owner = owner;
		this.title = title;
		this.visibility = visibility;
		addTags(tags);
	}
	
	public Type getType() {
		return type;
	}
	
	public Member getOwner() {
		return owner;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Visibility getVisibility() {
		return visibility;
	}
	
	private void addTags(String tags) {
		String[] parts = tags.split(",");
		tagList.addAll(Arrays.asList(parts));
	}
	
	public boolean hasTag(String tag) {
		for(String s : tagList) {
			if(s.equals(tag)) {
				return true;
			}
		}
		return false;
	}
	
	public String getAllTags() {
		String tags = tagList.get(0);
		
		for(int i=1; i<tagList.size(); i++) {
			tags+= ", " + tagList.get(i);
		}
		return tags;
	}
}
