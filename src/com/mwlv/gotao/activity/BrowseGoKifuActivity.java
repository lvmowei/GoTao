package com.mwlv.gotao.activity;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mwlv.gotao.R;
import com.mwlv.gotao.board.Board;
import com.mwlv.gotao.board.BrowseGoKifuBoard;
import com.mwlv.gotao.board.Function;
import com.mwlv.gotao.board.SubBoard;
import com.mwlv.gotao.sgf.SgfFile;
import com.mwlv.gotao.sgf.SgfNode;
import com.mwlv.gotao.view.GoView;

public class BrowseGoKifuActivity extends Activity {

	public static final String Tag = "GoTao";

	private GoView goView;

	private TextView commentTv;
	private LinearLayout branchBtns;
	private Button preBranchBtn;
	private Button nextBranchBtn;
	private Button confirmBtn;
	private Button cancelBtn;
	
	private Button openBtn;
	private Button researchBtn;
	private Button backBtn;
	private Button forwardBtn;
	private Button resetBtn;

	private BrowseGoKifuBoard board;

	private String info;
	private String black;
	private String white;
	private CharSequence save;
	private CharSequence open;
	private SgfFile sgfFile;

	private Function infoListener = new Function() {
		public Object apply(Object... obj) {
			String comments = (String) obj[0];
			commentTv.setText(comments);
			return null;
		}
	};
	
	private Function branchListener = new Function() {
		public Object apply(Object... obj) {
			showBranchBtns();
			return null;
		}
	};

	private void showBranchBtns() {
		branchBtns.setVisibility(View.VISIBLE);
		preBranchBtn.setEnabled(false);
		nextBranchBtn.setEnabled(true);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //闅愬幓鏍囬鏍忥紙搴旂敤绋嬪簭鐨勫悕瀛楋級
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //闅愬幓鐘舵�佹爮閮ㄥ垎(鐢垫睜绛夊浘鏍囧拰涓�鍒囦慨楗伴儴鍒�)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.browse_go_kifu);

		goView = (GoView) findViewById(R.id.browse_goview);
		
		commentTv = (TextView)findViewById(R.id.browse_comment);
		commentTv.setMovementMethod(ScrollingMovementMethod.getInstance());
		branchBtns = (LinearLayout)findViewById(R.id.browse_branch_btns);
		
		preBranchBtn = (Button) findViewById(R.id.browse_branch_pre_btn);
		nextBranchBtn = (Button) findViewById(R.id.browse_branch_next_btn);
		confirmBtn = (Button) findViewById(R.id.browse_branch_confirm_btn);
		cancelBtn = (Button) findViewById(R.id.browse_branch_cancel_btn);
		

		openBtn = (Button) findViewById(R.id.browse_openfile_btn);
		openBtn.setEnabled(true);
		
		researchBtn = (Button) findViewById(R.id.browse_research_btn);
		researchBtn.setEnabled(false);

		backBtn = (Button) findViewById(R.id.browse_back_btn);
		backBtn.setEnabled(false);

		forwardBtn = (Button) findViewById(R.id.browse_forward_btn);
		forwardBtn.setEnabled(false);
		
		resetBtn = (Button) findViewById(R.id.browse_reset_btn);
		resetBtn.setEnabled(false);
		// 妫嬬洏瑙嗗浘
		board = new BrowseGoKifuBoard();
		goView.setBoard(board);
		//goView.setBackgroundColor(Color.rgb(255, 128, 64));

		// 瀛楃涓茶祫婧�
		Resources r = BrowseGoKifuActivity.this.getResources();
		info = r.getString(R.string.info);
		black = r.getString(R.string.black);
		white = r.getString(R.string.white);
		save=r.getText(R.string.save);
		open=r.getText(R.string.open);

		// 鐩戝惉浜嬩欢
		board.setListener(infoListener);
		board.setBranchListener(branchListener);
		action();

		if (savedInstanceState != null) {
			Bundle map = savedInstanceState.getBundle(Tag);
			if (map != null) {
				board.restoreState(map);
			}
		}

		Log.d(Tag, "initFinished");
	}


