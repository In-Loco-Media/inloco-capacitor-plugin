declare module "@capacitor/core" {
  interface PluginRegistry {
    InlocoEngage: IInlocoEngagePlugin;
  }
}

export interface IInlocoEngagePlugin {
  initSdk(): void;
  setUserId(options: {userId: string}): void;
  clearUserId(): void;
  setFirebasePushProvider(options: {token: string }): void;
  presentNotification(options: {message: object}): void;
  setAddress(options: {address: object}): void;
  clearAddress(): void;
  trackEvent(options: {name: string, properties: object}): void;
  trackLocalizedEvent(options: {name: string, properties: object}): void;
  getInstallationId(): Promise<{value: string}>;
}
