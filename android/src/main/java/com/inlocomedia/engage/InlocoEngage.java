package com.inlocomedia.engage;

import android.location.Address;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.google.gson.Gson;
import com.inlocomedia.android.common.InLoco;
import com.inlocomedia.android.common.InLocoEvents;
import com.inlocomedia.android.common.listener.InLocoListener;
import com.inlocomedia.android.common.listener.Result;
import com.inlocomedia.android.engagement.InLocoAddressValidation;
import com.inlocomedia.android.engagement.InLocoPush;
import com.inlocomedia.android.engagement.PushMessage;
import com.inlocomedia.android.engagement.request.FirebasePushProvider;
import com.inlocomedia.android.location.InLocoVisits;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@NativePlugin()
public class InlocoEngage extends Plugin {

    @PluginMethod()
    public void initSdk(PluginCall call) {
        InLoco.init(getActivity());
        call.success();
    }

    @PluginMethod()
    public void setUserId(PluginCall call) {
        String userId = call.getString("userId");
        if(userId == null) {
            call.reject("The userId can't be empty");
            return;
        }
        InLoco.setUserId(getActivity(), userId);
        call.success();
    }

    @PluginMethod()
    public void clearUserId(PluginCall call) {
        InLoco.clearUserId(getActivity());
        call.success();
    }

    @PluginMethod()
    public void setFirebasePushProvider(PluginCall call) {
        String token = call.getString("token");
        if(token == null) {
            call.reject("The Firebase token can't be empty");
            return;
        }
        InLocoPush.setPushProvider(getActivity(), FirebasePushProvider.newProvider(token));
        call.success();
    }

    @PluginMethod()
    public void presentNotification(PluginCall call) {
        JSONObject dataJSON = call.getData().getJSObject("message").getJSObject("data");
        if(dataJSON == null) {
            call.reject("The message data can't be empty");
            return;
        }
        final Map<String, String> data = new Gson().fromJson(dataJSON.toString(), Map.class);

        if (data != null) {
            int smallIconResId = getActivity()
                    .getResources()
                    .getIdentifier("ic_notification", "mipmap", null);
            if (smallIconResId == 0) {
                smallIconResId = getActivity()
                        .getResources()
                        .getIdentifier("ic_launcher", "mipmap", null);
                if (smallIconResId == 0) {
                    smallIconResId = android.R.drawable.ic_dialog_info;
                }
            }
            final PushMessage pushContent = InLocoPush.decodeReceivedMessage(getActivity(), data);
            int randomNotificationId = Integer.parseInt(
                    new SimpleDateFormat("ddHHmmss", Locale.US)
                            .format(new Date())
            );
            if (pushContent != null) {
                InLocoPush.presentNotification(getContext(),
                        pushContent,
                        smallIconResId,
                        randomNotificationId
                );
            }
        }
        call.success();
    }

    @PluginMethod()
    public void setAddress(PluginCall call) {
        Address address = new Address(new Locale(call.getString("locale")));
        address.setCountryName(call.getString("countryName"));
        address.setCountryCode(call.getString("countryCode"));
        address.setAdminArea(call.getString("adminArea"));
        address.setSubAdminArea(call.getString("subAdminArea"));
        address.setLocality(call.getString("locality"));
        address.setSubLocality(call.getString("subLocality"));
        address.setThoroughfare(call.getString("thoroughfare"));
        address.setSubThoroughfare(call.getString("subThoroughfare"));
        address.setPostalCode(call.getString("postalCode"));

        InLocoAddressValidation.setAddress(getActivity(), address);

        call.success();
    }

    @PluginMethod
    public void clearAddress(PluginCall call) {
        InLocoAddressValidation.clearAddress(getActivity());
        call.success();
    }

    @PluginMethod()
    public void trackEvent(PluginCall call) {
        String name = call.getString("name");
        if(name == null) {
            call.reject("The event name can't be empty");
            return;
        }
        JSONObject dataJSON = call.getData().getJSObject("properties");
        final Map data;
        if (dataJSON != null) {
            data = new Gson().fromJson(dataJSON.toString(), Map.class);
        } else {
            data = null;
        }
        InLocoEvents.trackEvent(getActivity(), name, data);
        call.success();
    }

    @PluginMethod()
    public void trackLocalizedEvent(PluginCall call) {
        String name = call.getString("name");
        if(name == null) {
            call.reject("The event name can't be empty");
            return;
        }
        JSONObject dataJSON = call.getData().getJSObject("properties");
        final Map data;
        if (dataJSON != null) {
            data = new Gson().fromJson(dataJSON.toString(), Map.class);
        } else {
            data = null;
        }
        InLocoVisits.trackLocalizedEvent(getActivity(), name, data);
        call.success();
    }

    @PluginMethod()
    public void getInstallationId(final PluginCall call) {
        InLoco.getInstallationId(getActivity(), new InLocoListener<String>() {
            @Override
            public void onResult(final Result<String> result) {
                if (result.isSuccessful()) {
                    String installationId = result.getResult();
                    JSObject ret = new JSObject();
                    ret.put("value", installationId);
                    call.success(ret);
                } else {
                    call.reject("Failed to retrieve the installation id");
                }
            }
        });
    }


}
