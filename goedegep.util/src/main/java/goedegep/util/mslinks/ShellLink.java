/*
	https://github.com/BlackOverlord666/mslinks

	Copyright (c) 2015 Dmitrii Shamrikov

	Licensed under the WTFPL
	You may obtain a copy of the License at

	http://www.wtfpl.net/about/

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package goedegep.util.mslinks;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import goedegep.util.mslinks.data.CNRLink;
import goedegep.util.mslinks.data.ItemID;
import goedegep.util.mslinks.data.LinkFlags;
import goedegep.util.mslinks.data.VolumeID;
import goedegep.util.mslinks.extra.ConsoleData;
import goedegep.util.mslinks.extra.ConsoleFEData;
import goedegep.util.mslinks.extra.EnvironmentVariable;
import goedegep.util.mslinks.extra.Stub;
import goedegep.util.mslinks.extra.Tracker;
import goedegep.util.mslinks.extra.VistaIDList;
import goedegep.util.mslinks.io.ByteReader;
import goedegep.util.mslinks.io.ByteWriter;

/**
 * This class provides functionality to create Windows Shell Links ('.lnk' files, also referred to as shortcuts).
 * <p>
 * For information on the Shell Link file format, see https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-shllink/16cb4ca1-9339-4d0c-a68d-bf1d6cc0f943?redirectedfrom=MSDN.
 * <p>
 * To create a shortcut file:
 * <ul>
 * <li>
 * Create an empty <code>ShellLink</code> object.
 * </li>
 * <li>
 * Fill it with the required information using the setters and/or use the getters to obtains the sub structures and use the setters on those.
 * </li>
 * <li>
 * Write the <code>ShellLink</code> object to a file by calling saveTo().
 * </li>
 * </ul>
 * There are a few static convenience methods to perform several actions at once.
 * <p>
 * Many methods return the object itself, so you can use constructions like:<br/>
 * <pre>
 * ShellLink shellLink = ShellLink.createLink(...)
        .setWorkingDir(...)
        .setIconLocation(...)
        .setCMDArgs(...)
        .saveTo(shortcutPath);
 * </pre>
 */
public class ShellLink {
  private static final String         NEWLINE = System.getProperty("line.separator");
  private static final Logger LOGGER = Logger.getLogger(ShellLink.class.getName());

  private static Map<String, String> env = System.getenv();

  @SuppressWarnings("serial")
  private static HashMap<Integer, Class<? extends Object>> extraTypes = new HashMap<Integer, Class<? extends Object>>() {{
    put(ConsoleData.signature, ConsoleData.class);
    put(ConsoleFEData.signature, ConsoleFEData.class);
    put(Tracker.signature, Tracker.class);
    put(VistaIDList.signature, VistaIDList.class);
    put(EnvironmentVariable.signature, EnvironmentVariable.class);
  }};


  private ShellLinkHeader header;
  private LinkTargetIDList idlist;
  private LinkInfo info;
  private String name, relativePath, workingDir, cmdArgs, iconLocation;
  private HashMap<Integer, Serializable> extra = new HashMap<>();

  private Path linkFileSource;

  /**
   * Constructor to create an empty <code>ShellLink</code> object.
   */
  public ShellLink() {
    header = new ShellLinkHeader();
    header.getLinkFlags().setIsUnicode();
  }

  /**
   * Constructor to create a ShellLink from an existing shortcut file.
   * 
   * @param file the name of the shortcut file to read.
   * @throws IOException
   * @throws ShellLinkException
   */
  public ShellLink(String file) throws IOException, ShellLinkException {
    this(Paths.get(file));
  }

  /**
   * Constructor to create a ShellLink from an existing shortcut file.
   * 
   * @param file the <code>File</code>, a shortcut file, to read.
   * @throws IOException
   * @throws ShellLinkException
   */
  public ShellLink(File file) throws IOException, ShellLinkException {
    this(file.toPath());
  }

  /**
   * Constructor to create a ShellLink from an existing shortcut file.
   * 
   * @param path a <code>Path</code> to the shortcut file to read.
   * @throws IOException
   * @throws ShellLinkException
   */
  public ShellLink(Path path) throws IOException, ShellLinkException {
    this(Files.newInputStream(path));
    linkFileSource = path.toAbsolutePath();
  }

  /**
   * Constructor to create a ShellLink from an <code>InputStream</code>
   * 
   * @param in the <code>InputStream</code> to read from.
   * @throws IOException
   * @throws ShellLinkException
   */
  public ShellLink(InputStream in) throws IOException, ShellLinkException {
    this(new ByteReader(in));
    in.close();
  }

