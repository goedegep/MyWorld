/*
 * Copyright (c) 2016, 2017 Gluon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

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
 */
package com.gluonhq.charm.down.plugins.desktop;

import com.gluonhq.charm.down.plugins.DisplayService;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DesktopDisplayService implements DisplayService {

    private static final Logger LOG = Logger.getLogger(DesktopDisplayService.class.getName());

    private final Dimension2D dimensions;

    public DesktopDisplayService() {
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        dimensions = new Dimension2D(bounds.getWidth(), bounds.getHeight());
    }
    
    @Override
    public boolean isPhone() {
        return false;
    }

    @Override
    public boolean isTablet() {
        return "tablet".equals(System.getProperty("charm-desktop-form"));
    }

    @Override
    public boolean isDesktop() {
        return true;
    }
    
    /**
     * Retrieve the dimension of the primary screen based on its bounds 
     * @return Dimension of the Screen
     */
    @Override
    public Dimension2D getScreenResolution() {
        return dimensions;
    }

    @Override
    public Dimension2D getDefaultDimensions() {
        return isTablet() ? new Dimension2D(900, 600) : new Dimension2D(335, 600);
    }

    /**
     * Returns the screen scale of the primary screen
     * @return the screen scale
     */
    @Override
    public float getScreenScale() {
        if (renderScaleMethod != null) {
            try {
                return (float) renderScaleMethod.invoke(Screen.getPrimary());
            } catch (IllegalAccessException e) {
                LOG.log(Level.SEVERE, "Could not get render scale from screen.", e);
            } catch (InvocationTargetException e) {
                LOG.log(Level.SEVERE, "Could not get render scale from screen.", e);
            }
        } else if (outputScaleXMethod != null && outputScaleYMethod != null) {
            try {
                double outputScaleX = (double) outputScaleXMethod.invoke(Screen.getPrimary());
                double outputScaleY = (double) outputScaleYMethod.invoke(Screen.getPrimary());
                return (float) Math.min(outputScaleX, outputScaleY);
            } catch (IllegalAccessException e) {
                LOG.log(Level.SEVERE, "Could not get output scale from screen.", e);
            } catch (InvocationTargetException e) {
                LOG.log(Level.SEVERE, "Could not get output scale from screen.", e);
            }
        }

        return 1.0f;
    }

    @Override
    public boolean isScreenRound() {
        return false;
    }

    private static Method renderScaleMethod = null;
    private static Method outputScaleXMethod = null;
    private static Method outputScaleYMethod = null;
    static {
        try {
            outputScaleXMethod = Screen.class.getMethod("getOutputScaleX");
            outputScaleYMethod = Screen.class.getMethod("getOutputScaleY");
        } catch (NoSuchMethodException e) {
            log("Could not find method getOutputScaleX/Y on javafx.stage.Screen", e);
        } catch (SecurityException e) {
            log("Could not find method getOutputScaleX/Y on javafx.stage.Screen", e);
        }

        if (outputScaleXMethod == null || outputScaleYMethod == null) {
            try {
                renderScaleMethod = Screen.class.getDeclaredMethod("getRenderScale");
                renderScaleMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                log("Could not find method getRenderScale on javafx.stage.Screen", e);
            } catch (SecurityException e) {
                log("Could not find method getRenderScale on javafx.stage.Screen", e);
            }
        }

        if ((outputScaleXMethod == null || outputScaleYMethod == null) && renderScaleMethod == null) {
            LOG.log(Level.SEVERE, "Failed to detect valid render scale method. Set log level for this logger to FINE for more details.");
        }
    }

    private static void log(String message, Throwable cause) {
        LOG.log(Level.FINE, message);
        if (LOG.isLoggable(Level.FINE)) {
            cause.printStackTrace();
        }
    }
}
