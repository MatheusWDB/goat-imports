// INICIA ANTES DE TUDO
checkAuthUserId()
// INICIA ANTES DE TUDO

var userId
var user
var enderecos

const cep = document.querySelector('#zipCode')
const rua = document.querySelector('#rua')
const bairro = document.querySelector('#bairro')
const cidade = document.querySelector('#cidade')
const uf = document.querySelector('#uf')
const complemento = document.querySelector('#complemento')


cep.addEventListener('focusout', async () => {
    try {
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

    } catch (error) {
        console.log(error)
    }
})

const lista = document.querySelector('#Modal3>.modal-content3')

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
    userId = localStorage.getItem('authUserId');
    console.log(userId)

    if (!userId) {
        alert("Usuário não autenticado. Redirecionando para a página de login.")
        window.location.href = "../../index.html";
    } else {
        console.log("Token carregado com sucesso")
        buscarUsuarioPorId()
    }
}

async function buscarUsuarioPorId() {
    try {
        const response = await fetch(`http://localhost:8080/users/findById/${userId}`, {
            method: 'GET',
        })

        if (response.ok) {
            const data = await response.json();
            user = data
            console.log(user)
            infoUser()
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        alert('Erro no servidor!' + error.message);
    }
}

async function registrarEndereço() {
    const selected = document.querySelector('input[name="tipo"]:checked');
    const body = {
        zipCode: cep.value,
        streetName: rua.value,
        streetNumber: document.getElementById('number').value,
        neighborhood: bairro.value,
        city: cidade.value,
        federalUnit: uf.value,
        complement: document.getElementById('complemento').value,
        type: selected.value
    }
    try {
        const response = await fetch(`http://localhost:8080/addresses/create/${userId}`, {
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
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        alert('Erro no servidor!' + error.message);
    }
}

async function buscarTodosEndereçosPorIdUsuario() {
    try {
        const response = await fetch(`http://localhost:8080/addresses/findAllByUserId/${userId}`, {
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
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        alert('Erro no servidor!' + error.message);
    }
}

async function renderizarEnderecos() {
    lista.innerHTML = ''

    enderecos.forEach(endereco => {
        endereco.zipCode = endereco.zipCode.replace('-', '')

        const div2 = document.createElement('div')
        div2.classList.add('enderecos')
        div2.id = endereco.id

        const address = document.createElement('p')
        address.textContent = `${endereco.streetName}, ${endereco.streetNumber} - ${endereco.neighborhood},  ${endereco.city} -  ${endereco.federalUnit} - ${endereco.zipCode}`

        const complemento = document.createElement('p')
        complemento.textContent = 'Complemento: ' + endereco.complement

        const tipo = document.createElement('p')
        tipo.textContent = 'Tipo de Endereço: ' + endereco.type

        const button = document.createElement('button')
        button.type = 'button'
        button.textContent = 'Excluir'
        button.onclick = () => deletarEndereçoPorId(div2.id)

        div2.appendChild(address)
        div2.appendChild(complemento)
        div2.appendChild(tipo)
        div2.appendChild(button)

        lista.appendChild(div2)
    });
}

async function deletarEndereçoPorId(addressId) {
    try {
        const response = await fetch(`http://localhost:8080/addresses/delete/${addressId}`, {
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
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
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
        phone: phone.value,
        email: email.value,
        dateOfBirth: dateOfBirth.value,
        password: password.value
    }
    try {
        const response = await fetch(`http://localhost:8080/users/update/${userId}`, {
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
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
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
    document.getElementById('Modal3').style.display = 'flex';
}

function closeModal3() {
    document.getElementById('Modal3').style.display = 'none';
}


