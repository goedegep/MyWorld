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
package com.gluonhq.charm.down.plugins;

import java.util.Optional;
import javafx.scene.image.Image;

/**
 * The picture service allows the developer to load a picture from the device's local file
 * system or from a picture taken directly using the device's camera.
 *
 * <p><b>Example</b></p>
 * <pre>
 * {@code ImageView imageView = new ImageView();
 *  Services.get(PicturesService.class).ifPresent(service -> {
 *      service.takePhoto(false).ifPresent(image -> imageView.setImage(image));
 *  });}</pre>
 *
 * <p><b>Android Configuration</b></p>
 * <p>The permission <code>android.permission.CAMERA</code> needs to be added as well as the permissions
 * <code>android.permission.READ_EXTERNAL_STORAGE</code> and <code>android.permission.WRITE_EXTERNAL_STORAGE</code>
 * to be able to read and write images.</p>
 * <pre>
 * {@code <manifest ...>
 *    <uses-permission android:name="android.permission.CAMERA"/>
 *    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 *    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 *    <activity android:name="com.gluonhq.impl.charm.down.plugins.android.PermissionRequestActivity" />
 * </manifest>}</pre>
 *
 * <p><b>iOS Configuration</b></p>
 * <p>The following keys are required:</p>
 * <pre>
 * {@code <key>NSCameraUsageDescription</key>
 *  <string>Reason to use Camera Service (iOS 10+)</string>
 *  <key>NSPhotoLibraryUsageDescription</key>
 *  <string>Reason to use Photo Library (iOS 10+)</string>}</pre>
 *
 * @since 3.0.0
 */
public interface PicturesService {

    /**
     * Use the device's camera to take a photo, and retrieve an {@link Image}. 
     * It can be saved as well in the device's public album.
     * 
     * @param savePhoto if true, image is saved to public album
     * @return an Optional with the Image or empty if it failed or it was cancelled
     */
    Optional<Image> takePhoto(boolean savePhoto); 
    
    /**
     * Retrieve an image from the device's gallery of images
     * @return an Optional with the Image or empty if it failed or it was cancelled
     */
    Optional<Image> loadImageFromGallery();
    
}
