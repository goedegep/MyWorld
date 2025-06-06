/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.fontbox.ttf.gsub;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.apache.fontbox.ttf.CmapLookup;
import org.apache.fontbox.ttf.model.GsubData;
import org.apache.fontbox.ttf.model.ScriptFeature;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

/**
 * 
 * Latin-specific implementation of GSUB system
 * 
 * @author Palash Ray
 * @author Tilman Hausherr
 *
 */
public class GsubWorkerForLatin implements GsubWorker
{
    private static final Logger LOG = Logger.getLogger(GsubWorkerForLatin.class.getName());

    /**
     * This sequence is very important. This has been taken from <a href=
     * "https://docs.microsoft.com/en-us/typography/script-development/standard">https://docs.microsoft.com/en-us/typography/script-development/standard</a>
     */
    private static final List<String> FEATURES_IN_ORDER = Arrays.asList("ccmp", "liga", "clig");

    private final CmapLookup cmapLookup;
    private final GsubData gsubData;

    GsubWorkerForLatin(CmapLookup cmapLookup, GsubData gsubData)
    {
        this.cmapLookup = cmapLookup;
        this.gsubData = gsubData;
    }

    @Override
    public List<Integer> applyTransforms(List<Integer> originalGlyphIds)
    {
        List<Integer> intermediateGlyphsFromGsub = originalGlyphIds;

        for (String feature : FEATURES_IN_ORDER)
        {
            if (!gsubData.isFeatureSupported(feature))
            {
                LOG.info(MessageFormat.format("the feature {} was not found", feature));
                continue;
            }

            LOG.info(MessageFormat.format("applying the feature {0}", feature));

            ScriptFeature scriptFeature = gsubData.getFeature(feature);

            intermediateGlyphsFromGsub = applyGsubFeature(scriptFeature,
                    intermediateGlyphsFromGsub);
        }

        return Collections.unmodifiableList(intermediateGlyphsFromGsub);
    }

    private List<Integer> applyGsubFeature(ScriptFeature scriptFeature,
            List<Integer> originalGlyphs)
    {
        if (scriptFeature.getAllGlyphIdsForSubstitution().isEmpty())
        {
            LOG.info(MessageFormat.format("getAllGlyphIdsForSubstitution() for {0} is empty",
                        scriptFeature.getName()));
            return originalGlyphs;
        }
        
        GlyphArraySplitter glyphArraySplitter = new GlyphArraySplitterRegexImpl(
                scriptFeature.getAllGlyphIdsForSubstitution());

        List<List<Integer>> tokens = glyphArraySplitter.split(originalGlyphs);
        List<Integer> gsubProcessedGlyphs = new ArrayList<>();

        for (List<Integer> chunk : tokens)
        {
            if (scriptFeature.canReplaceGlyphs(chunk))
            {
                // gsub system kicks in, you get the glyphId directly
                List<Integer> replacementForGlyphs = scriptFeature.getReplacementForGlyphs(chunk);
                gsubProcessedGlyphs.addAll(replacementForGlyphs);
            }
            else
            {
                gsubProcessedGlyphs.addAll(chunk);
            }
        }

        LOG.info(MessageFormat.format("originalGlyphs: {0}, gsubProcessedGlyphs: {1}", originalGlyphs, gsubProcessedGlyphs));

        return gsubProcessedGlyphs;
    }
}
