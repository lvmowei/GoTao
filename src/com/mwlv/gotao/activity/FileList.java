package com.mwlv.gotao.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

public class FileList extends ListActivity {

	private List<String> _items = null;
	private String filePath = "/sdcard";
	private String filter=null;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
        //隐去标题栏（应用程序的名字）
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状�1�7�栏部分(电池等图标和丄1�7切修饰部刄1�7)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getListView().setBackgroundResource(R.drawable.file_list_background);
		filter=request();
		fillList(new File(filePath).listFiles());
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (_items == null || _items.size() == 0)
			return;

		filePath = _items.get((int) id);
		File file = new File(filePath);
		if (file.isDirectory()) {
			try {
				fillList(file.listFiles());
			} catch (Exception ex) {
				Toast.makeText(FileList.this, ex.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		} else{
			response(filePath);
		}
	}

	private void fillList(File[] files) {
		List<String> items = new ArrayList<String>();
		for (File file : files){
			if(file.canRead()){
				if(file.isFile() && !file.getName().endsWith(filter))
					continue;
				items.add(file.getPath());
			}
		}

		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		setListAdapter(fileList);

		this._items = items;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			response("");
			return true;
		case KeyEvent.KEYCODE_DPAD_UP:
			fillList(new File("/").listFiles());
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
	
	private String request(){
		 Intent i = this.getIntent();
		 Bundle b = i.getExtras();
		 String s = b.getString("filter");
		 return s;
	}
}
