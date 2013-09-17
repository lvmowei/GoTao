package com.mwlv.gotao.board;

import com.mwlv.gotao.sgf.SgfNode;

public class BrowseGoKifuBoard extends Board {
	
	protected Function branchListener;
	
	/**
	 * 返回还能否前进
	 */
	public int forward() {

		position++;
		SgfNode nextNode;
		SgfNode currNode;
		if(position == 0){
			currNode = sgfFile.getSgfNode();
			
			if(currNode.getChildrenSgfNodes() != null){
				/*第一步出现分支，回调分支显示*/
				branchListener.apply(currNode);
				nextNode = currNode.getChildrenSgfNodes().get(branchIndex);
				branchList = currNode.getChildrenSgfNodes();
				list.clear();
				this.put(nextNode);
				listener.apply((branchIndex + 1) + "/" + branchList.size() + "\r\n" + nextNode.getComment());
			}else{
				/*第一步无分支*/
				if(position > getCount() - 1){
					nextNode = currNode.getNextSgfNode();
					this.put(nextNode);
					listener.apply(nextNode.getComment());
				} else{
					PieceProcess p = getPieceProcess(position);
					currentGrid.executePieceProcess(p, false);
					nextNode = p.getSgfNode();
					listener.apply(nextNode.getComment());
				}
			}
		}else{
			PieceProcess p = getPieceProcess(position - 1);
			currNode = p.getSgfNode();
			if(p.getSgfNode().getChildrenSgfNodes() != null){
				branchListener.apply(currNode);
				branchList = currNode.getChildrenSgfNodes();
				nextNode = currNode.getChildrenSgfNodes().get(branchIndex);
				for(int i=getCount()-1;i>=position;i--)
					list.remove(i);
				this.put(nextNode);
				listener.apply((branchIndex + 1) + "/" + branchList.size() + "\r\n" + nextNode.getComment());
			}else{
				if(position > getCount() - 1){
					nextNode = p.getSgfNode().getNextSgfNode();
					this.put(nextNode);
					listener.apply(nextNode.getComment());
				} else{
					p = getPieceProcess(position);
					currentGrid.executePieceProcess(p, false);
					nextNode = p.getSgfNode();
					listener.apply(nextNode.getComment());
				}
			}
		}
		
		if(nextNode.getNextSgfNode() == null && nextNode.getChildrenSgfNodes() == null)
			return 0;
		else return 1;
	}

	/**返回还能否后退
	 * @return
	 */
	public int back() {
		
		PieceProcess p=list.get(position);
		currentGrid.executePieceProcess(p, true);
		position--;
		branchIndex = 0;
		branchList = null;
		if(position == -1){
			listener.apply(sgfFile.getSgfNode().getComment());
			return 0;	
		}
		else{
			p=list.get(position);
			listener.apply(p.getSgfNode().getComment());
			return 1;
		} 
	}
	
	public int nextBranch(){
		branchIndex++;
		PieceProcess p=list.remove(getCount()-1);
		currentGrid.executePieceProcess(p, true);
		SgfNode nextNode = branchList.get(branchIndex);
		this.put(nextNode);
		listener.apply((branchIndex + 1) + "/" + branchList.size() + "\r\n" + nextNode.getComment());
		if(branchIndex >= branchList.size() - 1)
			return 0;
		else return 1;
	}
	
	public int preBranch(){
		branchIndex--;
		PieceProcess p=list.remove(getCount()-1);
		currentGrid.executePieceProcess(p, true);
		SgfNode preNode = branchList.get(branchIndex);
		this.put(preNode);
		listener.apply((branchIndex + 1) + "/" + branchList.size() + "\r\n" + preNode.getComment());
		if(branchIndex <= 0)
			return 0;
		else return 1;
	}
	
	public int confirmBranch(){
		//position++;
//		for(int i=getCount()-1;i>position+1;i--)
//			list.remove(i);
		branchIndex = 0;
		branchList = null;
		PieceProcess p=list.get(position);
		if(p.getSgfNode().getNextSgfNode() != null || p.getSgfNode().getChildrenSgfNodes() != null)
			return 1;
		else return 0;
	}
	
	public int cancelBranch(){
		PieceProcess p=list.remove(getCount()-1);
		currentGrid.executePieceProcess(p, true);
		position--;
		branchIndex = 0;
		branchList = null;
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
			finishedPut();
			return true;
		}
		return false;
	}
	
	public void resetBoard(){
		position = -1;
		branchIndex = 0;
		branchList = null;
		for(int i = 0;i<n;i++)
			for(int j=0;j<n;j++)
				currentGrid.getA()[i][j] = initGrid.getA()[i][j];
	}
	
	public Function getBranchListener() {
		return branchListener;
	}

	public void setBranchListener(Function branchListener) {
		this.branchListener = branchListener;
	}
}
