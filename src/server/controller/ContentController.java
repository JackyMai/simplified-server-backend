package server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import server.model.Content;
import server.model.Content.Visibility;
import server.model.Member;

public class ContentController {
	private ArrayList<Content> contentList;
	private MemberController memberCtrl;
	
	public ContentController(MemberController memberCtrl) {
		this.contentList = new ArrayList<Content>();
		this.memberCtrl = memberCtrl;
	}
	
	public boolean checkValidity(String[] parts, int lineNumber) {
		String owner = parts[1];
		String title = parts[2];
		String visibility = parts[3];
		
		if(!owner.equals("") && !memberCtrl.containsName(owner)) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": member does not exist");
		} else if(title.equals("")) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": invalid foramt, empty title");
		} else if(getContent(title) != null) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": duplicate content title (albeit with different type)");
		} else if(!checkVisibility(visibility)) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": invalid format, unknown visibility");
		} else if(parts.length > 5) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": invalid format, too many fields");
		}
		
		return true;
	}
	
	public boolean checkVisibility(String visibility) {
		for(Visibility v : Visibility.values()) {
			if(v.name().equals(visibility.toUpperCase())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkOwner(String owner, String content) {
		for(Content c : contentList) {
			if(c.getTitle().equals(content)) {
				if(c.getOwner().getName().equals(owner)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	public void addContent(Content content) {
		contentList.add(content);
	}
	
	public Content getContent(String Title) {
		for(Content c : contentList) {
			if(c.getTitle().equals(Title)) {
				return c;
			}
		}
		
		return null;
	}
	
	public void listContent() {
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
			System.out.println(c.getType().toString() + ": " + c.getTitle());
		}
	}
	
	public void listMemberContent(String memberName, String visibility) {
		Collections.sort(contentList, new Comparator<Content>() {
			@Override
			public int compare(Content c1, Content c2) {
				return c1.getTitle().compareTo(c2.getTitle());
			}
		});
		
		for(Content c : contentList) {
			if(c.getOwner().getName().equals(memberName) 
					&& c.getVisibility().toString().equals(visibility.toUpperCase())) {
				System.out.println(memberName + " " + c.getTitle() + "(" + c.getVisibility().toString() + ")"); 
			}
		}
	}
	
	public void listVisibleContent(String memberName) {
		Collections.sort(contentList, new Comparator<Content>() {
			@Override
			public int compare(Content c1, Content c2) {
				return c1.getTitle().compareTo(c2.getTitle());
			}
		});
		
		for(Content c : contentList) {
			if(c.getOwner() != null 
					&& !c.getOwner().getName().equals(memberName)) {
				Boolean print = false;
				if(c.getVisibility().equals(Visibility.PUBLIC)) {
					print = true;
				} else if(c.getVisibility().equals(Visibility.FRIENDS) 
						&& c.getOwner().isFriend(memberName)) {
					print = true;
				}
				
				if(print) {
					System.out.println(c.getTitle() + " (" + c.getOwner().getName() + ", " + c.getVisibility() + ")");
				}
			}
		}
	}
	
	public void listContentWithTag(String memeberName, String tag) {
		Collections.sort(contentList, new Comparator<Content>() {
			@Override
			public int compare(Content c1, Content c2) {
				return c1.getTitle().compareTo(c2.getTitle());
			}
		});
		
		for(Content c : contentList) {
			if(c.hasTag(tag)) {													// If content has the tag specified
				Boolean print = false;
				if(c.getOwner().getName().equals(memeberName)) {				// If the member is owner of content
					print = true;
				} else if(c.getVisibility().equals(Visibility.PUBLIC)) {		// If the visibility of the content is public
					print = true;
				} else if(c.getVisibility().equals(Visibility.FRIENDS) 			// If the visibility is "Friends" and member is friend of owner
						&& c.getOwner().isFriend(memeberName)) {
					print = true;
				}
				if(print) {
					System.out.println(c.getTitle() + " (" + c.getOwner().getName() + ", " + c.getVisibility().toString() + ", " + c.getAllTags());
				}
			}
		}
	}
	
	public void listUnowned() {
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
			if(c.getOwner() == null) {
				System.out.println(c.getTitle() + " (" + c.getVisibility().toString() + ")");
			}
		}
	}
	
	public void listContentless() {
		ArrayList<Member> memberList = memberCtrl.getMemberList();
		
		for(Content c : contentList) {
			if(c.getOwner() != null) {
				memberList.remove(c.getOwner());
			}
		}
		
		Collections.sort(memberList, new Comparator<Member>() {
			@Override
			public int compare(Member m1, Member m2) {
				return m1.getName().compareTo(m2.getName());
			}
		});
		
		for(Member m : memberList) {
			System.out.println(m.getName());
		}
	}
}
