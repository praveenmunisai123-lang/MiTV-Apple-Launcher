Mi TV Apple-inspired launcher project

This is a starter Android TV HOME launcher project for Android 8.0+ (min SDK 26).
It does not uninstall or disable the Xiaomi/Google launcher or any streaming app.

Build without installing Android Studio:
1. Create/sign in to a GitHub account.
2. Create a new empty repository.
3. Upload the contents of this ZIP to the repository (keep the .github folder).
4. Open the repository's Actions tab.
5. Select "Build Android TV launcher" and choose "Run workflow" if it did not start automatically.
6. Wait for the workflow to finish successfully.
7. Open the completed workflow run, find Artifacts, and download
   "MiTV-Apple-Inspired-Launcher-APK".
8. Extract the downloaded ZIP to get app-debug.apk.

Install to TV:
- Keep ADB connected to 192.168.1.18:5555.
- From C:\platform-tools PowerShell run:
  .\adb.exe -s 192.168.1.18:5555 install -r "FULL_PATH_TO_app-debug.apk"
- Then press HOME on the TV remote. If Android asks which launcher to use,
  select "Mi TV Home" and choose "Always" only after testing.
- To return to the old launcher, use Android Settings > Apps > Mi TV Home >
  Clear defaults (wording may vary), then press HOME and choose Google TV Launcher.

Important:
- This is an initial lightweight launcher, not a full tvOS clone.
- It uses installed package IDs for Netflix, YouTube, Prime Video, Hotstar and Spotify.
  If an app is absent or has a different package ID, its tile will show an unavailable message.
- Test before changing defaults. Do not disable the original launcher.
- The APK produced by GitHub Actions is a debug-signed APK.
