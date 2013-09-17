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
        //闅愬幓鏍囬鏍忥紙搴旂敤绋嬪簭鐨勫悕瀛楋級  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //闅愬幓鐘舵�佹爮閮ㄥ垎(鐢垫睜绛夊浘鏍囧拰涓�鍒囦慨楗伴儴鍒�)
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