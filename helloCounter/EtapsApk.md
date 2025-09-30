## 1) Générer l’APK

### Option 1 – Android Studio
1. Ouvrir le projet dans Android Studio.
2. Aller dans le menu **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**.
3. Une notification apparaîtra, cliquer sur **Locate** pour accéder au fichier généré.

📂 L’APK est situé dans :  

### Option 2 – Ligne de commande (Gradle)
Depuis le dossier racine du projet :
- Sous Windows (PowerShell) :
```powershell
.\gradlew assembleDebug

Le fichier app-debug.apk sera créé au même emplacement
 ---> app/build/outputs/apk/debug/app-debug.apk

