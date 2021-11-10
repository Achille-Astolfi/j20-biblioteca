#COMMENTI
#Si parte sempre con un'immagine esistente: la parola chiave è FROM
#A noi serve Java 11; si usa l'immagine openjdk (ex java - rinominata)
#con la tag 11
FROM openjdk:11
#Aggiungo i file necessari al filesystem
#esistono due parole chiave che sono ADD e COPY che funzionano allo stesso modo
#ADD <nome del mio filesystem> <directory di destinazione>
#NB: deve avere lo slash finale
ADD j20-biblioteca-1.0-SNAPSHOT.jar ./
#Posso ma in questo cao è necessario, aggiungere una riga con il comando da eseguire
#quando parte il container
#La parola chiave è ENTRYPOINT
#Gli argomenti sono dati da un array si String
ENTRYPOINT ["java", "-jar", "j20-biblioteca-1.0-SNAPSHOT.jar"]
#Se ho bisogno di specificare altro, lo aggiungo come altri elementi dell'array
#ENTRYPOINT ["java", "-jar", "j20-biblioteca-1.0-SNAPSHOT.jar", "--spring.profiles.active=collaudo"]

#Nella cartella simple si esegue:
#sudo docker build --tag biblio .
