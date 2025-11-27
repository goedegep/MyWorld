/*
 * Copyright (c) 2018, Gluon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL GLUON BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * This is a modified version of com.gluonhq.maps.tile.TileRetrieverProvider from the Gluon Maps library.
 */
package com.gluonhq.maps.tile;

import com.gluonhq.impl.maps.tile.osm.CachedOsmTileRetriever;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Provider for the {@link TileRetriever} implementation.
 */
public class TileRetrieverProvider {

    /**
     * Singleton instance of the TileRetrieverProvider.
     */
    private static TileRetrieverProvider provider;
    
    /**
     * Returns the singleton instance of the TileRetrieverProvider.
     *
     * @return the TileRetrieverProvider instance
     */
    public static synchronized TileRetrieverProvider getInstance() {
        if (provider == null) {
            provider = new TileRetrieverProvider();
        }
        return provider;
    }

    /**
     * ServiceLoader to load TileRetriever implementations.
     */
    private final ServiceLoader<TileRetriever> loader;

    /**
     * Private constructor to avoid external instantiation.
     */
    private TileRetrieverProvider() {
        loader = ServiceLoader.load(TileRetriever.class);
    }
    
    /**
     * Loads and returns a TileRetriever implementation.
     * It return the first implementation found by the ServiceLoader.
     * If no implementation is found, it defaults to CachedOsmTileRetriever.
     *
     * @return the TileRetriever instance
     */
    public TileRetriever load() {
        Iterator<TileRetriever> tileRetrievers = loader.iterator();
        if (tileRetrievers.hasNext()) {
            return tileRetrievers.next();
        } else {
            return new CachedOsmTileRetriever();
        }
    }
}
