package com.mwlv.gotao.board;

import java.util.Arrays;
import java.util.List;

import com.mwlv.gotao.util.Utils;

/**棋局
 * @author Administrator
 *
 */
public class Grid {
	/**
	 * 棋局状态
	 */
	private int[][] a;

	public Grid() {
		a = new int[Board.n][Board.n];
	}

	/**获取某坐标的棋子状态
	 * @param c
	 * @return
	 */
	public int getValue(Coordinate c) {
		return a[c.x][c.y];
	}

	/**设置某坐标的棋子状态
	 * @param c
	 * @param value
	 */
	public void setValue(Coordinate c, int value) {
		a[c.x][c.y] = value;
	}

	
	/**执行棋子变换
	 * @param p
	 * @param reverse false为下一步吃字 true为上一步还原
	 */
	public void executePieceProcess(PieceProcess p, boolean reverse) {
		if (!reverse) {
			setValue(p.c, p.bw);
			for (PieceProcess pp : p.removedList) {
				setValue(pp.c, Board.None);
			}
		} else {
			for (PieceProcess pp : p.removedList) {
				setValue(pp.c, pp.bw);
			}
			setValue(p.c, Board.None);
		}
	}

	/**落子&提子
	 * @param piece
	 * @return
	 */
	public boolean putPiece(PieceProcess piece) {
		if (!piece.c.isValid())
			return false;
		if (getValue(piece.c) != Board.None)
			return false;

		setValue(piece.c, piece.bw);
		startPick(piece.c, piece.bw, piece.removedList);

		piece.resultBlackCount=getPieceCount(Board.Black);
		piece.resultWhiteCount=getPieceCount(Board.White);
		
		return true;
	}

	/**统计盘面棋子数量
	 * @param bw
	 * @return
	 */
	private int getPieceCount(int bw) {
		int c = 0;
		for (int i = 0; i < Board.n; i++) {
			for (int j = 0; j < Board.n; j++) {
				if (a[i][j] == bw) {
					c++;
				}
			}
		}
		return c;
	}
	
	/**提子
	 * @param c
	 * @param bw
	 * @param removedList
	 */
	private void startPick(Coordinate c, int bw, List<PieceProcess> removedList) {
		int reBw = Utils.getReBW(bw);
		pickOther(c, reBw, removedList);

		//成功提取对方棋子则返回
		if (removedList.size() > 0)
			return;
		//不能提取对方的只能提自己的
		pickSelf(c, bw, removedList);
	}

	private void pickOther(Coordinate c, int bw, List<PieceProcess> removedList) {
		boolean[][] v = new boolean[Board.n][Board.n];

		for (int i = 0; i < 4; i++) {
			Coordinate nc = c.getNear(i);
			Block block = new Block(bw);
			pick(nc, v, bw, block);

			if (!block.isLive()) {
				deleteBlock(block, removedList);
			}
		}
	}

	/**自提处理
	 * @param c
	 * @param bw
	 * @param removedList
	 */
	private void pickSelf(Coordinate c, int bw, List<PieceProcess> removedList) {
		boolean[][] v = new boolean[Board.n][Board.n];
		Block block = new Block(bw);
		pick(c, v, bw, block);
		if (!block.isLive()) {
			deleteBlock(block, removedList);
		}
	}

	/**深度优先递归构造棋块
	 * @param c
	 * @param v
	 * @param bw
	 * @param block
	 */
	private void pick(Coordinate c, boolean[][] v, int bw, Block block) {
		if (c == null)
			return;
		//这个点遍历过
		if (v[c.x][c.y] == true)
			return;

		if (getValue(c) == Board.None) {
			block.addAir(1);
			return;
		} else if (getValue(c) != bw) {
			return;
		}

		v[c.x][c.y] = true;
		block.add(c);

		for (int i = 0; i < 4; i++) {
			Coordinate nc = c.getNear(i);
			pick(nc, v, bw, block);
		}
	}

	/**删除一个棋块
	 * @param block
	 * @param removedList
	 */
	private void deleteBlock(final Block block, final List<PieceProcess> removedList) {
		block.each(new Function() {
			public Object apply(Object... obj) {
				Coordinate c = (Coordinate) obj[0];

				a[c.x][c.y] = Board.None;
				removedList.add(new PieceProcess(block.getBw(),c,null));

				return null;
			}
		});
	}

	// ------------------------------------------------------------------

	@Override
	public String toString() {
		String s = "";
		for (int j = 0; j < Board.n; j++) {
			for (int i = 0; i < Board.n; i++) {
				if (a[i][j] == Board.None) {
					s += " +";
				} else if (a[i][j] == Board.White) {
					s += " o";
				} else if (a[i][j] == Board.Black) {
					s += " x";
				}
			}
			s += "\n";
		}
		return s;
	}
	
	public int[][] getA() {
		return a;
	}

	public void setA(int[][] a) {
		this.a = a;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(a);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grid other = (Grid) obj;
		if (!myEqualse(a, other.a,Board.n))
			return false;
		return true;
	}
	
	private boolean myEqualse(int[][] a,int b[][],int n){
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				if(a[j][i]!=b[j][i])
					return false;
			}
		}
		return true;
	}
}
