// Generated by view binder compiler. Do not edit!
package com.my.newproject11.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.my.newproject11.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ImagesBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView imageview1;

  @NonNull
  public final LinearLayout linear1;

  @NonNull
  public final LinearLayout linear2;

  @NonNull
  public final RecyclerView recyclerview1;

  @NonNull
  public final TextView textview1;

  @NonNull
  public final NestedScrollView vscroll1;

  private ImagesBinding(@NonNull LinearLayout rootView, @NonNull ImageView imageview1,
      @NonNull LinearLayout linear1, @NonNull LinearLayout linear2,
      @NonNull RecyclerView recyclerview1, @NonNull TextView textview1,
      @NonNull NestedScrollView vscroll1) {
    this.rootView = rootView;
    this.imageview1 = imageview1;
    this.linear1 = linear1;
    this.linear2 = linear2;
    this.recyclerview1 = recyclerview1;
    this.textview1 = textview1;
    this.vscroll1 = vscroll1;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ImagesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ImagesBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.images, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ImagesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageview1;
      ImageView imageview1 = ViewBindings.findChildViewById(rootView, id);
      if (imageview1 == null) {
        break missingId;
      }

      id = R.id.linear1;
      LinearLayout linear1 = ViewBindings.findChildViewById(rootView, id);
      if (linear1 == null) {
        break missingId;
      }

      id = R.id.linear2;
      LinearLayout linear2 = ViewBindings.findChildViewById(rootView, id);
      if (linear2 == null) {
        break missingId;
      }

      id = R.id.recyclerview1;
      RecyclerView recyclerview1 = ViewBindings.findChildViewById(rootView, id);
      if (recyclerview1 == null) {
        break missingId;
      }

      id = R.id.textview1;
      TextView textview1 = ViewBindings.findChildViewById(rootView, id);
      if (textview1 == null) {
        break missingId;
      }

      id = R.id.vscroll1;
      NestedScrollView vscroll1 = ViewBindings.findChildViewById(rootView, id);
      if (vscroll1 == null) {
        break missingId;
      }

      return new ImagesBinding((LinearLayout) rootView, imageview1, linear1, linear2, recyclerview1,
          textview1, vscroll1);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
