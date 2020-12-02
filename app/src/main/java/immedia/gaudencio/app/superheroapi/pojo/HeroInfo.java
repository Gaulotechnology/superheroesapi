
package immedia.gaudencio.app.superheroapi.pojo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeroInfo implements Serializable {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("results-for")
    @Expose
    private String resultsFor;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("error")
    @Expose
    private String error;

    public HeroInfo(String response, String resultsFor, List<Result> results){
        this.response = response;
        this.resultsFor = resultsFor;
        this.results = results;

    }

    public String getError(){
        return error;
    }

    public String getResponse(){
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResultsFor() {
        return resultsFor;
    }

    public void setResultsFor(String resultsFor) {
        this.resultsFor = resultsFor;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}