package com.ixinrun.lifestyle.module_run.main.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.ixinrun.lifestyle.common.db.table.StepTable;
import com.ixinrun.lifestyle.module_run.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 图表
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/14
 */
public class StepChartView extends FrameLayout {

    private LineChart mStepChartLc;

    public StepChartView(@NonNull Context context) {
        this(context, null);
    }

    public StepChartView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.main_run_step_chart_layout, this);
        mStepChartLc = view.findViewById(R.id.step_chart_lc);
        initChart();
    }

    private void initChart() {
        //设置线状图不显示描述
        mStepChartLc.setDescription(null);
        //Y 自下往上动态绘制  这里添加初始的动画效果
        mStepChartLc.animateY(1000);

        //获取柱状图的X轴
        XAxis xAxis = mStepChartLc.getXAxis();
        //下面两个是获取Y轴  包括左右
        YAxis axisLeft = mStepChartLc.getAxisLeft();
        YAxis axisRight = mStepChartLc.getAxisRight();
        setAxis(xAxis, axisLeft, axisRight);
    }

    /**
     * 设置折线图XY轴
     *
     * @param axis      x轴
     * @param axisLeft  左边y轴
     * @param axisRight 右边y轴
     */
    private void setAxis(XAxis axis, YAxis axisLeft, YAxis axisRight) {
        //设置X轴在图底部显示
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置X轴的宽度
        axis.setAxisLineWidth(1);
        axis.setAxisLineColor(Color.BLACK);
        //起始0坐标开始
        axis.setAxisMinimum(0);
        //设置X轴显示轴线
        axis.setDrawAxisLine(true);
        //x的表格线不显示
        axis.setDrawGridLines(false);
        //设置X轴显示
        axis.setEnabled(true);
        //x轴显示字符串
        axis.setValueFormatter(new IAxisValueFormatter() {
            private String[] mLableXHeartRate = new String[]{"one", "two", "three", "four", "前天", "昨天", "今天"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mLableXHeartRate[(int) value];
            }
        });

        //x轴
        axis.setLabelCount(7, true);
        axis.setAxisLineWidth(2);

        //y轴0刻度
        axisLeft.setAxisMinimum(30);
        //不画网格线
        axisLeft.setDrawGridLines(false);
        axisLeft.setAxisLineColor(Color.BLACK);
        //显示左Y轴轴线
        axisLeft.setDrawAxisLine(true);
        axisLeft.setAxisLineWidth(2);
        axisLeft.setEnabled(true);
        axisLeft.setDrawLabels(true);

        //不显示右Y轴
        axisRight.setEnabled(false);
    }

    /**
     * 填充图表数据
     *
     * @param list
     */
    public void setData(List<StepTable> list) {
        if (list == null) {
            return;
        }

        //坐标映射队列
        List<Entry> mListEnryMin = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //添加x,y坐标的值
            mListEnryMin.add(new Entry(i, list.get(i).getStepNum()));
        }

        //折线描述
        LineDataSet barDataSet = new LineDataSet(mListEnryMin, "步数动态");
        //设置线条颜色为红色
        barDataSet.setColors(getResources().getColor(R.color.theme_color));
        barDataSet.setLineWidth(2);
        barDataSet.setCircleColor(getResources().getColor(R.color.theme_color));
        barDataSet.setCircleColorHole(getResources().getColor(R.color.theme_color));
        barDataSet.setCircleRadius(4);
        barDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        barDataSet.setDrawFilled(true);
        barDataSet.setFillColor(getResources().getColor(R.color.theme_color));
        barDataSet.setFillAlpha(50);

        //设置折线图转择点的值的大小
        barDataSet.setValueTextSize(10);
        barDataSet.setValueTextColor(getResources().getColor(R.color.theme_color));

        //展示
        mStepChartLc.setData(new LineData(barDataSet));
    }
}
