package marvel.android.jimmy.marvel3.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import marvel.android.jimmy.marvel3.R;
import marvel.android.jimmy.marvel3.model.pojo.character.Result;

import static marvel.android.jimmy.marvel3.view.activity.HomeActivty.RESULT_KEY;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgCharacter, imgCharacterTwo;
    private TextView titleview, descview;
    private Button btnHq;
    public static final String RESULT_KEY_CHACTER = "character";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_characters);

        initViews();

        if (getIntent() != null && getIntent().getExtras() != null) {
            Result result = getIntent().getExtras().getParcelable(RESULT_KEY);
            setViews(result);
            loadImage(result);

            btnHq.setOnClickListener(v -> {
                Intent intent = new Intent(DetailActivity.this, DetailHQActivty.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(RESULT_KEY_CHACTER, result);
                intent.putExtras(bundle);
                startActivity(intent);
            });
        }



    }

    private void setViews(Result result) {
        titleview.setText(result.getName());
        descview.setText(result.getDescription());
    }

    private void loadImage(Result result) {
        Picasso.get().load(result.getThumbnail().getPath() + ".jpg").into(imgCharacter);
        Picasso.get().load(result.getThumbnail().getPath() + ".jpg").into(imgCharacterTwo);

    }


    public void initViews() {
        titleview = findViewById(R.id.title_character);
        descview = findViewById(R.id.description_character);
        imgCharacter = findViewById(R.id.img_character);
        imgCharacterTwo = findViewById(R.id.img_character_two);
        btnHq = findViewById(R.id.btn_hq);
    }
}
