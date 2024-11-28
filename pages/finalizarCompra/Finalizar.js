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
                var url
                if (selectedPaymentMethod == "debit_card" || selectedPaymentMethod == "credit_card") {
                    url = "http://localhost:8080/process_payment/card"
                } else if (selectedPaymentMethod == "ticket") {
                    url = "http://localhost:8080/process_payment/ticket"
                } else if (selectedPaymentMethod == "bank_transfer") {
                    url = "http://localhost:8080/process_payment/pix"
                }

                // callback chamado quando há click no botão de envio de dados
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
                            renderizarStatusDePagamento(response.id)
                            window.scrollTo({
                                top: 0,
                                behavior: "smooth" // Rolagem suave
                            });
                            resolve();
                        })
                        .catch((error) => {
                            // manejar a resposta de erro ao tentar criar um pagamento
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


var userId
var items = {
    items: [
        {
            "idProduct": 1,
            "quantity": 10
        },
        {
            "idProduct": 2,
            "quantity": 5
        },
        {
            "idProduct": 3,
            "quantity": 15
        }
    ]
}

// INICIA ANTES DE TUDO
//checkAuthUserId()
// INICIA ANTES DE TUDO

function checkAuthUserId() {
    userId = localStorage.getItem('authUserId');
    console.log(userId)

    if (!userId) {
        alert("Usuário não autenticado. Redirecionando para a página de login.")
        window.location.href = "../../index.html";
    } else {
        console.log(items)
    }
}

async function finalizarPedido() {
    const addressId = 1
    items.paymentMethod = 1
    console.log(items)
    try {
        const response = await fetch(`http://localhost:8080/orders/create/${addressId}`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(items)
        })

        if (response.ok) {
            alert("Pedido realizado com sucesso!")
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

