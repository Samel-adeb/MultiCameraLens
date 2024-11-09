package com.my.newproject11;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.util.*;

import java.io.File;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;

import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.my.newproject11.R;

import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.widget.NestedScrollView;

public class ImagesActivity extends AppCompatActivity {


    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();

    private LinearLayout linear1;
    private NestedScrollView vscroll1;
    private ImageView imageview1;
    private TextView textview1;
    private LinearLayout linear2;
    private RecyclerView recyclerview1;

    private SharedPreferences lastFile;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.images);
        initialize(_savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
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
        vscroll1 = (NestedScrollView) findViewById(R.id.vscroll1);
        imageview1 = (ImageView) findViewById(R.id.imageview1);
        textview1 = (TextView) findViewById(R.id.textview1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        recyclerview1 = (RecyclerView) findViewById(R.id.recyclerview1);
        lastFile = getSharedPreferences("lastFile", Activity.MODE_PRIVATE);

        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                finish();
            }
        });
    }

    private void initializeLogic() {
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
        recyclerview1.setAdapter(new Recyclerview1Adapter(list));
        recyclerview1.setLayoutManager(new GridLayoutManager(ImagesActivity.this, 2));
        recyclerview1.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {

        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {

            default:
                break;
        }
    }

    public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;

        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View _v = _inflater.inflate(R.layout.gridview, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }

        @Override
        public void onBindViewHolder(ViewHolder _holder, @SuppressLint("RecyclerView") final int _position) {
            View _view = _holder.itemView;

            final androidx.cardview.widget.CardView cardview1 = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview1);
            final RelativeLayout capturedImage = (RelativeLayout) _view.findViewById(R.id.capturedImage);
            final ImageView image3 = (ImageView) _view.findViewById(R.id.image3);
            final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
            final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);

            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _view.setLayoutParams(_lp);
            image3.setImageBitmap(decodeSampleBitmapFromPath(_data.get((int) _position).get("filePath").toString(), 1024, 1024));
            if (_data.get((int) _position).get("filePath").toString().endsWith("dng")) {
                textview1.setText("RAW");
            } else {
                textview1.setText("JPEG");
            }
            capturedImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    intent.setClass(getApplicationContext(), ViewActivity.class);
                    intent.putExtra("position", String.valueOf((long) (_position)));
                    startActivity(intent);
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }

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
    public float getDip(int _input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
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
