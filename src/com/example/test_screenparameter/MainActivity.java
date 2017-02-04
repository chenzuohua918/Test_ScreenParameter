package com.example.test_screenparameter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tv_inch, tv_resolution, tv_statusbar, tv_ppi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_inch = (TextView) findViewById(R.id.tv_inch);
		tv_resolution = (TextView) findViewById(R.id.tv_resolution);
		tv_statusbar = (TextView) findViewById(R.id.tv_statusbar);
		tv_ppi = (TextView) findViewById(R.id.tv_ppi);
	}

	public void getScreenParameter(View view) {
		tv_inch.append(String.valueOf(ScreenUtil.getScreenInch(this)));
		tv_resolution.append(ScreenUtil.getScreenResolution(this));
		tv_statusbar.append(String.valueOf(ScreenUtil.getStatusHeight(this)));
		tv_ppi.append(String.valueOf(ScreenUtil.getScreenPPI(this)));
	}
}
