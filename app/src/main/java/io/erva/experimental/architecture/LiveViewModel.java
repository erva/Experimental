package io.erva.experimental.architecture;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import timber.log.Timber;

public abstract class LiveViewModel<SCREEN_STATE> extends androidx.lifecycle.ViewModel {

  protected final SCREEN_STATE screenState;
  private final MutableLiveData<SCREEN_STATE> liveData = new MutableLiveData<>();
  private final CompositeDisposable compositeDisposable = new CompositeDisposable();

  @SuppressWarnings("unchecked")
  protected LiveViewModel() {
    try {
      Class<SCREEN_STATE> viewDataClass =
          (Class<SCREEN_STATE>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
      Constructor constructor = viewDataClass.getConstructor();
      screenState = (SCREEN_STATE) constructor.newInstance();
      liveData.setValue(screenState);
    } catch (Exception e) {
      Timber.e(e);
      throw new IllegalStateException("Can't create screen state class");
    }
  }

  @NonNull
  public MutableLiveData<SCREEN_STATE> getLiveData() {
    return liveData;
  }

  protected void registerSubscription(@NonNull Disposable subscription) {
    compositeDisposable.add(subscription);
  }

  protected void notifyDataSetChanged(SCREEN_STATE value) {
    liveData.setValue(value);
  }

  @Override
  protected void onCleared() {
    compositeDisposable.dispose();
    super.onCleared();
  }
}