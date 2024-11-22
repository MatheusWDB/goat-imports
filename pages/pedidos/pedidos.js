// INICIA ANTES DE TUDO
checkAuthUserId()
// INICIA ANTES DE TUDO
var userId

function checkAuthUserId() {
    userId = localStorage.getItem('authUserId');
    console.log(userId)

    if (!userId) {
        alert("Usuário não autenticado. Redirecionando para a página de login.")
        window.location.href = "../../index.html";
    } else {
        console.log("userId carregado com sucesso")
        buscarTodosPedidos()
    }
}

async function buscarTodosPedidos() {
    try {
        const response = await fetch(`http://localhost:8080/orders/findAllByUserId/${userId}`, {
            method: 'Get'
        })

        if (response.ok) {
            const data = await response.json();
            const orders = data
            console.log(orders)
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