package marvel.android.jimmy.marvel3.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import marvel.android.jimmy.marvel3.R;
import marvel.android.jimmy.marvel3.model.pojo.character.Result;
import marvel.android.jimmy.marvel3.view.interfaces.ClickDetail;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolderCharacter> {

    private List<Result> results;
    private ClickDetail clickDetail;

    public CharactersAdapter(List<Result> listaResults, ClickDetail clickDetail) {
        this.results = listaResults;
        this.clickDetail = clickDetail;
    }

    @NonNull
    @Override
    public CharactersAdapter.ViewHolderCharacter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recycler, parent,false);
        return new ViewHolderCharacter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharactersAdapter.ViewHolderCharacter holder, int position) {
        final Result result = results.get(position);
        holder.bind(result);
        holder.itemView.setOnClickListener(view -> clickDetail.clickTransition(result));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setUpdate(List<Result> results) {
        if (this.results.isEmpty()){
            this.results = results;
        }else {
            this.results.addAll(results);
        }
        notifyDataSetChanged();
    }

    public class ViewHolderCharacter extends RecyclerView.ViewHolder {
        private ImageView imgCharacter;
        private TextView txtNome;

        public ViewHolderCharacter(@NonNull View itemView) {
            super(itemView);
            imgCharacter = itemView.findViewById(R.id.imgCharacter_card);
            txtNome = itemView.findViewById(R.id.text_card);
        }
        public void bind(Result result){
            Picasso.get().load(result.getThumbnail().getPath() + ".jpg").into(imgCharacter);
            txtNome.setText(result.getName());
        }

    }



}
