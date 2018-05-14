INSERT INTO functionality_areas (functionality_number, title, parent)
VALUES ('0', 'Kravspec', NULL);

INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1', 'Del 1', '0', '', FALSE);

  INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
  VALUES ('1.1', 'Dokumentets struktur', '1', '', FALSE);

    INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
    VALUES ('1.1.1', 'Dokumentets struktur', '1.1', '', FALSE);

  INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
  VALUES ('1.2', 'Overordnet beskrivelse av ønsket løsning', '1', '', FALSE);

    INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
    VALUES ('1.2.1', 'Innledning','1.2', '', FALSE);
    INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
    VALUES ('1.2.2', 'Sentrale hensyn', '1.2', '', FALSE);
    INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
    VALUES ('1.2.3', 'Sentrale hensyn', '1.2', '', FALSE);

  INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
  VALUES ('1.3', 'Krav til ønsket løsning - veiledning til besvarelse', '1', '', FALSE);

    INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
    VALUES ('1.3.1', 'Innledning', '1.3', '', FALSE);
    INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
    VALUES ('1.3.2', ' Om utfylling av tabellene', '1.3', '', FALSE);

  INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
  VALUES ('1.4', 'Funksjonelle krav til ønsket løsning', '1', '', FALSE);

  INSERT INTO functionality_areas (functionality_number, title, description,
                                   explanation, consequence, parent, type, show_me)
  VALUES
  ('1.4.1', 'Krav til oppfyllelse av NOARK5-krav O og B',
   'Kravene i dette kapittelet sikrer at alle obligatoriske (O) og betingede obligatoriske (B) Noark5-krav oppfylles.',
   'Noark-standarden består av obligatoriske, betinget obligatoriske og valgfrie krav. Obligatoriske og betinget obligatoriske krav må være oppfylt for at løsningen skal anses å være Noark5-godkjent. Dette er en forutsetning for å kunne ivareta kravene stilt i den generelle lovgivningen som berører offentlig forvaltning.Det anbefales at det settes krav til at leverandøren om at korrekt og fullstendig uttrekk skal kunne produseres uten betydelig kostnad for kommunen. Uttrekket skal kunne godkjennes av kommunens arkivdepot.',
   'Ved manglende kravstilling omkring disse spørsmålene kan det være mulig at den leverte løsningen ikke tilfredsstiller de lovpålagte kravene til denne type arkivløsning.', '1.4', 'funksjonell', true);

  INSERT INTO functionality_areas (functionality_number, title, description,
                                 explanation, consequence, parent, show_me)
  VALUES
    ('1.4.2', 'Krav til ivaretakelse av spesielle bestemmelser',
     'Kravene her gjenspeiler at elektronisk saksbehandling i offentlig og kommunal sektor er underlagt ulike lover og forskrifter.',
     'Elektronisk saksbehandling er underlagt lovbestemte krav for elektronisk signatur. Kravene skal også sikre integritet, konfidensialitet, autentisitet.
For å sikre at organisasjonen etterlever disse bestemmelsene, må kravene i dette kapittelet oppfylles, og det anbefales at kravene stilles som obligatoriske.',
     'Ved manglende kravoppfyllelse innenfor disse områdene vil det være store svakheter og mangler med den tilbudte løsningen.', '1.4', true);

  INSERT INTO functionality_areas (functionality_number, title, description,
                                 explanation, consequence, parent, type, 
                                   show_me)
  VALUES
  ('1.4.3', 'Krav til brukergrensesnitt og brukervennlighet',
   'Kravene her sørger for at løsningen støtter brukernes oppgaver både visuelt og strukturelt, fremmer bruk av systemet på en egnet måte, og letter arbeidshverdagen ved å være så intuitiv som mulig, samt ved å tilby brukeren målrettet hjelp med oppgavene ved behov.',
   'En løsning med god brukervennlighet og et intuitivt brukergrensesnitt gir økt motivasjon i organisasjonen til å benytte løsningen i den daglige saksbehandlingen. Krav som stilles her vil derfor øke muligheten for god dokumentfangst og sikker dokumentforvaltning.',
   'Dersom valgt løsning er vanskelig å bruke, viser erfaringer at betydelige deler av saksbehandlingen foregår på utsiden av sak/arkiv-løsningen, og at organisasjonen dermed taper dokumentasjon som kan være av vesentlig verdi både for organisasjonen selv og for innbyggerne.', '1.4', 'funksjonell', true);


  INSERT INTO functionality_areas (functionality_number, title, description,
                                   explanation, consequence, parent, type, show_me)
  VALUES
  ('1.4.4', 'Krav til grunnleggende funksjonalitet for journalføring og 
  arkivering',
   'Rediger meg', 'Rediger meg', 'Rediger meg', '1.4', 'funksjonell', true);

INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.4.1', 'Krav til arkivstruktur, arkiv og arkivdeler', '1.4.4', 'funksjonell', FALSE);

INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.4.2', 'Krav til klassifikasjonssystem og klasse', '1.4.4', 'funksjonell', FALSE);

INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.4.3', 'Krav til mappe', '1.4.4', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.4.4', 'Krav til registrering', '1.4.4', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.4.5', 'Krav til dokumentbeskrivelse og dokumentobjekt', '1.4.4', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.4.6', 'Krav til nøkkelord', '1.4.4', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.4.7', 'Krav til kryssreferanse', '1.4.4', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.4.8', 'Krav til dokumentfangst', '1.4.4', 'funksjonell', FALSE);

INSERT INTO functionality_areas (functionality_number, title, description,
                                 explanation, consequence, parent, type, show_me)
VALUES ('1.4.5', 'Krav til gjenfinning – søk og rapporter', 'Rediger meg',
        'Rediger meg', 'Rediger meg', '1.4', 'funksjonell', true);

INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.1', 'Krav til søkefunksjonalitet', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.2', 'Krav til bevaring og kassasjon', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.3', 'Krav til periodisering', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.4', 'Krav til avlevering', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.5', 'Krav til administrasjon av kjernen', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.6', 'Krav til sletting av dokumenter', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.7', 'Krav til frysing av metadata og dokument', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.8', 'Krav til oppsplitting og sammenslåing av mapper, flytting av registreringer', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.9', 'Krav til presedens', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.10', 'Krav til masseimport utløst fra NOARK5-kjerne', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.11', 'Krav til elektroniske skjema for utfylling over internett', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.12', 'Krav til elektronisk dokumentutveksling', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.13', 'Krav til migrering mellom NOARK-løsninger', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.14', 'Krav til tilgjengeliggjøring av offentlig journal på internett', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.15', 'Krav til sikkerhet i kjernen', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.16', 'Krav til rettighetsangivelser', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.17', 'Krav til skjerming og gradering', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.18', 'Krav knyttet til arkivformat og andre format', '1.4.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.5.19', 'Krav knyttet til masseoppdatering', '1.4.5', 'funksjonell', FALSE);


INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('1.4.6', 'Krav til adresseregister', 'Rediger meg', 'Rediger meg', 
        'Rediger meg', '1.4', 'funksjonell', true);

INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me) VALUES
  ('1.4.7', 'Krav til saksbehandlingsløsninger',
   'å sikre at saksbehandlere og ledere opplever at systemet har funksjonalitet som støtter dem i deres daglige arbeog at bruken av systemet ikke krever mer innsats enn den gevinsten som systemet gir.',
   'Rediger meg', 'Rediger meg', '1.4', 'funksjonell', true);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.7.1', 'Krav til saksoppfølging', '1.1', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.7.2', 'Krav til dokumentproduksjon', '1.1', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me) 
