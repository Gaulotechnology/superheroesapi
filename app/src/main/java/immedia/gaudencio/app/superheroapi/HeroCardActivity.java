package immedia.gaudencio.app.superheroapi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import immedia.gaudencio.app.superheroapi.Interfaces.ApiInterface;
import immedia.gaudencio.app.superheroapi.Networking.ApiClient;
import immedia.gaudencio.app.superheroapi.pojo.Result;
import immedia.gaudencio.app.superheroapi.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Robert Matsaneng
 * This activity will handle displaying the heros details once user slects them from the list**/
public class HeroCardActivity extends AppCompatActivity {

    String TAG = "HeroCardActivity";
    List<Result> list;
    int position;
    ImageView heroImage;
    TextView heroName, heroPower, heroSpeed, heroStrength, heroPublisher;
    TextView heroFullName, heroFirstAppearance, heroRelatives, heroAffiliations;
    Retrofit retrofit;
    Button btnDownloadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_card);

        heroImage = findViewById(R.id.cardHeroImage);
        heroName = findViewById(R.id.cardHeroName);
        heroPower = findViewById(R.id.cardHeroPower);
        heroSpeed = findViewById(R.id.cardHeroSpeed);
        heroStrength = findViewById(R.id.cardHeroStrength);
        heroPublisher = findViewById(R.id.cardHeroPublisher);
        heroFullName = findViewById(R.id.cardHeroFullName);
        heroFirstAppearance = findViewById(R.id.cardHeroFirstAppearance);
        heroRelatives = findViewById(R.id.cardHeroRelatives);
        heroAffiliations = findViewById(R.id.cardHeroAffiliations);
        btnDownloadImage = findViewById(R.id.btnDownloadPoster);


        if(getIntent().getExtras() != null) {
            list  = (List<Result>) getIntent().getSerializableExtra("LIST");
            position = getIntent().getIntExtra("position",0);
            Log.i(TAG, "List size: "+String.valueOf(list.size())
                    +"\nName: "+list.get(position).getName());

            //Set values
            Glide.with(getApplicationContext())
                    .load(list.get(position).getImage().getUrl()).into(heroImage);
            heroName.setText(list.get(position).getName());
            heroPublisher.setText(list.get(position).getBiography().getPublisher());
            heroSpeed.setText(list.get(position).getPowerstats().getSpeed());
            heroStrength.setText(list.get(position).getPowerstats().getStrength());
            heroPower.setText(list.get(position).getPowerstats().getPower());
            heroFullName.setText(list.get(position).getBiography().getFullName());
            heroFirstAppearance.setText(list.get(position).getBiography().getFirstAppearance());
            heroRelatives.setText(list.get(position).getConnections().getRelatives());
            heroAffiliations.setText(list.get(position).getConnections().getGroupAffiliation());

            btnDownloadImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "downloading image..",
                            Toast.LENGTH_SHORT);
                    toast.show();

                    downloadImage(list.get(position).getImage().getUrl(),list.get(position).getName());
                    Log.i(TAG, "Trying to download URL: "+list.get(position).getImage().getUrl()+"\tName: "+list.get(position).getName());
                }
            });

        }
    }

    public void downloadImage(String imageURL, final String name){
        retrofit = ApiClient.getInstance();
        ApiInterface client =  retrofit.create(ApiInterface.class);
        Call<ResponseBody> call = client.downloadFileWithDynamicUrl(imageURL);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                boolean writtenToDisk = writeResponseBodyToDisk(response.body(), name);

                Log.i("File download success? ", String.valueOf(writtenToDisk));

                if(writtenToDisk){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Saved Successfully",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Failed to save",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String filename) {


        String fullPath;
        fullPath = getExternalFilesDir(null) + File.separator;
        Log.i(TAG, "Directory: "+fullPath);
        try {
            File path= new File(getApplicationContext().getFilesDir(), "MyAppName" + File.separator + "Images");
            if(!path.exists()){
                path.mkdirs();
            }
            File heroImageFile = new File(fullPath, filename+".jpeg");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(heroImageFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.i("File Download: " , fileSizeDownloaded + " of " + fileSize);
                    Log.i("File Download: " , heroImageFile.getAbsolutePath());

                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}