package io.erva.experimental.architecture;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import timber.log.Timber;

public abstract class LiveViewModel<T> extends androidx.lifecycle.ViewModel {

  protected final T data;
  private final MutableLiveData<T> liveData = new MutableLiveData<>();
  private final CompositeDisposable compositeDisposable = new CompositeDisposable();

  @SuppressWarnings("unchecked")
  protected LiveViewModel() {
    try {
      Class<T> viewDataClass =
          (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
      Constructor constructor = viewDataClass.getConstructor();
      data = (T) constructor.newInstance();
      liveData.setValue(data);
    } catch (Exception e) {
      Timber.e(e);
      throw new IllegalStateException("Can't create data class");
    }
  }

  @NonNull
  public MutableLiveData<T> getLiveData() {
    return liveData;
  }

  protected void registerSubscription(@NonNull Disposable subscription) {
    compositeDisposable.add(subscription);
  }

  protected void notifyDataSetChanged(T value) {
    liveData.setValue(value);
  }

  @Override
  protected void onCleared() {
    compositeDisposable.dispose();
    super.onCleared();
  }
}