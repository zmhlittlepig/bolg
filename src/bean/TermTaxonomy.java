package bean;


public class TermTaxonomy {

  private long termTaxonomyId;
  private long termId;
  private String taxonomy;
  private String description;
  private long parent;
  private long count;

  public TermTaxonomy(){
    taxonomy = "";
    description = "";
  }

  public long getTermTaxonomyId() {
    return termTaxonomyId;
  }

  public void setTermTaxonomyId(long termTaxonomyId) {
    this.termTaxonomyId = termTaxonomyId;
  }


  public long getTermId() {
    return termId;
  }

  public void setTermId(long termId) {
    this.termId = termId;
  }


  public String getTaxonomy() {
    return taxonomy;
  }

  public void setTaxonomy(String taxonomy) {
    this.taxonomy = taxonomy;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public long getParent() {
    return parent;
  }

  public void setParent(long parent) {
    this.parent = parent;
  }


  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

}
