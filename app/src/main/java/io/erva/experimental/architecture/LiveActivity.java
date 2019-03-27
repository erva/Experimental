package io.erva.experimental.architecture;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public abstract class LiveActivity<VM extends LiveViewModel<D>, D, I> extends AppCompatActivity {

  private final Observer<D> observer = liveData -> {
    if (liveData != null) notifyDataSetChanged(liveData);
  };
  private VM viewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ViewModel viewModelAnnotation = this.getClass().getAnnotation(ViewModel.class);
    Class<? extends androidx.lifecycle.ViewModel> modelClass = viewModelAnnotation.value();
    viewModel = (VM) ViewModelProviders.of(this).get(modelClass);
    viewModel.getLiveData().observe(this, observer);
  }

  @Override
  protected void onDestroy() {
    viewModel.getLiveData().removeObserver(observer);
    super.onDestroy();
  }

  @NonNull
  protected final D getValue() {
    if (viewModel.getLiveData().getValue() == null) {
      throw new IllegalStateException("getValue is null");
    }
    return viewModel.getLiveData().getValue();
  }

  protected abstract void notifyDataSetChanged(@NonNull D liveData);

  @SuppressWarnings("unchecked")
  protected I getViewModel() {
    return (I) viewModel;
  }
}