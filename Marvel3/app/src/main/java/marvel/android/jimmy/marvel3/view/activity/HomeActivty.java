package marvel.android.jimmy.marvel3.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import marvel.android.jimmy.marvel3.R;
import marvel.android.jimmy.marvel3.model.pojo.character.Result;
import marvel.android.jimmy.marvel3.view.adapter.CharactersAdapter;
import marvel.android.jimmy.marvel3.view.interfaces.ClickDetail;
import marvel.android.jimmy.marvel3.viewmodel.HomeViewModel;

public class HomeActivty extends AppCompatActivity implements ClickDetail {

    private CharactersAdapter adapter;
    private RecyclerView recyclerView;
    private HomeViewModel homeViewModel;
    private ProgressBar progressBar;
    private List<Result> results = new ArrayList<>();
    public static final String RESULT_KEY = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViewHome();
        setLayoutRecyclerHome();
        loadApiHome();
    }
    public void setLayoutRecyclerHome(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    public void loadApiHome(){
        homeViewModel.getCharacterApi(1);

        homeViewModel.getMutable().observe(this, results -> {
            adapter.setUpdate(results);
        });

        homeViewModel.getLoading().observe(this, (Boolean loading) -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        homeViewModel.getError().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        });
    }

    public void initViewHome(){
        adapter = new CharactersAdapter(results, this);
        recyclerView = findViewById(R.id.recycler_view_home);
        progressBar = findViewById(R.id.progress_bar);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    @Override
    public void clickTransition(Result result) {
        Intent intent = new Intent(HomeActivty.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RESULT_KEY, result);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
