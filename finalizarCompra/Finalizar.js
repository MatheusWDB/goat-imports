
const urlLocal = "http://localhost:8080"
const url = "https://goatimports.onrender.com"
var amount = 0
var enderecoSelecionado = {}
var items = JSON.parse(localStorage.getItem('carrinho'))
items.forEach(item => {
    amount += (item.price * item.quantity)
});
console.log(amount)
const order = {}
order.items = []
items.forEach(item => {
    const itemInfo = { idProduct: item.id, size: item.size, quantity: item.quantity }
    order.items.push(itemInfo)
});
var addressId = 0
const userId = localStorage.getItem('authUserId');
var user


// INICIA ANTES DE TUDO
checkAuthUserId()
// INICIA ANTES DE TUDO

function checkAuthUserId() {
    console.log(userId)
    if (!userId) {
        alert("Usuário não autenticado. Redirecionando para a página de login.")
        logout()
    } else {
        buscarUsuarioPorId(userId)
        buscarTodosEndereçosPorIdUsuario(userId)
    }
}

// MERCADO PAGO
const mp = new MercadoPago("TEST-d5fee87b-795e-4498-97db-e1d2a7782aa5", {
    local: 'pt-BR'
});
const bricksBuilder = mp.bricks();

function renderizarMetodosDePagamento() {
    const renderPaymentBrick = async (bricksBuilder) => {
        const settings = {
            initialization: {
                amount: amount,
                payer: {
                    entityType: "individual",
                    firstName: user.name,
                    lastName: user.surname,
                    email: user.email,
                    identification: {
                        type: 'CPF',
                        number: '12345678909',
                    },
                    address: {
                        zipCode: enderecoSelecionado.zipCode,
                        federalUnit: enderecoSelecionado.federalUnit,
                        city: enderecoSelecionado.city,
                        neighborhood: enderecoSelecionado.neighborhood,
                        streetName: enderecoSelecionado.streetName,
                        streetNumber: enderecoSelecionado.number,
                        complement: enderecoSelecionado.complement,
                    },
                }
            },
            customization: {
                visual: {
                    style: {
                        theme: "default",
                    },
                    defaultPaymentOption: {
                        // walletForm: true,
                        creditCardForm: true,
                        // debitCardForm: true,
                        // savedCardForm: 'card id sent in the initialization',
                        // ticketForm: true,
                    },
                    texts: {
                        entityType: {
                            placeholder: "string",
                            label: "string",
                        },
                    }
                },
                paymentMethods: {
                    creditCard: "all",
                    debitCard: "all",
                    ticket: "all",
                    bankTransfer: "all",
                    maxInstallments: 12
                }
            },
            callbacks: {
                onReady: () => {
                    /*
                    Callback chamado quando o Brick está pronto.
                    Aqui, você pode ocultar seu site, por exemplo.
                    */
                },
                onSubmit: async ({ selectedPaymentMethod, formData }) => {
                    switch (selectedPaymentMethod) {
                        case "bank_transfer":
                            order.paymentMethod = 1
                            break
                        case "credit_card":
                            order.paymentMethod = 2
                            break
                        case "debit_card":
                            order.paymentMethod = 3
                            break
                        case "ticket":
                            order.paymentMethod = 4
                            break
                    }
                    var urlPagamento
                    if (selectedPaymentMethod == "debit_card" || selectedPaymentMethod == "credit_card") {
                        urlPagamento = `${url}/process_payment/card`
                    } else if (selectedPaymentMethod == "ticket") {
                        urlPagamento = `${url}/process_payment/ticket`
                    } else if (selectedPaymentMethod == "bank_transfer") {
                        urlPagamento = `${url}/process_payment/pix`
                    }

                    // callback chamado quando há click no botão de envio de dados
                    document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
                    document.getElementById('loading-overlay').style.display = 'flex';
                    return new Promise((resolve, reject) => {
                        fetch(urlPagamento, {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/json",
                            },
                            body: JSON.stringify(formData),
                        })
                            .then((response) => response.json())
                            .then((response) => {
                                // receber o resultado do pagamento  
                                switch (response.status) {
                                    case "in_process":
                                        order.status = 3
                                        break
                                    case "approved":
                                        order.status = 2
                                        break
                                    case "rejected":
                                        order.status = 4
                                        break
                                    case "pending":
                                        order.status = 1
                                        break
                                }
                                document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
                                document.getElementById('loading-overlay').style.display = 'none';
                                finalizarPedido(response.id)
                                window.scrollTo({
                                    top: 0,
                                    behavior: "smooth" // Rolagem suave
                                });
                                resolve();
                            })
                            .catch((error) => {
                                // manejar a resposta de erro ao tentar criar um pagamento
                                document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
                                document.getElementById('loading-overlay').style.display = 'none';
                                reject();
                            });
                    });
                },
                onBinChange: (bin) => {
                    // callback chamado sempre que o bin do cartão é alterado
                },
                onError: (error) => {
                    // callback chamado para todos os casos de erro do Brick
                    console.error(error);
                }
            }
        };

        window.paymentBrickController = await bricksBuilder.create(
            "payment",
            "paymentBrick_container",
            settings
        );
    };
    renderPaymentBrick(bricksBuilder);
}

