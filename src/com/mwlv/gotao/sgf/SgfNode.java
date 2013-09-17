package com.mwlv.gotao.sgf;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mwlv.gotao.board.Coordinate;

public class SgfNode {

	/**
	 * 颜色
	 */
	private int bw;
	/**
	 * 坐标
	 */
	private Coordinate c;
	/**
	 * 下一步有分支
	 */
	private List<SgfNode> childrenSgfNodes;
	/**
	 * 下一步无分支
	 */
	private SgfNode nextSgfNode;
	
	/**
	 * 统一处理
	 */
	private List<SgfNode> nextSgfNodes;
	/**
	 * 该节点的原数据
	 */
	private String sgfNodeStr;
	/**
	 * 该节点的comment
	 */
	private String comment = "";

	private static final int ACode = (int) 'a';

	private void praseSgfNode() {
		// String regex = "([BW])\\[([a-z][a-z])\\][.\\r\\n]*";
		//
		// Pattern pattern = Pattern.compile(regex);
		// Matcher matcher = pattern.matcher(sgfNodeStr);
		// if (matcher.matches()) {
		// String bw = matcher.group(1);
		// if("B".equals(bw))
		// this.bw = 1;
		// else
		// this.bw = 2;
		// String co = matcher.group(2);
		// char xChar = co.charAt(0);
		// char yChar = co.charAt(1);
		//
		// int x = (int) xChar - ACode;
		// int y = (int) yChar - ACode;
		//
		// this.c = new Coordinate(x, y);
		// } else {
		// System.out.println("no matches!!");
		// }

		int bIndex = sgfNodeStr.indexOf("B[");
		int wIndex = sgfNodeStr.indexOf("W[");

		if (bIndex != -1) {
			this.bw = 1;
			char xChar = sgfNodeStr.charAt(bIndex + 2);
			char yChar = sgfNodeStr.charAt(bIndex + 3);

			int x = (int) xChar - ACode;
			int y = (int) yChar - ACode;

			this.c = new Coordinate(x, y);
		} else if (wIndex != -1) {
			this.bw = 2;
			char xChar = sgfNodeStr.charAt(wIndex + 2);
			char yChar = sgfNodeStr.charAt(wIndex + 3);

			int x = (int) xChar - ACode;
			int y = (int) yChar - ACode;

			this.c = new Coordinate(x, y);
		}
		
		int cStartIndex = sgfNodeStr.indexOf("C[");
		if(cStartIndex != -1){
			int cEndIndex = sgfNodeStr.indexOf("]", cStartIndex);
			this.comment = sgfNodeStr.substring(cStartIndex + 2, cEndIndex);
		}
		
		int nStartIndex = sgfNodeStr.indexOf("N[");
		if(nStartIndex != -1){
			int nEndIndex = sgfNodeStr.indexOf("]", nStartIndex);
			this.comment = sgfNodeStr.substring(nStartIndex + 2, nEndIndex) +"\r\n"+ comment;
		}
	}

	public SgfNode(String sgfNodeStr) {
		this.sgfNodeStr = sgfNodeStr;
		praseSgfNode();
	}

	public SgfNode() {
		// TODO Auto-generated constructor stub
	}

	public int getBw() {
		return bw;
	}

	public void setBw(int bw) {
		this.bw = bw;
	}

	public Coordinate getC() {
		return c;
	}

	public void setC(Coordinate c) {
		this.c = c;
	}

	public List<SgfNode> getChildrenSgfNodes() {
		return childrenSgfNodes;
	}

	public void setChildrenSgfNodes(List<SgfNode> childrenSgfNodes) {
		this.childrenSgfNodes = childrenSgfNodes;
	}

	public SgfNode getNextSgfNode() {
		return nextSgfNode;
	}

	public void setNextSgfNode(SgfNode nextSgfNode) {
		this.nextSgfNode = nextSgfNode;
	}

	public String getSgfNodeStr() {
		return sgfNodeStr;
	}

	public void setSgfNodeStr(String sgfNodeStr) {
		this.sgfNodeStr = sgfNodeStr;
		praseSgfNode();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<SgfNode> getNextSgfNodes() {
		if(nextSgfNode != null){
			nextSgfNodes = new ArrayList<SgfNode>();
			nextSgfNodes.add(nextSgfNode);
		}else
			nextSgfNodes = childrenSgfNodes;
		
		return nextSgfNodes;
	}

	public void setNextSgfNodes(List<SgfNode> nextSgfNodes) {
		this.nextSgfNodes = nextSgfNodes;
	}
}
