package io.erva.experimental.ui.cell;

import android.view.View;
import android.widget.TextView;
import io.erva.celladapter.Layout;
import io.erva.celladapter.x.Cell;

@Layout(android.R.layout.simple_list_item_1)
public class MainCell extends Cell<MainCell.Model, Cell.Listener<MainCell.Model>> {

  TextView textView;

  public MainCell(View view) {
    super(view);
    textView = view.findViewById(android.R.id.text1);
  }

  @Override
  protected void bindView() {
    textView.setText(getItem().getData());
  }

  public static class Model {

    private String data;

    public Model(String data) {
      this.data = data;
    }

    String getData() {
      return data;
    }
  }
}