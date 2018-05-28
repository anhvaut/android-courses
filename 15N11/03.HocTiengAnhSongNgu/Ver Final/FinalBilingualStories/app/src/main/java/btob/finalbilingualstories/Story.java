package btob.finalbilingualstories;

/**
 * Created by qs270 on 11/22/2017.
 */

public class Story {
    String EngTitle;
    String VieTitle;
    String Image;
    String MP3;
    String EngStory;
    String VieStory;

    public Story(String engTitle, String vieTitle, String image, String MP3, String engStory, String vieStory) {
        EngTitle = engTitle;
        VieTitle = vieTitle;
        Image = image;
        this.MP3 = MP3;
        EngStory = engStory;
        VieStory = vieStory;
    }

    public Story() {
        //
    }

    public Story(String engTitle, String vieTitle, String image) {
        EngTitle = engTitle;
        VieTitle = vieTitle;
        Image = image;
    }

    public String getEngTitle() {
        return EngTitle;
    }

    public void setEngTitle(String engTitle) {
        EngTitle = engTitle;
    }

    public String getVieTitle() {
        return VieTitle;
    }

    public void setVieTitle(String vieTitle) {
        VieTitle = vieTitle;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMP3() {
        return MP3;
    }

    public void setMP3(String MP3) {
        this.MP3 = MP3;
    }

    public String getEngStory() {
        return EngStory;
    }

    public void setEngStory(String engStory) {
        EngStory = engStory;
    }

    public String getVieStory() {
        return VieStory;
    }

    public void setVieStory(String vieStory) {
        VieStory = vieStory;
    }
}
