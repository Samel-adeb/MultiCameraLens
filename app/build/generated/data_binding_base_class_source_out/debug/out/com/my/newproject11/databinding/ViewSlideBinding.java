// Generated by view binder compiler. Do not edit!
package com.my.newproject11.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.my.newproject11.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ViewSlideBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final RelativeLayout capturedImage;

  @NonNull
  public final CardView cardview1;

  @NonNull
  public final ImageView image3;

  private ViewSlideBinding(@NonNull LinearLayout rootView, @NonNull RelativeLayout capturedImage,
      @NonNull CardView cardview1, @NonNull ImageView image3) {
    this.rootView = rootView;
    this.capturedImage = capturedImage;
    this.cardview1 = cardview1;
    this.image3 = image3;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ViewSlideBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ViewSlideBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.view_slide, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ViewSlideBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.capturedImage;
      RelativeLayout capturedImage = ViewBindings.findChildViewById(rootView, id);
      if (capturedImage == null) {
        break missingId;
      }

      id = R.id.cardview1;
      CardView cardview1 = ViewBindings.findChildViewById(rootView, id);
      if (cardview1 == null) {
        break missingId;
      }

      id = R.id.image3;
      ImageView image3 = ViewBindings.findChildViewById(rootView, id);
      if (image3 == null) {
        break missingId;
      }

      return new ViewSlideBinding((LinearLayout) rootView, capturedImage, cardview1, image3);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
