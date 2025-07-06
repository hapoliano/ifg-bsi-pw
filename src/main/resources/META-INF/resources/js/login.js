function fazerLogin() {
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    if (!email || !senha) {
        alert("Por favor, preencha o e-mail e a senha.");
        return;
    }

    const loginData = {
        email: email,
        senha: senha
    };

    fetch('/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
        .then(response => {
            if (response.ok) {
                console.log("Login bem-sucedido!");
                window.location.href = '/homepage';
            } else {
                alert("E-mail ou senha inválidos. Tente novamente.");
            }
        })
        .catch(error => {
            console.error('Erro na requisição de login:', error);
            alert("Ocorreu um erro ao tentar fazer login. Verifique o console para mais detalhes.");
        });
}