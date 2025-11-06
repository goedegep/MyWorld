package goedegep.myworld.common;

/**
 * Possible user choices.
 */
public enum UserChoice {
  SHOW_SETTINGS_EDITOR("Edit User Settings"),
  CREATE_MISSING_FILES_AND_OR_FOLDERS("Create missing files and/or folders");

  private String text;

  UserChoice(String text) {
    this.text = text;
  }

  public String toString() {
    return text;
  }

}
