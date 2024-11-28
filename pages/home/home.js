// INICIA ANTES DE TUDO
const userId = localStorage.getItem('authUserId');
var items = localStorage.getItem('carrinho') === null ?
    [] :
    JSON.parse(localStorage.getItem('carrinho'))
checkAuthUserId()

function checkAuthUserId() {
    if (!userId) {
        alert("Usuário não autenticado. Redirecionando para a página de login.")
        logout()
    } else {
        buscarTodasCategorias()
        buscarTodosProdutos()
    }
}
// INICIA ANTES DE TUDO


var products
var categories


const catalogo = document.getElementById('catalogo');
const categorias = document.getElementById('categoria');



async function buscarTodasCategorias() {
    try {
        const response = await fetch("http://localhost:8080/categories/findAll", {
            method: 'GET'
        })

        if (response.ok) {
            const data = await response.json();
            categories = data
            renderizarListaCategorias(categories)
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

async function buscarTodosProdutos() {
    try {
        const response = await fetch("http://localhost:8080/products/findAll", {
            method: 'GET',
        })

        if (response.ok) {
            const data = await response.json();
            products = data
            renderizarListaProdutos(products)
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

const renderizarListaCategorias = (categories) => {
    categories.forEach(category => {
        const button = document.createElement('button')
        button.classList.add('button-categoria')

        button.onclick = () => filtro(category.id)

        const card = document.createElement('button')
        card.classList.add('cardcategoria')
        card.textContent = category.name

        button.appendChild(card)

        categorias.appendChild(button)
    });
}

const filtro = (idCategoria) => {
    let productFilter = []
    products.forEach(product => {
        product.categories.forEach(idCategory => {
            if (idCategory === idCategoria) {
                productFilter.push(product)
            }
        })
    });
    renderizarListaProdutos(productFilter)
}

const renderizarListaProdutos = (products) => {
    catalogo.innerHTML = ''

    products.forEach(product => {
        const button = document.createElement('button')
        button.classList.add('button-card')
        button.onclick = () => selecionarProduto(product)

        const card = document.createElement('div')
        card.id = product.id
        card.classList.add('card')

        const nome = document.createElement('p')
        nome.textContent = product.name
        nome.classList.add('nomeProduto')

        const preco = document.createElement('p')
        preco.textContent = "R$ " + product.price.toString().replace(".", ",")
        preco.classList.add('precoProduto')

        const imagem = document.createElement('img');
        imagem.src = product.imgUrl;
        imagem.alt = product.name;
        imagem.classList.add('imgProduto');

        card.appendChild(imagem);
        card.appendChild(nome);
        card.appendChild(preco);

        button.appendChild(card)

        catalogo.appendChild(button)

    })
}

const selecionarProduto = async (produto) => {
    document.getElementById('modal').style.display = 'flex';
    var quantidadeInput = document.getElementById("quantidade")

    const button = document.getElementById('buttonAdicionarAoCarrinho')
    button.onclick = () => {
        var tamanhoSelecionado = document.querySelector('input[name="tamanho"]:checked');
        var quantidade = parseInt(quantidadeInput.value)
        if (!tamanhoSelecionado) {
            alert("Escolha o tamanho da peça!")
        } else if (quantidade <= 0) {
            alert("Defina a quantidade!")
        } else {
            adicionarAoCarrinho(produto, quantidade, quantidadeInput, tamanhoSelecionado)
        }
    }

    const imagemProdutoSelecionado = document.getElementById('imagemProdutoSelecionado')
    imagemProdutoSelecionado.src = produto.imgUrl
    imagemProdutoSelecionado.alt = produto.name

    const nomeProdutoSelecionado = document.getElementById('nomeProdutoSelecionado')

    nomeProdutoSelecionado.textContent = produto.name

    const precoProdutoSelecionado = document.getElementById('precoProdutoSelecionado')
    precoProdutoSelecionado.textContent = `Preço: R$${produto.price.toString().replace(".", ",")}`

    var features

    try {
        const response = await fetch(`http://localhost:8080/features/findByProductId/${produto.id}`, {
            method: 'GET',
        })

        if (response.ok) {
            const data = await response.json();
            features = data
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        alert('Erro no servidor!' + error.message);
    }

    const marcaProdutoSelecionado = document.getElementById("marcaProdutoSelecionado")
    marcaProdutoSelecionado.textContent = features.mark

    const modeloProdutoSelecionado = document.getElementById("modeloProdutoSelecionado")
    modeloProdutoSelecionado.textContent = features.model

    const corProdutoSelecionado = document.getElementById("corProdutoSelecionado")
    corProdutoSelecionado.textContent = features.color

    const composicaoProdutoSelecionado = document.getElementById("composicaoProdutoSelecionado")
    composicaoProdutoSelecionado.textContent = features.composition
}

function adicionarAoCarrinho(produto, quantidade, quantidadeInput, tamanhoSelecionado) {
    var contador = true
    quantidadeInput.value = 0
    const product = { ...produto }

    items.forEach(item => {
        if (produto.id === item.id && tamanhoSelecionado.value === item.size) {
            item.quantity += quantidade
            !contador
        }
    });

    if (contador) {
        product.size = tamanhoSelecionado.value
        product.quantity = quantidade
        items.push(product)
    }
    localStorage.setItem('carrinho', JSON.stringify(items))    
    const radios = document.querySelectorAll('input[name="tamanho"]');
    radios.forEach(radio => radio.checked = false);
    document.getElementById('modal').style.display = 'none';
}

//
function scrollLeftProduct() {
    catalogo.scrollBy({
        left: -200, // A largura do seu card 
        behavior: 'smooth'
    });
}
function scrollRightProduct() {
    catalogo.scrollBy({
        left: 200, // A largura do seu card 
        behavior: 'smooth'
    });
}

function scrollLeftCategory() {
    categorias.scrollBy({
        left: -200, // A largura do seu card 
        behavior: 'smooth'
    });
}
function scrollRightCategory() {
    categorias.scrollBy({
        left: 200, // A largura do seu card 
        behavior: 'smooth'
    });
}

function logout() {
    localStorage.clear();
    window.location.href = "../../index.html"
}

function closemodal() {
    document.getElementById('modal').style.display = 'none';
}