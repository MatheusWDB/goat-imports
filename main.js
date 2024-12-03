const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');
const urlLocal = "http://localhost:8080"
const url = "https://goatimports.onrender.com"

var userId = localStorage.getItem('authUserId');
checkAuthUserId()
function checkAuthUserId() {
    if (userId) {
        window.location.href = "./home/home.html"
    } else {
        localStorage.clear();
    }
}


registerBtn.addEventListener('click', () => {
    container.classList.add("active");
});

loginBtn.addEventListener('click', () => {
    removerActive()
});

const removerActive = () => {
    container.classList.remove("active");
}

async function Login(event) {
    event.preventDefault();
    const email = document.getElementById('loginEmail').value;
    const senha = document.getElementById('loginPassword').value;

    const body = {
        email: email,
        password: senha
    }

    try {
        document.getElementById('favicon').setAttribute('href', './imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`${url}/users/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        })

        if (response.ok) {
            const data = await response.json();
            const userId = data.id;
            if (userId) {
                localStorage.setItem('authUserId', userId);
                window.location.href = "home/home.html";
            }
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }

        document.getElementById('favicon').setAttribute('href', './imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        document.getElementById('favicon').setAttribute('href', './imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
        alert('Erro no servidor!' + error.message);
    }

}

const telefoneInput = document.getElementById('telefone');

telefoneInput.addEventListener('input', () => {
    let value = telefoneInput.value.replace(/\D/g, '');
    value = value.replace(/^(\d{2})(\d)/, '($1) $2');
    value = value.replace(/(\d{1})(\d{4})(\d{4})/, '$1 $2-$3');
    telefoneInput.value = value.slice(0, 16);
});

async function Registro(event) {
    event.preventDefault();
    const nome = document.getElementById('nome').value;
    const sobrenome = document.getElementById('sobrenome').value;
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const confirmeSenha = document.getElementById('confirmarSenha').value;
    const telefone = document.getElementById('telefone').value;

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert("Por favor, insira um email válido.");
        return;
    }

    if (telefone.length < 16) {
        alert('Por favor, insira um número de telefone válido.');
        return
    }

    if (senha.length < 8) {
        alert("A senha deve ter no mínimo 8 caracteres.");
        return;
    }

    if (senha !== confirmeSenha) {
        alert('As senhas não coincidem.');
        return;
    }

    const body = {
        name: nome,
        surname: sobrenome,
        email: email,
        phone: telefone,
        password: senha,
    }

    try {
        document.getElementById('favicon').setAttribute('href', './imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`${url}/users/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });

        if (response.ok) {
            removerActive()
            alert('Conta criada com sucesso!')
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message)
        }
        document.getElementById('favicon').setAttribute('href', './imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        document.getElementById('favicon').setAttribute('href', './imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
        alert('Erro no servidor!' + error.message);
    }
}

