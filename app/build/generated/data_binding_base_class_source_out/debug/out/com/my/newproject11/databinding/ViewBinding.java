// Generated by view binder compiler. Do not edit!
package com.my.newproject11.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager.widget.ViewPager;
import com.my.newproject11.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ViewBinding implements androidx.viewbinding.ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView imageview1;

  @NonNull
  public final ImageView imageview2;

  @NonNull
  public final ImageView imageview3;

  @NonNull
  public final LinearLayout linear1;

  @NonNull
  public final LinearLayout linear2;

  @NonNull
  public final LinearLayout linear3;

  @NonNull
  public final LinearLayout linear4;

  @NonNull
  public final RelativeLayout linear5;

  @NonNull
  public final TextView textview1;

  @NonNull
  public final ViewPager viewpager1;

  private ViewBinding(@NonNull LinearLayout rootView, @NonNull ImageView imageview1,
      @NonNull ImageView imageview2, @NonNull ImageView imageview3, @NonNull LinearLayout linear1,
      @NonNull LinearLayout linear2, @NonNull LinearLayout linear3, @NonNull LinearLayout linear4,
      @NonNull RelativeLayout linear5, @NonNull TextView textview1, @NonNull ViewPager viewpager1) {
    this.rootView = rootView;
    this.imageview1 = imageview1;
    this.imageview2 = imageview2;
    this.imageview3 = imageview3;
    this.linear1 = linear1;
    this.linear2 = linear2;
    this.linear3 = linear3;
    this.linear4 = linear4;
    this.linear5 = linear5;
    this.textview1 = textview1;
    this.viewpager1 = viewpager1;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ViewBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageview1;
      ImageView imageview1 = ViewBindings.findChildViewById(rootView, id);
      if (imageview1 == null) {
        break missingId;
      }

      id = R.id.imageview2;
      ImageView imageview2 = ViewBindings.findChildViewById(rootView, id);
      if (imageview2 == null) {
        break missingId;
      }

      id = R.id.imageview3;
      ImageView imageview3 = ViewBindings.findChildViewById(rootView, id);
      if (imageview3 == null) {
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

      id = R.id.linear3;
      LinearLayout linear3 = ViewBindings.findChildViewById(rootView, id);
      if (linear3 == null) {
        break missingId;
      }

      id = R.id.linear4;
      LinearLayout linear4 = ViewBindings.findChildViewById(rootView, id);
      if (linear4 == null) {
        break missingId;
      }

      id = R.id.linear5;
      RelativeLayout linear5 = ViewBindings.findChildViewById(rootView, id);
      if (linear5 == null) {
        break missingId;
      }

      id = R.id.textview1;
      TextView textview1 = ViewBindings.findChildViewById(rootView, id);
      if (textview1 == null) {
        break missingId;
      }

      id = R.id.viewpager1;
      ViewPager viewpager1 = ViewBindings.findChildViewById(rootView, id);
      if (viewpager1 == null) {
        break missingId;
      }

      return new ViewBinding((LinearLayout) rootView, imageview1, imageview2, imageview3, linear1,
          linear2, linear3, linear4, linear5, textview1, viewpager1);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
