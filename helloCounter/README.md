
---

## ✅ DoD
- [ ] Thème `AppTheme` appliqué globalement.
- [ ] Textes centralisés dans `strings.xml`.
- [ ] Accessibilité validée (contentDescription, tailles, focus).
- [ ] APK debug compilé et installable.
- [ ] README + captures présents dans le dépôt.

---

## 🩹 Dépannage
- **Preview vide** → *Build → Rebuild Project*.
- **Texte non lu par TalkBack** → vérifier `contentDescription`.
- **APK non installable** → vérifier `applicationId`, désinstaller version précédente.
- **Couleurs illisibles** → désactiver `dynamicColor` si Android < 12.

---

## ⚡ Cheatsheet

```kotlin
// Thème global
AppTheme { MainScreen() }

// A11y pour icône signifiante
.semantics { contentDescription = stringResource(R.string.cd_increment) }

// Build APK
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
