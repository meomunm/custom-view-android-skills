package techkids.vn.customviewskills.models;

public class ModelListCustom {
    private String title;
    private String description;

    public ModelListCustom() {
        //empty constructor
    }

    public ModelListCustom(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
