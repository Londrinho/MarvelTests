package marvel.android.jimmy.marvel3.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import marvel.android.jimmy.marvel3.model.pojo.character.Result;
import marvel.android.jimmy.marvel3.repository.RepositoryApi;

import static marvel.android.jimmy.marvel3.useful.AppUseful.md5;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Result>> resultMutableLiveData = new MutableLiveData<>();
    private RepositoryApi respositoryApi = new RepositoryApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<String> liveDataError = new MutableLiveData<>();
    public static final String PUBLIC_KEY = "fe81c0a4bd6c7f00e3df25d68d8d8a92";
    public static final String PRIVATE_KEY = "ceac13aef2089eaf3c704ba9da60cf2156b60912";
    String ts = Long.toString(System.currentTimeMillis() / 1000);
    String hash = md5(ts + PRIVATE_KEY + PUBLIC_KEY);

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<Result>> getMutable(){
        return this.resultMutableLiveData;
    }
    public LiveData<Boolean> getLoading(){
        return this.loading;
    }
    public LiveData<String> getError(){
        return this.liveDataError;
    }

    public void getCharacterApi(int pagina){

        disposable.add(
                respositoryApi.getCharacterResponse(pagina, "name", ts, hash, PUBLIC_KEY)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> loading.setValue(true))
                        .doOnTerminate(() -> loading.setValue(false))
                        .subscribe(characterResponse -> resultMutableLiveData.setValue(characterResponse.getData().getResults())
                                ,throwable -> Log.i("LOG", "ERROR: " + throwable.getMessage()))
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
