package com.kikyou.mymacapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

	private TextView tvMainContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvMainContent = (TextView) findViewById(R.id.tv_main_content);
		//以文件方式存储数据
		writeData();
		readData();
		getSharedPreferences("mode", MODE_PRIVATE);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		sp.edit().putString("", "").apply();
	}

	private void writeData() {
		BufferedWriter writer = null;
		try {
			//fileOutputStream不能被关闭，只关闭writer即可
			FileOutputStream fileOutputStream = openFileOutput("data", MODE_PRIVATE);//如果有同名则覆盖，还有种追加模式
			writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
			writer.write("写入的一个东西");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void readData() {
		BufferedReader reader = null;
		try {
			FileInputStream fileInputStream = openFileInput("data");
			reader = new BufferedReader(new InputStreamReader(fileInputStream));
			String content = reader.readLine();
			tvMainContent.setText(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
