package io.erva.experimental.architecture;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import java.lang.reflect.ParameterizedType;

public abstract class LiveFragment<VM extends LiveViewModel<D>, D, I> extends Fragment {

  private final Observer<D> observer = liveData -> {
    if (liveData != null) notifyDataSetChanged(liveData);
  };
  private VM viewModel;

  @SuppressWarnings("unchecked")
  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Class<VM> modelClass =
        (Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    viewModel = ViewModelProviders.of(this).get(modelClass);
    viewModel.getLiveData().observe(this, observer);
  }

  @Override
  public void onDestroyView() {
    viewModel.getLiveData().removeObserver(observer);
    super.onDestroyView();
  }

  @NonNull
  protected final D getValue() {
    if (viewModel.getLiveData().getValue() == null) {
      throw new IllegalStateException("getValue is null");
    }
    return viewModel.getLiveData().getValue();
  }

  @SuppressWarnings("unchecked")
  protected I getViewModel() {
    return (I) viewModel;
  }

  protected abstract void notifyDataSetChanged(@NonNull D liveData);
}