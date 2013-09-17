package com.mwlv.gotao.activity;

import com.mwlv.gotao.GoTaoConstants;
import com.mwlv.gotao.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GoTao extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐去标题栏（应用程序的名字）  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状�1�7�栏部分(电池等图标和丄1�7切修饰部刄1�7)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        
        Button browseGoKifuBtn = (Button) findViewById(R.id.browseGoKifu);
        browseGoKifuBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(GoTao.this, BrowseGoKifuActivity.class);
				startActivityForResult(intent, GoTaoConstants.REQUEST_BROWSE_GO_KIFU_CODE);
			}
		});
        
        Button goPuzzleBtn = (Button) findViewById(R.id.goPuzzleLibBtn);
        goPuzzleBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(GoTao.this, PuzzleList.class);
				startActivityForResult(intent, GoTaoConstants.REQUEST_GO_PUZZLE_LIST_CODE);
			}
		});
    }
    
    
}