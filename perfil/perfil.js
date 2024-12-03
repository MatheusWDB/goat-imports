// INICIA ANTES DE TUDO
const urlLocal = "http://localhost:8080"
const url = "https://goatimports.onrender.com"
const userId = localStorage.getItem('authUserId');
checkAuthUserId()
// INICIA ANTES DE TUDO
var user
var enderecos

const cep = document.querySelector('#zipCode')
const rua = document.querySelector('#rua')
const bairro = document.querySelector('#bairro')
const cidade = document.querySelector('#cidade')
const uf = document.querySelector('#uf')
const complemento = document.querySelector('#complemento')

const telefoneInput = document.getElementById('phone');

telefoneInput.addEventListener('input', () => {
    let value = telefoneInput.value.replace(/\D/g, '');
    value = value.replace(/^(\d{2})(\d)/, '($1) $2');
    value = value.replace(/(\d{1})(\d{4})(\d{4})/, '$1 $2-$3');
    telefoneInput.value = value.slice(0, 16);
});

cep.addEventListener('focusout', async () => {
    try {
        document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const onlyNumbers = /^[0-9]+$/
        const cepValid = /^[0-9]{8}$/
        if (!onlyNumbers.test(cep.value) || !cepValid.test(cep.value)) {
            throw { cep_error: 'CEP inválido' }
        }

        const response = await fetch(`https://viacep.com.br/ws/${cep.value}/json/`)

        if (!response.ok) {
            throw await response.json()
        }

        const responseCep = await response.json()

        cep.value = responseCep.cep
        rua.value = responseCep.logradouro
        bairro.value = responseCep.bairro
        cidade.value = responseCep.localidade
        uf.value = responseCep.uf
        complemento.value = responseCep.complemento
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log(error)
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    }
})

const lista = document.querySelector('#modal3>.btnmodal3>.modal-content3')

const button2 = document.createElement('button')
button2.type = 'button'
button2.classList.add('btn-modal')
button2.textContent = 'Fechar'
button2.onclick = () => closeModal3()

const button3 = document.createElement('button')
button3.type = 'button'
button3.classList.add('btn-modal')
button3.textContent = 'Cadastrar Endereço'
button3.onclick = () => {
    closeModal3()
    OpenModal2()
}

const nome = document.getElementById('Nome')
const sobrenome = document.getElementById('Sobrenome')
const telefone = document.getElementById('telefone')
const e_mail = document.getElementById('e-mail')
const dob = document.getElementById('DataNascimento')

const name = document.getElementById('name')
const surname = document.getElementById('surname')
const phone = document.getElementById('phone')
const email = document.getElementById('email')
const dateOfBirth = document.getElementById('dateOfBirth')
const password = document.getElementById('password')

function checkAuthUserId() {
    if (!userId) {
        alert("Usuário não autenticado. Redirecionando para a página de login.")
        logout()
    } else {
        console.log("Token carregado com sucesso")
        buscarUsuarioPorId()
    }
}

async function buscarUsuarioPorId() {
    try {
        document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`${url}/users/findById/${userId}`, {
            method: 'GET',
        })

        if (response.ok) {
            const data = await response.json();
            user = data
            infoUser()
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
        alert('Erro no servidor!' + error.message);
    }
}

async function registrarEndereço() {
    const selected = document.querySelector('input[name="tipo"]:checked');
    const body = {
        zipCode: cep.value, //49156631
        streetName: rua.value,
        number: document.getElementById('number').value,
        neighborhood: bairro.value,
        city: cidade.value,
        federalUnit: uf.value,
        complement: document.getElementById('complemento').value,
        type: selected.value
    }
    console.log(body)
    try {
        document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`${url}/addresses/create/${userId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        })

        if (response.ok) {
            alert("Endereço cadastrado com sucesso!")
            closeModal2()
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
        alert('Erro no servidor!' + error.message);
    }
}

async function buscarTodosEndereçosPorIdUsuario() {
    try {
        document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`${url}/addresses/findAllByUserId/${userId}`, {
            method: 'GET'
        })

        if (response.ok) {
            const data = await response.json()
            enderecos = data;
            renderizarEnderecos()
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
        alert('Erro no servidor!' + error.message);
    }
}

async function renderizarEnderecos() {
    lista.innerHTML = ''

    if (enderecos.length == 0) {
        const vazio = document.createElement("p")
        vazio.textContent = "Nenhum endereço cadastrado!"

        lista.appendChild(vazio)
    }

    enderecos.forEach(endereco => {
        const div2 = document.createElement('div')
        div2.classList.add('enderecos')
        div2.id = endereco.id

        if(!endereco.complement){
            endereco.complement = 'N/A'
        }

        const address = document.createElement('p')
        address.textContent = `${endereco.streetName}, ${endereco.number}, ${endereco.complement} - ${endereco.neighborhood}, ${endereco.city} - ${endereco.federalUnit}, ${endereco.zipCode}`

        const tipo = document.createElement('p')
        tipo.textContent = 'Tipo de Endereço: ' + endereco.type

        const button = document.createElement('button')
        button.type = 'button'
        button.textContent = 'Excluir'
        button.onclick = () => deletarEndereçoPorId(endereco.id)

        div2.appendChild(address)
        div2.appendChild(tipo)
        div2.appendChild(button)

        lista.appendChild(div2)
    });
}

async function deletarEndereçoPorId(addressId) {
    try {
        console.log(addressId)
        document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`${url}/addresses/delete/${addressId}`, {
            method: 'DELETE'
        })

        if (response.ok) {
            alert('Endereço deletado!')
            buscarTodosEndereçosPorIdUsuario()
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
        alert('Erro no servidor!' + error.message);
    }
}

function infoUser() {
    nome.value = user.name
    sobrenome.value = user.surname
    telefone.value = user.phone
    e_mail.value = user.email
    dob.value = user.dateOfBirth

    name.value = user.name
    surname.value = user.surname
    phone.value = user.phone
    email.value = user.email
    dateOfBirth.value = user.dateOfBirth
}

function voltarHome() {
    location.href = "../home/home.html"
}

//

function OpenModal() {
    document.getElementById('confirmationModal').style.display = 'flex';
}

function closeModal() {
    document.getElementById('confirmationModal').style.display = 'none';
}

async function atualizarUsuario() {
    const body = {
        name: name.value,
        surname: surname.value,
        phone: phone.value, //(79) 9 8814-3425
        email: email.value,
        dateOfBirth: dateOfBirth.value,
        password: password.value
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(email.value)) {
        alert("Por favor, insira um email válido.");
        return;
    }

    if (phone.value.length < 16) {
        alert('Por favor, insira um número de telefone válido.');
        return
    }

    try {
        document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`${url}/users/update/${userId}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        })

        if (response.ok) {
            alert("Dados atualizado com sucesso!")
            buscarUsuarioPorId()
            closeModal()
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
        alert('Erro no servidor!' + error.message);
    }
}

function OpenModal2() {
    document.getElementById('Modal2').style.display = 'flex';
}

function closeModal2() {
    document.getElementById('Modal2').style.display = 'none';
}

function OpenModal3() {
    buscarTodosEndereçosPorIdUsuario()
    document.getElementById('modal3').style.display = 'flex';
}

function closeModal3() {
    document.getElementById('modal3').style.display = 'none';
}

function logout() {
    localStorage.clear();
    window.location.href = "../index.html"
}
