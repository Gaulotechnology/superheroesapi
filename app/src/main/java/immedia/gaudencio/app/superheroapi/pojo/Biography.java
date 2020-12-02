package immedia.gaudencio.app.superheroapi.pojo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Biography implements Serializable {

    @SerializedName("full-name")
    @Expose
    private String fullName;
    @SerializedName("alter-egos")
    @Expose
    private String alterEgos;
    @SerializedName("aliases")
    @Expose
    private List<String> aliases = null;
    @SerializedName("place-of-birth")
    @Expose
    private String placeOfBirth;
    @SerializedName("first-appearance")
    @Expose
    private String firstAppearance;
    @SerializedName("publisher")
    @Expose
    private String publisher;
    @SerializedName("alignment")
    @Expose
    private String alignment;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAlterEgos() {
        return alterEgos;
    }

    public void setAlterEgos(String alterEgos) {
        this.alterEgos = alterEgos;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getFirstAppearance() {
        return firstAppearance;
    }

    public void setFirstAppearance(String firstAppearance) {
        this.firstAppearance = firstAppearance;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

}