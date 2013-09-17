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
        //隐去标题栏（应用程序的名字）
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状�1�7�栏部分(电池等图标和丄1�7切修饰部刄1�7)
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
		String[] puzzleGroups = new String[]{"TOM死活预1�7"};//new String[]{"TOM死活预1�7","高段习题雄1�7","家教网的死活预1�7","手筋练习"};
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
