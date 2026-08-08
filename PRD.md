# Product Requirements Document (PRD)
# 🐍🪜 Ular Tangga — Game Android Anak-Anak

**Versi:** 1.1
**Tanggal:** 09 Agustus 2026
**Penulis:** Rizzal Aulia Ramadhan (Master Peng) + Bejo
**Status:** Revisi — setelah review desain

---

## Daftar Isi

1. [Ringkasan Produk](#1-ringkasan-produk)
2. [Visi & Tujuan](#2-visi--tujuan)
3. [Target Pengguna](#3-target-pengguna)
4. [Mode Permainan](#4-mode-permainan)
5. [Aturan Permainan (Standar Ular Tangga)](#5-aturan-permainan)
6. [Papan Permainan](#6-papan-permainan)
7. [Desain Visual & UX](#7-desain-visual--ux)
8. [Teknologi & Arsitektur](#8-teknologi--arsitektur)
9. [Fitur AI / Komputer](#9-fitur-ai--komputer)
10. [Audio & Efek Suara](#10-audio--efek-suara)
11. [Spesifikasi Teknis](#11-spesifikasi-teknis)
    - [11.5 Progres & Koleksi](#115-progres--koleksi)
12. [Struktur Penyimpanan](#12-struktur-penyimpanan)
13. [Riset Aturan Ular Tangga](#13-riset-aturan-ular-tangga)
14. [Rencana Pengembangan](#14-rencana-pengembangan)
15. [Quality Assurance](#15-quality-assurance)
16. [Risiko & Mitigasi](#16-risiko--mitigasi)
17. [Lampiran](#17-lampiran)

---

## 1. Ringkasan Produk

**Ular Tangga** adalah game papan klasik yang dimainkan secara offline di perangkat Android. Game ini dirancang khusus untuk anak-anak dengan desain yang ramah, ceria, dan tanpa distraksi (no ads, no in-app purchase, no internet required). Mendukung mode multiplayer 2–5 pemain dalam satu perangkat, serta mode 1 pemain vs AI/Komputer.

| Item | Keterangan |
|------|------------|
| Nama Produk | Ular Tangga |
| Platform | Android Only |
| Koneksi Internet | Tidak diperlukan (fully offline) |
| Monetisasi | Gratis, tanpa iklan, tanpa IAP |
| Target Usia | 6–10 tahun (anak-anak SD) |
| Jumlah Pemain | 1–5 orang |
| Bahasa | Indonesia |

---

## 2. Visi & Tujuan

### Visi
Membuat permainan ular tangga digital yang menyenangkan, aman, dan edukatif untuk anak-anak Indonesia — tanpa gangguan iklan atau konten yang tidak pantas.

### Tujuan
- **Fun First:** Anak-anak senang bermain dan ingin bermain lagi.
- **Ramah Anak:** Desain warna ceria, animasi lucu, suara menyenangkan.
- **Offline Friendly:** Bisa dimainkan di mana saja tanpa internet.
- **Multiplayer Social:** Mendorong interaksi sosial antar anak/saudara/keluarga.
- **No Distraction:** Tanpa iklan, tanpa notifikasi, tanpa link ke luar.

---

## 3. Target Pengguna

### Pengguna Utama
- Anak-anak usia 6–10 tahun (SD)

### Pengguna Sekunder
- Orang tua yang ingin game aman untuk anak
- Kakak/adik yang bermain bersama

### Persona

| Persona | Usia | Kebutuhan |
|---------|------|-----------|
| **Dina** | 7 tahun | Mau main game seru di tablet, suka warna warni |
| **Raka** | 9 tahun | Mau tantang kakaknya main ular tangga |
| **Bunda Sari** | 32 tahun | Mau game yang aman untuk anak, tanpa iklan |

---

## 4. Mode Permainan

### 4.1 Mode Multiplayer (2–5 Pemain)
- Dimainkan bergantian di **satu perangkat yang sama**
- Setiap pemain memilih warna/token berbeda
- Giliran bergantian secara clockwise
- Cocok untuk bermain bareng keluarga/teman

### 4.2 Mode 1 Pemain vs Komputer (AI)
- Pemain manusia melawan 1–4 AI
- AI bermain **fair & random murni** (dadu standar 1–6, peluang sama)
- Tidak ada "tingkat kesulitan" — ular tangga adalah permainan keberuntungan murni, bukan skill
- Variasi diberikan lewat **jumlah AI lawan**, bukan "AI sulit vs AI mudah"

> **Catatan Desain:** Ular tangga itu 100% hoki. Tidak ada strategi, tidak ada skill ceiling. Membuat AI dengan dadu yang dicurangi (biased dice) hanya menciptakan ilusi kemenangan palsu dan tidak mendidik. Lebih jujur: semua pemain (manusia maupun AI) dapat dadu yang benar-benar acak.

### 4.3 Opsi Setup Game
Pemain dapat mengatur:
- **Jumlah pemain** (2–5)
- **Siapa saja pemainnya** (manusia vs komputer)
- **Nama setiap pemain**
- **Token/warna pion** (opsional)
- **Jumlah pemain AI** (1–4, kalau main vs AI)

---

## 5. Aturan Permainan

### 5.1 Aturan Standar (Berdasarkan Riset)

Berikut adalah aturan umum ular tangga yang berlaku di versi game ini:

#### Papan & Penomoran
- Papan berukuran **10×10 kotak** dengan total **100 kotak** bernomor 1–100.
- Penomoran dimulai dari **kiri bawah (kotak 1)** ke kanan, lalu baris berikutnya dari **kanan ke kiri**, dan seterusnya secara zigzag (*boustrophedon*).
- Kotak 100 ada di **kanan atas** papan.

#### Memulai Permainan
- Semua pemain melempar dadu satu kali.
- Pemain dengan angka tertinggi bermain duluan.
- Jika seri, pemain yang seri melempar lagi untuk menentukan urutan.
- Pemain bergantian secara clockwise.

#### Gerakan
- Setiap giliran, pemain melempar **1 dadu** (angka 1–6).
- Pion bergerak maju sejumlah kotak sesuai angka dadu.
- Pemain mengikuti urutan nomor kotak (zigzag).

#### Tangga 🪜
- Jika pion mendarat di **kaki/bawah tangga**, pemain langsung naik ke **ujung atas tangga**.
- Jika mendarat di **atas tangga**, pion tetap di situ (tidak turun).

#### Ular 🐍
- Jika pion mendarat di **kepala/atas ular**, pion langsung turun ke **ekor/bawah ular**.
- Jika mendarat di **bawah ular**, pion tetap di situ (tidak naik).

#### Dadu Angka 6
- Jika pemain mendapat angka **6**, setelah bergerak pemain **mendapat giliran lagi** (lempar dadu sekali lagi).
- **Tiga 6 berturut-turut:** Jika pemain mendapat 6 sebanyak 3 kali berturut-turut dalam satu giliran, seluruh giliran dibatalkan dan pion kembali ke posisi semula sebelum giliran dimulai.

#### Menang
- Pemain harus mendapat **angka tepat** untuk mendarat di kotak 100.
- Jika lemparan melebihi kotak 100, pion **tetap di posisi sebelumnya** (tidak bergerak).
- **Pemain pertama yang mendarat di kotak 100 adalah pemenang.**

#### Pion Sama Kotak
- Beberapa pion boleh berada di kotak yang sama. Tidak ada interaksi atau konflik.

### 5.2 Ringkasan Alur Permainan

```
1. Setup → Pilih mode, jumlah pemain, nama, token
2. Penentuan urutan → Semua lempar dadu, tertinggi duluan
3. Giliran dimulai:
   a. Pemain lempar dadu
   b. Pion bergerak maju
   c. Cek: Ada tangga? → Naik
   d. Cek: Ada ular? → Turun
   e. Cek: Dapat 6? → Lempar lagi
   f. Cek: 3x 6 berturut? → Pion balik ke posisi awal giliran
   g. Cek: Mendarat di 100? → MENANG!
4. Giliran berikutnya → Pemain berikutnya
5. Ulangi sampai ada pemenang
```

---

## 6. Papan Permainan

### 6.1 Spesifikasi Papan

| Item | Keterangan |
|------|------------|
| Grid | 10 × 10 |
| Total Kotak | 100 |
| Nomor | 1–100 (zigzag, kiri-kanan-kanan-kiri) |
| Orientasi | Portrait (default), Landscape (auto-rotate) |

### 6.2 Posisi Ular Standar

| No | Kepala (Atas) | Ekor (Bawah) |
|----|--------------|--------------|
| 1 | 16 | 6 |
| 2 | 47 | 26 |
| 3 | 49 | 11 |
| 4 | 56 | 53 |
| 5 | 62 | 19 |
| 6 | 64 | 60 |
| 7 | 87 | 24 |
| 8 | 93 | 73 |
| 9 | 95 | 75 |
| 10 | 98 | 78 |

### 6.3 Posisi Tangga Standar

| No | Bawah (Kaki) | Atas (Puncak) |
|----|-------------|---------------|
| 1 | 2 | 38 |
| 2 | 7 | 14 |
| 3 | 8 | 31 |
| 4 | 15 | 26 |
| 5 | 21 | 42 |
| 6 | 28 | 84 |
| 7 | 36 | 44 |
| 8 | 51 | 67 |
| 9 | 71 | 91 |
| 10 | 78 | 99 |

> **Catatan:** Posisi ini menggunakan layout standar yang umum. Bisa disesuaikan saat pengembangan untuk keseimbangan permainan.

### 6.4 Visual Papan

```
100  99  98  97  96  95  94  93  92  91
 81  82  83  84  85  86  87  88  89  90
 80  79  78  77  76  75  74  73  72  71
 61  62  63  64  65  66  67  68  69  70
 60  59  58  57  56  55  54  53  52  51
 41  42  43  44  45  46  47  48  49  50
 40  39  38  37  36  35  34  33  32  31
 21  22  23  24  25  26  27  28  29  30
 20  19  18  17  16  15  14  13  12  11
  1   2   3   4   5   6   7   8   9  10
```

---

## 7. Desain Visual & UX

### 7.1 Prinsip Desain

| Prinsip | Keterangan |
|---------|------------|
| **Ceria & Ramah Anak** | Warna-warna cerah, bentuk bulat/soft, karakter lucu |
| **Sederhana** | UI tidak ribet, anak 4 tahun bisa paham |
| **Feedback Visual** | Animasi jelas saat naik tangga, turun ular, dapat 6 |
| **No Distraksi** | Tanpa iklan, banner, popup, atau link ke luar |
| **Touchable Area Besar** | Tombol besar, mudah disentuh jari kecil |

### 7.2 Palet Warna

| Elemen | Warna | Keterangan |
|--------|-------|------------|
| Background | Langit biru muda | Ceria, menenangkan |
| Papan | Hijau rumput | Natural, kontras baik |
| Kotak Genap | Kuning gading | Lembut di mata |
| Kotak Ganji | Hijau muda terang | Variasi tanpa mengganggu |
| Ular | Merah oranye | Terlihat jelas, tapi tidak menyeramkan |
| Tangga | Coklat kayu | Natural, mudah dikenali |
| Tombol Utama | Biru cerah | Call-to-action jelas |
| Token Pemain | Merah, Biru, Hijau, Kuning, Ungu | 5 warna berbeda |

### 7.3 Karakter Ular & Tangga

- **Ular:** Desain kartun lucu, mata besar, senyum — **bukan** ular menyeramkan.
- **Tangga:** Tangga kayu dengan anak tangga berwarna-warni.
- **Token Pemain:** Bisa berupa hewan lucu (kucing, kelinci, anjing, bebek, katak, dan bisa ditambah di update mendatang).

### 7.4 Layout Layar

#### Menu Utama
```
┌─────────────────────────┐
│                         │
│    🐍 Ular Tangga 🪜    │
│                         │
│  [ ▶ MAIN BARENG ]      │
│  [ 🤖 MAIN VS KOMPUTER] │
│  [ ⚙️ PENGATURAN ]      │
│  [ ❓ CARA BERMAIN ]    │
│                         │
└─────────────────────────┘
```

#### Setup Game
```
┌─────────────────────────┐
│  ⬅ Kembali               │
│                         │
│  Jumlah Pemain:          │
│  [2] [3] [4] [5]        │
│                         │
│  Pemain 1: [_________]   │
│  Token: 🐱 🐰 🐶 🐥 🐸  │
│                         │
│  Pemain 2: [_________]   │
│  Token: 🐱 🐰 🐶 🐥 🐸  │
│                         │
│  [ MULAI MAIN! ]        │
│                         │
└─────────────────────────┘
```

#### Layar Permainan
```
┌─────────────────────────┐
│  Pemain 1 🐱  Pemain 2 🐰│
│  Kotak: 24    Kotak: 51  │
│                         │
│ ┌───────────────────┐   │
│ │                   │   │
│ │   PAPAN ULANG     │   │
│ │   TANGGA 10x10    │   │
│ │                   │   │
│ │                   │   │
│ │                   │   │
│ │                   │   │
│ │                   │   │
│ └───────────────────┘   │
│                         │
│   🎲 GILIRAN: PEMAIN 1  │
│                         │
│   [ 🎲 LEMPAR DADU ]   │
│                         │
│   📋 Riwayat:           │
│   P1: 🎲 4 → Kotak 24  │
│   P2: 🎲 6 → Kotak 45  │
│                         │
└─────────────────────────┘
```

### 7.5 Animasi

| Event | Animasi |
|-------|---------|
| Lempar dadu | Dadu berputar 3D, muncul angka |
| Gerak pion | Pion melompati kotak satu per satu |
| Naik tangga | Pion naik tangga dengan efek glow |
| Turun ular | Pion turun dengan efek slide lucu |
| Dapat 6 | Efek bintang + teks "DAPAT 6! LEMPAR LAGI!" |
| Menang | Confetti, tangan tepuk, teks "PEMENANG! 🎉" |
| 3x 6 | Efek "Yahh!" + pion kembali ke posisi awal |

---

## 8. Teknologi & Arsitektur

### 8.1 Tech Stack

| Layer | Teknologi | Alasan |
|-------|-----------|--------|
| **Bahasa Utama** | Kotlin | Bahasa resmi Android, modern, aman |
| **UI Framework** | Jetpack Compose | Declarative UI, modern, performa baik |
| **Architecture Pattern** | MVVM + Clean Architecture | Terstruktur, testable, scalable |
| **State Management** | Compose State + ViewModel | Responsive, lifecycle-aware |
| **Animasi** | Compose Animation API | Animasi halus, performa baik |
| **Sound** | SoundPool (Android SDK) | Low-latency audio playback |
| **Penyimpanan** | SharedPreferences | Untuk pengaturan sederhana |
| **Min SDK** | API 24 (Android 7.0) | Cakupan ~95% device Android |
| **Target SDK** | API 34 (Android 14) | Latest stable |
| **Build** | Gradle + Kotlin DSL | Standard Android build system |
| **Testing** | JUnit 5 + Compose Testing | Unit test + UI test |

### 8.2 Alasan Pilihan Teknologi

#### Mengapa Kotlin + Jetpack Compose?
- **Kotlin:** Declarative, coroutine built-in, null safety, 100% interoperable dengan Java. Industry standard untuk Android dev.
- **Jetpack Compose:** UI modern yang declarative — kode lebih sedikit, lebih mudah di-maintain, animasi built-in. Cocok untuk game 2D sederhana.
- **Alternatif yang ditolak:**
  - *Unity/Godot:* Terlalu berat untuk game papan sederhana, overkill.
  - *Java:* Lebih verbose, tidak se-modern Kotlin.
  - *Flutter:* Cross-platform tapi butuh Dart, komunitas Android native lebih besar.
  - *LibGDX:* Overkill untuk game non-action.

#### Mengapa MVVM + Clean Architecture?
- **Separation of concerns:** Game logic (domain) terpisah dari UI.
- **Testable:** Game rules bisa di-test tanpa UI.
- **Maintainable:** Mudah tambah fitur tanpa mengacaukan kode lain.

### 8.3 Arsitektur Aplikasi

```
┌─────────────────────────────────────────────┐
│                   UI LAYER                   │
│  ┌─────────┐ ┌──────────┐ ┌──────────────┐  │
│  │  Menu   │ │  Game    │ │  Settings    │  │
│  │  Screen │ │  Screen  │ │  Screen      │  │
│  └────┬────┘ └────┬─────┘ └──────┬───────┘  │
│       │           │              │           │
│  ┌────┴───────────┴──────────────┴───────┐   │
│  │           ViewModels                  │   │
│  └────────────────┬──────────────────────┘   │
├───────────────────┼──────────────────────────┤
│              DOMAIN LAYER                    │
│  ┌────────────────┴──────────────────────┐   │
│  │          Game Engine                  │   │
│  │  ┌─────────┐ ┌────────┐ ┌─────────┐  │   │
│  │  │  Board  │ │  Dice  │ │  Player │  │   │
│  │  └─────────┘ └────────┘ └─────────┘  │   │
│  │  ┌──────────┐ ┌────────┐ ┌────────┐  │   │
│  │  │  Snake   │ │ Ladder │ │  AI    │  │   │
│  │  └──────────┘ └────────┘ └────────┘  │   │
│  │  ┌────────────────────────────────┐   │   │
│  │  │  GameState (State Flow)        │   │   │
│  │  └────────────────────────────────┘   │   │
│  └───────────────────────────────────────┘   │
├──────────────────────────────────────────────┤
│               DATA LAYER                     │
│  ┌────────────┐  ┌──────────────────────┐    │
│  │ SharedPref │  │  Sound Manager       │    │
│  │ (Settings) │  │  (SoundPool)         │    │
│  └────────────┘  └──────────────────────┘    │
└──────────────────────────────────────────────┘
```

### 8.4 Struktur Folder

```
ular-tangga/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ulartangga/game/
│   │   │   │   ├── domain/
│   │   │   │   │   ├── model/
│   │   │   │   │   │   ├── Board.kt
│   │   │   │   │   │   ├── Player.kt
│   │   │   │   │   │   ├── Snake.kt
│   │   │   │   │   │   ├── Ladder.kt
│   │   │   │   │   │   ├── Dice.kt
│   │   │   │   │   │   ├── GameState.kt
│   │   │   │   │   │   └── GameConfig.kt
│   │   │   │   │   ├── engine/
│   │   │   │   │   │   ├── GameEngine.kt
│   │   │   │   │   │   └── AIPlayer.kt
│   │   │   │   │   └── usecase/
│   │   │   │   │       ├── RollDiceUseCase.kt
│   │   │   │   │       ├── MovePlayerUseCase.kt
│   │   │   │   │       └── CheckWinUseCase.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── theme/
│   │   │   │   │   │   ├── Color.kt
│   │   │   │   │   │   ├── Theme.kt
│   │   │   │   │   │   └── Type.kt
│   │   │   │   │   ├── screen/
│   │   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   │   ├── GameScreen.kt
│   │   │   │   │   │   ├── SetupScreen.kt
│   │   │   │   │   │   ├── HowToPlayScreen.kt
│   │   │   │   │   │   └── SettingsScreen.kt
│   │   │   │   │   ├── component/
│   │   │   │   │   │   ├── BoardView.kt
│   │   │   │   │   │   ├── DiceView.kt
│   │   │   │   │   │   ├── PlayerToken.kt
│   │   │   │   │   │   ├── ScoreBoard.kt
│   │   │   │   │   │   └── GameLog.kt
│   │   │   │   │   └── viewmodel/
│   │   │   │   │       ├── GameViewModel.kt
│   │   │   │   │       └── SettingsViewModel.kt
│   │   │   │   ├── data/
│   │   │   │   │   ├── SoundManager.kt
│   │   │   │   │   └── PreferencesManager.kt
│   │   │   │   └── UlarTanggaApp.kt
│   │   │   ├── res/
│   │   │   │   ├── raw/
│   │   │   │   │   ├── dice_roll.mp3
│   │   │   │   │   ├── ladder_climb.mp3
│   │   │   │   │   ├── snake_slide.mp3
│   │   │   │   │   ├── win_fanfare.mp3
│   │   │   │   │   └── extra_turn.mp3
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── snake_cartoon.xml
│   │   │   │   │   ├── ladder_wood.xml
│   │   │   │   │   └── tokens/
│   │   │   │   │       ├── token_cat.xml
│   │   │   │   │       ├── token_bunny.xml
│   │   │   │   │       ├── token_dog.xml
│   │   │   │   │       ├── token_duck.xml
│   │   │   │   │       └── token_frog.xml
│   │   │   │   └── values/
│   │   │   │       ├── strings.xml
│   │   │   │       └── colors.xml
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   │       └── java/com/ulartangga/game/
│   │           ├── domain/
│   │           │   ├── GameEngineTest.kt
│   │           │   ├── BoardTest.kt
│   │           │   └── DiceTest.kt
│   │           └── ui/
│   │               └── GameViewModelTest.kt
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── PRD.md
└── README.md
```

---

## 9. Fitur AI / Komputer

### 9.1 Filosofi AI

Ular tangga adalah permainan **keberuntungan murni** (pure luck). Tidak ada strategi, tidak ada skill, tidak ada decision-making. Oleh karena itu:

- **AI tidak memiliki "tingkat kesulitan"** — konsep ini tidak berlaku di game zero-skill
- AI menggunakan **dadu acak murni** (`Random.nextInt(1, 7)`), persis seperti pemain manusia
- Setiap pemain (manusia atau AI) memiliki **peluang menang yang sama** per lemparan dadu

### 9.2 Variasi Mode AI

Untuk memberikan variasi permainan:

| Jumlah AI | Deskripsi |
|-----------|-----------|
| 1 AI | 1 vs 1 dengan komputer |
| 2 AI | 1 manusia + 2 AI (3 pemain total) |
| 3 AI | 1 manusia + 3 AI (4 pemain total) |
| 4 AI | 1 manusia + 4 AI (5 pemain total) |

### 9.3 Karakter AI

- Setiap AI memiliki **nama bot yang lucu** (contoh: "Bot Kucing", "Bot Kelinci", dll)
- Setiap AI muncul dengan **token/avatar berbeda** agar mudah dibedakan
- AI menampilkan **topi/topi kecil** pada token untuk menunjukkan "ini komputer"

### 9.4 Waktu Tunggu AI
- AI "berpikir" selama **1–1.5 detik** sebelum menunjukkan hasil dadu
- Ini memberi pemain manusia waktu untuk melihat apa yang terjadi
- Bisa di-skip dengan tap layar
- Animasi AI berjalan **sama persis** seperti animasi pemain manusia (tidak ada spesial treatment)

---

## 10. Audio & Efek Suara

### 10.1 Daftar Sound

| Event | File | Durasi | Keterangan |
|-------|------|--------|------------|
| Lempar Dadu | dice_roll.mp3 | ~1s | Suara dadu bergulir |
| Naik Tangga | ladder_climb.mp3 | ~1.5s | Nada naik (ascending) |
| Turun Ular | snake_slide.mp3 | ~1.5s | Nada turun (descending) lucu |
| Dapat 6 | extra_turn.mp3 | ~0.8s | Nada ceria, "Yay!" |
| 3x 6 | three_sixes.mp3 | ~1s | Nada kecewa lucu, "Yahh~" |
| Menang | win_fanfare.mp3 | ~3s | Tepuk tangan + fanfare |
| Tombol Tekan | button_click.mp3 | ~0.3s | Klik ringan |
| Background Music | bgm_playful.mp3 | Loop | Musik ceria, bisa dimute |

### 10.2 Pengaturan Audio

- **Master Volume:** Slider 0–100%
- **Sound Effects:** Toggle on/off
- **Background Music:** Toggle on/off
- Default: Semua aktif, volume 80%

---

## 11. Spesifikasi Teknis

### 11.1 Persyaratan Minimum

| Item | Minimum | Recommended |
|------|---------|-------------|
| Android Version | 7.0 (API 24) | 10+ (API 29) |
| RAM | 2 GB | 4 GB+ |
| Storage | 50 MB | 100 MB |
| Layar | 4.7" | 5.5"+ |
| Orientasi | Portrait | Portrait + Landscape |

### 11.2 Performa

| Metrik | Target |
|--------|--------|
| App Launch | < 2 detik |
| Frame Rate | 60 FPS saat animasi |
| Memory Usage | < 150 MB |
| APK Size | < 30 MB |
| Battery Impact | Minimal (game ringan) |

### 11.3 Kompatibilitas

- Mendukung **dark mode** (opsional, mengikuti sistem)
- Mendukung **font scaling** (aksesibilitas)
- **Tidak crash** jika device di-rotate saat bermain
- **Auto-save** posisi game jika app di-minimize

---

## 11.5 Progres & Koleksi

Untuk menjaga anak-anak tetap tertarik (retensi), game menyediakan sistem progres ringan:

### Statistik Pemain
- Total permainan dimainkan
- Total kemenangan per nama pemain
- "Lagi hoki!" — reaksi saat pemain menang beberapa kali berturut-turut

### Koleksi Token (Coming Soon / Update)
- 5 token bawaan (gratis, semua terbuka dari awal)
- Bisa ditambah token hewan baru di update mendatang (panda, kura-kura, burung hantu)
- Tampilan token tetap sederhana — tidak ada sistem "buka dengan koin" atau IAP

> **Catatan Desain:** Semua token tersedia gratis dari awal. Progression di sini murni untuk rasa pencapaian, bukan untuk monetisasi. Game tetap zero-ads, zero-IAP.

---

## 12. Struktur Penyimpanan

### 12.1 SharedPreferences

```json
{
  "settings": {
    "sound_effects": true,
    "background_music": true,
    "master_volume": 80,
    "player_names": ["Pemain 1", "Pemain 2"]
  },
  "stats": {
    "total_games_played": 25,
    "total_wins": {"Raka": 12, "Dina": 10, "AI Bot": 3},
    "last_played": "2026-08-08T10:30:00Z"
  }
}
```

### 12.2 Data Tersimpan

| Data | Penyimpanan | Keterangan |
|------|-------------|------------|
| Pengaturan | SharedPreferences | Persisten |
| Posisi terakhir | SharedPreferences (in-memory) | Auto-save saat pause, reset saat game selesai |
| Statistik pemain | SharedPreferences | Total main & total menang per nama pemain |
| High score | Tidak ada | Game ini tanpa skor |

---

## 13. Riset Aturan Ular Tangga

### 13.1 Asal Usul

Ular tangga berasal dari **India kuno** (abad ke-2 Masehi) dengan nama **Moksha Patam**. Permainan ini awalnya mengajarkan moralitas:
- **Tangga** = kebajikan (iman, kerja keras, kedermawanan)
- **Ular** = keburukan (kemarahan, keserakahan, kemalasan)

Dibawa ke Inggris pada tahun 1890-an dan menjadi populer di seluruh dunia.

### 13.2 Aturan Variasi yang Ditemukan

| Variasi | Keterangan | Digunakan? |
|---------|-----------|------------|
| Bounce-back | Jika lempar melebihi 100, pion memantul mundur | ❌ Tidak |
| Exact win | Harus tepat sampai 100 | ✅ Ya |
| Extra turn on 6 | Dapat giliran lagi saat dadu 6 | ✅ Ya |
| Three 6s rule | 3x 6 berturut = giliran batal | ✅ Ya |
| Enter on 6 | Pion baru masuk papan kalau dadu 6 | ❌ Tidak |
| Kick out | Pion lawan dikembalikan kalau kotak sama | ❌ Tidak |

### 13.3 Matematika Permainan

- **Rata-rata giliran untuk menang:** ~39 lemparan dadu (dari posisi 1 ke 100)
- **Rata-rata durasi permainan:** 15–45 menit
- **Peluang menang per giliran:** Bergantung posisi saat ini
- **Game selalu menghasilkan pemenang.**

---

## 14. Rencana Pengembangan

### Fase 1: Core Game (Minggu 1–2)
- [ ] Setup project Android + Kotlin + Compose
- [ ] Implementasi Board model (10×10, snake, ladder)
- [ ] Implementasi Dice (random 1–6)
- [ ] Implementasi Player model
- [ ] Game engine (giliran, gerakan, cek snake/ladder)
- [ ] Unit tests untuk game rules

### Fase 2: UI/Game Screen (Minggu 3–4)
- [ ] Menu utama
- [ ] Setup screen (pilih mode, nama pemain)
- [ ] Board view (papan visual dengan snake & ladder)
- [ ] Dice view (animasi lempar dadu)
- [ ] Player token rendering
- [ ] Giliran indicator
- [ ] Game log / riwayat

### Fase 3: Animasi & Audio (Minggu 5)
- [ ] Animasi gerak pion
- [ ] Animasi naik tangga
- [ ] Animasi turun ular
- [ ] Animasi lempar dadu
- [ ] Sound effects
- [ ] Background music

### Fase 4: AI & Polish (Minggu 6)
- [ ] AI player (dadunya acak murni)
- [ ] Nama & avatar lucu untuk bot AI
- [ ] Statistik menang/kalah per pemain
- [ ] Animasi menang (confetti)
- [ ] Auto-save game
- [ ] Pengaturan (volume, dll)
- [ ] How to play screen
- [ ] Testing di berbagai device (termasuk low-end)

### Fase 5: Release (Minggu 7)
- [ ] Bug fixing
- [ ] Performance optimization
- [ ] Play testing dengan anak-anak
- [ ] Screenshot & deskripsi Play Store
- [ ] Build APK release
- [ ] Publish ke Google Play Store

---

## 15. Quality Assurance

### 15.1 Test Case Utama

| No | Test Case | Expected Result |
|----|-----------|-----------------|
| 1 | Lempar dadu normal (angka 1–5) | Pion bergerak maju sesuai angka |
| 2 | Lempar dadu dapat 6 | Pion bergerak + dapat giliran lagi |
| 3 | 3x dapat 6 berturut-turut | Pion kembali ke posisi awal giliran |
| 4 | Mendarat di kaki tangga | Pion naik ke atas tangga |
| 5 | Mendarat di atas tangga | Pion tetap di posisi |
| 6 | Mendarat di kepala ular | Pion turun ke ekor ular |
| 7 | Mendarat di bawah ular | Pion tetap di posisi |
| 8 | Lempar melebihi 100 | Pion tetap di posisi sebelumnya |
| 9 | Lempar tepat sampai 100 | Pemain menang, animasi kemenangan |
| 10 | Multiplayer giliran | Urutan giliran benar, clockwise |
| 11 | AI bermain | AI bergerak dengan dadu acak murni, animasi normal |
| 12 | Auto-save | Game tersimpan saat minimize |
| 13 | Resume game | Posisi dan giliran pulih dengan benar |
| 14 | Rotate device | Layout menyesuaikan, tidak crash |
| 15 | 5 pemain + AI | Semua berjalan tanpa bug |

### 15.2 Testing Device

| Device | Android Version | RAM | Resolusi |
|--------|----------------|-----|----------|
| Samsung Galaxy A12 | Android 11 | 4 GB | 720 × 1600 |
| Xiaomi Redmi 9 | Android 10 | 4 GB | 1080 × 2340 |
| Realme C2 (low-end) | Android 9 | 2 GB | 720 × 1560 |
| Samsung Galaxy J2 (low-end) | Android 7 | 2 GB | 540 × 960 |
| Pixel 6 | Android 14 | 8 GB | 1080 × 2400 |
| Tablet Samsung Tab A7 | Android 11 | 3 GB | 1200 × 2000 |

---

## 16. Risiko & Mitigasi

| Risiko | Dampak | Mitigasi |
|--------|--------|----------|
| Anak bosan (game keberuntungan) | Retensi rendah | Animasi seru, token lucu, statistik kemenangan, mode vs AI |
| Device low-end lag | UX buruk | Optimasi animasi, rendering sederhana, target 60fps |
| Bug auto-save corrupt | Data hilang | In-memory save + validasi saat resume |
| Sound tidak playback | Pengalaman kurang | Fallback tanpa sound, graceful degradation |
| Play Store rejection | Delay release | Ikuti semua guideline Google Play Family |

---

## 17. Lampiran

### A. Referensi
- Wikipedia: Snakes and Ladders — https://en.wikipedia.org/wiki/Snakes_and_ladders
- Board and Pieces: Snakes & Ladders Rules — https://sites.google.com/site/boardandpieces/list-of-games/snakes-and-ladders
- Yellow Mountain Imports: How to Play — https://www.ymimports.com/pages/how-to-play-snakes-and-ladders

### B. Icon Tokens (SVG/XML)

| Token | Hewan | Warna |
|-------|-------|-------|
| 🐱 | Kucing | Merah |
| 🐰 | Kelinci | Biru |
| 🐶 | Anjing | Hijau |
| 🐥 | Bebek | Kuning |
| 🐸 | Katak | Ungu |

---

*Document generated by Padepokan Digital — Bejo*
*Untuk Master Peng (Rizzal Aulia Ramadhan)*
