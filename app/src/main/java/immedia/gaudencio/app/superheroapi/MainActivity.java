package immedia.gaudencio.app.superheroapi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import immedia.gaudencio.app.superheroapi.Adapters.HeroAdapter;
import immedia.gaudencio.app.superheroapi.Interfaces.ApiInterface;
import immedia.gaudencio.app.superheroapi.Networking.ApiClient;
import immedia.gaudencio.app.superheroapi.pojo.HeroInfo;
import immedia.gaudencio.app.superheroapi.pojo.Result;
import immedia.gaudencio.app.superheroapi.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    String TAG = "MAIN ACTIVITY";
    Retrofit retrofit;
    List<Result> heroData;
    SearchView searchView;
    ListView listView;
    HeroAdapter heroAdapter;
    LinearLayout progressBar;
    private Context cxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.heroList);
        progressBar = findViewById(R.id.llProgressBar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!getNetworkStatus(getApplicationContext())){
                    Toast.makeText(getApplicationContext(),
                            "Check Network Connection!", Toast.LENGTH_LONG).show();
                }else{
                    searchHero(s);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if(heroData != null){
                    heroData.clear();
                    heroAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

    }


    /**
     * Search name of hero
     * @param searchString name of hero**/
    public void searchHero(String searchString){

        progressBar.setVisibility(View.VISIBLE);
        retrofit = ApiClient.getInstance();
        ApiInterface client =  retrofit.create(ApiInterface.class);

        Call<HeroInfo> call = client.searchHero(searchString.trim()
                , getApplicationContext().getString(R.string.api_superheroes_token));

        call.enqueue(new Callback<HeroInfo>() {
            @Override
            public void onResponse(Call<HeroInfo> call, Response<HeroInfo> response) {

                Log.i(TAG, response.body().getResponse());

                if(response.isSuccessful() && !response.body()
                        .getResponse().equalsIgnoreCase("error")){

                    Log.i(TAG, "good\n"+response.body().getResults());
                    heroData = response.body().getResults();
                    int resultSize = heroData.size();

                    for(int i=0; i<heroData.size();i++){

                        Log.i(TAG, "Search results: "
                                +String.valueOf(heroData.size())+"\tName: "
                                +heroData.get(i).getName()
                                +"\tFull Names: "+heroData.get(i).getBiography().getFullName()
                                +heroData.get(i).getId()+"\t"+heroData.get(i).getName()
                                +"\tPower: "+heroData.get(i).getPowerstats().getIntelligence()
                                +"\tImage URL: "+heroData.get(i).getImage().getUrl());

                        heroAdapter = new HeroAdapter(getApplicationContext()
                                , R.layout.listview_heros, heroData);
                        listView.setAdapter(heroAdapter);
                        progressBar.setVisibility(View.INVISIBLE);
                        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView,
                                                    View view, int i, long l) {
                                //User clicks list item
                                openHero(i, heroData);
                            }
                        });
                    }

                    response.body().setResults(response.body().getResults());
                }else{
                    Toast.makeText(getApplicationContext(),
                            response.body().getError(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<HeroInfo> call, Throwable t) {

                Log.i(TAG, "bad");
                Toast.makeText(getApplicationContext(),
                        "Failed to retrieve data!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
    }

    /**
     * when user selects a hero from the list**/
    public void openHero(int position, List<Result> data){

        Intent intent = new Intent(getApplicationContext(), HeroCardActivity.class);
        intent.putExtra("LIST", (Serializable) data);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    /**
     * Check if device has network connection
     * @param cxt Application context
     * @return true/false
     */
    public boolean getNetworkStatus(Context cxt){
        this.cxt = cxt;

        ConnectivityManager cm = (ConnectivityManager)cxt
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        Log.i(TAG, "getNetworkStatus: "+connected);

        return connected;
    }

}