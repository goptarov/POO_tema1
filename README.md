Goptarov Gabriel
325CC

Dificultatea Implementării
Principalele provocări întâmpinate în realizarea acestui proiect au fost legate de arhitectura aplicației și gestionarea corectă a stării jocului. Dificultățile specifice au constat în:

Persistența Datelor (JSON): Implementarea citirii și scrierii corecte a stării jocului folosind biblioteca json-simple. Reconstituirea istoricului mutărilor și a pieselor capturate la încărcarea unui joc salvat (Load Game) a necesitat o logică detaliată pentru a sincroniza tabla de joc cu fișierul JSON.

Logica de Joc și Validări: Gestionarea algoritmilor pentru Check și Checkmate. A fost dificilă evitarea erorilor de tip ConcurrentModificationException în timpul simulării mutărilor pentru verificarea stării regelui.

Structura Proiectului: Organizarea claselor pe pachete (game, board, pieces) și configurarea corectă a classpath-ului pentru compilare și rulare, asigurând vizibilitatea corectă a claselor între pachete.