  /**
   * Constructor to create a ShellLink from a <code>ByteReader</code>.
   * 
   * @param data the <code>ByteReader</code> to read from.
   * @throws ShellLinkException
   * @throws IOException
   */
  private ShellLink(ByteReader data) throws ShellLinkException, IOException {
    header = new ShellLinkHeader(data);
    LinkFlags lf = header.getLinkFlags();
    if (lf.hasLinkTargetIDList()) 
      idlist = new LinkTargetIDList(data);
    if (lf.hasLinkInfo())
      info = new LinkInfo(data);
    if (lf.hasName())
      name = data.readUnicodeString();
    if (lf.hasRelativePath())
      relativePath = data.readUnicodeString();
    if (lf.hasWorkingDir()) 
      workingDir = data.readUnicodeString();
    if (lf.hasArguments()) 
      cmdArgs = data.readUnicodeString();
    if (lf.hasIconLocation())
      iconLocation = data.readUnicodeString();

    while (true) {
      int size = (int)data.read4bytes();
      if (size < 4) break;
      int sign = (int)data.read4bytes();
      try {
        Class<? extends Object> cl = extraTypes.get(sign);
        if (cl != null)
          extra.put(sign, (Serializable)cl.getConstructor(ByteReader.class, int.class).newInstance(data, size));
        else
          extra.put(sign, new Stub(data, size, sign));
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException | NoSuchMethodException	| SecurityException e) {	
        e.printStackTrace();
      }
    }
  }

  private void serialize(OutputStream out) throws IOException {
    LinkFlags lf = header.getLinkFlags();
    ByteWriter bw = new ByteWriter(out);
    header.serialize(bw);
    if (lf.hasLinkTargetIDList())
      idlist.serialize(bw);

    if (lf.hasLinkInfo())
      info.serialize(bw);
    if (lf.hasName())
      bw.writeUnicodeString(name);
    if (lf.hasRelativePath())
      bw.writeUnicodeString(relativePath);
    if (lf.hasWorkingDir()) 
      bw.writeUnicodeString(workingDir);
    if (lf.hasArguments()) 
      bw.writeUnicodeString(cmdArgs);
    if (lf.hasIconLocation())
      bw.writeUnicodeString(iconLocation);

    for (Serializable i : extra.values())
      i.serialize(bw);

    bw.write4bytes(0);
    out.close();
  }

  /**
   * Get the <code>ShellLinkHeader</code>.
   * 
   * @return the <code>ShellLinkHeader</code>.
   */
  public ShellLinkHeader getHeader() {
    return header;
  }

  /**
   * Get the <code>LinkInfo</code>.
   * 
   * @return the <code>LinkInfo</code>.
   */
  public LinkInfo getLinkInfo() {
    return info;
  }

  /**
   * Create the <code>LinkInfo</code>.
   * 
   * @return the newly created <code>LinkInfo</code>.
   */
  public LinkInfo createLinkInfo() {
    info = new LinkInfo();
    header.getLinkFlags().setHasLinkInfo();
    return info;
  }

  /**
   * Get the name.
   * 
   * @return the name.
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name.
   * 
   * @param s the name.
   * @return this
   */
  public ShellLink setName(String s) {
    if (s == null) 
      header.getLinkFlags().clearHasName();
    else 
      header.getLinkFlags().setHasName();
    name = s;
    return this;
  }

  /**
   * Get the relative path.
   * 
   * @return the relative path.
   */
  public String getRelativePath() {
    return relativePath;
  }

  /**
   * Set the relative path.
   * 
   * @param s the relative path.
   * @return this.
   */
  public ShellLink setRelativePath(String s) {
    if (s == null) 
      header.getLinkFlags().clearHasRelativePath();
    else { 
      header.getLinkFlags().setHasRelativePath();
      if (!s.startsWith("."))
        s = ".\\" + s;
    }
    relativePath = s;
    return this;
  }

  /**
   * Get the working directory.
   * 
   * @return the working directory.
   */
  public String getWorkingDir() {
    return workingDir;
  }

  /**
   * Set the working directory.
   * 
   * @param s the working directory.
   * @return this
   */
  public ShellLink setWorkingDir(String s) {
    if (s == null) 
      header.getLinkFlags().clearHasWorkingDir();
    else {
      header.getLinkFlags().setHasWorkingDir();
      s = Paths.get(s).toAbsolutePath().normalize().toString();
    }
    workingDir = s;
    return this;
  }

