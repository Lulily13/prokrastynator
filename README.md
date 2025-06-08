# 🕓 Prokrastynator

**Aplikacja konsolowa Java 22 do analizy i generowania rocznych raportów czasu pracy z danych `.xlsx`.**
Pozwala zapanować nad czasem pracy i projektami — nawet jeśli lubisz odwlekać sprawy na później 😉

---

## 📌 Kluczowe informacje

* **Język**: Java 22
* **Build**: Maven
* **Typ aplikacji**: Konsolowa (CLI)
* **Wejście**: Pliki `.xlsx` z danymi o czasie pracy
* **Wyjście**: Raporty w konsoli, plikach `.xlsx` i `.pdf` z wykresami
* **Testy**: JUnit 5

---

## 🗂️ Struktura danych wejściowych
## 📁 Organizacja folderów i plików YAML

📂 dane/
┣ 📂 2024/
┃ ┣ 📂 01/
┃ ┃ ┣ 📄 jan_kowalski.xlsx
┃ ┃ ┣ 📄 anna_nowak.xlsx
┃ ┃ ┗ ...
┃ ┣ 📂 02/
┃ ┃ ┣ 📄 jan_kowalski.xlsx
┃ ┃ ┣ 📄 anna_nowak.xlsx
┃ ┗ ...
┣ 📂 2025/
┃ ┣ 📂 06/
┃ ┃ ┣ 📄 jan_kowalski.xlsx
┃ ┃ ┣ 📄 anna_nowak.xlsx
┃ ┗ ...

Folder `2025/` zawiera podfoldery miesięczne (`01/`, `02/`, ..., `12/`), w których znajdują się pliki `.xlsx` z danymi dla poszczególnych osób.

Nazwa pliku: np. jan_kowalski.xlsx, anna.nowak.xlsx

📄 Struktura pliku pracownika (Excel)
Każdy plik .xlsx zawiera zakładki (arkusze) z nazwami projektów.

W każdej zakładce dane w formacie:

Data	Zadanie	Godzina
13.01.2025	Wizyta u klienta	3
19.01.2025	Analiza wymagań	5
21.01.2025	Spisanie dokumentu wymagań	7
22.01.2025	Prezentacja dla klienta	2
23.01.2025	Spotkanie po prezentacji, podsumowanie i wnioski	1

🧾 Kolumny:
Kolumna	Format	Opis
Data	dd.MM.yyyy	Data wykonania zadania
Zadanie	Tekst	Opis zadania (opcjonalnie z prefixem, np. #bugfix)
Godzina	Liczba (int/double)	Liczba przepracowanych godzin


---

## 📊 Typy raportów

1. **Godziny pracy wszystkich pracowników** w danym roku
   ➤ + wykres słupkowy

2. **Łączna liczba godzin pracy nad projektami** w danym roku
   ➤ + wykres słupkowy

3. **Raport roczny danego pracownika z podziałem na miesiące** – ile godzin spędził nad projektami

4. **Udział procentowy projektów w czasie pracy pracownika** w ujęciu rocznym
   ➤ + wykres kołowy

5. **Raport zadaniowy** w ujęciu rocznym:

   * Lista zadań posortowana wg czasu (najdłużej → najkrócej)
   * Filtrowanie po typie zadania (e.g. `#bugfix`, `#feature`)

6. **Najczęstsze etykiety** – na podstawie danych projektowych lub pracownika w ujęciu rocznym

---

## ⚙️ Jak używać

1. Uruchom aplikację:

   ```bash
   java -jar target/prokrastynator.jar
   ```

2. Wybierz rok i rodzaj raportu z poziomu konsoli:

   ```
   Podaj rok: 2025
   Wybierz raport:
   [1] Godziny wszystkich pracowników
   [2] Godziny projektów
   [3] Raport pracownika
   ...
   Podaj imię i nazwisko: Jan_Nowak
   ```

---

## 🧪 Testy

* **JUnit 5** – pokrycie każdego raportu testami jednostkowymi
* Testy uruchamiane przez:

  ```bash
  mvn test
  ```

---

## 🔧 Budowanie i uruchamianie

### Wymagania

* Java 22 (ZGC, Records, Pattern Matching, Virtual Threads itd.)
* Maven 3.8+

### Build & Run

```bash
mvn clean package
java -jar target/prokrastynator.jar
```

---

## 📈 Eksporty i wykresy

* **Eksport do `.xlsx`** – dane tabelaryczne
* **Eksport do `.pdf`** – wraz z wykresami (słupkowe i kołowe)
* Wykresy tworzone za pomocą biblioteki:

  * `XChart` (lekkie i łatwe w PDF)

---

## 🧠 Przyszłe kierunki

* [ ] Automatyczna klasyfikacja typów zadań (prefixy)
* [ ] Historia raportów użytkownika
* [ ] Obsługa wielu języków
* [ ] Potencjalna wersja z GUI (JavaFX / Spring Shell)

---

## 📜 Licencja

Projekt open-source, licencja **MIT**.

---
