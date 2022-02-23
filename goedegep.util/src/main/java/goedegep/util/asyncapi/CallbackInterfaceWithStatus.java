package goedegep.util.asyncapi;

public interface CallbackInterfaceWithStatus<T> {
  public void message(String message);
  
  public void intermediateResult(T intermediateResult);
  
  public void result(T result);
}
