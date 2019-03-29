package io.erva.experimental.architecture;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import java.lang.reflect.ParameterizedType;

public abstract class LiveFragment<VIEW_MODEL extends LiveViewModel<SCREEN_STATE>, SCREEN_STATE, VIEW_MODEL_API> extends Fragment {

  private final Observer<SCREEN_STATE> observer = liveData -> {
    if (liveData != null) notifyDataSetChanged(liveData);
  };
  private VIEW_MODEL viewModel;

  @SuppressWarnings("unchecked")
  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Class<VIEW_MODEL> modelClass =
        (Class<VIEW_MODEL>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    viewModel = ViewModelProviders.of(this).get(modelClass);
    viewModel.getLiveData().observe(this, observer);
  }

  @Override
  public void onDestroyView() {
    viewModel.getLiveData().removeObserver(observer);
    super.onDestroyView();
  }

  @NonNull
  protected final SCREEN_STATE getValue() {
    if (viewModel.getLiveData().getValue() == null) {
      throw new IllegalStateException("getValue is null");
    }
    return viewModel.getLiveData().getValue();
  }

  @SuppressWarnings("unchecked")
  protected VIEW_MODEL_API getViewModel() {
    return (VIEW_MODEL_API) viewModel;
  }

  protected abstract void notifyDataSetChanged(@NonNull SCREEN_STATE liveData);
}