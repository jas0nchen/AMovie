package cn.jas0n.amovie.util;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: Jas0n
 * Date: 2016/6/28
 * E-mail:chendong90x@gmail.com
 */
public class ViewGroupUtils {

    private interface ViewGroupUtilsImpl {
        void offsetDescendantRect(ViewGroup parent, View child, Rect rect);
    }

    private static class ViewGroupUtilsImplBase implements ViewGroupUtilsImpl {
        @Override
        public void offsetDescendantRect(ViewGroup parent, View child, Rect rect) {
            parent.offsetDescendantRectToMyCoords(child, rect);
            // View#offsetDescendantRectToMyCoords includes scroll offsets of the last child.
            // We need to reverse it here so that we get the rect of the view itself rather
            // than its content.
            rect.offset(child.getScrollX(), child.getScrollY());
        }
    }

    private static final ViewGroupUtilsImpl IMPL;

    static {
        final int version = Build.VERSION.SDK_INT;
        IMPL = new ViewGroupUtilsImplBase();
    }

    /**
     * This is a port of the common
     * {@link ViewGroup#offsetDescendantRectToMyCoords(android.view.View, android.graphics.Rect)}
     * from the framework, but adapted to take transformations into account. The result
     * will be the bounding rect of the real transformed rect.
     *
     * @param descendant view defining the original coordinate system of rect
     * @param rect       (in/out) the rect to offset from descendant to this view's coordinate system
     */
    static void offsetDescendantRect(ViewGroup parent, View descendant, Rect rect) {
        IMPL.offsetDescendantRect(parent, descendant, rect);
    }

    /**
     * Retrieve the transformed bounding rect of an arbitrary descendant view.
     * This does not need to be a direct child.
     *
     * @param descendant descendant view to reference
     * @param out        rect to set to the bounds of the descendant view
     */
    public static void getDescendantRect(ViewGroup parent, View descendant, Rect out) {
        out.set(0, 0, descendant.getWidth(), descendant.getHeight());
        offsetDescendantRect(parent, descendant, out);
    }

}
