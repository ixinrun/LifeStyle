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
import com.ixinrun.lifestyle.common.db.table.DbStepInfo;
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
        // 设置线状图不显示描述
        mStepChartLc.setDescription(null);
        // Y自下往上动态绘制，这里添加初始的动画效果
        mStepChartLc.animateY(1000);

        // 获取柱状图的X轴
        XAxis xAxis = mStepChartLc.getXAxis();
        // 下面两个是获取Y轴，包括左右
        YAxis yAxisLeft = mStepChartLc.getAxisLeft();
        YAxis yAxisRight = mStepChartLc.getAxisRight();
        setAxis(xAxis, yAxisLeft, yAxisRight);
    }

    /**
     * 设置折线图XY轴
     *
     * @param xAxis      x轴
     * @param yAxisLeft  左边y轴
     * @param yAxisRight 右边y轴
     */
    private void setAxis(XAxis xAxis, YAxis yAxisLeft, YAxis yAxisRight) {
        // 启用X轴
        xAxis.setEnabled(true);
        // 设置X轴位于在图底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 设置X轴坐标显示
        xAxis.setDrawLabels(true);
        // 设置X轴轴线显示
        xAxis.setDrawAxisLine(true);
        // 设置X轴轴线粗细
        xAxis.setAxisLineWidth(2);
        xAxis.setAxisLineColor(Color.BLACK);
        // 设置X轴纵向网格线
        xAxis.setDrawGridLines(false);
        // 设置x轴字符串
        xAxis.setTextSize(10f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            private String[] mLableXHeartRate = new String[]{"one", "two", "three", "four", "前天", "昨天", "今天"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mLableXHeartRate[(int) value];
            }
        });
        xAxis.setLabelCount(7, true);

        // 启用左y轴
        yAxisLeft.setEnabled(true);
        // 设置Y轴坐标显示
        yAxisLeft.setDrawLabels(true);
        // 设置y轴轴线显示
        yAxisLeft.setDrawAxisLine(true);
        // 设置X轴轴线粗细
        yAxisLeft.setAxisLineWidth(2);
        yAxisLeft.setAxisLineColor(Color.BLACK);
        // 设置Y轴横向网格线
        yAxisLeft.setDrawGridLines(false);

        // 不显示右Y轴
        yAxisRight.setEnabled(false);
    }

    /**
     * 填充图表数据
     *
     * @param list
     */
    public void setData(List<DbStepInfo> list) {
        if (list == null) {
            return;
        }

        // 坐标映射队列
        List<Entry> mListEnryMin = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            // 添加x,y坐标的值
            mListEnryMin.add(new Entry(i, list.get(i).getStepNum()));
        }

        // 折线描述
        LineDataSet barDataSet = new LineDataSet(mListEnryMin, "步数动态");
        // 设置线条颜色为红色
        barDataSet.setColors(getResources().getColor(R.color.theme_color));
        barDataSet.setLineWidth(2);
        barDataSet.setCircleColor(getResources().getColor(R.color.theme_color));
        barDataSet.setCircleColorHole(getResources().getColor(R.color.theme_color));
        barDataSet.setCircleRadius(4);
        barDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        barDataSet.setDrawFilled(true);
        barDataSet.setFillColor(getResources().getColor(R.color.theme_color));
        barDataSet.setFillAlpha(30);

        // 设置折线图转择点的值的大小
        barDataSet.setValueTextSize(8);
        barDataSet.setValueTextColor(getResources().getColor(R.color.theme_color));

        // 展示
        mStepChartLc.setData(new LineData(barDataSet));
    }
}
