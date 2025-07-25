function salvarConta() {
    const nome = document.getElementById('nome').value;
    const saldo = document.getElementById('saldo').value;
    const tipo = document.querySelector('input[name="tipoConta"]:checked').value;

    if (!nome || !saldo) {
        alert("Por favor, preencha o nome e o saldo inicial.");
        return;
    }

    const contaDTO = {
        nome: nome,
        saldo: parseFloat(saldo),
        tipo: tipo
    };

    fetch('/contas', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(contaDTO)
    })
        .then(response => {
            if (response.ok) {
                alert("Conta salva com sucesso!");
                window.location.href = '/contas/visao-geral';
            } else {
                return response.text().then(text => { throw new Error(text); });
            }
        })
        .catch(error => {
            console.error('Erro ao salvar conta:', error);
            alert(`Erro: ${error.message}`);
        });
}