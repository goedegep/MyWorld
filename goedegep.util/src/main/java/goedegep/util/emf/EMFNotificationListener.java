package goedegep.util.emf;

import org.eclipse.emf.common.notify.Notification;

public interface EMFNotificationListener {
  
  public void notifyChanged(Notification notification);
  
}