function renderizarStatusDePagamento(paymentId) {
    const renderStatusScreenBrick = async (bricksBuilder) => {
        const settings = {
            initialization: {
                paymentId: paymentId, // Payment identifier, from which the status will be checked
            },
            customization: {
                visual: {
                    hideStatusDetails: true,
                    hideTransactionDate: true,
                    style: {
                        theme: 'default', // 'default' | 'dark' | 'bootstrap' | 'flat'
                    }
                },
                backUrls: {
                    'error': `${window.location.origin}/finalizarCompra/Finalizar.html`,
                    'return': `${window.location.origin}/home/home.html`
                }
            },
            callbacks: {
                onReady: () => {
                    // Callback called when Brick is ready
                },
                onError: (error) => {
                    // Callback called for all Brick error cases
                },
            },
        };
        window.statusScreenBrickController = await bricksBuilder.create('statusScreen', 'statusScreenBrick_container', settings);
    };
    renderStatusScreenBrick(bricksBuilder);
}
// MERCADO PAGO

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

async function finalizarPedido(id) {
    console.log(order)
    try {
        document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`${url}/orders/create/${addressId}`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(order)
        })

        if (response.ok) {
            alert("Pedido realizado com sucesso!")
            document.getElementById("modal").style.display = 'none'
            renderizarStatusDePagamento(id)
            localStorage.removeItem('carrinho');
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
            const enderecos = data;
            if (enderecos.length == 0) {
                alert("Você não possui endereços cadastrados, iremos redirecioná-lo!")
                window.location.href = "../perfil/perfil.html"
            }
            renderizarEnderecos(enderecos)
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

async function renderizarEnderecos(enderecos) {
    const divEndereco = document.getElementById("enderecos")
    divEndereco.innerHTML = ''

    enderecos.forEach(endereco => {
        const div2 = document.createElement('div')
        div2.classList.add('enderecos')
        div2.id = endereco.id

        const address = document.createElement('p')
        address.textContent = `${endereco.streetName}, ${endereco.number}, ${endereco.complement} - ${endereco.neighborhood}, ${endereco.city} - ${endereco.federalUnit}, ${endereco.zipCode}`

        const tipo = document.createElement('p')
        tipo.textContent = 'Tipo de Endereço: ' + endereco.type

        const button = document.createElement('button')
        button.type = 'button'
        button.textContent = 'Escolher'
        button.onclick = () => {
            if (addressId !== endereco.id) {
                // Remover seleção anterior
                if (addressId) {
                    const prevSelected = document.getElementById(addressId);
                    if (prevSelected) {
                        prevSelected.classList.remove('selecionado');
                        enderecoSelecionado = {}
                    }
                }
                // Atualizar o ID do endereço selecionado e aplicar a classe
                addressId = endereco.id;
                div2.classList.add('selecionado');
                enderecoSelecionado = endereco
            }
        };


        div2.appendChild(address)
        div2.appendChild(tipo)
        div2.appendChild(button)

        divEndereco.appendChild(div2)
    });
}

function passarParaPagamento() {
    document.getElementById('modalEnderecos').style.display = 'none';
    document.getElementById("modalPagamento").style.display = 'flex'
    renderizarMetodosDePagamento()
}

function logout() {
    localStorage.clear();
    window.location.href = "../index.html"
}

function voltarHome() {
    location.href = "../home/home.html"
}