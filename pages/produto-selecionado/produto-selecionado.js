// INICIA ANTES DE TUDO
checkAuthUserId()
// INICIA ANTES DE TUDO

const catalogo = document.getElementById('catalogo');
var userId
var params
var productId
var product

function checkAuthUserId() {
    userId = localStorage.getItem('authUserId');

    if (!userId) {
        alert("Usuário não autenticado. Redirecionando para a página de login.")
        window.location.href = "../../index.html";
    } else {
        params = new URLSearchParams(window.location.search);
        productId = params.get('id');
        console.log(productId)
        buscarProdutoPorId()
    }
}

async function buscarProdutoPorId() {
    try {
        const response = await fetch(`http://localhost:8080/products/findById/${productId}`, {
            method: 'GET'
        })

        if (response.ok) {
            const data = await response.json();
            product = data
            renderizarListaProduto(product)
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        alert('Erro no servuserIdor!' + error.message);
    }
}

async function buscarCaracteristicaPorIdProduto() {
    try {
        const response = await fetch(`http://localhost:8080/features/findByProductId/${productId}`, {
            method: 'GET'
        })

        if (response.ok) {
            const data = await response.json();
            feature = data
            renderizarListaProduto(feature)
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        alert('Erro no servuserIdor!' + error.message);
    }
}

async function buscarCategoriasPorIdProduto() {
    try {
        const response = await fetch(`http://localhost:8080/categories/findAllByIdProduct/${productId}`, {
            method: 'GET'
        })

        if (response.ok) {
            const data = await response.json();
            categories = data
            renderizarListaProduto(categories)
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        alert('Erro no servuserIdor!' + error.message);
    }
}

function renderizarListaProduto() {
    const button = document.createElement('button')
    button.classList.add('button-card')
    button.onclick = () => selecionarProduto(card.id)

    const card = document.createElement('div')
    card.id = product.id
    card.classList.add('card')

    const nome = document.createElement('p')
    nome.textContent = product.name
    const descricao = document.createElement('p')
    descricao.textContent = product.description
    const preco = document.createElement('p')
    preco.textContent = "R$ " + product.price.toString().replace(".", ",")
    const tamanho = document.createElement('p')
    tamanho.textContent = product.size
    const estoque = document.createElement('p')
    estoque.textContent = product.stock
    const imagem = document.createElement('img');
    imagem.src = product.imgUrl;
    imagem.alt = product.name;
    imagem.style.width = "100%";


    card.appendChild(imagem);
    card.appendChild(nome);
    card.appendChild(preco);

    button.appendChild(card)

    catalogo.appendChild(button)
}
