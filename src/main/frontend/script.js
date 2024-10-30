function getMetadata() {
    const folderPath = document.getElementById('folderPath').value;
    console.log(`Sending request to folder: ${folderPath}`);
    fetch('http://localhost:8023/api/images/metadata', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({ folderPath: folderPath })
    })
        .then(response => {
            if (!response.ok) {
                console.error('Error response:', response); // Логируем ответ сервера
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => displayMetadata(data))
        .catch(error => console.error('Error fetching metadata:', error));
}

function displayMetadata(metadataList) {
    const tableBody = document.getElementById('metadataTable').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = '';  // Очищаем таблицу перед добавлением новых данных

    metadataList.forEach(metadata => {
        const row = `
            <tr>
                <td>${metadata.fileName}</td>
                <td>${metadata.width}x${metadata.height}</td>
                <td>${metadata.dpi}</td>
                <td>${metadata.colorDepth}</td>
                <td>${metadata.compression}</td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}
