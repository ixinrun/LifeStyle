package com.ixinrun.lifestyle.common.widget.float_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.core.view.ViewCompat;

import com.ixinrun.base.utils.AppUtil;
import com.ixinrun.base.utils.DensityUtil;
import com.ixinrun.base.utils.ScreenUtil;
import com.ixinrun.base.utils.StatusBarUtil;

/**
 * 描述: 悬浮球
 * </p>
 *
 * @author xinrun
 * @date 2021/4/15
 */
public class FloatView extends FrameLayout {
    private static final int TOUCH_TIME_THRESHOLD = 150;
    private float mOriginalX;
    private float mOriginalY;
    private long mLastTouchDownTime;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mStatusBarHeight;
    private Handler mHandler;
    private MoveAnimator mMoveAnimator;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowParams;

    private float mDesX, mDesY;
    private boolean isFix;
    private boolean isAttachEdge;
    private int mAttachEdgeMargin;
    private boolean isShallow;
    private float mAlpha;
    private View.OnClickListener mOnClickListener;

    public FloatView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mScreenWidth = ScreenUtil.getScreenWidth();
        mScreenHeight = ScreenUtil.getScreenHeight();
        mStatusBarHeight = StatusBarUtil.getStatusBarHeight();
        mDesX = (float) (mScreenWidth * Math.random());
        mDesY = (float) (mScreenHeight * Math.random());
        isFix = false;
        isAttachEdge = false;
        mAttachEdgeMargin = 10;
        isShallow = false;
        mAlpha = 0.8f;
        mHandler = new Handler(Looper.getMainLooper());
        mMoveAnimator = new MoveAnimator();
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mWindowParams = new WindowManager.LayoutParams();
        setLayoutParams(mWindowParams);
        setClickable(true);
        //visible or gone
        AppUtil.addForegroundCallback(isForeground -> setVisibility(isForeground ? VISIBLE : GONE));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //start position
        updatePosition(mDesX, mDesY);
        //alpha
        setAlpha(isShallow ? mAlpha : 1);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event == null) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setAlpha(1f);
                changeOriginalTouchParams(event);
                mMoveAnimator.stop();
                break;

            case MotionEvent.ACTION_MOVE:
                if (!isFix) {
                    updateViewPosition(event);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (!isFix && isAttachEdge) {
                    moveToEdge();
                }
                if (isOnClickEvent()) {
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(this);
                    }
                }

                if (isShallow) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setAlpha(mAlpha);
                        }
                    }, 5000);
                }
                break;

            default:
                break;
        }
        return false;
    }

    private void changeOriginalTouchParams(MotionEvent event) {
        mOriginalX = event.getX();
        mOriginalY = event.getY();
        mLastTouchDownTime = System.currentTimeMillis();
    }

    private void updateViewPosition(MotionEvent event) {
        float desX = event.getRawX() - mOriginalX;
        if (desX < 0) {
            desX = 0;
        }
        if (desX > mScreenWidth - getWidth()) {
            desX = mScreenWidth - getWidth();
        }

        float desY = event.getRawY() - mOriginalY - mStatusBarHeight;
        if (desY < 0) {
            desY = 0;
        }
        if (desY > mScreenHeight - getHeight()) {
            desY = mScreenHeight - getHeight();
        }
        updatePosition(desX, desY);
    }

    private void moveToEdge() {
        float moveXDistance = isNearestLeft(mWindowParams) ? mAttachEdgeMargin : mScreenWidth - getWidth() - mAttachEdgeMargin;
        if (mWindowParams.y < mAttachEdgeMargin) {
            mMoveAnimator.start(mWindowParams, moveXDistance, mAttachEdgeMargin);
        } else if (mScreenHeight - (mWindowParams.y + getHeight() + mStatusBarHeight) < mAttachEdgeMargin) {
            mMoveAnimator.start(mWindowParams, moveXDistance, mScreenHeight - mStatusBarHeight - getHeight() - mAttachEdgeMargin);
        } else {
            mMoveAnimator.start(mWindowParams, moveXDistance, mWindowParams.y);
        }
    }

    private boolean isNearestLeft(WindowManager.LayoutParams windowParams) {
        int middle = mScreenWidth / 2;
        return windowParams.x < middle;
    }

    private boolean isOnClickEvent() {
        return System.currentTimeMillis() - mLastTouchDownTime < TOUCH_TIME_THRESHOLD;
    }

    class MoveAnimator implements Runnable {
        private float destinationX;
        private float destinationY;
        private long startingTime;
        private WindowManager.LayoutParams windowParams;

        void start(WindowManager.LayoutParams windowParams, float x, float y) {
            this.destinationX = x;
            this.destinationY = y;
            this.startingTime = System.currentTimeMillis();
            this.windowParams = windowParams;
            mHandler.post(this);
        }

        @Override
        public void run() {
            if (getRootView() == null || getRootView().getParent() == null) {
                return;
            }
            float progress = Math.min(1, (System.currentTimeMillis() - startingTime) / 400f);
            float deltaX = (destinationX - windowParams.x) * progress;
            float deltaY = (destinationY - windowParams.y) * progress;

            updatePosition(windowParams.x + deltaX, windowParams.y + deltaY);

            if (progress < 1) {
                mHandler.post(this);
            }
        }

        private void stop() {
            mHandler.removeCallbacks(this);
        }
    }

    private void updatePosition(float x, float y) {
        mDesX = x;
        mDesY = y;
        mWindowParams.x = (int) x;
        mWindowParams.y = (int) y;
        if (ViewCompat.isAttachedToWindow(this)) {
            mWindowManager.updateViewLayout(this, mWindowParams);
        }
    }

    /**
     * 设置悬浮内容
     *
     * @param resid
     * @return
     */
    public FloatView setView(@DrawableRes int resid, int width, int height) {
        removeAllViews();
        ImageView iv = new ImageView(getContext());
        iv.setBackgroundResource(resid);
        if (width != 0 || height != 0) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    DensityUtil.dpi2px(getContext(), width),
                    DensityUtil.dpi2px(getContext(), height));
            iv.setLayoutParams(lp);
        }
        addView(iv);
        return this;
    }

    /**
     * 设置悬浮内容
     *
     * @param view
     * @return
     */
    public FloatView setView(View view) {
        removeAllViews();
        addView(view);
        return this;
    }

    /**
     * 设置初始位置
     */
    public FloatView setStartPosition(int x, int y) {
        this.mDesX = x;
        this.mDesY = y;
        return this;
    }

    /**
     * 是否固定
     */
    public FloatView setFix(boolean isFix) {
        this.isFix = isFix;
        return this;
    }

    /**
     * 是否自动贴边
     */
    public FloatView setAttachEdge(boolean isAttachEdge) {
        this.isAttachEdge = isAttachEdge;
        return this;
    }

    /**
     * 自动贴边边界距离
     */
    public FloatView setAttachEdgeMargin(int mAttachEdgeMargin) {
        this.mAttachEdgeMargin = mAttachEdgeMargin;
        return this;
    }

    /**
     * 颜色变浅
     *
     * @param isShallow 是否变浅
     */
    public FloatView setShallow(boolean isShallow) {
        this.isShallow = isShallow;
        return this;
    }

    /**
     * 设置透明度
     *
     * @param alpha 透明度值（0f-1f）
     */
    public FloatView setShallowAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        if (alpha < 0) {
            this.mAlpha = 0;
        } else if (alpha > 1f) {
            this.mAlpha = 1f;
        } else {
            this.mAlpha = alpha;
        }
        return this;
    }

    /**
     * 设置 floatView事件监听
     */
    public FloatView setFloatViewClickListener(View.OnClickListener l) {
        this.mOnClickListener = l;
        return this;
    }

}
