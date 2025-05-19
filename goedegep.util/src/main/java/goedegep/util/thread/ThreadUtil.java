package goedegep.util.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Objects;

/**
 * This code and documentation is copied from the article 'Java tip: How to list and find threads and thread groups' by David Robert Nadeau, Ph.D. on the website https://nadeausoftware.com/.<br/>
 * It is actually obtained from 'https://web.archive.org/web/20191105154937/http://nadeausoftware.com/articles/2008/04/java_tip_how_list_and_find_threads_and_thread_groups'.
 * 
 * Java's threads are essential for building complex applications, but thread control is split across several classes in different packages added at different times in the JDK's history.
 * This class shows how to connect these classes together to find threads and thread groups, and get thread information.
 * 
 * <h4>Introduction</h4>
 * The principal threading classes are Thread and ThreadGroup in java.lang.
 * Java 5 added the ThreadInfo and ThreadMXBean classes in java.lang.management to get state information and CPU usage for threads.
 * Java 6 expanded these to get information on locks held by threads.
 * Java 5 also added java.util.concurrent classes for managing thread pools and creating special types of locks.
 * There is a lot of functionality here, but there are some gaps. For example, though thread groups are organized as a tree of groups within groups, there is no method to get the tree's root ThreadGroup.
 * Though threads have names and IDs, there are no methods to search for them by name or ID.
 * Methods on ThreadInfo give you important state and locking information about a thread, but there is no method to get a ThreadInfo for a Thread, or to get the Thread described by a ThreadInfo. And so on.
 * These functionality gaps are not fatal, just mildly annoying. This class provides utility methods to help list and find threads, thread groups, and thread info.
 * These utility methods build upon each other.
 * 
 * <h4>Getting Threads</h4>
 * Every Thread has a name and a unique long integer ID. The JDK's own threads have names like "Finalizer" and "Reference Handler".
 * When applications don't name their threads, threads are automatically named "Thread-0", "Thread-1", and so on.
 * There's no method to get a Thread based upon its ID or name. And getting a list of all Thread objects has the same difficulties as getting a list of all ThreadGroup objects did above.
 */
public class ThreadUtil {
  
  /**
   * Private constructor as this is a utility class.
   */
  private ThreadUtil() {
    
  }
  
  /**
   * Since the same root thread group is used for the life of the JVM, it is safe to cache it.
   */
  private static ThreadGroup rootThreadGroup = null;
  /**
   * Get the root thread group.
   * <p>
   * To get the root thread group, first get the current thread and its thread group.
   * Then get its parent group, then its parent group, and on up until you find a group with a null parent.
   * That's the root ThreadGroup.
   * 
   * @return the root {@code ThreadGroup}.
   */
  public static ThreadGroup getRootThreadGroup( ) {
    if (rootThreadGroup == null) {
      rootThreadGroup = Thread.currentThread( ).getThreadGroup( );
      ThreadGroup parentThreadGroup;
      while ( (parentThreadGroup = rootThreadGroup.getParent( )) != null ) {
        rootThreadGroup = parentThreadGroup;
      }
    }
    
    return rootThreadGroup;
  }
  
  /**
   * Get a list of all thread groups.
   * <p>
   * The enumerate( ) method on a ThreadGroup lists that group's child groups.
   * Pass a true second argument and it will recursively traverse the group and children to fill a given array with ThreadGroup objects.
   * Start at the root ThreadGroup and you'll get a list of all thread groups, except the root thread group.
   * Since this method only lists the descendants of the root, you'll have to add the root to this list yourself.
   * This sounds simple enough, but if the array you pass to enumerate( ) is too small, the method silently drops some groups.
   * To allocate an array the right size, you could use the activeGroupCount( ) method on a ThreadGroup but it returns the number of groups in that group only, not in total.
   * There's no method to get the total number of thread groups. Even if there were such a method, it could be wrong a moment later if other threads add or destroy thread groups.
   * Instead, you'll have to make a guess at the right array size then call enumerate( ).
   * The method returns the number of groups it added to your array. If that number equals your array size, some groups might have been silently dropped. Increase the array size and try again.
   * 
   * @return
   */
  public static ThreadGroup[] getAllThreadGroups( ) {
    final ThreadGroup root = getRootThreadGroup();
    int nAlloc = root.activeGroupCount();
    int n = 0;
    ThreadGroup[] groups;
    do {
      nAlloc *= 2;
      groups = new ThreadGroup[nAlloc];
      n = root.enumerate(groups, true);
    } while (n == nAlloc);

    ThreadGroup[] allGroups = new ThreadGroup[n+1];
    allGroups[0] = root;
    System.arraycopy(groups, 0, allGroups, 1, n);
    return allGroups;
  }
  
