// INICIA ANTES DE TUDO
const userId = localStorage.getItem('authUserId');
var orders
checkAuthUserId()
// INICIA ANTES DE TUDO

function checkAuthUserId() {
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
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`https://goatimports.onrender.com/orders/findAllByUserId/${userId}`, {
            method: 'Get'
        })

        if (response.ok) {
            const data = await response.json();
            orders = data
            console.log(orders)
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