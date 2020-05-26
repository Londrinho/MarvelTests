package marvel.android.jimmy.marvel3.repository;

import io.reactivex.Observable;
import marvel.android.jimmy.marvel3.model.pojo.character.CharacterResponse;
import marvel.android.jimmy.marvel3.model.pojo.hq.HqResponse;

import static marvel.android.jimmy.marvel3.data.remote.Retrofit.getApiService;

public class RepositoryApi {

    public Observable<CharacterResponse> getCharacterResponse (int offset,
                                                               String orderBy,
                                                               String ts,
                                                               String hash,
                                                               String apikey){

        return getApiService().getAllCharacter(offset, orderBy, ts, hash, apikey);
    }

    public Observable<HqResponse> getHQResponse (int offset,
                                                 String ts,
                                                 String hash,
                                                 String apikey, Long characterId){

        return getApiService().getComicId(characterId, offset, ts, hash, apikey);
    }

}
