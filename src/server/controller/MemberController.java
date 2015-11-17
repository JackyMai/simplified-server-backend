package server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import server.model.Member;

public class MemberController {
	private ArrayList<Member> memberList;
	
	public MemberController() {
		memberList = new ArrayList<Member>();
	}
	
	public boolean checkValidity(String[] parts, int lineNumber) {
		if(parts.length < 3) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": invalid format, missing field");
		} else if(parts.length > 3) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": invalid format, too many fields");
		}
		
		String name = parts[1];
		
		if(name.equals("")) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": invalid format, empty member name");
		} else if(containsName(name)) {
			throw new IllegalArgumentException("ERROR at line " + lineNumber + ": duplicate member name");
		}
		
		return true;
	}
	
	public void addMember(Member member) {
		memberList.add(member);
	}
	
	public Member getMember(String name) {
		for(Member m : memberList) {
			if(m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}
	
	public ArrayList<Member> getMemberList() {
		return memberList;
	}
	
	public boolean containsName(String name) {
		for(Member m : memberList) {
			if(m.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void listMembers() {
		Collections.sort(memberList, new Comparator<Member>() {
			@Override
			public int compare(Member m1, Member m2) {
				return m1.getName().compareTo(m2.getName());
			}
		});
		
		for(Member m : memberList) {
			System.out.println(m.getName() + "(" + m.getNickname() + ")");
		}
	}
}
