package lt.telesoftas.lab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MyImageView extends ImageView {

	public int WIDTH = 6;
	public int HEIGHT = 6;
	private static final int NONE = 0;
	private static final int ALL = 255;
	
	private int alpha = NONE;
	
	int [][] level;
	Context c;
	Vibrator vib ;
	// This example will cause the phone to vibrate "SOS" in Morse Code
    // In Morse Code, "s" = "dot-dot-dot", "o" = "dash-dash-dash"
    // There are pauses to separate dots/dashes, letters, and words
    // The following numbers represent millisecond lengths
    int dot = 200;      // Length of a Morse Code "dot" in milliseconds
    int dash = 500;     // Length of a Morse Code "dash" in milliseconds
    int short_gap = 200;    // Length of Gap Between dots/dashes
    int medium_gap = 500;   // Length of Gap Between Letters
    int long_gap = 1000;    // Length of Gap Between Words
	long[] pattern = {
	        0,  // Start immediately
	        dot, short_gap, dot, short_gap, dot,    // s
	        short_gap,
	        
	    };
//	dash, short_gap, dash, short_gap, dash, // o
//    medium_gap,
//    dot, short_gap, dot, short_gap, dot,    // s
//    long_gap
	
	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public MyImageView(Context context) {
		super(context);
		init(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setShow(boolean show) {
		if (show) {
			alpha = ALL;
		} else {
			alpha = NONE;
		}
		setPaint();
		invalidate();
	}
	
	public void init(Context c) {
		
		this.c = c;
		vib = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
	    
		maze m  = new maze();
		WIDTH++;
		HEIGHT++;
        m.init(WIDTH, HEIGHT);
        level = m.getMap();
		
//		level = new int[WIDTH][HEIGHT];
//        for (int i = 0; i < WIDTH; i++) {
//        	for (int j = 0; j < HEIGHT; j++) {
//        		if (i == 0) level[i][j] = 1;
//        		else if (i == WIDTH - 1) level[i][j] = 1;
//        		else if (j == 0) level[i][j] = 1;
//        		else if (j == WIDTH - 1) level[i][j] = 1;
//        		else level[i][j] = 0;
//        	}
//        }
//        level[2][5] = level[4][6] = level[1][2] = level[2][3] = 1;
//        level[6][6] = 1;
//        level[HEIGHT - 1][WIDTH / 2 + 1] = 2;
//        level[0][WIDTH - 2] = 3;
        
        setPaint();
        
	}
	
	private void setPaint() {
        paintFill = new Paint();
        paintFill.setStrokeWidth(5.f);
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setAntiAlias(true);
        paintFill.setColor(Color.parseColor("#FFFF8080"));
        paintFill.setAlpha(alpha);
        
        paintEmpty = new Paint();
        paintEmpty.setStrokeWidth(5.f);
        paintEmpty.setAntiAlias(true);
        paintEmpty.setStyle(Paint.Style.FILL);
        paintEmpty.setColor(Color.parseColor("#FF808080"));
        paintEmpty.setAlpha(alpha);
        
        paintStart = new Paint();
        paintStart.setStrokeWidth(5.f);
        paintStart.setStyle(Paint.Style.FILL);
        paintStart.setAntiAlias(true);
        paintStart.setColor(Color.parseColor("#80FF8080"));
//        paintStart.setAlpha(alpha);
        
        paintFinish = new Paint();
        paintFinish.setStrokeWidth(5.f);
        paintFinish.setAntiAlias(true);
        paintFinish.setStyle(Paint.Style.FILL);
        paintFinish.setColor(Color.parseColor("#FF80FF80"));
//        paintFinish.setAlpha(alpha);
        
        paintReturn = new Paint();
        paintReturn.setStrokeWidth(5.f);
        paintReturn.setAntiAlias(true);
        paintReturn.setStyle(Paint.Style.FILL);
        paintReturn.setColor(Color.parseColor("#80FFFF80"));
//        paintReturn(alpha);
	}
	
	private Paint paintFill;
	private Paint paintEmpty;
	private Paint paintStart;
	private Paint paintFinish;
	private Paint paintReturn;
	private int cellWidth;
	
	private boolean show = false;
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		int size = canvas.getWidth();
        cellWidth = size / WIDTH;
        
        for (int i = 0; i < WIDTH; i++) {
        	for (int j = 0; j < HEIGHT; j++) {
        		if (level[i][j] == 1) {
        			canvas.drawRect(j * cellWidth + 2, i * cellWidth + 2, (j + 1) * cellWidth - 2, (i + 1) * cellWidth - 2, paintFill);
        		} else if (level[i][j] == 0) {
        			canvas.drawRect(j * cellWidth + 2, i * cellWidth + 2, (j + 1) * cellWidth - 2, (i + 1) * cellWidth - 2, paintEmpty);
        		} else if (level[i][j] == 2) {
        			if (showSF || alpha == ALL) canvas.drawRect(j * cellWidth + 2, i * cellWidth + 2, (j + 1) * cellWidth - 2, (i + 1) * cellWidth - 2, paintStart);
        			startI = i;
        			startJ =j;
        		} else if (level[i][j] == 3) {
        			if (showSF || alpha == ALL) canvas.drawRect(j * cellWidth + 2, i * cellWidth + 2, (j + 1) * cellWidth - 2, (i + 1) * cellWidth - 2, paintFinish);
        			finishI = i;
        			finishJ = j;
        		}
        	}
        }
        
        if (show) {
			canvas.drawRect(lastJ * cellWidth + 2, lastI * cellWidth + 2, (lastJ + 1) * cellWidth - 2, (lastI + 1) * cellWidth - 2, paintReturn);

        }
	}
	
	private int startI = 0;
	private int startJ = 1;
	private int finishI;
	private int finishJ;
	
	private float tx;
	private float ty;
	private int exitI = startI;
	private int exitJ = startJ;
	
	private int lastI = startI;
	private int lastJ = startJ;
	private boolean vibrating = false;
	private boolean started = false;
	private boolean showSF = true;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		tx = event.getX();
		ty = event.getY();
		int j = (int) ((tx / cellWidth));
		int i = (int) ((ty / cellWidth));
		
		if (i < 0) i = 0;
		if (j < 0) j = 0;
		if (i >= WIDTH || j >= HEIGHT) {
			showRunnable.run();
			return true;
		}
		if (i >= WIDTH) i = WIDTH - 1;
		if (j >= HEIGHT) j = HEIGHT - 1;
		
		if (!started ) {
			if (level[i][j] == 2) {
				started = true;
				cancel();
				showSF = false;
				invalidate();
			}
			return true;
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
			lastI = startI;
			lastJ = startJ;
			started = false;
			showSF  = true;
			cancel();
			showRunnable.run();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			showSF = false;
			invalidate();
		}
		
		if (level[i][j] == 1) {
			vibrate();
			exitI = lastI;
			exitJ = lastJ;
		} else {
			if (!vibrating ) {
				lastI = i;
				lastJ = j;
				if (level[i][j] == 3) {
					playWin();
				}
			}
			if (i == exitI && j == exitJ) {
				cancel();
			}
		} 
		
//		invalidate();
		return true;
	}
	
	public void vibrate() {
		if (!vibrating) {
			showReturn.postDelayed(showRunnable, 1000);
			vibrating = true;
			vib.vibrate(pattern, 0);
		}
	}
	
	Handler showReturn = new Handler() {
		public void handleMessage(android.os.Message msg) {};
	};
	
	Runnable showRunnable = new Runnable() {
		
		@Override
		public void run() {
			show = true;
			MyImageView.this.invalidate();
		}
	};
	
	public void cancel() {
		vib.cancel();
		showReturn.removeCallbacks(showRunnable);
		show = false;
		vibrating = false;
		invalidate();
	}
	
	private void playWin() {
		started = false;
		 MediaPlayer mp = MediaPlayer.create(c,
                 R.raw.victory_fanfare);
         mp.start();
         mp.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.release();
			}
		});
         init(c);
         showSF = true;
         invalidate();
    }
	
	      
	     

}