	private void action() {
//		gotoBtn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				int i = 0;
//				try {
//					i = Integer.valueOf(String.valueOf(editText.getText()));
//				} catch (Exception ex) {
//					return;
//				}
//
//				Board b = board.getSubBoard(i);
//				b.setListener(infoListener);
//				goView.setBoard(b);
//				returnBtn.setEnabled(true);
//				backBtn.setEnabled(true);
//				forwardBtn.setEnabled(true);
//			}
//		});

		researchBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {

			}
		});

		backBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
//				if (goView.getBoard() instanceof SubBoard) {
//					SubBoard sb = (SubBoard) goView.getBoard();
//					sb.back();
//					goView.invalidate();
//				}
//				Board b = null;
//				if (goView.getBoard() instanceof SubBoard){
//					b = goView.getBoard();
//				}else{
//					b = board.getSubBoard(board.getList().size());
//					b.setListener(infoListener);
//					goView.setBoard(b);
//				}
//				
//				int pos = ((SubBoard)b).back();
//				sgfFile.setCurrentNode(b.getList().get(pos).getSgfNode());
				forwardBtn.setEnabled(true);
				branchBtns.setVisibility(View.GONE);
				if(board.back() == 0){
					backBtn.setEnabled(false);
					resetBtn.setEnabled(false);
				}					
				goView.invalidate();
			}
		});
		
		forwardBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
//				if (goView.getBoard() instanceof SubBoard) {
//					SubBoard sb = (SubBoard) goView.getBoard();
//					sb.forward();
//					goView.invalidate();
//				}
				backBtn.setEnabled(true);
				resetBtn.setEnabled(true);
				if(board.forward() == 0 || board.getBranchList() != null)
					forwardBtn.setEnabled(false);
				goView.invalidate();
			}
		});
		
		openBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				openSgf();
			}
		});
		
		nextBranchBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(board.nextBranch() == 0)
					nextBranchBtn.setEnabled(false);
				preBranchBtn.setEnabled(true);
				goView.invalidate();
			}
		});
		
		preBranchBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(board.preBranch() == 0)
					preBranchBtn.setEnabled(false);
				nextBranchBtn.setEnabled(true);
				goView.invalidate();
			}
		});
		
		confirmBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(board.confirmBranch() != 0)
					forwardBtn.setEnabled(true);
				branchBtns.setVisibility(View.GONE);
				goView.invalidate();
			}
		});
		
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				board.cancelBranch();
				forwardBtn.setEnabled(true);
				branchBtns.setVisibility(View.GONE);
				goView.invalidate();
			}
		});
		
		resetBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				board.resetBoard();
				goView.invalidate();
				commentTv.setText(sgfFile.getSgfNode().getComment());
				resetBtn.setEnabled(false);
				backBtn.setEnabled(false);
				forwardBtn.setEnabled(true);
				branchBtns.setVisibility(View.GONE);
			}
		});
		
	}

//	private void updateInfo(final String black, final String white, int count,
//			int expBw) {
//		String bw = "?";
//		if (expBw == Board.Black)
//			bw = black;
//		else if (expBw == Board.White)
//			bw = white;
//
//		String s = String.format(info, count, bw);
//		//textView.setText(s);
//	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		/*涓存椂淇濆瓨妫嬪眬*/
		outState.putBundle(Tag, board.saveState());
	}

	// ------------------------------------------------------------------sgf鏂囦欢鎿嶄綔
	
