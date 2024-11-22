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
finalizarPedido()
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

