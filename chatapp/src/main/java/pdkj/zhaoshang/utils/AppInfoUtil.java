package pdkj.zhaoshang.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class AppInfoUtil {

	/**
     * 获取版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {    
			// ---get the package info---    
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;    
			if (versionName == null || versionName.length() <= 0) {    
				return "";
			}    
		} catch (Exception e) {
		}    
		return versionName;    
	}
	/**
	 * 获取版本号
	 */
	public static int getAppVersionCode(Context context) {
		int versionCode = 1;
		try {    
			// ---get the package info--- 
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = pi.versionCode;
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		return versionCode; 
	}
	
	/**
	 * 应用是否在运行
	 * @param context
	 * @return
	 */
	public static Boolean isAppRunning(Context context)
	{
		boolean isAppRunning = false;
		ActivityManager am = (ActivityManager)
				context.getSystemService(Context.ACTIVITY_SERVICE);
	    List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
	    for (ActivityManager.RunningTaskInfo info : list) {
	        if (info.topActivity.getPackageName().equals(context.getPackageName()) 
	        		&& info.baseActivity.getPackageName().equals(context.getPackageName())
	        		&& (!info.baseActivity.getClassName().equals(info.topActivity.getClassName()))
	        		) {
	            isAppRunning = true;
	            break;
	        }
	    }
	    return isAppRunning;
	}
	
	/**
	 * 获取启动APp的意图, 如果该APP 已经启动
	 * @return
	 */
	public static Intent getOnlyIntent(){
		Intent appIntent = new Intent(Intent.ACTION_MAIN);
		appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED );//
		appIntent.setComponent(new ComponentName("com.pengdikj.xiaoya", "com.pengdikj.xiaoya.MainActivity"));
		return appIntent;
	}


	/**
	 * 获取手机MAC地址
	 *
	 * @return
	 */
	public static String getMAC(Context context) {
		String str = null, macSerial = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 如果当前设备系统大于等于6.0 使用下面的方法
			return getMac();
		} else {
			try {
				WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				// 获取MAC地址
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				String mac = wifiInfo.getMacAddress();
				if (null == mac) {
					// 未获取到
					mac = "";
				}
				return mac;
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
	}

	/**
	 * 获取手机的MAC地址
	 *
	 * @return
	 */
	public static String getMac() {
		String str = "";
		String macSerial = "";
		try {
			Process pp = Runtime.getRuntime().exec(
					"cat /sys/class/net/wlan0/address ");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (; null != str; ) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (macSerial == null || "".equals(macSerial)) {
			try {
				return loadFileAsString("/sys/class/net/eth0/address")
						.toUpperCase().substring(0, 17);
			} catch (Exception e) {
				e.printStackTrace();
				macSerial = getAndroid7MAC();
			}
		}
		return macSerial;
	}

	/**
	 * 兼容7.0获取不到的问题
	 *
	 * @return
	 */
	public static String getAndroid7MAC() {
		try {
			List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface nif : all) {
				if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
				byte[] macBytes = nif.getHardwareAddress();
				if (macBytes == null) {
					return "";
				}
				StringBuilder res1 = new StringBuilder();
				for (byte b : macBytes) {
					res1.append(String.format("%02X:", b));
				}
				if (res1.length() > 0) {
					res1.deleteCharAt(res1.length() - 1);
				}
				return res1.toString();
			}
		} catch (Exception ex) {
		}
		return "02:00:00:00:00:00";
	}

	public static String loadFileAsString(String fileName) throws Exception {
		FileReader reader = new FileReader(fileName);
		String text = loadReaderAsString(reader);
		reader.close();
		return text;
	}

	public static String loadReaderAsString(Reader reader) throws Exception {
		StringBuilder builder = new StringBuilder();
		char[] buffer = new char[4096];
		int readLength = reader.read(buffer);
		while (readLength >= 0) {
			builder.append(buffer, 0, readLength);
			readLength = reader.read(buffer);
		}
		return builder.toString();
	}
}