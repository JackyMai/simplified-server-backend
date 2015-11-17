package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import server.controller.ContentController;
import server.controller.ErrorController;
import server.controller.FolderController;
import server.controller.FriendController;
import server.controller.MemberController;
import server.model.Content;
import server.model.Member;
import server.model.Content.Type;
import server.model.Content.Visibility;
import server.model.Folder;

public class Main {
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		
		String line;
		String[] parts;
		int lineNumber = 1;
		
		MemberController memberCtrl = new MemberController();
		FriendController friendCtrl = new FriendController(memberCtrl);
		ContentController contentCtrl = new ContentController(memberCtrl);
		FolderController folderCtrl = new FolderController(memberCtrl, contentCtrl);
		ErrorController errorCtrl = new ErrorController();
		
		// Reading the .csv file and storing it line by line
		while((line = br.readLine()) != null) {
			parts = line.split("\t");

			if(line.contains("#")) {		// If the line doesn't contain the "#" character
				lineNumber++;
				continue;
			}
			
			try {
				switch (parts[0]) {				// parts[0] indicates the type of input
				case "MEMBER":
					if(memberCtrl.checkValidity(parts, lineNumber)) {
						Member member = new Member(parts[1], parts[2]);
						memberCtrl.addMember(member);
					}
					break;
				case "FRIEND":
					if(friendCtrl.checkValidity(parts, lineNumber)) {
						friendCtrl.addFriend(parts[1], parts[2]);
					}
					break;
				case "TEXT":
				case "MUSIC":
					if(contentCtrl.checkValidity(parts, lineNumber)) {
						String tags = "";					// Start with an empty string in case of blank tags
						if(parts.length == 5) {				// If the tag field is not blank, use it
							tags = parts[4];
						}
						
						Type type = Type.valueOf(parts[0]);
						Member owner = memberCtrl.getMember(parts[1]);
						String title = parts[2];
						Visibility visibility = Visibility.valueOf(parts[3].toUpperCase());
						
						Content content = new Content(type, owner, title, visibility, tags);
						contentCtrl.addContent(content);
					}
					break;
				case "FOLDER":
					if(folderCtrl.checkValidity(parts, lineNumber)) {
						Folder folder;
						Content content = contentCtrl.getContent(parts[3]);
						
						if((folder = folderCtrl.getFolder(parts[2])) != null) {		// If the folder already exists, add content to it
							folder.addContent(content);
						} else {
							folder = new Folder(parts[1], parts[2], content);		// Otherwise create the folder and add content to it
							folderCtrl.addFolder(folder);
						}
					}
					break;
				case "":					// For blank lines
					throw new IllegalArgumentException("ERROR at line " + lineNumber + ": empty lines");
				default:					// Just in case of rubbish inputs
					throw new IllegalArgumentException("ERROR at line " + lineNumber + ": invalid format, incorrect input type");
				}
			} catch (IllegalArgumentException e) {
				errorCtrl.addError(e.getMessage());
			}

			lineNumber++;
		}

		br.close();

		// The output section starts here
		Date date = new Date();
		System.out.println(date.toString());		// Printing the data and time etc.
		System.out.println(args[1]);				// as well as the command entered

		// Splitting the command argument and finding out what functionality to perform
		parts = args[1].split(":");

		switch (parts[0]) {
			case "LISTCONTENT":
				contentCtrl.listContent();
				break;
			case "LISTMEMBERS":
				memberCtrl.listMembers();
				break;
			case "LISTFOLDER":
				folderCtrl.listFolder(parts[1]);
				break;
			case "LISTMEMBERCONTENT":
				contentCtrl.listMemberContent(parts[1], parts[2]);
				break;
			case "LISTVISIBLECONTENT":
				contentCtrl.listVisibleContent(parts[1]);
				break;
			case "LISTCONTENTWITHTAG":
				contentCtrl.listContentWithTag(parts[1], parts[2]);
				break;
			case "LISTUNOWNED":
				contentCtrl.listUnowned();
				break;
			case "LISTCONTENTLESS":
				contentCtrl.listContentless();
				break;
			default:
				break;
		}
		
		System.out.println();						// Empty line for separating result from error
		errorCtrl.printError();
	}
}
