package com.my.newproject11;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.*;
import android.media.Image;
import android.media.ImageReader;
import android.os.Environment;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import androidx.annotation.NonNull;

import android.graphics.SurfaceTexture;
import android.graphics.Rect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.widget.Toast;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class MultiCameraCaptureHelper {

    CameraManager cameraManager;
    private final List<String> cameraIds = new ArrayList<>();
    private final Map<String, CameraDevice> cameraDevices = new HashMap<>();
    private final Map<String, TextureView> textureViews = new HashMap<>();
    private final Map<String, CameraCaptureSession> captureSessions = new HashMap<>();
    private final Map<String, ImageReader> imageReaders = new HashMap<>();
    SurfaceTexture surfaceTexture;
    private SharedPreferences lastFile;
    Surface previewSurface;
    CameraDevice cameraDevice1;
    private final Context context;
    boolean isRaw;
    String deviceName;
    String model;
    String apiVersion;
    ImageSavedCallback callback;


    public MultiCameraCaptureHelper(Context context, SharedPreferences shared) {
        this.context = context;
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        lastFile = shared;
    }

    public void initializeCameras(TextureView textureView1, TextureView textureView2, TextureView textureView3, TextureView textureView4, View part2, List<String> cameraViews, Context context, boolean enableAutoFocus) {
        try {
            cameraIds.clear();
            cameraIds.addAll(Arrays.asList(cameraManager.getCameraIdList()));
            clearOldCameraDevice();
            if (cameraManager == null) {
                cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            }
            if (cameraViews.size() == 0) {
                textureView1.setVisibility(View.GONE);
                textureView3.setVisibility(View.GONE);
                textureView2.setVisibility(View.GONE);
                textureView4.setVisibility(View.GONE);
                part2.setVisibility(View.GONE);
            }
            if (cameraViews.size() == 1) {
                textureView1.setVisibility(View.VISIBLE);
                textureView3.setVisibility(View.GONE);
                textureView2.setVisibility(View.GONE);
                textureView4.setVisibility(View.GONE);
                part2.setVisibility(View.GONE);

                setupPreviewForCamera(cameraViews.get(0), textureView1, context, enableAutoFocus);
            }
            if (cameraViews.size() == 2) {
                textureView1.setVisibility(View.VISIBLE);
                textureView3.setVisibility(View.VISIBLE);
                textureView2.setVisibility(View.GONE);
                textureView4.setVisibility(View.GONE);
                part2.setVisibility(View.VISIBLE);
                setupPreviewForCamera(cameraViews.get(0), textureView1, context, enableAutoFocus);
                setupPreviewForCamera(cameraViews.get(1), textureView3, context, enableAutoFocus);

            }
            if (cameraViews.size() == 3) {
                textureView1.setVisibility(View.VISIBLE);
                textureView3.setVisibility(View.VISIBLE);
                textureView2.setVisibility(View.VISIBLE);
                textureView4.setVisibility(View.GONE);
                part2.setVisibility(View.VISIBLE);
                setupPreviewForCamera(cameraViews.get(0), textureView1, context, enableAutoFocus);
                setupPreviewForCamera(cameraViews.get(1), textureView3, context, enableAutoFocus);
                setupPreviewForCamera(cameraViews.get(2), textureView2, context, enableAutoFocus);
            }
            if (cameraViews.size() == 4) {
                textureView1.setVisibility(View.VISIBLE);
                textureView3.setVisibility(View.VISIBLE);
                textureView2.setVisibility(View.VISIBLE);
                textureView4.setVisibility(View.VISIBLE);
                part2.setVisibility(View.VISIBLE);
                setupPreviewForCamera(cameraViews.get(0), textureView1, context, enableAutoFocus);
                setupPreviewForCamera(cameraViews.get(1), textureView3, context, enableAutoFocus);
                setupPreviewForCamera(cameraViews.get(2), textureView2, context, enableAutoFocus);
                setupPreviewForCamera(cameraViews.get(3), textureView4, context, enableAutoFocus);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void clearOldCameraDevice() {
        for (Map.Entry<String, CameraDevice> entry : cameraDevices.entrySet()) {
            CameraDevice cameraDevice = entry.getValue();

            cameraDevice.close(); // Close each CameraDevice
            cameraDevice = null;

        }
        if (cameraDevice1 != null) {
            cameraDevice1.close();
        }

        cameraDevices.clear();


        captureSessions.clear();


        textureViews.clear();

    }

    private void setupPreviewForCamera(String cameraId, TextureView textureView, Context context, boolean enableAutoFocus) {


        try {
            cameraManager.openCamera(cameraId, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice cameraDevice) {
                    cameraDevice1 = cameraDevice;
                    cameraDevices.put(cameraId, cameraDevice);
                    textureViews.put(cameraId, textureView);
                    setupCaptureSession(cameraDevice, cameraId, textureView, enableAutoFocus);
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                    cameraDevice.close();
                    cameraDevices.remove(cameraId);
                }

                @Override
                public void onError(@NonNull CameraDevice cameraDevice, int error) {
                    cameraDevice.close();
                    cameraDevices.remove(cameraId);
                }
            }, null);
        } catch (CameraAccessException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private void setupCaptureSession(CameraDevice cameraDevice, String cameraId, TextureView textureView, boolean enableAutoFocus) {
        SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();


        CameraCharacteristics characteristics;
        try {
            characteristics = cameraManager.getCameraCharacteristics(cameraId);
            Size jpegSize = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                    .getOutputSizes(ImageFormat.JPEG)[0];
            Size[] previewSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(SurfaceTexture.class);
            Size optimalSize = chooseOptimalSize(previewSizes, textureView.getWidth(), textureView.getHeight());

            // Set aspect ratio on the TextureView based on optimal preview size
            setAspectRatio(textureView, optimalSize.getWidth(), optimalSize.getHeight());

            surfaceTexture.setDefaultBufferSize(optimalSize.getWidth(), optimalSize.getHeight());
            Surface previewSurface = new Surface(surfaceTexture);
            ImageReader imageReader = ImageReader.newInstance(optimalSize.getWidth(), optimalSize.getHeight(),
                    ImageFormat.JPEG, 1);
            imageReaders.put(cameraId, imageReader);

            imageReader.setOnImageAvailableListener(reader -> {
                Image image = reader.acquireLatestImage();
                if (image != null) {
                    saveImage(cameraId, image, characteristics);
                    image.close();
                }
            }, null);

            List<Surface> surfaces = Arrays.asList(previewSurface, imageReader.getSurface());
            cameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    captureSessions.put(cameraId, session);
                    try {
                        CaptureRequest.Builder previewRequest = cameraDevice1.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                        previewRequest.addTarget(previewSurface);
                        if (enableAutoFocus) {
                            previewRequest.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                        }
                        session.setRepeatingRequest(previewRequest.build(), null, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    System.out.println("Failed to configure session for camera " + cameraId);
                }
            }, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    public void captureAllCameras(boolean isRaw, String deviceName, String model, String apiVersion, ImageSavedCallback callback) {
        this.isRaw = isRaw;
        this.deviceName = deviceName;
        this.model = model;
        this.apiVersion = apiVersion;
        this.callback = callback;
        for (String cameraId : cameraIds) {
            CameraDevice cameraDevice = cameraDevices.get(cameraId);
            CameraCaptureSession session = captureSessions.get(cameraId);
            ImageReader imageReader = imageReaders.get(cameraId);

            if (cameraDevice != null && session != null && imageReader != null) {
                try {
                    CaptureRequest.Builder captureRequest = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                    captureRequest.addTarget(imageReader.getSurface());
                    if (isRaw) {
                        captureRequest.set(CaptureRequest.JPEG_QUALITY, (byte) 100);
                    }
                    session.capture(captureRequest.build(), null, null);

                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveImage(String cameraId, Image image, CameraCharacteristics characteristics) {
        Integer lensFacing = characteristics.get(CameraCharacteristics.LENS_FACING);
        String position = (lensFacing == CameraCharacteristics.LENS_FACING_FRONT) ? "Front" : "Rear";
        Rect activeArraySize = characteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        double megapixels = activeArraySize.width() * activeArraySize.height() / 1_000_000.0;
        megapixels = (double) Math.round(megapixels * 4);
        String mpixel = String.format("%.2f MP", megapixels);

        String format = isRaw ? "RAW" : "JPEG";
        String extension = isRaw ? ".dng" : ".jpg";

        // Date format for the image filename
        String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = deviceName + "_" + model + "_API" + apiVersion + "_" + date + "_Camera_" + cameraId + "_" + position + "_" + mpixel + "_" + format + extension;

        // Define paths for saving the image
        File storageDir = new File(getExternalStorageDir(), "MultiCameraLens/photos/" + (isRaw ? "RAW" : "JPEG"));
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        File file = new File(storageDir, filename);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            fos.write(bytes);
            lastFile.edit().putString("lastFile", file.getAbsolutePath()).commit();
            if (callback != null) {
                callback.onImageSaved();
            }
            ArrayList<HashMap<String, Object>> savedList = new ArrayList<>();
            HashMap<String, Object> newMap = new HashMap<>();
            if (lastFile.getString("savedList", "").equals("")) {

            } else {
                savedList = new Gson().fromJson(lastFile.getString("savedList", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
            }
            newMap = new HashMap<>();
            newMap.put("fileName", filename);
            newMap.put("filePath", file.getAbsolutePath());
            savedList.add(newMap);
            lastFile.edit().putString("savedList", new Gson().toJson(savedList)).commit();
            Toast.makeText(context, "Image saved at: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            System.out.println("Image saved as: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getExternalStorageDir() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
    private void setAspectRatio(TextureView textureView, int width, int height) {
        int viewWidth = textureView.getWidth();
        int viewHeight = textureView.getHeight();

        float aspectRatio = (float) height / width;

        if (viewWidth / viewHeight > aspectRatio) {
            viewWidth = (int) (viewHeight * aspectRatio);
        } else {
            viewHeight = (int) (viewWidth / aspectRatio);
        }

        textureView.getLayoutParams().width = viewWidth;
        textureView.getLayoutParams().height = viewHeight;
        textureView.requestLayout();
    }

    private Size chooseOptimalSize(Size[] choices, int textureViewWidth, int textureViewHeight) {
        List<Size> suitableSizes = new ArrayList<>();
        float targetRatio = (float) textureViewHeight / textureViewWidth;

        for (Size option : choices) {
            float optionRatio = (float) option.getHeight() / option.getWidth();
            if (Math.abs(optionRatio - targetRatio) < 0.1) {
                suitableSizes.add(option);
            }
        }

        if (!suitableSizes.isEmpty()) {
            return Collections.min(suitableSizes, (s1, s2) -> Long.compare(
                    s1.getWidth() * s1.getHeight(),
                    s2.getWidth() * s2.getHeight()));
        } else {
            return choices[0]; // fallback if no match is found
        }
    }


}
