package io.erva.experimental.ui;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import io.erva.experimental.R;
import io.erva.experimental.architecture.LiveActivity;
import io.erva.experimental.ui.view.ContentRecyclerView;

public class MainActivity
    extends LiveActivity<MainViewModel, MainViewModel.MainData, MainInterface> {

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
  protected void notifyDataSetChanged(@NonNull MainViewModel.MainData liveData) {
    progressView.setVisibility(liveData.progress ? View.VISIBLE : View.GONE);
    recyclerView.notifyDataSetChanged(liveData.items);
  }

  @Override protected void onResume() {
    super.onResume();
    if (getValue().items.isEmpty()) {
      getViewModel().fetchData();
    }
  }
}