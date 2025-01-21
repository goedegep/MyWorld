package goedegep.util;

public class Result {
  ResultType resultType;
  String message;
  
  public ResultType getResultType() {
    return resultType;
  }
  
  public void setResultType(ResultType resultType) {
    this.resultType = resultType;
  }
  
  public String getMessage() {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  

  public enum ResultType {
    OK,
    FAILED
  }

}

