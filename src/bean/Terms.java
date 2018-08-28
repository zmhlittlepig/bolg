package bean;


public class Terms {

  private long termId;
  private String name;
  private String slug;
  private long termGroup;

  public Terms(){
    slug = "";
    termGroup = -1;
  }

  @Override
  public String toString() {
    return "Terms{" +
            "termId=" + termId +
            ", name='" + name + '\'' +
            ", slug='" + slug + '\'' +
            ", termGroup=" + termGroup +
            '}';
  }

  public long getTermId() {
    return termId;
  }

  public void setTermId(long termId) {
    this.termId = termId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }


  public long getTermGroup() {
    return termGroup;
  }

  public void setTermGroup(long termGroup) {
    this.termGroup = termGroup;
  }

}
