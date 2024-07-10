# E-Notes
## Project ini dibuat untuk keperluan tugas besar mata kuliah Pemrograman Berorientasi Object, Dosen Pengampu : Aldy Rialdy Atmadja, MT.

E-Notes adalah aplikasi manajemen catatan dan tugas yang dibangun menggunakan Java dan framework Spring Boot. Aplikasi ini memungkinkan pengguna untuk membuat, mengedit, menghapus, dan melihat catatan serta tugas mereka dengan mudah.
### Result App https://e-notes-production.up.railway.app/login (masih terdapat bug dalam request)

## Pembuat
- Muhammad Rizki Al-Fathir (1227050093)
- Nabila Lailatanzila (1227050100)
- Muhammad Saifurridwani I'jazi (1227050094)

## Bahasa dan Framework
- Bahasa: Java
- Framework: Spring Boot

## Fitur Utama
- **Manajemen Catatan:** Tambah, edit, hapus, dan lihat catatan.
- **Manajemen Tugas:** Tambah, edit, hapus, dan lihat tugas.
- **Autentikasi Pengguna:** Sistem login dan registrasi pengguna.
- **Validasi Formulir:** Validasi input pengguna.
- **Toastr Notifications:** Notifikasi interaktif untuk aksi pengguna.

## Dependencies
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Web
- Spring Boot DevTools
- MySQL Connector/J
- Spring Boot Starter Test
- Lombok
- Spring Boot Starter Security
- Toastr (WebJar)

## Instalasi
1. **Clone repository:**
   ```
   git clone https://github.com/username/e-notes.git
   ```
2. Build proyek menggunakan maven
   ```
   mvn clean install
   ```
3. Jalankan Aplikasi
   ```
   mvn spring-boot:run
   ```
## Konfigurasi Database
Aplikasi ini menggunakan MySQL sebagai database. Pastikan Anda telah mengkonfigurasi `application.properties` dengan benar:
```
spring.datasource.url=jdbc:mysql://localhost:3306/enotes
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```
## Kontribusi
1. Fork repository ini.
2. Buat branch fitur (git checkout -b feature/AmazingFeature).
3. Commit perubahan Anda (git commit -m 'Add some AmazingFeature').
4. Push ke branch (git push origin feature/AmazingFeature).
5. Buat Pull Request.

   
