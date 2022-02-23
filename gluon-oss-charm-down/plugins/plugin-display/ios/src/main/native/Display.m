/*
 * Copyright (c) 2016, 2018 Gluon
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
#import <UIKit/UIKit.h>
#include "jni.h"

JNIEXPORT jboolean JNICALL Java_com_gluonhq_charm_down_plugins_ios_IOSDisplayService_isIphone
(JNIEnv *env, jclass jClass)
{
    NSString *deviceModel = (NSString*)[UIDevice currentDevice].model;
    if ([[deviceModel substringWithRange:NSMakeRange(0, 4)] isEqualToString:@"iPad"]) {
        return JNI_FALSE;
    } else {
        return JNI_TRUE;
    }
}

JNIEXPORT jdoubleArray JNICALL Java_com_gluonhq_charm_down_plugins_ios_IOSDisplayService_screenSize
(JNIEnv *env, jclass jClass)
{
    CGRect screenBounds = [[UIScreen mainScreen] bounds];
    CGFloat screenScale = [[UIScreen mainScreen] scale];
    CGSize screenSize = CGSizeMake(screenBounds.size.width * screenScale, screenBounds.size.height * screenScale);
    
    jdoubleArray output = (*env)->NewDoubleArray(env, 2);
    if (output == NULL) 
    {
        return NULL;
    }
    jdouble res[] = {screenSize.width, screenSize.height};
    (*env)->SetDoubleArrayRegion(env, output, 0, 2, res);
    return output;
}

JNIEXPORT jdoubleArray JNICALL Java_com_gluonhq_charm_down_plugins_ios_IOSDisplayService_screenBounds
(JNIEnv *env, jclass jClass)
{
    CGRect screenBounds = [[UIScreen mainScreen] bounds];
    
    jdoubleArray output = (*env)->NewDoubleArray(env, 2);
    if (output == NULL) 
    {
        return NULL;
    }
    jdouble res[] = {screenBounds.size.width, screenBounds.size.height};
    (*env)->SetDoubleArrayRegion(env, output, 0, 2, res);
    return output;
}

JNIEXPORT jfloat JNICALL Java_com_gluonhq_charm_down_plugins_ios_IOSDisplayService_screenScale
(JNIEnv *env, jclass jClass)
{
    return [UIScreen mainScreen].scale;
}
