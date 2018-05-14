



INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  1, 1,'O (i)', '1.4',
  'Leverandøren skal gi en oversiktlig, overordnet og kortfattet beskrivelse av funksjonalitet, arkitektur, grensesnitt og virkemåte for den tilbudte løsningen. \n Herunder skal også funksjoner som er under utvikling eller under planlegging tilsvarende, og det skal angis planlagt ferdig-dato.'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  2, 0, null, '1.4.1',
  'Å ivareta kravene stilt i den generelle lovgivningen som berører offentlig forvaltning.'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  3, 1, 'O', '1.4.1',
  'Tilbudt løsning skal være godkjent jfr. NOARK5-standarden. Kopi av egenerklæringsskjema for NOARK5-godkjenning skal vedlegges.'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  4, 2, 'O', '1.4.1',
  'Tilbudt sak- og arkivløsning skal oppfylle alle obligatoriske krav til indre og ytre kjerne jf. NOARK5-standarden.'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  5, 3, 'O', '1.4.1',
  'Tilbudt sak- og arkivløsning skal oppfylle alle obligatoriske krav jf. NOARK5-standardens kapittel 7 - 13.'
);


INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  6, 4, 'O', '1.4.1',
  'Tilbudt sak- og arkivløsning skal oppfylle alle krav til indre og ytre kjerne merket B (Betinget obligatorisk) med unntak av de som spesifikt gjelder fagsystemer.'
);


INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  7, 5, 'O', '1.4.1',
  'Leverandøren skal ta ansvar for at uttrekk til arkivdepot fra levert løsning er korrekt og blir godkjent av oppdragsgivers arkivdepot.'
);


INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  8, 6, '1', '1.4.1',
  '«Track a case» - Spor saken: Det bør være mulig for innbyggerne å følge med på status for innmeldt sak. Eksempelvis ved bruk av eget sporingsnummer.'
);


INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  20, 1, 'O', '1.4.2',
  'Elektronisk saksbehandling er en forutsetning og kravene i eForvaltningsforskriften må følges.'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  21, 2, 'O', '1.4.2',
  'Løsningen skal tilfredsstille kravene i Kravspesifikasjonen for PKI i offentlig sektor. http://www.regjeringen.no/nb/dep/fad/aktuelt/nyheter/2010/ny-kravspesifikasjon-for-pki-i-offentlig.html?id=621773 (v. 2.0)'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  30, 0, null, '1.4.3',
  'Å sørge for at systemløsningen som velges både visuelt og strukturelt støtter brukernes oppgaver, fremmer bruk av systemet på en egnet måte, og letter arbeidshverdagen ved å være så intuitiv som mulig, samt ved å tilby brukeren målrettet hjelp med oppgavene ved behov.'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  31, 1, '1', '1.4.3',
  'Løsningen skal ha samme utseende/utforming av brukergrensesnitt for alle brukergrupper slik at intern brukerstøtte på tvers av fagområder lettes, og brukere lett kan kjenne seg igjen.'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  32, 2, '1', '1.4.3',
  'Løsningen skal fungere på en slik måte at brukeren kun har tilgang til/må forholde seg til informasjon og funksjoner som er relevante for den rollen brukeren har, og den oppgaven som skal utføres. Et eksempel på dette kan være at brukeren kun skal forholde seg til skjermbilder og funksjoner relevante for den sakstypen som behandles.'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  33, 3, '1', '1.4.3',
  'Løsningen skal inneholde en hjelpefunksjon som tilbyr målrettet hjelp til brukeren når denne er i gang med en arbeidsoperasjon. Med målrettet menes at brukeren skal få tilgang til hjelp knyttet til den oppgaven / det arbeidsområdet som er relevant, og skal slippe å lete igjennom dokumentasjon for å finne denne informasjonen. Hjelpefunksjonen skal oppdateres samtidig med oppgraderinger, nye versjoner etc. av programvaren for øvrig.'
);


INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  34, 4, 'O', '1.4.3',
  'Navigasjon i løsningen skal utelukkende baseres på løsningens menyelementer eller andre funksjoner for
navigasjon. Det skal ikke være nødvendig for brukeren å benytte navigasjonsmulighetene i nettleser for å f.eks. gå ett eller flere steg «tilbake» i løsningen eller for å avbryte en arbeidsprosess/oppgave.'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  35, 5, '1', '1.4.3',
  'Løsningen skal være konsistent på alle områder, inkludert menyer, prinsipper for navigasjon, tilbakemeldinger til brukerne, etc.'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  36, 6, 'O', '1.4.3',
  'Feilmeldinger og andre meldinger skal være på norsk. Dersom brukeren setter i gang en oppgave som det forventes vil ta tid, må brukeren få beskjed om dette.'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  37, 7, '2', '1.4.3',
  'Feilmeldinger og andre meldinger bør være allment forståelige.'
);


INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  38, 1, '1', '1.4.4',
  'Det skal kunne opprettes underarkiv til et arkiv.'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  39, 1, '1', '1.4.4',
  'Ved avslutting av en arkivdel skal alle sakene i arkivdelen kunne avsluttes i én operasjon. Funksjonen skal være rettighetsstyrt.'
);

INSERT INTO requirements (id, show_order, priority, functionality,
                          requirement_text)
VALUES (
  40, 1, '1', '1.4.4',
  'Det skal være mulig å avslutte mange arkivsaker samtidig. Funksjonen skal være rettighetsstyrt.'
);

