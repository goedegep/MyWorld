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

#include "Position.h"

extern JNIEnv *jEnv;
#define GET_MAIN_JENV \
if (jEnv == NULL) NSLog(@"ERROR: Java has been detached already, but someone is still trying to use it at %s:%s:%d\n", __FUNCTION__, __FILE__, __LINE__);\
JNIEnv *env = jEnv;

#define GLASS_CHECK_EXCEPTION(ENV)                                                 \
do {                                                                               \
jthrowable t = (*ENV)->ExceptionOccurred(ENV);                                 \
if (t) {                                                                       \
(*ENV)->ExceptionClear(ENV);                                               \
/* TODO: (*ENV)->CallStaticVoidMethod(ENV, jApplicationClass, jApplicationReportException, t);      */         \
};                                                                             \
} while (0)


static int positionInited = 0;

// Position
jclass mat_jPositionServiceClass;
jmethodID mat_jPositionService_setLocation = 0;
Position *_position;

JNIEXPORT void JNICALL Java_com_gluonhq_charm_down_plugins_ios_IOSPositionService_initPosition
(JNIEnv *env, jclass jClass)
{
    if (positionInited)
    {
        return;
    }
    positionInited = 1;
    
    mat_jPositionServiceClass = (*env)->NewGlobalRef(env, (*env)->FindClass(env, "com/gluonhq/charm/down/plugins/ios/IOSPositionService"));
    mat_jPositionService_setLocation = (*env)->GetMethodID(env, mat_jPositionServiceClass, "setLocation", "(DD)V");
    GLASS_CHECK_EXCEPTION(env);

    _position = [[Position alloc] init];
}

JNIEXPORT void JNICALL Java_com_gluonhq_charm_down_plugins_ios_IOSPositionService_startObserver
(JNIEnv *env, jclass jClass)
{
    dispatch_async(dispatch_get_main_queue(), ^{
        [_position startObserver];
    });
    return;   
}

JNIEXPORT void JNICALL Java_com_gluonhq_charm_down_plugins_ios_IOSPositionService_stopObserver
(JNIEnv *env, jclass jClass)
{
    [_position stopObserver];
    return;   
}

void setLocation(CLLocation *newLocation) {
    GET_MAIN_JENV;
    if (newLocation)
    {
        double lat = newLocation.coordinate.latitude;
        double lon = newLocation.coordinate.longitude;
        (*env)->CallVoidMethod(env, mat_jPositionServiceClass, mat_jPositionService_setLocation, lat, lon);
    }

}

@implementation Position

- (void)startObserver 
{

    self.locationManager = [[CLLocationManager alloc] init];
    self.locationManager.delegate = self;
    // TODO: Allow user change the default settings:
    self.locationManager.distanceFilter = 10.0f;
    self.locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters;

    if ([[[UIDevice currentDevice] systemVersion] floatValue] >= 8.0)
    {
        // try to save battery by using GPS only when app is used:
        [self.locationManager requestWhenInUseAuthorization];
    }

    NSLog(@"Start updating location");
    [self.locationManager startUpdatingLocation];

    // Request a location update
    [self.locationManager requestLocation];
  
}

- (void)stopObserver 
{
    NSLog(@"Stop updating location");
    [self.locationManager stopUpdatingLocation];
}

- (void)locationManager:(CLLocationManager *)manager didUpdateLocations:(NSArray *)locations {
    CLLocation *newLocation = [locations lastObject];
    NSLog(@"NewLocation: %f, %f", newLocation.coordinate.latitude, newLocation.coordinate.longitude);
    
    if (newLocation.horizontalAccuracy < 0) 
    {
        NSLog(@"iOS location update, horizontal accuracy too small: %.2f", newLocation.horizontalAccuracy);
        // return;
    }

    NSTimeInterval interval = [newLocation.timestamp timeIntervalSinceNow];
    if (interval < -5) 
    {
        NSLog(@"iOS location update, time interval to large (probably cached): %.2f", interval);
        // return;
    }

    setLocation(newLocation);
}

- (void)locationManager:(CLLocationManager *)manager didFailWithError:(NSError *)error
{
    if ([CLLocationManager authorizationStatus] == kCLAuthorizationStatusDenied)
    {
        NSLog(@"User has denied location services");
    } 
    else 
    {
        NSLog(@"Location manager did fail with error: %@", error);
        switch([error code])
        {
            case kCLErrorNetwork: // general, network-related error
            {
                NSLog(@"ErrorNetwork");
            }
            break;
            case kCLErrorDenied:
            {
                NSLog(@"ErrorDenied");
            }
            break;
            case kCLErrorLocationUnknown:
            {
                NSLog(@"ErrorLocationUnknown");
            }
            break;
            default:
            {
                NSLog(@"Unknown error: %@", error);    
            }
            break;
        }
    }
}

@end
