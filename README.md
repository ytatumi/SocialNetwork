# SocialNetwork

**Gruppuppgift: Socialt Nätverk**

**Beskrivning**

Ni ska designa ett förenklat socialt nätverk.

**Krav**

* Abstrakt klass User med namn, e-post och abstrakt metod postMessage().
* Interface Likeable med like() och unlike().
* Klasser RegularUser, AdminUser, Moderator som ärver User och implementerar olika interfaces.
* Post-klass som kan vara Likeable. 

**Förslag på arbetsfördelning i gruppen**
* En person gör User och Post.
* En person gör RegularUser och AdminUser.
* En person gör Moderator och testkörning.

**Utmaning**

Skapa ett interface Reportable för inlägg som kan anmälas och låt Moderator hantera rapporter.