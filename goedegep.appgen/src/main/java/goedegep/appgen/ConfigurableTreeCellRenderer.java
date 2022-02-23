package goedegep.appgen;


import java.awt.Component;
import java.text.Format;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 * A tree cell renderer, which can be configured.
 * Specific renderers can be added for specific classes of the user object of a DefaultMutableTreeNode.
 *
 */
@SuppressWarnings("serial")
public class ConfigurableTreeCellRenderer extends DefaultTreeCellRenderer {
  private static final Logger         LOGGER = Logger.getLogger(ConfigurableTreeCellRenderer.class.getName());

  private DefaultTreeCellRenderer defaultTreeCellRenderer = new DefaultTreeCellRenderer();
  private Map<Class<?>, Format> classSpecificFormat = new HashMap<Class<?>, Format>();
  private Map<Class<?>, TreeCellRenderer> classSpecificRenderers = new HashMap<Class<?>, TreeCellRenderer>();

  public void addClassSpecificFormat(Class<?> cellClass, Format format) {
    LOGGER.info("Setting class specific format for cellClass: " + cellClass.getName());
    classSpecificFormat.put(cellClass, format);
  }

  public void addClassSpecificCellRenderer(Class<?> cellClass, TreeCellRenderer cellRenderer) {
    LOGGER.info("Setting class specific renderer for cellClass: " + cellClass.getName());
    if (cellRenderer instanceof DefaultTreeCellRenderer) {
      ((DefaultTreeCellRenderer) cellRenderer).setLeafIcon(null);
    }
    classSpecificRenderers.put(cellClass, cellRenderer);
  }

  /**
   * Specific renderers are applied in the following order:
   * - based on the class of 'value'
   *   - classSpecificRenderers
   *   - classSpecificFormat (formatted text is passed on to DefaultTreeCellRenderer)
   * - DefaultTreeCellRenderer
   */
  public Component getTreeCellRendererComponent(
      JTree tree,
      Object value,
      boolean sel,
      boolean expanded,
      boolean leaf,
      int row,
      boolean hasFocus) {    
    LOGGER.info("=> class=" + value.getClass().getName() + ", value=" + value.toString());

    TreeCellRenderer renderer;
    Format format;
    Object userObject = null;
    if (value instanceof DefaultMutableTreeNode) {
      userObject = ((DefaultMutableTreeNode) value).getUserObject();
      LOGGER.info("class of userObject=" + userObject.getClass().getName());
    }

    // Check for class specific renderer
    if (userObject != null) {
      LOGGER.info("Checking for class specific renderer for class: " + userObject.getClass().getName());
      renderer = classSpecificRenderers.get(userObject.getClass());
      if (renderer != null) {
        LOGGER.info("<= Class specific renderer: " + renderer);
        return renderer.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
      } else {
        LOGGER.info("No Class specific renderer");
      }
    }

    // Check for class specific format
    if (value != null) {
      LOGGER.info("Checking for class specific format.");
      format = classSpecificFormat.get(value.getClass());
      if (format != null) {
        LOGGER.info("<= Class specific format: " + format);
        return  defaultTreeCellRenderer.getTreeCellRendererComponent(
            tree, format.format(value), sel,
            expanded, leaf, row,
            hasFocus);
      } else {
        LOGGER.fine("No class specific format");
      }
    }

    LOGGER.info("<= default to 'DefaultTreeCellRenderer'");
    return  defaultTreeCellRenderer.getTreeCellRendererComponent(
        tree, value, sel,
        expanded, leaf, row,
        hasFocus);
  }

}
