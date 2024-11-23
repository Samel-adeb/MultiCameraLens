package com.my.newproject11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;

import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.util.*;

import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;

import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.PagerAdapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import java.lang.reflect.Field;
import android.view.Menu; 
import android.view.MenuItem; 
import android.widget.PopupMenu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.my.newproject11.R;

import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;

import java.io.File;

public class ViewActivity extends AppCompatActivity {


    private double position = 0;
    private double screenWidth = 0;
    private HashMap<String, Object> mcurrent = new HashMap<>();

    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private ArrayList<String> popup = new ArrayList<>();

    private LinearLayout linear1;
    private LinearLayout linear2;
    private ImageView imageview1;
    private TextView textview1;
    private RelativeLayout linear5;
    private ImageView imageview2;
    private LinearLayout linear3;
    private LinearLayout linear4;
    private ImageView imageview3;
    private ViewPager viewpager1;

    private SharedPreferences lastFile;
    private PopupWindow pop;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.view);
        initialize(_savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        } else {
            initializeLogic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            initializeLogic();
        }
    }

    private void initialize(Bundle _savedInstanceState) {

        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        imageview1 = (ImageView) findViewById(R.id.imageview1);
        textview1 = (TextView) findViewById(R.id.textview1);
        linear5 = (RelativeLayout) findViewById(R.id.linear5);
        imageview2 = (ImageView) findViewById(R.id.imageview2);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        linear4 = (LinearLayout) findViewById(R.id.linear4);
        imageview3 = (ImageView) findViewById(R.id.imageview3);
        viewpager1 = (ViewPager) findViewById(R.id.viewpager1);
        lastFile = getSharedPreferences("lastFile", Activity.MODE_PRIVATE);

        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                onBackPressed();
            }
        });
        
        imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                PopupMenu popup = new PopupMenu(ViewActivity.this, imageview2);

Menu menu = popup.getMenu();

String[] items = {"Open File", "Delete File"};
for (String item : items) {
	    menu.add(item);
}

try {
	    Field mFieldPopup = popup.getClass().getDeclaredField("mPopup");
	    mFieldPopup.setAccessible(true);
	    Object mPopup = mFieldPopup.get(popup);
	    mPopup.getClass().getDeclaredMethod("setForceShowIcon", boolean.class).invoke(mPopup, true);
} catch (Exception e) {
	    e.printStackTrace();
}


popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
	
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getTitle().toString()) {
			
			case "Open File":
			openDelete(true, viewpager1.getCurrentItem());
			return true;
			case "Delete File":
			openDelete(false, viewpager1.getCurrentItem());
			return true;
			
			default: return false;
		}
	}
});
popup.show();
            }
        });

        viewpager1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int _position, float _positionOffset, int _positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int _position) {
                refreshView(list.get((int) (_position)));
            }

            @Override
            public void onPageScrollStateChanged(int _scrollState) {

            }
        });
    }

    private void initializeLogic() {
        screenWidth = getDisplayWidthPixels(getApplicationContext());
        position = Double.parseDouble(getIntent().getStringExtra("position"));
        ArrayList<HashMap<String, Object>> savedList = new ArrayList<>();
        if (lastFile.getString("savedList", "").equals("")) {

        } else {
            savedList = new Gson().fromJson(lastFile.getString("savedList", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
        }
        for (int i = 0; i < (int) (savedList.size()); i++) {
            if (isExistFile(savedList.get((int) i).get("filePath").toString())) {
                list.add(savedList.get((int) (i)));
            }
        }
        final float scaleFactor = 0.84f;
        viewpager1.setPageMargin((int) (280 - screenWidth));
        viewpager1.setOffscreenPageLimit(2);
        viewpager1.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page1, float position) {
                page1.setScaleY((1 - Math.abs(position) * (1 - scaleFactor)));
                page1.setScaleX(scaleFactor + Math.abs(position) * (1 - scaleFactor));
            }
        });
        refreshView(list.get((int) (position)));
        viewpager1.setAdapter(new Viewpager1Adapter(list));
        viewpager1.setCurrentItem((int) position);
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {

        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent.setClass(getApplicationContext(), ImagesActivity.class);
        startActivity(intent);
        finish();
    }


    public void refreshView(final HashMap<String, Object> mapFr) {
        mcurrent = mapFr;
        textview1.setText(mapFr.get("fileName").toString());
        imageview3.setImageBitmap(decodeSampleBitmapFromPath(mapFr.get("filePath").toString(), 1024, 1024));
    }

    public Bitmap decodeSampleBitmapFromPath(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public boolean isExistFile(String path) {
        File file = new File(path);
        return file.exists();
    }

    public boolean deleteFile(String path) {
        File file = new File(path);

        if (!file.exists()) return false;

        if (file.isFile()) {
            file.delete();
            return false;
        }

        File[] fileArr = file.listFiles();

        if (fileArr != null) {
            for (File subFile : fileArr) {
                if (subFile.isDirectory()) {
                    deleteFile(subFile.getAbsolutePath());
                }

                if (subFile.isFile()) {
                    subFile.delete();
                }
            }
        }

        file.delete();
        return false;
    }

    public void scrollToPosition(final double position) {
        viewpager1.setCurrentItem((int) position);
        refreshView(list.get((int) (position)));
    }

    public int getDisplayWidthPixels(Context _context) {
        return _context.getResources().getDisplayMetrics().widthPixels;
    }

    public void openDelete(final boolean open, final double position) {
        if (open) {
            try {
                String filePath = list.get((int) position).get("filePath").toString();
                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse("file://" + filePath), "image/*");
                i.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(i);
                
            } catch (Exception e) {

            }
        } else {
            ArrayList<HashMap<String, Object>> savedList = new ArrayList<>();
            if (lastFile.getString("savedList", "").equals("")) {

            } else {
                savedList = new Gson().fromJson(lastFile.getString("savedList", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
            }
            if ((position == (list.size() - 1)) && (list.size() > 1)) {
                lastFile.edit().putString("lastFile", list.get((int) position - 1).get("filePath").toString()).commit();
            } else {
                if (position == (list.size() - 1)) {
                    lastFile.edit().putString("lastFile", "").commit();
                }
            }
            deleteFile(list.get((int) position).get("filePath").toString());
            savedList.remove(list.get((int) (position)));
            lastFile.edit().putString("savedList", new Gson().toJson(savedList)).commit();
            intent.setClass(getApplicationContext(), ImagesActivity.class);
            startActivity(intent);
            finish();
        }
    }


    public class Viewpager1Adapter extends PagerAdapter {
        Context _context;
        ArrayList<HashMap<String, Object>> _data;

        public Viewpager1Adapter(Context _ctx, ArrayList<HashMap<String, Object>> _arr) {
            _context = _ctx;
            _data = _arr;
        }

        public Viewpager1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _context = getApplicationContext();
            _data = _arr;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public boolean isViewFromObject(View _view, Object _object) {
            return _view == _object;
        }

        @Override
        public void destroyItem(ViewGroup _container, int _position, Object _object) {
            _container.removeView((View) _object);
        }

        @Override
        public int getItemPosition(Object _object) {
            return super.getItemPosition(_object);
        }

        @Override
        public CharSequence getPageTitle(int pos) {
            // use the activitiy event (onTabLayoutNewTabAdded) in order to use this method
            return "page " + String.valueOf(pos);
        }

        @Override
        public Object instantiateItem(ViewGroup _container, final int _position) {
            View _view = LayoutInflater.from(_context).inflate(R.layout.view_slide, _container, false);

            final androidx.cardview.widget.CardView cardview1 = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview1);
            final RelativeLayout capturedImage = (RelativeLayout) _view.findViewById(R.id.capturedImage);
            final ImageView image3 = (ImageView) _view.findViewById(R.id.image3);

            image3.setImageBitmap(decodeSampleBitmapFromPath(_data.get((int) _position).get("filePath").toString(), 1024, 1024));
            capturedImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    scrollToPosition(_position);
                }
            });

            _container.addView(_view);
            return _view;
        }
    }

    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationX(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[0];
    }

    @Deprecated
    public int getLocationY(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[1];
    }

    @Deprecated
    public int getRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double) _arr.keyAt(_iIdx));
        }
        return _result;
    }

    @Deprecated
    public float getDip(int _input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }

}
