package life.genny.abn;

import javax.json.bind.annotation.JsonbProperty;

public class CompanyInfo {
  @JsonbProperty("Abn")
  String abn;
  @JsonbProperty("AbnStatus")
  String abnStatus;
  @JsonbProperty("IsCurrent")
  boolean isCurrent;
  @JsonbProperty("Name")
  String name;
  @JsonbProperty("NameType")
  String nameType;
  @JsonbProperty("Postcode")
  String postcode;
  @JsonbProperty("Score")
  String score;
  @JsonbProperty("State")
  String state;

  public void setAbn(String abn) {
    this.abn = abn;
  }

  public void setAbnStatus(String abnStatus) {
    this.abnStatus = abnStatus;
  }

  public void setCurrent(boolean isCurrent) {
    this.isCurrent = isCurrent;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setNameType(String nameType) {
    this.nameType = nameType;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public void setState(String state) {
    this.state = state;
  }

  public CompanyInfo() {}

  public String getAbn() {
    return abn;
  }

  public String getAbnStatus() {
    return abnStatus;
  }

  public boolean isCurrent() {
    return isCurrent;
  }

  public String getName() {
    return name;
  }

  public String getNameType() {
    return nameType;
  }

  public String getPostcode() {
    return postcode;
  }

  public String getScore() {
    return score;
  }

  public String getState() {
    return state;
  }


}
