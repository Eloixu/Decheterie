package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Remi on 30/11/2015.
 */
public class ApkInfos extends ContenantBean {
    private String url;
    private String description;

    public ApkInfos() {
        this.url = "";
        this.description = "";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
