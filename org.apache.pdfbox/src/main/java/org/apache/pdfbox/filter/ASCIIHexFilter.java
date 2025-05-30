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
package org.apache.pdfbox.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.logging.Logger;

//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.util.Hex;

/**
 * Decodes data encoded in an ASCII hexadecimal form, reproducing the original binary data.
 *
 * @author Ben Litchfield
 */
final class ASCIIHexFilter extends Filter
{
    private static final Logger LOG = Logger.getLogger(ASCIIHexFilter.class.getName());

    private static final int[] REVERSE_HEX = {
      /*   0 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /*  10 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /*  20 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /*  30 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /*  40 */  -1, -1, -1, -1, -1, -1, -1, -1,  0,  1,
      /*  50 */   2,  3,  4,  5,  6,  7,  8,  9, -1, -1,
      /*  60 */  -1, -1, -1, -1, -1, 10, 11, 12, 13, 14,
      /*  70 */  15, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /*  80 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /*  90 */  -1, -1, -1, -1, -1, -1, -1, 10, 11, 12,
      /* 100 */  13, 14, 15, -1, -1, -1, -1, -1, -1, -1,
      /* 110 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 120 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 130 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 140 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 150 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 160 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 170 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 180 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 190 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 200 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 210 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 220 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 230 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 240 */  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      /* 250 */  -1, -1, -1, -1, -1, -1
    };

    @Override
    public DecodeResult decode(InputStream encoded, OutputStream decoded,
                                         COSDictionary parameters, int index) throws IOException
    {
        int value, firstByte, secondByte;
        while ((firstByte = encoded.read()) != -1)
        {
            // always after first char
            while (isWhitespace(firstByte))
            {
                firstByte = encoded.read();
            }
            if (firstByte == -1 || isEOD(firstByte))
            {
                break;
            }
       
            if (REVERSE_HEX[firstByte] == -1)
            {
                LOG.severe(MessageFormat.format("Invalid hex, int: {0} char: {1}", firstByte, (char) firstByte));
            }
            value = REVERSE_HEX[firstByte] * 16;
            secondByte = encoded.read();
       
            if (secondByte == -1 || isEOD(secondByte)) 
            {
                // second value behaves like 0 in case of EOD
                decoded.write(value);
                break;
            }
            if (REVERSE_HEX[secondByte] == -1)
            {
                LOG.severe(MessageFormat.format("Invalid hex, int: {0} char: {1}", secondByte, (char) secondByte));
            }
            value += REVERSE_HEX[secondByte];
            decoded.write(value);
        }
        decoded.flush();
        return new DecodeResult(parameters);
    }

    // whitespace
    //   0  0x00  Null (NUL)
    //   9  0x09  Tab (HT)
    //  10  0x0A  Line feed (LF)
    //  12  0x0C  Form feed (FF)
    //  13  0x0D  Carriage return (CR)
    //  32  0x20  Space (SP)
    private static boolean isWhitespace(int c)
    {
        switch (c)
        {
        case 0:
        case 9:
        case 10:
        case 12:
        case 13:
        case 32:
            return true;
        default:
            return false;
        }
    }

    private static boolean isEOD(int c)
    {
        return c == '>';
    }

    @Override
    public void encode(InputStream input, OutputStream encoded, COSDictionary parameters)
        throws IOException
    {
        int byteRead;
        while ((byteRead = input.read()) != -1)
        {
            Hex.writeHexByte((byte)byteRead, encoded);
        }
        encoded.flush();
    }
}