//	final int menuSave=Menu.FIRST;
//	final int menuOpen=Menu.FIRST+1;
//	final int menuAbout=Menu.FIRST+2;
//	final int menuClear=Menu.FIRST+3;
//	final int menuSet=Menu.FIRST+4;
//	
//	@Override
//	public boolean onCreateOptionsMenu (Menu menu) {
//		menu.add(0,menuOpen,0,open).setIcon(android.R.drawable.ic_menu_search);
//		menu.add(0,menuSave,0,save).setIcon(android.R.drawable.ic_menu_save);
//		menu.add(0,menuAbout,0,R.string.about).setIcon(android.R.drawable.ic_dialog_info);
//		menu.add(0,menuClear,0,R.string.clear).setIcon(android.R.drawable.ic_menu_close_clear_cancel);
//		menu.add(0,menuSet,0,R.string.set).setIcon(android.R.drawable.ic_menu_sort_by_size);
//		
//		return true;
//	}
	
//	@Override
//	public boolean onOptionsItemSelected (MenuItem item) {
//		super.onOptionsItemSelected(item);
//		switch(item.getItemId()){
//		case menuSave:
//			this.showDialog(SaveDialog);
//			break;
//		case menuOpen:
//			openSgf();
//			break;
//		case menuAbout:
//			this.showDialog(AboutDialog);
//			break;
//		case menuClear:
//			this.onCreate(null);
//			break;
//		case menuSet:
//			this.showDialog(SetDialog);
//			break;
//		}
//		return true;
//	}
//	
//	private void save(String name){
//		String path=name+".sgf";
//		try {
//			SgfHelper.save(board, path);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void openSgf() {
		Intent i = new Intent(this, FileList.class);
		Bundle b = new Bundle();

		b.putString("filter", "sgf");
		i.putExtras(b);

		startActivityForResult(i, 10);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Bundle b = data.getExtras();
			String string = b.getString("result");

			sgfFile = new SgfFile(string);
			try {
				sgfFile.load();
				board.cleanAll();
				board.setSgfFile(sgfFile);
				commentTv.setText(sgfFile.getSgfNode().getComment());
				goView.invalidate();
				forwardBtn.setEnabled(true);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			this.setResult(RESULT_OK, null);
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
//	private final static int SaveDialog=0;
//	private final static int AboutDialog=1;
//	private final static int SetDialog=2;
//	private final static int BranchDialog = 3;
	
//	@Override
//	protected Dialog onCreateDialog(int id){
//		switch(id){
//		case SaveDialog:
//			final EditText fileNameTxt=new EditText(this);
//			return new AlertDialog.Builder(this)
//			.setIcon(android.R.drawable.ic_input_add)
//			.setTitle("FileName")
//			.setMessage(R.string.fileName)
//			.setView(fileNameTxt)
//			.setPositiveButton("OK",new OnClickListener(){
//				@Override
//				public void onClick(DialogInterface a0,int a1){
//					//save(fileNameTxt.getText().toString());
//				}
//			}).create();
//		case AboutDialog:
//			return new AlertDialog.Builder(this)
//			.setIcon(android.R.drawable.ic_input_add)
//			.setTitle("About")
//			.setMessage(R.string.aboutInfo)
//			.setPositiveButton("OK",null)
//			.create();
//		case SetDialog:
//			//return createSetDialog();
//		case BranchDialog:
//			
//		default:
//			return null;
//		}
//	}
//
//	private Dialog createSetDialog() {
//		final EditText fileNameTxt1=new EditText(this);
//		return new AlertDialog.Builder(this)
//		.setIcon(android.R.drawable.ic_menu_sort_by_size)
//		.setTitle("set")
//		.setMessage(R.string.boardSize)
//		.setView(fileNameTxt1)
//		.setPositiveButton("OK",new OnClickListener(){
//			@Override
//			public void onClick(DialogInterface a0,int a1){
//				int i = 0;
//				try {
//					i = Integer.valueOf(String.valueOf(fileNameTxt1.getText()));
//				} catch (Exception ex) {
//					return;
//				}
//				if(i<2)return;
//				Board.n=i;
//				BrowseGoKifuActivity.this.onCreate(null);
//			}
//		}).create();
//	}
}