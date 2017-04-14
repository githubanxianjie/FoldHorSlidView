package comm.custom;

import android.view.View;

/**
 * 当前viewpager中个变压器，使pageadpter按对应规则滑动，
 * @author anxianjie-g
 * 2017-3-24
 */
public class MyPageTransformer implements CustomViewPager.PageTransformer {

	private static final float MIN_SCALE = 0.8f;

	private float getScale(float position) {
		return MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
	}

	private void animate(View view, float position, int pageWidth,
			int pageHeight) {
		float scaleFactor = getScale(position);
		view.setPivotX(pageWidth * 0.5f);
		view.setPivotY(pageHeight * 0.5f);

		// mRot = (ROT_MAX * position);
		// view.setRotationY(mRot);

		view.setScaleY(scaleFactor);
		view.setScaleX(scaleFactor);
	}

	@Override
	public void transformPage(View view, float position) {
		// TODO Auto-generated method stub

		int pageWidth = view.getWidth();
		int pageHeight = view.getHeight();

		if (position < -1) { // [-Infinity,-1)
			// This page is way off-screen to the left.
			animate(view, position, pageWidth, pageHeight);

		} else if (position <= 0) { // [-1,0]
			// Use the default slide transition when moving to the left page
			animate(view, position, pageWidth, pageHeight);

		} else if (position <= 1) { // (0,1]
			// Fade the page out.
			// Counteract the default slide transition

			// Scale the page down (between MIN_SCALE and 1)
			animate(view, position, pageWidth, pageHeight);

		} else { // (1,+Infinity]
			// This page is way off-screen to the right.
			animate(view, position, pageWidth, pageHeight);
		}

	}
}