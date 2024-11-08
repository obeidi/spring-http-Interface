#!/bin/bash

# 1. Schritt: Erstelle eine Gruppe "loggroup", wenn sie noch nicht existiert
GROUP_NAME="loggroup"
if ! grep -q "^${GROUP_NAME}:" /etc/group; then
    echo "Erstelle Gruppe ${GROUP_NAME}..."
    sudo groupadd ${GROUP_NAME}
else
    echo "Gruppe ${GROUP_NAME} existiert bereits."
fi

# 2. Schritt: Füge den Benutzer "user123" zur Gruppe "loggroup" hinzu
USER_NAME="user123"
if id -u "${USER_NAME}" &>/dev/null; then
    echo "Füge ${USER_NAME} zur Gruppe ${GROUP_NAME} hinzu..."
    sudo usermod -aG ${GROUP_NAME} ${USER_NAME}
else
    echo "Benutzer ${USER_NAME} existiert nicht. Bitte stellen Sie sicher, dass der Benutzer existiert."
    exit 1
fi

# 3. Schritt: Setze die Berechtigungen für das Log-Verzeichnis
LOG_DIRECTORY="/path/to/your/log-directory"  # Ersetze dies mit dem tatsächlichen Pfad
if [ -d "${LOG_DIRECTORY}" ]; then
    echo "Ändere Eigentümer und Gruppe des Verzeichnisses ${LOG_DIRECTORY}..."
    sudo chown root:${GROUP_NAME} ${LOG_DIRECTORY}

    echo "Setze Berechtigungen für das Verzeichnis ${LOG_DIRECTORY} auf 0770..."
    sudo chmod 0770 ${LOG_DIRECTORY}
else
    echo "Log-Verzeichnis ${LOG_DIRECTORY} existiert nicht. Bitte überprüfen Sie den Pfad."
    exit 1
fi

# 4. Schritt: Überprüfe die Änderungen
echo "Überprüfe die aktuellen Berechtigungen für das Log-Verzeichnis..."
ls -ld ${LOG_DIRECTORY}
echo "Benutzer ${USER_NAME} zur Gruppe ${GROUP_NAME} hinzugefügt. Berechtigungen und Eigentümer wurden angepasst."
