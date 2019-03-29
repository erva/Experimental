package io.erva.experimental.ui;

import android.annotation.SuppressLint;
import io.erva.experimental.architecture.LiveViewModel;
import io.erva.experimental.ui.cell.MainCell;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class MainViewModel
    extends LiveViewModel<MainViewModel.ScreenState>
    implements MainInterface {

  @SuppressLint("DefaultLocale")
  @Override
  public void fetchData() {
    screenState.progress = true;
    notifyDataSetChanged(screenState);

    registerSubscription(
        Single.timer(2500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(success -> {
              screenState.progress = false;
              screenState.items.clear();
              for (int i = 0; i <= 15; i++) {
                screenState.items.add(new MainCell.Model(String.format("Item #%d", i)));
              }
              notifyDataSetChanged(screenState);
            }, Timber::e)
    );
  }

  public static class ScreenState {

    final List<MainCell.Model> items = new ArrayList<>();
    boolean progress = false;

    public ScreenState() {
    }
  }
}