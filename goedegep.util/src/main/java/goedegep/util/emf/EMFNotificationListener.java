package goedegep.util.emf;

import org.eclipse.emf.common.notify.Notification;

/**
 * This functional interface indicates that an implementing class can react to EMF Notifications.
 *
 */
@FunctionalInterface
public interface EMFNotificationListener {
  
  public void notifyChanged(Notification notification);
  
}