  /**
   * Get a thread group by name.
   * <p>
   * To find a named thread group, you'll have to search the thread group tree.
   * You can search recursively starting at the root thread group, but it's easier to loop through the array returned by the getAllThreadGroups( ) method above.
   * Note that thread groups may not have unique names. This search will stop on the first group that matches.
   * 
   * @param name The name of the thread group to be obtained (may not be null).
   * @return A {@code ThreadGroup} with the specified name, or {@code null} if no such group exists.
   */
  public static ThreadGroup getThreadGroup(final String name) {
    Objects.requireNonNull(name, "'name' may not be null");
    
    final ThreadGroup[] threadGroups = getAllThreadGroups();
    for (ThreadGroup threadGroup : threadGroups)
      if (threadGroup.getName().equals(name))
        return threadGroup;
    return null;
  }
  
  /**
   * Get a list of all threads.
   * <p>
   * An enumerate() method on a {@code ThreadGroup} lists that group's threads.
   * With a true second argument, it will recursively traverse the group to fill a given array with Thread objects.
   * Start at the root ThreadGroup and you'll get a list of all threads in the JVM.
   * The problem here is the same as that for listing thread groups. If the array you pass to enumerate( ) is too small, some threads might be silently dropped from the returned array.
   * So, you'll need to take a guess at the array size, call enumerate( ), check the returned value, and try again if the array filled up.
   * To get a good starting guess, look to the java.lang.management package.
   * The ManagementFactory class there returns a ThreadMXBean who's getThreadCount( ) method returns the total number of threads in the JVM.
   * Of course, this can change a moment later, but it's a good first guess.
   * 
   * @return a list of all threads.
   */
  public static Thread[] getAllThreads( ) {
    final ThreadGroup root = getRootThreadGroup( );
    final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean( );
    int nAlloc = threadMXBean.getThreadCount();
    int n = 0;
    Thread[] threads;
    do {
      nAlloc *= 2;
      threads = new Thread[nAlloc];
      n = root.enumerate( threads, true );
    } while (n == nAlloc);
    
    return java.util.Arrays.copyOf(threads, n);
  }
  
  /**
   * Get a list of all threads in a thread group.
   * <p>
   * Listing the threads in just one thread group requires most of the same steps used above to get all threads in the JVM.
   * The group's enumerate() method fills your array with Thread objects, but you have to allocate the array at the right size or threads might be silently dropped.
   * Call activeCount() on the group to get the number of threads in the group at that instant. Then use a larger size in case threads are added in the mean time.
   * 
   * @param threadGroup the group for which to get all threads.
   * @return all threads in {@code threadGroup}.
   */
  public static Thread[] getGroupThreads(final ThreadGroup threadGroup) {
    Objects.requireNonNull(threadGroup, "'threadGroup' may not be null");
    
    int nAlloc = threadGroup.activeCount();
    int n = 0;
    Thread[] threads;
    do {
      nAlloc *= 2;
      threads = new Thread[nAlloc];
      n = threadGroup.enumerate(threads);
    } while (n == nAlloc);
    
    return java.util.Arrays.copyOf( threads, n );
  }
  
  /**
   * Get a thread by its ID.
   * <p>
   * If you have a thread ID, you'll have to loop through a list of all threads to find the corresponding Thread object.
   * 
   * @param id the ID of the thread to be obtained.
   * @return the {@code Thread} with the specified ID, or null if no such thread exists.
   */
  public static Thread getThread(final long id) {
    final Thread[] threads = getAllThreads( );
    for (Thread thread : threads) {
      if (thread.threadId() == id) {
        return thread;
      }
    }
    
    return null;
  }
  
  /**
   * Get a thread by its name.
   * <p>
   * If you have a thread name, you'll have to loop through a list of all threads to find the corresponding Thread object.
   * 
   * @param name the name of the thread to be obtained.
   * @return a {@code Thread} with the specified name, or null if no such thread exists.
   */
  public static Thread getThread(final String name) {
    Objects.requireNonNull(name, "'name' may not be null");
    
    final Thread[] threads = getAllThreads( );
    for (Thread thread : threads) {
      if (thread.getName().equals(name)) {
        return thread;
      }
    }
    
    return null;
  }
}
