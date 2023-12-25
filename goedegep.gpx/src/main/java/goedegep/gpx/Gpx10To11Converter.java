package goedegep.gpx;

import java.util.Iterator;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;

import goedegep.gpx.model.BoundsType;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.EmailType;
import goedegep.gpx.model.ExtensionsType;
import goedegep.gpx.model.FixType;
import goedegep.gpx.model.GPXFactory;
import goedegep.gpx.model.GPXPackage;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.LinkType;
import goedegep.gpx.model.MetadataType;
import goedegep.gpx.model.PersonType;
import goedegep.gpx.model.RteType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;
import goedegep.gpx10.model.RteptType;
import goedegep.gpx10.model.TrkptType;

public class Gpx10To11Converter {
  private static final Logger LOGGER = Logger.getLogger(Gpx10To11Converter.class.getName());

  static final GPXFactory GPX_FACTORY = GPXFactory.eINSTANCE;
  static final GPXPackage GPX_PACKAGE = GPXPackage.eINSTANCE;
  
  public static DocumentRoot convertGpxVersion10To11(goedegep.gpx10.model.DocumentRoot documentRoot10) {
    LOGGER.info("=>");
    
    DocumentRoot documentRoot = GPX_FACTORY.createDocumentRoot();
    
    // mixed
    FeatureMap featureMap = documentRoot10.getMixed();
    Iterator<Entry> iterator = featureMap.iterator();
    while (iterator.hasNext()) {
      Entry entry = iterator.next();
      Object value = entry.getValue();
      LOGGER.info("value: " + value.toString());
      if (value instanceof goedegep.gpx10.model.GpxType gpxType) {
        documentRoot.setGpx(convertGpx10ToGpx(documentRoot10.getGpx()));
      } else {
        throw new RuntimeException("value not supported: " + value.toString());
      }
    }
    
    // xMLNSPrefixMap
    // Ignore the values in the model and set the values for version 1.1.
    documentRoot.getXMLNSPrefixMap().put("", "http://www.topografix.com/GPX/1/1");
    documentRoot.getXMLNSPrefixMap().put("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    
    // xSISchemaLocation
    // Ignore the values in the model and set the values for version 1.1.
    documentRoot.getXSISchemaLocation().put("http://www.topografix.com/GPX/1/1", "http://www.topografix.com/GPX/1/1/gpx.xsd");
        
    LOGGER.info("<=");
    return documentRoot;
  }

  private static GpxType convertGpx10ToGpx(goedegep.gpx10.model.GpxType gpxType10) {
    GpxType gpxType = GPX_FACTORY.createGpxType();
    
    // 1.1 metadata attributes
    if (gpxType10.getName() != null  ||
        gpxType10.getDesc() != null  ||
        gpxType10.getAuthor() != null  ||
        gpxType10.getEmail() != null  ||
        gpxType10.getUrl() != null  ||
        gpxType10.getUrlname() != null  ||
        gpxType10.getTime() != null  ||
        gpxType10.getKeywords() != null  ||
        gpxType10.getBounds() != null) {
      MetadataType metaDataType = GPX_FACTORY.createMetadataType();
      
      // name
      metaDataType.setName(gpxType10.getName());
      
      // desc
      metaDataType.setDesc(gpxType10.getDesc());
      
      // author/email
      if (gpxType10.getAuthor() != null  ||  gpxType10.getEmail() != null) {
        PersonType personType = GPX_FACTORY.createPersonType();
        personType.setName(gpxType10.getAuthor());
        
        if (gpxType10.getEmail() != null) {
          String[] emailParts = gpxType10.getEmail().split("@");
          if (emailParts.length != 2) {
            throw new RuntimeException("Wrong email address format: " + gpxType10.getEmail());
          } else {
            EmailType emailType = GPX_FACTORY.createEmailType();
            emailType.setId(emailParts[0]);
            emailType.setDomain(emailParts[1]);
            personType.setEmail(emailType);
          }
        }
        
        metaDataType.setAuthor(personType);
      }
      
      // url/urlname
      if (gpxType10.getUrl() != null  ||  gpxType10.getUrlname() != null) {
        metaDataType.getLink().add(createLink(gpxType10.getUrl(), gpxType10.getUrlname()));
      }
      
      // time
      if (gpxType10.getTime() != null) {
        metaDataType.setTime(gpxType10.getTime());
      }
      
      // keywords
      if (gpxType10.getKeywords() != null) {
        metaDataType.setKeywords(gpxType10.getKeywords());
      }
      
      // bounds
      if (gpxType10.getBounds() != null) {
        metaDataType.setBounds(convertBounds10ToBounds(gpxType10.getBounds()));
      }
      
      gpxType.setMetadata(metaDataType);
    }
    
    // any
    if (!gpxType10.getAny().isEmpty()) {
      ExtensionsType extensionsType = GPX_FACTORY.createExtensionsType();
      
      extensionsType.getAny().addAll(gpxType10.getAny());
      
      gpxType.setExtensions(extensionsType);
    }
    
    // version
    gpxType.setVersion("1.1");
    
    // creator
    gpxType.setCreator(gpxType10.getCreator());
    
    // wpt
    for (goedegep.gpx10.model.WptType wptType10: gpxType10.getWpt()) {
      gpxType.getWpt().add(convertWpt10ToWpt(wptType10));
    }
    
    // rte
    for (goedegep.gpx10.model.RteType rteType10: gpxType10.getRte()) {
      gpxType.getRte().add(convertRte10ToRte(rteType10));
    }
    
    // trk
    for (goedegep.gpx10.model.TrkType trkType10: gpxType10.getTrk()) {
      gpxType.getTrk().add(convertTrk10ToTrk(trkType10));
    }
    
    return gpxType;
  }

  private static BoundsType convertBounds10ToBounds(goedegep.gpx10.model.BoundsType boundsType10) {
    BoundsType boundsType = GPX_FACTORY.createBoundsType();
    
    if (boundsType10.getMinlat() != null) {
      boundsType.setMinlat(boundsType10.getMinlat());
    }
    
    if (boundsType10.getMinlon() != null) {
      boundsType.setMinlon(boundsType10.getMinlon());
    }
    
    if (boundsType10.getMaxlat() != null) {
      boundsType.setMaxlat(boundsType10.getMaxlat());
    }
    
    if (boundsType10.getMaxlon() != null) {
      boundsType.setMaxlon(boundsType10.getMaxlon());
    }
    
    return boundsType;
  }


  private static WptType convertWpt10ToWpt(goedegep.gpx10.model.WptType wptType10) {
    WptType wptType = GPX_FACTORY.createWptType();
    
    if (wptType10.getEle() != null) {
      wptType.setEle(wptType10.getEle());
    }
    
    if (wptType10.getTime() != null) {
      wptType.setTime(wptType10.getTime());
    }
    
    if (wptType10.getMagvar() != null) {
      wptType.setMagvar(wptType10.getMagvar());
    }
    
    if (wptType10.getGeoidheight() != null) {
      wptType.setGeoidheight(wptType10.getGeoidheight());
    }
    
    if (wptType10.getName() != null) {
      wptType.setName(wptType10.getName());
    }
    
    if (wptType10.getCmt() != null) {
      wptType.setCmt(wptType10.getCmt());
    }
    
    if (wptType10.getDesc() != null) {
      wptType.setDesc(wptType10.getDesc());
    }
    
    if (wptType10.getSrc() != null) {
      wptType.setSrc(wptType10.getSrc());
    }
    
    if (wptType10.getUrl() != null  ||  wptType10.getUrlname() != null) {
      wptType.getLink().add(createLink(wptType10.getUrl(), wptType10.getUrlname()));
    }
    
    if (wptType10.getSym() != null) {
      wptType.setSym(wptType10.getSym());
    }
    
    if (wptType10.getType() != null) {
      wptType.setType(wptType10.getType());
    }
    
    if (wptType10.getFix() != null) {
      wptType.setFix(convertFixType10ToFixType(wptType10.getFix()));
    }
    
    if (wptType10.getSat() != null) {
      wptType.setSat(wptType10.getSat());
    }
    
    if (wptType10.getHdop() != null) {
      wptType.setHdop(wptType10.getHdop());
    }
    
    if (wptType10.getVdop() != null) {
      wptType.setVdop(wptType10.getVdop());
    }
    
    if (wptType10.getPdop() != null) {
      wptType.setPdop(wptType10.getPdop());
    }
    
    if (wptType10.getAgeofdgpsdata() != null) {
      wptType.setAgeofdgpsdata(wptType10.getAgeofdgpsdata());
    }
    
    if (!wptType10.getAny().isEmpty()) {
      ExtensionsType extensionsType = GPX_FACTORY.createExtensionsType();
      
      extensionsType.getAny().addAll(wptType10.getAny());
      
      wptType.setExtensions(extensionsType);
    }
    
    if (wptType10.getDgpsid() != null) {
      wptType.setDgpsid(wptType10.getDgpsid());
    }
    
    if (wptType10.getLat() != null) {
      wptType.setLat(wptType10.getLat());
    }
    
    if (wptType10.getLon() != null) {
      wptType.setLon(wptType10.getLon());
    }
    
    return wptType;
  }

  private static LinkType createLink(String url, String urlname) {
    LinkType linkType = GPX_FACTORY.createLinkType();
    
    linkType.setHref(url);
    linkType.setText(urlname);
    
    return linkType;
  }

  private static FixType convertFixType10ToFixType(goedegep.gpx10.model.FixType fixType10) {
    FixType fixType = FixType.get(fixType10.getLiteral());
    
    return fixType;
  }

  private static RteType convertRte10ToRte(goedegep.gpx10.model.RteType rteType10) {
    RteType rteType = GPX_FACTORY.createRteType();
    
    if (rteType10.getName() != null) {
      rteType.setName(rteType10.getName());
    }
    
    if (rteType10.getCmt() != null) {
      rteType.setCmt(rteType10.getCmt());
    }
    
    if (rteType10.getDesc() != null) {
      rteType.setDesc(rteType10.getDesc());
    }
    
    if (rteType10.getSrc() != null) {
      rteType.setSrc(rteType10.getSrc());
    }
    
    if (rteType10.getUrl() != null  || rteType10.getUrlname() != null) {
     rteType.getLink().add(createLink(rteType10.getUrl(), rteType10.getUrlname()));
    }
    
    if (rteType10.getNumber() != null) {
      rteType.setNumber(rteType10.getNumber());
    }
    
    if (!rteType10.getAny().isEmpty()) {
      ExtensionsType extensionsType = GPX_FACTORY.createExtensionsType();
      
      extensionsType.getAny().addAll(rteType10.getAny());
      
      rteType.setExtensions(extensionsType);
    }
    
    for (goedegep.gpx10.model.RteptType rteptType10: rteType10.getRtept()) {
      rteType.getRtept().add(convertRtept10ToWpt(rteptType10));
    }
    
    
    return rteType;
  }

  private static WptType convertRtept10ToWpt(RteptType rteptType10) {
    WptType wptType = GPX_FACTORY.createWptType();
    
    if (rteptType10.getEle() != null) {
      wptType.setEle(rteptType10.getEle());
    }
    
    if (rteptType10.getTime() != null) {
      wptType.setTime(rteptType10.getTime());
    }
    
    if (rteptType10.getMagvar() != null) {
      wptType.setMagvar(rteptType10.getMagvar());
    }
    
    if (rteptType10.getGeoidheight() != null) {
      wptType.setGeoidheight(rteptType10.getGeoidheight());
    }
    
    if (rteptType10.getName() != null) {
      wptType.setName(rteptType10.getName());
    }
    
    if (rteptType10.getCmt() != null) {
      wptType.setCmt(rteptType10.getCmt());
    }
    
    if (rteptType10.getDesc() != null) {
      wptType.setDesc(rteptType10.getDesc());
    }
    
    if (rteptType10.getSrc() != null) {
      wptType.setSrc(rteptType10.getSrc());
    }
    
    if (rteptType10.getUrl() != null  ||  rteptType10.getUrlname() != null) {
      wptType.getLink().add(createLink(rteptType10.getUrl(), rteptType10.getUrlname()));
    }
    
    if (rteptType10.getSym() != null) {
      wptType.setSym(rteptType10.getSym());
    }
    
    if (rteptType10.getType() != null) {
      wptType.setType(rteptType10.getType());
    }
    
    if (rteptType10.getFix() != null) {
      wptType.setFix(convertFixType10ToFixType(rteptType10.getFix()));
    }
    
    if (rteptType10.getSat() != null) {
      wptType.setSat(rteptType10.getSat());
    }
    
    if (rteptType10.getHdop() != null) {
      wptType.setHdop(rteptType10.getHdop());
    }
    
    if (rteptType10.getVdop() != null) {
      wptType.setVdop(rteptType10.getVdop());
    }
    
    if (rteptType10.getPdop() != null) {
      wptType.setPdop(rteptType10.getPdop());
    }
    
    if (rteptType10.getAgeofdgpsdata() != null) {
      wptType.setAgeofdgpsdata(rteptType10.getAgeofdgpsdata());
    }
    
    if (!rteptType10.getAny().isEmpty()) {
      ExtensionsType extensionsType = GPX_FACTORY.createExtensionsType();
      
      extensionsType.getAny().addAll(rteptType10.getAny());
      
      wptType.setExtensions(extensionsType);
    }
    
    if (rteptType10.getDgpsid() != null) {
      wptType.setDgpsid(rteptType10.getDgpsid());
    }
    
    if (rteptType10.getLat() != null) {
      wptType.setLat(rteptType10.getLat());
    }
    
    if (rteptType10.getLon() != null) {
      wptType.setLon(rteptType10.getLon());
    }    
    
    return wptType;
  }

  private static TrkType convertTrk10ToTrk(goedegep.gpx10.model.TrkType trkType10) {
    TrkType trkType = GPX_FACTORY.createTrkType();
    
    if (trkType10.getName() != null) {
      trkType.setName(trkType10.getName());
    }
    
    if (trkType10.getCmt() != null) {
      trkType.setCmt(trkType10.getCmt());
    }
    
    if (trkType10.getDesc() != null) {
      trkType.setDesc(trkType10.getDesc());
    }
    
    if (trkType10.getSrc() != null) {
      trkType.setSrc(trkType10.getSrc());
    }
    
    if (trkType10.getUrl() != null  || trkType10.getUrlname() != null) {
      trkType.getLink().add(createLink(trkType10.getUrl(), trkType10.getUrlname()));
    }
    
    if (trkType10.getNumber() != null) {
      trkType.setNumber(trkType10.getNumber());
    }
    
    if (!trkType10.getAny().isEmpty()) {
      ExtensionsType extensionsType = GPX_FACTORY.createExtensionsType();
      
      extensionsType.getAny().addAll(trkType10.getAny());
      
      trkType.setExtensions(extensionsType);
    }
    
    for (goedegep.gpx10.model.TrksegType trksegType10: trkType10.getTrkseg()) {
      trkType.getTrkseg().add(convertTrkseg10ToTrkseg(trksegType10));
    }
    
    
    return trkType;
  }

  private static TrksegType convertTrkseg10ToTrkseg(goedegep.gpx10.model.TrksegType trksegType10) {
    TrksegType trksegType = GPX_FACTORY.createTrksegType();
    
    for (goedegep.gpx10.model.TrkptType trkptType10: trksegType10.getTrkpt()) {
      trksegType.getTrkpt().add(convertTrkpt10ToWpt(trkptType10));
    }
    
    return trksegType;
  }

  private static WptType convertTrkpt10ToWpt(TrkptType trkptType10) {
    WptType wptType = GPX_FACTORY.createWptType();
    
    if (trkptType10.getEle() != null) {
      wptType.setEle(trkptType10.getEle());
    }
    
    if (trkptType10.getTime() != null) {
      wptType.setTime(trkptType10.getTime());
    }
    
    if (trkptType10.getCourse() != null) {
      throw new RuntimeException("Element course in a Trkpt is not supported.");
    }
    
    if (trkptType10.getMagvar() != null) {
      wptType.setMagvar(trkptType10.getMagvar());
    }
    
    if (trkptType10.getGeoidheight() != null) {
      wptType.setGeoidheight(trkptType10.getGeoidheight());
    }
    
    if (trkptType10.getName() != null) {
      wptType.setName(trkptType10.getName());
    }
    
    if (trkptType10.getCmt() != null) {
      wptType.setCmt(trkptType10.getCmt());
    }
    
    if (trkptType10.getDesc() != null) {
      wptType.setDesc(trkptType10.getDesc());
    }
    
    if (trkptType10.getSrc() != null) {
      wptType.setSrc(trkptType10.getSrc());
    }
    
    if (trkptType10.getUrl() != null  ||  trkptType10.getUrlname() != null) {
      wptType.getLink().add(createLink(trkptType10.getUrl(), trkptType10.getUrlname()));
    }
    
    if (trkptType10.getSym() != null) {
      wptType.setSym(trkptType10.getSym());
    }
    
    if (trkptType10.getType() != null) {
      wptType.setType(trkptType10.getType());
    }
    
    if (trkptType10.getFix() != null) {
      wptType.setFix(convertFixType10ToFixType(trkptType10.getFix()));
    }
    
    if (trkptType10.getSat() != null) {
      wptType.setSat(trkptType10.getSat());
    }
    
    if (trkptType10.getHdop() != null) {
      wptType.setHdop(trkptType10.getHdop());
    }
    
    if (trkptType10.getVdop() != null) {
      wptType.setVdop(trkptType10.getVdop());
    }
    
    if (trkptType10.getPdop() != null) {
      wptType.setPdop(trkptType10.getPdop());
    }
    
    if (trkptType10.getAgeofdgpsdata() != null) {
      wptType.setAgeofdgpsdata(trkptType10.getAgeofdgpsdata());
    }
    
    if (!trkptType10.getAny().isEmpty()  ||  trkptType10.getSpeed() != null) {
      ExtensionsType extensionsType = GPX_FACTORY.createExtensionsType();
      
      if (!trkptType10.getAny().isEmpty()) {
        extensionsType.getAny().addAll(trkptType10.getAny());
      }
      
      if (trkptType10.getSpeed() != null) {
        extensionsType.setSpeed(trkptType10.getSpeed());
      }
      
      wptType.setExtensions(extensionsType);
    }
    
    if (trkptType10.getDgpsid() != null) {
      wptType.setDgpsid(trkptType10.getDgpsid());
    }
    
    if (trkptType10.getLat() != null) {
      wptType.setLat(trkptType10.getLat());
    }
    
    if (trkptType10.getLon() != null) {
      wptType.setLon(trkptType10.getLon());
    }    
    
    return wptType;
  }
  
}
