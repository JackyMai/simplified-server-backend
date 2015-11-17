package server.controller;

import server.model.Member;

public class FriendController{
	private MemberController memberCtrl;
	
	public FriendController(MemberController memberCtrl) {
		this.memberCtrl = memberCtrl;
	}
	
	public boolean checkValidity(String[] parts, int lineNumber) {
		if(parts.length < 3) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": invalid format, missing field");
		} else if(parts.length > 3) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": invalid format, too many fields");
		} 
		
		String name = parts[1];
		String friend = parts[2];
		
		if(!memberCtrl.containsName(name)) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": no such member");
		} else if(!memberCtrl.containsName(friend)) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": no such friend");
		} else if(name.equals(friend)) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": friend with self");
		} else if(memberCtrl.getMember(name).isFriend(friend)) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": duplicate friend");
		}
		
		return true;
	}
	
	public void addFriend(String name, String friend) {
		Member member = memberCtrl.getMember(name);
		Member newFriend = memberCtrl.getMember(friend);
		
		member.addFriend(newFriend);
	}
}
