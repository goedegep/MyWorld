/*
 * Copyright (c) 2017, Gluon
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

import java.io.File;

/**
 * 
 * The ShareService provides a way to share content (text and/or files) from the
 * current app by using other suitable apps existing in the user device.
 * 
 * <p><b>Example</b></p>
 * <pre>
 * {@code Services.get(ShareService.class).ifPresent(service -> {
 *      service.share("This is the subject", "This is the content of the message");
 *  });}</pre>
 *
 * When sharing files, the Charm Down 
 * <a href="http://docs.gluonhq.com/charm/javadoc/latest/com/gluonhq/charm/down/plugins/StorageService.html">
 * StorageService</a> can be used to create/read the file. Note that on Android, 
 * the file has to be located in a public folder (see 
 * StorageService#getPublicStorage), or sharing it won't be allowed. 
 * 
 * <p><b>Example</b></p>
 * <pre>
 * {@code 
 * File root = Services.get(StorageService.class)
 *           .flatMap(s -> s.getPublicStorage("Documents"))
 *           .orElseThrow(() -> new RuntimeException("Documents not available")); 
 * 
 * // select or create a file within Documents folder:
 * File file = new File(root, "myFile.txt");
 * 
 * // share the file
 * Services.get(ShareService.class).ifPresent(service -> {
 *      service.share("text/plain", file);
 * });
 * }</pre>
 * 
 * <p><b>Android Configuration</b>: none</p>
 * <p><b>iOS Configuration</b>: nothing is required, but if the app is sharing
 * images to the user's local gallery, the key 
 * {@code NSPhotoLibraryUsageDescription} is required in the app's plist file. 
 * 
 * Other similar keys could be required as well.</p>
 * 
 * @since 3.4.0
 */
public interface ShareService {
    
    /**
     * Allows sharing a message, selecting from the suitable apps available in the
     * user device.
     * 
     * @param contentText A string with the content to be shared
     */
    void share(String contentText);
    
    /**
     * Allows sharing a message, selecting from the suitable apps available in the
     * user device. 
     * 
     * Intended to add a subject to the message, for instance, when it is shared 
     * with an email application.
     * 
     * @param subject A string with the subject of the message
     * @param contentText A string with the content to be shared
     */
    void share(String subject, String contentText);
    
    /**
     * Allows sharing a file, selecting from the suitable apps available in the
     * user device. 
     * 
     * Note: On Android, the file has to be located in a public folder (see 
     * StorageService#getPublicStorage), or sharing it won't be allowed. 
     *
     * @param type On Android only, the MIME type of the file. It can be 
     * '&lowast;/&lowast;', but not empty. On iOS it can be null. Usual types are:
     *  <ul><li>application/xml</li>
     *   <li>application/zip</li>
     *   <li>application/pdf</li>
     *   <li>text/css</li>
     *   <li>text/html</li>
     *   <li>text/csv</li>
     *   <li>text/plain</li>
     *   <li>image/png</li>
     *   <li>image/jpeg</li>
     *   <li>image/gif</li>
     *   <li>image/*</li></ul>
     * @param file A valid file to be shared. 
     */
    void share(String type, File file);
    
    /**
     * Allows sharing a file, selecting from the suitable apps available in the
     * user device. A message will be also added.
     * 
     * Note: On Android, the file has to be located in a public folder (see 
     * StorageService#getPublicStorage), or sharing it won't be allowed. 
     *
     * @param subject A string with the subject of the message
     * @param contentText A string with the content to be shared
     * @param type On Android only, the MIME type of the file. It can be 
     * '&lowast;/&lowast;', but not empty. On iOS it can be null. Usual types are:
     *  <ul><li>application/xml</li>
     *   <li>application/zip</li>
     *   <li>application/pdf</li>
     *   <li>text/css</li>
     *   <li>text/html</li>
     *   <li>text/csv</li>
     *   <li>text/plain</li>
     *   <li>image/png</li>
     *   <li>image/jpeg</li>
     *   <li>image/gif</li>
     *   <li>image/*</li></ul>
     * @param file A valid file to be shared. 
     */
    void share(String subject, String contentText, String type, File file);
}
