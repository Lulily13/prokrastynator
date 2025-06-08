package com.tigers;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class Report2Test {

    private DataCollector collector;

    // DANE TESTOWE

    @BeforeEach
    void setUp() {
        collector = new DataCollector();

        Collection<Task> tasks = Arrays.asList(
                // 2024
                new Task("2024", "01", "01",
                        "Nowak_Anna", 5, "Projekt A", "Dev"),
                new Task("2024", "02", "10",
                        "Kowalski_Jan", 7, "Projekt A", "Bugfix"),
                new Task("2024", "03", "15",
                        "Nowak_Anna", 3, "Projekt B", "QA"),
                // 2023 – powinno zostać odfiltrowane
                new Task("2023", "01", "05",
                        "Nowak_Anna", 4, "Projekt A", "Stary rok")
        );
        collector.setTasks(tasks);
    }

    /* ====================================================================
       1.  Raport dla pojedynczego wpisu – nagłówek + 1 wiersz z projektem
       Gdy w roku 2024 istnieje dokładnie jeden task, raport ma zawierać nagłówek i dokładnie jeden wiersz z tym projektem i jego liczbą godzin.
       ==================================================================== */
    @Test
    @DisplayName("Jeden wpis → nagłówek + 1 projekt")
    void singleEntryReport() {
        DataCollector dc = new DataCollector();
        dc.setTasks(Collections.singletonList(
                new Task("2024", "06", "20",
                        "Nowak_Anna", 8, "Projekt X", "Task")
        ));

        Collection<String> lines = new Report2("2024").prepareReport(dc);

        assertEquals(2, lines.size());
        assertTrue(lines.stream().anyMatch(l -> l.contains("| Projekt X | 8.0h")));
    }

    /* ===============================================================
       2.  Dwa wpisy tego samego projektu (ten sam rok) → godziny się sumują
       Dwa zadania tego samego projektu w tym samym roku powinny zostać zsumowane do jednej pozycji;
       raport wyświetla łączną liczbę godzin (5 h) w jednym wierszu.
       ============================================================== */
    @Test
    @DisplayName("Sumowanie godzin, gdy projekt występuje kilka razy w tym samym roku")
    void sumsSameProjectHours() {
        DataCollector dc = new DataCollector();
        dc.setTasks(Arrays.asList(
                new Task("2024", "01", "01", "Anna", 3, "Projekt S", "Z1"),
                new Task("2024", "02", "10", "Anna", 2, "Projekt S", "Z2")
        ));

        Collection<String> lines = new Report2("2024").prepareReport(dc);

        assertTrue(lines.stream().anyMatch(l -> l.contains("| Projekt S | 5.0h")));
        assertEquals(2, lines.size());  // nagłówek + 1 wiersz
    }

    /* ===============================================================
       3.  Kilka projektów w danym roku – wszystkie powinny się pojawić
       Dla wielu projektów w tym samym roku raport zwraca wszystkie projekty oraz ich poprawne sumy godzin;
       w tym przypadku pojawiają się zarówno „Projekt A”, jak i „Projekt B”.
       ============================================================== */
    @Test
    @DisplayName("Raport zawiera wszystkie projekty z danego roku")
    void multipleProjectsAppear() {
        Collection<String> lines =
                new Report2("2024").prepareReport(collector);

        assertTrue(lines.stream().anyMatch(l -> l.contains("| Projekt A | 12.0h")));
        assertTrue(lines.stream().anyMatch(l -> l.contains("| Projekt B | 3.0h")));
        assertEquals(3, lines.size());  // nagłówek + 2 projekty
    }

    /* ===============================================================
       4.  Zadania z innego roku są ignorowane
       Zadania spoza roku zadanego w konstruktorze (tu: 2023) są uwzględniane tylko wtedy, gdy raport jest generowany właśnie dla tego roku;
       wówczas raport zawiera wyłącznie wpisy z 2023.
       ============================================================== */
    @Test
    @DisplayName("Zadania spoza wybranego roku są pomijane")
    void otherYearIgnored() {
        Collection<String> lines =
                new Report2("2023").prepareReport(collector);

        // w 2023 mamy tylko jeden task 4h dla Projektu A
        assertTrue(lines.stream().anyMatch(l -> l.contains("| Projekt A | 4.0h")));
        assertEquals(2, lines.size()); // nagłówek + 1 projekt
    }

    /* ===============================================================
       5.  Pusty DataCollector → tylko nagłówek
       Gdy DataCollector nie ma żadnych zadań, metoda zwraca listę składającą się wyłącznie z nagłówka – brak dodatkowych wierszy.
       ============================================================== */
    @Test
    @DisplayName("Pusty DataCollector zwraca wyłącznie nagłówek")
    void emptyCollectorReturnsHeader() {
        DataCollector empty = new DataCollector();
        Collection<String> lines =
                new Report2("2024").prepareReport(empty);

        assertEquals(1, lines.size());
        assertEquals("Lp | Projekt | Liczba godzin", lines.iterator().next());
    }

    /* ===============================================================
       6.  Nazwy projektów z różną wielkością liter traktowane osobno
       Raport traktuje nazwy projektów rozróżniając wielkość liter, więc „Projekt A” i „projekt a” pojawiają się jako osobne pozycje z własnymi sumami godzin.
       ============================================================== */

    @Test
    @DisplayName("Projekt 'projekt a' i 'Projekt A' są rozróżniane")
    void caseSensitiveProjectNames() {
        // kopiujemy na mutowalną listę
        List<Task> mutable = new ArrayList<>(collector.getTasks());
        mutable.add(new Task("2024", "04", "12",
                "Anna", 2, "projekt a", "LowerCase"));
        collector.setTasks(mutable);   // podmieniamy w kolektorze

        Collection<String> lines =
                new Report2("2024").prepareReport(collector);

        boolean upper = lines.stream().anyMatch(l -> l.contains("| Projekt A | 12.0h"));
        boolean lower = lines.stream().anyMatch(l -> l.contains("| projekt a | 2.0h"));
        assertTrue(upper && lower);
    }

    /* ===============================================================
       7.  Zadanie z 0h nie powinno wpłynąć na sumę
       Dodanie zadania z liczbą godzin równą 0 nie powinno zmienić sumy godzin projektu; wiersz dla „Projektu A” wciąż pokazuje 12.0 h.
       ============================================================== */
    @Test
    @DisplayName("Task z 0 godzin nie zmienia sum")
    void zeroHoursIgnored() {
        List<Task> mut = new ArrayList<>(collector.getTasks()); // kopia
        mut.add(new Task("2024", "05", "20",
                "Anna", 0, "Projekt A", "Zero"));
        collector.setTasks(mut);  // podmieniamy w kolektorze

        Collection<String> lines =
                new Report2("2024").prepareReport(collector);

        assertTrue(lines.stream().anyMatch(l -> l.contains("| Projekt A | 12.0h")));
    }

    /* ===============================================================
       8.  Zadanie z pustą nazwą projektu jest pomijane
       Zadania z pustą lub samymi spacjami w nazwie projektu powinny zostać pominięte – raport nie zawiera wiersza z pustą nazwą projektu.
       ============================================================== */

    @Test
    @DisplayName("Pusta nazwa projektu nie trafia do raportu")
    void emptyProjectNameIgnored() {

        List<Task> mut = new ArrayList<>(collector.getTasks());
        mut.add(new Task("2024", "06", "30",
                "Anna", 3, "", "Brak nazwy"));
        collector.setTasks(mut);

        Collection<String> lines =
                new Report2("2024").prepareReport(collector);


        boolean emptyRow =
                lines.stream().anyMatch(l -> l.matches("\\d+ \\|\\s*\\|.*"));
        assertFalse(emptyRow,
                "Raport nie powinien zawierać wiersza z pustą nazwą projektu");
    }
}
