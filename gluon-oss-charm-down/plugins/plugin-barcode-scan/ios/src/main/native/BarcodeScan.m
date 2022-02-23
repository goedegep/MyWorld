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

#include "BarcodeScan.h"

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


static int BarcodeScanInited = 0;

// BarcodeScan
jclass mat_jScanServiceClass;
jmethodID mat_jScanService_setResult = 0;
BarcodeScan *_barcodeScan;


JNIEXPORT void JNICALL Java_com_gluonhq_charm_down_plugins_ios_IOSBarcodeScanService_initBarcodeScan
(JNIEnv *env, jclass jClass)
{
    if (BarcodeScanInited)
    {
        return;
    }
    BarcodeScanInited = 1;
    
    mat_jScanServiceClass = (*env)->NewGlobalRef(env, (*env)->FindClass(env, "com/gluonhq/charm/down/plugins/ios/IOSBarcodeScanService"));
    mat_jScanService_setResult = (*env)->GetStaticMethodID(env, mat_jScanServiceClass, "setResult", "(Ljava/lang/String;)V");
    GLASS_CHECK_EXCEPTION(env);
}

void sendScanResult(NSString *scanResult) {
	GET_MAIN_JENV;
        const char *scanChars = [scanResult UTF8String];
        jstring arg = (*env)->NewStringUTF(env, scanChars);
        (*env)->CallStaticVoidMethod(env, mat_jScanServiceClass, mat_jScanService_setResult, arg);
        (*env)->DeleteLocalRef(env, arg);
	NSLog(@"Finished sending scan result");
}

JNIEXPORT void JNICALL Java_com_gluonhq_charm_down_plugins_ios_IOSBarcodeScanService_startBarcodeScan
(JNIEnv *env, jclass jClass)
{
    _barcodeScan = [[BarcodeScan alloc] init];
    [_barcodeScan display];
    return;
}

@implementation BarcodeScan 

AVCaptureSession *_session;
AVCaptureDevice *_device;
AVCaptureDeviceInput *_input;
AVCaptureMetadataOutput *_output;
AVCaptureVideoPreviewLayer *_prevLayer;
UIButton *_button;

- (void)display {
    if(![[UIApplication sharedApplication] keyWindow])
    {
        NSLog(@"key window was nil");
        return;
    }
   
    // get the root view controller
    UIViewController *rootViewController = [[[UIApplication sharedApplication] keyWindow] rootViewController];
    if(!rootViewController)
    {
        NSLog(@"rootViewController was nil");
        return;
    }
   
    // get the view
    UIView *view = self.view;

    _session = [[AVCaptureSession alloc] init];
    _device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
    NSError *error = nil;

    _input = [AVCaptureDeviceInput deviceInputWithDevice:_device error:&error];
    if (_input) {
        [_session addInput:_input];
    } else {
        NSLog(@"Error: %@", error);
    }

    _output = [[AVCaptureMetadataOutput alloc] init];
    [_output setMetadataObjectsDelegate:self queue:dispatch_get_main_queue()];
    [_session addOutput:_output];

    _output.metadataObjectTypes = [_output availableMetadataObjectTypes];

    _prevLayer = [AVCaptureVideoPreviewLayer layerWithSession:_session];
    _prevLayer.frame = view.bounds;
    _prevLayer.videoGravity = AVLayerVideoGravityResizeAspectFill;
    _prevLayer.connection.videoOrientation = [self videoOrientationFromCurrentDeviceOrientation];
    [view.layer addSublayer:_prevLayer];

    // cancel button
    _button = [[UIButton alloc] init];
    [_button setTitle:@"Cancel" forState:UIControlStateNormal];
    [_button setTitleColor:_button.tintColor forState:UIControlStateNormal];
    [_button sizeToFit];
    [view addSubview:_button];
    [self placeCancelButton];
    [_button addTarget:self action:@selector(done:) forControlEvents:UIControlEventTouchUpInside];
    
    // show view controller
    [rootViewController presentViewController:self animated:YES completion:nil];
    [_session startRunning];

}

