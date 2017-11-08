package reactcamera;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;


public class MaskView extends android.support.v7.widget.AppCompatImageView {
	private Paint mLinePaint;
	private Paint mAreaPaint;
	private Paint mTextPaint;
	private Paint mTopPaint;
	private Rect mCenterRect = null;
	private Context mContext;

	private String belowText;


	public MaskView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initPaint();
		mContext = context;
		Point p	= DisplayUtil.getScreenMetrics(mContext);
		widthScreen = p.x;
		heightScreen = p.y;
	}

	private void initPaint(){
		//绘制中间透明区域矩形边界的Paint
		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mLinePaint.setColor(Color.BLUE);
		mLinePaint.setStyle(Style.STROKE);
		mLinePaint.setStrokeWidth(5f);
		mLinePaint.setAlpha(0);

		//绘制四周阴影区域
		mAreaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mAreaPaint.setColor(Color.BLACK);
		mAreaPaint.setStyle(Style.FILL);
		mAreaPaint.setAlpha(150);

		//绘制下方提示文字
		mTextPaint = new Paint();
		mTextPaint.setStrokeWidth(3);
		mTextPaint.setTextSize(40);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextAlign(Paint.Align.CENTER);

		//绘制四角
		mTopPaint = new Paint();
		mTopPaint.setColor(Color.BLUE);
		mTopPaint.setStrokeWidth(10);
		mTopPaint.setStyle(Style.FILL);



	}
	public void setCenterRect(Rect r){
		this.mCenterRect = r;
		postInvalidate();
	}

	public void setText(String s){
		this.belowText = s;
	}
	public void clearCenterRect(Rect r){
		this.mCenterRect = null;
	}

	int widthScreen, heightScreen;
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(mCenterRect == null)
			return;
		//绘制四周阴影区域
		canvas.drawRect(0, 0, widthScreen, mCenterRect.top, mAreaPaint);
		canvas.drawRect(0, mCenterRect.bottom + 1, widthScreen, heightScreen, mAreaPaint);
		canvas.drawRect(0, mCenterRect.top, mCenterRect.left - 1, mCenterRect.bottom  + 1, mAreaPaint);
		canvas.drawRect(mCenterRect.right + 1, mCenterRect.top, widthScreen, mCenterRect.bottom + 1, mAreaPaint);

		//绘制目标透明区域
		canvas.drawRect(mCenterRect, mLinePaint);
		//绘制下方提示文字
		canvas.drawText(belowText,widthScreen/2,mCenterRect.bottom+100,mTextPaint);
		//绘制左上角
		canvas.drawLine(mCenterRect.left,mCenterRect.top-3,mCenterRect.left+100,mCenterRect.top-3,mTopPaint);
		canvas.drawLine(mCenterRect.left-3,mCenterRect.top-8,mCenterRect.left-3,mCenterRect.top+100,mTopPaint);
		//绘制右上角
		canvas.drawLine(mCenterRect.right,mCenterRect.top-3,mCenterRect.right-100,mCenterRect.top-3,mTopPaint);
		canvas.drawLine(mCenterRect.right+3,mCenterRect.top-8,mCenterRect.right+3,mCenterRect.top+100,mTopPaint);
		//绘制左下角
		canvas.drawLine(mCenterRect.left-3,mCenterRect.bottom+3,mCenterRect.left+100,mCenterRect.bottom+3,mTopPaint);
		canvas.drawLine(mCenterRect.left-3,mCenterRect.bottom+8,mCenterRect.left-3,mCenterRect.bottom-100,mTopPaint);
		//绘制右下角
		canvas.drawLine(mCenterRect.right+8,mCenterRect.bottom-3,mCenterRect.right-100,mCenterRect.bottom-3,mTopPaint);
		canvas.drawLine(mCenterRect.right+3,mCenterRect.bottom-8,mCenterRect.right+3,mCenterRect.bottom-100,mTopPaint);
		super.onDraw(canvas);
	}



}
