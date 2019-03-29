package io.erva.experimental.architecture;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import java.lang.reflect.ParameterizedType;

public abstract class LiveActivity<VIEW_MODEL extends LiveViewModel<SCREEN_STATE>, SCREEN_STATE, VIEW_MODEL_API> extends AppCompatActivity {

  private final Observer<SCREEN_STATE> observer = liveData -> {
    if (liveData != null) notifyDataSetChanged(liveData);
  };
  private VIEW_MODEL viewModel;

  @SuppressWarnings("unchecked") @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Class<VIEW_MODEL> modelClass =
        (Class<VIEW_MODEL>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    viewModel = ViewModelProviders.of(this).get(modelClass);
    viewModel.getLiveData().observe(this, observer);
  }

  @Override
  protected void onDestroy() {
    viewModel.getLiveData().removeObserver(observer);
    super.onDestroy();
  }

  @NonNull
  protected final SCREEN_STATE getValue() {
    if (viewModel.getLiveData().getValue() == null) {
      throw new IllegalStateException("getValue is null");
    }
    return viewModel.getLiveData().getValue();
  }

  protected abstract void notifyDataSetChanged(@NonNull SCREEN_STATE liveData);

  @SuppressWarnings("unchecked")
  protected VIEW_MODEL_API getViewModel() {
    return (VIEW_MODEL_API) viewModel;
  }
}