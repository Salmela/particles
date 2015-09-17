## Viikko 3

Olen saanut toteutettua lähes kaiken mitä halusin projektin tekevän. Olen aloittanut koodaamaan projektin tietorakenteita. Aloitin taulukko pohjaisesta nopeutus tietorakenteesta.

Aloitin kirjoittamaan yksikkö testejä. Yksikkä testejen tekeminen on osoittautun hivenen haasteelliseksi. Olen lisännyt testejä nopeutus rakenteiden rajapinnalle (Storage interface), jotta minun ei tarvisi kirjoittaa testejä jokaiselle nopeutus rakenteelle. Toteutan samalla tavalla simulaattorien testit(Model interface).

En aijo kirjoittaa testejä gui luokalle(Window class), koska se ei ole tapana. Simulaattori luokkia en aijo myöskään testata erikseen, koska niitä käytetään vain tietorakenteiden testaukseen. Eikä ne varsinaisesti kuulu projektiin. Pääluokkaa(App class) on haastavaa testata, koska se luo aina graaffisen ikkunan eikä se sisällä varsinaista logiikkaa.

Joudun varmaan koodaamaan tietorakeenteen partikkeli setille, joka käytännössä tulee oleemaan hajautus taulu. Olen käyttänyt tähän mennessä olen käyttänyt HashSettiä partikkeleiden kasaamiseen. Joudun myös ArrayStorage nopeutus rakenteelle koodaamaan linkitetyn listan tai dynaamisesti kasvavan taulukon.

En ole vielä aloittanu koodaamaan TreeStorage nopeutus rakenteen logiikaa enkä ole lisännyt sen tynkä tiedostoa vielä gittiin.
