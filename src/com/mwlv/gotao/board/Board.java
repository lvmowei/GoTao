package com.mwlv.gotao.board;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.mwlv.gotao.sgf.SgfFile;
import com.mwlv.gotao.sgf.SgfNode;
import com.mwlv.gotao.util.Utils;

/**棋盘
 * @author Administrator
 *
 */
public abstract class Board {

	/**
	 * 大小
	 */
	public static int n = 19;
	/**
	 * 空
	 */
	public static final int None = 0;
	/**
	 * 黑子
	 */
	public static final int Black = 1;
	/**
	 * 白字
	 */
	public static final int White = 2;

	/**
	 * 行棋记录
	 */
	protected List<PieceProcess> list = new ArrayList<PieceProcess>();
	
	/**
	 * 当前棋盘状态
	 */
	protected Grid currentGrid = new Grid();
	
	protected Grid initGrid = new Grid();
	
	/**
	 * 位置
	 */
	protected int position = -1;
	
	protected int branchIndex = 0;
	
	protected List<SgfNode> branchList;
	
	protected List<SgfNode> nextSgfNodes;
	
	protected SgfFile sgfFile;
	
	/**
	 * 轮到哪一方下
	 */
	protected int expBw = Black;
	
	/**
	 * 用來回显
	 */
	protected Function listener;
	
	public abstract int forward();
	
	public abstract int back();
	
	public abstract void resetBoard();

//	public void gotoIt(int n) {
//		if (n > parent.getCount() || n < 0)
//			return;
//
//		this.cleanGrid();
//		for (int i = 0; i < n; i++) {
//			forward();
//		}
//	}
	
//	@Override
//	public boolean put(int x,int y){
//		boolean r=super.put(x, y);
//		if(r==true){
//			position++;
//		}
//		return r;
//	}

	public void cleanAll(){
		this.currentGrid = new Grid();
		this.list = new ArrayList<PieceProcess>();
		this.expBw = Black;
		position = -1;
	}
	
	public boolean put(int x, int y) {
		Coordinate c = new Coordinate(x, y);
		PieceProcess p=new PieceProcess(expBw,c);
		
		if (currentGrid.putPiece(p)) {
			if(!check(p)){
				currentGrid.executePieceProcess(p, true);
				return false;
			}
			
			list.add(p);
			finishedPut();
			return true;
		}
		return false;
	}
	
	public boolean put(SgfNode sgfNode) {
		Coordinate c = sgfNode.getC();
		PieceProcess p=new PieceProcess(sgfNode.getBw(),c);
		
		if (currentGrid.putPiece(p)) {
			if(!check(p)){
				currentGrid.executePieceProcess(p, true);
				return false;
			}
			p.setSgfNode(sgfNode);
			list.add(p);
			finishedPut();
			return true;
		}
		return false;
	}
	
	public boolean add(int bw,Coordinate c) {
		PieceProcess p=new PieceProcess(bw,c);
		currentGrid.setValue(c, bw);
		return true;
	}
	
	protected void finishedPut(){
		expBw = Utils.getReBW(expBw);
		//postEnvet();
	}
	
	/**打劫检测
	 * @param p
	 * @return
	 */
	protected boolean check(PieceProcess p){
		int i=0;
		for(PieceProcess pp:list){
			if(pp.resultBlackCount==p.resultBlackCount 
					&& pp.resultWhiteCount==p.resultWhiteCount){
				if(isOverEqualse(i,p)){
					return false;
				}
			}
			i++;
		}
		return true;
	}
	
	private boolean isOverEqualse(int position,PieceProcess p){
		Board sb=getSubBoard(position+1);
		return sb.currentGrid.equals(this.currentGrid);
	}
	
	//------------------------------------------------------------------rebuilt
	
	public SubBoard getSubBoard(int index){
		SubBoard board=new SubBoard(this);
		board.gotoIt(index);
		return board;
	}
	
	protected void cleanGrid(){
		this.currentGrid = new Grid();
	}
	
	protected void addPieceProcess(PieceProcess p) {
		currentGrid.executePieceProcess(p, false);
		list.add(p);
		finishedPut();
	}
	
	protected void removePieceProcess(){
		if(list.size()==0)return;
		PieceProcess p=list.remove(getCount()-1);
		currentGrid.executePieceProcess(p, true);
		finishedPut();
	}

	//------------------------------------------------------------------getter
	

	public int getValue(int x, int y) {
		return currentGrid.getValue(new Coordinate(x, y));
	}

	private void postEnvet() {
		if(listener==null)return;
		listener.apply(getCount(),expBw);
	}
	
	public void setListener(Function listener){
		this.listener=listener;
	}

	public Coordinate getLastPosition() {
		if(getCount()==0)return null;
		return list.get(getCount() - 1).c;
	}
	
	public List<PieceProcess> getValidList(){
		List<PieceProcess> validList = new ArrayList<PieceProcess>();
		for(int i = 0;i<=position;i++){
			validList.add(list.get(i));
		}
		return validList;
	}

	public int getCount() {
		return list.size();
	}
	
	public PieceProcess getPieceProcess(int i){
		if(i>=getCount())return null;
		return list.get(i);
	}
	
	//------------------------------------------------------------------status
	
	public Bundle saveState() {
        Bundle map = new Bundle();
        map.putInt("count", getCount());
        int i=0;
        for(PieceProcess p:list){
        	map.putInt("x"+i, p.c.x);
        	map.putInt("y"+i, p.c.y);
        	i++;
        }
        return map;
    }
	
	public void restoreState(Bundle map) {
		int n=map.getInt("count");
		for(int i=0;i<n;i++){
			int x=map.getInt("x"+i);
			int y=map.getInt("y"+i);
			
			this.put(x, y);
		}
    }

	public List<PieceProcess> getList() {
		return list;
	}

	public void setList(List<PieceProcess> list) {
		this.list = list;
	}

	public SgfFile getSgfFile() {
		return sgfFile;
	}

	public void setSgfFile(SgfFile sgfFile) {
		this.sgfFile = sgfFile;
		List<SgfNode> sgfPreNodeLst = sgfFile.getSgfPreNodeLst();
//		sgfFile.setCurrentNode(sgfFile.getSgfNode());
		position=-1;
		initGrid = new Grid();
		expBw = sgfFile.getSenteColor();
		for(SgfNode sn : sgfPreNodeLst){
			this.add(sn.getBw(),sn.getC());
			initGrid.setValue(sn.getC(), sn.getBw());
		}
		nextSgfNodes = sgfFile.getSgfNode().getNextSgfNodes();
	}

	public List<SgfNode> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<SgfNode> branchList) {
		this.branchList = branchList;
	}
	
	
}
