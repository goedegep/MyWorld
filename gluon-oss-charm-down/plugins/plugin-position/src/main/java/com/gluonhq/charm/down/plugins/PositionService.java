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

import javafx.beans.property.ReadOnlyObjectProperty;

/**
 * The PositionService provides details about a device's current location on
 * earth.
 *
 * <p>The PositionService provides a read-only {@link #positionProperty() position property}
 * that will be updated at regular intervals by the underlying platform implementation.
 * A user of the PositionService can listen to changes of the position by registering a
 * {@link javafx.beans.value.ChangeListener ChangeListener} to the
 * {@link #positionProperty() position property}.</p>
 *
 * <p><b>Example</b></p>
 * <pre>
 * {@code Services.get(PositionService.class).ifPresent(service -> {
 *      Position position = service.getPosition();
 *      System.out.printf("Current position: %.5f, %.5f",
 *              position.getLatitude(), position.getLongitude());
 *  });}</pre>
 *
 * <p><b>Android Configuration</b></p>
 * <p>The permission <code>android.permission.ACCESS_FINE_LOCATION</code> needs to be added.</p>
 * <pre>
 * {@code <manifest ...>
 *    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 *    ...
 *    <activity android:name="com.gluonhq.impl.charm.down.plugins.android.PermissionRequestActivity" />
 *  </manifest>}</pre>
 *
 * <p><b>iOS Configuration</b></p>
 * <p>The following keys are required:</p>
 * <pre>
 * {@code <key>NSLocationUsageDescription</key>
 *  <string>Reason to use Location Service (iOS 6+)</string>
 *  <key>NSLocationWhenInUseUsageDescription</key>
 *  <string>Reason to use Location Service (iOS 8+)</string>}</pre>
 *
 * @since 3.0.0
 */
public interface PositionService {

    /**
     * A read-only property containing information about the device's current
     * location on earth. The property can contain a <code>null</code> object
     * when the position of the device could be determined.
     *
     * @return a read-only object property containing the device's current location
     */
    ReadOnlyObjectProperty<Position> positionProperty();

    /**
     * The current position on earth of the device. Can return <code>null</code>
     * when the position of the device could not be determined, for instance
     * when the GPS has been turned off.
     *
     * @return the current position of the device
     */
    Position getPosition();
}
