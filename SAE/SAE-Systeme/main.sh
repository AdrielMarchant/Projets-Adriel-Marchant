#!/bin/bash

echo " "
echo "\\||programme principal||//"
echo " "


#Pour chaque test, nous allons vérifier l'existence d'elements réels, ainsi que d'elements faux

#Affichage du PAth
echo "path : $PATH"

echo ""

#Verification des applications installées
for app in ~/Documents/sql_developpeur/opt/sqldeveloper/sqldeveloper.sh ~/Documents/AsciidocFX_Linux/AsciidocFX/AsciidocFX.sh ~/Documents/Mensonge/PasTresVrai.sh
do
	if  type $app 
	then

		2>/dev/null 
		echo "l'application $app a été trouvé et est executable"
		echo " "
	else
		2>/dev/null 
		echo "l'application $app est introuvable"
		echo " "
	fi
done

echo ""

#Verification des users
for name in testVM Aa Bb Cc Dd Ee Ff Gg Hh Ii Jj Kk Ll Mm Nn Oo Pp Qq Rr Ss Tt Uu
do

	if cat /etc/passwd | grep -q "$name" 
	then
		echo "l utilisateur $name existe"
	else
		echo "l utilisateur $name n existe pas"
	fi
done

echo ""

#Verification des groupes
for group in Etudiant VM diantEtu
do

	if cat /etc/group | grep -q "$group" 
	then
		echo "le groupe $group existe"
	else
		echo "le groupe $group n existe pas"
	fi
done

#Verification du masque 
umask



exit 0
