package com.mwlv.gotao.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mwlv.gotao.GoTaoConstants;
import com.mwlv.gotao.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PuzzleList extends ListActivity {

	private List<String> _items = null;
	private String filePath = "/sdcard";

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
        //闅愬幓鏍囬鏍忥紙搴旂敤绋嬪簭鐨勫悕瀛楋級
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //闅愬幓鐘舵�佹爮閮ㄥ垎(鐢垫睜绛夊浘鏍囧拰涓�鍒囦慨楗伴儴鍒�)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getListView().setBackgroundResource(R.drawable.file_list_background);
		fillList();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (_items == null || _items.size() == 0)
			return;
		
		Intent i = new Intent(this, GoPuzzleLibActivity.class);
		Bundle b = new Bundle();

		b.putInt("puzzleGroupId", (int)id);
		i.putExtras(b);

		startActivityForResult(i, GoTaoConstants.REQUEST_GO_PUZZLE_CODE);
		
	}

	private void fillList() {
		List<String> items = getPuzzleGroups();

		ArrayAdapter<String> puzzleList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		setListAdapter(puzzleList);

		this._items = items;
	}

	private List<String> getPuzzleGroups() {
		String[] puzzleGroups = new String[]{"TOM姝绘椿棰�"};//new String[]{"TOM姝绘椿棰�","楂樻涔犻闆�","瀹舵暀缃戠殑姝绘椿棰�","鎵嬬瓔缁冧範"};
		return Arrays.asList(puzzleGroups);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			response("");
			return true;
		case KeyEvent.KEYCODE_DPAD_UP:
			fillList();
			filePath = "/";
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void response(String value) {
		Intent i = new Intent();
		Bundle b = new Bundle();
		b.putString("result", value);
		i.putExtras(b);

		this.setResult(RESULT_OK, i);
		this.finish();
	}
}
