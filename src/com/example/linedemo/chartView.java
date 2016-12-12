package com.example.linedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import android.graphics.Color;

public class chartView extends Activity implements OnClickListener {
	private Intent intent;
	private CombinedChart mChart;
	private final int itemcount = 12;
	private List<String> months = new ArrayList<String>();
	private List<Map<String, Object>> month = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	//数据
	private String[] mTimes = new String[] { "00:00", "1:00", "2:00", "3:00",
			"4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00",
			"12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00",
			"19:00", "20:00", "21:00", "22:00", "23:00", };
	private int[] arrs = {10,15,12,22,43,25,19,30,32,15,8,26,19,34,44,20,15,5,35,24,16,28,43};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.chartview);
		ImageView backbtn = (ImageView) findViewById(R.id.backbtn);
		backbtn.setOnClickListener(this);

		intent = getIntent();
		TextView titleView = (TextView) findViewById(R.id.title_bar_name);
		titleView.setText(intent.getStringExtra("days") + "天坐姿曲线图");
		mChart = (CombinedChart) findViewById(R.id.chart1);
		mChart.setDescription("");
		//设置背景颜色
		mChart.setBackgroundColor(Color.WHITE);
		mChart.setDrawGridBackground(true);
		mChart.setDrawBarShadow(true);
		mChart.setMaxVisibleValueCount(100);
		mChart.getAxisRight().setEnabled(false);

		// draw bars behind lines
		mChart.setDrawOrder(new DrawOrder[] { DrawOrder.BAR, DrawOrder.BUBBLE,
				DrawOrder.CANDLE, DrawOrder.LINE, DrawOrder.SCATTER });

		YAxis rightAxis = mChart.getAxisRight();
		rightAxis.setDrawGridLines(false);

		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.setDrawGridLines(false);

		XAxis xAxis = mChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		Map<String, Object> map = null;
		for (int i = 0; i < mTimes.length; i++) {
			map = new HashMap<String, Object>();
			map.put("addDay",mTimes[i]);
			month.add(map);
		}
		
		Map<String, Object> dataMap = null;
		for (int i = 0; i < arrs.length; i ++) {
			dataMap = new HashMap<String, Object>();
			dataMap.put("learnCount", arrs[i]);
			data.add(dataMap);
		}

		// 横向显示的时间 12/3 12/4
		for (int index = 0; index < month.size(); index++) {
			Map<String, Object> cdata = month.get(index);
			
			months.add(cdata.get("addDay").toString());
		}

		CombinedData data = new CombinedData(months);

		data.setData(generateLineData());
		// data.setData(generateBarData());
		// //data.setData(generateBubbleData());
		// data.setData(generateScatterData());
		// data.setData(generateCandleData());

		mChart.setData(data);
		mChart.invalidate();
	}

	private LineData generateLineData() {

		LineData d = new LineData();

		ArrayList<Entry> entries = new ArrayList<Entry>();
		// 纵向显示的数据
		for (int index = 0; index < data.size(); index++) {
			Map<String, Object> cdata = data.get(index);

			entries.add(new Entry(Integer.parseInt(cdata.get("learnCount")
					.toString()), index));
		}

		LineDataSet set = new LineDataSet(entries,
				intent.getStringExtra("days") + "天坐姿曲线图");
		set.setColor(Color.rgb(40, 40, 40));
		set.setLineWidth(2.5f);
		set.setCircleColor(Color.rgb(40, 40, 40));
		set.setCircleSize(5f);
		set.setFillColor(Color.rgb(40, 40, 40));
		set.setDrawCubic(true);
		set.setDrawValues(true);
		set.setValueTextSize(10f);
		set.setValueTextColor(Color.rgb(40, 40, 40));

		set.setAxisDependency(YAxis.AxisDependency.LEFT);

		d.addDataSet(set);

		return d;
	}

	private BarData generateBarData() {

		BarData d = new BarData();

		ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

		for (int index = 0; index < itemcount; index++)
			entries.add(new BarEntry(getRandom(15, 30), index));

		BarDataSet set = new BarDataSet(entries, "Bar DataSet");
		set.setColor(Color.rgb(60, 220, 78));
		set.setValueTextColor(Color.rgb(60, 220, 78));
		set.setValueTextSize(10f);
		d.addDataSet(set);

		set.setAxisDependency(YAxis.AxisDependency.LEFT);

		return d;
	}

	protected ScatterData generateScatterData() {

		ScatterData d = new ScatterData();

		ArrayList<Entry> entries = new ArrayList<Entry>();

		for (int index = 0; index < itemcount; index++)
			entries.add(new Entry(getRandom(20, 15), index));

		ScatterDataSet set = new ScatterDataSet(entries, "Scatter DataSet");
		set.setColor(Color.GREEN);
		set.setScatterShapeSize(7.5f);
		set.setDrawValues(false);
		set.setValueTextSize(10f);
		d.addDataSet(set);

		return d;
	}

	protected CandleData generateCandleData() {

		CandleData d = new CandleData();

		ArrayList<CandleEntry> entries = new ArrayList<CandleEntry>();

		for (int index = 0; index < itemcount; index++)
			entries.add(new CandleEntry(index, 20f, 10f, 13f, 17f));

		CandleDataSet set = new CandleDataSet(entries, "Candle DataSet");
		set.setColor(Color.rgb(80, 80, 80));
		set.setBodySpace(0.3f);
		set.setValueTextSize(10f);
		set.setDrawValues(false);
		d.addDataSet(set);

		return d;
	}

	protected BubbleData generateBubbleData() {

		BubbleData bd = new BubbleData();

		ArrayList<BubbleEntry> entries = new ArrayList<BubbleEntry>();

		for (int index = 0; index < itemcount; index++) {
			float rnd = getRandom(20, 30);
			entries.add(new BubbleEntry(index, rnd, rnd));
		}

		BubbleDataSet set = new BubbleDataSet(entries, "Bubble DataSet");
		set.setColors(ColorTemplate.VORDIPLOM_COLORS);
		set.setValueTextSize(10f);
		set.setValueTextColor(Color.WHITE);
		set.setHighlightCircleWidth(1.5f);
		set.setDrawValues(true);
		bd.addDataSet(set);

		return bd;
	}

	private float getRandom(float range, float startsfrom) {
		return (float) (Math.random() * range) + startsfrom;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}
}
