package com.kikyou.mymacapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

	private TextView tvMainContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ARouter.init(getApplication());
		setContentView(R.layout.activity_main);
		tvMainContent = (TextView) findViewById(R.id.tv_main_content);
		findViewById(R.id.test).setOnClickListener(v -> ARouter.getInstance().build("/test/two").navigation());
		//以文件方式存储数据
		writeData();
		readData();
		getSharedPreferences("mode", MODE_PRIVATE);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		sp.edit().putString("", "").apply();

		//测试
		Log.e("TEST", "test=" + test("ABCABC"));
		Log.e("TEST", "test=" + test("AABBCC"));
		Log.e("TEST", "test=" + test("AACCAABB"));

		Log.e("TEST", "test2=" + test2("18", "27"));//45
		Log.e("TEST", "test2=" + test2("180", "27"));//207
		Log.e("TEST", "test2=" + test2("18", "270"));//288
		Log.e("TEST", "test2=" + test2("1873737724647483882", "27123"));//1873737724647511005

		Log.e("TEST", "test3=" + test3(5));
		Log.e("TEST", "test3=" + test3(6));
		Log.e("TEST", "test3=" + test3(7));
		Log.e("TEST", "test3=" + test3(10));

		Log.e("TEST", "sort1=" + Arrays.toString(sort1(new int[]{8, 2, 3, 7, 5, 1, 6, 4, 9})));
		Log.e("TEST", "sort2=" + Arrays.toString(sort2(new int[]{8, 2, 5, 7, 3, 1, 6, 4, 9})));
		Log.e("TEST", "sort3=" + Arrays.toString(sort3(new int[]{8, 2, 5, 7, 3, 1, 6, 4, 9})));
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

	/**
	 * 字符串去重
	 *
	 * @param oldStr 需要去重的字符串
	 * @return 去重后的字符串
	 */
	private String test(String oldStr) {
		char[] chars = oldStr.toCharArray();
		StringBuilder sb = new StringBuilder();
		String result = sb.toString();
		for (char aChar : chars) {
			if (!result.contains(String.valueOf(aChar))) {
				sb.append(aChar);
			}
			result = sb.toString();
		}
		return result;
	}

	/**
	 * ArrayList容量的增长
	 * 自增长，初始容量为10，每次增长为1.5倍
	 * 通过私有方法grow来增加容量
	 */
	private void test1() {
		ArrayList<String> arr = new ArrayList<>();
	}

	/**
	 * 字符串数字相加，不考虑出现非数字或者小数的情况
	 */
	private String test2(String one, String two) {
		char[] charsOne;
		char[] charsTwo;
		if (one.length() > two.length()) {
			charsOne = one.toCharArray();
			charsTwo = two.toCharArray();
		} else {
			charsOne = two.toCharArray();
			charsTwo = one.toCharArray();
		}
		StringBuilder sb = new StringBuilder();
		//37
		for (int i = 1; i <= charsOne.length; i++) {
			int num1 = charsOne[charsOne.length - i] - '0';
			int num2;
			int num3;

			if (charsTwo.length >= i) {
				num2 = charsTwo[charsTwo.length - i] - '0';
				int num = num1 + num2;
				if (num > 9) {
					num3 = num - 10;
					//前一位要+1
					int num4 = charsOne[charsOne.length - i - 1] - '0';
					charsOne[charsOne.length - i - 1] = String.valueOf(num4 + 1).toCharArray()[0];//3
				} else {
					num3 = num;
				}
			} else {
				num3 = num1;
			}
			sb.insert(0, num3);
		}
		String result = sb.toString();
		return result;
	}

	/**
	 * 斐波那契数列
	 * 1 2 3 5 8 13 21 34...n
	 * 时间复杂度O(n)
	 */
	private int test3(int n) {
		ArrayList<Integer> arr = new ArrayList<>();
		arr.add(1);
		arr.add(2);

		for (int i = 2; i < n; i++) {
			Integer f3 = arr.get(i - 2);
			Integer f4 = arr.get(i - 1);
			arr.add(i, f3 + f4);
		}
		Log.e("TEST", "arr=" + arr);
		return arr.get(arr.size() - 1);
	}

	/**
	 * 简单选择排序
	 * 其他排序还有冒泡、折半(二分法）
	 * 猜测时间复杂度为O(n²)，也可以打成O(n^2)
	 */
	private int[] sort1(int[] args) {
//		8, 2, 3, 7, 5, 1, 6, 4, 9
		//分析错误：i = 0,j = 1
		// 1....283751649
		// 2....~
		// 3....~
		// 5....183752649
		// 10..138752649
		// 11..128753649
		// 第三轮当i下标为2，j下标还为1时，就会出现错误的调换
		// 12..182...
		// 每轮都只能和后面的数字比较才对
		for (int i = 0; i < args.length - 1; i++) {
			for (int j = i + 1; j < args.length; j++) {
				if (args[i] > args[j]) {
					int temp = args[j];
					args[j] = args[i];
					args[i] = temp;
				}
			}
		}
		return args;
	}

	private int[] sort2(int[] args) {
		//		8, 2, 3, 7,     5, 1, 6, 4, 9
		for (int i = 0; i < args.length / 2; i ++) {//0123
			for (int j = args.length - 1; j >= args.length / 2; j--) {//87654
				if (args[i] > args[j]) {
					int temp = args[j];
					args[j] = args[i];
					args[i] = temp;
				}
			}
		}
		return args;
	}

	//时间复杂度O(n^2)，空间复杂度O(1)
	private int[] sort3(int[] args) {
		//		8, 2, 3, 7, 5, 1, 6, 4, 9
		//冒泡排序：分析下
		// i=0,j=0....283751649
		// i=0,j=1....238...
		// i=0,j=7....237516489
		// i=1,j=0....23...
		// i=1,j=2....2357...
		// i=1,j=6....235164789
		// i=2,j=0....231564789
		// i=3,j=0....231546789
		// i=4,j=0....231456789
		// i=5,j=0....213456789
		// i=6,j=0....123456789
		for (int i = 0; i < args.length; i++){
			for (int j = 0; j < args.length - 1 - i; j++) {
				if (args[j] > args[j + 1]) {
					int temp = args[j + 1];
					args[j + 1] = args[j];
					args[j] = temp;
				}
			}
		}
		return args;
	}
}
