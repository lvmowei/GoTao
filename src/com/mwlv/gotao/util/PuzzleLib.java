package com.mwlv.gotao.util;

public class PuzzleLib {

	static String[][] puzzleLib = new String[][]{{
							"(;AB[cb][cc][dd][ed][fd][fc][gc][gb][ga]AW[bb][bc][bd][cd][de][cf][ff][ge][gd][hc][hb][ha][id][eb][ec]C[黑先]SZ[19](;B[ea];W[da]C[黑托，急所！];B[db];W[fa](;B[fb];W[ca];B[dc]C[succeed])(;B[dc]	;W[ca];B[fb]C[succeed]))(;B[ca]	;W[ea]	;B[dc];W[db]C[被吃啦])(;B[dc];W[ca];B[da];W[ba]C[无法做活])(;B[db];W[ea](;B[dc];W[ca]C[被吃啦])(;B[da];W[dc]C[自杀啊])))",
							"(;AB[bb][cb][cc][cd][dd][ed][fd][gd][hd][hc][da]AW[ab][bc][bd][be][ce][de][ee][fe][ge][he][ie][ic][jc][hb][gc][fc][db][dc]C[黑先]SZ[19](;B[gb];W[fb]C[唯一的应手](;B[fa];W[eb]C[好棋！];B[ha]C[恭喜答对！佩服佩服！])(;B[eb];W[fa]C[次序有问题吧];B[ec];W[ba]C[就差一点点，换种方法再试试。]))(;B[ec];W[gb];B[eb];W[ba]C[无法做活])(;B[fa];W[gb];B[ec];W[fb];B[eb];W[ba]C[换种方法试试])(;B[eb];W[gb];B[ec];W[ba]C[再换个方法试试])(;B[fb];W[gb];B[ec];W[fa];B[eb];W[ba]C[再换个方法试试]))",
							"(;AB[bd][bc][cc][dc][db][ae]AW[be][bf][ad][cd][dd][ed][ec][eb][gb]VW[aa:jj]SZ[19]C[黑先活](;B[ba];W[ab]C[要点！点，你怎么办？](;B[bb]C[接不归了！没想到能走出这样的妙手，黑胜。])(;B[ac];W[da]C[黑棋失败。]))(;B[ac];W[da]C[先扳。];B[ca];W[bb]C[再点刀把五，黑棋失败。])(;B[bb];W[ca]C[很象要点的一手。];B[da];W[ba];B[ac];W[aa]C[这是盘角曲四，黑死棋。])(;B[da];W[ba]C[扩大眼位很多场合是好手，但这次不是。];B[bb];W[ca];B[ac];W[aa]C[盘角曲四，黑死棋。])(;B[ab];W[ba]C[点杀了，黑棋失败。])(;B[ca];W[bb]C[黑棋失败。]))"
							},
							{
							"",
							"",
							""}};

	public static String getPuzzle(int groupId,int puzzleId){
		return puzzleLib[groupId][puzzleId];
	}
	
}
