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
    extends LiveViewModel<MainViewModel.MainData>
    implements MainInterface {

  @SuppressLint("DefaultLocale")
  @Override
  public void fetchData() {
    data.progress = true;
    notifyDataSetChanged(data);

    registerSubscription(
        Single.timer(2500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(success -> {
              data.progress = false;
              data.items.clear();
              for (int i = 0; i <= 15; i++) {
                data.items.add(new MainCell.Model(String.format("Item #%d", i)));
              }
              notifyDataSetChanged(data);
            }, Timber::e)
    );
  }

  public static class MainData {

    final List<MainCell.Model> items = new ArrayList<>();
    boolean progress = false;

    public MainData() {
    }
  }
}
