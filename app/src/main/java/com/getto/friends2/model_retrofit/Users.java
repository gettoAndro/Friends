package com.getto.friends2.model_retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Getto on 24.06.2017.
 */

public class Users implements Serializable {

        @SerializedName("results")
        @Expose
        private List<Result> results = new ArrayList<>();


        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }


}
