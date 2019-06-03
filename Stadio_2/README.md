# Gestione della biglietteria di uno stadio.
*Un numero TOT di biglietti che possono essere acquistati presso N diverse rivendite.
Ciascun cliente puo' acquistare fino ad un massimo di MAX biglietti.
A ciascuna rivendita verra' assegnato inizialmente un lotto di L biglietti (L << 'TOT'): 
 - se questi si esauriscono, la rivendita potra' rifornirsi, a patto che rimangono ancora biglietti disponibili.
Il cliente puo' scegliere se pagare in contanti o tramite carta di credito:
 - Carta di credito: piu' breve, alta priorita'
 - Contanti: piu' lungo, bassa priorita'
Alcune rivendite accettano soltanto carta di credito, alcune accettano entrambi metodi.*
 
## Le cose di sincronizzare:
 - Quando le rivendite vogliono dei biglietti nello stesso tempo
 - L'accesso alla rivendita deve occorrere in ordine (coda per ogni rivendita)
 - I clienti verranno creati poi si metteranno a coda
 - Se il cliente riesce ad acquistare, va via
 - Se non, inizialmente compra quelli biglietti disponibili e rimane in fila aspettando che arrivino altri biglietti
 
## Le classi:
 - Biglietteria (risorsa da proteggere perche' piu' rivendite vogliono degli biglietti, quindi sincronizza)
 - Clienti (thread)
   - metodo run();
   - metti_in_coda(); // Devo proteggere inserimento in coda, 2 clienti tentino di occupare lo stesso posto in coda sincronizza
   - acquista();
 - Rivendite (risorsa da proteggere, quindi sincronizza)
   - servi_cliente(); // metti in coda, rimuovi dalla coda
   - alloca_lotto(); // sincronizzato per proteggere biglietti (mutual exclusion)
