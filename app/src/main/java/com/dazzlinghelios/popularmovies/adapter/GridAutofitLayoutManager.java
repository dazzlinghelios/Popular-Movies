package com.dazzlinghelios.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

/**
 * Created by Merlin on 11/12/16.
 *
 * Customized gridview-autofit layout manager which auto set the number of column
 * the gridview will display in recyclerview
 */
public class GridAutofitLayoutManager extends GridLayoutManager{
    private int columnWidth;
    private boolean columnWidthChanged = true;
    private static final String TAG = GridAutofitLayoutManager.class.getSimpleName();

    public GridAutofitLayoutManager(Context context, int columnWidth) {
        super(context, 2);  // default is 2, will be changed automatically later
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    public GridAutofitLayoutManager(Context context, int columnWidth, int orientation,
                                    boolean reverseLayout) {
        super(context, 2, orientation, reverseLayout);
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    private int checkedColumnWidth(Context context, int columnWidth) {
        if (columnWidth <= 0) {
            columnWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48,
                    context.getResources().getDisplayMetrics());
        }
        return columnWidth;
    }

    private void setColumnWidth(int newColumnWidth) {
        if (newColumnWidth > 0 && newColumnWidth != columnWidth) {
            columnWidth = newColumnWidth;
            columnWidthChanged = true;
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (columnWidthChanged && columnWidth > 0) {
            int totalSpace;
            if (getOrientation() == VERTICAL) {
                totalSpace = getWidth() - getPaddingRight() - getPaddingLeft();
            } else {
                totalSpace = getWidth() - getPaddingBottom() - getPaddingTop();
            }
            int spanCount = Math.max(1, totalSpace / columnWidth);
            setSpanCount(spanCount);  // set number of span
            columnWidthChanged = false;
        }
        super.onLayoutChildren(recycler, state);
    }
}