// hide barcodeScan preview and view controller
- (IBAction)done:(id)sender
{
    if([_session isRunning])
    {
        [_session stopRunning];
    }
    [_session removeInput:_input];
    [_session removeOutput:_output];
    [_prevLayer removeFromSuperlayer];
    [_button removeFromSuperview];
    [_button release];
    _button = nil;
    [self dismissViewControllerAnimated:YES completion:nil];
    _prevLayer = nil;
    _session = nil;
}

// place cancel button
- (void) placeCancelButton
{
    CGRect sbFrame = [[UIApplication sharedApplication] statusBarFrame];
    int ofs = sbFrame.size.height;
    _button.frame = CGRectMake(10, ofs, _button.frame.size.width, _button.frame.size.height);
}

// device will / did rotate
- (void)viewWillTransitionToSize:(CGSize)size withTransitionCoordinator:(id<UIViewControllerTransitionCoordinator>)coordinator
{
    [super viewWillTransitionToSize:size withTransitionCoordinator:coordinator];
    
    // Place code here which is performed before the rotation animation starts.
    
    [coordinator animateAlongsideTransition:^(id<UIViewControllerTransitionCoordinatorContext> context)
    {
        // Perform this code here during rotation animation
        
    } completion:^(id<UIViewControllerTransitionCoordinatorContext> context)
    {
                    
        // rotation finised, resize preview layer
        _prevLayer.frame = self.view.bounds;
        // rotate camera based on new orientation
        _prevLayer.connection.videoOrientation = [self videoOrientationFromCurrentDeviceOrientation];
        
        // adjust cancel button location
        [self placeCancelButton];

    NSLog(@"self.view.bounds.size width:%f height:%f ",self.view.bounds.size.width,self.view.bounds.size.height);
    NSLog(@"self.view.frame.size width:%f height:%f",self.view.frame.size.width,self.view.frame.size.height);

    }];
}

- (AVCaptureVideoOrientation) videoOrientationFromCurrentDeviceOrientation {
    switch (self.interfaceOrientation) {
        case UIInterfaceOrientationPortrait: {
            return AVCaptureVideoOrientationPortrait;
        }
        case UIInterfaceOrientationLandscapeLeft: {
            return AVCaptureVideoOrientationLandscapeLeft;
        }
        case UIInterfaceOrientationLandscapeRight: {
            return AVCaptureVideoOrientationLandscapeRight;
        }
        case UIInterfaceOrientationPortraitUpsideDown: {
            return AVCaptureVideoOrientationPortraitUpsideDown;
        }
        case UIInterfaceOrientationUnknown: {
            return AVCaptureVideoOrientationPortrait;
        }
    }
}

- (void)captureOutput:(AVCaptureOutput *)captureOutput didOutputMetadataObjects:(NSArray *)metadataObjects fromConnection:(AVCaptureConnection *)connection
{
    NSString *detectionString = nil;
    NSArray *barCodeTypes = @[AVMetadataObjectTypeUPCECode, AVMetadataObjectTypeCode39Code, AVMetadataObjectTypeCode39Mod43Code,
            AVMetadataObjectTypeEAN13Code, AVMetadataObjectTypeEAN8Code, AVMetadataObjectTypeCode93Code, AVMetadataObjectTypeCode128Code,
            AVMetadataObjectTypePDF417Code, AVMetadataObjectTypeQRCode, AVMetadataObjectTypeAztecCode];

    for (AVMetadataObject *metadata in metadataObjects) {
        for (NSString *type in barCodeTypes) {
            if ([metadata.type isEqualToString:type])
            {
                detectionString = [(AVMetadataMachineReadableCodeObject *)metadata stringValue];
                break;
            }
        }

        if (detectionString != nil)
        {
            NSLog(@"String: %@", detectionString);
            sendScanResult(detectionString);

            [self done:nil];
            break;

        }
        else
        {
            NSLog(@"String: none");
            NSString *result = nil;
            sendScanResult(result);
        }
    }

}
@end
