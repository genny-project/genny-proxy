package life.genny.abn;

import java.util.List;
import javax.json.bind.annotation.JsonbProperty;

public class AbnSearchResult {

  @JsonbProperty("Message")
  String message;

  public void setMessage(String message) {
    this.message = message;
  }

  public void setNames(List<CompanyInfo> names) {
    this.names = names;
  }

  @JsonbProperty("Names")
  List<CompanyInfo> names;

  public AbnSearchResult() {}

  public String getMessage() {
    return message;
  }

  public List<CompanyInfo> getNames() {
    return names;
  }

  @Override
  public String toString() {
    return "AbnSearchResult [message=" + message + ", names=" + names + "]";
  }
}


