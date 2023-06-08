package com.activitymanage.danil;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.*;
import android.widget.*;
import android.util.*;

import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.content.Intent;

import java.util.Calendar;
import java.text.SimpleDateFormat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class NotesetActivity extends AppCompatActivity {


	private FloatingActionButton _fab;
	private double position = 0;
	private boolean stt = false;
	private HashMap<String, Object> tmp = new HashMap<>();
	private boolean ddel = false;

	private ArrayList<HashMap<String, Object>> notes = new ArrayList<>();

	private LinearLayout bar;
	private ScrollView vscroll1;
	private ImageView back;
	private LinearLayout linear2;
	private ImageView pin;
	private ImageView del;
	private ImageView share;
	private LinearLayout linear3;
	private TextView title;
	private EditText etitle;
	private TextView content;
	private LinearLayout vbox;
	private EditText econtent;
	private ImageView sttc;
	private ProgressBar progressbar1;

	private Intent intent = new Intent();
	private Calendar cale = Calendar.getInstance();
	private AlertDialog.Builder dbl;
	private SharedPreferences data;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.noteset);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			//initializeLogic();
		}
	}

	private void initialize(Bundle _savedInstanceState) {

		_fab = (FloatingActionButton) findViewById(R.id._fab);

		bar = (LinearLayout) findViewById(R.id.bar);
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		back = (ImageView) findViewById(R.id.back);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		pin = (ImageView) findViewById(R.id.pin);
		del = (ImageView) findViewById(R.id.del);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		title = (TextView) findViewById(R.id.title);
		etitle = (EditText) findViewById(R.id.etitle);
		content = (TextView) findViewById(R.id.content);
		vbox = (LinearLayout) findViewById(R.id.vbox);
		econtent = (EditText) findViewById(R.id.econtent);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		dbl = new AlertDialog.Builder(this);
		data = getSharedPreferences("color", Activity.MODE_PRIVATE);

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), NotesActivity.class);
				startActivity(intent);
				finish();
			}
		});

		pin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (notes.get((int)position).get("ispin").toString().equals("true")) {
					notes.get((int)position).put("ispin", "false");
					pin.setImageResource(R.drawable.ic_star_outline_black);
				}
				else {
					pin.setImageResource(R.drawable.ic_star_black);
					notes.get((int)position).put("ispin", "true");
				}
			}
		});

		del.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dbl.setTitle("Удалить");
				dbl.setMessage("Вы точно хотите удалить это?");
				dbl.setPositiveButton("Да", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						ddel = true;
						notes.remove((int)(position));
						intent.setClass(getApplicationContext(), NotesActivity.class);
						startActivity(intent);
						startActivity(intent);
					}
				});
				dbl.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {

					}
				});
				dbl.create().show();
			}
		});


		title.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				title.setVisibility(View.GONE);
				etitle.setVisibility(View.VISIBLE);
			}
		});

		content.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				econtent.setVisibility(View.VISIBLE);
				content.setVisibility(View.GONE);
				vbox.setVisibility(View.VISIBLE);
			}
		});



		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), NotesActivity.class);
				startActivity(intent);
				finish();
			}
		});


	}
	private void initializeLogic() {
		del.setVisibility(View.VISIBLE);
		progressbar1.setVisibility(View.GONE);
		share.setVisibility(View.GONE);
		stt = false;
		notes = new Gson().fromJson(getIntent().getStringExtra("notes"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		position = Double.parseDouble(getIntent().getStringExtra("position"));
		if (getIntent().getStringExtra("t").equals("v")) {
			title.setText(notes.get((int)position).get("title").toString());
			content.setText(notes.get((int)position).get("content").toString());
			etitle.setText(notes.get((int)position).get("title").toString());
			econtent.setText(notes.get((int)position).get("content").toString());
			etitle.setVisibility(View.GONE);
			econtent.setVisibility(View.GONE);
			vbox.setVisibility(View.INVISIBLE);
			title.setVisibility(View.VISIBLE);
			content.setVisibility(View.VISIBLE);
			if (notes.get((int)position).get("ispin").toString().equals("true")) {
				pin.setImageResource(R.drawable.ic_star_black);
			}
			else {
				pin.setImageResource(R.drawable.ic_star_outline_black);
			}
		}
		else {
			title.setVisibility(View.GONE);
			content.setVisibility(View.GONE);
			vbox.setVisibility(View.VISIBLE);
			etitle.setVisibility(View.VISIBLE);
			econtent.setVisibility(View.VISIBLE);
			pin.setVisibility(View.GONE);
			del.setVisibility(View.GONE);
		}
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
		intent.setClass(getApplicationContext(), NotesActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onPause() {
		super.onPause();
		cale = Calendar.getInstance();
		if (getIntent().getStringExtra("t").contains("v")) {
			if (ddel) {
				FileUtil.writeFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/.database/note.aio"), new Gson().toJson(notes));
			}
			else {
				FileUtil.writeFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/.database/note.aio"), new Gson().toJson(notes));
			}
		}
		else {
			if (econtent.getText().toString().equals("") && etitle.getText().toString().equals("")) {
				arsybaiUtil.showMessage(getApplicationContext(), "Blank note discarded");
			}
			else {
				tmp.put("title", etitle.getText().toString());
				tmp.put("content", econtent.getText().toString());
				tmp.put("ispin", "false");
				tmp.put("date", new SimpleDateFormat("dd MMMM yyyy").format(cale.getTime()));
				notes.add(tmp);
				FileUtil.writeFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/.database/note.aio"), new Gson().toJson(notes));
			}
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
