// Função para salvar o cartão de crédito
function salvarCartao() {
    const banco = document.getElementById('banco').value;
    const dataValidade = document.getElementById('dataValidade').value;
    const limiteCredito = document.getElementById('limiteCredito').value;
    const valorGasto = document.getElementById('valorGasto').value;

    if (!banco || !dataValidade || !limiteCredito || !valorGasto) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    const regexData = /^(0[1-9]|1[0-2])\/\d{2}$/;
    if (!regexData.test(dataValidade)) {
        alert("Formato da data de validade inválido. Use MM/AA.");
        return;
    }

    const cartaoDTO = {
        banco,
        dataValidade,
        limiteCredito: parseFloat(limiteCredito),
        valorGasto: parseFloat(valorGasto)
    };

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
                window.location.href = '/cartoes/visao-geral';
            } else {
                return response.text().then(text => {
                    throw new Error(text || "Erro desconhecido ao salvar o cartão.");
                });
            }
        })
        .catch(error => {
            console.error('Erro ao salvar o cartão:', error);
            alert(`Erro: ${error.message}`);
        });
}

// Função para deletar o cartão de crédito
function deletarCartao(buttonElement) {
    const id = buttonElement.getAttribute('data-id');

    if (!id) {
        alert('ID do cartão não encontrado.');
        return;
    }

    if (confirm('Tem certeza que deseja excluir este cartão? Esta ação não pode ser desfeita.')) {
        fetch(`/cartoes/${id}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    alert('Cartão excluído com sucesso!');
                    location.reload();
                } else {
                    // Verifica se há corpo JSON na resposta
                    const contentType = response.headers.get("content-type");
                    if (contentType && contentType.includes("application/json")) {
                        return response.json().then(body => {
                            alert('Erro ao excluir cartão: ' + (body.error || 'Erro desconhecido.'));
                        });
                    } else {
                        return response.text().then(text => {
                            alert('Erro ao excluir cartão: ' + (text || 'Erro desconhecido.'));
                        });
                    }
                }
            })
            .catch(error => {
                console.error('Erro:', error);
                alert('Ocorreu um erro na comunicação com o servidor.');
            });
    }
}
