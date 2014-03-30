package com.example.drawer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class BrushView extends View {
	private Paint brush= new Paint();
	private Path path= new Path();
	public Button btnEraseAll;
	public LayoutParams params;
	public OutputStream osi;
	
	public BrushView(Context context, OutputStream os)
	{
		super(context);
		brush.setAntiAlias(true);
		brush.setColor(Color.WHITE);
		brush.setStyle(Paint.Style.STROKE);
		brush.setStrokeJoin(Paint.Join.ROUND);
		brush.setStrokeWidth(10f);
		btnEraseAll=new Button(context);
		btnEraseAll.setText("Erase Everything");
		params= new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		btnEraseAll.setLayoutParams(params);
		btnEraseAll.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				path.reset();
				postInvalidate();
			}
			
		});
		osi=os;
	}
	public void setOutputStream(OutputStream os)
	{
		osi=os;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		float pointX= event.getX();
		float pointY= event.getY();
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				path.moveTo(pointX, pointY);
				return true;
			case MotionEvent.ACTION_MOVE:
				path.lineTo(pointX, pointY);
				break;
			default:
			return false;
		}
		postInvalidate();
		String temp= String.valueOf(pointX)+";"+String.valueOf(pointY)+";"+"000"+";"+"000"+";"+"255";
		byte[] by= new byte[24];
		Log.w("dSDD", "dsaasdf");
		try {
			by = temp.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.w("adsf","EXCEPTION!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int x=0;x<by.length;x++)
		{
			try {
				osi.write(by[x]);
			} catch (IOException e) {
				Log.w("ddd","EXPRESSION");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawPath(path, brush);
	}
}
