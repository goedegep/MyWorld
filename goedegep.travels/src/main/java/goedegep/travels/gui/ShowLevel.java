package goedegep.travels.gui;

/**
 * This enum defines the level of information shown on the map for the selected item in the tree.
 */
enum ShowLevel {
  SELECTED_ITEM("Selected item"),
  DAY("Day"),
  TRAVEL("Travel");
  
  /**
   * Name to be shown in the GUI
   */
  private String displayName;
  
  /**
   * Constructor.
   * 
   * @param displayName the name to be shown in the GUI for this show level
   */
  ShowLevel(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Get the name to be shown in the GUI for this show level.
   * 
   * @return the name to be shown in the GUI for this show level
   */
  public String getDisplayName() {
    return displayName;
  }
  
  /**
   * Get the next show level.
   * 
   * @return the next show level
   */
  public ShowLevel next() {
    if (ordinal() < values().length - 1) {
      return values()[(ordinal() + 1)];
    } else {
      return this;
    }
  }
  
  /**
   * Get the previous show level.
   * 
   * @return the previous show level
   */
  public ShowLevel previous() {
    if (ordinal() > 0) {
      return values()[(ordinal() - 1)];
    } else {
      return this;
    }
  }
  
  @Override
  public String toString() {
    return displayName;
  }
}
