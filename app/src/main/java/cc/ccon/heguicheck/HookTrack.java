package cc.ccon.heguicheck;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class HookTrack implements IXposedHookLoadPackage {
    private static final String TAG = "HookTrack";

    private static final String filterStr = " - secheguicheck";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {

        if (lpparam == null) {
            return;
        }


        XposedHelpers.findAndHookMethod(
                "android.telephony.TelephonyManager",
                lpparam.classLoader,
                "getDeviceId",
                new DumpMethodHook(lpparam.packageName) {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log(lpparam.packageName + "调用getDeviceId获取imei" + filterStr);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                "android.telephony.TelephonyManager",
                lpparam.classLoader,
                "getDeviceId",
                int.class,
                new DumpMethodHook(lpparam.packageName) {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log(lpparam.packageName + "调用getDeviceId(int)获取imei" + filterStr);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                "android.telephony.TelephonyManager",
                lpparam.classLoader,
                "getSubscriberId",
                int.class,
                new DumpMethodHook(lpparam.packageName) {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log(lpparam.packageName + "调用getSubscriberId获取imsi" + filterStr);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                "android.telephony.TelephonyManager",
                lpparam.classLoader,
                "getImei",
                new DumpMethodHook(lpparam.packageName) {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log(lpparam.packageName + "调用getImei获取imei" + filterStr);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                "android.telephony.TelephonyManager",
                lpparam.classLoader,
                "getImei",
                int.class,
                new DumpMethodHook(lpparam.packageName) {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log(lpparam.packageName + "调用getImei(int)获取imei" + filterStr);
                    }
                }
        );


        XposedHelpers.findAndHookMethod(
                "android.net.wifi.WifiInfo",
                lpparam.classLoader,
                "getMacAddress",
                new DumpMethodHook(lpparam.packageName) {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log(lpparam.packageName + "调用getMacAddress获取mac地址" + filterStr);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                "java.net.NetworkInterface",
                lpparam.classLoader,
                "getHardwareAddress",
                new DumpMethodHook(lpparam.packageName) {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log(lpparam.packageName + "调用getHardwareAddress获取mac地址" + filterStr);
                    }
                }
        );


        XposedHelpers.findAndHookMethod(
                "android.provider.Settings.Secure",
                lpparam.classLoader,
                "getString",
                ContentResolver.class,
                String.class,
                new DumpMethodHook(lpparam.packageName) {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log(lpparam.packageName + "调用Settings.Secure.getString获取" + param.args[1] + filterStr);
                    }
                }
        );


        XposedHelpers.findAndHookMethod(
                "android.location.LocationManager",
                lpparam.classLoader,
                "getLastKnownLocation",
                String.class,
                new DumpMethodHook(lpparam.packageName) {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log(lpparam.packageName + "调用getLastKnownLocation获取GPS地址" + filterStr);
                    }
                }
        );


        XposedHelpers.findAndHookMethod(
                "android.app.ApplicationPackageManager",
                lpparam.classLoader,
                "getInstalledPackages",
                int.class,
                new DumpMethodHook(lpparam.packageName) {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log(lpparam.packageName + "调用getInstalledPackages(int)获取已安装应用列表" + filterStr);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                "android.app.ApplicationPackageManager",
                lpparam.classLoader,
                "getInstalledApplications",
                int.class,
                new DumpMethodHook(lpparam.packageName) {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log(lpparam.packageName + "调用getInstalledApplications(int)获取已安装应用列表" + filterStr);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                "android.app.ApplicationPackageManager",
                lpparam.classLoader,
                "queryIntentActivities",
                Intent.class,
                int.class,
                new DumpMethodHook(lpparam.packageName) {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log(lpparam.packageName + "调用queryIntentActivities获取已安装应用信息" + filterStr);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                "android.os.SystemProperties",
                lpparam.classLoader,
                "get",
                String.class,
                String.class,
                new DumpMethodHook(lpparam.packageName) {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        if (param.args[0].equals("ro.serialno")) {
                            XposedBridge.log(lpparam.packageName + "调用SystemProperties.get(String, String)获取序列号" + filterStr);
                        }
                    }
                }
        );

    }
}
