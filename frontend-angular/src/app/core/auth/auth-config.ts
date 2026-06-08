import { MsalGuardConfiguration, MsalInterceptorConfiguration } from '@azure/msal-angular';
import {
  BrowserCacheLocation,
  InteractionType,
  PublicClientApplication,
} from '@azure/msal-browser';

export const azureB2cConfig = {
  tenantName: import.meta.env['NG_APP_TENANT_NAME'],
  tenantDomain: import.meta.env['NG_APP_TENANT_DOMAIN'],
  userFlowName: import.meta.env['NG_APP_USER_FLOW_NAME'],
  clientId: import.meta.env['NG_APP_CLIENT_ID'],
  authority: import.meta.env['NG_APP_AUTHORITY'],
  knownAuthority: import.meta.env['NG_APP_KNOWN_AUTHORITY'],
  redirectUri: import.meta.env['NG_APP_REDIRECT_URI'],
  apiBaseUrl: import.meta.env['NG_APP_API_BASE_URL'],
  apiScope: import.meta.env['NG_APP_API_SCOPE'],
};

export const msalInstance = new PublicClientApplication({
  auth: {
    clientId: azureB2cConfig.clientId,
    authority: azureB2cConfig.authority,
    knownAuthorities: [azureB2cConfig.knownAuthority],
    redirectUri: azureB2cConfig.redirectUri,
    postLogoutRedirectUri: azureB2cConfig.redirectUri,
  },
  cache: {
    cacheLocation: BrowserCacheLocation.LocalStorage,
  },
});

export const msalGuardConfig: MsalGuardConfiguration = {
  interactionType: InteractionType.Redirect,
  authRequest: {
    scopes: [azureB2cConfig.apiScope],
  },
};

export const msalInterceptorConfig: MsalInterceptorConfiguration = {
  interactionType: InteractionType.Redirect,
  protectedResourceMap: new Map([[azureB2cConfig.apiBaseUrl, [azureB2cConfig.apiScope]]]),
};