VALUES ('1.4.7.3', 'Krav til maler', '1.4.7', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.7.4', 'Krav til saks- og dokumenthistorikk', '1.4.7', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.7.5', 'Krav til dokumentflyt', '1.4.7', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.7.6', 'Krav til arbeidsflyt', '1.4.7', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.7.7', 'Øvrige krav til saksbehandlerfunksjonalitet', '1.4.7', 'funksjonell', FALSE);

INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('1.4.8', 'Krav til E-post', 'Rediger meg', 'Rediger meg', 'Rediger 
meg', '1.4', 'funksjonell', true);

INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me) VALUES
  ('1.4.8.1', 'Overordnede krav til e-postfunksjonalitet',
   'Sikre at all kommunikasjon mottatt og sendt via e-post blir journalført, samt effektivisere hverdagen ved å tilrettelegge for utstrakt bruk av e-post i saksbe­handlingen.',
   'Rediger meg', 'Rediger meg', '1.4.8', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.8.2', 'Krav til dokumentfangst, e-posthode og e-postmelding', '1.4.8', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.8.3', 'Ekspedering av dokumenter med epost / som e-post', '1.4.8', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.8.4', 'Krav til import / registrering av innkommet e-post', '1.4.8', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.8.5', 'Krav til å sende kopi av saksdokumenter per e-post', '1.4.8', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.8.6', 'Krav til sikkerhetshåndtering av e-post', '1.4.8', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.8.7', 'Tidsstempling av e-post', '1.4.8', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.8.8', 'Ikke-benekting ved bruk av e-post', '1.4.8', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me) VALUES ('1.4.8.9', 'Konfidensialitet', '1.4.8', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.8.10', 'Kryptering av e-post', '1.4.8', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.8.11', 'Integritetssikring ved elektronisk signering', '1.4.8', 'funksjonell', FALSE);

INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('1.4.9', 'Krav til funksjonalitet for utvalgsbehandling', 'Rediger
meg', 'Rediger meg', 'Rediger meg', '1.4', 'funksjonell', true);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.9.1', 'Krav til utvalgsbehandling – administrativ sak', '1.4.9', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.9.2', 'Krav til utvalgsbehandling – generelle krav', '1.4.9', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.9.3', 'Krav til utvalgsbehandling – administrere beslutningsorgan', '1.4.9', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.9.4', 'Krav til utvalgsbehandling – forberede møte', '1.4.9', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.9.5', 'Krav til utvalgsbehandling – selve møtet', '1.4.9', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.9.6', 'Krav til utvalgsbehandling – etter møtet', '1.4.9', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.9.7', 'Krav til utvalgsbehandling – administrasjon av møtebehandlingen', '1.4.9', 'funksjonell', FALSE);

INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('1.4.10', 'Krav til funksjonalitet for byggesaksmodul', 'Rediger meg', 
        'Rediger meg', 'Rediger meg', '1.4', 'funksjonell', true);

INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me) VALUES
  ('1.4.11', 'Krav til funksjonalitet for publisering av offentlige 
  dokumenter', 'Rediger meg', 'Rediger meg',
   'Rediger meg', '1.4', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.11.1', 'Funksjonalitet og presentasjon', '1.4.11',
        'funksjonell', true);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.4.11.2', 'Krav til sikkerhet og tilgjengelighet for offentlig journal', '1.4.11', 'funksjonell', FALSE);


INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('1.5', 'Krav til rapporter og statistikker', 'Rediger meg', 'Rediger
meg', 'Rediger meg', '1', 'funksjonell', FALSE);


INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.5.1', 'Krav til anbefalte statistikker og rapporter jf. NOARK5',
        '1.5', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.5.2', 'Krav til andre rapporter og statistikker', '1.5',
        'funksjonell', FALSE);

INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('1.6', 'Krav til administrasjon av løsningen', 'Rediger meg', 'Rediger
 meg', 'Rediger meg', '1', 'funksjonell', FALSE);


INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.6.1', 'Overordnede krav til administrasjon av løsningen', '1.17',
        'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.6.2', 'Krav til administrativ struktur', '1.17', 'funksjonell',
        FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.6.3', 'Krav til brukeradministrasjon', '1.17', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.6.4', 'Krav til roller, rettigheter og brukers relasjon til adm
enhet, journalenhet og arkivdel', '1.17', 'funksjonell', FALSE);




INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('1.7', 'Krav til sikkerhet, sporbarhet og kontroll', 'Rediger meg', 'Rediger meg', 'Rediger meg', '1', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.7.1', 'Overordnede krav til sikkerhet', '1.7', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.7.2', 'Krav til roller og rettigheter', '1.7', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.7.3', 'Krav til autorisasjon og autentisering', '1.7', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.7.4', 'Krav til autorisasjon av stedfortredere', '1.7', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.7.5', 'Krav til identifisering av virksomhetseksterne brukere', '1.7', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.7.6', 'Krav til håndtering av historiske brukeridenter', '1.7', 'funksjonell', FALSE);
INSERT INTO functionality_areas (functionality_number, title, parent, type, show_me)
VALUES ('1.7.7', 'Krav til sikring av elektronisk avsendte og mottatte dokumenter', '1.7', 'funksjonell', FALSE);


INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('1.7.8', 'Krav til sporbarhet og kontroll', 'Rediger meg', 'Rediger
meg', 'Rediger meg', '1.7', 'funksjonell', FALSE);

INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('1.7.9', 'Krav til validering', 'Rediger meg', 'Rediger meg', 'Rediger
 meg', '1.7', 'funksjonell', FALSE);

INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('2', 'Del 2 - Tekniske krav til ønsket løsning', 'Rediger meg', 'Rediger meg', 'Rediger meg', '0', '', FALSE);
INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, show_me)
VALUES ('2.1', 'Krav til arkivdatabase', 'Rediger meg', 'Rediger meg', 'Rediger meg', '2',TRUE );
INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('2.2', 'Tekniske krav', 'Rediger meg', 'Rediger meg', 'Rediger meg', '2', '', FALSE);
INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('2.3', 'Krav til servere', 'Rediger meg', 'Rediger meg', 'Rediger meg', '2', '', FALSE);
INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('2.4', 'Krav til klienter', 'Rediger meg', 'Rediger meg', 'Rediger meg', '2', '', FALSE);
INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('2.5', 'Generelle tekniske krav', 'Rediger meg', 'Rediger meg', 'Rediger meg', '2', '', FALSE);

INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('3', 'Del 3 - Krav til integrasjoner i ønsket løsning', 'Rediger meg', 'Rediger meg', 'Rediger meg', '0', '', FALSE);
INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me) VALUES
  ('3.1', 'Overordnede og generelle krav knyttet til integrasjoner', 'Rediger meg', 'Rediger meg', 'Rediger meg',
   '3', '', FALSE);
INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('3.2', 'Krav til skanning', 'Rediger meg', 'Rediger meg', 'Rediger meg', '3', '', FALSE);
INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('3.3', 'Krav til mottak av elektroniske skjema', 'Rediger meg', 'Rediger meg', 'Rediger meg', '3', '', FALSE);
INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('3.4', 'Krav til integrasjon mot kartsystem', 'Rediger meg', 'Rediger meg', 'Rediger meg', '3', '', FALSE);
INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me) VALUES
  ('3.5', 'Krav til integrasjon mot fagsystemer og interne og eksterne systemer', 'Rediger meg', 'Rediger meg',
   'Rediger meg', '3', '', FALSE);
INSERT INTO functionality_areas (functionality_number, title, description, explanation, consequence, parent, type, show_me)
VALUES ('3.6', 'Krav til konvertering', 'Rediger meg', 'Rediger meg', 'Rediger meg', '3', '', FALSE);

