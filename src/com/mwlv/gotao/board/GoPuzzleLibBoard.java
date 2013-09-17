package com.mwlv.gotao.board;

import java.util.ArrayList;
import java.util.List;

import com.mwlv.gotao.sgf.SgfNode;

public class GoPuzzleLibBoard extends Board {
	
	private boolean isRight = true;
	
	private boolean succeed = false;
	
	protected List<PieceProcess> rightPieceList = new ArrayList<PieceProcess>();

	@Override
	public int forward() {
		return 0;
	}

	@Override
	public int back() {
		return 0;
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
			position++;
			finishedPut();
			if(!succeed)
				if(pieceIsRight(p)){
					aiPutPiece();
				}
			return true;
		}
		return false;
	}
	
	

	private void aiPutPiece() {
		
		PieceProcess p = null;
		if(nextSgfNodes != null){
			SgfNode nextSgfNode = nextSgfNodes.get(0);
			nextSgfNodes = nextSgfNode.getNextSgfNodes();
			if(nextSgfNodes == null && !nextSgfNode.getComment().equals("succeed")){
				succeed = false;
				isRight = false;
			}
			p=new PieceProcess(nextSgfNode.getBw(),nextSgfNode.getC());
			p.setSgfNode(nextSgfNode);
		
		
		if (currentGrid.putPiece(p)) {
			if(!check(p)){
				currentGrid.executePieceProcess(p, true);
				return;
			}
			
			list.add(p);
			position++;
			rightPieceList.add(p);
			finishedPut();
			return;
		}
		}
	}

	private boolean pieceIsRight(PieceProcess p) {
		
		if(!isRight)
			return isRight;
		
		for(SgfNode nextSgfNode:nextSgfNodes){
			if(nextSgfNode.getC().x == p.c.x && nextSgfNode.getC().y == p.c.y){
				nextSgfNodes = nextSgfNode.getNextSgfNodes();
				if(nextSgfNodes == null && nextSgfNode.getComment().equals("succeed")){
					succeed = true;
				}
				p.setSgfNode(nextSgfNode);
				rightPieceList.add(p);
				isRight = true;
				return isRight;
			}
		}
		
		isRight = false;
		return isRight;
	}
	
	public void resetBoard(){
		position = -1;
		isRight = true;
		succeed = false;
		rightPieceList.clear();
		list.clear();
		expBw = sgfFile.getSenteColor();
		nextSgfNodes = sgfFile.getSgfNode().getNextSgfNodes();
		for(int i = 0;i<n;i++)
			for(int j=0;j<n;j++)
				currentGrid.getA()[i][j] = initGrid.getA()[i][j];
	}

	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public boolean isSucceed() {
		return succeed;
	}

	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}
	
	
}
