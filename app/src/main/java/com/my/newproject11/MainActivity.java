package com.my.newproject11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.my.newproject11.R;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.annotation.SuppressLint;
import android.widget.LinearLayout;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.util.*;

import java.util.*;
import java.text.*;
import java.util.HashMap;
import java.util.ArrayList;

import android.widget.ImageView;

import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

import androidx.cardview.widget.CardView;

import android.widget.ScrollView;
import android.widget.Switch;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.graphics.Typeface;

import androidx.camera.camera2.*;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraDevice;
import android.os.Environment;

public class MainActivity extends AppCompatActivity {

    public final int REQ_CD_H = 101;

    private Toolbar _toolbar;
    private AppBarLayout _app_bar;
    private CoordinatorLayout _coordinator;
    private DrawerLayout _drawer;
    private HashMap<String, Object> lensItem = new HashMap<>();
    private boolean isRaw = false;
    private String data = "";
    private Surface previewSurface;
    private SurfaceTexture surfaceTexture;
    private CameraDevice cameraDevice;
    private String devicename = "";
    private String model = "";
    private String androidversion = "";
    private String apiversion = "";
    private double frontlens = 0;
    private double backlens = 0;
    private String stereo = "";
    private MultiCameraCaptureHelper captureHelper;
    private boolean autofocus = false;

    private ArrayList<HashMap<String, Object>> lens = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> cameraViews = new ArrayList<>();
    private ArrayList<String> gs = new ArrayList<>();

    private FrameLayout linear4;
    private LinearLayout linear13;
    private LinearLayout linear1;
    private LinearLayout all1;
    private LinearLayout all2;
    private TextureView cam1;
    private TextureView cam2;
    private TextureView cam3;
    private TextureView cam4;
    private LinearLayout linear2;
    private LinearLayout linear3;
    private LinearLayout linear5;
    private ImageView imageview1;
    private LinearLayout linear14;
    private ImageView imageview2;
    private RecyclerView recyclerview1;
    private LinearLayout linear6;
    private LinearLayout linear7;
    private LinearLayout raw;
    private LinearLayout jpeg;
    private TextView textview1;
    private TextView textview2;
    private LinearLayout linear8;
    private ImageView snap;
    private LinearLayout linear9;
    private LinearLayout capturedImage;
    private CardView cardview1;
    private ImageView image3;
    private ScrollView _drawer_vscroll1;
    private LinearLayout _drawer_linear1;
    private ImageView _drawer_imageview1;
    private Switch _drawer_switch1;
    private TextView _drawer_textview1;
    private ImageView _drawer_imageview2;
    private TextView _drawer_deviceName;
    private TextView _drawer_model;
    private TextView _drawer_apiversion;
    private TextView _drawer_androidversion;
    private TextView _drawer_textview6;
    private ImageView _drawer_imageview3;
    private TextView _drawer_noCam;
    private TextView _drawer_moFrontCam;
    private TextView _drawer_noRearCam;
    private TextView _drawer_textview10;
    private ImageView _drawer_imageview4;
    private TextView _drawer_compatibility;

