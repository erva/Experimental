package io.erva.experimental.ui;

import androidx.annotation.IntDef;
import io.erva.experimental.ui.cell.MainCell;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public final class MainScreenState {

  @IntDef({ State.DEFAULT, State.PROGRESS, State.DONE})
  @Retention(RetentionPolicy.SOURCE)
  @interface State {
    int DEFAULT = 0;
    int PROGRESS = 1;
    int DONE = 2;
  }

  @State private int state = State.DEFAULT;
  private final List<MainCell.Model> items = new ArrayList<>();
  private boolean progress = false;

  public int getState() {
    return state;
  }

  public List<MainCell.Model> getItems() {
    return items;
  }

  public boolean isProgress() {
    return progress;
  }

  public MainScreenState() {
  }

  public void progress(){
    state = State.PROGRESS;
    progress = true;
    items.clear();
  }

  public void done(List<MainCell.Model> items){
    state = State.DONE;
    progress = false;
    this.items.clear();
    this.items.addAll(items);
  }
}