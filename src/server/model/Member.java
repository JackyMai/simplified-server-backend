package server.model;

import java.util.ArrayList;

public class Member {
	private String name;
	private String nickname;
	private ArrayList<Member> friendList;		// All members inside this list can view contents owned by this member
	
	public Member(String name, String nickname) {
		this.name = name;
		this.nickname = nickname;
		this.friendList = new ArrayList<Member>();
	}
	
	public String getName() {
		return name;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void addFriend(Member friend) {
		friendList.add(friend);
	}
	
	public boolean isFriend(String friend) {
		for(Member m : friendList) {
			if(m.getName().equals(friend)) {
				return true;
			}
		}
		return false;
	}
}
