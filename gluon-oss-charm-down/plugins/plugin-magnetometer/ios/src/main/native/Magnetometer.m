/*
 * Copyright (c) 2016, Gluon
 *
 * This program is fr_aee software: you can redistribute it and/or modify
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

#include "Magnetometer.h"

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


static int MagnetometerInited = 0;

// Magnetometer
jclass mat_jMagnetometerServiceClass;
jmethodID mat_jMagnetometerService_notifyReading = 0;
Magnetometer *_magnetometer;
double magRate = 0.1;

JNIEXPORT void JNICALL Java_com_gluonhq_charm_down_plugins_ios_IOSMagnetometerService_initMagnetometer
(JNIEnv *env, jclass jClass)
{
    if (MagnetometerInited)
    {
        return;
    }
    MagnetometerInited = 1;
    
    mat_jMagnetometerServiceClass = (*env)->NewGlobalRef(env, (*env)->FindClass(env, "com/gluonhq/charm/down/plugins/ios/IOSMagnetometerService"));
    mat_jMagnetometerService_notifyReading = (*env)->GetMethodID(env, mat_jMagnetometerServiceClass, "notifyReading", "(DDDDDDD)V");
    GLASS_CHECK_EXCEPTION(env);
    
     _magnetometer = [[Magnetometer alloc] init];
}

JNIEXPORT void JNICALL Java_com_gluonhq_charm_down_plugins_ios_IOSMagnetometerService_startObserver
(JNIEnv *env, jclass jClass, jint jfrequency)
{
    if (jfrequency > 0) {
        magRate = 1.0 / ((double) jfrequency);
    }

    dispatch_async(dispatch_get_main_queue(), ^{
        [_magnetometer startObserver];
    });
    return;   
}

JNIEXPORT void JNICALL Java_com_gluonhq_charm_down_plugins_ios_IOSMagnetometerService_stopObserver
(JNIEnv *env, jclass jClass)
{
    [_magnetometer stopObserver];
    return;   
}

void sendReading0(CMMagnetometerData  *magnetometerData) {
    GET_MAIN_JENV;
    if (magnetometerData) 
    {
        double x = magnetometerData.magneticField.x;
        double y = magnetometerData.magneticField.y;
        double z = magnetometerData.magneticField.z;
        double m = sqrt(x * x + y * y + z * z);
        (*env)->CallVoidMethod(env, mat_jMagnetometerServiceClass, mat_jMagnetometerService_notifyReading, x, y, z, m, 0, 0, 0);
    }
}
void sendReading(CMDeviceMotion *motionData) {
    GET_MAIN_JENV;
    if (motionData) 
    {
        double x = motionData.magneticField.field.x;
        double y = motionData.magneticField.field.y;
        double z = motionData.magneticField.field.z;
        double m = sqrt(x * x + y * y + z * z);

        double defaultYaw = motionData.attitude.yaw; // 0 right side device towards North, + Pi/2 towards West, Pi South, - Pi/2 East.
        double yaw = M_PI / 2 + defaultYaw; // 0 front side device towards North
        if (yaw > M_PI) {
            yaw -= 2 * M_PI;
        } else if (yaw < -M_PI) {
            yaw += 2 * M_PI;
        }
        yaw = - yaw; // + Pi/2 towards East

        double defaultPitch = motionData.attitude.pitch; // 0 parallel to ground, -Pi/2 top side towards ground
        double pitch = -defaultPitch; // +PI/2 top side towards ground

        double roll  = motionData.attitude.roll;

        (*env)->CallVoidMethod(env, mat_jMagnetometerServiceClass, mat_jMagnetometerService_notifyReading, 
                    x, y, z, m, yaw, pitch, roll);
    }
}

@implementation Magnetometer 

- (void) startObserver
{   

    if (!self.motionManager) {
        self.motionManager = [[CMMotionManager alloc] init];
    }

    if ([self.motionManager isDeviceMotionAvailable])
    {
        self.motionManager.deviceMotionUpdateInterval = magRate; // in seconds
        [self.motionManager startDeviceMotionUpdatesUsingReferenceFrame:CMAttitudeReferenceFrameXMagneticNorthZVertical 
                        toQueue:[NSOperationQueue mainQueue]
                        withHandler:^(CMDeviceMotion *motionData, NSError *error) {
                sendReading(motionData);
        }];
    } else
    {
        NSLog(@"Error: No Magnetometer Available");
    }

}

- (void) stopObserver 
{
    if (self.motionManager) 
    {
        [self.motionManager stopDeviceMotionUpdates];
    }
}

@end

