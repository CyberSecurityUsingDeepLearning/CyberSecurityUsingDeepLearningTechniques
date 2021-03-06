#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
Created on Mon Jan  9 20:46:43 2017

@author: Divyashri Sreedharan Nair
"""

from androguard.core.bytecodes import apk
import os
import subprocess
import math
import sys

cwd = os.getcwd()
file_name=sys.argv[1];

def output_result_image(res, f) :
    try:
        from PIL import Image
        from PIL import ImageDraw
    except:
        import Image
    
    i = Image.new("RGB",(len(res[0])*10,len(res)*10))
    d = ImageDraw.Draw(i)
    
    for xcounter,row in enumerate(res):
        for ycounter,col in enumerate(row):
            d.text((ycounter*10,xcounter*10),str(col))
        
    i.save(open(f,"wb"),"PNG")
    
    
    
#print("=====================================")
permissionHash = {"ACCESS_CHECKIN_PROPERTIES":0,
                    "ACCESS_COARSE_LOCATION":1,
                    "ACCESS_FINE_LOCATION":2,
                    "ACCESS_LOCATION_EXTRA_COMMANDS":3,
                    "ACCESS_NETWORK_STATE:":4,
                    "ACCESS_NOTIFICATION_POLICY":5,
                    "ACCESS_WIFI_STATE":6,
                    "ACCOUNT_MANAGER":7,
                    "ADD_VOICEMAIL":8,
                    "BATTERY_STATS":9,
                    "BIND_ACCESSIBILITY_SERVICE":10,
                    "BIND_APPWIDGET":11,
                    "BIND_CARRIER_MESSAGING_SERVICE":12,
                    "BIND_CARRIER_SERVICES":13,
                    "BIND_CHOOSER_TARGET_SERVICE":14,
                    "BIND_CONDITION_PROVIDER_SERVICE":15,
                    "BIND_DEVICE_ADMIN":16,
                    "BIND_DREAM_SERVICE":17,
                    "BIND_INCALL_SERVICE":18,
                    "BIND_INPUT_METHOD":19,
                    "BIND_MIDI_DEVICE_SERVICE":20,
                    "BIND_NFC_SERVICE":21,
                    "BIND_NOTIFICATION_LISTENER_SERVICE":22,
                    "BIND_PRINT_SERVICE":23,
                    "BIND_QUICK_SETTINGS_TILE":24,
                    "BIND_REMOTEVIEWS":25,
                    "BIND_SCREENING_SERVICE":26,
                    "BIND_TELECOM_CONNECTION_SERVICE":27,
                    "BIND_TEXT_SERVICE":28,
                    "BIND_TV_INPUT":29,
                    "BIND_VOICE_INTERACTION":30,
                    "BIND_VPN_SERVICE":31,
                    "BIND_VR_LISTENER_SERVICE":32,
                    "BIND_WALLPAPER":33,
                    "BLUETOOTH":34,
                    "BLUETOOTH_ADMIN":35,
                    "BLUETOOTH_PRIVILEGED":36,
                    "BODY_SENSORS":37,
                    "BROADCAST_PACKAGE_REMOVED":38,
                    "BROADCAST_SMS":39,
                    "BROADCAST_STICKY":40,
                    "BROADCAST_WAP_PUSH":41,
                    "CALL_PHONE":42,
                    "CALL_PRIVILEGED":43,
                    "CAMERA":44,
                    "CAPTURE_AUDIO_OUTPUT":45,
                    "CAPTURE_SECURE_VIDEO_OUTPUT":46,
                    "CAPTURE_VIDEO_OUTPUT":47,
                    "CHANGE_COMPONENT_ENABLED_STATE":48,
                    "CHANGE_CONFIGURATION":49,
                    "CHANGE_NETWORK_STATE":50,
                    "CHANGE_WIFI_MULTICAST_STATE":51,
                    "CHANGE_WIFI_STATE":52,
                    "CLEAR_APP_CACHE":53,
                    "CONTROL_LOCATION_UPDATES":54,
                    "DELETE_CACHE_FILES":55,
                    "DELETE_PACKAGES":56,
                    "DIAGNOSTIC":57,
                    "DISABLE_KEYGUARD":58,
                    "DUMP":59,
                    "EXPAND_STATUS_BAR":60,
                    "FACTORY_TEST":61,
                    "GET_ACCOUNTS":62,
                    "GET_ACCOUNTS_PRIVILEGED":63,
                    "GET_PACKAGE_SIZE":64,
                    "GET_TASKS":65,
                    "GLOBAL_SEARCH":66,
                    "INSTALL_LOCATION_PROVIDER":67,
                    "INSTALL_PACKAGES":68,
                    "INSTALL_SHORTCUT":69,
                    "INTERNET":70,
                    "KILL_BACKGROUND_PROCESSES":71,
                    "LOCATION_HARDWARE":72,
                    "MANAGE_DOCUMENTS":73,
                    "MASTER_CLEAR":74,
                    "MEDIA_CONTENT_CONTROL":75,
                    "MODIFY_AUDIO_SETTINGS":76,
                    "MODIFY_PHONE_STATE":77,
                    "MOUNT_FORMAT_FILESYSTEMS":78,
                    "MOUNT_UNMOUNT_FILESYSTEMS":79,
                    "NFC":80,
                    "PACKAGE_USAGE_STATS":81,
                    "PERSISTENT_ACTIVITY":82,
                    "PROCESS_OUTGOING_CALLS":83,
                    "READ_CALENDAR":84,
                    "READ_CALL_LOG":85,
                    "READ_CONTACTS":86,
                    "READ_EXTERNAL_STORAGE":87,
                    "READ_FRAME_BUFFER":88,
                    "READ_INPUT_STATE":89,
                    "READ_LOGS":90,
                    "READ_PHONE_STATE":91,
                    "READ_SMS":92,
                    "READ_SYNC_SETTINGS":93,
                    "READ_SYNC_STATS":94,
                    "READ_VOICEMAIL":95,
                    "REBOOT":96,
                    "RECEIVE_BOOT_COMPLETED":97,
                    "RECEIVE_MMS":98,
                    "RECEIVE_SMS":99,
                    "RECEIVE_WAP_PUSH":100,
                    "RECORD_AUDIO":101,
                    "REORDER_TASKS":102,
                    "REQUEST_IGNORE_BATTERY_OPTIMIZATIONS":103,
                    "REQUEST_INSTALL_PACKAGES":104,
                    "RESTART_PACKAGES":105,
                    "SEND_RESPOND_VIA_MESSAGE":106,
                    "SEND_SMS":107,
                    "SET_ALARM":108,
                    "SET_ALWAYS_FINISH":109,
                    "SET_ANIMATION_SCALE":110,
                    "SET_DEBUG_APP":111,
                    "SET_PREFERRED_APPLICATIONS":112,
                    "SET_PROCESS_LIMIT":113,
                    "SET_TIME":114,
                    "SET_TIME_ZONE":115,
                    "SET_WALLPAPER":116,
                    "SET_WALLPAPER_HINTS":117,
                    "SIGNAL_PERSISTENT_PROCESSES":118,
                    "STATUS_BAR":119,
                    "SYSTEM_ALERT_WINDOW":120,
                    "TRANSMIT_IR":121,
                    "UNINSTALL_SHORTCUT":122,
                    "UPDATE_DEVICE_STATS":123,
                    "USE_FINGERPRINT":124,
                    "USE_SIP":125,
                    "VIBRATE":126,
                    "WAKE_LOCK":127,
                    "WRITE_APN_SETTINGS":128,
                    "WRITE_CALENDAR":129,
                    "WRITE_CALL_LOG":130,
                    "WRITE_CONTACTS":131,
                    "WRITE_EXTERNAL_STORAGE":132,
                    "WRITE_GSERVICES":133,
                    "WRITE_SECURE_SETTINGS":134,
                    "WRITE_SETTINGS":135,
                    "WRITE_SYNC_SETTINGS":136,
                    "WRITE_VOICEMAIL":137}

basePath = "/home/msprj_security/apkfile"
PUTIN = "/home/msprj_security/pngfile/"+ file_name

# Creates a list containing 12 lists, each of 12 items, all set to 0
w, h = 12, 12 
Matrix = [[0 for x in range(w)] for y in range(h)] 
permIndex = []

for root, dirs, files in os.walk(basePath):
    path = root.split('/')
    for file in files:
      	if file==file_name:
        	if file.endswith(".apk"): 
            		#print(file)
            		path = os.path.abspath(root) + '/' + file;
            		a= apk.APK(path);
            		Matrix = [[0 for x in range(w)] for y in range(h)] 
            		for p in a.get_permissions():
                		templst = p.split(".");
                		new_str = templst[len(templst)-1]
                		if new_str in permissionHash:
                    			index = permissionHash[new_str]
                    			permIndex.append(index)
                    			r = int(math.floor(index / 12))
                    			c = index % 12
                    			Matrix[r][c] = 1

			#print("PNG Generated for APK file:")
			#print(file)
            		#output_result_image(Matrix, PUTIN+"/"+file+".png")
	    		output_result_image(Matrix, PUTIN+".png")
			#print("=====================================")
