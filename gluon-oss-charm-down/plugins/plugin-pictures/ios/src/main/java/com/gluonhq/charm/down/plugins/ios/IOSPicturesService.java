/*
 * Copyright (c) 2016, Gluon
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
package com.gluonhq.charm.down.plugins.ios;

import com.gluonhq.charm.down.plugins.PicturesService;
import com.gluonhq.impl.charm.down.plugins.NestedEventLoopInvoker;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Optional;
import javafx.application.Platform;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

/**
 * Note:Since iOS 10 requires {@code NSCameraUsageDescription} and 
 * {@code NSPhotoLibraryUsageDescription} in pList.
 */
public class IOSPicturesService implements PicturesService {
    
    static {
        IOSPlatform.init();
        System.loadLibrary("Pictures");
        initPictures();
    }
    
    private static ObjectProperty<Image> result;
        
    @Override
    public Optional<Image> takePhoto(boolean savePhoto) {
        result = new SimpleObjectProperty<>();
        takePicture(savePhoto);
        try {
            NestedEventLoopInvoker.enterNestedEventLoop(result);
        } catch (Exception e) {
            System.out.println("GalleryActivity: enterNestedEventLoop failed: " + e);
        }
        return Optional.ofNullable(result.get());
    }

    @Override
    public Optional<Image> loadImageFromGallery() {
        result = new SimpleObjectProperty<>();
        selectPicture();
        try {
            NestedEventLoopInvoker.enterNestedEventLoop(result);
        } catch (Exception e) {
            System.out.println("GalleryActivity: enterNestedEventLoop failed: " + e);
        }
        return Optional.ofNullable(result.get());
    }
    
    // native
    private static native void initPictures(); // init IDs for java callbacks from native
    public static native void takePicture(boolean savePhoto);
    public static native void selectPicture();

    // callback
    public static void setResult(String v) {
        if (v != null && !v.isEmpty()) {
            try {
                byte[] imageBytes = Base64.getDecoder().decode(v.replaceAll("\\s+", "").getBytes());
                result.set(new Image(new ByteArrayInputStream(imageBytes)));
            } catch (Exception ex) {
                System.err.println("Error setResult: " + ex);
            }
        }
        Platform.runLater(() -> {
            try {
                NestedEventLoopInvoker.exitNestedEventLoop(result, null);
            } catch (Exception e) {
                System.out.println("GalleryActivity: exitNestedEventLoop failed: " + e);
            }
        });
    }
}
