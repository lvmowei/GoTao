package com.mwlv.gotao.sgf;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mwlv.gotao.board.Coordinate;
import com.mwlv.gotao.util.TextFile;

public class SgfFile {

	String filePath = null;
	SgfNode sgfNode = null;
	List<SgfNode> sgfPreNodeLst = null;
	private String fileComment = "";
	
	public SgfFile(String filePath) {
		super();
		this.filePath = filePath;
		sgfPreNodeLst = new ArrayList<SgfNode>();
	}

	public SgfFile() {
		sgfPreNodeLst = new ArrayList<SgfNode>();
	}

	public void load() throws Exception {
		String sgfStr = TextFile.read(filePath);
		parseSgfStr(sgfStr);
	}
	
	public void parseSgfStr(String sgfStr){
		sgfStr = sgfStr.substring(1, sgfStr.length());
//		String regex = "\\((.+)\\)";
//
//		Pattern pattern = Pattern.compile(regex);
//		Matcher matcher = pattern.matcher(sgfStr);
//		if (matcher.matches()) {
//			sgfStr = matcher.group(1);
//		} else {
//			System.out.println("no matches!!");
//		}
		String[] ds = sgfStr.split(";");

		int index;
		List<String> sgfNodeStrLst = new ArrayList<String>();
		
		for (String s : ds) {
			if (s.equals(""))
				continue;
			index = s.lastIndexOf("]");
			if (index == (s.length() - 1)) {
				sgfNodeStrLst.add(s);
			} else {
				String nodeStr = s.substring(0, index + 1);
				sgfNodeStrLst.add(nodeStr);
				for (int i = index + 1; i < s.length(); i++) {
					sgfNodeStrLst.add(String.valueOf(s.charAt(i)));
				}
			}
		}
		SgfNode sgfTreeNode = new SgfNode();
		parseSgfTree(sgfTreeNode, sgfNodeStrLst);
		
		/*第一个节点不是分支节点的情况在第一个点上加一个空*/
		if(sgfTreeNode.getSgfNodeStr() != null){
			sgfNode = new SgfNode();
			sgfNode.setNextSgfNode(sgfTreeNode);
		}else{
			sgfNode = sgfTreeNode;
		}
		sgfNode.setComment(fileComment);
	}

	private int parseSgfTree(SgfNode sgfNode, List<String> sgfNodeStrLst) {

		SgfNode currSgfNode = sgfNode;
		int currIndex;
		for (currIndex = 0;currIndex<sgfNodeStrLst.size();) {

			String sgfNodeStr = sgfNodeStrLst.get(currIndex);
			currIndex++;
			
			if (sgfNodeStr.equals("(")) {
				List<SgfNode> childrenSgfNodes;
				if (currSgfNode.getChildrenSgfNodes() == null) {
					childrenSgfNodes = new ArrayList<SgfNode>();
					currSgfNode.setChildrenSgfNodes(childrenSgfNodes);
				} else {
					childrenSgfNodes = currSgfNode.getChildrenSgfNodes();
				}
				SgfNode chrildSgfNode = new SgfNode();
				int offset = parseSgfTree(chrildSgfNode, sgfNodeStrLst.subList(currIndex, sgfNodeStrLst.size()));
				childrenSgfNodes.add(chrildSgfNode);
				currIndex += offset;
			} else if (sgfNodeStr.startsWith("B[") || sgfNodeStr.startsWith("W[")) {
				if(currSgfNode.getSgfNodeStr() == null)
					currSgfNode.setSgfNodeStr(sgfNodeStr);
				else{
					SgfNode nextSgfNode = new SgfNode(sgfNodeStr);
					currSgfNode.setNextSgfNode(nextSgfNode);
					currSgfNode = nextSgfNode;
				}
			} else if (sgfNodeStr.equals(")")) {
				return currIndex;
			} else {
				parseSgfInfo(sgfNodeStr);
			}
		}
		return currIndex;
	}
	
	private void parseSgfInfo(String sgfInfoNode){
		int abIndex = sgfInfoNode.indexOf("AB[");
		int awIndex = sgfInfoNode.indexOf("AW[");
		
		if(abIndex != -1){
			int start = abIndex + 2;
			int end = abIndex + 5;
			while(sgfInfoNode.charAt(start) == '[' && sgfInfoNode.charAt(end) == ']'){
				String co = sgfInfoNode.substring(start, end + 1);
				sgfPreNodeLst.add(new SgfNode("B" + co));
				start+=4;
				end+=4;
			}
		}
		
		if(awIndex != -1){
			int start = awIndex + 2;
			int end = awIndex + 5;
			while(sgfInfoNode.charAt(start) == '[' && sgfInfoNode.charAt(end) == ']'){
				String co = sgfInfoNode.substring(start, end + 1);
				sgfPreNodeLst.add(new SgfNode("W" + co));
				start+=4;
				end+=4;
			}
		}
		
		int cStartIndex = sgfInfoNode.indexOf("C[");
		if(cStartIndex != -1){
			int cEndIndex = sgfInfoNode.indexOf("]", cStartIndex);
			this.fileComment  = sgfInfoNode.substring(cStartIndex + 2, cEndIndex);
		}
	}
	
	public int getSenteColor(){
		if(sgfNode.getNextSgfNode() != null)
			return sgfNode.getNextSgfNode().getBw();
		else 
			return sgfNode.getChildrenSgfNodes().get(0).getBw();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public SgfNode getSgfNode() {
		return sgfNode;
	}

	public void setSgfNode(SgfNode sgfNode) {
		this.sgfNode = sgfNode;
	}

	public List<SgfNode> getSgfPreNodeLst() {
		return sgfPreNodeLst;
	}

	public void setSgfPreNodeLst(List<SgfNode> sgfPreNodeLst) {
		this.sgfPreNodeLst = sgfPreNodeLst;
	}

	public String getFileComment() {
		return fileComment;
	}

	public void setFileComment(String fileComment) {
		this.fileComment = fileComment;
	}
	
	
	
}
