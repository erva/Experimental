package io.erva.experimental.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import io.erva.celladapter.x.Cell;
import io.erva.celladapter.x.CellAdapter;
import io.erva.experimental.ui.cell.MainCell;
import java.util.List;

public class ContentRecyclerView extends RecyclerView {

  private CellAdapter cellAdapter;
  private Listener listener;

  public ContentRecyclerView(Context context) {
    super(context);
  }

  public ContentRecyclerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public ContentRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void setListener(Listener listener) {
    this.listener = listener;
  }

  public void notifyDataSetChanged(List<MainCell.Model> items) {
    if (cellAdapter == null) {
      cellAdapter = new CellAdapter();
      cellAdapter.registerCell(MainCell.Model.class, MainCell.class,
          new Cell.Listener<MainCell.Model>() {
            @Override public void onCellClicked(MainCell.Model item) {
              if (listener != null) {
                listener.onItemClicked(item);
              }
            }
          });
      setAdapter(cellAdapter);
    }

    // Diff utils here
    cellAdapter.setItems(items);
    cellAdapter.notifyDataSetChanged();
  }

  public interface Listener {

    void onItemClicked(MainCell.Model item);
  }
}