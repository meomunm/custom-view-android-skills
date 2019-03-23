package techkids.vn.customviewskills.exceptions;

public class PagesLessException extends Exception{
    @Override
    public String getMessage() {
        return "Pages must equal or large than 2";
    }
}
