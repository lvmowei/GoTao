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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mwlv.gotao.R;
import com.mwlv.gotao.board.Board;
import com.mwlv.gotao.board.Function;
import com.mwlv.gotao.board.GoPuzzleLibBoard;
import com.mwlv.gotao.board.SubBoard;
import com.mwlv.gotao.sgf.SgfFile;
import com.mwlv.gotao.sgf.SgfNode;
import com.mwlv.gotao.util.PuzzleLib;
import com.mwlv.gotao.view.GoView;

public class GoPuzzleLibActivity extends Activity {

	public static final String Tag = "GoTao";

	private GoView goView;

	private TextView commentTv;
	
	private Button researchBtn;
	private Button backBtn;
	private Button nextBtn;
	private Button preBtn;
	private Button resetBtn;

	private GoPuzzleLibBoard board;

	private String info;
	private String black;
	private String white;
	private CharSequence save;
	private CharSequence open;
	private SgfFile sgfFile;

	private Function infoListener = new Function() {
		public Object apply(Object... obj) {
//			int count = (Integer) obj[0];
//			int expBw = (Integer) obj[1];
//
//			updateInfo(black, white, count, expBw);
//
			SgfNode sgfNode = (SgfNode) obj[0];
			commentTv.setText(sgfNode.getComment());
			return null;
		}
	};
	
	private Function branchListener = new Function() {
		public Object apply(Object... obj) {
			SgfNode sgfNode = (SgfNode) obj[0];
			return null;
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //隐去标题栏（应用程序的名字）
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状�1�7�栏部分(电池等图标和丄1�7切修饰部刄1�7)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.go_puzzle_lib);

		goView = (GoView) findViewById(R.id.puzzle_goview);
		
		commentTv = (TextView)findViewById(R.id.puzzle_comment);
		commentTv.setMovementMethod(ScrollingMovementMethod.getInstance());
		
		researchBtn = (Button) findViewById(R.id.puzzle_research_btn);
		researchBtn.setEnabled(false);

		backBtn = (Button) findViewById(R.id.puzzle_back_btn);
		backBtn.setEnabled(false);
		
		resetBtn = (Button) findViewById(R.id.puzzle_reset_btn);
		resetBtn.setEnabled(false);
		// 棋盘视图
		board = new GoPuzzleLibBoard();
		goView.setBoard(board);

		// 字符串资溄1�7
		Resources r = GoPuzzleLibActivity.this.getResources();
		info = r.getString(R.string.info);
		black = r.getString(R.string.black);
		white = r.getString(R.string.white);
		save=r.getText(R.string.save);
		open=r.getText(R.string.open);

		// 监听事件
		board.setListener(infoListener);
		action();

//		if (savedInstanceState != null) {
//			Bundle map = savedInstanceState.getBundle(Tag);
//			if (map != null) {
//				board.restoreState(map);
//			}
//		}
		
		int puzzleGroupId = this.getIntent().getIntExtra("puzzleGroupId", 0);
		String puzzle = PuzzleLib.getPuzzle(puzzleGroupId, 0);
		try {
		sgfFile = new SgfFile();
		sgfFile.parseSgfStr(puzzle);
		board.cleanAll();
		board.setSgfFile(sgfFile);
		commentTv.setText(sgfFile.getSgfNode().getComment());
		goView.invalidate();
	} catch (Exception e) {
		e.printStackTrace();
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
				if(board.back() == 0){
					backBtn.setEnabled(false);
					resetBtn.setEnabled(false);
				}					
				goView.invalidate();
			}
		});
		
		resetBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				board.resetBoard();
				goView.invalidate();
				resetBtn.setEnabled(false);
				backBtn.setEnabled(false);
				commentTv.setText(sgfFile.getSgfNode().getComment());
			}
		});
		
		goView.setOnTouchListener(new OnTouchListener(){

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					
					resetBtn.setEnabled(true);
				
							float xf = event.getX();
							float yf = event.getY();
				
							int x = goView.x2Coordinate(xf);
							int y = goView.y2Coordinate(yf);
				
							if (board.put(x, y)) {
								goView.invalidate();
								if(board.isSucceed())
									commentTv.setText("答对了！");
								else if(board.isRight() == false)
									commentTv.setText("答错了！");
							}
						}
						return true;
			}
			
		} );
		
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
		/*临时保存棋局*/
		outState.putBundle(Tag, board.saveState());
	}

	// ------------------------------------------------------------------sgf文件操作
	
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

//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode == RESULT_OK) {
//			Bundle b = data.getExtras();
//			String string = b.getString("result");
//
//			sgfFile = new SgfFile(string);
//			try {
//				sgfFile.load();
//				board.cleanAll();
//				board.setSgfFile(sgfFile);
//				commentTv.setText(sgfFile.getSgfNode().getComment());
//				goView.invalidate();
//			} catch (Exception e) {
//				
//				e.printStackTrace();
//			}
//		}
//	}
	
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