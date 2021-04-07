package com.ixinrun.lifestyle.module_step.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.ixinrun.base.utils.DensityUtil;
import com.ixinrun.lifestyle.module_step.R;

/**
 * 功能描述: 自定义步数进度条
 * </p>
 *
 * @author ixinrun
 * @data 2016/5/5
 */
public class ArcProgressView extends View {

    private Paint mArcPaint;
    private Paint mArcProgressPaint;
    private int mArcColor;
    private int mArcProgressColor;
    private float mArcWidth;
    private int mDuration;
    private int mLineWith;
    private int mLineSpace;

    private final static float DEFAULT_WIDTH = 100;
    private final static float DEFAULT_HEIGHT = 100;
    private final static float START_ANGLE = 135;
    private final static float SWEEP_ANGLE = 270;
    private float mMaxProgress;
    private float mCurrentAngle = 0;
    private int mWidth, mHeight;                       //view最终真实宽高
    private float k;  // sweepAngle / mMaxProgress 的值

    private ValueAnimator mProgressValueAnimator;
    private OnValueChangeListener valueChangeListener;

    private Context mContext;

    /**
     * 通过new的过程来生成自定义view的对象,进第一个构造方法
     *
     * @param context
     */
    public ArcProgressView(Context context) {
        super(context);
    }

    /**
     * 在xml布局中inflate该view或findviewbyid方法来找到view，进入第二构造方法
     *
     * @param context
     * @param attrs
     */
    public ArcProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 同第二个构造方法一样，只不过多了一个style
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ArcProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setWillNotDraw(false);  //触发view调用重写onDraw()方法；
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcProgresslView);
        //获取自定义属性和默认值
        mArcColor = typedArray.getColor(R.styleable.ArcProgresslView_ArcColor, Color.RED);
        mArcProgressColor = typedArray.getColor(R.styleable.ArcProgresslView_ArcProgressColor, Color.GREEN);
        mArcWidth = typedArray.getDimension(R.styleable.ArcProgresslView_ArcWidth, 1);
        mDuration = typedArray.getInteger(R.styleable.ArcProgresslView_duration, 0);
        mLineWith = typedArray.getInteger(R.styleable.ArcProgresslView_LineWidth, 4);
        mLineSpace = typedArray.getInteger(R.styleable.ArcProgresslView_LineSpace, 4);
        typedArray.recycle();
        initValueAnimator();
    }

    /**
     * 定义动画。动画完毕后同样肩负着刷新画布的任务
     */
    private void initValueAnimator() {
        mProgressValueAnimator = new ValueAnimator();
        mProgressValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mProgressValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (float) animation.getAnimatedValue();
                mCurrentAngle = value * k;
                invalidate(); //刷新画布
                if (valueChangeListener != null) {
                    valueChangeListener.onValueChange(value);
                }
            }
        });
    }

    public interface OnValueChangeListener {
        void onValueChange(float value);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centre = mWidth / 2; //获取相对于父控件圆弧圆心的x坐标
        float arcRadius = centre - mArcWidth / 2;
        float arcProgressRadius = centre - mArcWidth / 2;

        //画最外层的大弧
        mArcPaint = new Paint();
        mArcPaint.setColor(mArcColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mArcWidth); //设置圆环的宽度
        mArcPaint.setPathEffect(new DashPathEffect(new float[]{mLineWith, mLineSpace}, 0));
        mArcPaint.setAntiAlias(true);  //消除锯齿
        RectF oval = new RectF(centre - arcRadius, centre - arcRadius, centre + arcRadius, centre + arcRadius);  //用于定义的圆弧的形状,这里是相对于父控件区域内的坐标
        canvas.drawArc(oval, START_ANGLE, SWEEP_ANGLE, false, mArcPaint);

        //画进度弧
        mArcProgressPaint = new Paint();
        mArcProgressPaint.setColor(mArcProgressColor);
        mArcProgressPaint.setStyle(Paint.Style.STROKE);
        mArcProgressPaint.setStrokeWidth(mArcWidth); //设置圆环的宽度
        mArcProgressPaint.setPathEffect(new DashPathEffect(new float[]{mLineWith, mLineSpace}, 0));
        mArcProgressPaint.setAntiAlias(true);  //消除锯齿
        RectF oval1 = new RectF(centre - arcProgressRadius, centre - arcProgressRadius, centre + arcProgressRadius, centre + arcProgressRadius);  //用于定义的圆弧的形状,这里是相对于父控件区域内的坐标
        canvas.drawArc(oval1, START_ANGLE, mCurrentAngle, false, mArcProgressPaint);
    }

    /**
     * 重写onMeasure()方法，使其在布局中能更好的控制的view的宽高。如果不重写则会出现无论设置much_layout还是wrap_layout都是much_layout的结果。
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureSize(DensityUtil.dpi2px(mContext, DEFAULT_WIDTH), widthMeasureSpec);
        int height = measureSize(DensityUtil.dpi2px(mContext, DEFAULT_HEIGHT), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureSize(int defaultSize, int spec) {
        int specMode = MeasureSpec.getMode(spec);
        int specSize = MeasureSpec.getSize(spec);
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            return Math.min(defaultSize, specSize);
        }
        return defaultSize;
    }

    /**
     * 重写onSizeChanged()获取View最终的宽高；
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    /**
     * 为进度设置动画
     *
     * @param last
     * @param current
     */
    private void setAnimation(float last, float current, int duration) {
        if (mProgressValueAnimator != null) {
            mProgressValueAnimator.setFloatValues(last, current);
            mProgressValueAnimator.setDuration(duration);
            mProgressValueAnimator.start();
        }
    }

    public void setMaxProgress(float maxProgress) {
        this.mMaxProgress = maxProgress;
        k = SWEEP_ANGLE / maxProgress;
    }

    public void setProgress(float lastProgress, float currentProgress) {
        if (currentProgress > mMaxProgress) {
            currentProgress = mMaxProgress;
        } else if (currentProgress < 0) {
            currentProgress = 0;
        }
        if (lastProgress > currentProgress) {
            lastProgress = currentProgress;
        } else if (lastProgress < 0) {
            lastProgress = 0;
        }
        if (mDuration > 0) {
            mCurrentAngle = 0;
            setAnimation(lastProgress, currentProgress, mDuration);
        } else {
            mCurrentAngle = currentProgress * k;
        }
        invalidate(); //刷新画布
    }

    public void setOnValueChangeListener(OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

}
