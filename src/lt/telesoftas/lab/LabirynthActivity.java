package lt.telesoftas.lab;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class LabirynthActivity extends Activity {
    /** Called when the activity is first created. */
	public static final int WIDTH = 7;
	public static final int HEIGHT = 7;
	
    GridView grid;
    ImageView canvas;
    MyImageView vv;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);
//        grid = (GridView)findViewById(R.id.gridView1);
//        
//        grid.setAdapter(new GridAdapter());
//        
//        grid.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				
//				return false;
//			}
//		});
        
        int [][] level = new int[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
        	for (int j = 0; j < HEIGHT; j++) {
        		level[i][j] = 1;
        	}
        }
        
        canvas = (ImageView)findViewById(R.id.canvas);
        
        int size = canvas.getWidth();
        int cellSize = size / WIDTH;
        
        vv = (MyImageView)findViewById(R.id.canvas);
        show = true;
        findViewById(R.id.bjuton).setOnClickListener(new OnClickListener() {
			

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vv.setShow(show);
				show = !show;
			}
		});
        
        
    }
	private boolean show;
	
	@Override
	protected void onDestroy() {
		
		
		vv.cancel();
		super.onDestroy();
	}
	
	public class GridAdapter extends BaseAdapter {
	 
		public GridAdapter() {
			
		}
	 
		public View getView(int position, View convertView, ViewGroup parent) {
	 
			TextView cell;
			if (convertView == null) {
			cell = new TextView(getApplicationContext());
			cell.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
//					if (event.getAction() == MotionEvent.ACTION_MOVE) {
						v.setBackgroundColor(Color.RED);
//					} else {
//						v.setBackgroundColor(Color.BLACK);
//					}
					return true;
				}
			});
			cell.setText("" + position);
			} else {
				return convertView;
			}
			return cell;
		}
	 
		@Override
		public int getCount() {
			return 10 * 10;
		}
	 
		@Override
		public Object getItem(int position) {
			return null;
		}
	 
		@Override
		public long getItemId(int position) {
			return 0;
		}
	 
	}
}