  /**
   * Get the command arguments.
   * 
   * @return the command arguments.
   */
  public String getCMDArgs() {
    return cmdArgs;
  }

  /**
   * Set the command arguments.
   * 
   * @param s the command arguments.
   * @return this
   */
  public ShellLink setCMDArgs(String s) {
    if (s == null) 
      header.getLinkFlags().clearHasArguments();
    else 
      header.getLinkFlags().setHasArguments();
    cmdArgs = s;
    return this;
  }

  /**
   * Get the icon location.
   * 
   * @return the icon location as String
   */
  public String getIconLocation() {
    return iconLocation;
  }

  /**
   * Set the icon location.
   * 
   * @param s the icon location.
   * @return
   */
  public ShellLink setIconLocation(String s) {
    if (s == null) 
      header.getLinkFlags().clearHasIconLocation();
    else {
      header.getLinkFlags().setHasIconLocation();
      String t = resolveEnvVariables(s);
      if (!Paths.get(t).isAbsolute())
        s = Paths.get(s).toAbsolutePath().toString();
    }
    iconLocation = s;
    return this;
  }

  /**
   * Get the console data.
   * <p>
   * By calling this method the console data will be created if it doesn't exist yet.
   * 
   * @return the console data.
   */
  public ConsoleData getConsoleData() {
    ConsoleData cd = (ConsoleData)extra.get(ConsoleData.signature);
    if (cd == null) {
      cd = new ConsoleData();
      extra.put(ConsoleData.signature, cd);
    }
    return cd;
  }

  /**
   * Get the language.
   * <p>
   * By calling this method the console FE data will be created if it doesn't exist yet.
   * 
   * @return the language
   */
  public String getLanguage() { 
    ConsoleFEData cd = (ConsoleFEData)extra.get(ConsoleFEData.signature);
    if (cd == null) {
      cd = new ConsoleFEData();
      extra.put(ConsoleFEData.signature, cd);
    }
    return cd.getLanguage();
  }

  /**
   * Set the language.
   * <p>
   * By calling this method the console FE data will be created if it doesn't exist yet.
   * 
   * @param s the language.
   * @return
   */
  public ShellLink setLanguage(String s) { 
    ConsoleFEData cd = (ConsoleFEData)extra.get(ConsoleFEData.signature);
    if (cd == null) {
      cd = new ConsoleFEData();
      extra.put(ConsoleFEData.signature, cd);
    }
    cd.setLanguage(s);
    return this;
  }

  /**
   * Save a <code>ShellLink</code> object to a file.
   * 
   * @param path the <code>Path</code> to save the <code>ShellLink</code> object to.
   * @return this
   * @throws IOException
   */
  public ShellLink saveTo(String path) throws IOException {
    linkFileSource = Paths.get(path).toAbsolutePath().normalize();
    if (Files.isDirectory(linkFileSource))
      throw new IOException("path is directory!");

    if (!header.getLinkFlags().hasRelativePath()) {
      Path target = Paths.get(resolveTarget());
      Path origin = linkFileSource.getParent();
      if (target.getRoot().equals(origin.getRoot())) 
        setRelativePath(origin.relativize(target).toString());
    }

    if (!header.getLinkFlags().hasWorkingDir()) {
      Path target = Paths.get(resolveTarget());
      if (!Files.isDirectory(target))
        setWorkingDir(target.getParent().toString());
    }

    serialize(Files.newOutputStream(linkFileSource));
    return this;
  }

  public String resolveTarget() {
    if (header.getLinkFlags().hasLinkTargetIDList() && idlist != null && idlist.isCorrect()) {
      String path = "";
      for (ItemID i : idlist) {
        if (i.getType() == ItemID.TYPE_DRIVE || i.getType() == ItemID.TYPE_DRIVE_OLD)
          path = i.getName();
        else if (i.getType() == ItemID.TYPE_DIRECTORY || i.getType() == ItemID.TYPE_DIRECTORY_OLD)
          path += i.getName() + File.separator;
        else if (i.getType() == ItemID.TYPE_FILE || i.getType() == ItemID.TYPE_FILE_OLD)
          path += i.getName();				
      }
      return path;
    }

    if (header.getLinkFlags().hasLinkInfo() && info != null) {
      CNRLink l = info.getCommonNetworkRelativeLink();
      String cps = info.getCommonPathSuffix();
      String lbp = info.getLocalBasePath();

      if (lbp != null) {
        String path = lbp;
        if (cps != null && !cps.equals("")) {
          if (path.charAt(path.length() - 1) != File.separatorChar)
            path += File.separatorChar;
          path += cps;
        }
        return path;
      }			

      if (l != null && cps != null)
        return l.getNetName() + File.separator + cps;			
    }

    if (linkFileSource != null && header.getLinkFlags().hasRelativePath() && relativePath != null) 
      return linkFileSource.resolveSibling(relativePath).normalize().toString();

    return "<unknown>";
  }

