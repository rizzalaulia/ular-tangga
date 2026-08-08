# 🐍🪜 Ular Tangga — Game Android Anak-Anak

Game ular tangga klasik untuk Android, dirancang khusus untuk anak-anak Indonesia.

## ✨ Fitur

- 🎮 **Multiplayer 2–5 Pemain** — Main bareng keluarga di satu HP
- 🤖 **vs Komputer** — Lawan AI dengan 3 level kesulitan
- 📴 **Fully Offline** — Tanpa internet, tanpa iklan, tanpa distraksi
- 👶 **Ramah Anak** — Desain ceria, animasi lucu, suara menyenangkan
- 💾 **Auto-Save** — Game otomatis tersimpan saat di-minimize

## 📱 Spesifikasi

| Item | Keterangan |
|------|------------|
| Platform | Android Only |
| Min Android | 7.0 (API 24) |
| Bahasa | Indonesia |
| Size | ~30 MB |
| Internet | Tidak diperlukan |

## 🛠 Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM + Clean Architecture
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)

## 📁 Struktur Proyek

```
app/src/main/java/com/ular_tangga/game/
├── domain/          # Game logic (Board, Snake, Ladder, Dice, Player)
│   ├── model/       # Data models
│   ├── engine/      # Game engine + AI
│   └── usecase/     # Business logic
├── ui/              # Compose UI
│   ├── screen/      # Halaman-halaman (Home, Game, Setup, dll)
│   ├── component/   # Komponen reusable (Board, Dice, Token)
│   ├── theme/       # Warna, font, tema
│   └── viewmodel/   # State management
├── data/            # Sound, SharedPreferences
└── UlarTanggaApp.kt
```

## 🚀 Mulai Develop

```bash
# Clone repo
git clone https://github.com/rizzal-au/ular-tangga.git
cd ular-tangga

# Buka di Android Studio
# atau build via CLI:
./gradlew assembleDebug
```

## 📋 Aturan Permainan

1. Papan 10×10 (100 kotak), nomor 1–100 zigzag
2. Lempar dadu (1–6) untuk bergerak
3. Mendarat di **kaki tangga** → naik ke atas
4. Mendarat di **kepala ular** → turun ke bawah
5. Dapat **angka 6** → lempar lagi
6. **3× 6 berturut** → giliran batal, pion balik
7. Sampai **tepat di kotak 100** → MENANG!

## 📄 Dokumentasi

- [PRD (Product Requirements Document)](PRD.md)

## 👨‍💻 Author

**Rizzal Aulia Ramadhan** (Master Peng)

## 📝 License

MIT License
