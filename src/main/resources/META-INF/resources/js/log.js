async function carregarLogs() {
    try {
        const response = await fetch('/logs/data');
        if (!response.ok) {
            throw new Error('Falha ao carregar os logs. Você tem permissão para ver esta página?');
        }

        const logs = await response.json();
        const tabelaCorpo = document.querySelector('#logTable tbody');
        tabelaCorpo.innerHTML = ''; // Limpa a tabela antes de preencher

        logs.forEach(log => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${log.dataHora}</td>
                <td>${log.usuarioEmail}</td>
                <td>${log.acao}</td>
            `;
            tabelaCorpo.appendChild(tr);
        });
    } catch (error) {
        console.error('Erro:', error);
        alert(error.message);
    }
}

function filtrarTabela() {
    const filtro = document.getElementById('filtro').value.toLowerCase();
    const linhas = document.querySelectorAll('#logTable tbody tr');

    linhas.forEach(linha => {
        const textoLinha = linha.textContent.toLowerCase();
        if (textoLinha.includes(filtro)) {
            linha.style.display = '';
        } else {
            linha.style.display = 'none';
        }
    });
}

document.addEventListener('DOMContentLoaded', () => {
    carregarLogs();
    document.getElementById('filtro').addEventListener('keyup', filtrarTabela);
});