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

import java.util.Date;
import java.util.List;

/**
 *
 * @author Thomas
 */
public abstract class GPXMeasurable extends GPXLineItem {
    public abstract List<GPXMeasurable> getGPXMeasurables();
    
    public GPXMeasurable() {
        super();
    }

    /**
     * Calculates the getLength of the track
     * 
     * @return the tracks collection's getLength in meters
     */
    public double getLength() {
        double length = 0.0;

        final List<GPXMeasurable> gpxMeasurables = getGPXMeasurables();
        for (int i = 0; i < gpxMeasurables.size(); i++) {           
            length += gpxMeasurables.get(i).getLength();
        }

        return length;
    }
    
    /**
     * Calculates the total ascent in the track.
     * 
     * <p>The total ascent of the track is calculated by comparing each
     * of the track's segments  with their predecessors. If the
     * elevation of a segments is higher than the elevation of the
     * predecessor, the total ascent is increased accordingly.</p>
     * 
     * @see Track#cumulativeDescent()
     * @return the tracks's total ascent in meters
     */
    public double getCumulativeAscent() {
        double ascent = 0.0;

        final List<GPXMeasurable> gpxMeasurables = getGPXMeasurables();
        for (int i = 0; i < gpxMeasurables.size(); i++) {
            ascent += gpxMeasurables.get(i).getCumulativeAscent();
        }

        return ascent;
    }

    /**
     * Calculates the total descent in the track.
     * 
     * <p>The total descent of the track is calculated by comparing each
     * of the track's segments with their predecessors. If the
     * elevation of a segment is lower than the elevation of the
     * predecessor, the total descent is increased accordingly.</p>
     * 
     * @see Track#cumulativeAscent()
     * @return the tracks's total descent in meters
     */
    protected double getCumulativeDescent() {
        double descent = 0.0;

        final List<GPXMeasurable> gpxMeasurables = getGPXMeasurables();
        for (int i = 0; i < gpxMeasurables.size(); i++) {
            descent += gpxMeasurables.get(i).getCumulativeDescent();
        }

        return descent;
    }

    /**
     * Returns the point in time when the track was entered
     * 
     * <p>Usually this is the time stamp of the segment that was added
     * first to the track.</p>
     * 
     * @see Track#endTime()
     * @return the point in time when the track was entered 
     */
    protected Date getStartTime() {
        Date result = null;

        final List<GPXMeasurable> gpxMeasurables = getGPXMeasurables();
        for (int i = 0; i < gpxMeasurables.size(); i++) {
            GPXMeasurable measurable = gpxMeasurables.get(i);
            Date startingTime = measurable.getStartTime();

            if (startingTime != null) {
                if (result == null || startingTime.before(result)) {
                    result = startingTime;
                }
            }
        }

        return result;
    }

    /**
     * Returns the point in time when the track was left
     * 
     * <p>Usually this is the time stamp of the segment that was added
     * last to the track.</p>
     * 
     * @see Track#startingTime
     * @return the point in time when the track was left
     */
    protected Date getEndTime() {
        Date result = null;

        final List<GPXMeasurable> gpxMeasurables = getGPXMeasurables();
        for (int i = 0; i < gpxMeasurables.size(); i++) {
            GPXMeasurable measurable = gpxMeasurables.get(i);
            Date endTime = measurable.getEndTime();

            if (endTime != null) {
                if (result == null || endTime.after(result)) {
                    result = endTime;
                }
            }
        }

        return result;
    }

    /**
     * @return the duration
     */
    @Override
    public long getDuration() {
        if (getEndTime() != null && getStartTime() != null) {
            return getEndTime().getTime() - getStartTime().getTime();
        } else {
            return 0;
        }
    }
}