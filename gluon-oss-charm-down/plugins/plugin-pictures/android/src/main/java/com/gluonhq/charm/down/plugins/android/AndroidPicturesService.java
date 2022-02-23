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
package com.gluonhq.charm.down.plugins.android;

import android.Manifest;
import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import com.gluonhq.charm.down.plugins.PicturesService;
import com.gluonhq.impl.charm.down.plugins.NestedEventLoopInvoker;
import com.gluonhq.impl.charm.down.plugins.android.FileUtils;
import com.gluonhq.impl.charm.down.plugins.android.PermissionRequestActivity;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafxports.android.FXActivity;

/**
 * Android implementation of the PicturesService
 */
public class AndroidPicturesService implements PicturesService {

    private static final Logger LOG = Logger.getLogger(AndroidPicturesService.class.getName());

    private static final FXActivity ACTIVITY = FXActivity.getInstance();

    private static final int SELECT_PICTURE = 1;
    private static final int TAKE_PICTURE = 2;    

    private static ObjectProperty<Image> result;
        
    @Override
    public Optional<Image> takePhoto(boolean savePhoto) {
        if (! verifyPermissions()) {
            return Optional.empty();
        }
        result = new SimpleObjectProperty<>();
        takePicture(savePhoto);
        return Optional.ofNullable(result.get());
    }
    
    @Override
    public Optional<Image> loadImageFromGallery() {
        if (! verifyPermissions()) {
            return Optional.empty();
        }
        result = new SimpleObjectProperty<>();
        selectPicture();
        return Optional.ofNullable(result.get());
    }
    
    private void takePicture(boolean savePhoto) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File photo = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES), "IMG_"+ timeStamp + ".jpg");
        Uri photoUri = Uri.fromFile(photo);
        
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

        if (ACTIVITY == null) {
            LOG.log(Level.WARNING, "FXActivity not found. This service is not allowed when "
                            + "running in background mode or from wearable");
            return;
        }

        ACTIVITY.setOnActivityResultHandler((requestCode, resultCode, data) -> {
            if (requestCode == TAKE_PICTURE) {
                if (resultCode == RESULT_OK) {
                    String selectedImagePath = FileUtils.getPath(ACTIVITY, photoUri);
                    final File file = new File(selectedImagePath);
                    Uri converted = Uri.fromFile(file);
                    final Image image = new Image(converted.toString());
                    if (!savePhoto) {
                        file.delete();
                    }
                    // media scanner to rescan DIRECTORY_PICTURES after an image is saved/deleted
                    MediaScannerConnection.scanFile(ACTIVITY, new String[] { file.toString() }, null, null);
                    // set image
                    result.set(image);
                }
                Platform.runLater(() -> {
                    try {
                        NestedEventLoopInvoker.exitNestedEventLoop(result, null);
                    } catch (Exception e) {
                        System.out.println("GalleryActivity: exitNestedEventLoop failed: " + e);
                    }
                });
            }
        });

        // check for permissions
        if (intent.resolveActivity(ACTIVITY.getPackageManager()) != null) {
            ACTIVITY.startActivityForResult(intent, TAKE_PICTURE);
            try {
                NestedEventLoopInvoker.enterNestedEventLoop(result);
            } catch (Exception e) {
                System.out.println("GalleryActivity: enterNestedEventLoop failed: " + e);
            } 
        } else {
            System.out.println("GalleryActivity: resolveActivity failed");
        }
    }
    
    private void selectPicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        
        if (ACTIVITY == null) {
            LOG.log(Level.WARNING, "FXActivity not found. This service is not allowed when "
                            + "running in background mode or from wearable");
            return;
        }

        ACTIVITY.setOnActivityResultHandler((requestCode, resultCode, data) -> {
            if (requestCode == SELECT_PICTURE) { 
                if (resultCode == RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    String selectedImagePath = FileUtils.getPath(ACTIVITY, selectedImageUri);
                    if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
                        Uri converted = Uri.fromFile(new File(selectedImagePath));
                        result.set(new Image(converted.toString()));
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
        });
        
        // check for permissions
        if (intent.resolveActivity(ACTIVITY.getPackageManager()) != null) {
            ACTIVITY.startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
            try {
                NestedEventLoopInvoker.enterNestedEventLoop(result);
            } catch (Exception e) {
                System.out.println("GalleryActivity: enterNestedEventLoop failed: " + e);
            }
        } else {
            System.out.println("GalleryActivity: resolveActivity failed");
        }
    }

    private boolean verifyPermissions() {
        boolean cameraEnabled = PermissionRequestActivity.verifyPermissions(Manifest.permission.CAMERA, 
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (! cameraEnabled) {
            LOG.log(Level.WARNING, "Camera disabled");
        }
        return cameraEnabled;
    }

}
