Goptarov Gabriel
325CC

    Pentru Strategy Pattern am mutat logica de miscare a pieselor (getPossibleMoves) in clase noi pentru fiecare piesa ([pieceName]MoveStrategy). Interfata ChessPiece(implementata de clasa Piece) pastreaza functia getPossibleMoves, dar aceasta acum doar apeleaza functia getPossibleMoves din MoveStrategy.
    Factory Pattern-ul e implementat prin clasa PieceFactory, cu singura functie createPiece care creeaza o piesa in functie de parametrul dat.
    Singleton Pattern-ul e implementat doar pentru clasa Main, astfel incat variabilele din cursul unui joc sa persiste global indiferent de clasa in care le folosim.
    Am Organizat interfata grafica folosint clasa AppWindow, care este fereastra principala in care se pot deschide multe paneluri(JPanel). Fereastra principala implementeaza si interfata GameObserver, care impune 2 functii, onMovePiece() si onPlayerSwitch(). Amandoua fiind apelate dupa ce se muta o piesa si, respectiv, se schimba jucatorul.
    In timpul meciului, jucatorul poate apasa pe orice piesa de a sa, miscarile posibile ale piesei fiind evidentiate prin colorarea patratelor cu un violet deschis. Apoi poate apasa pe unul dintre patratele colorate pentru a muta piesa in locul respectiv sau poate selecta o alta piesa pentru mutare. Informatii despre cursul jocului vor fi mereu prezentate in bara de sus, iar 3 butoane pentru a renunta, pentru a salva si a iesi din joc si pentru a iesi din joc fara a salva se afla in bara de jos.
    Am implemetat si posibilitatea de a promova un pion, atunci cand acesta ajunge in partea cealalta a tablei, computerul mereu va promova intr-o regina, dar jucatorul va putea alege intre celelalte piese printr-un option dialog.
    O problema pe care o are programul este ca atunci cand dam resign la un joc aceste nu este sters din memorie, nu am reusit sa rezolv problema asta, s-ar putea sa mai fie si probleme in calcularea punctajelor, zona asta a codului e destul de dezordonata.

