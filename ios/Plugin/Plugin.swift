import Foundation
import Capacitor
import InLocoSDK

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitor.ionicframework.com/docs/plugins/ios
 */
@objc(InlocoEngage)
public class InlocoEngage: CAPPlugin {
    @objc func initSdk(_ call: CAPPluginCall) {
        ILMInLoco.initSdk()
        call.success()
    }

    @objc func setUserId(_ call: CAPPluginCall) {
        let userId = call.getString("userId")!
        ILMInLoco.setUserId(userId)
        call.success()
    }

    @objc func clearUserId(_ call: CAPPluginCall) {
        ILMInLoco.clearUserId()
        call.success()
    }

    @objc func setAddress(_ call: CAPPluginCall) {
        let userAddress = ILMUserAddress()
        userAddress.locale = Locale.init(identifier: call.getString("locale")!);
        userAddress.countryName = call.getString("countryName")!
        userAddress.countryCode = call.getString("countryCode")!
        userAddress.adminArea = call.getString("adminArea")!
        userAddress.subAdminArea = call.getString("subAdminArea")!
        userAddress.locality = call.getString("locality")!
        userAddress.subLocality = call.getString("subLocality")!
        userAddress.thoroughfare = call.getString("thoroughfare")!
        userAddress.subThoroughfare = call.getString("subThoroughfare")!
        userAddress.postalCode = call.getString("postalCode")!
        ILMInLoco.setUserAddress(userAddress)
        call.success()
    }

    @objc func clearAddress(_ call: CAPPluginCall) {
        ILMInLoco.clearUserAddress()
        call.success()
    }

    @objc func trackEvent(_ call: CAPPluginCall) {
        let name = call.getString("name")
        let properties = call.getObject("properties") ?? nil

        let stringProperties = convertToStringStringDict(originalDict: properties)!
        ILMInLocoEvents.trackEvent(name!, properties: stringProperties)
        call.success()
    }
    
    @objc func trackLocalizedEvent(_ call: CAPPluginCall) {
        let name = call.getString("name")
        let properties = call.getObject("properties") ?? nil

        let stringProperties = convertToStringStringDict(originalDict: properties)!
        ILMInLocoVisits.trackLocalizedEvent(name!, properties: stringProperties)
        call.success()
    }

    @objc func getInstallationId(_ call: CAPPluginCall) {
        ILMInLoco.getInstallationId { (installationId) in
            call.success([
                "value": installationId!
            ])
        }
    }

    func convertToStringStringDict(originalDict: [String:Any]?) -> [String:String]? {
        if(originalDict != nil) {
            var stringStringDict = [String: String]()
            for (key, value) in originalDict! {
                stringStringDict[key] = String(describing: value)
            }
            return stringStringDict;
        }
        return nil;
    }
}
