package com.lijiadayuan.lishijituan.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lijiadayuan.lishijituan.R;

public class RoundRectImageView extends ImageView {

	private Context mContext;
	private boolean isRound = false;
	private int cornerRadius = 0;
	private int topLeftCornerRadius = 0;
	private int topRightCornerRadius = 0;
	private int bottomLeftCornerRadius = 0;
	private int bottomRightCornerRadius = 0;
	private float strokeWidth = 0;
	private int strokeColor = Color.WHITE;
	
	private Paint paint;
	private Paint blankPaint;
	private Paint strokePaint;
	
	private RectF rect;
	private RectF strokeRect;
	private Path rectPath;
	private float[] rectRadius;

	private Paint bgPaint;
	private int bgColor = Color.TRANSPARENT;
	
	public RoundRectImageView(Context context) {
		super(context);
		mContext = context;
	}

	public RoundRectImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setCustomAttributes(attrs);
	}

	public RoundRectImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		setCustomAttributes(attrs);
	}

	private void setCustomAttributes(AttributeSet attrs) {
		TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.roundRectimageview);
		cornerRadius = a.getDimensionPixelSize(R.styleable.roundRectimageview_cornerRadius, 0);
		isRound = a.getBoolean(R.styleable.roundRectimageview_round, false);
		topLeftCornerRadius = a.getDimensionPixelSize(R.styleable.roundRectimageview_topLeftCornerRadius, 0);
		topRightCornerRadius = a.getDimensionPixelSize(R.styleable.roundRectimageview_topRightCornerRadius, 0);
		bottomLeftCornerRadius = a.getDimensionPixelSize(R.styleable.roundRectimageview_bottomLeftCornerRadius, 0);
		bottomRightCornerRadius = a.getDimensionPixelSize(R.styleable.roundRectimageview_bottomRightCornerRadius, 0);
		if(topLeftCornerRadius > 0 || topRightCornerRadius > 0 || bottomLeftCornerRadius > 0 || bottomRightCornerRadius > 0) {
			rectRadius = new float[]{
					topLeftCornerRadius, topLeftCornerRadius, 
					topRightCornerRadius, topRightCornerRadius,
					bottomRightCornerRadius, bottomRightCornerRadius,
					bottomLeftCornerRadius, bottomLeftCornerRadius};
		}
		bgColor = a.getColor(R.styleable.roundRectimageview_backgroundcolor, Color.TRANSPARENT);
		strokeWidth = a.getDimensionPixelSize(R.styleable.roundRectimageview_strokeWidth, 0);
		strokeColor = a.getColor(R.styleable.roundRectimageview_strokeColor, Color.WHITE);
		a.recycle();
	}

	PaintFlagsDrawFilter canvasDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.setDrawFilter(canvasDrawFilter);
		int sc = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
		if(bgColor != Color.TRANSPARENT) {
			if(bgPaint == null) {
				bgPaint = new Paint();
				bgPaint.setColor(bgColor);
			}
			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), bgPaint);
		}
		super.onDraw(canvas);

		if(isRound || cornerRadius > 0 || rectRadius != null) {
			if(paint == null) {
				paint = new Paint();
				paint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
			}
			int sc2 = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), paint, Canvas.ALL_SAVE_FLAG);
			canvas.drawColor(Color.BLACK);
			if(blankPaint == null) {
				blankPaint = new Paint();
				blankPaint.setColor(Color.TRANSPARENT);
				blankPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
			} 
			
			if(isRound) { //draw round mask
				float radius = Math.min(canvas.getWidth()/2, canvas.getHeight()/2);
				canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, radius, blankPaint);
			} else if(cornerRadius > 0) { //draw roundrect mask
				cornerRadius = Math.min(Math.min(canvas.getWidth()/2, canvas.getHeight()/2), cornerRadius);
				if(rect == null) {
					rect = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
				}
				canvas.drawRoundRect(rect, cornerRadius, cornerRadius, blankPaint);
			} else if(rectRadius != null) {
				if(rectPath == null) {
					rectPath = new Path();
					rectPath.addRoundRect(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), rectRadius, Path.Direction.CCW);
					rectPath.close();
				}
				canvas.drawPath(rectPath, blankPaint);
			}
			
			canvas.restoreToCount(sc2);
		}
		
		canvas.restoreToCount(sc);
		
		//描边
		if(strokeWidth > 0) {
			if(strokePaint == null) {
				strokePaint = new Paint();
				strokePaint.setStrokeWidth(strokeWidth);
				strokePaint.setColor(strokeColor);
				strokePaint.setStyle(Paint.Style.STROKE);
			}
			if(isRound) { //draw round mask
				float radius = Math.min(canvas.getWidth()/2, canvas.getHeight()/2)-strokeWidth/2;
				canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, radius, strokePaint);
			} else if(cornerRadius > 0) { //draw roundrect mask
				cornerRadius = Math.min(Math.min(canvas.getWidth()/2, canvas.getHeight()/2), cornerRadius);
				if(strokeRect == null) {
					strokeRect = new RectF(strokeWidth/2, strokeWidth/2, canvas.getWidth()-strokeWidth, canvas.getHeight()-strokeWidth);
				}
				canvas.drawRoundRect(rect, cornerRadius, cornerRadius, strokePaint);
			} else if(rectRadius != null) {
				if(rectPath == null) {
					rectPath = new Path();
					rectPath.addRoundRect(new RectF(strokeWidth/2, strokeWidth/2, canvas.getWidth()-strokeWidth, canvas.getHeight()-strokeWidth), rectRadius, Path.Direction.CCW);
					rectPath.close();
				}
				canvas.drawPath(rectPath, strokePaint);
			} else {
				if(rect == null) {
					rect = new RectF(strokeWidth/2, strokeWidth/2, canvas.getWidth()-strokeWidth, canvas.getHeight()-strokeWidth);
				}
				canvas.drawRect(rect, strokePaint);
			}
		}
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		rect = null;
		strokeRect = null;
		rectPath = null;
	}
}