  /**
   * Set path of target file of directory. Function accepts local paths and network paths.
   * Environment variables are accepted but resolved here and aren't kept in link.
   */
  public ShellLink setTarget(String target) {
    target = resolveEnvVariables(target);

    Path tar = Paths.get(target).toAbsolutePath();
    target = tar.toString();

    if (target.startsWith("\\\\")) {
      int p1 = target.indexOf('\\', 2);
      int p2 = target.indexOf('\\', p1+1);

      LinkInfo inf = createLinkInfo();
      inf.createCommonNetworkRelativeLink().setNetName(target.substring(0, p2));
      inf.setCommonPathSuffix(target.substring(p2+1));

      if (Files.isDirectory(Paths.get(target)))
        header.getFileAttributesFlags().setDirecory();

      header.getLinkFlags().setHasExpString();
      extra.put(EnvironmentVariable.signature, new EnvironmentVariable().setVariable(target));

    } else try {
      header.getLinkFlags().setHasLinkTargetIDList();
      idlist = new LinkTargetIDList();
      String[] path = target.split("\\\\");
      idlist.add(new ItemID().setType(ItemID.TYPE_CLSID));
      idlist.add(new ItemID().setType(ItemID.TYPE_DRIVE).setName(path[0]));
      for (int i=1; i<path.length; i++)
        idlist.add(new ItemID().setType(ItemID.TYPE_DIRECTORY).setName(path[i]));

      LinkInfo inf = createLinkInfo();
      inf.createVolumeID().setDriveType(VolumeID.DRIVE_FIXED);
      inf.setLocalBasePath(target);

      if (Files.isDirectory(tar))
        header.getFileAttributesFlags().setDirecory();
      else 
        idlist.getLast().setType(ItemID.TYPE_FILE);

    } catch (ShellLinkException e) {
      e.printStackTrace();
    }

    return this;
  }

  /**
   * Create a <code>ShellLink</code> where the 'target' is specified.
   * <p> 
   * This is a convenience method, which is equivalent to calling (new ShellLink()).setTarget( target ).
   * 
   * @param target Path of target file of directory. Local paths and network paths are accepted.
   *               Environment variables are accepted but resolved here and aren't kept in the link.
   * @return the newly created <code>ShellLink</code>.
   */
  public static ShellLink createLink(String target) {
    ShellLink sl = new ShellLink();
    sl.setTarget( target );
    return sl;
  }

  /**
   * Create a <code>ShellLink</code> where the 'target' is specified and save it to a file.
   * <p> 
   * This is a convenience method, which is equivalent to calling createLink(target).saveTo(linkpath).
   * 
   * @param target Path of target file of directory. Local paths and network paths are accepted.
   *               Environment variables are accepted but resolved here and aren't kept in the link.
   * @param linkpath the path to save the <code>ShellLink</code> to.
   * @return the newly created <code>ShellLink</code>.
   */
  public static ShellLink createLink(String target, String linkpath) throws IOException {
    LOGGER.severe("target=" + target + ", linkpath=" + linkpath);
    return createLink(target).saveTo(linkpath);
  }

  private static String resolveEnvVariables(String path) {
    for (String i : env.keySet()) {
      String p = i.replace("(", "\\(").replace(")", "\\)");
      String r = env.get(i).replace("\\", "\\\\");
      path = Pattern.compile("%"+p+"%", Pattern.CASE_INSENSITIVE).matcher(path).replaceAll(r);
    }
    return path;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();

    buf.append(header.toString()).append(NEWLINE);
    buf.append(idlist.toString()).append(NEWLINE);
    buf.append(info.toString()).append(NEWLINE);

    buf.append("NameString:").append(name).append(NEWLINE);
    buf.append("RelativePath:").append(relativePath).append(NEWLINE);
    buf.append("WorkingDir:").append(workingDir).append(NEWLINE);
    buf.append("CommandLineArguments:").append(cmdArgs).append(NEWLINE);
    buf.append("IconLocation:").append(iconLocation).append(NEWLINE);
    buf.append("ExtraData:").append(NEWLINE);

    for (Integer key: extra.keySet()) {
      Serializable value = extra.get(key);
      buf.append("  ").append(value.toString()).append(NEWLINE);
    }

    return buf.toString();
  }
}
