function salvarDespesa() {
    const descricao = document.getElementById('descricao').value;
    const valor = document.getElementById('valor').value;
    const data = document.getElementById('data').value;
    const categoriaId = document.getElementById('categoria').value;
    const cartaoId = document.getElementById('cartao').value;

    if (!descricao || !valor || !data) {
        alert("Por favor, preencha Descrição, Valor e Data.");
        return;
    }

    const despesaDTO = {
        descricao: descricao,
        valor: parseFloat(valor),
        data: data,
        categoriaId: categoriaId ? parseInt(categoriaId) : null,
        cartaoId: cartaoId ? parseInt(cartaoId) : null
    };

    fetch('/despesas', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(despesaDTO)
    })
        .then(response => {
            if (response.ok) {
                alert("Despesa salva com sucesso!");
                document.getElementById('despesaForm').reset();
                // Redireciona para a visão geral dos cartões para ver a fatura atualizada
                window.location.href = '/cartoes/visao-geral';
            } else {
                return response.text().then(text => { throw new Error(text); });
            }
        })
        .catch(error => {
            console.error('Erro ao salvar despesa:', error);
            alert(`Erro: ${error.message}`);
        });
}

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('data').valueAsDate = new Date();
});