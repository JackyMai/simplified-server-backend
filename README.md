# Simplified Server Back-end
A web site that hosts such things as texts, music, and other such content typically consists of two parts: the client - the bit that users interact with via their web browsers, and the server - the bit that provides the majority of the functionality.
This implementation will not deal with the content directly, but just with the data about the content, the metadata.

## This implementation has the following functionalities:
1. List all content in the system in visibility order (Public, Friends, Private), and for those with the same visibility, in alphabetical order of title. Use the format [type]:[title] for each content item (do not show the '[' and ']', but do show the ':'). LISTCONTENT
2. List all members in the system in alphabetical order of name. Use the format [member name]([nickname]) for each member. LISTMEMBERS
3. For a specified folder, list all content in a specified folder in visibility order (first Public, the Friends, then Private) and for those with the same visibility in alphabetical order of title. Use the format [visibility] [content title]([folder title]) LISTFOLDER:folder title
4. For a specified member and visibility, list all content owned by that member with the specified visibility in alphabetical order of title. Use the format [member name] [content title] ([visibility]) LISTMEMBERCONTENT:member name:visibility
5. For a specified member, list all content that member can see that is not owned by that member. The order is alphabetical by title (that is, visibility is ignored in this case). Use the format [content title] ([owner name], visibility). LISTVISIBLECONTENT:member name
6. For a specified member and tag, list all content that that member can see, including that owned by that member, that has that tag. The order is alphabetical by title (ignore visibility). Use the format [content title] ([owner name], [visibility], [all tags]) LISTCONTENTWITHTAG:member name:tag
7. List all content that is not owned by any member. The order is visibility order (Public, Friends, Private), and for those with the same visibility in alphabetical order of title. Use the format [content title] ([visibility]). LISTUNOWNED
8. List all members that do not own any content. The order is alphabetical order of name. Use the format [member name]. LISTCONTENTLESS

## Usage:
The expected usage for this system is:
```
command_line_prompt> java -jar <runnablejarfile> <datafile> <command>
```

## Input Format
The input consists of 4 sections in the following order:
1. Members. This section lists all the members. Information for each member is on one line. The line consists of three fields. The first field is the string MEMBER, the second is the member's name, and the third is the member's nickname. No two members can have the same name (that is, names must be unique), but they can have the same nickname. The fields are separated by a single tab character.
2. Friends. This lists all the friends of a member. Each line consists of the 3 fields; the string FRIEND, the name of the member, and the name of the friend, again tab separated. If a member has multiple friends, then there will be multiple lines for that member.
3. Content. This lists all the content. Each line consists of 5 fields. The first field indicates the type of the content, either TEXT or MUSIC. The next field is the name of the owner. Field 3 is the name of the content, which must be unique. Field 4 is the visibility of the content, one of Public, Friends, or Private. Field 5 is a comma-separated list of tags.
4. Folders. This section lists what folders the content belongs to (if any). The format is there are 4 fields: the string FOLDER, the name of the owner of the folder, the name of the folder, which must be unique, and the name of the content in the folder. When there are multiple content items in an folder, there will be multiple lines, one for each item.

Also, the input uses "#" at the beginning of a line as a comment character, and such lines are to be ignored. Also, lines that are empty or blank should also be just ignored.

The sections of the input are expected to be in the order above.
