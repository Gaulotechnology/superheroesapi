package immedia.gaudencio.app.superheroapi.pojo;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "heroes")
public class Result implements Serializable {

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "heroId")
    private String id;
    @NonNull
    @ColumnInfo(name = "heroName")
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("powerstats")
    @Expose
    private Powerstats powerstats;
    @SerializedName("biography")
    @Expose
    private Biography biography;
    @SerializedName("appearance")
    @Expose
    private Appearance appearance;
    @SerializedName("work")
    @Expose
    private Work work;
    @SerializedName("connections")
    @Expose
    private Connections connections;
    @SerializedName("image")
    @Expose
    private Image image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Powerstats getPowerstats() {
        return powerstats;
    }

    public void setPowerstats(Powerstats powerstats) {
        this.powerstats = powerstats;
    }

    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Connections getConnections() {
        return connections;
    }

    public void setConnections(Connections connections) {
        this.connections = connections;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}