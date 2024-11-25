// MERCADO PAGO
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
            preferenceId: "<PREFERENCE_ID>",
            payer: {
                entityType: "individual",
                firstName: "",
                lastName: "",
                email: "matheus@gmail.com",
                identification: {
                    type: 'CPF',
                    number: '12345678909',
                },
                address: {
                    zipCode: '<PAYER_ZIP_CODE_HERE>',
                    federalUnit: '<PAYER_FED_UNIT_HERE>',
                    city: '<PAYER_CITY_HERE>',
                    neighborhood: '<PAYER_NEIGHBORHOOD_HERE>',
                    streetName: '<PAYER_STREET_NAME_HERE>',
                    streetNumber: '<PAYER_STREET_NUMBER_HERE>',
                    complement: '<PAYER_COMPLEMENT_HERE>',
                }
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
                // callback chamado quando há click no botão de envio de dados
                return new Promise((resolve, reject) => {
                    fetch("http://localhost:8080/process_payment", {
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
                    'error': `${window.location.origin}`,
                    'return': `${window.location.origin}`
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

/* 
const cartaoAleatorio = [
    {
        numeroDoCartao: "5031 4332 1540 6351",
        dataDeExpiracao: "11/25",
        codigoDeSeguranca: "123"
    },
    {
        numeroDoCartao: "4235 6477 2802 5682",
        dataDeExpiracao: "11/25",
        codigoDeSeguranca: "123"
    },
    {
        numeroDoCartao: "3753 651535 56885",
        dataDeExpiracao: "11/25",
        codigoDeSeguranca: "1234"
    }
]
 
const titulatAleatorio = ["APRO", "OTHE", "CONT", "FUND"]
 
const cartaoEscolhido = cartaoAleatorio[Math.floor(Math.random() * 3)]
const titularEscolhido = titulatAleatorio[Math.floor(Math.random() * 4)]
 
const numeroDoCartao = document.getElementById('form-checkout__cardNumber')
numeroDoCartao.value = cartaoEscolhido.numeroDoCartao
const dataDeExpiracao = document.getElementById('form-checkout__expirationDate')
dataDeExpiracao.value = cartaoEscolhido.dataDeExpiracao
const codigoDeSeguranca = document.getElementById('form-checkout__securityCode')
codigoDeSeguranca.value = cartaoEscolhido.codigoDeSeguranca
const titularDoCartao = document.getElementById('form-checkout__cardholderName')
titularDoCartao.value = titularEscolhido
const numeroDoDocumento = document.getElementById('form-checkout__identificationNumber')
numeroDoDocumento.value = "12345678909"
 
*/

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

