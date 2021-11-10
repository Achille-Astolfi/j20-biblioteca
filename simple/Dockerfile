#Si parte sempre con un'immagine esistente la parola chiave è FROM
#A noi serve Java 11; si usa l'immagine openjdk (ex java - rinominata)
#con la tag 11
FROM openjdk:11
#Altro esempi
#FROM openjdk:8
#Aggiungo i file necessari al filesystem
#esistono due parole chiave che sono ADD e COPY - funzionano allo stesso modo
#ADD <nome del mio filesystem> <directory di destinazione>
#NOTA BENE la destinazione deve avere lo / finale!
ADD j20-biblioteca-1.0-SNAPSHOT.jar ./
#Posso, e in questo caso è necessario, aggiungere una riga con il comando da eseguire
#quando parte il Container
#La parola chiave è ENTRYPOINT
#Gli argomenti... è un array di string!
ENTRYPOINT ["java", "-jar", "j20-biblioteca-1.0-SNAPSHOT.jar"]
#Se ho bisogno di specificare altro, lo aggiungo come altri elementi dell'array
#ENTRYPOINT ["java", "-jar", "j20-biblioteca-1.0-SNAPSHOT.jar", "--spring.profiles.active=collaudo"]
