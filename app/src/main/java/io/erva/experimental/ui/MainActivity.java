package io.erva.experimental.ui;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import io.erva.experimental.R;
import io.erva.experimental.architecture.LiveActivity;
import io.erva.experimental.ui.view.ContentRecyclerView;

import static io.erva.experimental.ui.MainScreenState.State.*;

public class MainActivity
    extends LiveActivity<MainViewModel, MainScreenState, MainInterface> {

  private View progressView;
  private ContentRecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    progressView = findViewById(R.id.progressView);
    recyclerView = findViewById(R.id.recyclerView);
    notifyDataSetChanged(getValue());
  }

  @Override
  protected void notifyDataSetChanged(@NonNull MainScreenState screenState) {
    switch (screenState.getState()) {
      case DEFAULT:
        progressView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        break;
      case DONE:
        progressView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.notifyDataSetChanged(screenState.getItems());
        break;
      case PROGRESS:
        progressView.setVisibility(screenState.isProgress() ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(View.GONE);
        break;
    }
  }

  @Override protected void onResume() {
    super.onResume();
    if (getValue().getItems().isEmpty()) {
      getViewModel().fetchData();
    }
  }
}