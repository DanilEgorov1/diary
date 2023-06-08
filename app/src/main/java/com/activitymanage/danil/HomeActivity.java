package com.activitymanage.danil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.*;
import android.widget.*;
import android.graphics.*;
import android.util.*;

import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class HomeActivity extends AppCompatActivity {
	
	
	private HashMap<String, Object> pp = new HashMap<>();
	private double num = 0;
	
	private ArrayList<String> db = new ArrayList<>();
	private ArrayList<String> img = new ArrayList<>();
	
	private LinearLayout linear1;
	private ScrollView vscroll1;
	private TextView textview1;
	private LinearLayout bbo;
	private LinearLayout note;
	private LinearLayout todo;
	private LinearLayout shop;
	private LinearLayout diary;
	private ImageView imageview2;
	private TextView tnote;
	private ImageView imageview3;
	private TextView ttodo;
	private ImageView imageview4;
	private TextView tshop;
	private ImageView imageview5;
	private TextView tdiary;
	
	private Intent intent = new Intent();
	private AlertDialog.Builder dbl;
	private AlertDialog.Builder sgl;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
		}
	}

	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		textview1 = (TextView) findViewById(R.id.textview1);
		bbo = (LinearLayout) findViewById(R.id.bbo);
		note = (LinearLayout) findViewById(R.id.note);
		todo = (LinearLayout) findViewById(R.id.todo);
		shop = (LinearLayout) findViewById(R.id.shop);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		tnote = (TextView) findViewById(R.id.tnote);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		ttodo = (TextView) findViewById(R.id.ttodo);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		tshop = (TextView) findViewById(R.id.tshop);
		dbl = new AlertDialog.Builder(this);
		sgl = new AlertDialog.Builder(this);

		
		note.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.putExtra("bookmark", "false");
				intent.putExtra("s", "false");
				intent.setClass(getApplicationContext(), NotesActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		todo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.putExtra("t", "p");
				intent.setClass(getApplicationContext(), TodoActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		shop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), ShopActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}
	private void initializeLogic() {
		android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable(); s.setColor(Color.parseColor("#ffffff")); s.setCornerRadius(10); note.setBackground(s);
		todo.setBackground(s);
		shop.setBackground(s);
		//		diary.setBackground(s);
		android.graphics.drawable.GradientDrawable t = new android.graphics.drawable.GradientDrawable(); t.setColor(Color.parseColor("#673ab7")); t.setCornerRadius(70); tnote.setBackground(t);
		ttodo.setBackground(t);
		tshop.setBackground(t);
		tdiary.setBackground(t);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if (false) {
			
		}
		else {
			dbl.setTitle("Выйти");
			dbl.setMessage("Вы точно хотите выйти?");
			dbl.setPositiveButton("Да", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					finish();
				}
			});
			dbl.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
				}
			});
			dbl.create().show();
		}
	}

	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	

	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	

	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	

	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	

	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	

	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	

	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	

	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
