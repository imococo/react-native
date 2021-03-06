// Copyright 2004-present Facebook. All Rights Reserved.

package com.facebook.react.views.modal;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import com.facebook.infer.annotation.Assertions;

/**
 * Helper class for Modals.
 */
/*package*/ class ModalHostHelper {

  private static final Point MIN_POINT = new Point();
  private static final Point MAX_POINT = new Point();

  /**
   * To get the size of the screen, we use information from the WindowManager and
   * default Display. We don't use DisplayMetricsHolder, or Display#getSize() because
   * they return values that include the status bar. We only want the values of what
   * will actually be shown on screen.
   * This should only be called on the native modules/shadow nodes thread.
   */
  @TargetApi(16)
  public static Point getModalHostSize(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = Assertions.assertNotNull(wm).getDefaultDisplay();
    // getCurrentSizeRange will return the min and max width and height that the window can be
    display.getCurrentSizeRange(MIN_POINT, MAX_POINT);

    final int rotation = display.getRotation();
    if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
      // If we are vertical the width value comes from min width and height comes from max height
      return new Point(MIN_POINT.x, MAX_POINT.y);
    } else {
      // If we are horizontal the width value comes from max width and height comes from min height
      return new Point(MAX_POINT.x, MIN_POINT.y);
    }
  }
}
