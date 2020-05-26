package marvel.android.jimmy.marvel3.data.remote;

import io.reactivex.Observable;
import marvel.android.jimmy.marvel3.model.pojo.character.CharacterResponse;
import marvel.android.jimmy.marvel3.model.pojo.hq.HqResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TakingData {

    @GET ("characters?")
    Observable<CharacterResponse> getAllCharacter(
            @Query("offset")int offset,
            @Query("orderBy") String orderBy,
            @Query("ts") String ts,
            @Query("hash") String hash,
            @Query("apikey") String apikey

    );

    @GET("characters/{characterId}/comics?")
    Observable<HqResponse> getComicId(
            @Path("characterId") Long id,
            @Query("offset")int offset,
            @Query("ts") String ts,
            @Query("hash") String hash,
            @Query("apikey") String apikey
    );
}
