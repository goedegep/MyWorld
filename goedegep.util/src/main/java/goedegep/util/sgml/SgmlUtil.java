package goedegep.util.sgml;

import goedegep.util.html.HtmlUtil;
import goedegep.util.text.Indent;

/**
 * This class provides SGML (Standard Generalized Markup Language) related utility methods.
 * <p>
 * As XML is a subset of SGML, these methods can also be used for XML.<br/>
 * As HTML is closely related to SGML, most methods can also be used for HTML.
 */
public class SgmlUtil {
  /**
   * Constructor
   * <p>
   * A private constructor, so this utility class cannot be instantiated nor extended.
   */
  private SgmlUtil() {
  }
  
  /**
   * Create an element, without using a name space and indentation.
   * 
   * @param tag The SGML tag
   * @param text the content of the element
   * @return the created element
   */
  public static String createElement(String tag, String text) {
    return createElement(null, null, tag, text);
  }

  /**
   * Create an element, without using indentation.
   * 
   * @param nameSpace the name space for the tag
   * @param tag The SGML tag
   * @param text the content of the element
   * @return the created element
   */
  public static String createElement(String nameSpace, String element, String text) {
    return createElement(null, nameSpace, element, text);
  }

  /**
   * Create an element.
   * <p>
   * 
   * @param indent the spaces before the element (optional).
   * @param nameSpace the name space for the tag (optional, but may not be empty).
   * @param tag The SGML tag
   * @param text the content of the element
   * @return the created element
   */
  public static String createElement(Indent indent, String nameSpace, String element, String text) {
    String indentation;
    
    if (indent != null) {
      indentation = indent.toString();
    } else {
      indentation = "";
    }
    
    text = HtmlUtil.encodeHTML(text, false);
    if (nameSpace != null) {
      if (nameSpace.length() == 0) {
        throw new IllegalArgumentException("nameSpace may not be an empty String in createElement()");
      }
      return indentation + "<" + nameSpace + ":" + element + ">" + text + "</" + nameSpace + ":" + element + ">";
    } else {
      return indentation + "<" + element + ">" + text + "</" + element + ">";      
    }
  }

  
  /**
   * Create an open/close element.
   * <p>
   * An example of such an element is '<br/>'.
   * 
   * @param tag The SGML tag
   * @return the created open/close element
   */
  public static String createElementOpenClose(String tag) {
    return createElementOpenClose(null, null, tag);
  }
  /**
   * Create an open/close element.
   * <p>
   * An example of such an element is '<br/>'.
   * 
   * @param nameSpace the name space for the tag (optional, but may not be empty).
   * @param tag The SGML tag
   * @return the created open/close element
   */
  public static String createElementOpenClose(String nameSpace, String tag) {
    return createElementOpenClose(null, nameSpace, tag);
  }
  
  /**
   * Create an open/close element.
   * <p>
   * An example of such an element is '<br/>'.
   * 
   * @param indent the spaces before the element (optional).
   * @param nameSpace the name space for the tag (optional, but may not be empty).
   * @param tag The SGML tag
   * @return the created open/close element
   */
  public static String createElementOpenClose(Indent indent, String nameSpace, String tag) {
    String indentation;
    
    if (indent != null) {
      indentation = indent.toString();
    } else {
      indentation = "";
    }
    
    if (nameSpace != null) {
      if (nameSpace.length() == 0) {
        throw new IllegalArgumentException("nameSpace may not be an empty String in createElementOpenClose");
      }
      return indentation + "<" + nameSpace + ":" + tag + "/>";
    } else {
      return indentation + "<" + tag + "/>";      
    }
  }
  
  /**
   * Create an 'element open part'.
   * 
   * @param tag The SGML tag
   * @return the created 'element open part'
   */
  public static String createElementOpen(String tag) {
    return createElementOpen(null, null, tag, null);
  }
  
  /**
   * Create an 'element open part'.
   * 
   * @param nameSpace the name space for the tag (optional, but may not be empty).
   * @param tag The SGML tag
   * @return the created 'element open part'
   */
  public static String createElementOpen(String nameSpace, String tag) {
    return createElementOpen(null, nameSpace, tag, null);
  }
  
  /**
   * Create an 'element open part'.
   * 
   * @param indent the spaces before the element (optional).
   * @param nameSpace the name space for the tag (optional, but may not be empty).
   * @param tag The SGML tag
   * @return the created 'element open part'
   */
  public static String createElementOpen(Indent indent, String nameSpace, String element) {
    return createElementOpen(indent, nameSpace, element, null);
  }
  
  /**
   * Create an 'element open part'.
   * 
   * @param indent the spaces before the element (optional).
   * @param nameSpace the name space for the tag (optional, but may not be empty).
   * @param tag The SGML tag
   * @param attributeDefinitions attribute definitions, an array of 'key=value' (optional).
   * @return the created 'element open part'
   */
  public static String createElementOpen(Indent indent, String nameSpace, String tag, String[] attributeDefinitions) {
    StringBuilder buf = new StringBuilder();
    
    if (indent != null) {
      buf.append(indent);
    }
    
    buf.append("<");
    
    if (nameSpace != null) {
      if (nameSpace.length() == 0) {
        throw new IllegalArgumentException("nameSpace may not be an empty String in createElementOpen");
      }
      buf.append(nameSpace);
      buf.append(":");
    }
    buf.append(tag);
    
    if (attributeDefinitions != null) {
      for (String attributeDefinition: attributeDefinitions) {
        buf.append(" ");
        buf.append(attributeDefinition);
      }
    }
    
    buf.append(">");
    
    return buf.toString();
  }

  /**
   * Create an 'element close part'.
   * 
   * @param tag The SGML tag
   * @return the created 'element close part'
   */
  public static String createElementClose(String tag) {
    return createElementClose(null, null, tag);
  }
  
  /**
   * Create an 'element close part'.
   * 
   * @param tag The SGML tag
   * @param nameSpace the name space for the tag (optional, but may not be empty).
   * @return the created 'element close part'
   */
  public static String createElementClose(String nameSpace, String tag) {
    return createElementClose(null, nameSpace, tag);
  }
  
  /**
   * Create an 'element close part'.
   * 
   * @param indent the spaces before the element (optional).
   * @param tag The SGML tag
   * @param nameSpace the name space for the tag (optional, but may not be empty).
   * @return the created 'element close part'
   */
  public static String createElementClose(Indent indent, String nameSpace, String tag) {
    String indentation;
    
    if (indent != null) {
      indentation = indent.toString();
    } else {
      indentation = "";
    }
    
    if (nameSpace != null) {
      if (nameSpace.length() == 0) {
        throw new IllegalArgumentException("nameSpace may not be an empty String in createElementClose");
      }
      return indentation + "</" + nameSpace + ":" + tag + ">";
    } else {
      return indentation + "</" + tag + ">";      
    }
  }

  /**
   * Enclose a String in "<![CDATA[" and "]]>" markers.
   * 
   * @param string the text to be enclosed
   * @return the enclosed text
   */
  public static String encloseInCData(String string) {
    return new StringBuilder("<![CDATA[").append(string).append("]]>").toString();
  }
}
