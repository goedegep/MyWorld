module org.apache.commons.imaging {
  exports org.apache.commons.imaging.common.mylzw;
  exports org.apache.commons.imaging.formats.jpeg.iptc;
  exports org.apache.commons.imaging.common;
  exports org.apache.commons.imaging.formats.jpeg.exif;
  exports org.apache.commons.imaging.formats.tiff.write;
  exports org.apache.commons.imaging.internal;
  exports org.apache.commons.imaging.formats.ico;
  exports org.apache.commons.imaging.formats.dcx;
  exports org.apache.commons.imaging.formats.jpeg.decoder;
  exports org.apache.commons.imaging.formats.png.transparencyfilters;
  exports org.apache.commons.imaging;
  exports org.apache.commons.imaging.formats.gif;
  exports org.apache.commons.imaging.formats.tiff.photometricinterpreters;
  exports org.apache.commons.imaging.formats.bmp;
  exports org.apache.commons.imaging.formats.jpeg.segments;
  exports org.apache.commons.imaging.formats.tiff.photometricinterpreters.floatingpoint;
  exports org.apache.commons.imaging.common.bytesource;
  exports org.apache.commons.imaging.formats.xbm;
  exports org.apache.commons.imaging.formats.psd;
  exports org.apache.commons.imaging.formats.icns;
  exports org.apache.commons.imaging.formats.psd.datareaders;
  exports org.apache.commons.imaging.formats.tiff.datareaders;
  exports org.apache.commons.imaging.formats.jpeg;
  exports org.apache.commons.imaging.formats.xpm;
  exports org.apache.commons.imaging.formats.jpeg.xmp;
  exports org.apache.commons.imaging.formats.tiff.taginfos;
  exports org.apache.commons.imaging.formats.tiff.fieldtypes;
  exports org.apache.commons.imaging.formats.pnm;
  exports org.apache.commons.imaging.formats.psd.dataparsers;
  exports org.apache.commons.imaging.formats.tiff;
  exports org.apache.commons.imaging.color;
  exports org.apache.commons.imaging.formats.png.scanlinefilters;
  exports org.apache.commons.imaging.formats.rgbe;
  exports org.apache.commons.imaging.common.itu_t4;
  exports org.apache.commons.imaging.formats.tiff.constants;
  exports org.apache.commons.imaging.formats.wbmp;
  exports org.apache.commons.imaging.formats.pcx;
  exports org.apache.commons.imaging.palette;
  exports org.apache.commons.imaging.formats.png.chunks;
  exports org.apache.commons.imaging.formats.png;
  exports org.apache.commons.imaging.icc;

  requires transitive java.desktop;
  requires java.logging;
  requires org.junit.jupiter.api;
  requires org.junit.jupiter.params;
  requires org.hamcrest;
}