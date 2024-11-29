var items = JSON.parse(localStorage.getItem('carrinho'))
console.log(items)
const order = {}
order.items = []
items.forEach(item => {
    const itemInfo = { idProduct: item.id, size: item.size, quantity: item.quantity }
    order.items.push(itemInfo)
});
console.log(order)
var addressId = 1
const userId = localStorage.getItem('authUserId');


// INICIA ANTES DE TUDO
//checkAuthUserId()
// INICIA ANTES DE TUDO

function checkAuthUserId() {
    console.log(userId)
    if (!userId) {
        alert("Usuário não autenticado. Redirecionando para a página de login.")
        window.location.href = "../../index.html";
    } else {
        buscarTodosEndereçosPorIdUsuario(userId)
    }
}
// MERCADO PAGO
/*
const cartaoAleatorio = [
    {
        numeroDoCartao: "5031 4332 1540 6351",
        codigoDeSeguranca: "123"
    },
    {
        numeroDoCartao: "4235 6477 2802 5682",
        codigoDeSeguranca: "123"
    },
    {
        numeroDoCartao: "3753 651535 56885",
        codigoDeSeguranca: "1234"
    }
]
const titulatAleatorio = ["APRO", "OTHE", "CONT"]
const cartaoEscolhido = cartaoAleatorio[Math.floor(Math.random() * 3)]
const titularEscolhido = titulatAleatorio[Math.floor(Math.random() * 3)]
const numeroDoCartao = document.getElementById('cardNumber')
const dataDeExpiracao = document.getElementById('expirationDate')
const codigoDeSeguranca = document.getElementById('securityCode')
const titularDoCartao = document.getElementById('cardholderName')
*/

const mp = new MercadoPago("TEST-d5fee87b-795e-4498-97db-e1d2a7782aa5", {
    local: 'pt-BR'
});
const bricksBuilder = mp.bricks();

const renderPaymentBrick = async (bricksBuilder) => {
    const settings = {
        initialization: {
            /*
            "amount" é a quantia total a pagar por todos os meios de pagamento com exceção da Conta Mercado Pago e Parcelas sem cartão de crédito, que têm seus valores de processamento determinados no backend através do "preferenceId"
            */
            amount: 100,
            payer: {
                entityType: "individual",
                firstName: "Bianca",
                lastName: "Santos Carvalho",
                email: "princesinhalinda123@gmail.com",
                identification: {
                    type: 'CPF',
                    number: '12345678909',
                },
                address: {
                    zipCode: '49156631',
                    federalUnit: 'SE',
                    city: 'Nossa Senhora do Socorro',
                    neighborhood: 'São Brás',
                    streetName: 'Travessa Ayrton Senna',
                    streetNumber: '54',
                    complement: '',
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
                var url
                if (selectedPaymentMethod == "debit_card" || selectedPaymentMethod == "credit_card") {
                    url = "https://goatimports.onrender.com/process_payment/card"
                } else if (selectedPaymentMethod == "ticket") {
                    url = "https://goatimports.onrender.com/process_payment/ticket"
                } else if (selectedPaymentMethod == "bank_transfer") {
                    url = "https://goatimports.onrender.com/process_payment/pix"
                }

                // callback chamado quando há click no botão de envio de dados
                document.getElementById('loading-overlay').style.display = 'flex';
                return new Promise((resolve, reject) => {
                    fetch(url, {
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
                            document.getElementById('loading-overlay').style.display = 'none';
                            renderizarStatusDePagamento(response.id)
                            finalizarPedido()
                            window.scrollTo({
                                top: 0,
                                behavior: "smooth" // Rolagem suave
                            });
                            resolve();
                        })
                        .catch((error) => {
                            // manejar a resposta de erro ao tentar criar um pagamento
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
                    'error': `${window.location.origin}/pages/finalizarCompra/Finalizar.html`,
                    'return': `${window.location.origin}/pages/home/home.html`
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




async function finalizarPedido() {
    console.log(order)
    try {
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`http://localhost:8080/orders/create/${addressId}`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(order)
        })

        if (response.ok) {
            alert("Pedido realizado com sucesso!")
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

async function buscarTodosEndereçosPorIdUsuario() {
    try {
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`https://goatimports.onrender.com/addresses/findAllByUserId/${userId}`, {
            method: 'GET'
        })

        if (response.ok) {
            const data = await response.json()
            const enderecos = data;
            renderizarEnderecos(enderecos)
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

async function renderizarEnderecos(enderecos) {
    lista.innerHTML = ''
    enderecos.forEach(endereco => {
        const div2 = document.createElement('div')
        div2.classList.add('enderecos')
        div2.id = endereco.id

        const address = document.createElement('p')
        address.textContent = `${endereco.streetName}, ${endereco.streetNumber}, ${endereco.complement} - ${endereco.neighborhood}, ${endereco.city} - ${endereco.federalUnit}, ${endereco.zipCode}`

        const tipo = document.createElement('p')
        tipo.textContent = 'Tipo de Endereço: ' + endereco.type

        const button = document.createElement('button')
        button.type = 'button'
        button.textContent = 'Escolher'
        button.onclick = () =>

            div2.appendChild(address)
        div2.appendChild(tipo)
        div2.appendChild(button)

        lista.appendChild(div2)
    });
}

