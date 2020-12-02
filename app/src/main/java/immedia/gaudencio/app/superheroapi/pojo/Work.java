package immedia.gaudencio.app.superheroapi.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Work implements Serializable {

    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("base")
    @Expose
    private String base;

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

}