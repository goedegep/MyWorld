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

#include "RuntimeArgs.h"

extern JNIEnv *jEnv;
#define GET_MAIN_JENV \
if (jEnv == NULL) NSLog(@"ERROR: Java has been detached already, but someone is still trying to use it at %s:%s:%d\n", __FUNCTION__, __FILE__, __LINE__);\
JNIEnv *env = jEnv;

#define GLASS_CHECK_EXCEPTION(ENV)                                                 \
do {                                                                               \
jthrowable t = (*ENV)->ExceptionOccurred(ENV);                                 \
if (t) {                                                                       \
(*ENV)->ExceptionClear(ENV);                                               \
};                                                                             \
} while (0)

static int runtimeArgsInited = 0;

jclass mat_jRuntimeArgsClass;
jmethodID mat_jProcessRuntimeArgsMethod = 0;
RasDelegate *_rasDelegate;


JNIEXPORT void JNICALL Java_com_gluonhq_charm_down_plugins_ios_IOSRuntimeArgsService_initRuntimeArgs
(JNIEnv *env, jclass jClass)
{
    if (runtimeArgsInited)
    {
        return;
    }
    runtimeArgsInited = 1;

    mat_jRuntimeArgsClass = (*env)->NewGlobalRef(env, (*env)->FindClass(env, "com/gluonhq/charm/down/plugins/ios/IOSRuntimeArgsService"));
    GLASS_CHECK_EXCEPTION(env);
    mat_jProcessRuntimeArgsMethod = (*env)->GetMethodID(env, mat_jRuntimeArgsClass, "processRuntimeArgs", "(Ljava/lang/String;Ljava/lang/String;)V");
    GLASS_CHECK_EXCEPTION(env);

    _rasDelegate = [[RasDelegate alloc] init];
    [_rasDelegate register];
}

void processRuntimeArgs(NSString* key, NSString* value) {
    GET_MAIN_JENV;
    const char *keyChars = [key UTF8String];
    jstring jkey = (*env)->NewStringUTF(env, keyChars);
    const char *valueChars = [value UTF8String];
    jstring jvalue = (*env)->NewStringUTF(env, valueChars);
    (*env)->CallVoidMethod(env, mat_jRuntimeArgsClass, mat_jProcessRuntimeArgsMethod, jkey, jvalue);
    (*env)->DeleteLocalRef(env, jkey);
    (*env)->DeleteLocalRef(env, jvalue);
}

@implementation GlassApplication (RuntimeArgsAdditions)

// TODO: Add the rest of methods that allow opening externally the application

// iOS 4 - 9
- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url
    sourceApplication:(NSString *)sourceApplication annotation:(id)annotation {

    GET_MAIN_JENV;
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    {
        NSLog(@"OPENURL CALLED in CHARM DOWN! %@", url.absoluteString);
        processRuntimeArgs(@"Launch.URL", url.absoluteString);
    }
    [pool drain];
    GLASS_CHECK_EXCEPTION(env);
    return TRUE;
}

// iOS 10
- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url
        options:(NSDictionary<NSString *,id> *)options
{
    GET_MAIN_JENV;
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    {
        NSLog(@"OPENURL CALLED in CHARM DOWN! %@", url.absoluteString);
        processRuntimeArgs(@"Launch.URL", url.absoluteString);
    }
    [pool drain];
    GLASS_CHECK_EXCEPTION(env);
    return TRUE;
}

// called with app opened either on front or in the background, when user clicks on notification

- (void)application:(UIApplication *)application didReceiveLocalNotification:(UILocalNotification *)notification
{
    GET_MAIN_JENV;
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    {
        NSDictionary *myUserInfo = notification.userInfo;
        NSString *myId = [myUserInfo objectForKey:@"userId"];
        NSLog(@"Sending this notification with id %@", myId);
        processRuntimeArgs(@"Launch.LocalNotification", myId);
    }
    [pool drain];
    GLASS_CHECK_EXCEPTION(env);
}

// Remote Notifications iOS < 10

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult result))completionHandler;
{
    GET_MAIN_JENV;
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    {
        NSError *err;
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:userInfo options:0 error: &err];
        NSString *jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        NSLog(@"Received remote notification, forward to RAS");
        processRuntimeArgs(@"Launch.PushNotification", jsonString);
        NSLog(@"Processed remote notification");
    }
    [pool drain];
    GLASS_CHECK_EXCEPTION(env);

    if(application.applicationState == UIApplicationStateInactive) {
        NSLog(@"App was Inactive");
        //Show the view with the content of the push
    } else if (application.applicationState == UIApplicationStateBackground) {
        NSLog(@"App was in Background");
        //Refresh the local model
    } else {
        NSLog(@"App is Active");
        //Show an in-app banner
    }
    NSLog(@"call completionhandler after remote notification");
    completionHandler(UIBackgroundFetchResultNewData);

}
@end

// Remote Notifications iOS 10

@implementation RasDelegate

- (void)register
{
    UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
    center.delegate = self;
}

-(void)userNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(UNNotificationPresentationOptions options))completionHandler{

    //When a notification is delivered to a foreground app, this will show it on top:
    completionHandler(UNNotificationPresentationOptionAlert | UNNotificationPresentationOptionSound);
}

-(void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void(^)())completionHandler{

    //Called when a notification is delivered to foreground or background app.
    NSLog(@"Received remote notification: Userinfo %@",response.notification.request.content.userInfo);
    GET_MAIN_JENV;
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    {
        NSError *err;
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:response.notification.request.content.userInfo options:0 error: &err];
        NSString *jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        processRuntimeArgs(@"Launch.PushNotification", jsonString);
    }
    [pool drain];
    GLASS_CHECK_EXCEPTION(env);
    completionHandler();
}

@end
