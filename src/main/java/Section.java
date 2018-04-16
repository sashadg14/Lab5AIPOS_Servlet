public class Section {
    private String section;
    private String info;

    public Section(String section, String info) {
        this.section = section;
        this.info = info;
    }

    public Section(){}

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
