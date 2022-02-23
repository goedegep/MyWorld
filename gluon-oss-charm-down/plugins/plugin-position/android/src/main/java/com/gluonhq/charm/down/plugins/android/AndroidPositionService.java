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
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.down.plugins.Position;
import com.gluonhq.charm.down.plugins.PositionService;
import com.gluonhq.charm.down.plugins.LifecycleEvent;
import static com.gluonhq.impl.charm.down.plugins.android.AndroidApplication.getApplication;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gluonhq.impl.charm.down.plugins.android.AndroidLooperTask;
import com.gluonhq.impl.charm.down.plugins.android.PermissionRequestActivity;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

/**
 * An implementation of the
 * {@link PositionService PositionService} for the
 * Android platform.
 * 
 * Requires ACCESS_FINE_LOCATION permission:
 * {@code <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 * 
 * <activity android:name="com.gluonhq.impl.charm.down.plugins.android.PermissionRequestActivity" />
 * }
 */
public class AndroidPositionService implements PositionService, LocationListener {

    private static final Logger LOG = Logger.getLogger(AndroidPositionService.class.getName());

    /**
     * The minimum number of milliseconds between location updates.
     */
    private static final long MIN_TIME = 5000;
    /**
     * The minimum number of meters between location updates.
     */
    private static final float MIN_DISTANCE = 15.0f;

    private final ReadOnlyObjectWrapper<Position> positionProperty = new ReadOnlyObjectWrapper<>();

    private LocationManager locationManager;
    private String locationProvider;

    private AndroidLooperTask looperTask = null;

    public AndroidPositionService() {
        boolean gpsEnabled = PermissionRequestActivity.verifyPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        if (! gpsEnabled) {
            LOG.log(Level.WARNING, "GPS disabled");
        } else {
            initialize();
        }
    }    
        
    private void initialize() {    
        Context activityContext = getApplication();

        Object systemService = activityContext.getSystemService(Activity.LOCATION_SERVICE);
        locationManager = (LocationManager) systemService;

        List<String> locationProviders = locationManager.getAllProviders();
        if (locationProviders == null || locationProviders.isEmpty()) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else {
            LOG.log(Level.INFO, String.format("Available location providers on this device: %s.", locationProviders.toString()));
            if (locationProviders.contains(LocationManager.GPS_PROVIDER)) {
                locationProvider = LocationManager.GPS_PROVIDER;
            } else if (locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER;
            } else if (locationProviders.contains(LocationManager.PASSIVE_PROVIDER)) {
                locationProvider = LocationManager.PASSIVE_PROVIDER;
            } else {
                locationProvider = locationProviders.get(0);
            }
        }
        LOG.log(Level.INFO, String.format("Picked %s as location provider.", locationProvider));

        boolean locationProviderEnabled = locationManager.isProviderEnabled(locationProvider);
        if (!locationProviderEnabled) {
            LOG.log(Level.INFO, String.format("Location provider %s is not enabled, starting intent to ask user to activate the location provider.", locationProvider));
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activityContext.startActivity(intent);
        }

        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            LOG.log(Level.INFO, String.format("Last known location for provider %s: %f / %f", locationProvider, lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));

            Platform.runLater(() -> {
                Position lastKnownPosition = new Position(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                positionProperty.set(lastKnownPosition);
            });
        }

        Services.get(LifecycleService.class).ifPresent(l -> {
            l.addListener(LifecycleEvent.PAUSE, () -> quitLooperTask());
            l.addListener(LifecycleEvent.RESUME, () -> createLooperTask());
        });
        createLooperTask();
    }

    @Override
    public ReadOnlyObjectProperty<Position> positionProperty() {
        return positionProperty.getReadOnlyProperty();
    }

    @Override
    public Position getPosition() {
        return positionProperty.get();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            LOG.log(Level.INFO, String.format("Android location changed: %f / %f", location.getLatitude(), location.getLongitude()));

            Platform.runLater(() -> {
                Position newPosition = new Position(location.getLatitude(), location.getLongitude());
                positionProperty.set(newPosition);
            });
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LOG.log(Level.INFO, String.format("Status for LocationProvider %s changed to %d.", provider, status));
    }

    @Override
    public void onProviderEnabled(String provider) {
        LOG.log(Level.INFO, String.format("LocationProvider %s was enabled by the user, starting looper task.", provider));

        if (provider.equals(locationProvider) && looperTask == null) {
            createLooperTask();
        }
    }

    private void createLooperTask() {
        LOG.log(Level.INFO, String.format("Creating LooperTask to request location updates every %d milliseconds or %f meters.", MIN_TIME, MIN_DISTANCE));

        looperTask = new AndroidLooperTask() {

            @Override
            public void execute() {
                locationManager.requestLocationUpdates(locationProvider, MIN_TIME, MIN_DISTANCE, AndroidPositionService.this);
            }
        };

        Thread thread = new Thread(looperTask);
        thread.setDaemon(true);
        thread.start();
    }
    
    private void quitLooperTask() {
        if (looperTask != null) {
            looperTask.quit();
            looperTask = null;
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        LOG.log(Level.INFO, String.format("LocationProvider %s was disabled by the user, quitting looper task.", provider));

        if (provider.equals(locationProvider)) {
            quitLooperTask();
        }
    }

}
