package com.mwlv.gotao.board;

import java.util.ArrayList;
import java.util.List;

import com.mwlv.gotao.sgf.SgfNode;


/**
 * 每一步棋的记录
 * @author yangjiandong
 *
 */
public class PieceProcess {
	/**
	 * 颜色
	 */
	public int bw;
	/**
	 * 坐标
	 */
	public Coordinate c;
	/**
	 * 吃子
	 */
	public List<PieceProcess> removedList;
	
	private SgfNode sgfNode;
	
	public int resultBlackCount;
	public int resultWhiteCount;

	public PieceProcess(int bw, Coordinate c,List<PieceProcess> removedList) {
		this.bw = bw;
		this.c = c;
		this.removedList=removedList;
	}
	
	public PieceProcess(int bw, Coordinate c ) {
		this.bw = bw;
		this.c = c;
		this.removedList=new ArrayList<PieceProcess>();
	}

	public SgfNode getSgfNode() {
		return sgfNode;
	}

	public void setSgfNode(SgfNode sgfNode) {
		this.sgfNode = sgfNode;
	}
	
	
}