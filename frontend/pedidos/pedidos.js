// INICIA ANTES DE TUDO
const userId = localStorage.getItem('authUserId');
var orders
const urlLocal = "http://localhost:8080"
const url = "https://goatimports.onrender.com"
const content = document.getElementById("content")
const modalContent = document.getElementById("modal-content")
var products
checkAuthUserId()
// INICIA ANTES DE TUDO

function checkAuthUserId() {
    if (!userId) {
        alert("Usuário não autenticado. Redirecionando para a página de login.")
        logout()
    } else {
        console.log("userId carregado com sucesso")
        buscarTodosPedidos()
    }
}

async function buscarTodosPedidos() {
    try {
        document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`${url}/orders/findAllByUserId/${userId}`, {
            method: 'Get'
        })

        if (response.ok) {
            const data = await response.json();
            orders = data
            renderizarPedidos()
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

function openModal() {
    document.getElementById('modal').style.display = 'flex';
}

function closemodal() {
    document.getElementById('modal').style.display = 'none';
}

function renderizarPedidos() {
    content.innerHTML = ''

    if (orders.length == 0) {
        const vazio = document.createElement("h3")
        vazio.textContent = "Você ainda não finalizou nenhuma compra!"

        content.appendChild(vazio)
    }


    orders.forEach(order => {
        const cardPedidos = document.createElement("div")
        cardPedidos.classList.add("cardPedidos")
        cardPedidos.onclick = () => selecionarPedido(order)

        const numeroPedido = document.createElement("p")
        numeroPedido.textContent = `Nº do pedido: ${order.orderNumber}`

        const valorPedido = document.createElement("p")
        valorPedido.textContent = `Valor: R$ ${order.total.toString().replace(".", ",")}`

        const quantidadeItens = document.createElement("p")
        quantidadeItens.textContent = `Quantidade de itens: ${order.products.length}`

        const statusPagamento = document.createElement("p")
        switch (order.status) {
            case 'PENDING':
                order.status = 'Pendente'
                break
            case 'APPROVED':
                order.status = 'Aprovado'
                break
            case 'IN_PROCCESS':
                order.status = 'Em processamento'
                break
            case 'REJECTED':
                order.status = 'Recusado'
                break
        }
        statusPagamento.textContent = `Status de pagamento: ${order.status}`

        cardPedidos.appendChild(numeroPedido)
        cardPedidos.appendChild(valorPedido)
        cardPedidos.appendChild(quantidadeItens)
        cardPedidos.appendChild(statusPagamento)

        content.appendChild(cardPedidos)
    });
}

async function selecionarPedido(order) {
    openModal()
    const PedidosSelecionado = document.getElementById("PedidosSelecionado")
    PedidosSelecionado.innerHTML = ''
    products = []

    try {
        for (const product of order.products) {
            document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
            document.getElementById('loading-overlay').style.display = 'flex';

            const response = await fetch(`${url}/products/findById/${product.idProduct}`, {
                method: 'GET'
            });

            if (response.ok) {
                const data = await response.json();
                data.subTotal = product.subTotal;
                data.quantity = product.quantity;
                products.push(data);
            } else {
                const error = await response.json();
                console.log(error);
                alert(error.message);
            }
        }
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        alert('Erro no servidor! ' + error.message);
    } finally {
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    }

    products.forEach(product => {
        const img = document.createElement("img")
        img.src = product.imgUrl
        img.alt = product.name

        PedidosSelecionado.appendChild(img)

        const contentProduto = document.createElement("contentProduto")
        contentProduto.classList.add("contentProduto")

        const nome = document.createElement("p")
        nome.textContent = product.name

        contentProduto.appendChild(nome)

        const quantidade = document.createElement("p")
        quantidade.textContent = `Quantidade: ${product.quantity}`

        contentProduto.appendChild(quantidade)

        const valor = document.createElement("p")
        valor.textContent = `Valor: R$ ${product.subTotal.toFixed(2).toString().replace(".",",")}`

        contentProduto.appendChild(valor)

        PedidosSelecionado.appendChild(contentProduto)
    });

    const valorTotal = document.createElement("h3")
    valorTotal.textContent = `Total: R$ ${order.total.toFixed(2).toString().replace(".",",")}`
    valorTotal.style.width = '200px'

    PedidosSelecionado.appendChild(valorTotal)
}

function logout() {
    localStorage.clear();
    window.location.href = "../index.html"
}

function voltarHome() {
    location.href = "../home/home.html"
}