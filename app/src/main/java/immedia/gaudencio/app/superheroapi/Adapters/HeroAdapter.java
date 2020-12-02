package immedia.gaudencio.app.superheroapi.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import java.util.List;

import immedia.gaudencio.app.superheroapi.pojo.Result;
import immedia.gaudencio.app.superheroapi.R;

public class HeroAdapter extends ArrayAdapter<Result> {
    Context context;
    int resource;
    List<Result> heroinfo;
    private int lastPosition = -1 ;

    public HeroAdapter(Context context, int resource, List<Result> heroinfo){
        super(context, resource, heroinfo);
        this.heroinfo = heroinfo;
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.
                    inflate(R.layout.listview_heros,null,true);
        }

        ImageView img= convertView.findViewById(R.id.hero_image);
        TextView heroName= convertView.findViewById(R.id.heroName);
        TextView heroBio= convertView.findViewById(R.id.heroBio);
        heroName.setText(heroinfo.get(position).getName());
        heroBio.setText(heroinfo.get(position).getBiography().getPublisher());

        Glide.with(context)
                .load(heroinfo.get(position).getImage().getUrl())
                .placeholder(R.drawable.ic_captain_murica)
                .error(R.drawable.ic_launcher_foreground)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model, Target<Drawable> target,
                                                boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource,
                                                   Object model, Target<Drawable> target,
                                                   DataSource dataSource,
                                                   boolean isFirstResource) {
                        return false;
                    }
                })
                .into(img);

        //Animate listview for no reason other than im stuck for ideas, besides lists are usually
        //to look at.
        Animation animation = AnimationUtils.loadAnimation(getContext(),
                (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        convertView.startAnimation(animation);
        lastPosition = position;
        return convertView;
    }

}
