package com.mwlv.gotao.board;


/**子棋盘
 * @author mwlv
 *
 */
public class SubBoard extends Board {
	private Board parent;
	private int position = -1;

	public SubBoard(Board parent) {
		super();
		this.parent = parent;
	}

	public int forward() {
		if (position + 1 < parent.getCount()) {
			position++;
			PieceProcess p = parent.getPieceProcess(position);
			this.addPieceProcess(p);
		}
		return position;
	}

	public int back() {
		if (position < 0)return -1; 

		this.removePieceProcess();
		position--;
		return position;
	}

	public void gotoIt(int n) {
		if (n > parent.getCount() || n < 0)
			return;

		this.cleanGrid();
		for (int i = 0; i < n; i++) {
			forward();
		}
	}
	@Override
	public boolean put(int x,int y){
		boolean r=super.put(x, y);
		if(r==true){
			position++;
		}
		return r;
	}

	@Override
	public void resetBoard() {
		// TODO Auto-generated method stub
		
	}
}
