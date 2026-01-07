package goedegep.jfx;

public enum Operation {
  NEW_OBJECT,                  // Shown in case of an empty table
  NEW_OBJECT_BEFORE,           // Only applicable for ordered collections (where the list isn't sorted)
  NEW_OBJECT_AFTER,            // Only applicable for ordered collections (where the list isn't sorted)
  DELETE_OBJECT,               // Delete the current object
  MOVE_OBJECT_UP,              // Only applicable for ordered collections (where the list isn't sorted)
  MOVE_OBJECT_DOWN,            // Only applicable for ordered collections (where the list isn't sorted)
  ATTRIBUTE_EDITOR,            // Open the object in an editor panel, for which the function is to be specified in a descriptor (not implemented yet)
  OPEN,                        // Open the object in an external application (same as the Windows 'Open' functionality).
  EXTENDED_OPERATION           // For a custom specific extension
}
