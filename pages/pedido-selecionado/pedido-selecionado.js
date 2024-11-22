async function buscarPedidoPorId() {
    try {
        const response = await fetch(`http://localhost:8080/orders/findById/${orderId}`, {
            method: 'GET'
        })

        if (response.ok) {
            const data = await response.json()
            pedido = data;
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

async function deletarPedidoPorId() {
    try {
        const response = await fetch(`http://localhost:8080/orders/delete/${orderId}`, {
            method: 'GET'
        })

        if (response.ok) {
            alert()
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