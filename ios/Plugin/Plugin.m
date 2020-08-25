#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

CAP_PLUGIN(InlocoEngage, "InlocoEngage",
           CAP_PLUGIN_METHOD(initSdk, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(setUserId, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(clearUserId, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(setAddress, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(clearAddress, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(trackEvent, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(trackLocalizedEvent, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(getInstallationId, CAPPluginReturnPromise);
)
