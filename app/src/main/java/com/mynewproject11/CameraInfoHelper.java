package com.mynewproject11;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.util.SizeF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CameraInfoHelper {
    private final CameraManager cameraManager;
    private int lensCount = 0; // Variable to store the number of lenses
    private final List<Map<String, Object>> cameraCharacteristicsList = new ArrayList<>();

    public CameraInfoHelper(Context context) {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }

    public void fetchCameraInfo() {
        try {
            for (String cameraId : cameraManager.getCameraIdList()) {
                lensCount++;  // Increment lens count for each camera found
                
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
                Map<String, Object> cameraInfo = new HashMap<>();
                
                // Camera ID
                cameraInfo.put("cameraid", cameraId);
                
                // Get lens position (front/back)
                Integer lensFacing = characteristics.get(CameraCharacteristics.LENS_FACING);
                String lensPosition = (lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_FRONT) ? "Front" : "Back";
                cameraInfo.put("position", lensPosition);

                // Calculate Megapixels
                SizeF sensorSize = characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
                Rect activeArraySize = characteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
                if (sensorSize != null && activeArraySize != null) {
                    int pixelCount = activeArraySize.width() * activeArraySize.height();
                    double megapixels = pixelCount / 1_000_000.0;
					megapixels = (double) Math.round(megapixels * 4);
                    cameraInfo.put("megapixels", String.format("%.2f MP", megapixels));
                }

                // Check for logical multi-camera support (for stereo depth capability)
                boolean isLogicalMultiCamera = false;
                int[] capabilities = characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
                if (capabilities != null) {
                    for (int capability : capabilities) {
                        if (capability == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_LOGICAL_MULTI_CAMERA) {
                            isLogicalMultiCamera = true;
                            break;
                        }
                    }
                }
                cameraInfo.put("stereo", isLogicalMultiCamera ? "yes" : "no");

                // Add this camera's info to the list
                cameraCharacteristicsList.add(cameraInfo);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public int getLensCount() {
        return lensCount;
    }

    public List<Map<String, Object>> getCameraCharacteristicsList() {
        return cameraCharacteristicsList;
    }
}