package marvel.android.jimmy.marvel3.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import marvel.android.jimmy.marvel3.R;
import marvel.android.jimmy.marvel3.model.pojo.character.Result;
import marvel.android.jimmy.marvel3.model.pojo.hq.ResultHQ;
import marvel.android.jimmy.marvel3.view.adapter.HQAdapter;
import marvel.android.jimmy.marvel3.viewmodel.HqDetailViewModel;

import static marvel.android.jimmy.marvel3.view.activity.DetailActivity.RESULT_KEY_CHACTER;

public class DetailHQActivty extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtTitle;
    private ImageView imageCharacter;
    private HQAdapter hqAdapter;
    private List<ResultHQ> results = new ArrayList<>();
    private HqDetailViewModel hqDetailViewModel;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hq);

        initViewHQ();
        // setLayoutRecyclerHQ();
        //  receivesDataCharacter();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(hqAdapter);
        if (getIntent() != null && getIntent().getExtras() != null) {
            Result result = getIntent().getExtras().getParcelable(RESULT_KEY_CHACTER);
            if (result != null) {
                txtTitle.setText(result.getName());
                Picasso.get().load(result.getThumbnail().getPath() + ".jpg").into(imageCharacter);
                loadApiHQ(result.getId());
            }
        }
    }

    public void receivesDataCharacter(){


    }

    public void initViewHQ(){
        txtTitle = findViewById(R.id.title_character_hq);
        imageCharacter = findViewById(R.id.img_character_hq);
        recyclerView = findViewById(R.id.recycler_view_hq);
        hqAdapter = new HQAdapter(results);
        progressBar = findViewById(R.id.progress_bar_hq);
        hqDetailViewModel = ViewModelProviders.of(this).get(HqDetailViewModel.class);
    }

    public void setLayoutRecyclerHQ(){

    }

    public void loadApiHQ(Long id){
        hqDetailViewModel.getHqApi(id);

        hqDetailViewModel.getMutable().observe(this, results -> {
            hqAdapter.setUpdate(results);
        });

        hqDetailViewModel.getLoading().observe(this, loading -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        hqDetailViewModel.getError().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        });
    }
}
