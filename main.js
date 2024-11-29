const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');
const urlLocal = "http://localhost:8080"
const urlApi = "https://goatimports.onrender.com"

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
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`https://goatimports.onrender.com/users/login`, {
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
                window.location.href = "pages/home/home.html";
            }
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }

        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        document.getElementById('loading-overlay').style.display = 'none';
        alert('Erro no servidor!' + error.message);
    }

}

async function Registro(event) {
    event.preventDefault();
    const nome = document.getElementById('nome').value;
    const sobrenome = document.getElementById('sobrenome').value;
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const confirmeSenha = document.getElementById('confirmarSenha').value;
    const telefone = document.getElementById('telefone').value;

    if (senha !== confirmeSenha) {
        alert('As senhas não coincidem!');
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
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`https://goatimports.onrender.com/users/register`, {
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
        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        document.getElementById('loading-overlay').style.display = 'none';
        alert('Erro no servidor!' + error.message);
    }
}

