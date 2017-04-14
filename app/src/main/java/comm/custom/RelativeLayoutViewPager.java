package comm.custom;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RelativeLayoutViewPager extends RelativeLayout {
    private static final String TAG = "RelativeLayoutViewPager";

    public RelativeLayoutViewPager(Context context) {
        super(context);
        init();
    }

    public RelativeLayoutViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RelativeLayoutViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLongClickable(true);
    }

    float downX = 0;
    float downY = 0;
    long dowTime = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	getParent().requestDisallowInterceptTouchEvent(true);

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downX = ev.getX();
            downY = ev.getY();
            dowTime = System.currentTimeMillis();
            if (onTouchEvent != null) {
                onTouchEvent.onTouchDown();
            }
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            long tillTime = System.currentTimeMillis() - dowTime;
            float x = ev.getX() - downX;
            float offsetX = Math.abs(x);
            float offsetY = Math.abs(ev.getY() - downY);
            if (tillTime < 250 && offsetX < 20 && offsetY < 20) { //ģ�ⵥ���¼�
                if (onClickPoint != null) {
                    onClickPoint.onClickPoint((int) ev.getX(), (int) ev.getY());
                    onClickPoint.onClickPointByScreen((int) ev.getRawX(), (int) ev.getRawY());
                }
            } else if (x > 100) { //�һ�
                if (onClickPoint != null) {
                    onClickPoint.toLeft();
                }
            } else if (x < -100) { //��
                if (onClickPoint != null) {
                    onClickPoint.toRight();
                }
            }

            if (onTouchEvent != null) {
                onTouchEvent.onTouchUp();
            }
        }


        return dispatchEvent(ev);
    }

    public boolean dispatchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
        return true;
    }

    private OnTouchEvent onTouchEvent;

    public void setOnTouchEvent(OnTouchEvent onTouchEvent) {
        this.onTouchEvent = onTouchEvent;
    }

    public interface OnTouchEvent {
        void onTouchDown();

        void onTouchUp();
    }

    private OnClickPoint onClickPoint;

    public void setOnClickPoint(OnClickPoint onClickPoint) {
        this.onClickPoint = onClickPoint;
    }

    public int getClickRange(int clickPointX, int clickPointY, View childView) {

        int[] parentXy = new int[2];
        getLocationInWindow(parentXy);


        int[] childXy = new int[2];
        childView.getLocationInWindow(childXy);

        int parentWidth = getWidth();
        int childWidth = childView.getWidth();

        if (clickPointX > parentXy[0] && clickPointX + childWidth / 2 < childXy[0]) {
            return ClickRange.LEFTTWO;
		}else if (clickPointX > parentXy[0] && clickPointX < childXy[0]) {
            return ClickRange.LEFT;
        } else if (clickPointX > childXy[0] + childWidth * 1.5 && clickPointX < parentWidth) {
        	return ClickRange.RIGHTTWO;
        } else if (clickPointX > childXy[0] + childWidth && clickPointX < parentWidth) {
            return ClickRange.RIGHT;
        }

        return ClickRange.CURRENT;
    }


    public interface OnClickPoint {
        void onClickPoint(int pX, int pY);

        void onClickPointByScreen(int pX, int pY);

        void toLeft();

        void toRight();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ClickRange.LEFTTWO,ClickRange.LEFT, ClickRange.CURRENT, ClickRange.RIGHT, ClickRange.RIGHTTWO})
    public @interface ClickRange {
    	int LEFTTWO = -2;
        int LEFT = -1;
        int CURRENT = 0;
        int RIGHT = 1;
        int RIGHTTWO = 2;
    }
}

