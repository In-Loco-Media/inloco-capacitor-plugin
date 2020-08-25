import {Plugins, Capacitor} from "@capacitor/core";

const { InlocoEngage } = Plugins;

export class InLoco {
    static init(): void {
        InlocoEngage.initSdk();
    }

    static setUser(userId: string): void {
        if(userId){
            InlocoEngage.setUserId({userId: userId});
        }
    }

    static clearUser(): void {
        InlocoEngage.clearUserId();
    }

    static setFirebasePushProvider(token: string): void {
        if(token) {
            InlocoEngage.setFirebasePushProvider({token: token});
        }
    }

    static isInLocoMessage(message: any): boolean {
        return message != null && 'in_loco_data' in message.data;
    }

    static presentNotification(message: object): void {
        if(message != null){
            InlocoEngage.presentNotification({message: message});
        }
    }

    static setUserAddress(address: any): void {
        if (address != null && "subThoroughfare" in address) {
            address.subThoroughfare = String(address.subThoroughfare);
        }
        if (Capacitor.getPlatform() == 'ios' && address != null && "locale" in address) {
            address.locale = address.locale.replace("-", "_");
        }
        InlocoEngage.setAddress(address);
    }

    static clearAddress() {
        InlocoEngage.clearAddress();
    }

    static trackEvent(name: string, properties: any): void {
        for (var property in properties) {
            if (properties.hasOwnProperty(property) && properties[property] != null) {
                properties[property] = properties[property].toString();
            }
        }
        InlocoEngage.trackEvent({name: name, properties: properties});
    }

    static trackLocalizedEvent(name: string, properties: any): void {
        for (var property in properties) {
            if (properties.hasOwnProperty(property) && properties[property] != null) {
                properties[property] = properties[property].toString();
            }
        }
        InlocoEngage.trackLocalizedEvent({name: name, properties: properties});
    }

    static getInstallationId(): Promise<{value: string}>{
        return InlocoEngage.getInstallationId();
    }

}
