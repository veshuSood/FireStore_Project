🚨 Android Studio + Firebase Setup Hell (Emulator/Device + Firestore + Google APIs)
Tech Stack: Android Studio (Otter), Firebase Firestore, OnePlus Nord CE 4, Pixel 3 AVD Emulator

I was building a simple Firebase Firestore app (save data on button click) for practice. It was supposed to be a quick task but turned into a frustrating loop of device issues, emulator problems, and Google API/system image errors. At one point, dropping the whole idea of development actually felt like an option.

🐛 The Cascade of Failures (Recent Debug Sessions)
Issue #	Problem	Symptoms	Status
1	Real Device (OnePlus Nord CE 4) Not Detected	USB debugging ON, laptop sees MTP, Android Studio blind. No "Android Composite ADB Interface".	❌ Unresolved (Wi-Fi ADB workaround)
2	Emulator (AVD) No Internet	"Site can't be reached" in browser. INTERNET permission already added. Home Wi-Fi works fine on host.	⚠️ Intermittent
3	Firestore Data Not Saving	Button logs "clicked," but no collection/docs in console. Logcat initially empty or confusing.	❌ Blocked by net/device
4	Logcat Ghosting	Nothing useful appears. "Verbose" option not obvious. Clicking some help opens Google Docs instead of answers.	✅ Fixed (adb logcat + filters)
5	Permissions Panic	Confusion about needing Wi-Fi permission separately for Firestore.	✅ Clarified (only INTERNET needed)
6	Emulator Already Running	Message: "Pixel 3 is already running as process XXXX". Looked like an error at first.	✅ Understood (not an error)
7	Google APIs / System Image Error	Emulator complaining or misbehaving due to wrong/limited system image (no proper Google APIs/Play support).	✅ Fixed by using Google Play system image
🧩 Google APIs / System Image Error
One more hidden blocker was the Google APIs / system image configuration.

Initially, the AVD was created with a basic image that did not have proper Google APIs / Play support, which can affect apps relying on Google services and sometimes cause weird behavior.
​

The fix was to recreate or reconfigure the emulator using a Google Play system image (for example: an x86/x86_64 Google Play image from the SDK Manager / System Images section).
​

After switching to a Google Play image, the emulator behaved more reliably for Firebase-related work and Google services usage.
​

🔍 What I Tried (The Research Rabbit Holes)
Drivers/ADB for real device: Universal ADB, OEM drivers, revoking USB debugging repeatedly, checking for "Android Composite ADB Interface".

Emulator experiments: Wipe data, cold boot, recreate AVDs, play with DNS and host network, restarts and repairs of Android Studio.

Firestore setup: Test mode rules, correct package and project, INTERNET permission, adding logs and using Logcat filters.

Google API/system image fix: Switching from a plain Google APIs image to a Google Play system image so that Google services behave correctly on the emulator.
​

Wireless debugging: ADB over Wi‑Fi as a workaround when USB connection refused to cooperate.

💡 Hard-Won Lessons
Real device issues are often driver/ADB related, not just Developer Options or cables.

Emulator internet problems are usually host/DNS or emulator state issues, not app permissions.

Firestore failures can be completely silent if rules, network, or logging are not set up correctly.

System image choice matters: using a Google Play system image can avoid some Google API related issues on the emulator.
​

Debugging Android is heavily about environment: setup and tooling can block you even when the code is mostly correct.

🚀 Current Setup (What Works Now)
text
✅ Emulator runs (Pixel 3, API with Google Play image)
✅ Logcat usable (Verbose + filters + adb logcat)
✅ Firestore rules in test mode for development
✅ App button logs and internet permission added
⚠️ Wi‑Fi ADB used as a workaround for real device
❌ OnePlus physical device still not detected properly by Android Studio
This whole experience was exhausting, but it also forced deeper understanding of ADB, emulators, system images, and Firebase tooling. Hopefully this report saves someone else from the same spiral.
