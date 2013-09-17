package com.mwlv.gotao.board;

import java.util.ArrayList;
import java.util.List;


/**
 * 棋块
 * @author yangjiandong
 *
 */
public class Block {
	private List<Coordinate> block=new ArrayList<Coordinate>();
	/**
	 * 气数
	 */
	private int airCount=0;
	/**
	 * 颜色
	 */
	private int bw;
	
	public Block(int bw){
		this.bw=bw;
	}
	
	public int getBw(){
		return bw;
	}
	
	public void add(Coordinate c){
		block.add(c);
	}
	
	/**增加气数
	 * @param air
	 */
	public void addAir(int air){
		airCount+=air;
	}
	
	/**棋块是否存活
	 * @return
	 */
	public boolean isLive(){
		if(airCount>0 && block.size()>0)return true;
		return false;
	}
	
	/**处理棋块中的每一个棋子
	 * @param f
	 */
	public void each(Function f){
		for(Coordinate c:block){
			f.apply(c);
		}
	}
}