    private Intent h = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private File _file_h;
    private SharedPreferences lastFile;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.main);
        initialize(_savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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

        _app_bar = (AppBarLayout) findViewById(R.id._app_bar);
        _coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
        _toolbar = (Toolbar) findViewById(R.id._toolbar);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _v) {
                onBackPressed();
            }
        });
        _drawer = (DrawerLayout) findViewById(R.id._drawer);
        ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(MainActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
        _drawer.addDrawerListener(_toggle);
        _toggle.syncState();

        LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);

        linear4 = (FrameLayout) findViewById(R.id.linear4);
        linear13 = (LinearLayout) findViewById(R.id.linear13);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        all1 = (LinearLayout) findViewById(R.id.all1);
        all2 = (LinearLayout) findViewById(R.id.all2);
        cam1 = (TextureView) findViewById(R.id.cam1);
        cam2 = (TextureView) findViewById(R.id.cam2);
        cam3 = (TextureView) findViewById(R.id.cam3);
        cam4 = (TextureView) findViewById(R.id.cam4);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        linear5 = (LinearLayout) findViewById(R.id.linear5);
        imageview1 = (ImageView) findViewById(R.id.imageview1);
        linear14 = (LinearLayout) findViewById(R.id.linear14);
        imageview2 = (ImageView) findViewById(R.id.imageview2);
        recyclerview1 = (RecyclerView) findViewById(R.id.recyclerview1);
        linear6 = (LinearLayout) findViewById(R.id.linear6);
        linear7 = (LinearLayout) findViewById(R.id.linear7);
        raw = (LinearLayout) findViewById(R.id.raw);
        jpeg = (LinearLayout) findViewById(R.id.jpeg);
        textview1 = (TextView) findViewById(R.id.textview1);
        textview2 = (TextView) findViewById(R.id.textview2);
        linear8 = (LinearLayout) findViewById(R.id.linear8);
        snap = (ImageView) findViewById(R.id.snap);
        linear9 = (LinearLayout) findViewById(R.id.linear9);
        capturedImage = (LinearLayout) findViewById(R.id.capturedImage);
        cardview1 = (CardView) findViewById(R.id.cardview1);
        image3 = (ImageView) findViewById(R.id.image3);
        _drawer_vscroll1 = (ScrollView) _nav_view.findViewById(R.id.vscroll1);
        _drawer_linear1 = (LinearLayout) _nav_view.findViewById(R.id.linear1);
        _drawer_imageview1 = (ImageView) _nav_view.findViewById(R.id.imageview1);
        _drawer_switch1 = (Switch) _nav_view.findViewById(R.id.switch1);
        _drawer_textview1 = (TextView) _nav_view.findViewById(R.id.textview1);
        _drawer_imageview2 = (ImageView) _nav_view.findViewById(R.id.imageview2);
        _drawer_deviceName = (TextView) _nav_view.findViewById(R.id.deviceName);
        _drawer_model = (TextView) _nav_view.findViewById(R.id.model);
        _drawer_apiversion = (TextView) _nav_view.findViewById(R.id.apiversion);
        _drawer_androidversion = (TextView) _nav_view.findViewById(R.id.androidversion);
        _drawer_textview6 = (TextView) _nav_view.findViewById(R.id.textview6);
        _drawer_imageview3 = (ImageView) _nav_view.findViewById(R.id.imageview3);
        _drawer_noCam = (TextView) _nav_view.findViewById(R.id.noCam);
        _drawer_moFrontCam = (TextView) _nav_view.findViewById(R.id.moFrontCam);
        _drawer_noRearCam = (TextView) _nav_view.findViewById(R.id.noRearCam);
        _drawer_textview10 = (TextView) _nav_view.findViewById(R.id.textview10);
        _drawer_imageview4 = (ImageView) _nav_view.findViewById(R.id.imageview4);
        _drawer_compatibility = (TextView) _nav_view.findViewById(R.id.compatibility);
        _file_h = createNewPictureFile(getApplicationContext());
        Uri _uri_h = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            _uri_h = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_h);
        } else {
            _uri_h = Uri.fromFile(_file_h);
        }
        h.putExtra(MediaStore.EXTRA_OUTPUT, _uri_h);
        h.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        lastFile = getSharedPreferences("lastFile", Activity.MODE_PRIVATE);

        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _drawer.openDrawer(GravityCompat.START);
            }
        });

        raw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                isRaw = true;
                if (isRaw) {
                    raw.setBackground(new GradientDrawable() {
                        public GradientDrawable getIns(int a, int b, int c, int d) {
                            this.setCornerRadius(a);
                            this.setStroke(b, c);
                            this.setColor(d);
                            return this;
                        }
                    }.getIns((int) 10, (int) 2, 0xFFFFFFFF, Color.TRANSPARENT));
                    jpeg.setBackground(new GradientDrawable() {
                        public GradientDrawable getIns(int a, int b, int c, int d) {
                            this.setCornerRadius(a);
                            this.setStroke(b, c);
                            this.setColor(d);
                            return this;
                        }
                    }.getIns((int) 10, (int) 0, Color.TRANSPARENT, Color.TRANSPARENT));
                } else {
                    jpeg.setBackground(new GradientDrawable() {
                        public GradientDrawable getIns(int a, int b, int c, int d) {
                            this.setCornerRadius(a);
                            this.setStroke(b, c);
                            this.setColor(d);
                            return this;
                        }
                    }.getIns((int) 10, (int) 2, 0xFFFFFFFF, Color.TRANSPARENT));
                    raw.setBackground(new GradientDrawable() {
                        public GradientDrawable getIns(int a, int b, int c, int d) {
                            this.setCornerRadius(a);
                            this.setStroke(b, c);
                            this.setColor(d);
                            return this;
                        }
                    }.getIns((int) 10, (int) 0, Color.TRANSPARENT, Color.TRANSPARENT));
                }
            }
        });

        jpeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                isRaw = false;
                if (isRaw) {
                    raw.setBackground(new GradientDrawable() {
                        public GradientDrawable getIns(int a, int b, int c, int d) {
                            this.setCornerRadius(a);
                            this.setStroke(b, c);
                            this.setColor(d);
                            return this;
                        }
                    }.getIns((int) 10, (int) 2, 0xFFFFFFFF, Color.TRANSPARENT));
                    jpeg.setBackground(new GradientDrawable() {
                        public GradientDrawable getIns(int a, int b, int c, int d) {
                            this.setCornerRadius(a);
                            this.setStroke(b, c);
                            this.setColor(d);
                            return this;
                        }
                    }.getIns((int) 10, (int) 0, Color.TRANSPARENT, Color.TRANSPARENT));
                } else {
                    jpeg.setBackground(new GradientDrawable() {
                        public GradientDrawable getIns(int a, int b, int c, int d) {
                            this.setCornerRadius(a);
                            this.setStroke(b, c);
                            this.setColor(d);
                            return this;
                        }
                    }.getIns((int) 10, (int) 2, 0xFFFFFFFF, Color.TRANSPARENT));
                    raw.setBackground(new GradientDrawable() {
                        public GradientDrawable getIns(int a, int b, int c, int d) {
                            this.setCornerRadius(a);
                            this.setStroke(b, c);
                            this.setColor(d);
                            return this;
                        }
                    }.getIns((int) 10, (int) 0, Color.TRANSPARENT, Color.TRANSPARENT));
                }
            }
        });

        snap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                captureHelper.captureAllCameras(isRaw, devicename, model, apiversion, new ImageSavedCallback() {
                    @Override
                    public void onImageSaved() {
                        callBack();
                        System.out.println("Image has been saved successfully!");
                    }
                });
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intent.setClass(getApplicationContext(), ImagesActivity.class);
                startActivity(intent);
            }
        });

        _drawer_switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
                final boolean _isChecked = _param2;
                autofocus = _isChecked;
            }
        });
    }

    private void initializeLogic() {
        getSupportActionBar().hide();
        CameraInfoHelper cameraInfoHelper = new CameraInfoHelper(this);
        cameraInfoHelper.fetchCameraInfo();

        int lensCount = cameraInfoHelper.getLensCount();
        List<Map<String, Object>> cameraDetails = cameraInfoHelper.getCameraCharacteristicsList();
        captureHelper = new MultiCameraCaptureHelper(this, lastFile);
        frontlens = 0;
        backlens = 0;
        stereo = "No";
        devicename = Build.MANUFACTURER;
        model = Build.MODEL;
        androidversion = Build.VERSION.RELEASE;
        apiversion = Build.VERSION.SDK;
        for (int i = 0; i < (int) (lensCount); i++) {
            lensItem = new HashMap<>();
            if (i == 0) {
                lensItem.put("selected", "true");
            } else {
                lensItem.put("selected", "false");
            }
            if (cameraDetails.get((int) i).get("position").toString().contains("ront")) {
                frontlens++;
            } else {
                lensItem.put("cameraid", cameraDetails.get((int) i).get("cameraid").toString());
                backlens++;
            }
            if (cameraDetails.get((int) i).get("stereo").toString().contains("yes")) {
                stereo = "Yes";
            } else {

            }
            if (cameraDetails.get((int) i).get("position").toString().contains("ront")) {

            } else {
                lens.add(lensItem);
            }
        }
        if (lens.size() == 1) {
            Toast.makeText(getApplicationContext(), "Only one back lens available. Doesn't support multicamera", Toast.LENGTH_SHORT).show();
        }
        if (lastFile.getString("lastFile", "").equals("")) {
            capturedImage.setVisibility(View.GONE);
        } else {
            image3.setImageBitmap(decodeSampleBitmapFromPath(lastFile.getString("lastFile", ""), 1024, 1024));
        }
        _drawer_deviceName.setText("Device Name: ".concat(devicename));
        _drawer_model.setText("Model: ".concat(model));
        _drawer_apiversion.setText("API Version: ".concat(apiversion));
        _drawer_androidversion.setText("Android Version: ".concat(androidversion));
        _drawer_noCam.setText("No of cameras: ".concat(String.valueOf((long) (lensCount))));
        _drawer_moFrontCam.setText("No of front cameras: ".concat(String.valueOf((long) (frontlens))));
        _drawer_noRearCam.setText("No of rear cameras: ".concat(String.valueOf((long) (backlens))));
        _drawer_compatibility.setText("Compatibility: ".concat(stereo));
        textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.BOLD);
        textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.NORMAL);
        _drawer_textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.NORMAL);
        _drawer_deviceName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.NORMAL);
        _drawer_model.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.NORMAL);
        _drawer_apiversion.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.NORMAL);
        _drawer_androidversion.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.NORMAL);
        _drawer_textview6.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.NORMAL);
        _drawer_noCam.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.NORMAL);
        _drawer_moFrontCam.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.NORMAL);
        _drawer_noRearCam.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.NORMAL);
        _drawer_textview10.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.NORMAL);
        _drawer_compatibility.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.NORMAL);
        recyclerview1.setAdapter(new Recyclerview1Adapter(lens));
        recyclerview1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (isRaw) {
            jpeg.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int a, int b, int c, int d) {
                    this.setCornerRadius(a);
                    this.setStroke(b, c);
                    this.setColor(d);
                    return this;
                }
            }.getIns((int) 10, (int) 0, Color.TRANSPARENT, Color.TRANSPARENT));
            raw.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int a, int b, int c, int d) {
                    this.setCornerRadius(a);
                    this.setStroke(b, c);
                    this.setColor(d);
                    return this;
                }
            }.getIns((int) 10, (int) 2, 0xFFFFFFFF, Color.TRANSPARENT));
        } else {
            jpeg.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int a, int b, int c, int d) {
                    this.setCornerRadius(a);
                    this.setStroke(b, c);
                    this.setColor(d);
                    return this;
                }
            }.getIns((int) 10, (int) 2, 0xFFFFFFFF, Color.TRANSPARENT));
            raw.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int a, int b, int c, int d) {
                    this.setCornerRadius(a);
                    this.setStroke(b, c);
                    this.setColor(d);
                    return this;
                }
            }.getIns((int) 10, (int) 0, Color.TRANSPARENT, Color.TRANSPARENT));
        }
        capturedImage.setBackground(new GradientDrawable() {
            public GradientDrawable getIns(int a, int b, int c, int d) {
                this.setCornerRadius(a);
                this.setStroke(b, c);
                this.setColor(d);
                return this;
            }
        }.getIns((int) 10, (int) 4, 0xFFFFFFFF, Color.TRANSPARENT));
        snap.setBackground(new GradientDrawable() {
            public GradientDrawable getIns(int a, int b, int c, int d) {
                this.setCornerRadius(a);
                this.setStroke(b, c);
                this.setColor(d);
                return this;
            }
        }.getIns((int) 360, (int) 2, 0xFFFFFFFF, Color.TRANSPARENT));
        cam1.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {


                checkAndFilter(lens);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        });
        cam2.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {


                checkAndFilter(lens);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        });
        cam3.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {


                checkAndFilter(lens);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        });
        cam4.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {


                checkAndFilter(lens);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        });
        _drawer_imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (_drawer.isDrawerOpen(GravityCompat.START)) {
                    _drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {

        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {
            case REQ_CD_H:
                if (_resultCode == Activity.RESULT_OK) {
                    String _filePath = _file_h.getAbsolutePath();


                } else {

                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (_drawer.isDrawerOpen(GravityCompat.START)) {
            _drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void checkAndFilter(final ArrayList<HashMap<String, Object>> lmap) {
        try {
            cameraViews.clear();
            gs.clear();
        } catch (Exception e) {

        }
        for (int i = 0; i < (int) (lmap.size()); i++) {
            if (lmap.get((int) i).get("selected").toString().equals("true")) {
                cameraViews.add(lmap.get((int) (i)));
                gs.add(lmap.get((int) i).get("cameraid").toString());
            }
        }
        captureHelper.initializeCameras(cam1, cam2, cam3, cam4, all2, gs, this, autofocus);
    }


    public void setListData(final ArrayList<HashMap<String, Object>> lenslist) {
        checkAndFilter(lenslist);
        lens = lenslist;
        recyclerview1.setAdapter(new Recyclerview1Adapter(lens));
        recyclerview1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    public File createNewPictureFile(Context context) {
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = date.format(new Date()) + ".jpg";
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + fileName);
        return file;
    }

    public Bitmap decodeSampleBitmapFromPath(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public void callBack() {
        if (lastFile.getString("lastFile", "").equals("")) {
            capturedImage.setVisibility(View.GONE);
        } else {
            image3.setImageBitmap(decodeSampleBitmapFromPath(lastFile.getString("lastFile", ""), 1024, 1024));
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
            View _v = _inflater.inflate(R.layout.lens, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }

        @Override
        public void onBindViewHolder(ViewHolder _holder, @SuppressLint("RecyclerView") final int _position) {
            View _view = _holder.itemView;

            final LinearLayout linear12 = (LinearLayout) _view.findViewById(R.id.linear12);
            final LinearLayout linear13 = (LinearLayout) _view.findViewById(R.id.linear13);
            final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
            final ImageView imageview4 = (ImageView) _view.findViewById(R.id.imageview4);
            final ImageView checked = (ImageView) _view.findViewById(R.id.checked);

            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _view.setLayoutParams(_lp);
            textview1.setText("Lens ".concat(String.valueOf((long) (_position + 1))));
            if (_data.get((int) _position).get("selected").toString().equals("true")) {
                checked.setVisibility(View.VISIBLE);
            } else {
                checked.setVisibility(View.GONE);
            }
            linear12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    if (_data.get((int) _position).get("selected").toString().equals("true")) {
                        checked.setVisibility(View.GONE);
                        _data.get((int) _position).put("selected", "false");
                    } else {
                        checked.setVisibility(View.VISIBLE);
                        _data.get((int) _position).put("selected", "true");
                    }
                    setListData(_data);
                }
            });
            textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf"), Typeface.BOLD);
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

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }

}
