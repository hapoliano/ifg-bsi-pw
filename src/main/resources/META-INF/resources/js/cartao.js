// Função para salvar o cartão de crédito
function salvarCartao() {
    // Coleta os valores dos campos do formulário
    const banco = document.getElementById('banco').value;
    const dataValidade = document.getElementById('dataValidade').value;
    const limiteCredito = document.getElementById('limiteCredito').value;
    const valorGasto = document.getElementById('valorGasto').value;

    // Validação simples para campos vazios
    if (!banco || !dataValidade || !limiteCredito || !valorGasto) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    // Validação do formato da data de validade (MM/AA)
    const regexData = /^(0[1-9]|1[0-2])\/\d{2}$/;
    if (!regexData.test(dataValidade)) {
        alert("Formato da data de validade inválido. Use MM/AA.");
        return;
    }

    // Monta o objeto DTO que será enviado para o backend
    const cartaoDTO = {
        banco: banco,
        dataValidade: dataValidade,
        limiteCredito: parseFloat(limiteCredito),
        valorGasto: parseFloat(valorGasto)
    };

    // Faz a requisição POST para o endpoint de salvar cartões
    fetch('/cartoes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(cartaoDTO)
    })
        .then(response => {
            if (response.ok) {
                alert("Cartão salvo com sucesso!");
                // Redireciona para a página de visão geral após o sucesso
                window.location.href = '/cartoes/visao-geral';
            } else {
                // Se houver erro, exibe a mensagem de erro vinda do backend
                return response.text().then(text => {
                    throw new Error(text || "Ocorreu um erro ao salvar o cartão.");
                });
            }
        })
        .catch(error => {
            console.error('Erro ao salvar o cartão:', error);
            alert(`Erro: ${error.message}`);
        });
}