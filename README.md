# DoIt
## Introduzione
###### Progettazione collaborativa e matching di competenze (DOIT)

La piattaforma vuole promuovere lo svolgimento collaborativo di progetti innovativi e facilitare l’inserimento delle persone all’interno degli stessi progetti sulla base delle loro competenze. Allo stesso tempo vuole realizzare una vetrina dei progetti svolti in cui un qualsiasi cittadino sia dunque capace di ricercare e visualizzare le informazioni sui progetti e chi li ha svolti. In questo caso le funzionalità saranno fornite dalla piattaforma per soddisfare le esigenze dei seguenti attori:

- Proponente: sono organizzazioni articolate che vogliono inserire descrizioni di proposte di progetto identificando le caratteristiche principali. Tra queste verranno indicate le competenze e gli obiettivi del progetto.

- Progettista: sono persone o enti che hanno specifiche competenze e si candidano ai progetti. Hanno interesse dunque a poter inserire le proprie generalità e competenze al fine di poter essere selezionati o comunque al fine di supportare le proprie competenze. Ovviamente la piattaforma mantiene traccia dei progetti svolti che contribuiscono al curriculum del progettista sia esso ente o persona fisica (con effetto a cascata).

- Esperto: sono persone fisiche che possono essere coinvolti nei meccanismi di valutazione sia delle proposte di progetto sia di eventuali più cordate che rispondono alla stessa chiamata di progetto.

- Sponsor: è una persona o ente che ha interesse a finanziare un progetto pubblicato sulla piattaforma.

- Utente: iscritto alla piattaforma che non è un esperto / progettista / proponente / sponsor.


## Funzionalità implementate

Sono state implementate le seguenti funzionalità:

- Una persona o ente può iscriversi o accedere alla piattaforma ed assumere il ruolo di *Proponente*, *Progettista*, *Esperto* e *Sponsor*. Ha inoltre la possibilità di visualizzare i progetti presenti sulla piattaforma ed i profili dei vari iscritti.

- Un *Progettista* ha la possibilità di candidarsi ad  un progetto inviando un Invito oppure essere invitato da un Proponente a partecipare al progetto.

- Un *Proponente* ha la possibilità di proporre un Progetto. Ha la facoltà di chiudere le candidature al progetto ed effettuare il passaggio alla Fase successiva nello sviluppo del Progetto. Inoltre può accettare i progettisti che si sono candidati o rimuovere progettisti già presenti.

- Un *Esperto* viene interpellato da un *Proponente* quando quest’ultimo vuole far si che il proprio progetto venga valutato, inoltre durante la valutazione del progetto, l’esperto, valuta i progettisti che ne hanno preso parte. Il Proponente richiede la valutazione del Progetto tramite l’invio di un Invito.

- Di un Progetto vengono specificati il nome, gli obiettivi, i requisiti ed i Tag. Inoltre presenta uno Stato e una Fase che variano nel tempo.

- I possibili Stati in cui un Progetto si può trovare sono: *VALUTATO*, *IN_VALUTAZIONE*, *NON_VALUTATO*. Il primo sta a indicare che il progetto è stato valutato almeno una volta da un Esperto; il secondo sta a indicare che è stato richiesto ad un Esperto di valutare tale Progetto; il terzo, infine, sta a indicare che il Progetto non è mai stato valutato.

- Le fasi di un Progetto possono essere INIZIO, SVILUPPO e PUBBLICAZIONE. Esse indicano il punto in cui si trova il Progetto durante il suo ciclo di vita; inoltre determinano quali attività si possono svolgere sul Progetto. In particolare: 

  1. **fase di _INIZIO_**:
       - Vengono definiti i dettagli del progetto (il Progetto si deve trovare nello Stato NON_VALUTATO);
       - È possibile modificare i dettagli del progetto (il Progetto si deve trovare nello Stato NON_VALUTATO oppure in quello VALUTATO);
       - Vengono aggiunti Progettisti (tramite invito o candidatura) (il Progetto si deve trovare nello Stato NON_VALUTATO oppure in quello VALUTATO);
       - È possibile far valutare il progetto più volte (il Progetto si deve trovare nello Stato IN_VALUTAZIONE);
       - È possibile chiudere le candidature (il Progetto si può trovare in qualsiasi Stato)
       - Per passare alla fase successiva il Progetto non deve trovarsi nello Stato IN_VALUTAZIONE;
  2. **fase di _SVILUPPO_**:
       - È possibile modificare i dettagli del progetto;
       - Non sono accettate candidature;
       - È possibile invitare progettisti;
       - Il progetto non può esser sottoposto a valutazione;
       - Per passare alla fase successiva il Progetto non deve trovarsi nello Stato IN_VALUTAZIONE;
  3. **fase di _PUBBLICAZIONE_**:
       - Non è più possibile modificare il progetto;
       - Non è possibile invitare progettisti;

- Un Tag rappresenta una categoria. Può essere associato a più progetti che sono in qualche modo correlati a tale categoria. Presenta inoltre una descrizione.

- L’Invito è il meccanismo utilizzato per comunicare con gli altri iscritti. Esistono varie tipologie di invito: *PROPOSTA*, utilizzato quando il Proponente invita un Progettista a partecipare ad un Progetto; *RICHIESTA*, utilizzato quando il Progettista si vuole candidare ad un Progetto; *VALUTAZIONE*, utilizzato quando il Proponente vuole far valutare un Progetto.

## Tecnologie utilizzate

Il progetto ha una struttura client-server e prevede l’utilizzo dei seguenti framework: 

- **_Lato server_** spring boot e librerie dipendenti (spring jpa, mysql connector, spring security affiancato da JWT);
- **_Lato client_** angular, bootstrap, font awesome e ngx-toastr.

Sono stati utilizzati strumenti esterni tra cui DBMS MySql (database disponibile su server remoto) per la memorizzazione dei dati della piattaforma, Postman utilizzato per testare le chiamate REST e Spring Boot Test per la definizione dei casi di test backend. 

## Conclusione

Il progetto è nella fase iniziale del processo di sviluppo e non tutte le funzionalità sono state implementate (solamente le più importanti). È possibile apportare degli interventi adattivi e perfettivi: si potrebbe infatti creare una versione mobile del progetto (eventualmente utilizzando Ionic) o ancora aggiungere la possibilità di poter gestire la piattaforma da parte di uno staff (introducendo amministratore, moderatori, assistenza...). 


## Link: 

[API REST](https://docs.google.com/document/d/16OW7QSL1tU7NaCpgY1egUgvuEePVTtGo1WAbr2z3L4s/edit?usp=sharing)
[SITO DOIT](http://207.154.220.231/)
[GIT_HUB: VPP](https://github.com/damiano-cacchiarelli/DMR-DOIT-VP)  


## Damiano Cacchiarelli 
damiano.cacchiarelli@studenti.unicam.it
## Matteo Romagnoli
matteo02.romagnoli@studenti.unicam.it
## Roberto Cesetti
roberto.cesetti@studenti.unicam.it


