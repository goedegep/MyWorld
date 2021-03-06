/*
 * Copyright (c) 2014ff Thomas Feuster
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package goedegep.gluonmaps.gpx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Thomas
 */
public abstract class GPXLineItem {
    public static enum GPXLineItemType {
        GPXFile,
        GPXTrack,
        GPXTrackSegment,
        GPXWaypoint;
        
        public static boolean isParentTypeOf(final GPXLineItemType parent, final GPXLineItemType item) {
            return parent.ordinal() == item.ordinal()-1;
        }
        
        public static boolean isChildTypeOf(final GPXLineItemType child, final GPXLineItemType item) {
            return child.ordinal() == item.ordinal()+1;
        }
        
        public static boolean isSameTypeAs(final GPXLineItemType child, final GPXLineItemType item) {
            return child.ordinal() == item.ordinal();
        }
    }
    
    public static enum GPXLineItemData {
        Type,
        Name,
        Start,
        Duration,
        Length,
        Speed,
        CumAscent,
        CumDescent,
        Position,
        Date,
        DistToPrev,
        Elevation,
        ElevationDiffToPrev,
        Slope,
        NoItems
    }
    
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z"); 
    
    private boolean hasUnsavedChanges = false;
    private int myNumber;

    public GPXLineItem() {
        super();
    }
    
    public boolean hasUnsavedChanges() {
        // do I or any of my children have unsaved changes?
        if (hasUnsavedChanges) {
            return true;
        }
        
        for (GPXLineItem child : getChildren()) {
            if (child.hasUnsavedChanges()) {
                return true;
            }
        }
        
        return false;
    }
    public void setHasUnsavedChanges() {
        this.hasUnsavedChanges = true;
    }
    public void resetHasUnsavedChanges() {
        // reset me and my children
        this.hasUnsavedChanges = false;

        for (GPXLineItem child : getChildren()) {
            child.resetHasUnsavedChanges();
        }
    }

    public Integer getNumber() {
        return myNumber;
    }

    public void setNumber(Integer number) {
        myNumber = number;
    }

    protected static String DURATION_FORMAT = "%1$02d:%2$02d:%3$02d"; 
    
    public abstract GPXLineItemType getType();
    public abstract String getName();
    public abstract void setName(final String name);
    public abstract String getData(final GPXLineItem.GPXLineItemData gpxLineItemData);
    public abstract Date getDate();
    
    // get associated GPXLineItemType - could be children or parents
    public abstract GPXFile getGPXFile();
    public abstract List<GPXTrack> getGPXTracks();
    public abstract List<GPXTrackSegment> getGPXTrackSegments();
    public abstract List<GPXWaypoint> getGPXWaypoints();

    public abstract GPXLineItem getParent();
    public abstract void setParent(final GPXLineItem parent);

    public abstract List<GPXLineItem> getChildren();
    public abstract void setChildren(final List<GPXLineItem> children);
    protected <T extends GPXLineItem> List<T> castChildren(final Class<T> clazz, final List<GPXLineItem> children) {
        @SuppressWarnings("unchecked")
        final List<T> gpxChildren = children.stream().
                map((GPXLineItem child) -> {
                    assert child.getClass().isInstance(clazz);
                    child.setParent(this);
                    return (T) child;
                }).collect(Collectors.toList());
        
        return gpxChildren;
    }
    
    protected abstract long getDuration();
    
    protected String getDurationAsString() {
        // http://stackoverflow.com/questions/17940200/how-to-find-the-duration-of-difference-between-two-dates-in-java
        final long diff = getDuration();
        // TFE, 20170716: negative differences are only shown for hours
        final long diffSeconds = Math.abs(diff / 1000 % 60);
        final long diffMinutes = Math.abs(diff / (60 * 1000) % 60);
        final long diffHours = diff / (60 * 60 * 1000);
        return String.format(DURATION_FORMAT, diffHours, diffMinutes, diffSeconds);
    }
    
    public void acceptVisitor(final IGPXLineItemVisitor visitor) {
        if (visitor.deepthFirst()) {
            visitChildren(visitor);
            visitMe(visitor);
        } else {
            visitMe(visitor);
            visitChildren(visitor);
        }
    }
    protected abstract void visitMe(final IGPXLineItemVisitor visitor);
    private void visitChildren(final IGPXLineItemVisitor visitor) {
        for (GPXLineItem child : getChildren()) {
            child.acceptVisitor(visitor);
        }
    }
}